package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing Polygons
 *
 * @author Dan
 */
public class PolygonTests {

    /**
     * Test method for {@link geometries.Polygon#Polygon(primitives.Point...)}.
     */
    @Test
    public void testConstructor() {
        // ============ Equivalence Partitions Tests ==============

        // TC01: Correct concave quadrangular with vertices in correct order
        try {
            new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1));
        } catch (IllegalArgumentException e) {
            fail("Failed constructing a correct polygon");
        }

        // TC02: Wrong vertices order
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(0, 1, 0), new Point(1, 0, 0), new Point(-1, 1, 1)), //
                "Constructed a polygon with wrong order of vertices");

        // TC03: Not in the same plane
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 2, 2)), //
                "Constructed a polygon with vertices that are not in the same plane");

        // TC04: Concave quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0.5, 0.25, 0.5)), //
                "Constructed a concave polygon");

        // =============== Boundary Values Tests ==================

        // TC10: Vertex on a side of a quadrangular
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0),
                        new Point(0, 0.5, 0.5)),
                "Constructed a polygon with vertix on a side");

        // TC11: Last point = first point
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 0, 1)),
                "Constructed a polygon with vertice on a side");

        // TC12: Co-located points
        assertThrows(IllegalArgumentException.class, //
                () -> new Polygon(new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(0, 1, 0)),
                "Constructed a polygon with vertice on a side");

    }

    /**
     * Test method for {@link geometries.Polygon#getNormal(primitives.Point)}.
     */
    @Test
    public void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        // TC01: There is a simple single test here - using a quad
        Point[] pts = {
                new Point(0, 0, 1),
                new Point(1, 0, 0),
                new Point(0, 1, 0),
                new Point(-1, 1, 1)
        };
        Polygon pol = new Polygon(pts);
        // ensure there are no exceptions
        assertDoesNotThrow(() -> pol.getNormal(new Point(0, 0, 1)), "");
        // generate the test result
        Vector result = pol.getNormal(new Point(0, 0, 1));
        // ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Polygon's normal is not a unit vector");
        // ensure the result is orthogonal to all the edges
        for (int i = 0; i < 3; ++i)
            assertTrue(isZero(result.dotProduct(pts[i].subtract(pts[i == 0 ? 3 : i - 1]))),
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

        Polygon polygon = new Polygon(pts);
        Plane plane = new Plane(pts[0], pts[1], pts[2]);

        Ray ray = new Ray(new Point(0, 0, -1), new Vector(1, 0, 2));
        Point expected = new Point(0.5, 0, 0);

        List<Point> polygonResult = polygon.findIntersections(ray);
        assertEquals(1, polygonResult.size(), "number of elements is not equal");
        assertEquals(expected, polygonResult.get(0), "the point is wrong");

        //TC02: The intersection point is outside the triangle in front of the sides
        ray = new Ray(new Point(0, 0, -1), new Vector(0.5, -5, 1));
        List<Point> planeResult = plane.findIntersections(ray);
        polygonResult = polygon.findIntersections(ray);

        assertNotNull(planeResult, "should be not null");
        assertNull(polygonResult, "not suppose to be intersection point");

        //TC03: The point of intersection is outside the triangle opposite the vertices
        ray = new Ray(new Point(0, 0, -1), new Vector(2, 0, 1));
        planeResult = plane.findIntersections(ray);
        polygonResult = polygon.findIntersections(ray);

        assertNotNull(planeResult, "should be not null");
        assertNull(polygonResult, "not suppose to be intersection point");

        // =============== Boundary Values Tests ==================
        //TC10: Intersection point on a side
        ray = new Ray(new Point(0, 0, -1), new Vector(0, 1, 2));
        planeResult = plane.findIntersections(ray);
        polygonResult = polygon.findIntersections(ray);

        assertNotNull(planeResult, "should be not null");
        assertNull(polygonResult, "not suppose to be intersection point");

        //TC11: Intersection point on a vertex
        ray = new Ray(new Point(0, 0, -1), new Vector(1, 0, 1));
        planeResult = plane.findIntersections(ray);
        polygonResult = polygon.findIntersections(ray);

        assertNotNull(planeResult, "should be not null");
        assertNull(polygonResult, "not suppose to be intersection point");

        //TC12: Intersection on the continuation of a rib
        ray = new Ray(new Point(0, 0, -1), new Vector(0, 2, 1));
        planeResult = plane.findIntersections(ray);
        polygonResult = polygon.findIntersections(ray);

        assertNotNull(planeResult, "should be not null");
        assertNull(polygonResult, "not suppose to be intersection point");
    }
}
