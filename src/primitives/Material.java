package primitives;

/**
 * The Material class represents the material properties of a geometry.
 * It defines the diffuse reflection coefficient (Kd), specular reflection coefficient (Ks),
 * and the shininess factor (nShininess) of the material.
 */
public class Material {
    /**
     * The diffuse reflection coefficient:
     * It represents the amount of light that is diffusely
     * reflected by the surface of an object.
     * This coefficient determines the color and intensity of the diffuse reflection.
     */
    Double3 Kd = Double3.ZERO;
    /**
     * The specular reflection coefficient:
     * It represents the amount of light that is specularly reflected
     * by the surface of an object.
     * This coefficient determines the color and intensity of the specular
     * reflection, which is responsible for highlights and shiny effects.
     */
    Double3 Ks = Double3.ZERO;
    /**
     * The transmission coefficient (refraction):
     * It represents the amount of light that is transmitted
     * through a transparent or translucent object.
     * This coefficient determines the color and intensity of the transmitted light.
     */
    Double3 Kt = Double3.ZERO;
    /**
     * Reflection coefficient:
     * It represents the amount of light that is reflected by a surface.
     * This coefficient determines the color and intensity of the reflected light.
     */
    Double3 Kr = Double3.ZERO;

    /**
     * The shininess factor (nShininess) of the material.
     */
    int nShininess = 0;


    /**
     * Sets the diffuse reflection coefficient (Kd) of the material.
     *
     * @param kd the diffuse reflection coefficient.
     * @return the Material with the updated diffuse reflection coefficient.
     */
    public Material setKd(Double3 kd) {
        Kd = kd;
        return this;
    }

    /**
     * Sets the specular reflection coefficient (Ks) of the material.
     *
     * @param ks the specular reflection coefficient.
     * @return the Material with the updated specular reflection coefficient.
     */
    public Material setKs(Double3 ks) {
        Ks = ks;
        return this;
    }

    /**
     * Sets the shininess factor (nShininess) of the material.
     *
     * @param nShininess the shininess factor.
     * @return the Material with the updated shininess factor.
     */
    public Material setShininess(int nShininess) {
        this.nShininess = nShininess;
        return this;
    }

    /**
     * Sets the diffuse reflection coefficient (Kd) of the material.
     *
     * @param kd the diffuse reflection coefficient.
     * @return the Material with the updated diffuse reflection coefficient.
     */
    public Material setKd(double kd) {
        Kd = new Double3(kd);
        return this;
    }

    /**
     * Sets the specular reflection coefficient (Ks) of the material.
     *
     * @param ks the specular reflection coefficient.
     * @return the Material with the updated specular reflection coefficient.
     */
    public Material setKs(double ks) {
        Ks = new Double3(ks);
        return this;
    }

    /**
     * Getter.
     *
     * @return the diffuse reflection coefficient.
     */
    public Double3 getKd() {
        return Kd;
    }

    /**
     * Getter.
     *
     * @return the specular reflection coefficient.
     */
    public Double3 getKs() {
        return Ks;
    }

    /**
     * Getter.
     *
     * @return the shininess factor.
     */
    public int getShininess() {
        return nShininess;
    }

    /**
     * Getter.
     *
     * @return the transparency coefficient.
     */
    public Double3 getKt() {
        return Kt;
    }

    /**
     * Getter.
     *
     * @return the reflection coefficient.
     */
    public Double3 getKr() {
        return Kr;
    }

    /**
     * Sets the transparency coefficient (Kt) of the material.
     *
     * @param kt the transparency coefficient.
     * @return the Material with the updated transparency coefficient.
     */
    public Material setKt(Double3 kt) {
        Kt = kt;
        return this;
    }

    /**
     * Sets the reflection coefficient (Kr) of the material.
     *
     * @param kr the reflection coefficient.
     * @return the Material with the updated reflection coefficient.
     */
    public Material setKr(Double3 kr) {
        Kr = kr;
        return this;
    }

    /**
     * Sets the transparency coefficient (Kt) of the material.
     *
     * @param kt the transparency coefficient.
     * @return the Material with the updated transparency coefficient.
     */
    public Material setKt(double kt) {
        this.Kt = new Double3(kt);
        return this;
    }

    /**
     * Sets the reflection coefficient (Kr) of the material.
     *
     * @param kr the reflection coefficient.
     * @return the Material with the updated reflection coefficient.
     */
    public Material setKr(double kr) {
        this.Kr = new Double3(kr);
        return this;
    }

}
