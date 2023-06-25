package miniProjects;

import geometries.*;
import lighting.AmbientLight;
import lighting.DirectionalLight;
import lighting.PointLight;
import lighting.SpotLight;
import org.junit.jupiter.api.Test;
import primitives.*;
import renderer.Camera;
import renderer.ImageWriter;
import renderer.RayTracerBasic;
import scene.Scene;

import java.util.List;

import static java.awt.Color.*;

public class SoftShadowTest {

    private Material trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);


    private Geometry plane = new Plane( new Point(0,0,200), new Vector(0,1,0))
            .setEmission(new Color(15, 60, 15))
            .setMaterial(new Material().setKr(1));
    private Scene scene = new Scene.SceneBuilder("Test scene")
            .setBackground(new Color(42,201,255))
            .build();
    /*private Camera camera = new Camera(
            new Point(0, 0, 1000),
            new Vector(0, 0, -1),
            new Vector(0, 1, 0))
            .setVPSize(200, 200)
            .setVPDistance(1000)
            .setRayTracer(new RayTracerBasic(scene));*/
    Camera camera = new Camera(
            new Point(0, 300, 1000),
            new Vector(0, 0, -1),
            new Vector(0, 1, 0))
            .setVPSize(2000, 2000)
            .setVPDistance(1500);

    @Test
    public void EmptyRoom() {
        Camera camera = new Camera(
                new Point(0, 300, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setVPSize(2000, 2000).setVPDistance(1500) //
                .setRayTracer(new RayTracerBasic(scene));

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        //Right wall:
        Geometry rightWall = new Polygon(new Point(501, 0, 0), new Point(501, 0, 300), new Point(501, 500, 300), new Point(501, 500, 0));
        rightWall.setMaterial(new Material());
        rightWall.setEmission(new Color(122, 122, 122));
        scene.geometries.add(rightWall);
        //  part 1 of right wall

        //  part 2 of right wall

        //  part 3 of right wall

        //  part 4 of right wall

        //Left wall:
        Polygon leftWall = new Polygon(new Point(-501, 0, 0), new Point(-501, 0, 600), new Point(-501, 500, 600), new Point(-501, 500, 0));
        leftWall.setMaterial(new Material());
        leftWall.setEmission(new Color(122, 122, 122));
        scene.geometries.add(leftWall);

        //Ceiling:

        //Floor:
        Geometry floorBackground = new Polygon(
                new Point(-500, -2, 0),
                new Point(-500, -2, 300),
                new Point(500, -2, 300),
                new Point(500, -2, 0));
        floorBackground.setMaterial(new Material());
        floorBackground.setEmission(new Color(122, 122, 122));
        scene.geometries.add(floorBackground);


        //Front wall:
        Geometry frontWall = new Polygon(
                new Point(-500, 0, 0),
                new Point(500, 0, 0),
                new Point(500, 500, 0),
                new Point(-500, 500, 0))
                .setMaterial(new Material())
                .setEmission(new Color(122, 122, 122));
        scene.geometries.add(frontWall);

        //region mirror
        // back
        Polygon mirrorBack = new Polygon(new Point(-200, 275, 1.1), new Point(450, 275, 1.1), new Point(450, 425, 1.1), new Point(-200, 425, 1.1));
        mirrorBack.setEmission(new Color(122, 122, 122)).setMaterial(new Material().setKs(0).setKd(0.5));
        scene.geometries.add(mirrorBack);

        //End Of Room
        Polygon doorWall = new Polygon(new Point(0, 0, 300), new Point(0, 500, 300), new Point(500, 500, 300), new Point(500, 0, 300));
        doorWall.setMaterial(new Material());
        doorWall.setEmission(new Color(122, 122, 122));
        scene.geometries.add(doorWall);

        /*scene.geometries.add(
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                new Sphere(30d,new Point(0, 0, -11))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))
        );*/
        scene.lights.add(
                new PointLight(new Color(700, 400, 400), new Point(40, 40, 115)) //
                        .setKl(4E-4).setKq(2E-5));

        camera.setImageWriter(new ImageWriter("Empty Room", 600, 600))
                .renderImage()
                .writeToImage();
    }


    @Test
    public void OpenSpace() {
        //Colors array:
        Color[] colors = {
                new Color(42, 201, 255),// LIGHT BLUE SKY
                new Color(0, 255, 0)     // GREEN GRASS
        };

        Camera camera = new Camera(
                new Point(0, 0, 10000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setVPSize(2500, 2500)
                .setVPDistance(10000);


        scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

        scene.geometries.add(
                //sphere in sphere
                /*new Sphere(400d, new Point(200, -300, -1000)).setEmission(new Color(0, 50, 100)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                .setKt(new Double3(0.5, 0, 0))),
                new Sphere(200d, new Point(200, -300, -1000)).setEmission(new Color(100, 50, 20)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),*/
                //floor
                new Plane(new Point(-400, -700, -1000), new Vector(0, -1, 0))
                        .setEmission(colors[1])
                        .setMaterial(new Material() .setKd(0.25)),
                /*//wall 1
                new Plane(new Point(-400, -700, -5000), new Vector(0, 0, -1))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setKr(1)),
                //wall 2
                new Plane(new Point(1200, -650, -500), new Vector(1, 0, 0))
                        .setEmission(new Color(15, 15, 15))
                        .setMaterial(new Material().setKr(1)),*/
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
                        new Ray(new Point(600, -700, -500), new Vector(0, 1, 0)),
                        1050
                ).setEmission(new Color(100, 50, 20))
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)),
                new Sphere(
                        320,
                        new Point(600, 400, -500)
                ).setEmission(new Color(green))
                        .setMaterial(new Material()),
                new Sphere(200, new Point(1200, 1200, 0))
                .setEmission(new Color(200, 200, 0))
                        .setMaterial(new Material()
                                .setKt(0.5)
                                .setKs(0.5))
                /*new Cylinder(
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
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20))*/
        );

     /*   scene.lights.add(
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
                        .setKq(0.000005));*/
       scene.lights.add(
                new PointLight(
                        new Color(1020, 400, 400),
                        new Point(1200, 1400, 0))
                        .setKl(0.00001)
                        .setKq(0.000005));

        /*scene.lights.add(
                new DirectionalLight(new Color(white), new Vector(-1, 0, 0))
        );*/
        scene.lights.add(
                new DirectionalLight(new Color(white), new Vector(0, -1, 0))
        );

        ImageWriter imageWriter = new ImageWriter("MINI PROJECT 1", 500, 500);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }
    /**
     * Test for initialising the points in SpotLight
     */
    @Test
    void test() {
        Scene scene = new Scene.SceneBuilder("TestScene").build();
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))//
                .setVPSize(200, 200).setVPDistance(1000) //
                .setRayTracer(new RayTracerBasic(scene));
        Material roomMaterial = new Material().setKs(0.3).setKd(0.5);
        Material ball = new Material().setKs(0.8).setKd(0.5);
        scene.geometries.add(
                //Room
                //Right wall
                new Polygon(new Point(100, 100, 0), new Point(100, -80, 0), new Point(40, -80, -250), new Point(40, 100, -250))
                        .setEmission(new Color(200, 200, 100)),//.setMaterial(roomMaterial),
                //Left wall
                new Polygon(new Point(-100, 100, 0), new Point(-100, -80, 0), new Point(40, -80, -250), new Point(40, 100, -250))
                        .setEmission(new Color(255, 153, 34)),//.setMaterial(roomMaterial),
                //Ceiling
                new Triangle(new Point(100, 100, 0), new Point(-100, 100, 0), new Point(40, 99, -250))
                        .setEmission(new Color(0, 240, 0).scale(0.8)).setMaterial(roomMaterial),
                //floor
                new Triangle(new Point(40, -80, -250), new Point(185, -100, 0), new Point(-265, -100, 0))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)),
                //Lamp
                new Sphere( 15,new Point(40, 100, -100)).setMaterial(new Material().setKt(1))
                        .setEmission(new Color(GRAY))
                //mirror
                ,new Polygon(new Point(-40,20,0),new Point(-40,-20,0),new Point(-20,-20,-100),new Point(-20,20,-100))
                        .setEmission(new Color(15, 15, 15)).setMaterial(new Material().setKr(1))

                , new Sphere( 20,new Point(40, -20, -100)).setMaterial(ball).setEmission(new Color(BLUE))
        );
        Point sptPoint = new Point(-100, -40, 0);
        //scene.lights.add(new SpotLight(new Color(YELLOW), sptPoint, new Point(-10, -45, -50).subtract(sptPoint)).setKl(1000).setKq(1.5E-6));
        //scene.lights.add(new PointLight(new Color(240, 140, 0), new Point(40, 84, -100)).setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(new SpotLight(new Color(100, 20, 200), new Point(40, 75, -100),new Vector(0,-1,0))
                .setKl(1E-5).setKq(1.5E-7));

        camera.setImageWriter(new ImageWriter("softShadowTest2", 500, 500)) //
                .renderImage() //
                .writeToImage();
    }
   }

