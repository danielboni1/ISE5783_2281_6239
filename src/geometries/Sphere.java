/**
 * The Sphere class represents a sphere shape in a three-dimensional space.
 * It extends the RadialGeometry abstract class and provides methods for getting the sphere's center and radius.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.awt.*;
import java.util.List;

public class Sphere extends RadialGeometry {
    /**
     * The center point of sphere.
     */
    final Point center;

    /**
     * Constructs a new Sphere object with the specified radius, center point, and radius length.
     *
     * @param radius the radius of the sphere.
     * @param center the center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return the center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns the radius length of the sphere.
     *
     * @return the radius length of the sphere.
     */
    public Double getRadios() {
        return radius;
    }

    @Override
    public Vector getNormal(primitives.Point point) {
        return point.subtract(center).normalize();
    }
    @Override
    public List<Point> findIntsersections(Ray ray)
    {
        return null;
    }

}
