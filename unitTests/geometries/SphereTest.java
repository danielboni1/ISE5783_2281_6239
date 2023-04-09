package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;
class SphereTest {

    @Test
    void getNormal() {
            Point center = new Point(0, 0, 0);
            double radius = 2;
            Sphere sphere = new Sphere(radius, center);

            Point point = new Point(0, 0, 2);
            Vector normal = sphere.getNormal(point);
            assertTrue(normal.dotProduct(point.subtract(center)) == 2);
    }
}