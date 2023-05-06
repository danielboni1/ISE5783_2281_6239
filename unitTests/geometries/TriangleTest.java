package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

class TriangleTest {
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts = {
                new Point(0, 0, 1),
                new Point(1, 0, 0),
                new Point(0, 1, 0)
        };
        Triangle tri = new Triangle(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> tri.getNormal(new Point(0, 0, 1)), "");
        Vector result = tri.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 2; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 2 : i - 1]))),
                    "Polygon's normal is not orthogonal to one of the edges");
    }

    @Test
    void testfindIntersections() {
        // ============ Equivalence Partitions Tests ==============
        //TC01: The point of intersection inside the triangle
        Point[] pts = {
                new Point(1, 0, 0),
                new Point(0, -1, 0),
                new Point(0, 1, 0)
        };

        Triangle triangle = new Triangle(pts);
        Plane plane = new Plane(pts[0],pts[1],pts[2]);

        Ray ray = new Ray(new Point(0, 0, -1), new Vector(1, 0, 2));
        Point expected = new Point(0.5, 0, 0);

        List<Point> triangleResult = triangle.findIntersections(ray);
        assertEquals(1, triangleResult.size(), "number of elements is not equal");
        assertEquals(expected, triangleResult.get(0), "the point is wrong");

        //TC02: The intersection point is outside the triangle in front of the sides
        ray = new Ray(new Point(0, 0, -1), new Vector(0.5, -5, 1));
        List<Point> planeResult = plane.findIntersections(ray);
        triangleResult = triangle.findIntersections(ray);

        assertNotNull(planeResult,"should be not null");
        assertNull(triangleResult, "not suppose to be intersection point");

        //TC03: The point of intersection is outside the triangle opposite the vertices
        ray = new Ray(new Point(0, 0, -1), new Vector(2, 0, 1));
        planeResult = plane.findIntersections(ray);
        triangleResult = triangle.findIntersections(ray);

        assertNotNull(planeResult,"should be not null");
        assertNull(triangleResult, "not suppose to be intersection point");

        // =============== Boundary Values Tests ==================
        //TC10: Intersection point on a side
        ray = new Ray(new Point(0, 0, -1), new Vector(0, 1, 2));
        planeResult = plane.findIntersections(ray);
        triangleResult = triangle.findIntersections(ray);

        assertNotNull(planeResult,"should be not null");
        assertNull(triangleResult, "not suppose to be intersection point");

        //TC11: Intersection point on a vertex
        ray = new Ray(new Point(0, 0, -1), new Vector(1, 0, 1));
        planeResult = plane.findIntersections(ray);
        triangleResult = triangle.findIntersections(ray);

        assertNotNull(planeResult,"should be not null");
        assertNull(triangleResult, "not suppose to be intersection point");

        //TC12: Intersection on the continuation of a rib
        ray = new Ray(new Point(0, 0, -1), new Vector(0, 2, 1));
        planeResult = plane.findIntersections(ray);
        triangleResult = triangle.findIntersections(ray);

        assertNotNull(planeResult,"should be not null");
        assertNull(triangleResult, "not suppose to be intersection point");
    }
}