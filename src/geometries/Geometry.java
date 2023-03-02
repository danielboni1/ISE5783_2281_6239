/**
 * The Geometry interface represents a geometric shape in a three-dimensional space.
 * It provides methods for getting the normal vector of the shape at a specific point.
 * */
package geometries;

import primitives.Point;
import primitives.Vector;

public interface Geometry {
    /**
     * Returns the normal vector of the shape at the specified point.
     *
     * @param point the point on the shape where the normal vector is needed.
     * @return the normal vector of the shape at the specified point.
     */
    Vector getNormal(Point point);



}
