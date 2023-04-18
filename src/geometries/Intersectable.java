package geometries;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

/**
 * This interface
 */
public interface Intersectable {
    List<Point> findIntsersections(Ray ray);

}
