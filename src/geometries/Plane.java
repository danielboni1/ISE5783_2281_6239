/**
 * The Plane class represents a plane shape in a three-dimensional space.
 * It implements the Geometry interface and provides methods for getting the plane's normal vector.
 */
package geometries;

import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

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
        Vector v1 = p1.subtract(p0);
        Vector v2 = p2.subtract(p0);
        Vector N = v1.crossProduct(v2);
        if(isZero(v1.dotProduct(v2)))//todo: check if the vectors are on the same line
            throw new IllegalArgumentException("The points are on the same line");
        this.normal = N.normalize();
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
        return normal;
    }

}
