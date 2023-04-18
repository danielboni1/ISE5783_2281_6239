package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

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
}