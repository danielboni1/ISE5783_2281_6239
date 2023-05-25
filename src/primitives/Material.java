package primitives;

/**
 * The Material class represents the material properties of a geometry.
 * It defines the diffuse reflection coefficient (Kd), specular reflection coefficient (Ks),
 * and the shininess factor (nShininess) of the material.
 */
public class Material {
    Double3 Kd = Double3.ZERO;
    Double3 Ks = Double3.ZERO;
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
     * Returns the diffuse reflection coefficient (Kd) of the material.
     *
     * @return the diffuse reflection coefficient.
     */
    public Double3 getKd() {
        return Kd;
    }

    /**
     * Returns the specular reflection coefficient (Ks) of the material.
     *
     * @return the specular reflection coefficient.
     */
    public Double3 getKs() {
        return Ks;
    }

    /**
     * Returns the shininess factor (nShininess) of the material.
     *
     * @return the shininess factor.
     */
    public int getnShininess() {
        return nShininess;
    }
}
