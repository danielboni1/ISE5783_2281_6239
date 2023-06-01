/**
 * The Triangle class represents a three-sided polygon in a three-dimensional space.
 * It extends the Polygon class and provides a constructor that accepts three Point objects as vertices.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static primitives.Util.alignZero;

public class Triangle extends Polygon {
    /**
     * Constructs a new Triangle object with the specified vertices.
     *
     * @param vertices the vertices of the triangle.
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance) {
        // Find the intersections between the Ray and the plane of the Polygon
        List<GeoPoint> result = plane.findGeoIntersectionsHelper(ray,maxDistance);

        // If there are no intersections, return null
        if (result == null) {
            return null;
        }

        // Calculate vectors and normals for the Polygon
        Point p0 = ray.getP0();
        Vector v = ray.getDir();

        Point p1 = vertices.get(0);
        Point p2 = vertices.get(1);
        Point p3 = vertices.get(2);

        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);
        Vector v3 = p3.subtract(p0);

        Vector n1 = v1.crossProduct(v2);
        Vector n2 = v2.crossProduct(v3);
        Vector n3 = v3.crossProduct(v1);

        double s1 = alignZero(n1.dotProduct(v));
        double s2 = alignZero(n2.dotProduct(v));
        double s3 = alignZero(n3.dotProduct(v));

        // Check if the intersections are valid
        if (s1 > 0 && s2 > 0 && s3 > 0 || s1 < 0 && s2 < 0 && s3 < 0) {
            // If all dot products have the same sign, the intersections are valid
            return List.of(new GeoPoint(this,result.get(0).point));
        }

        // If any dot product has a different sign than the others, there are no valid intersections
        return null;
    }
}
