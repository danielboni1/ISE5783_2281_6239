package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.Iterator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static primitives.Util.isZero;

/**
 * Testing geometries.Plane
 *
 * @author Uriel Mangisto
 * @author Daniel Boni
 */
class PlaneTests {

    @Test
    public void testConstructor()
    {
        // =============== Boundary Values Tests ==================
        //TC01: The first 2 point are the same
        Point p1 = new Point(1,0,0);
        Point p2 = new Point(1,0,0);
        Point p3 = new Point(0,1,0);
        assertThrows(IllegalArgumentException.class
                ,() -> new Plane(p1,p2,p3)
                ,"The first two points are the same");
        //TCO2: The points are on the same line
        Point p4 = new Point(1,0,0);
        Point p5 = new Point(2,0,0);
        Point p6 = new Point(3,0,0);

        assertThrows(IllegalArgumentException.class
                ,() -> new Plane(p4,p5,p6)
                ,"The points are on the same line");

    }
    /**
     * Test method for {@link geometries.Plane#getNormal(primitives.Point)}.
     */
    @Test
    void testGetNormal() {
        // ============ Equivalence Partitions Tests ==============
        Point p1 = new Point(1,0,1);
        Point p2 = new Point(3,1,7);
        Point p3 = new Point(2,5,3);

        Plane plane1 = new Plane(p1,p2,p3);
        Vector result = plane1.getNormal(p3);

        //TC01: ensure |result| = 1
        assertEquals(1, result.length(), 0.00000001, "Plane's normal is not a unit vector");

        //TC02: ensure that the vector is correct
        assertEquals(new Vector(-28,2,9).normalize(), result,"Plane's normal is wrong");
    }
    @Test
    void testfindIntsersections()
    {
        Point p1 = new Point(0,1,0);
        Point p2 = new Point(1,0,0);
        Point p3 = new Point(0,0,0);
        Plane plane = new Plane(p1,p2,p3);

        // ============ Equivalence Partitions Tests ==============
        //TC01: A ray that starts outside the plane, is not parallel to the plane,
        // makes a non-right angle with the plane, and cuts the plane(1 point)
        Point p0 = new Point(0,0,-1);
        Vector dir = new Vector(0,1,1);
        Ray ray = new Ray(p0,dir);
        List<Point> result = plane.findIntsersections(ray);
        int len = result.size();
        assertEquals(1,len,"number of elements is not equal");
        assertEquals(result.get(0),new Point(0,1,0), "The point is wrong");

        //TC02: A ray that starts outside the plane, is not parallel to the plane,
        // makes a non-right angle with the plane, and does not intersect the plane (0 point)
        dir = new Vector(0,-1,-1);
        ray = new Ray(p0,dir);
        result = plane.findIntsersections(ray);
        assertNull(result,"There is a point that not suppose to be");


        // =============== Boundary Values Tests ==================
        //TC10: A ray that is parallel to a plane outside it (0 point)
        dir = new Vector(0,1,0);
        ray = new Ray(p0,dir);
        result = plane.findIntsersections(ray);
        assertNull(result,"There is a point that not suppose to be");

        //TC11: A ray that is parallel to the plane and within it (0 point)
        p0 = new Point(1,0,0);
        ray = new Ray(p0,dir);
        result = plane.findIntsersections(ray);
        assertNull(result,"There is a point that not suppose to be");

        //TC12: A ray perpendicular to the plane and starting behind it
        p0 = new Point(1,0,-1);
        dir = new Vector(0,0,1);
        ray = new Ray(p0,dir);
        result = plane.findIntsersections(ray);
        len = result.size();
        assertEquals(1,len,"number of elements is not equal");
        assertEquals(result.get(0),new Point(1,0,0), "The point is wrong");

        //TC13: A ray perpendicular to the plane and starting on it
        p0 = new Point(1,0,0);
        ray = new Ray(p0,dir);
        result = plane.findIntsersections(ray);
        assertNull(result,"There is a point that not suppose to be");

        //TC14: A ray perpendicular to the plane and starting in front of it
        p0 = new Point(0,0,1);
        ray = new Ray(p0,dir);
        result = plane.findIntsersections(ray);
        assertNull(result,"There is a point that not suppose to be");


        //TC15: A ray that is neither parallel nor perpendicular
        // to the plane but starts inside the plane
        p0 = new Point(1,0,0);
        dir = new Vector(0,1,1);
        ray = new Ray(p0,dir);
        result = plane.findIntsersections(ray);
        assertNull(result,"There is a point that not suppose to be");

        //TC16: Ray is neither orthogonal nor parallel to the plane and begins in
        //the same point which appears as reference point in the plane
        p0 = new Point(0,1,0);
        ray = new Ray(p0,dir);
        result = plane.findIntsersections(ray);
        assertNull(result,"There is a point that not suppose to be");
    }

}