package renderer;

import geometries.Intersectable;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import org.junit.jupiter.api.Test;
import primitives.Point;
import primitives.Vector;
import renderer.Camera;

import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Integration tests for Camera class with Ray-Sphere
 * and Ray-Triangle intersections
 */
public class CameraIntegrationTest {
    //The general cameras for the tests
    Camera camera1 = new Camera(
            new Point(0, 0, 0),
            new Vector(0, 0, -1),
            new Vector(0, -1, 0)
    );
    Camera camera2 = new Camera(
            new Point(0, 0, 0.5),
            new Vector(0, 0, -1),
            new Vector(0, -1, 0)
    );

    /**
     * Helper method that asserts the amount of intersections of a given geometry and camera
     *
     * @param camera   The camera used in the test
     * @param geo      The geometry to check intersections with
     * @param expected The expected number of intersections
     */
    private void assertCountIntersectionsEquals(Camera camera, Intersectable geo, int expected) {
        //preparing the list of the points and the actual result
        int result = 0;

        //setting the view plane:
        camera.setVPSize(3, 3);
        camera.setVPDistance(1);
        int nX = 3;
        int nY = 3;

        //todo
        for (int row = 0; row < nY; row++) {
            for (int column = 0; column < nX; column++) {
                var intersections = geo.findIntersections(camera.constructRay(nX, nY, column, row));
                if (intersections != null) {
                    result += intersections.size();
                }
            }
        }
        assertEquals(expected, result, "The amount of intersection points wrong");
    }

    /**
     * Integration test for Camera-Ray-Triangle intersection
     */
    @Test
    public void CameraRayTriangleIntegrationTest() {
        //TC01: 1 POINT- a small one in front of the center pixel
        assertCountIntersectionsEquals(
                camera1,
                new Triangle(
                        new Point(0, 1, -2),
                        new Point(1, -1, -2),
                        new Point(-1, -1, -2)
                ),
                1);
        //TC02: 2 POINTS- a narrow and long one in front of 3 of the pixels
        assertCountIntersectionsEquals(
                camera1,
                new Triangle(
                        new Point(0, 20, -2),
                        new Point(1, -1, -2),
                        new Point(-1, -1, -2)
                ),
                2);
    }

    /**
     * Integration test for Camera-Ray-Plane intersection
     */
    @Test
    public void CameraRayPlaneIntegrationTest() {
        //TCO1: Plane against camera
        assertCountIntersectionsEquals(
                camera1,
                new Plane(
                        new Point(0, 0, -10),
                        new Vector(0, 0, 1)
                ),
                9
        );

        //TCO2: Plane with small angle
        assertCountIntersectionsEquals(camera1,
                new Plane(
                        new Point(0, 0, -10),
                        new Vector(0, 5, 10)
                ),
                9
        );

        //TCO3: Plane parallel to lower rays
        assertCountIntersectionsEquals(camera1,
                new Plane(
                        new Point(0, 0, -1),
                        new Vector(0, 1, 1)
                ),
                6
        );

        //TCO4: Beyond Plane
        assertCountIntersectionsEquals(camera1,
                new Plane(
                        new Point(0, 0, -10),
                        new Vector(0, -1, 0)
                ),
                0
        );

    }

    /**
     * Integration test for Camera-Ray-Sphere intersection
     */
    @Test
    public void CameraRaySphereIntegrationTest() {
        // TC01: Small Sphere
        assertCountIntersectionsEquals(camera1,
                new Sphere(
                        1,
                        new Point(0, 0, -3)
                ),
                2
        );

        // TC02: Big Sphere
        assertCountIntersectionsEquals(camera2,
                new Sphere(
                        2.5,
                        new Point(0, 0, -2.5)
                ),
                18
        );

        // TC03: Medium Sphere
        assertCountIntersectionsEquals(camera2,
                new Sphere(
                        2,
                        new Point(0, 0, -2)
                ),
                10
        );

        // TC04: Inside Sphere
        assertCountIntersectionsEquals(camera2,
                new Sphere(
                        4,
                        new Point(0, 0, -1)
                ),
                9
        );

        // TC05: Beyond Sphere
        assertCountIntersectionsEquals(camera1,
                new Sphere(
                        0.5,
                        new Point(0, 0, 1)
                ),
                0
        );
    }

}
