package renderer;

import primitives.Point;
import primitives.Ray;
import primitives.Vector;

import static primitives.Util.isZero;
//todo: to fix the comments!!!!!!!!!!!!!!!!!
/**
 *  The Camera class represents a virtual camera in a 3D scene.
 *  It defines the camera's position and orientation,
 *  as well as the size and distance of its view plane.
 */
public class Camera  {
    /**
     * The camera's position in the scene.
     */
    private Point p0;

    /**
     * The up vector of the camera, used to determine its orientation.
     */
    private Vector vUp;

    /**
     * The to vector of the camera, used to determine its orientation.
     */
    private Vector vTo;

    /**
     * The right vector of the camera.
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

    /**
     * Constructs a new Camera with the given position and orientation vectors.
     *
     * @param p0 the position of the camera.
     * @param vto the to vector of the camera.
     * @param vup the up vector of the camera.
     * @throws IllegalArgumentException if the vto and vup vectors are not orthogonal.
     */
    public Camera(Point p0, Vector vto, Vector vup) {
        if (!isZero(vto.dotProduct(vup))) {
            throw new IllegalArgumentException("The vto and vup vectors must be orthogonal.");
        }
        this.vTo = vto.normalize();
        this.vUp = vup.normalize();
        this.vRight = vto.crossProduct(vup).normalize();
    }

    /**
     * Getter for the position of the camera.
     *
     * @return the position of the camera.
     */
    public Point getP0() {
        return p0;
    }

    /**
     * Getter for the TUp.
     *
     * @return the up vector of the camera.
     */
    public Vector getVUp() {
        return vUp;
    }

    /**
     * Getter for the VTo.
     *
     * @return the to vector of the camera.
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
     * @param width the width of the camera's view plane.
     * @param height the height of the camera's view plane.
     * @return the Camera object for chaining method calls.
     */
    public Camera setVPSize(double width, double height) {


        this.width = width;
        this.height = height;
        return this;
    }
    public Camera setVPDistance(double distance)
    {
        this.distance = distance;
        return this;
    }
    public Ray constructRay(int nX, int nY, int j, int i)
    {
        return null;
    }

}
