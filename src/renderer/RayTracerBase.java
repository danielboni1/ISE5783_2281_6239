package renderer;

import primitives.Color;
import primitives.Ray;
import scene.Scene;

/**
 * The base class for ray tracers in a scene.
 *
 */
public abstract class RayTracerBase {
    protected Scene scene;

    /**
     * Constructs a RayTracerBase with the given scene.
     *
     * @param scene the scene to trace rays in.
     */
    public RayTracerBase(Scene scene) {
        this.scene = scene;
    }
    /**
     * Traces a ray in the scene and calculates the color.
     *
     * @param ray the ray to trace.
     *
     * @return the color calculated from the traced ray.
     */
    public abstract Color traceRay(Ray ray);
}