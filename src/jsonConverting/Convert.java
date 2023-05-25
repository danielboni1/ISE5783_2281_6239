package jsonConverting;

import com.google.gson.Gson;
import geometries.Geometries;
import geometries.Plane;
import geometries.Sphere;
import geometries.Triangle;
import lighting.AmbientLight;
import primitives.Color;
import primitives.Double3;
import primitives.Point;
import primitives.Vector;
import renderer.ImageWriter;
import scene.Scene;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

/**
 * The converting methods for JSON format to regular class.
 */
public class Convert {
    /**
     * Converts the JSON file at the given path to an ImageWriter object.
     *
     * @param path the path of the JSON file.
     * @return the converted ImageWriter object.
     */
    public ImageWriter FromJsonToImageWriter(String path) {
        Gson gson = new Gson();

        try {
            FileReader fileReader = new FileReader(path);
            HelpersClasses.imageWriter = gson.fromJson(fileReader, jsonConverting.ImageWriterOfJson.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        return new ImageWriter(
                HelpersClasses.imageWriter.getName(),
                HelpersClasses.imageWriter.getNX(),
                HelpersClasses.imageWriter.getNY()
        );
    }

    /**
     * Converts the given ImageWriter object to JSON format and saves it to the specified path.
     *
     * @param imageWriter the ImageWriter object to convert.
     * @param path        the path to save the JSON file.
     */
    public static void FromImageWriterToJson(ImageWriter imageWriter, String path) {

        HelpersClasses.imageWriter = new jsonConverting.ImageWriterOfJson(imageWriter);

        Gson gson = new Gson();

        try (FileWriter fileWriter = new FileWriter(path)) {
            gson.toJson(HelpersClasses.imageWriter, fileWriter);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Converts the JSON file at the given path to a Scene object.
     *
     * @param path the path of the JSON file.
     * @return the converted Scene object.
     */
    public static Scene FromJsonToScene(String path) {
        Gson gson = new Gson();
        FileReader fileReader;
        SceneOfJson sceneOfJson;
        try {
            fileReader = new FileReader(path);
            sceneOfJson = gson.fromJson(fileReader, SceneOfJson.class);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        jsonConverting.HelpersClasses.sceneOfJson = sceneOfJson;

        // Start building Scene
        Scene.SceneBuilder sceneBuilder = new Scene.SceneBuilder("json1");

        // Parse background color
        String background = HelpersClasses.sceneOfJson.scene.background;
        Point temp = parseStringToPoint(background);
        sceneBuilder.setBackground(
                new Color(
                        temp.getX(), temp.getY(), temp.getZ()
                )
        );

        // Parse AmbientLight
        if (HelpersClasses.sceneOfJson.scene.ambientLight.intensity != null) {
            Point ambientLight = parseStringToPoint(HelpersClasses.sceneOfJson.scene.ambientLight.intensity);
            sceneBuilder.setAmbientLight(
                    new AmbientLight(
                            new Color(ambientLight.getX(), ambientLight.getY(), ambientLight.getZ()),
                            new Double3(1)
                    )
            );
        }

        // Parse Geometries
        jsonConverting.HelperGeometryData geometriesData = HelpersClasses.sceneOfJson.scene.geometryData;
        Geometries geometries = new Geometries();

        if (geometriesData != null) {
            if (geometriesData.triangles != null) {
                // Triangle parse
                for (jsonConverting.HelperTriangle triangle : geometriesData.triangles) {
                    // Getting points
                    Point p0 = parseStringToPoint(triangle.p0);
                    Point p1 = parseStringToPoint(triangle.p1);
                    Point p2 = parseStringToPoint(triangle.p2);

                    geometries.add(
                            new Triangle(p0, p1, p2)
                    );
                }
            }

            if (geometriesData.spheres != null) {
                // Sphere parse
                for (jsonConverting.HelperSphere sphere : geometriesData.spheres) {
                    // Getting point and radius
                    Point center = parseStringToPoint(sphere.center);
                    double radius = Double.parseDouble(sphere.radius);

                    geometries.add(
                            new Sphere(radius, center)
                    );
                }
            }

            // Plane parse
            if (geometriesData.planes != null) {
                for (jsonConverting.HelperPlane plane : geometriesData.planes) {
                    // Getting point and vector
                    Point p0 = parseStringToPoint(plane.p0);
                    Point tmp = parseStringToPoint(plane.normal);
                    Vector normal = new Vector(tmp.getX(), tmp.getY(), tmp.getZ());

                    geometries.add(
                            new Plane(p0, normal)
                    );
                }
            }
        }

        // Adding geometries to scene
        sceneBuilder.setGeometries(geometries);

        return sceneBuilder.build();
    }

    /**
     * Parses a string representation of a point and converts it to a Point object.
     *
     * @param s the string representation of the point in the format "x y z".
     * @return the parsed Point object.
     */
    private static Point parseStringToPoint(String s) {
        String[] coordinates = s.split(" ");
        double x = Double.parseDouble(coordinates[0]);
        double y = Double.parseDouble(coordinates[1]);
        double z = Double.parseDouble(coordinates[2]);

        return new Point(x, y, z);
    }
}
