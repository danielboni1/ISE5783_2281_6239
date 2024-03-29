package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

/**
 * The LightSource interface represents a light source in a scene.
 * It defines methods to retrieve the intensity and direction of the light at a given point.
 */
public interface LightSource {

    /**
     * Returns the intensity of the light at a given point.
     *
     * @param p the point at which to calculate the intensity.
     * @return the intensity of the light as a Color object.
     */
    public Color getIntensity(Point p);

    /**
     * Returns the direction from the light source to a given point.
     *
     * @param p the point for which to calculate the direction.
     * @return the direction vector from the light source as a Vector object.
     */
    public Vector getL(Point p);

    /**
     * Getter.
     *
     * @param point- the intersection point
     * @return the distance between the light source and the geometry
     */
    double getDistance(Point point);
    /**
     * Helper method that should be a getter for the directions of the rays
     * of the beam that we want to calculate the average transparency from.
     * (for soft shadow).
     *
     * @param p           of the geoPoint
     * @return list of vectors
     */
    public List<Vector> getLightVectors(Point p);//, int numOfPoints);

    }
