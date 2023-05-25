package lighting;

import primitives.Color;
import primitives.Double3;

/**
 * The AmbientLight class represents ambient light in a scene.
 * It is a type of Light that contributes a constant color to all objects in the scene.
 */
public class AmbientLight extends Light {
    //protected final Color intensity;
    /**
     * Represents the absence of ambient light.
     */
    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    /**
     * Constructs an AmbientLight object with the specified intensity and attenuation factors.
     *
     * @param IA The intensity of the ambient light.
     * @param KA The attenuation factors for the ambient light.
     */
    public AmbientLight(Color IA, Double3 KA) {
        super(IA.scale(KA));
    }

    /**
     * Constructs an AmbientLight object with the specified intensity.
     *
     * @param intensity The intensity of the ambient light.
     */
    public AmbientLight(Color intensity) {
        super(Color.BLACK);
    }

    /**
     * Getter.
     *
     * @return The intensity of the ambient light.
     */
    public Color getIntensity() {
        return intensity;
    }

    /**
     * Getter.
     *
     * @return The NONE ambient light, representing the absence of ambient light.
     */
    public static AmbientLight getNONE() {
        return NONE;
    }
}

