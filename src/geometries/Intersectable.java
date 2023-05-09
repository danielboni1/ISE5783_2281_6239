package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;

/**
 * This interface to
 */
public interface Intersectable {
    /**
     * Computes intersections between the shape and a given ray.
     *
     *
     * @param ray the ray to intersect with the plane.
     * @return a list of intersection points, or null if there are no intersections.
     */
    List<Point> findIntersections(Ray ray);

}
