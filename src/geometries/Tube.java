package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Util;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;

import static primitives.Util.*;

/**
 * The Tube class represents a tube shape in a three-dimensional space.
 * It extends the RadialGeometry class and adds an axis ray attribute to define the direction of the tube.
 */
public class Tube extends RadialGeometry {
    /**
     * The axis ray of the tube.
     */
    Ray axisRay;

    /**
     * Constructs a new Tube object with the specified radius and axis ray.
     *
     * @param radius  the radius of the tube.
     * @param axisRay the axis ray of the tube.
     */
    public Tube(double radius, Ray axisRay) {
        super(radius);
        this.axisRay = axisRay;
    }

    /**
     * Returns the axis ray of the tube.
     *
     * @return the axis ray of the tube.
     */
    public Ray getAxisRay() {
        return axisRay;
    }

    @Override
    public Vector getNormal(Point point) {
        Point P0 = axisRay.getP0();
        Vector v = axisRay.getDir();

        Vector P0_P = point.subtract(P0);

        double t = alignZero(v.dotProduct(P0_P));

        if (t == 0d) {
            return P0_P.normalize();
        }

        Point o = P0.add(v.scale(t));

        if (point.equals(o)) {
            throw new IllegalArgumentException("point cannot be on the tube axis");
        }

        Vector n = point.subtract(o).normalize();

        return n;
    }

    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        Vector dir = ray.getDir();
        Vector v = axisRay.getDir();
        double dirV = dir.dotProduct(v);

        // In case the ray starts on the p0 of the axis ray.
        if (ray.getP0().equals(axisRay.getP0())) {
            //If the ray and the axis ray are in the same direction
            if (isZero(dirV))
                //Return p0+radius
                return List.of(new GeoPoint(this,ray.getPoint(radius)));
            //Opposite directions-> there are no intersection points
            if (dir.equals(v.scale(dir.dotProduct(v))))
                return null;

            //If it's not in the same direction and not in opposite directions
            return List.of(new GeoPoint(this, ray.getPoint(
                    Math.sqrt(radius * radius / dir.subtract(v.scale(dir.dotProduct(v)))
                            .lengthSquared()))));
        }

        Vector deltaP = ray.getP0().subtract(axisRay.getP0());
        double dpV = deltaP.dotProduct(v);

        double a = 1 - dirV * dirV;
        double b = 2 * (dir.dotProduct(deltaP) - dirV * dpV);
        double c = deltaP.lengthSquared() - dpV * dpV - radius * radius;

        if (isZero(a)) {
            if (isZero(b)) { // If a constant equation.
                return null;
            }
            return List.of(new GeoPoint(this,ray.getPoint(-c / b))); // if it's linear, there's a solution.
        }

        double discriminant = alignZero(b * b - 4 * a * c);

        if (discriminant < 0) // No real solutions ("MERUKAV").
            return null;

        //-b+-(SHORESH(d/2a))
        double t1 = alignZero(-(b + Math.sqrt(discriminant)) / (2 * a)); // Positive solution.
        double t2 = alignZero(-(b - Math.sqrt(discriminant)) / (2 * a)); // Negative solution.

        if (discriminant <= 0) // No real solutions ("MERUKAV").
            return null;

        if (t1 > 0 && t2 > 0) {
            List<GeoPoint> _points = new LinkedList<GeoPoint>();
            _points.add(new GeoPoint(this, ray.getPoint(t1)));
            _points.add(new GeoPoint(this,ray.getPoint(t2)));
            return _points;
        }
        else if (t1 > 0) {
            List<GeoPoint> _points = new LinkedList<GeoPoint>();
            _points.add(new GeoPoint(this,ray.getPoint(t1)));
            return _points;
        }
        else if (t2 > 0) {
            List<GeoPoint> _points = new LinkedList<GeoPoint>();
            _points.add(new GeoPoint(this,ray.getPoint(t2)));
            return _points;
        }
        return null;
    }

}
