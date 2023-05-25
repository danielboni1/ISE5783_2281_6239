package renderer;

import geometries.Intersectable.GeoPoint;
import lighting.LightSource;
import primitives.*;
import scene.Scene;

import java.util.List;

import static primitives.Util.alignZero;

/**
 * A basic implementation of a ray tracer that traces rays in a scene.
 */
public class RayTracerBasic extends RayTracerBase {

    /**
     * Constructs a RayTracerBasic with the given scene.
     *
     * @param scene the scene to trace rays in.
     */
    public RayTracerBasic(Scene scene) {
        super(scene);
    }

    @Override
    public Color traceRay(Ray ray) {
        List<GeoPoint> intersections = scene.geometries.findGeoIntersectionsHelper(ray);

        if (intersections == null) {
            return scene.background;
        }

        GeoPoint closestPoint = ray.findClosestGeoPoint(intersections);
        return calcColor(closestPoint,ray);
    }

    /**
     * Calculates the color at a given point in the scene using the Phong reflection model.
     *
     * @param geoPoint the geometric point to calculate the color at.
     * @param ray      the ray that intersects the point.
     * @return the calculated color at the given point.
     */
    private Color calcColor(GeoPoint geoPoint, Ray ray) {
        Color Ia = scene.ambientLight.getIntensity(); // Ambient light intensity
        Color Ie = geoPoint.geometry.getEmission(); // Emission color of the intersected geometry
        Color Ils = Color.BLACK; // Total accumulated color from all light sources
        Material material = geoPoint.geometry.getMaterial(); // Material properties of the intersected geometry
        Vector n = geoPoint.geometry.getNormal(geoPoint.point); // Normal vector at the intersection point
        Vector v = ray.getDir(); // Direction of the viewing ray

        for (var light : scene.lights) {
            Vector l = light.getL(geoPoint.point); // Direction from the intersection point to the light source
            double nl = n.dotProduct(l); // Cosine of the angle between the normal vector and the light vector
            double vn = v.dotProduct(n); // Cosine of the angle between the normal vector and the viewing vector

            if (nl * vn > 0) {
                Double3 kD = material.getKd(); // Diffuse reflection coefficient
                Double3 kS = material.getKs(); // Specular reflection coefficient
                Vector r = l.subtract(n.scale(l.dotProduct(n) * 2)); // Reflection direction
                Double3 diff = kD.scale(Math.abs(l.dotProduct(n))); // Diffuse reflection color
                Double3 spec = kS.scale(Math.pow(Math.max(0, v.scale(-1).dotProduct(r)), material.getnShininess())); // Specular reflection color
                Color Il = light.getIntensity(geoPoint.point).scale(diff.add(spec)); // Light intensity scaled by the reflection colors
                Ils = Ils.add(Il); // Accumulate the light contribution
            }
        }

        return Ia.add(Ie).add(Ils); // Total color is the sum of ambient, emission, and light contributions
    }



}
