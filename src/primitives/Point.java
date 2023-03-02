/**
 * The Point class represents a point in three-dimensional space using three coordinates (x, y, z).
 */
package primitives;

import java.util.Objects;

public class Point {
    /**
     * The xyz coordinates of the point as a Double3 object.
     */
    final Double3 xyz;

    /**
     * Constructs a point with the specified x, y, and z coordinates.
     *
     * @param x the x coordinate of the point
     * @param y the y coordinate of the point
     * @param z the z coordinate of the point
     */
    public Point(double x, double y, double z) {
        xyz = new Double3(x, y, z);
    }

    /**
     * Constructs a point from the specified Double3 object.
     *
     * @param double3 the Double3 object representing the xyz coordinates of the point
     */
    Point(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Point point)) return false;
        return xyz.equals(point.xyz);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xyz);
    }

    @Override
    public String toString() {
        return "Point:  " + xyz;
    }

    /**
     * Returns the Euclidean distance between this point and the specified point.
     *
     * @param other the point to calculate the distance to
     * @return the Euclidean distance between this point and the specified point
     */
    public double distance(Point other) {
        return Math.sqrt(distanceSquared(other));
    }

    /**
     * Returns the squared Euclidean distance between this point and the specified point.
     *
     * @param other the point to calculate the distance to
     * @return the squared Euclidean distance between this point and the specified point
     */
    public double distanceSquared(Point other) {
        double dx = other.xyz.d1 - xyz.d1;
        double dy = other.xyz.d2 - xyz.d2;
        double dz = other.xyz.d3 - xyz.d3;

        return dx * dx + dy * dy + dz * dz;
    }

    /**
     * Returns a new point resulting from adding the specified vector to this point.
     *
     * @param vector the vector to add to this point
     * @return a new point resulting from adding the specified vector to this point
     */
    public Point add(Vector vector) {
        return new Point((xyz.add(vector.xyz)));
    }

    /**
     * Returns a new vector resulting from subtracting the specified point from this point.
     *
     * @param point the point to subtract from this point
     * @return a new vector resulting from subtracting the specified point from this point
     */
    public Vector subtract(Point point) {
        return new Vector(xyz.subtract(point.xyz));
    }

}
