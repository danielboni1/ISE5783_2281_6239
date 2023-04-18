package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Sphere
 */
class SphereTest {
    /**
     * Test method for {@link geometries.Sphere#getNormal(primitives.Point)}
     */
    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point center = new Point(0, 0, 0);
        double radius = 2;
        Sphere sphere = new Sphere(radius, center);
        Point point = new Point(0, 0, 2);

        Vector normal = sphere.getNormal(point);
        //TC01: ensure that the vector is correct
        assertTrue(normal.dotProduct(point.subtract(center)) == 2);
    }

    @Test
    List<Point> testfindIntsersections() {
        Point center = new Point(0, 0, 0);
        double radius = 2;
        Sphere sphere = new Sphere(radius, center);



    }
}