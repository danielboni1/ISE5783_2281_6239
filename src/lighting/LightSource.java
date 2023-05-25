package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

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

}
