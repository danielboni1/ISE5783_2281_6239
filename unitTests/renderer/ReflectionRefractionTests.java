/**
 * 
 */
package renderer;

import static java.awt.Color.*;

import geometries.Polygon;
import lighting.DirectionalLight;
import lighting.PointLight;
import org.junit.jupiter.api.Test;

import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import lighting.SpotLight;
import primitives.*;
import renderer.*;
import scene.Scene;

import java.util.List;

/** Tests for reflection and transparency functionality, test for partial
 * shadows
 * (with transparency)
 * @author dzilb */
public class ReflectionRefractionTests {
   private Scene scene = new Scene.SceneBuilder("Test scene").build();

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheres() {
      Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(150, 150).setVPDistance(1000);

      scene.geometries.add( //
                           new Sphere( 50d,new Point(0, 0, -50)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.4).setKs(0.3).setShininess(100).setKt(0.3)),
                           new Sphere( 25d,new Point(0, 0, -50)).setEmission(new Color(RED)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(100)));
      scene.lights.add( //
                       new SpotLight(new Color(1000, 600, 0), new Point(-100, -100, 500), new Vector(-1, -1, -2)) //
                          .setKl(0.0004).setKq(0.0000006));

      camera.setImageWriter(new ImageWriter("refractionTwoSpheres", 500, 500)) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere lighted by a spot light */
   @Test
   public void twoSpheresOnMirrors() {
      Camera camera = new Camera(new Point(0, 0, 10000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
         .setVPSize(2500, 2500).setVPDistance(10000); //

      scene.setAmbientLight(new AmbientLight(new Color(255, 255, 255), 0.1));

      scene.geometries.add( //
                           new Sphere( 400d,new Point(-950, -900, -1000)).setEmission(new Color(0, 50, 100)) //
                              .setMaterial(new Material().setKd(0.25).setKs(0.25).setShininess(20)
                                 .setKt(new Double3(0.5, 0, 0))),
                           new Sphere( 200d,new Point(-950, -900, -1000)).setEmission(new Color(100, 50, 20)) //
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

   /** Produce a picture of a two triangles lighted by a spot light with a
    * partially
    * transparent Sphere producing partial shadow */
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
                           new Sphere( 30d,new Point(60, 50, -50)).setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.2).setKs(0.2).setShininess(30).setKt(0.6)));

      scene.lights.add(new SpotLight(new Color(700, 400, 400), new Point(60, 50, 0), new Vector(0, 0, -1)) //
         .setKl(4E-5).setKq(2E-7));
      ImageWriter imageWriter = new ImageWriter("refractionShadow", 600, 600);
      camera.setImageWriter(imageWriter) //
         .setRayTracer(new RayTracerBasic(scene)) //
         .renderImage() //
         .writeToImage();
   }
   @Test

   public void allefectTestBounos() {
      Camera camera = new Camera(new Point(0, 0, -1000), new Vector(0, 0, 1), new Vector(0, -1, 0)) .setVPSize(200, 200).setVPDistance(1000);
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15)).background.add(new Color(white));

      scene.geometries.add( //
              new Triangle( new Point(-55, 10, 115), new Point(-25, 49, 115), new Point(-85, 49, 115))
                      .setEmission(new Color(76, 3, 19)), //
              new Sphere( 50, new Point(70, -70, 0))
                      .setEmission(new Color(200, 200, 0)),
              new Sphere(30, new Point(70, -70, -10))
                      .setEmission(new Color(java.awt.Color.orange)),
              new Sphere(20, new Point(70, -70, -30))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                      .setEmission(new Color(java.awt.Color.RED)),
              new Sphere( 20, new Point(-70, -60, -30))
                      .setEmission(new Color(java.awt.Color.darkGray))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60)),
              new Sphere( 20, new Point(-60, -50, -30))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                      .setEmission(new Color(java.awt.Color.darkGray)),
              new Sphere( 20, new Point(-40, -65, -30))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                      .setEmission(new Color(java.awt.Color.darkGray)),

              new Polygon(new Point(25, 110, 115), new Point(35, 110, 115), new Point(35, 50, 115), new Point(25, 50, 115))
                      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(60))
                      .setEmission(new Color(17, 11, 5)),

              new Triangle( new Point(30, -20, 115), new Point(15, 50, 115), new Point(45, 50, 115))
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
                      .setEmission(new Color(54, 40, 30))

      );


      scene.lights.add(new SpotLight(new Color(yellow), new Point(60, 50, 0), new Vector(0, 0, -1)) //
              .setKl(4E-5).setKq(2E-7));


      ImageWriter imageWriter = new ImageWriter("bonus", 600, 600);
      camera.setImageWriter(imageWriter) //
              .setRayTracer(new RayTracerBasic(scene)) //
              .renderImage() //
              .writeToImage();

   }
}
