/**
 * The SpotLight class represents a spotlight source in a scene.
 * It extends the PointLight class.
 */
package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.List;

import static primitives.Util.isZero;

public class SpotLight extends PointLight {

    private Vector direction;
    private double narrowBeam = 1;

    /**
     * Constructs a SpotLight object with the given intensity, position, and direction.
     *
     * @param intensity the intensity of the light as a Color object.
     * @param position  the position of the light as a Point object.
     * @param direction the direction of the light as a Vector object.
     */
    public SpotLight(Color intensity, Point position, Vector direction) {
        super(intensity, position);
        this.direction = direction.normalize();
    }

    @Override
    public Color getIntensity(Point point) {
        // Compute the intensity of the point light at the given point
        Color Ic = super.getIntensity(point);

        // Compute the projection of the vector from the light position
        // to the point on the direction of the spotlight
        double projection = getL(point).dotProduct(direction);

        // If the projection is zero or negative,
        // the point is outside the spotlight's cone, so the intensity is zero
        if (isZero(projection)) {
            return Color.BLACK;
        }

        // Compute the intensity factor based on the projection
        double factor = Math.max(0, projection);

        // Apply the narrow beam factor, if specified
        if (narrowBeam != 1) {
            factor = Math.pow(factor, narrowBeam);
        }

        // Scale the intensity by the computed factor and return the result
        return Ic.scale(factor);
    }

    /**
     * Sets the narrow beam factor for the spotlight.
     * The narrow beam factor controls the intensity falloff with angle.
     *
     * @param narrowBeam the narrow beam factor.
     * @return the SpotLight object with the updated narrow beam factor.
     */
    public SpotLight setNarrowBeam(double narrowBeam) {
        this.narrowBeam = narrowBeam;
        return this;
    }


}
