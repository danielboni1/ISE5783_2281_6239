/**
 * The Ray class represents a ray in 3D space.
 */
package primitives;

import java.util.Objects;

public class Ray {
    /**
     * The starting point of the ray.
     */
    final Point p0;
    /**
     * The direction of the ray.
     */
    final Vector dir;

    /**
     * Constructs a Ray object with a starting point and a direction vector.
     *
     * @param p0 The starting point of the ray.
     * @param dir The direction of the ray.
     */
    public Ray(Point p0, Vector dir) {
        if (dir.length()!=1)
        {
            dir = dir.normalize();
        }
        this.p0 = p0;
        this.dir = dir;
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
     * Returns the direction of the ray.
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

}
