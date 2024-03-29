
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Double3.ZERO;
import static primitives.Util.alignZero;
import static primitives.Util.isZero;
/**
 * The Plane class represents a plane shape in a three-dimensional space.
 * It implements the Geometry interface and provides methods for getting the plane's normal vector.
 */
public class Plane extends Geometry {

    private Point p0;
    private Vector normal;

    /**
     * Constructs a new Plane object with the specified point and normal vector.
     * If the length of the normal vector is not 1, it will be normalized.
     *
     * @param p0     the point on the plane.
     * @param normal the normal vector of the plane.
     */
    public Plane(Point p0, Vector normal) {
        if (normal.length() != 1) {
            normal = normal.normalize();
        }
        this.p0 = p0;
        this.normal = normal;
    }

    /**
     * Constructs a new Plane object with the specified three points.
     * The normal vector of the plane is calculated as the cross product
     * of two vectors from the three points.
     *
     * @param p0 the first point on the plane.
     * @param p1 the second point on the plane.
     * @param p2 the third point on the plane.
     */
    public Plane(Point p0, Point p1, Point p2) {
        this.p0 = p0;
        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);
        Vector N = v1.crossProduct(v2);
        if (N.lengthSquared() == 0)
            throw new IllegalArgumentException("The points are on the same line");
        this.normal = N.normalize();
    }

    /**
     * Getter.
     *
     * @return the point on the plane.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter.
     *
     * @return the normal vector of the plane.
     */
    public Vector getNormal() {
        return normal;
    }


    @Override
    public Vector getNormal(Point point) {
        return normal;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        Point rayP0 = ray.getP0();
        Vector v = ray.getDir();
        Vector n = this.normal;

        // check if the ray starts on the plane
        if (rayP0.equals(p0)) {
            return null;
        }

        // calculate the distance between the ray's start point and the plane
        Vector p0_q0 = p0.subtract(rayP0);
        double nP0Q0 = alignZero(n.dotProduct(p0_q0));

        // if the ray is parallel to the plane or is on the opposite direction, there are no intersections
        if (isZero(nP0Q0)) {
            return null;
        }

        // calculate the cosine of the angle between the ray direction and the plane normal
        double nv = alignZero(n.dotProduct(v));

        // if the ray is parallel to the plane, there are no intersections
        if (isZero(nv)) {
            return null;
        }

        // calculate the distance from the ray's start point to the intersection point
        double t = alignZero(nP0Q0 / nv);

        // if the intersection point is behind the ray's start point,
        // there are no intersections
        if (t <= 0) {
            return null;
        }

        // calculate the intersection point
        Point point = ray.getPoint(t);

        if (alignZero(t - maxDistance) <= 0) {
            GeoPoint geoP = new GeoPoint(this, point);
            return List.of(geoP);
        }

        return null;
    }
    /**
     * retruns two normalized orthogonal vectors in the plane
     *
     * @return
     */
    public List<Vector> getBaseVectors() {
        Vector v1;
        try {
            v1 = normal.crossProduct(new Vector(1, 0, 0));
        } catch (IllegalArgumentException e) {
            v1 = normal.crossProduct(new Vector(0, 1, 0));
        }
        Vector v2 = normal.crossProduct(v1);
        return List.of(v1.normalize(), v2.normalize());
    }

}
