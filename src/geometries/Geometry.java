
package geometries;

import primitives.Color;
import primitives.Material;
import primitives.Point;
import primitives.Vector;

/**
 * The Geometry interface represents a geometric shape in a three-dimensional space.
 * It provides methods for getting the normal vector of the shape at a specific point.
 */
public abstract class Geometry extends Intersectable {
    protected Color emission = Color.BLACK;
    private Material material = new Material();
    /**************************** Getters *********************************************/

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
     * Getter.
     *
     * @return the Material of the geometry.
     */
    public Material getMaterial() {
        return material;
    }

    /****************************** Setters *********************************************/

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

    /**
     * Setter.
     *
     * @param material- the material to set.
     * @return the geometry object with the updated Material.
     */
    public Geometry setMaterial(Material material) {
        this.material = material;
        return this;
    }
}
