package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class GeometriesTest {

    @Test
    void findIntsersections() {

        //============ Equivalence Partitions Tests ==============
        //TC01: The ray intersect part of the geometries
        Geometries geometries1 = new Geometries();
        Sphere sphere1 = new Sphere(0.5, new Point(0, 1, 1));
        Triangle triangle1 = new Triangle(new Point(0, 3, 0), new Point(1, 3, 1), new Point(-1, 3, 1));
        Polygon polygon1 = new Polygon (new Point(-1, 4, 2),new Point(1, 4, 2),
                new Point(1, 4, 0), new Point(-1, 4, 0));
        geometries1.add(sphere1);
        geometries1.add(triangle1);
        geometries1.add(polygon1);
        Ray ray = new Ray(new Point(0, 0, 1), new Vector(0, 1, 0));
        List<Point> result = geometries1.findIntsersections(ray);
        assertEquals(3, result.size(), "There are suppose to be 4 points");

        // =============== Boundary Values Tests ==================
        //TC10: Empty geometries collection
        Geometries geometries2 = new Geometries();
        ray = new Ray(new Point(0, 1, 0), new Vector(0, 1, 0));
        result = geometries2.findIntsersections(ray);
        assertNull(result, "TC10: The result is not null");

        //TC11: There is not a shape that intersect
        Triangle triangle2 = new Triangle(new Point(0,3,0),new Point(1,3,2),new Point(-1,3,2));
        geometries2.add(sphere1);
        geometries2.add(triangle2);
        geometries2.add(polygon1);
        ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
        result = geometries2.findIntsersections(ray);
        assertNull(result, "TC11: The result is not null");
        //TC12: Only one shape is intersect
        Geometries geometries3 = new Geometries();
        Polygon polygon2 = new Polygon(new Point(5,4,-5)
                ,new Point(5,4,5),new Point(-5,4,5),new Point(-5,4,-5));
        geometries3.add(sphere1);
        geometries3.add(polygon2);
        geometries3.add(triangle2);
        ray = new Ray(new Point(4.5, 0, 4.5), new Vector(0, 1, 0));
        result = geometries3.findIntsersections(ray);
        assertEquals(1, result.size(), "TC12: There are suppose to be 1 points");
        //TC13 All the shapes are intersect
        ray = new Ray(new Point(0, 0, 1), new Vector(0, 1, 0));
        result = geometries2.findIntsersections(ray);
        assertEquals(4, result.size(), "There are suppose to be 4 points");

    }
}