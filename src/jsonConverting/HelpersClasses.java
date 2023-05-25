package jsonConverting;

import renderer.ImageWriter;

/**
 * Helper classes for JSON converting operations.
 */
public class HelpersClasses {
    public static ImageWriterOfJson imageWriter;
    public static SceneOfJson sceneOfJson;

}

/**
 * Represents the JSON format of an ImageWriter.
 */
class ImageWriterOfJson {

    private int nX;
    private int nY;
    private String name;

    /**
     * Constructs an ImageWriterOfJson object from an ImageWriter object.
     *
     * @param imageWriter the ImageWriter object to convert.
     */
    public ImageWriterOfJson(ImageWriter imageWriter) {
        this(imageWriter.getNx(), imageWriter.getNy(), imageWriter.getImageName());
    }

    /**
     * Constructs an ImageWriterOfJson object with the given dimensions and name.
     *
     * @param nx       the width of the image.
     * @param ny       the height of the image.
     * @param imageName the name of the image.
     */
    public ImageWriterOfJson(int nx, int ny, String imageName) {
        nX = nx;
        nY = ny;
        name = imageName;
    }

    /**
     * Getter.
     *
     * @return the name of the image.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter.
     *
     * @return the width of the image.
     */
    public int getNX() {
        return nX;
    }

    /**
     * Getter.
     *
     * @return the height of the image.
     */
    public int getNY() {
        return nY;
    }
}

/**
 * Represents the JSON format of a Scene.
 */
class SceneOfJson {
    HelperScene scene;
}

/**
 * Represents the JSON format of a Scene with its properties.
 */
class HelperScene {
    String background;
    HelperAmbientLight ambientLight;
    HelperGeometryData geometryData;
}

/**
 * Represents the JSON format of an AmbientLight with its intensity.
 */
class HelperAmbientLight {
    String intensity;
}

/**
 * Represents the JSON format of GeometryData which
 * contains arrays of triangles, spheres, and planes.
 */
class HelperGeometryData {
    HelperTriangle[] triangles;
    HelperSphere[] spheres;
    HelperPlane[] planes;
}

/**
 * Represents the JSON format of a Triangle with its three points.
 */
class HelperTriangle {
    String p0;
    String p1;
    String p2;
}

/**
 * Represents the JSON format of a Sphere with its center and radius.
 */
class HelperSphere {
    String center;
    String radius;
}

/**
 * Represents the JSON format of a Plane with its normal and a point on the plane.
 */
class HelperPlane {
    String normal;
    String p0;
}
