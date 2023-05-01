/**
 * The Triangle class represents a three-sided polygon in a three-dimensional space.
 * It extends the Polygon class and provides a constructor that accepts three Point objects as vertices.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

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
    public List<Point> findIntersections(Ray ray) {
        List<Point> result = plane.findIntersections(ray);
        if (result == null) {
            return null;
        }
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

        double s1 = n1.dotProduct(v);
        double s2 = n2.dotProduct(v);
        double s3 = n3.dotProduct(v);

        if (s1 > 0 && s2 > 0 && s3 > 0 || s1 < 0 && s2 < 0 && s3 < 0)
            return result;

        return null;
    }
}
