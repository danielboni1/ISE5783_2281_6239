package scene;

import geometries.Geometries;
import lighting.AmbientLight;
import lighting.Light;
import lighting.LightSource;
import primitives.Color;

import java.util.LinkedList;
import java.util.List;

/**
 * Represents a scene in a 3D environment.
 */
public class Scene {
    public final String name;
    public final Color background;
    public final Geometries geometries;

    /**
     * The ambient light
     */
    public AmbientLight ambientLight;
    public List<LightSource> lights = new LinkedList<>();


    /**
     * Constructs a Scene object using a SceneBuilder.
     *
     * @param sceneBuilder The SceneBuilder used to construct the Scene object.
     */
    public Scene(SceneBuilder sceneBuilder) {
        name = sceneBuilder.name;
        background = sceneBuilder.background;
        ambientLight = sceneBuilder.ambientLight;
        geometries = sceneBuilder.geometries;
    }

    /**
     * getter for the name.
     *
     * @return The name of the scene.
     */
    public String getName() {
        return name;
    }

    /**
     * Getter for the background.
     *
     * @return The background color of the scene.
     */
    public Color getBackground() {
        return background;
    }

    /**
     * Getter for the ambient light.
     *
     * @return The ambient light in the scene.
     */
    public AmbientLight getAmbientLight() {
        return ambientLight;
    }

    /**
     * Getter for the geometries.
     *
     * @return The geometries in the scene.
     */
    public Geometries getGeometries() {
        return geometries;
    }

    /**
     * Sets the ambient light in the scene.
     *
     * @param ambientLight The ambient light to be set in the scene.
     * @return The updated Scene object.
     */
    public Scene setAmbientLight(AmbientLight ambientLight) {
        this.ambientLight = ambientLight;
        return this;
    }

    public Scene setLights(List<LightSource> lights) {
        this.lights = lights;
        return this;
    }

    /**
     * A builder class for constructing Scene objects.
     * (for builder pattern)
     */
    public static class SceneBuilder {
        public final String name;
        public Color background = new Color(java.awt.Color.black);
        public AmbientLight ambientLight = AmbientLight.NONE;
        public Geometries geometries = new Geometries();

        /**
         * Constructs a SceneBuilder with the specified scene name.
         *
         * @param name The name of the scene being built.
         */
        public SceneBuilder(String name) {
            this.name = name;
        }

        /**
         * Sets the background color of the scene being built.
         *
         * @param background The background color to be set.
         * @return The updated SceneBuilder object.
         */
        public SceneBuilder setBackground(Color background) {
            this.background = background;
            return this;
        }

        /**
         * Sets the ambient light in the scene being built.
         *
         * @param ambientLight The ambient light to be set.
         * @return The updated SceneBuilder object.
         */
        public SceneBuilder setAmbientLight(AmbientLight ambientLight) {
            this.ambientLight = ambientLight;
            return this;
        }

        /**
         * Sets the geometries in the scene being built.
         *
         * @param geometries The geometries to be set.
         * @return The updated SceneBuilder object.
         */
        public SceneBuilder setGeometries(Geometries geometries) {
            this.geometries = geometries;
            return this;
        }

        /**
         * Builds and returns the Scene object using the provided settings.
         *
         * @return The constructed Scene object.
         */
        public Scene build() {
            return new Scene(this);
        }
    }
}
