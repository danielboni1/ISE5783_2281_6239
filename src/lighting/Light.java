package lighting;

import primitives.Color;

/**
 * The Light class represents a light source in a scene.
 * It provides methods to get the intensity of the light.
 */
public abstract class Light {
    protected final Color intensity;

    /**
     * Constructs a Light object with the specified intensity.
     *
     * @param intensity- The intensity of the light.
     */
    protected Light(Color intensity) {
        this.intensity = intensity;
    }

    /**
     * Getter.
     *
     * @return The intensity of the light.
     */
    public Color getIntensity() {
        return intensity;
    }
}
