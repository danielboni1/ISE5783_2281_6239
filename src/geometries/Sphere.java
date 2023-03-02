/**
 * The Sphere class represents a sphere shape in a three-dimensional space.
 * It extends the RadialGeometry abstract class and provides methods for getting the sphere's center and radius.
 */
package geometries;

import primitives.Vector;

import java.awt.*;

public class Sphere extends RadialGeometry{
    /**
     * The center point of sphere.
     */
    final Point center;
    /**
     * The radius of the sphere.
     */
    final Double radios;

    /**
     * Constructs a new Sphere object with the specified radius, center point, and radius length.
     *
     * @param radius the radius of the sphere.
     * @param center the center point of the sphere.
     * @param radios the radius length of the sphere.
     */
    public Sphere(double radius, Point center, Double radios) {
        super(radius);
        this.center = center;
        this.radios = radios;
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
        return radios;
    }

    @Override
    public Vector getNormal(primitives.Point point) {
        return null;
    }

}
