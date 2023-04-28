package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

/**
 * Collection of geometries that describes a scene together.
 *
 */

public class Geometries implements Intersectable{

    private List<Intersectable> GeometryList;

    public Geometries() {
        GeometryList = new LinkedList<Intersectable>();
    }
    public Geometries(Intersectable... geometries)
    {
        GeometryList = new LinkedList<>();
        this.add(geometries);
    }
    public void add(Intersectable... geometries)
    {
        Collections.addAll(GeometryList,geometries);
    }


    @Override
    public List<Point> findIntsersections(Ray ray) {
        List<Point> result = null;
        for(var item : GeometryList)
        {
            List<Point> itemList = item.findIntsersections(ray);
            if (itemList != null)
            {
                if (result == null)
                {
                    result = new LinkedList<>(itemList);
                }
                else
                {
                    result.addAll(itemList);
                }
            }
        }
        return result;
    }
}
