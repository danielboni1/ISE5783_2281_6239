package renderer;
import geometries.Intersectable.GeoPoint;
import primitives.Color;
import primitives.Point;
import primitives.Ray;
import scene.Scene;

import java.util.List;

/**
 * A basic implementation of a ray tracer that traces rays in a scene.
 *
 */
public class RayTracerBasic extends RayTracerBase{

    /**
     * Constructs a RayTracerBasic with the given scene.
     *
     * @param scene the scene to trace rays in.
     *
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
        return calcColor(closestPoint);
    }

    /**
     * Calculates the color at a given point in the scene.
     *
     * @param point the point to calculate the color at.
     * @return the calculated color at the given point.
     */
    private Color calcColor(GeoPoint geopoint) {
        return scene.ambientLight.getIntensity().add(geopoint.geometry.getEmission());
    }

}
