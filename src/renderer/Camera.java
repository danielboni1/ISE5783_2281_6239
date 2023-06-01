package renderer;

import primitives.Color;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import java.util.MissingResourceException;

import static primitives.Util.isZero;

/**
 * The Camera class represents a virtual camera in a 3D scene.
 * It defines the camera's position and orientation,
 * as well as the size and distance of its view plane.
 */
public class Camera {
    /**
     * The camera's position in the scene.
     */
    private Point p0;

    /**
     * The up vector of the camera, used to determine its orientation.
     */
    private Vector vUp;

    /**
     * Vector to the center pixel, used to determine its orientation.
     */
    private Vector vTo;

    /**
     * Vector to right in the camera.
     */
    private Vector vRight;

    /**
     * The width of the camera's view plane.
     */
    private double width;

    /**
     * The height of the camera's view plane.
     */
    private double height;

    /**
     * The distance between the camera and its view plane.
     */
    private double distance;

    private ImageWriter imageWriter;

    private RayTracerBase rayTracerBase;

    /**
     * Constructs a new Camera with the given position and orientation vectors.
     *
     * @param p0  the position of the camera.
     * @param vto the to vector of the camera.
     * @param vup the up vector of the camera.
     * @throws IllegalArgumentException if the vto and vup vectors are not orthogonal.
     */
    public Camera(Point p0, Vector vto, Vector vup) {
        if (!isZero(vto.dotProduct(vup))) {
            throw new IllegalArgumentException("The vto and vup vectors must be orthogonal.");
        }
        this.p0 = p0;
        this.vTo = vto.normalize();
        this.vUp = vup.normalize();
        this.vRight = this.vTo.crossProduct(this.vUp).normalize();
    }

    /**
     * Getter for the position of the camera.
     *
     * @return p0
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter for the TUp.
     *
     * @return the vector that directed upwards
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * Getter for the VTo.
     *
     * @return the vector that directed forwards
     */
    public Vector getVTo() {
        return vTo;
    }

    /**
     * Getter for the VRight.
     *
     * @return the right vector of the camera.
     */
    public Vector getVRight() {
        return vRight;
    }

    /**
     * Getter for the width .
     *
     * @return the width of the camera's view plane.
     */
    public double getWidth() {
        return width;
    }

    /**
     * Getter for the height.
     *
     * @return the height of the camera's view plane.
     */
    public double getHeight() {
        return height;
    }

    /**
     * Getter for the distance.
     *
     * @return the distance between the camera and its view plane.
     */
    public double getDistance() {
        return distance;
    }

    /**
     * Sets the width and height of the camera's view plane.
     *
     * @param width  the width of the camera's view plane.
     * @param height the height of the camera's view plane.
     * @return the Camera with the width and height.
     */
    public Camera setVPSize(double width, double height) {
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Setter for the distance.
     *
     * @param distance- the distance from the center of the camera
     * @return the Camera with the distance.
     */
    public Camera setVPDistance(double distance) {
        this.distance = distance;
        return this;
    }


    /**
     * Setter.
     *
     * @param imageWriter- the image writer with the inserted properties.
     * @return the Camera object with the updated ImageWriter.
     */
    public Camera setImageWriter(ImageWriter imageWriter) {
        this.imageWriter = imageWriter;
        return this;
    }

    /**
     * Setter.
     *
     * @param rayTracerBase- the RayTracerBase to be set.
     * @return the Camera object with the updated RayTracerBase.
     */
    public Camera setRayTracer(RayTracerBase rayTracerBase) {
        this.rayTracerBase = rayTracerBase;
        return this;
    }

    /**
     * This method gets properties of a pixel in the
     * view plane and returns the ray that comes
     * out of the camera to that pixel
     *
     * @param nX  - the amount of the rows on the view plane.
     * @param nY- the amount of the columns on the view plane.
     * @param j   - index of x.
     * @param i   - index of y.
     * @return the ray that intersects the pixel.
     */
    public Ray constructRay(int nX, int nY, int j, int i) {
        //The starting point
        Point center = p0.add(vTo.scale(distance));

        //The length of each pixel(each row/column)
        double Rx = width / nX;
        double Ry = height / nY;

        Point movedPoint = center;

        //The distance that the movedPoint should move from the
        //center point in order to get to the requested pixel.
        //(locating the point at the pixel).

        //offsets for movePoint
        double Xj = (j - (nX - 1) / 2d) * Rx;
        double Yi = ((nY - 1) / 2d - i) * Ry;

        //Moving the movedPoint to the pixel if necessary
        if (!isZero(Xj)) {
            movedPoint = movedPoint.add(vRight.scale(Xj));
        }
        if (!isZero(Yi)) {
            movedPoint = movedPoint.add(vUp.scale(Yi));
        }

        //The vector of the requested ray is movedPoint-p0
        Vector CameraToPixel = movedPoint.subtract(p0);

        return new Ray(p0, CameraToPixel);
    }

    /**
     * Renders the image by casting rays from the camera to each pixel on the view plane.
     * Writes the computed pixel colors to the image writer.
     */
    public Camera renderImage() {
        try {
            if (imageWriter == null) {
                throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
            }
            if (rayTracerBase == null) {
                throw new MissingResourceException("missing resource", RayTracerBase.class.getName(), "");
            }
            int NX = imageWriter.getNx();
            int NY = imageWriter.getNy();
            for (int i = 0; i < NY; i++) {
                for (int j = 0; j < NX; j++) {
                    Color pixelColor = castRay(NX, NY, i, j);
                    imageWriter.writePixel(j, i, pixelColor);

                }
            }
        } catch (MissingResourceException ex) {
            throw new UnsupportedOperationException("Not implemented yet" + ex.getClassName());
        }
        return this;
    }

    /**
     * Casts a ray from the camera to the specified pixel and returns the color of the intersected object.
     *
     * @param nX the amount of rows on the view plane.
     * @param nY the amount of columns on the view plane.
     * @param i  the index of the pixel's row.
     * @param j  the index of the pixel's column.
     * @return the color of the intersected object.
     */

    private Color castRay(int nX, int nY, int i, int j) {
        Ray ray = constructRay(nX, nY, j, i);
        Color pixelColor = rayTracerBase.traceRay(ray);
        return pixelColor;
    }

    /**
     * Prints a grid on the image writer with the specified interval and color.
     *
     * @param interval the interval between grid lines.
     * @param color    the color of the grid lines.
     * @throws MissingResourceException if the image writer is not set.
     */
    public void printGrid(int interval, Color color) {
        if (imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        for (int i = 0; i < imageWriter.getNy(); i++) {
            for (int j = 0; j < imageWriter.getNx(); j++) {
                if (i % interval == 0 || j % interval == 0) {
                    imageWriter.writePixel(j, i, color);
                }
            }
        }
    }

    /**
     * Writes the image to the output file using the ImageWriter.
     *
     * @throws MissingResourceException if the image writer is not set.
     */
    public Camera writeToImage() {
        if (imageWriter == null)
            throw new MissingResourceException("missing resource", ImageWriter.class.getName(), "");
        imageWriter.writeToImage();
        return this;
    }


    /**
     * turns the camera by a given angle
     */
    public Camera pivot(double angle) {
        angle = Math.toRadians(angle);
        vUp = vUp.turn(angle, vRight);
        vRight = vRight.turn(angle, vUp.scale(-1));
        return this;
    }

    public Camera turnRight(double angle) {
        angle = Math.toRadians(angle);
        vTo = vTo.turn(angle, vRight);
        vRight = vRight.turn(angle, vUp.scale(-1));
        return this;
    }

    public Camera turnUp(double angle) {
        angle = Math.toRadians(angle);
        vTo = vTo.turn(angle, vUp);
        vUp = vUp.turn(angle, vTo.scale(-1));
        return this;
    }

    public Camera moveUp(double distance) {
        p0 = p0.add(vUp.scale(distance));
        return this;
    }

    public Camera moveTo(double distance) {
        p0 = p0.add(vTo.scale(distance));
        return this;
    }

    public Camera moveRight(double distance) {
        p0 = p0.add(vRight.scale(distance));
        return this;
    }

}
