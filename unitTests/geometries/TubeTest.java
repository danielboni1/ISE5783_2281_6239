package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static primitives.Util.isZero;
/**
 * Testing Tube
 *
 */
class TubeTest {
    /**
     * Test method for {@link geometries.Tube#getNormal(primitives.Point)}
     */
    @Test
    void getNormal() {
        Ray ray = new Ray(new Point(0,0,0),new Vector(0,0,1));
        Tube tube = new Tube(1,ray);
        // ============ Equivalence Partitions Tests ==============
        Vector normal = tube.getNormal(new Point(0,1,1));
        // TC01: from the side
        assertEquals(new Vector(0,1,0),normal,"normal is wrong");
        // =============== Boundary Values Tests ==================
        Vector bnormal = tube.getNormal(new Point(0,1,0));
        // TC10: on the base
        assertEquals(new Vector(0,1,0),bnormal,"normal is wrong");
    }
    @Test
    List<Point> testfindIntsersections()
    {
        return null;
    }
}