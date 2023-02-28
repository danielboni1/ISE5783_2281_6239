package geometries;

import primitives.Point;
import primitives.Vector;

public class Plane implements Geometry {
    Point p0;
    Vector normal;

    public Plane(Point p0, Vector normal) {
        if (normal.length()==1)
        {
            normal = normal.normalize();
        }
        this.p0 = p0;
        this.normal = normal;
    }
    public Plane(Point p0,Point p1,Point p2) {
        this.p0 = p0;
        this.normal = null;
    }


    public Point getP0() {
        return p0;
    }

    public Vector getNormal() {
        return normal;
    }
    public Vector getNormal(Point point)
    {
        return null;
    }
}
