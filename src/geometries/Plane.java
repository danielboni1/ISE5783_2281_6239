/**
 * The Plane class represents a plane shape in a three-dimensional space.
 * It implements the Geometry interface and provides methods for getting the plane's normal vector.
 */
package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
    Point p0;
    Vector normal;

    /**
     * Constructs a new Plane object with the specified point and normal vector.
     * If the length of the normal vector is not 1, it will be normalized.
     *
     * @param p0 the point on the plane.
     * @param normal the normal vector of the plane.
     */
    public Plane(Point p0, Vector normal) {
        if (normal.length()==1)
        {
            normal = normal.normalize();
        }
        this.p0 = p0;
        this.normal = normal;
    }

    /**
     * Constructs a new Plane object with the specified three points.
     * The normal vector of the plane is calculated as the cross product of two vectors from the three points.
     *
     * @param p0 the first point on the plane.
     * @param p1 the second point on the plane.
     * @param p2 the third point on the plane.
     */
    public Plane(Point p0,Point p1,Point p2) {
        this.p0 = p0;
        this.normal = null;
    }

    /**
     * Returns the point on the plane.
     *
     * @return the point on the plane.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Returns the normal vector of the plane.
     *
     * @return the normal vector of the plane.
     */
    public Vector getNormal() {
        return normal;
    }

    public Vector getNormal(Point point)
    {
        return null;
    }

}
