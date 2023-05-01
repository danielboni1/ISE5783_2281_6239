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
        Point[] pts =
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0), new Point(-1, 1, 1)};
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
    void testfindIntsersections()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01: The point of intersection inside the polygon
        Polygon polygon = new Polygon(new Point(1,0,0),new Point(0,-1,0),new Point(0,1,0));
        Ray ray = new Ray(new Point(0,0,-1),new Vector(1,0,2));
        Point P1 = new Point(0.5,0,0);
        List<Point> result= polygon.findIntersections(ray);
        assertEquals(1,result.size(),"number of elements is not equal");
        assertEquals(P1,result.get(0),"the point is wrong");

        //TC02: The intersection point is outside the triangle in front of the sides
        ray = new Ray(new Point(0,0,-1),new Vector(0.5,-5,1));
        result= polygon.findIntersections(ray);
        assertNull(result,"not suppose to be intsersection point");

        //TC03: The point of intersection is outside the triangle opposite the vertices
        ray = new Ray(new Point(0,0,-1),new Vector(2,0,1));
        result= polygon.findIntersections(ray);
        assertNull(result,"not suppose to be intsersection point");

        // =============== Boundary Values Tests ==================
        //TC10: Intersection point on a side
        ray = new Ray(new Point(0,0,-1),new Vector(0,1,2));
        result= polygon.findIntersections(ray);
        assertNull(result,"not suppose to be intsersection point");

        //TC11: Intersection point on a vertex
        ray = new Ray(new Point(0,0,-1),new Vector(1,0,1));
        result= polygon.findIntersections(ray);
        assertNull(result,"not suppose to be intsersection point");

        //TC12: Intersection on the continuation of a rib
        ray = new Ray(new Point(0,0,-1),new Vector(0,2,1));
        result= polygon.findIntersections(ray);
        assertNull(result,"not suppose to be intsersection point");
    }
}
