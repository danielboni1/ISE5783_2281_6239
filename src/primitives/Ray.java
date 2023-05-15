/**
 * The Ray class represents a ray in 3D space.
 */
package primitives;

import java.util.List;
import java.util.Objects;

import static primitives.Util.isZero;

/**
 * The Ray class represents a ray in 3D space.
 */
public class Ray {
    /**
     * The starting point of the ray.
     */
    final Point p0;

    /**
     * The normalized direction of the ray.
     */
    final Vector dir;

    /**
     * Constructs a Ray object with a starting point and a direction vector.
     *
     * @param p0  The starting point of the ray.
     * @param dir The direction of the ray.
     */
    public Ray(Point p0, Vector dir) {
        this.p0 = p0;
        this.dir = dir.normalize();
    }

    /**
     * Returns the starting point of the ray.
     *
     * @return The starting point of the ray.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter for dir.
     *
     * @return The direction of the ray.
     */
    public Vector getDir() {
        return dir;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Ray ray)) return false;
        return Objects.equals(p0, ray.p0) && Objects.equals(dir, ray.dir);
    }

    @Override
    public int hashCode() {
        return Objects.hash(p0, dir);
    }

    @Override
    public String toString() {
        return "Ray{" +
                "p0=" + p0 +
                ", dir=" + dir +
                '}';
    }

    /**
     * Calculating a point on a ray.
     *
     * @param t the length from p0
     * @return the point on the ray
     */
    public Point getPoint(double t) {
        if (isZero(t)) {
            return p0;
        }
        return p0.add(dir.scale(t));
    }

    /**
     * The function finds the closest point from the camera.
     * @param points- the list of the points that the ray intersect.
     * @return the closest point from the camera.
     */
   public Point findClosestPoint(List<Point> points)
    {
        if (points == null) {
            return null;
        }
        Point smallestDistance = points.get(0);
        for (Point p : points) {
            if (p0.distance(p) < p0.distance(smallestDistance)) {
                smallestDistance = p;
            }
        }
        return smallestDistance;
    }

}
