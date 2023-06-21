/**
 * The Point class represents a point in three-dimensional space using three coordinates (x, y, z).
 */
package primitives;

import java.util.LinkedList;
import java.util.List;
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

    /**
     * Getter
     *
     * @return the first coordinate of the point
     */
    public double getX() {
        return xyz.d1;
    }

    /**
     * Getter
     *
     * @return the second coordinate of the point
     */
    public double getY() {
        return xyz.d2;
    }

    /**
     * Getter
     *
     * @return the third coordinate of the point
     */
    public double getZ() {
        return xyz.d3;
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

    /**
     * Helper method that generates list of points (on a circle) that we'll create a list of vectors from.
     *
     * @param u         - vector of the circle plane.
     * @param v         - vector of the circle plane.
     * @param numPoints - number of the point that will be created.
     * @param center    - the center of the circle.
     * @param radius    - the radius of the circle.
     * @return - linked list of the points.
     */
    public static List<Point> getRandomPoints(Vector u, Vector v, int numPoints, Point center, double radius) {

        //The list that will be  returned:
        List<Point> points = new LinkedList<>();

        // 2pai/n
        double angleIncrement = 2 * Math.PI / numPoints;

        for (int i = 0; i < numPoints; i++) {
            double angle = i * angleIncrement;

            double x = center.getX() + radius * (Math.cos(angle) * u.getX() + Math.sin(angle) * v.getX());
            double y = center.getY() + radius * (Math.cos(angle) * u.getY() + Math.sin(angle) * v.getY());
            double z = center.getZ() + radius * (Math.cos(angle) * u.getZ() + Math.sin(angle) * v.getZ());

            points.add(new Point(x, y, z));
        }

        return points;
    }


}
