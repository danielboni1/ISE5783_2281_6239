package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
/**
 * Testing Cylinder
 *
 */
class CylinderTest {
    /**
     * Test method for {@link geometries.Cylinder#getNormal(primitives.Point)}
     */

    @Test
    void getNormal() {
        // ============ Equivalence Partitions Tests ==============
        Ray ray = new Ray(new Point(0,0,0),new Vector(0,0,1));
        Cylinder cylinder = new Cylinder(1,ray,3);
        // TC01: from the side
        Vector normal = cylinder.getNormal(new Point(0,1,1));
        assertEquals(new Vector(0,1,0),normal,"normal is wrong");
        // TC02: on the upper base
        normal = cylinder.getNormal(new Point(0,0.5,3));
        assertEquals(new Vector(0,0,1), normal, "normal is wrong");
        // TC03: on the lower base
        normal = cylinder.getNormal(new Point(0,0.5,0));
        assertEquals(new Vector(0,0,-1), normal, "normal is wrong");
        // =============== Boundary Values Tests ==================
        // TC10: on the center point of the upper base
        normal = cylinder.getNormal(new Point(0,0,3));
        assertEquals(new Vector(0,0,1), normal, "normal is wrong");
        // TC11: on the center point of the lower base
        normal = cylinder.getNormal(new Point(0,0,0));
        assertEquals(new Vector(0,0,-1), normal, "normal is wrong");
    }

    /***
     * Test method for {@link geometries.Cylinder#findIntersections(Ray)}
     */
    @Test
    public void testFindIntersections() {
        Cylinder cylinder = new Cylinder(1, new Ray(new Point(2, 0, 0), new Vector(0, 0, 1)), 10);
        Ray ray;
        Point p1;
        Point p2;
        // ============ Equivalence Partitions Tests ==============
        // case 01: The ray start under the cylinder and going through 2 bases (2
        // points)
        ray = new Ray(new Point(1.5, 0, -1), new Vector(0.1, 0, 1));
        List<Point> result = cylinder.findIntersections(ray);
        assertEquals( 2, result.size(),"Wrong number of points");
        if (result.get(0).getZ() > result.get(1).getZ())
            result = List.of(result.get(1), result.get(0));
        p1= new Point(1.6, 0, 0);
        p2= new Point(2.6, 0, 10);
        assertEquals(List.of(p1,p2), result,"Worng points");
        // case 02: The ray start before the cylinder and intersect (2 points)
        ray = new Ray(new Point(0, 0, 2), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals( 2, result.size(),"Wrong number of points");
        if (result.get(0).getZ()> result.get(1).getZ())
            result = List.of(result.get(1), result.get(0));
        p1=new Point(1, 0, 3);
        p2= new Point(3, 0,5);
        assertEquals( List.of(p1,p2), result,"Worng points");
        // case 03: The ray start before the cylinder and going through the top (2
        // points)
        ray = new Ray(new Point(0, 0, 8), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals( 2, result.size(),"Wrong number of points");
        if (result.get(0).getZ() > result.get(1).getZ())
            result = List.of(result.get(1), result.get(0));
        p1= new Point(1 ,0, 9);
        p2= new Point(2, 0, 10);
        assertEquals( List.of(p1, p2), result,"Worng points");
        // case 04: The ray start after the cylinder (0 points)
        ray = new Ray(new Point(4, 0, 5), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull( result,"should be null");
        // case 05: The ray start above the cylinder (0 points)
        ray = new Ray(new Point(0, 0, 11), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull( result,"should be null");
        // case 06: Ray's starts within cylinder and going through the top (1 point)
        ray = new Ray(new Point(1.5, 0, 3), new Vector(-0.05, 0, 1));
        result = cylinder.findIntersections(ray);
        p1=new Point(1.15, 0, 10);
        assertEquals( List.of(p1), result,"Worng point");
        // case 07: Ray's starts within cylinder and going through the buttom(1 point)
        ray = new Ray(new Point(1.5, 0, 3), new Vector(-0.1, 0, -1));
        result = cylinder.findIntersections(ray);
        p1= new Point(1.2, 0, 0);
        assertEquals( List.of(p1), result,"Worng point");
        // =============== Boundary Values Tests ==================
        // **** Group: Ray's line is parallel to the axis
        // case 11: Ray's starts under cylinder and going through the bases(2 point)
        ray = new Ray(new Point(1.5, 0, -1), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        assertEquals( 2, result.size(),"Wrong number of points");
        if (result.get(0).getZ() > result.get(1).getZ())
            result = List.of(result.get(1), result.get(0));
        p1= new Point(1.5, 0, 0);
        p2=  new Point(1.5, 0, 10);
        assertEquals(List.of(p1,p2), result,"Worng points");
        // case 12: Ray's starts within cylinder and going through the top (1 point)
        ray = new Ray(new Point(1.5, 0, 3), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        p1 =new  Point(1.5,0,10);
        assertEquals(List.of(p1), result,"Worng point");
        // case 13: Ray's starts within cylinder and going through the buttom (1 point)
        ray = new Ray(new Point(1.5, 0, 3), new Vector(0, 0, -1));
        result = cylinder.findIntersections(ray);
        p1 = new Point(1.5,0,0);
        assertEquals(List.of(p1), result,"Worng point");
        // case 14: Ray's starts at cylinder buttom and going through the top (1 point)
        ray = new Ray(new Point(1.5, 0, 0), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        p1 =new Point(1.5,0,10);
        assertEquals(List.of(p1), result,"Worng point");
        // case 15: Ray's starts at cylinder buttom and going outside (0 points)
        ray = new Ray(new Point(1.5, 0, 0), new Vector(0, 0, -1));
        result = cylinder.findIntersections(ray);
        assertNull(result,"should be null");
        // case 16: Ray's starts at cylinder center and going through the top (1 point)
        ray = new Ray(new Point(2, 0, 0), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        p1 = new Point(2,0,10);
        assertEquals(List.of(p1), result,"Worng point");
        // case 17: Ray's starts at cylinder center and going outside (0 points)
        ray = new Ray(new Point(2, 0, 0), new Vector(0, 0, -1));
        result = cylinder.findIntersections(ray);
        assertNull(result,"should be null");
        // case 18: Ray's starts at cylinder ditection and going through the top (1
        // point)
        ray = new Ray(new Point(2, 0, 5), new Vector(0, 0, 1));
        result = cylinder.findIntersections(ray);
        p1=  new Point(2,0,10);
        assertEquals( List.of(p1), result,"Worng point");
        // **** Group: Ray is orthogonal to the axis
        // case 21: Ray's starts before cylinder and intersect (2 point)
        ray = new Ray(new Point(0, 0, 5), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertEquals( 2, result.size(),"Wrong number of points");
        if (result.get(0).getX() > result.get(1).getX())
            result = List.of(result.get(1), result.get(0));
        p1= new Point(1,0,5);
        p2=new Point(3,0,5);
        assertEquals(List.of(p1,p2), result,"Worng points");
        // case 22: Ray's starts before and above cylinder (0 points)
        ray = new Ray(new Point(0, 0, 11), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull(result,"should be null");
        // case 23: Ray's starts before and going through the top (0 points)
        ray = new Ray(new Point(0, 0, 10), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull(result,"should be null");
        // case 24: Ray's starts at the cylinder center (0 points)
        ray = new Ray(new Point(2, 0, 0), new Vector(1, 0, 0));
        result = cylinder.findIntersections(ray);
        assertNull(result,"should be null");
        // case 25: Ray's tangent to cylinder top (0 points)
        ray = new Ray(new Point(1, 4, 10), new Vector(0, -1, 0));
        result = cylinder.findIntersections(ray);
        assertNull(result,"should be null");
        // **** Group: Ray's line is neither parallel nor orthogonal to the axis
        // case 31: Ray's starts within and exits exactly between the base and cylinder
        // (0 points)
        ray = new Ray(new Point(2.5, 0, 9), new Vector(0.5, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result,"should be null");
        // case 32: Ray's starts at the cylinder and exits exactly between the base and
        // cylinder (0 points)
        ray = new Ray(new Point(1, 0, 8), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result,"should be null");
        // case 33: Ray's through exactly between the bottom and cylinder exits exactly
        // between the top and cylinder (0 points)
        ray = new Ray(new Point(0.8, 0, -1), new Vector(0.2, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull( result,"should be null");
        // case 34: Ray's start exactly between the bottom and cylinder exits exactly
        // between the top and cylinder (0 points)
        ray = new Ray(new Point(1, 0, 0), new Vector(0.2, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull(result,"should be null");
        // case 35: Ray's starts at cylinder and going through the top (1
        // point)
        ray = new Ray(new Point(1, 0, 8), new Vector(0.1, 0, 1));
        result = cylinder.findIntersections(ray);
        p1= new Point(1.2, 0, 10);
        assertEquals(List.of(p1), result,"Worng point");
        // case 36: Ray's tangent the cylinder exactly between the top and
        // cylinder (0 points)
        ray = new Ray(new Point(0, 0, 9), new Vector(1, 0, 1));
        result = cylinder.findIntersections(ray);
        assertNull( result,"should be null");
    }
}