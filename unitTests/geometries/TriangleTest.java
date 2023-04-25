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
        Point[] pts =
                {new Point(0, 0, 1), new Point(1, 0, 0), new Point(0, 1, 0)};
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
    void testfindIntsersections()
    {
        // ============ Equivalence Partitions Tests ==============
        //TC01 החיתוך בתוך המשולש
        Triangle triangle = new Triangle(new Point(1,0,0),new Point(0,-1,0),new Point(0,1,0));
        Ray ray = new Ray(new Point(0,0,-1),new Vector(1,0,2));
        Point P1 = new Point(0.5,0,0);
        List<Point> result= triangle.findIntsersections(ray);
        assertEquals(1,result.size(),"number of elements is not equal");
        assertEquals(P1,result.get(0),"the point is wrong");
        //TC02 החיתוך מחוץ למשולש מול הצלעות
        ray = new Ray(new Point(0,0,-1),new Vector(0.5,-5,1));
        result= triangle.findIntsersections(ray);
        assertNull(result,"not suppose to be intsersection point");
        //TC03 החיתוך מחוץ למשולש מול הקודקודים
        ray = new Ray(new Point(0,0,-1),new Vector(2,0,1));
        result= triangle.findIntsersections(ray);
        assertNull(result,"not suppose to be intsersection point");

        // =============== Boundary Values Tests ==================
        //TC10 חיתוך על צלע
        ray = new Ray(new Point(0,0,-1),new Vector(0,1,2));
        result= triangle.findIntsersections(ray);
        assertNull(result,"not suppose to be intsersection point");
        //TC11 חיתוך על קודקוד
        ray = new Ray(new Point(0,0,-1),new Vector(1,0,1));
        result= triangle.findIntsersections(ray);
        assertNull(result,"not suppose to be intsersection point");
        //TC12 חיתוך על המשך צלע
        ray = new Ray(new Point(0,0,-1),new Vector(0,2,1));
        result= triangle.findIntsersections(ray);
        assertNull(result,"not suppose to be intsersection point");
    }
}