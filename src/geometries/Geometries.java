package geometries;

import primitives.Point;
import primitives.Ray;

import java.util.*;

/**
 * Collection of geometries that describes a scene together.
 * Contains a list of the geometries in the scene.
 * This class implements the Intersectable interface.
 */
public class Geometries extends Intersectable {

    private List<Intersectable> GeometryList;

    /**
     * Constructs an empty Geometries object.
     * Initializes the list of geometries as an empty LinkedList.
     */
    public Geometries() {
        GeometryList = new LinkedList<Intersectable>();
    }

    /**
     * Constructs a Geometries object with the given geometries.
     *
     * @param geometries the geometries to add to the Geometries object.
     */
    public Geometries(Intersectable... geometries) {
        GeometryList = new LinkedList<>();
        this.add(geometries);
    }

    /**
     * Adds the given geometries to the list of geometries.
     *
     * @param geometries the geometries to add.
     */
    public void add(Intersectable... geometries) {
        Collections.addAll(GeometryList, geometries);
    }


    @Override
    public List<GeoPoint> findGeoIntersectionsHelper(Ray ray, double maxDistance) {
        List<GeoPoint> result = null;
        for (var item : GeometryList) {
            List<GeoPoint> itemList = item.findGeoIntersectionsHelper(ray, maxDistance);
            if (itemList != null) {
                if (result == null) {
                    result = new LinkedList<>(itemList);
                } else {
                    result.addAll(itemList);
                }
            }
        }
        return result;
    }
}
