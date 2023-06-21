package renderer;

import org.junit.jupiter.api.Test;

import static java.awt.Color.*;

import geometries.*;
import lighting.*;
import primitives.*;
import scene.Scene;

/** Testing basic shadows
 * @author Dan */
public class ShadowTests {
   private Intersectable sphere     = new Sphere(60d,new Point(0, 0, -200))                                         //
      .setEmission(new Color(BLUE))                                                                                  //
      .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
   private Material      trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);

   private Scene         scene      = new Scene.SceneBuilder("Test scene").build();
   private Camera        camera     = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0))   //
      .setVPSize(200, 200).setVPDistance(1000)                                                                       //
           .setRayTracer(new RayTracerBasic(scene));

   /** Helper function for the tests in this module */
   void sphereTriangleHelper(String pictName, Triangle triangle, Point spotLocation) {
      scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
      scene.lights.add( //
                       new PointLight(new Color(400, 240, 0), spotLocation) //
                          .setKl(1E-5).setKq(1.5E-7));
      camera.setImageWriter(new ImageWriter(pictName, 500, 500)) //
         .renderImage() //
         .writeToImage();
   }

   /** Produce a picture of a sphere and triangle with point light and shade */
   @Test
   public void sphereTriangleInitial() {
      sphereTriangleHelper("shadowSphereTriangleInitial", //
                           new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                           new Point(-100, -100, 200));
   }

   /**
	 * Sphere-Triangle shading - move triangle up-right
	 */
	@Test
	public void sphereTriangleMove1() {
		sphereTriangleHelper("shadowSphereTriangleMove1", //
				new Triangle(new Point(-60, -30, 0), new Point(-30, -60, 0), new Point(-58, -58, -4)), //
				new Point(-100, -100, 200));
	}

   /**
	 * Sphere-Triangle shading - move triangle upper-righter
	 */
	@Test
	public void sphereTriangleMove2() {
		sphereTriangleHelper("shadowSphereTriangleMove2", //
		      new Triangle(new Point(-50, -20, 0), new Point(-20, -50, 0), new Point(-48, -48, -4)), //
				new Point(-100, -100, 200));
	}

       /** Sphere-Triangle shading - move spot closer */
   @Test
   public void sphereTriangleSpot1() {
      sphereTriangleHelper("shadowSphereTriangleSpot1", //
                           new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                           new Point(-100, -100, 150));
   }

   /** Sphere-Triangle shading - move spot even more close */
   @Test
   public void sphereTriangleSpot2() {
      sphereTriangleHelper("shadowSphereTriangleSpot2", //
                           new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4)), //
                           new Point(-100, -100, 110));
   }

   /** Produce a picture of two triangles lighted by a spotlight with a Sphere
    * producing a shading */
  @Test
   public void trianglesSphere() {
      scene.setAmbientLight(new AmbientLight(new Color(WHITE), 0.15));

      scene.geometries.add( //
                           new Triangle(new Point(-150, -150, -115), new Point(150, -150, -135),
                                        new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                           new Triangle(new Point(-150, -150, -115), new Point(-70, 70, -140), new Point(75, 75, -150)) //
                              .setMaterial(new Material().setKs(0.8).setShininess(60)), //
                           new Sphere(30d,new Point(0, 0, -11) ) //
                              .setEmission(new Color(BLUE)) //
                              .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30)) //
      );
      scene.lights.add( //
                       new SpotLight(new Color(700, 400, 400), new Point(40, 40, 115), new Vector(-1, -1, -4)) //
                          .setKl(4E-4).setKq(2E-5));

      camera.setImageWriter(new ImageWriter("shadowTrianglesSphere", 600, 600)) //
         .renderImage() //
         .writeToImage();
   }
    /*
    @Test
    void MiniProject_1() {
        Scene scene = new Scene.SceneBuilder("TestScene").build();
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000) //
                .setRayTracer(new RayTracerBasic(scene));


        Intersectable sphere = new Sphere( 60d,new Point(0, 0, -200)) //
                .setEmission(new Color(BLUE)) //
                .setMaterial(new Material().setKd(0.5).setKs(0.5).setShininess(30));
        Material trMaterial = new Material().setKd(0.5).setKs(0.5).setShininess(30);
        Geometry triangle = new Triangle(new Point(-70, -40, 0), new Point(-40, -70, 0), new Point(-68, -68, -4))
                .setEmission(new Color(BLUE)).setMaterial(trMaterial);


        scene.geometries.add(sphere, triangle.setEmission(new Color(BLUE)).setMaterial(trMaterial));
        scene.lights.add(
                new SpotLight(new Color(400, 240, 0), new Point(-100, -100, 200), new Vector(1, 1, -3)) //
                        .setKl(1E-5).setKq(1.5E-7));
        scene.lights.add(
                new PointLight(new Color(GREEN), new Point(-70, -125, 200)).setKl(1E-5).setKq(1.5E-7));  //, new Vector(1, 1, -3)) //

        camera.setImageWriter(new ImageWriter("softShadowTest", 500, 500)) //
                .renderImage() //
                .writeToImage();
    }


    @Test
    void test() {
        Scene scene = new Scene.SceneBuilder("TestScene").build();
        Camera camera = new Camera(new Point(0, 0, 1000), new Vector(0, 0, -1), new Vector(0, 1, 0)) //
                .setVPSize(200, 200).setVPDistance(1000) //
                .setRayTracer(new RayTracerBasic(scene));
        Material roomMaterial = new Material().setKs(0.3).setKd(0.5);
        Material ball = new Material().setKs(0.8).setKd(0.5);
        scene.geometries.add(
                //Room
                //Right wall
                new Polygon(new Point(100, 100, 0), new Point(100, -80, 0), new Point(40, -80, -250), new Point(40, 100, -250))
                        .setEmission(new Color(200, 200, 100)).setMaterial(roomMaterial),
                //Left wall
                new Polygon(new Point(-100, 100, 0), new Point(-100, -80, 0), new Point(40, -80, -250), new Point(40, 100, -250))
                        .setEmission(new Color(252, 247, 135)).setMaterial(roomMaterial),
                //Ceiling
                new Triangle(new Point(100, 100, 0), new Point(-100, 100, 0), new Point(40, 99, -250))
                        .setEmission(new Color(240, 240, 130).scale(0.8)).setMaterial(roomMaterial),
                //Floor
                new Triangle(new Point(40, -80, -250), new Point(185, -100, 0), new Point(-265, -100, 0))
                        .setEmission(new Color(lightGray)).setMaterial(new Material().setShininess(5).setKs(0.2)),
                //Lamp
                new Sphere( 15,new Point(40, 100, -100)).setMaterial(new Material().setKs(0.3).setKd(0.5).setShininess(20).setKt(0.1))
                        .setEmission(new Color(GRAY)),
                new Sphere( 20,new Point(40, 50, -100)).setMaterial(ball).setEmission(new Color(BLUE))
        );
        Point sptPoint = new Point(-100, -40, 0);
        scene.lights.add(new SpotLight(new Color(YELLOW), sptPoint, new Point(-10, -45, -50).subtract(sptPoint)).setKl(1000).setKq(1.5E-6));
        scene.lights.add(new PointLight(new Color(240, 140, 0), new Point(40, 84, -100)).setKl(1E-4).setKq(1.5E-7));
        camera.setImageWriter(new ImageWriter("softShadowTest2", 500, 500)) //
                .renderImage() //
                .writeToImage();
    }*/
}

