/**
 * The Tube class represents a tube shape in a three-dimensional space.
 * It extends the RadialGeometry class and adds an axis ray attribute to define the direction of the tube.
 */
package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

public class Tube extends RadialGeometry {
    /**
     * the axis ray of the tube.
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
        Vector v = point.subtract(axisRay.getP0());
        double t = axisRay.getDir().dotProduct(v);
        if(t>0)//ep - not in the same line
        {
            Vector vt = axisRay.getDir().scale(t);
            Point o = axisRay.getP0().add(vt);
            return point.subtract(o).normalize();
        }
       else
        {
            return point.subtract(axisRay.getP0()).normalize();
        }
    }

}
