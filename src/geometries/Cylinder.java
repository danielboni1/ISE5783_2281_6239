package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.ArrayList;
import java.util.List;

import static primitives.Util.alignZero;

/**
 *  Represents a Cylinder in 3D Cartesian coordinate system, which is a tube with a height.
 *  The tube is defined by its radius, its axis ray and its height.
 *  The tube is bounded by two disks perpendicular to the axis ray, at the top and bottom of the tube.
 *  This class extends Tube.
 */

public class Cylinder extends Tube {
    /**
     * The height of the tube.
     */
    double height;

    /**
     * Constructs a Cylinder object with the specified radius, axis ray and height.
     * @param radius the radius of the Cylinder.
     * @param axisRay the axis ray of the Cylinder.
     * @param height the height of the Cylinder.
     */
    public Cylinder(double radius, Ray axisRay, double height) {
        super(radius, axisRay);
        this.height = height;
    }

    /**
     * Returns the height of the tube.
     * @return the height of the tube.
     */
    public double getHeight() {
        return height;
    }

    @Override
    public Vector getNormal(Point point) {
        Point downCenter = this.getAxisRay().getP0();
        Point upCenter = downCenter.add(this.getAxisRay().getDir().scale(getHeight()));
        Vector upVector = upCenter.subtract(downCenter).normalize();
        Vector downVector = downCenter.subtract(upCenter).normalize();
        if (point.equals(downCenter)) {
            return downVector;
        }
        if (point == upCenter) {
            return upVector;
        }
        Vector v = point.subtract(axisRay.getP0());
        double t = axisRay.getDir().dotProduct(v);
        if (t == 0) {
            return downVector;
        }
        if (t == height) {
            return upVector;
        }
        Vector vt = axisRay.getDir().scale(t);
        Point o = axisRay.getP0().add(vt);
        return point.subtract(o).normalize();
    }



     //we find the projection and calcs its size and if it is like the height the point inside the cylinder (we can use dot product cause the axsis in normalized )
    @Override
    public List<Point> findIntersections(Ray ray) {
        List<Point> res = new ArrayList<>();
        List<Point> lst = super.findIntersections(ray);
        if (lst != null)
            for (Point point : lst) {
                double distance = alignZero(point.subtract(axisRay.getP0()).dotProduct(axisRay.getDir()));
                if (distance > 0 && distance <= height)
                    res.add(point);
            }
        Vector v = axisRay.getDir();
        Point basePoint1 = axisRay.getP0();
        Plane basePlane1 = new Plane(basePoint1, v);
        Point basePoint2;
        if (height != 0){
            basePoint2 = basePoint1.add(v.scale(height));
        }else{
            basePoint2 = basePoint1;
        }
        Plane basePlane2 = new Plane(basePoint2, v);
        lst = basePlane1.findIntersections(ray);
        if (lst != null)
            for (Point point : lst) {
                double distanceSquared = alignZero(point.distanceSquared(basePoint1));
                if (alignZero(distanceSquared - radius*radius) < 0)
                    res.add(point);
            }
        lst = basePlane2.findIntersections(ray);
        if (lst != null)
            for (Point point : lst) {
                double distanceSquared = alignZero(point.distanceSquared(basePoint2));
                if (alignZero(distanceSquared - radius*radius) < 0)
                    res.add(point);
            }
        if (res.size() == 0)
            return null;
        return res;
    }

}