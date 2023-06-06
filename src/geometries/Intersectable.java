package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.List;
import java.util.Objects;

/**
 * The Intersectable class represents a geometric shape that can be intersected by a ray.
 * It provides methods for computing intersections between the shape and a given ray.
 */
public abstract class Intersectable {

    /**
     * Computes intersections between the shape and a given ray.
     * (for the tests from the beginning...)
     *
     * @param ray the ray to intersect with the shape.
     * @return a list of intersection points, or null if there are no intersections.
     */
    public final List<Point> findIntersections(Ray ray) {
        var geoList = findGeoIntersections(ray);
        return geoList == null ? null : geoList.stream().map(gp -> gp.point).toList();
    }

    /**
     * Computes intersections between the shape and a given ray, returning a list of GeoPoints.
     * A GeoPoint contains information about the intersected geometry and the intersection point.
     *
     * @param ray the ray to intersect with the shape.
     * @return a list of GeoPoints representing the intersections, or null if there are no intersections.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray) {
        return findGeoIntersections(ray, Double.POSITIVE_INFINITY);
    }

    /**
     * Computes intersections between the shape and a given ray, up to a specified maximum distance.
     *
     * @param ray         the ray to intersect with the shape.
     * @param maxDistance the maximum allowed distance for an intersection point to be considered.
     * @return a list of GeoPoints representing the intersections, or null if there are no intersections.
     */
    public final List<GeoPoint> findGeoIntersections(Ray ray, double maxDistance) {
        return findGeoIntersectionsHelper(ray, maxDistance);
    }

    /**
     * Helper method for computing intersections between the shape and a given ray.
     * Subclasses must implement this method to provide the specific intersection logic.
     *
     * @param ray         the ray to intersect with the shape.
     * @param maxDistance the maximum allowed distance for an intersection point to be considered.
     * @return a list of GeoPoints representing the intersections, or null if there are no intersections.
     */
    protected abstract List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance);

    /**
     * Represents a geometric point on the shape.
     * Contains information about the intersected geometry and the intersection point.
     */
    public static class GeoPoint {
        public Geometry geometry;
        public Point point;

        /**
         * Constructs a GeoPoint with the given geometry and intersection point.
         *
         * @param geometry the intersected geometry.
         * @param point    the intersection point.
         */
        public GeoPoint(Geometry geometry, Point point) {
            this.geometry = geometry;
            this.point = point;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof GeoPoint geoPoint)) return false;
            return Objects.equals(geometry, geoPoint.geometry) && Objects.equals(point, geoPoint.point);
        }

        @Override
        public int hashCode() {
            return Objects.hash(geometry, point);
        }

        @Override
        public String toString() {
            return "GeoPoint{" +
                    "geometry=" + geometry +
                    ", point=" + point +
                    '}';
        }
    }
}
