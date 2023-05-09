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

    //helper function
    private void assertCountIntersectionsEquals(Camera camera, Intersectable geo, int expected) {
        //preparing the list of the points and the actual result
        int result = 0;
        List<Point> allIntersectionPoints = null;

        //setting the view plane:
        camera.setVPSize(3, 3);
        camera.setVPDistance(1);
        int nX = 3;
        int nY = 3;

        for (int i = 0; i < nY; i++) {
            for (int j = 0; j < nX; j++) {
                var intersections = geo.findIntersections(camera.constructRay(nX, nY, j, i));
                if (intersections != null) {
                    if (allIntersectionPoints == null) {
                        allIntersectionPoints = new LinkedList<>();
                    }
                    allIntersectionPoints.addAll(intersections);
                    result += intersections.size();
                }
            }
        }
        assertEquals(expected, result, "The amount of intersection points wrong");
    }

    @Test
    public void CameraRayTriangleIntegrationTest() {
        //TC01: 1 POINT
        assertCountIntersectionsEquals(
                camera1,
                new Triangle(
                        new Point(1, 1, -1),
                        new Point(-1, 1, -1),
                        new Point(0, -0.5, -1)
                ),
                1);
        //TC02: 2 POINTS
        assertCountIntersectionsEquals(
                camera1,
                new Triangle(
                        new Point(1, 1, -1),
                        new Point(-1, 1, -1),
                        new Point(0, -10, -1)
                ),
                2);
    }

    @Test
    public void CameraRayPlaneIntegrationTest() {
        //TCO1: Plane against camera
        assertCountIntersectionsEquals(
                camera1,
                new Plane(
                        new Point(0, 0, -5),
                        new Vector(0, 0, 1)
                ),
                9
        );

        //TCO2: Plane with small angle
        assertCountIntersectionsEquals(camera1,
                new Plane(
                        new Point(0, 0, -5),
                        new Vector(0, 1, 2)
                ),
                9
        );

        //TCO3: Plane parallel to lower rays
        assertCountIntersectionsEquals(camera1,
                new Plane(
                        new Point(0, 0, -10),
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
                new Sphere(2.5,
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
