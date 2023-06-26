package renderer;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import scene.Scene;

import static java.awt.Color.*;

/**
 * Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 *
 * @author dzilb
 */
public class ReflectionRefractionTests {
    private Scene scene = new Scene.SceneBuilder("Test scene").build();

    /**
     * Produce a picture of a sphere lighted by a spotlight
     */
    @Test
    public void twoSpheres() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(150, 150).setVPDistance(1000);

        scene.geometries.add( //
                new Sphere(50d, new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                new Sphere(25d, new Point(0, 0, -50)).setEmission(new Color(RED)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
        scene.lights.add( //
                new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                        .setKl(0.0004).setKq(0.0000006));

        camera.setImageWriter(new ImageWriter("refractionTwoSpheres-soft", 500, 500)) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of a sphere lighted by a spot light
     */
    @Test
    public void twoSpheresOnMirrors() {
        Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(2500, 2500).setVPDistance(10000); //

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add( //
                new Sphere(400d, new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(670, 670, 3000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(1)),
                new Triangle(new Point(1500, -1500, -1500), new Point(-1500, 1500, -1500),
                        new Point(-1500, -1500, -2000)) //
                        .setEmission(new Color(20, 20, 20)) //
                        .setMaterial(new Material().setKr(new Double3(0.5, 0, 0.4))));

        scene.lights.add(new SpotLight(new Color(1020, 400, 400), new Point(-750, -750, -150), new Vector(-1, -1, -4)) //
                .setKl(0.00001).setKq(0.000005));

        ImageWriter imageWriter = new ImageWriter("reflectionTwoSpheresMirrored", 500, 500);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * Produce a picture of two triangles lighted by a spot light with a
     * partially
     * transparent Sphere producing partial shadow
     */
    @Test
    public void trianglesTransparentSphere() {
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);

        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                        new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)), //
                new Sphere(30d, new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

        scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        ImageWriter imageWriter = new ImageWriter("refractionShadow-soft shadow version", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();
    }

    /**
     * bonus 7 version 1 (not final)
     */
    @Test
    public void allefectTestBounos() {
        Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVPSize(200, 200)
                .moveTo(10)
                .turnUp(8.1)
                .moveUp(-160)
                .setVPDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).background.add(new Color(white));

        scene.geometries.add( //
                new Triangle(new Point(-55, 10, 115), new Point(-25, 49, 115), new Point(-85, 49, 115))
                        .setEmission(new Color(76, 3, 19)), //
                new Sphere(50, new Point(70, -70, 0))
                        .setEmission(new Color(200, 200, 0)),
                new Sphere(30, new Point(70, -70, -10))
                        .setEmission(new Color(java.awt.Color.orange)),
                new Sphere(20, new Point(70, -70, -30))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(java.awt.Color.RED)),
                new Sphere(20, new Point(-70, -60, -30))
                        .setEmission(new Color(java.awt.Color.darkGray))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(20, new Point(-60, -50, -30))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(java.awt.Color.darkGray)),
                new Sphere(20, new Point(-40, -65, -30))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(120).setKt(0.3)),

                new Polygon(new Point(25, 110, 115), new Point(35, 110, 115), new Point(35, 50, 115), new Point(25, 50, 115))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(17, 11, 5)),

                new Triangle(new Point(30, -20, 115), new Point(15, 50, 115), new Point(45, 50, 115))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(0, 150, 0)),//
                new Polygon(new Point(-80, 110, 115), new Point(-30, 110, 115), new Point(-30, 50, 115), new Point(-80, 50, 115))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(240, 250, 240))
                , new Triangle(new Point(-30, -10, 115), new Point(-30, -14, 113), new Point(-25, -12, 114))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(54, 40, 30))
                , new Triangle(new Point(-27, -10, 115), new Point(-27, -14, 113), new Point(-22, -12, 114))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission((new Color(54, 40, 30)))
                , new Triangle(new Point(-20, -15, 101), new Point(-20, -19, 99), new Point(-15, -17, 100))
                        .setEmission(new Color(54, 40, 30))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                , new Triangle(new Point(-17, -15, 101), new Point(-17, -19, 99), new Point(-12, -17, 100))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(54, 40, 30)),
                new Cylinder(10, new Ray(new Point(-25, -40, -30), new Vector(0, 1, 0)), 50).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(100).setKt(0.5)),
                new Cylinder(6, new Ray(new Point(-25, -40, -30), new Vector(0, 1, 0)), 30).setEmission(new Color(red)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))

        );


        scene.lights.add(new SpotLight(new Color(yellow), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(pink), new Point(0, 50, 0)) //
                .setKl(4E-5).setKq(2E-7));


        ImageWriter imageWriter = new ImageWriter("bonus", 600, 600);
        camera.setImageWriter(imageWriter) //
                .setRayTracer(new RayTracerBasic(scene)) //
                .renderImage() //
                .writeToImage();

    }

    /**
     * same image without camera transformation
     */
    @Test
    public void allefectTestBounosNotMoved() {
        Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0))
                .setVPSize(200, 200)
                .setVPDistance(1000);
        scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).background.add(new Color(white));

        scene.geometries.add( //
                new Triangle(new Point(-55, 10, 115), new Point(-25, 49, 115), new Point(-85, 49, 115))
                        .setEmission(new Color(76, 3, 19)), //
                new Sphere(50, new Point(70, -70, 0))
                        .setEmission(new Color(200, 200, 0)),
                new Sphere(30, new Point(70, -70, -10))
                        .setEmission(new Color(java.awt.Color.orange)),
                new Sphere(20, new Point(70, -70, -30))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(java.awt.Color.RED)),
                new Sphere(20, new Point(-70, -60, -30))
                        .setEmission(new Color(java.awt.Color.darkGray))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
                new Sphere(20, new Point(-60, -50, -30))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(java.awt.Color.darkGray)),
                new Sphere(20, new Point(-40, -65, -30))
                        .setEmission(new Color(BLUE))
                        .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(120).setKt(0.3)),

                new Polygon(new Point(25, 110, 115), new Point(35, 110, 115), new Point(35, 50, 115), new Point(25, 50, 115))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(17, 11, 5)),

                new Triangle(new Point(30, -20, 115), new Point(15, 50, 115), new Point(45, 50, 115))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(0, 150, 0)),//
                new Polygon(new Point(-80, 110, 115), new Point(-30, 110, 115), new Point(-30, 50, 115), new Point(-80, 50, 115))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(240, 250, 240))
                , new Triangle(new Point(-30, -10, 115), new Point(-30, -14, 113), new Point(-25, -12, 114))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(54, 40, 30))
                , new Triangle(new Point(-27, -10, 115), new Point(-27, -14, 113), new Point(-22, -12, 114))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission((new Color(54, 40, 30)))
                , new Triangle(new Point(-20, -15, 101), new Point(-20, -19, 99), new Point(-15, -17, 100))
                        .setEmission(new Color(54, 40, 30))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                , new Triangle(new Point(-17, -15, 101), new Point(-17, -19, 99), new Point(-12, -17, 100))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                        .setEmission(new Color(54, 40, 30)),
                new Cylinder(10, new Ray(new Point(-25, -40, -30), new Vector(0, 1, 0)), 50).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.3).setKs(0.2).setShininess(100).setKt(0.5)),
                new Cylinder(6, new Ray(new Point(-25, -40, -30), new Vector(0, 1, 0)), 30).setEmission(new Color(red)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100))

        );


        scene.lights.add(new SpotLight(new Color(yellow), new Point(60, 50, 0), new Vector(0, 0, -1)) //
                .setKl(4E-5).setKq(2E-7));
        scene.lights.add(new PointLight(new Color(pink), new Point(0, 50, 0)) //
                .setKl(4E-5).setKq(2E-7));


        ImageWriter imageWriter = new ImageWriter("bonusNotMoved", 600, 600);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();

    }

    /**
     * image for the bonus of stage 7:
     * <p>
     * a couple of geometries on a mirror and in front of another mirror
     * with camera transformation (the camera's going up and looking down).
     * <p>
     * shapes: 5 pillars, 1 pyramid, 2 spheres, 1 mirror floor and 2 mirror walls.
     */
    @Test
    public void realBonus() {
        Camera camera = new Camera(
                new Point(0, 0, 10000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setVPSize(2500, 2500)
                .setVPDistance(10000);

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add(
                //sphere in sphere
                new Sphere(400d, new Point(200, -300, -1000)).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(200, -300, -1000)).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                //floor
                new Plane(new Point(-400, -700, -1000), new Vector(0, -1, 0))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setKr(1)),
                //wall 1
                new Plane(new Point(-400, -700, -5000), new Vector(0, 0, -1))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setKr(1)),
                //wall 2
                new Plane(new Point(1200, -650, -500), new Vector(1, 0, 0))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setKr(1)),
                //the pyramid
                new Triangle(
                        new Point(-200, -700, -1200),
                        new Point(-600, -700, -1200),
                        new Point(-333.33, -400, -1000))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Triangle(
                        new Point(-200, -700, -600),
                        new Point(-600, -700, -1200),
                        new Point(-333.33, -400, -1000))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Triangle(
                        new Point(-400, -700, -1200),
                        new Point(-200, -700, -600),
                        new Point(-333.33, -400, -1000))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                //the pillars
                new Cylinder(
                        60,
                        new Ray(new Point(600, -650, -500), new Vector(0, 1, 0)),
                        1000
                ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Cylinder(
                        60,
                        new Ray(new Point(-800, -650, -500), new Vector(0, 1, 0)),
                        1000
                ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Cylinder(
                        60,
                        new Ray(new Point(-1000, -650, -500), new Vector(0, 1, 0)),
                        1000
                ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Cylinder(
                        60,
                        new Ray(new Point(800, -650, -500), new Vector(0, 1, 0)),
                        1000
                ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Cylinder(
                        60,
                        new Ray(new Point(1000, -650, -500), new Vector(0, 1, 0)),
                        1000
                ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))
        );

        scene.lights.add(
                new SpotLight(
                        new Color(1020, 400, 400),
                        new Point(-600, -650, -150),
                        new Vector(-1, -1, -4))
                        .setKl(0.00001)
                        .setKq(0.000005));
        scene.lights.add(
                new SpotLight(
                        new Color(1020, 400, 400),
                        new Point(5, -650, -150),
                        new Vector(-1, -1, -4))
                        .setKl(0.00001)
                        .setKq(0.000005));
        scene.lights.add(
                new SpotLight(
                        new Color(1020, 400, 400),
                        new Point(-400, -650, -150),
                        new Vector(-1, -1, -4))
                        .setKl(0.00001)
                        .setKq(0.000005));
        scene.lights.add(
                new DirectionalLight(new Color(white), new Vector(-1, 0, 0))
        );
        scene.lights.add(
                new DirectionalLight(new Color(white), new Vector(0, -1, 0))
        );

        ImageWriter imageWriter = new ImageWriter("Real Bonus Stage 7", 500, 500);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void realBonusMoved() {
        Camera camera = new Camera(
                new Point(0, 0, 10000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setVPSize(2500, 2500)
                .setVPDistance(10000)
                .moveRight(100)
                .moveUp(1000)
                .turnUp(-3)
                .pivot(10);

        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add(
                //sphere in sphere
                new Sphere(400d, new Point(200, -300, -1000)).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(200, -300, -1000)).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                //floor
                new Plane(new Point(-400, -700, -1000), new Vector(0, -1, 0))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setKr(1)),
                //wall 1
                new Plane(new Point(-400, -700, -5000), new Vector(0, 0, -1))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setKr(1)),
                //wall 2
                new Plane(new Point(1200, -650, -500), new Vector(1, 0, 0))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setKr(1)),
                //the pyramid
                new Triangle(
                        new Point(-200, -700, -1200),
                        new Point(-600, -700, -1200),
                        new Point(-333.33, -400, -1000))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Triangle(
                        new Point(-200, -700, -600),
                        new Point(-600, -700, -1200),
                        new Point(-333.33, -400, -1000))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Triangle(
                        new Point(-400, -700, -1200),
                        new Point(-200, -700, -600),
                        new Point(-333.33, -400, -1000))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material()
                                .setKd(0.25)
                                .setKs(0.25)
                                .setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                //the pillars
                new Cylinder(
                        60,
                        new Ray(new Point(600, -650, -500), new Vector(0, 1, 0)),
                        1000
                ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Cylinder(
                        60,
                        new Ray(new Point(-800, -650, -500), new Vector(0, 1, 0)),
                        1000
                ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Cylinder(
                        60,
                        new Ray(new Point(-1000, -650, -500), new Vector(0, 1, 0)),
                        1000
                ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Cylinder(
                        60,
                        new Ray(new Point(800, -650, -500), new Vector(0, 1, 0)),
                        1000
                ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Cylinder(
                        60,
                        new Ray(new Point(1000, -650, -500), new Vector(0, 1, 0)),
                        1000
                ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))
        );

        scene.lights.add(
                new SpotLight(
                        new Color(1020, 400, 400),
                        new Point(-600, -650, -150),
                        new Vector(-1, -1, -4))
                        .setKl(0.00001)
                        .setKq(0.000005));
        scene.lights.add(
                new SpotLight(
                        new Color(1020, 400, 400),
                        new Point(5, -650, -150),
                        new Vector(-1, -1, -4))
                        .setKl(0.00001)
                        .setKq(0.000005));
        scene.lights.add(
                new SpotLight(
                        new Color(1020, 400, 400),
                        new Point(-400, -650, -150),
                        new Vector(-1, -1, -4))
                        .setKl(0.00001)
                        .setKq(0.000005));
        scene.lights.add(
                new DirectionalLight(new Color(white), new Vector(-1, 0, 0))
        );
        scene.lights.add(
                new DirectionalLight(new Color(white), new Vector(0, -1, 0))
        );

        ImageWriter imageWriter = new ImageWriter("Real Bonus Stage 7- moved", 500, 500);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }
}
