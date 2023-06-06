package geometries;

/**
 * The RadialGeometry class is an abstract class that represents a geometry shape with a radius.
 * It implements the Geometry interface and provides a protected radius attribute.
 * */
public abstract class RadialGeometry extends Geometry{
    /**
     * The radius of the shape
     */
    protected double radius;

    /**
     * Constructs a new RadialGeometry object with the specified radius.
     *
     * @param radius the radius of the geometry shape.
     */
    public RadialGeometry(double radius) {
        this.radius = radius;
    }
}
