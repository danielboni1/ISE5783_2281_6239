package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;
import java.awt.*;
import java.util.List;

import static primitives.Util.alignZero;
import static primitives.Util.isZero;

/**
 * The Sphere class represents a sphere shape in a three-dimensional space.
 * It extends the RadialGeometry abstract class and provides methods for getting the sphere's center and radius.
 */
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
     * Getter.
     *
     * @return the center point of the sphere.
     */
    public Point getCenter() {
        return center;
    }

    /**
     * Getter.
     *
     * @return the radius length of the sphere.
     */
    public Double getRadius() {
        return radius;
    }

    @Override
    public Vector getNormal(primitives.Point point) {
        return point.subtract(center).normalize();
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray,double maxDistance)
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
            if (alignZero(radius-maxDistance)<=0)
            {
                GeoPoint geoP1 = new GeoPoint(this,p1);
                return List.of(geoP1);
            }
            return null;
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
        if (t1>0 && t2>0 && alignZero(t1-maxDistance)<=0 && alignZero(t2-maxDistance)<=0)
        {
            // If the Ray intersects the sphere at two points, return both points
            Point p1 = ray.getPoint(t1);
            GeoPoint geoP1 = new GeoPoint(this,p1);
            Point p2 = ray.getPoint(t2);
            GeoPoint geoP2 = new GeoPoint(this,p2);
            return List.of(geoP1,geoP2);
        }
        if (t1>0&&alignZero(t1-maxDistance)<=0){
            // If the Ray intersects the sphere at only p1, return that point
            Point p1 = ray.getPoint(t1);
            GeoPoint geoP1 = new GeoPoint(this,p1);
            return List.of(geoP1);
        }
        if (t2>0&&alignZero(t2-maxDistance)<=0)
        {
            // If the Ray intersects the sphere at only p2, return that point
            Point p2 = ray.getPoint(t2);
            GeoPoint geoP2 = new GeoPoint(this,p2);
            return List.of(geoP2);
        }
        // If the Ray does not intersect the sphere, return null
        return null;
    }

    /**
     * checks if point is in sphere
     * @param p the point
     */
    public Boolean isIn(Point p){
        return center.distanceSquared(p) <= radius*radius;
    }

}
