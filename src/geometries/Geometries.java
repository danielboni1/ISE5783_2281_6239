package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

/**
 * Collection of geometries that describes a scene together.
 * Contains a list of the geometries in the scene.
 * This class implements Intersectale interface.
 */

public class Geometries extends Intersectable{

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
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray) {
        List<GeoPoint> result = null;
        for(var item : GeometryList)
        {
            List<GeoPoint> itemList = item.findGeoIntersectionsHelper(ray);
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
