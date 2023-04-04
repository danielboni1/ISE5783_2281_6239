package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import primitives.Ray;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static primitives.Util.isZero;

class TubeTest {

    @Test
    void getNormal() {
        Ray ray = new Ray(new Point(0,0,0),new Vector(0,0,1));
        Tube tube = new Tube(1,ray);
        Vector normal = tube.getNormal(new Point(0,1,1));
        assertEquals(new Vector(0,1,0),normal,"normal is wrong");
    }
}