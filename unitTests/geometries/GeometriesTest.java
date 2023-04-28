package geometries;

import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GeometriesTest {

    @Test
    void findIntsersections() {

        //============ Equivalence Partitions Tests ==============
        //TC01
        Geometries geometries1 = new Geometries();
        Sphere sphere1 = new Sphere(0.5,new Point(0,1,1));
        Sphere sphere2 = new Sphere(0.5,new Point(0,3,1));
        Sphere sphere3 = new Sphere(0.5,new Point(6,0,0));
        geometries1.add(sphere1);
        geometries1.add(sphere2);
        geometries1.add(sphere3);
        Ray ray = new Ray(new Point(0,0,1),new Vector(0,1,0));
        List<Point> result = geometries1.findIntsersections(ray);
        assertEquals(4,result.size(),"There are suppose to be 4 points");

        // =============== Boundary Values Tests ==================
        //TC10 Empty geometries collection
        Geometries geometries2 = new Geometries();
        ray = new Ray(new Point(0,1,0),new Vector(0,1,0));
        result = geometries2.findIntsersections(ray);
        assertNull(result,"The result is not null");

        //TC11 There is not a shape that intersect
        Sphere sphere4 = new Sphere(0.5,new Point(0,1,1));
        Sphere sphere5 = new Sphere(0.5,new Point(0,3,1));
        Sphere sphere6 = new Sphere(0.5,new Point(0,5,1));
        geometries2.add(sphere4);
        geometries2.add(sphere5);
        geometries2.add(sphere6);
        ray = new Ray(new Point(0,1,0),new Vector(1,0,0));
        result = geometries2.findIntsersections(ray);
        assertNull(result,"The result is not null");
        //TC12 Only one shape is intersect
        ray = new Ray(new Point(1,1,1),new Vector(-1,0,0));
        result = geometries2.findIntsersections(ray);
        assertEquals(2,result.size(),"There are suppose to be 2 points");
        //TC13 All the shapes are intersect
        ray = new Ray(new Point(0,0,1),new Vector(0,1,0));
        result = geometries2.findIntsersections(ray);
        assertEquals(6,result.size(),"There are suppose to be 6 points");

    }
}