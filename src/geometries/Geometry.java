
package geometries;

import primitives.*;

import java.util.List;
/**
 * The Geometry interface represents a geometric shape in a three-dimensional space.
 * It provides methods for getting the normal vector of the shape at a specific point.
 * */
public abstract class Geometry extends Intersectable{
    protected Color emission = Color.BLACK;
    private Material material =new Material();

    /**
     * Returns the normal vector of the shape at the specified point.
     *
     * @param point the point on the shape where the normal vector is needed.
     * @return the normal vector of the shape at the specified point.
     */
    public abstract Vector getNormal(Point point);


    /**
     * Getter.
     *
     * @return the emission color of the geometry.
     */
    public Color getEmission() {
        return emission;
    }

    /**
     * Setter.
     *
     * @param emission- the emission color to set.
     * @return the geometry object with the updated emission color.
     */
    public Geometry setEmission(Color emission) {
        this.emission = emission;
        return this;
    }

    public Material getMaterial() {
        return material;
    }

    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
