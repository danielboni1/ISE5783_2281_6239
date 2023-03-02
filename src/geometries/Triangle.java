/**
 * The Triangle class represents a three-sided polygon in a three-dimensional space.
 * It extends the Polygon class and provides a constructor that accepts three Point objects as vertices.
 */
package geometries;

import primitives.Point;

public class Triangle extends Polygon{
    /**
     * Constructs a new Triangle object with the specified vertices.
     *
     * @param vertices the vertices of the triangle.
     */
    public Triangle(Point... vertices) {
        super(vertices);
    }
}
