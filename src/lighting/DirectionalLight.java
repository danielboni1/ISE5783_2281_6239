package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

/**
 * The DirectionalLight class represents a directional light source in a scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class DirectionalLight extends Light implements LightSource {

    private Vector direction;

    /**
     * Constructs a DirectionalLight object with the specified intensity and direction.
     *
     * @param intensity the intensity of the light.
     * @param direction the direction of the light.
     */
    public DirectionalLight(Color intensity, Vector direction) {
        super(intensity);
        this.direction = direction.normalize();
    }

    /**
     * Returns the intensity of the light at a given point.
     *
     * @param point the point at which to calculate the intensity (not used in this implementation).
     * @return the intensity of the light.
     */
    @Override
    public Color getIntensity(Point point) {
        return getIntensity();
    }

    /**
     * Returns the direction from the light source to a given point.
     *
     * @param point the point for which to calculate the direction (not used in this implementation).
     * @return the direction vector from the light source.
     */
    @Override
    public Vector getL(Point point) {
        return direction;
    }

    @Override
    public double getDistance(Point point) {
        return Double.POSITIVE_INFINITY;
    }
}
