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
    private Scene scene = new Scene.SceneBuilder("Test scene").build();
    private Camera camera = new Camera(
            new Point(0, 0, 1000),
            new Vector(0, 0, -1),
            new Vector(0, 1, 0))
            .setVPSize(200, 200)
            .setVPDistance(1000)
            .setRayTracer(new RayTracerBasic(scene));

    @Test
    void tableTest() {
        Camera camera = new Camera(
                new Point(0, 0, 1000),
                new Vector(0, 0, -1),
                new  Vector(0, 1, 0))
                .setVPSize(200, 200)
                .setVPDistance(1000)
                .moveTo(-1000);
        ;


        scene.geometries.add(plane);
        scene.lights.add(
                new PointLight(new Color(400, 240, 0), new Point(0, -300, -200))
                        .setKl(1E-5).setKq(1.5E-7));
        ImageWriter imageWriter = new ImageWriter("pictName", 500, 500);
        camera.setImageWriter(imageWriter)
                .setRayTracer(new RayTracerBasic(scene))
                .renderImage()
                .writeToImage();
    }

    @Test
    public void softShadowtrianglesSphere() {
        Camera camera = new Camera(
                new Point(0, 0, 1000),
                new Vector(0, 0, -1),
                new Vector(0, 1, 0))
                .setVPSize(200, 200).setVPDistance(1000) //
                .setRayTracer(new RayTracerBasic(scene));

        scene.setAmbientLight(new AmbientLight(new Color(java.awt.Color.WHITE), 0.15));

        scene.geometries.add( //
                new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135), new Point(75, 75, -150))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150))
                        .setMaterial(new Material().setKs(0.8).setShininess(60)),
                new Sphere(30d,new Point(0, 0, -11))
                        .setEmission(new Color(java.awt.Color.BLUE))
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30))
        );
        scene.lights.add(
                new PointLight(new Color(700, 400, 400), new Point(40, 40, 115)) //
                        .setKl(4E-4).setKq(2E-5));

        camera.setImageWriter(new ImageWriter("softShadowTrianglesSphere", 600, 600)) //
                .renderImage()
                .writeToImage();
    }/**
     * produce a picture with 10 objects and spot light
     */
    @Test
    public void fourObjects() {
        Camera camera1 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000);
        //Camera camera2 = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
               // .setVPSize(200, 200).setVPDistance(1000);        // Rotate the camera by an angle

        //camera2.cameraPosition(new Point(900, 100, 600), new Point(0, 0, 0), 180);


        //Camera camera2 = new Camera(new Point(20, 25, 26), new Vector(-2, 0, 0), new Vector(0, 0, 2)) //
        //        .setVPSize(200, 200).setVPDistance(70);
        Scene scene = new Scene.SceneBuilder("Test scene").setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).build();
        scene.getGeometries().add( //
                new Plane(new Point(58, 40, 1), new Point(1, 13, 1), new Point(-18, 20, 1)) //
                        .setEmission(new Color(darkGray)) //
                        .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20).setKr(1)),
                new Triangle(new Point(-50, -50, 10), new Point(50, -50, 10),
                        new Point(25, 25, 14)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKt(0.45).setKs(0.5).setShininess(60)),
                new Sphere(5d, new Point(-50, -50, 10)).setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(1)),
                new Sphere(5d, new Point(50, -50, 10)).setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.8)),
                new Sphere(5d, new Point(25, 25, 14)).setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.8)),
                new Triangle(new Point(-50, -50, 10), new Point(50, -50, 10),
                        new Point(25, 0, 20)).setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.8)),
                new Triangle(new Point(-50, -50, 10), new Point(25, 25, 14),
                        new Point(25, 0, 20)).setEmission(new Color(BLACK)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKt(1)),
                new Sphere(19d, new Point(-50, 25, 30)).setEmission(new Color(BLUE)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(0.6)),
                new Triangle(new Point(-80, 70, 0), new Point(-80, -70, 0),
                        new Point(-90, 50, 20)).setEmission(new Color(BLACK)).
                        setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(10).setKr(0.2)), //
                new Triangle(new Point(-80, 70, 0), new Point(60, 70, 0),
                        new Point(-90, 50, 20)).setEmission(new Color(BLACK)).
                        setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(10).setKr(0.2)), //
                new Sphere(5d, new Point(-50, 25, 0)).setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(1)),
                new Sphere(5d, new Point(-50, 25, 4)).setEmission(new Color(GREEN)) //
                        .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60).setKr(1)));

//change in the radius - smaller creates a better picture.
        //list of lights:


        scene.lights.add(new SpotLight(new Color(WHITE), new Point(60, 50, 50), new Vector(-0.6, -0.5, -0.5)) //
                .setKl(0.00001).setKq(0.000005));
        scene.lights.add(new PointLight(new Color(WHITE), new Point(0, 0, 20)).setKl(0.00001).setKq(0.000005));
        scene.lights.add(new PointLight(new Color(WHITE), new Point(-50, -20, 20)).setKl(0.00001).setKq(0.000005));


        //scene.getLights().add(new SpotLight(new Color(WHITE),new Point(50,-30,200),new Vector(-1,0,-1)).setKl(0.0004).setKq(0.0000006));
        //scene.getLights().add(new SpotLight(new Color(WHITE),new Point(-75,30,200),new Vector(1,0,-0.55)).setKl(0.0004).setKq(0.0000006));


        ImageWriter imageWriter = new ImageWriter("refraction4Objects_firstAngle", 600, 600);
        camera1.setImageWriter(imageWriter); //
        camera1.setRayTracer(new RayTracerBasic(scene)); //
        camera1.renderImage(); //
        camera1.writeToImage();

        //ImageWriter imageWriter2 = new ImageWriter("refraction4Objects_secondAngle", 600, 600);
        ImageWriter imageWriter3 = new ImageWriter("refractionObjects_Rotated", 600, 600);
        ImageWriter imageWriter4 = new ImageWriter("refractionObjects_Soft_Shadowed_Rad5", 600, 600);

        //  camera2.moveCamera(0,0,25);
        //  camera2.rotateCamera(10);

        /*camera2.setImageWriter(imageWriter2); //
        camera2.setRayTracer(new RayTracerBasic(scene)); //
        camera2.renderImage(); //
        camera2.writeToImage();*/

        camera1.setImageWriter(imageWriter4); //
        camera1.setRayTracer(new RayTracerBasic(scene)); //
        camera1.renderImage(); //
        camera1.writeToImage();

        camera1.pivot(20);
        camera1.setImageWriter(imageWriter3); //
        camera1.setRayTracer(new RayTracerBasic(scene)); //
        camera1.renderImage(); //
        camera1.writeToImage();


    }


   }

