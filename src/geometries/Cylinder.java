/**
 * The Cylinder class represents a cylinder shape in a three-dimensional space.
 * It extends the Tube class and adds a height attribute to define the length of the cylinder.
 * */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Cylinder extends Tube{
    /**
     * The height of the Cylinder.
     */
    double height;

    /**
     * Constructs a new Cylinder object with the specified radius, axis ray, and height.
     *
     * @param radius the radius of the cylinder.
     * @param axisRay the axis ray of the cylinder.
     * @param height the height of the cylinder.
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    /**
     * Returns the height of the cylinder.
     *
     * @return the height of the cylinder.
     */
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        return super.getNormal(point);
    }

}
