package primitives;

import java.util.Objects;

public class Ray {
    final Point p0;
    final Vector dir;

    public Ray(Point p0, Vector dir) {
        if (dir.length()!=1)
        {
            dir = dir.normalize();
        }
        this.p0 = p0;
        this.dir = dir;
    }

    public Point getP0() {
        return p0;
    }

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
