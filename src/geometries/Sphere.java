/**
 * The Sphere class represents a sphere shape in a three-dimensional space.
 * It extends the RadialGeometry abstract class and provides methods for getting the sphere's center and radius.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.awt.*;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

public class Sphere extends RadialGeometry {
    /**
     * The center point of sphere.
     */
    final Point center;

    /**
     * Constructs a new Sphere object with the specified radius, center point, and radius length.
     *
     * @param radius the radius of the sphere.
     * @param center the center point of the sphere.
     */
    public Sphere(double radius, Point center) {
        super(radius);
        this.center = center;
    }

    /**
     * Returns the center point of the sphere.
     *
     * @return the center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Returns the radius length of the sphere.
     *
     * @return the radius length of the sphere.
     */
    public Double getRadios() {
        return radius;
    }

    @Override
    public Vector getNormal(primitives.Point point) {
        return point.subtract(center).normalize();
    }
    // This function takes a Ray object as input and returns a list of intersection points with the sphere
    @Override
    public List<Point> findIntersections(Ray ray)
    {
        // Extracting the starting point of the Ray and its direction vector
        Point p0 =  ray.getP0();
        Vector v = ray.getDir();
        Vector u;

        // Checking whether the Ray starts at the center of the sphere
        try{
            u = center.subtract(p0);
        }
        catch (IllegalArgumentException ex)
        {
            // If the Ray starts at the center of the sphere,
            // the intersection points are the endpoints of the diameter
            Point p1 = ray.getPoint(radius);
            return List.of(p1);
        }

        // Calculating the intersection points
        double tm = alignZero(v.dotProduct(u));
        // The squared distance between the point and the middle of the chord
        double dSquared = alignZero(u.lengthSquared() - tm * tm);
        double thSquared = alignZero(radius*radius - dSquared);
        if (thSquared<=0)
        {
            return null;
        }
        double th= Math.sqrt(thSquared);
        double t1 = alignZero(tm+th);
        double t2 = alignZero(tm-th);
        if (t1>0 && t2>0)
        {
            // If the Ray intersects the sphere at two points, return both points
            Point p1 = ray.getPoint(t1);
            Point p2 = ray.getPoint(t2);
            return List.of(p1,p2);
        }
        if (t1>0){
            // If the Ray intersects the sphere at only p1, return that point
            Point p1 = ray.getPoint(t1);
            return List.of(p1);
        }
        if (t2>0)
        {
            // If the Ray intersects the sphere at only p2, return that point
            Point p2 = ray.getPoint(t2);
            return List.of(p2);
        }
        // If the Ray does not intersect the sphere, return null
        return null;
    }

}
