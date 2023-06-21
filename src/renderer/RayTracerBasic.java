package renderer;


import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The RayTracerBasic class is responsible for performing basic ray tracing operations.
 */
public class RayTracerBasic extends RayTracerBase {

    private static final int MAX_NUMBER_OF_SHADOW_RAYS = 1;
    private static final double DELTA = 0.1;
    private static final int MAX_CALC_COLOR_LEVEL = 10;
    private static final double MIN_CALC_COLOR_K = 0.001;
    /**
     * Constructs a RayTracerBasic object with the given scene.
     *
     * @param scene the scene to trace rays in.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        GeoPoint closestPoint = findClosestIntersection(ray);
        return closestPoint == null ? scene.getBackground() : calcColor(closestPoint, ray);


    }

    /**
     * Calculates the color at the given intersection point using the Phong reflection model.
     *
     * @param geo the intersection point and geometry.
     * @param ray the ray that intersected the geometry.
     * @return the color at the intersection point.
     */
    private Color calcColor(GeoPoint geo, Ray ray) {
        return calcColor(geo, ray, MAX_CALC_COLOR_LEVEL, Double3.ONE)
                .add(scene.getAmbientLight().getIntensity());
    }

    /**
     * Calculates the color at the given intersection point using the Phong reflection model recursively.
     *
     * @param geo   the intersection point and geometry.
     * @param ray   the ray that intersected the geometry.
     * @param level the current recursion level.
     * @param k     the accumulation factor for transparency and reflection effects.
     * @return the color at the intersection point.
     */
    private Color calcColor(GeoPoint geo, Ray ray, int level, Double3 k) {
        Color color = geo.geometry.getEmission();

        Vector v = ray.getDir();
        Vector n = geo.geometry.getNormal(geo.point);

        // check that ray is not parallel to geometry
        double nv = alignZero(n.dotProduct(v));

        if (isZero(nv)) {
            return color;
        }
        Material material = geo.geometry.getMaterial();
        color = color.add(calcLocalEffects(geo,ray,k));
        //color = color.add(calcLocalEffects(geo, material, n, v, nv, k));
        return level == 1 ? color : color.add(calcGlobalEffects(geo, material, n, v, nv, level, k));
    }

    /**
     * Calculates the local effects (diffuse and specular) of the light sources on the geometry at the intersection point.
     *
     * @param geo    the intersection point and geometry.
     * @param ray   the ray that intersected the geometry.
     * @param k        the accumulation factor for transparency and reflection effects.
     * @return the color resulting from the local effects.
     */
    private Color calcLocalEffects(GeoPoint geo, Ray ray, Double3 k) {
        Color color = geo.geometry.getEmission();
        Vector v = ray.getDir();
        Point point = geo.point;
        Vector n = geo.geometry.getNormal(point);
        double nv = alignZero(n.dotProduct(v));
        if (nv == 0)
            return color;
        Material material = geo.geometry.getMaterial();
        //option for soft shadows:

        for (LightSource lightSource : scene.lights) {
            Color rayBeam = Color.BLACK;//starting color for shade
            var vectors = lightSource.getLightVectors(point);
            for (var l : vectors) {
                double nl = alignZero(n.dotProduct(l));
                // check that light direction is towards shape and not behind
                if (nl * nv > 0) { // sign(nl) == sing(nv)
                    Double3 ktr = transparency(lightSource, l,n,geo);
                    if (!ktr.product(k).lowerThan(MIN_CALC_COLOR_K)) {
                        Color iL = lightSource.getIntensity(geo.point).scale(ktr);
                        rayBeam = rayBeam.add(iL.scale(calcDiffusive(material, nl)), iL.scale(calcSpecular(material,nl, n, l , v)));
                    }
                }
            }
            color = color.add(rayBeam.reduce(vectors.size()));

        }
        return color;
    }
    /**
     * Calculates the specular reflection component of the light on the geometry at the intersection point.
     *
     *
     * @param material the material of the intersected geometry.
     * @param n         the normal vector at the intersection point.
     * @param l         the direction vector from the intersection point to the light source.
     * @param nl        the dot product between the normal and light direction vectors.
     * @param v         the direction vector of the ray.
     * @return the color resulting from the specular reflection.
     */
    private Double3 calcSpecular(Material material, double nl, Vector n, Vector l, Vector v) {
        Vector r = l.subtract(n.scale(nl * 2));
        double scaler = Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), material.getShininess());
        if (Util.isZero(scaler)){
            return Double3.ZERO;
        }
        return material.getKs().scale(scaler);
    }
    /**
     * Calculates the diffuse reflection component of the light on the geometry at the intersection point.
     *
     *  @param material the material of the intersected geometry.
     * @param nl        the dot product between the normal and light direction vectors.
     * @return the color resulting from the diffuse reflection.
     */
    private Double3 calcDiffusive(Material material, double nl) {
        return material.getKd().scale(Math.abs(nl));
    }
    /**
     * Calculates the global effects (reflection and refraction) of the light on the geometry at the intersection point.
     *
     * @param geo      the intersection point and geometry.
     * @param material the material of the intersected geometry.
     * @param n        the normal vector at the intersection point.
     * @param v        the direction vector of the ray.
     * @param nv       the dot product between the normal and view direction vectors.
     * @param level    the current recursion level.
     * @param k        the accumulation factor for transparency and reflection effects.
     * @return the color resulting from the global effects.
     */
    private Color calcGlobalEffects(GeoPoint geo, Material material, Vector n, Vector v, double nv, int level, Double3 k) {
        Color color = Color.BLACK;
        Double3 kkr = material.getKr().product(k);
        if (!kkr.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(calcGlobalEffect(constructReflectedRay(geo.point, v, n), level, material.getKr(), kkr));
        Double3 kkt = material.getKt().product(k);
        if (!kkt.lowerThan(MIN_CALC_COLOR_K))
            color = color.add(
                    calcGlobalEffect(constructRefractedRay(geo.point, v, n), level, material.getKt(), kkt));
        return color;
    }

    /**
     * Calculates the color resulting from the global effects (reflection or refraction) recursively.
     *
     * @param ray   the ray for the global effect.
     * @param level the current recursion level.
     * @param kx    the accumulation factor for transparency and reflection effects.
     * @param kkx   the product of kx and kkx (transparency and reflection effects).
     * @return the color resulting from the global effect.
     */
    private Color calcGlobalEffect(Ray ray, int level, Double3 kx, Double3 kkx) {
        GeoPoint geo = findClosestIntersection(ray);
        return (geo == null ? scene.getBackground() : calcColor(geo, ray, level - 1, kkx)).scale(kx);
    }

    /**
     * Constructs a refracted ray from the given intersection point, view direction, and normal vector.
     *
     * @param point the intersection point.
     * @param v     the view direction vector.
     * @param n     the normal vector.
     * @return the refracted ray.
     */
    private Ray constructRefractedRay(Point point, Vector v, Vector n) {
        return new Ray(point, n, v);
    }

    /**
     * Constructs a reflected ray from the given intersection point, view direction, and normal vector.
     *
     * @param point the intersection point.
     * @param v     the view direction vector.
     * @param n     the normal vector.
     * @return the reflected ray.
     */
    private Ray constructReflectedRay(Point point, Vector v, Vector n) {
        double vn = v.dotProduct(n);

        if (vn == 0) {
            return null;
        }

        Vector r = v.subtract(n.scale(2 * vn));
        return new Ray(point, n, r);
    }

    /**
     * Checks if the intersection point is unshaded by other geometries from the light source's perspective.
     *
     * @param gp          the intersection point and geometry.
     * @param lightSource the light source.
     * @param l           the direction vector from the intersection point to the light source.
     * @param n           the normal vector at the intersection point.
     * @param nv          the dot product between the normal and view direction vectors.
     * @return true if the intersection point is unshaded, false otherwise.
     */
    private boolean unshaded(GeoPoint gp, LightSource lightSource, Vector l, Vector n, double nv) {
        // from point to light source
        Vector lightDirection = l.scale(-1);
        double nl = n.dotProduct(lightDirection);
        // move it a little outside from the geometry
        Vector delta = n.scale(nl > 0 ? DELTA : -DELTA);
        Point pointRay = gp.point.add(delta);
        Ray lightRay = new Ray(pointRay, lightDirection);

        double maxDistance = lightSource.getDistance(gp.point);
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxDistance);

        if (intersections == null) {
            return true;
        }

        for (var item : intersections) {
            if (item.geometry.getMaterial().getKt().lowerThan(MIN_CALC_COLOR_K)) {
                return false;
            }
        }

        return true;
    }

    /**
     * Calculates the transparency factor for the given light source
     * on the geometry at the intersection point.
     *
     * @param lightSource the light source.
     * @param l           the direction vector from the intersection point to the light source.
     * @param n           the normal vector at the intersection point.
     * @param gp          the intersection point and geometry.
     * @return the transparency factor for the light source.
     */
    private Double3 transparency(LightSource lightSource, Vector l, Vector n, GeoPoint gp) {
        Vector lightDirection = l.scale(-1); // from point to light source
        Point point = gp.point;
        Ray lightRay = new Ray(point, n, lightDirection);

        double maxDistance = lightSource.getDistance(point);
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(lightRay, maxDistance);

        Double3 ktr = Double3.ONE;

        if (intersections == null)
            return ktr;

        for (var geo : intersections) {
            ktr = ktr.product(geo.geometry.getMaterial().getKt());
            if (ktr.lowerThan(MIN_CALC_COLOR_K)) {
                return Double3.ZERO;
            }
        }
        return ktr;
    }

    /**
     * Finds the closest intersection point between the given ray and the geometries in the scene.
     *
     * @param ray the ray to find intersections with.
     * @return the closest intersection point, or null if no intersection is found.
     */
    private GeoPoint findClosestIntersection(Ray ray) {
        List<GeoPoint> intersections = scene.getGeometries().findGeoIntersections(ray);
        return intersections == null ? null : ray.findClosestGeoPoint(intersections);
    }
}

