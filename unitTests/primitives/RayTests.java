package primitives;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Unit tests for primitives.Ray Class.
 *
 */
class RayTests {

    /**
     * Test method for {@link primitives.Ray}
     */
    @Test
    void testGetPoint() {
        Ray ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));
        // ============ Equivalence Partitions Tests ==============
        //TC01: Positive parameter
        assertEquals(new Point(2, 0, 1), ray.getPoint(2),"wrong point");

        //TC02: Negative parameter
        assertEquals(new Point(-3, 0, 1), ray.getPoint(-3),"wrong point");

        // =============== Boundary Values Tests ==================
        //TC03: Parameter is 0
        assertEquals(new Point(0, 0, 1), ray.getPoint(0),"wrong point");
    }

    @Test
    void testFindClosestPoint() {
        Ray ray = new Ray(new Point(0, 0, 1), new Vector(1, 0, 0));

        // ============ Equivalence Partitions Tests ==============
        //TC01: closest point is in the middle of the list
        assertEquals(ray.findClosestPoint(
                List.of(
                        new Point(0, 2, 10),
                        new Point(3, 1, 4),
                        new Point(0, 0, 2),
                        new Point(10, 0, 0))),
                new Point(0, 0, 2),
                "Test 1 failed");

        // =============== Boundary Values Tests ==================
        //TC02: empty list
        assertNull(ray.findClosestPoint(List.of()), "Test 2 failed");

        //TC03: 1 Point in the list
        assertEquals(
                new Point(1, 2, 3),
                ray.findClosestPoint(List.of(new Point(1, 2, 3))),
                "Test 3 failed");

        //TC04: the closest point is the first point of the list
        assertEquals(
                new Point(0, 0, 2),
                ray.findClosestPoint(List.of(
                        new Point(0, 0, 2),
                        new Point(2, 2, 2),
                        new Point(3, 3, 3),
                        new Point(-10, -2, 0))),
                "Test 4 failed");

        //TC05: the closest point is the last point of the list
        assertEquals(new Point(0, 0, 2),
                ray.findClosestPoint(List.of(
                        new Point(-12, 4, 7),
                        new Point(-7, 10, 0),
                        new Point(1, 2, 3),
                        new Point(0, 0, 2))),
                "Test 5 failed");
    }
}