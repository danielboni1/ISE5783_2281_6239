/**
 * The Vector class represents a vector in 3D space, extending the Point class.
 */
package primitives;

public class Vector extends Point {
    /**
     * Constructs a new Vector with the given x, y, and z coordinates.
     *
     * @param x the x-coordinate of the vector.
     * @param y the y-coordinate of the vector.
     * @param z the z-coordinate of the vector.
     * @throws IllegalArgumentException if the vector is the zero vector.
     */
    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO)) {
            throw new IllegalArgumentException("vector cannot be vector(0,0,0)");
        }
    }

    /**
     * Constructs a new Vector from a Double3 object.
     *
     * @param double3 the Double3 object from which to construct the vector.
     * @throws IllegalArgumentException if the vector is the zero vector.
     */
    Vector(Double3 double3) {
        this(double3.d1, double3.d2, double3.d3);
    }

    /**
     * Calculates the length of the vector.
     *
     * @return the length of the vector.
     */
    public double length() {
        return Math.sqrt(lengthSquared());
    }

    /**
     * Calculates the length of the vector squared.
     *
     * @return the length of the vector squared.
     */
    public double lengthSquared() {
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;

        return x * x + y * y + z * z;
    }

    /**
     * Returns a normalized version of the vector.
     *
     * @return a normalized version of the vector.
     */
    public Vector normalize() {
        double len = length();
        double x = xyz.d1 / len;
        double y = xyz.d2 / len;
        double z = xyz.d3 / len;
        return new Vector(x, y, z);
    }

    /**
     * Calculates the dot product of this vector with another vector.
     *
     * @param vector the other vector to calculate the dot product with.
     * @return the dot product of the two vectors.
     */
    public double dotProduct(Vector vector) {
        double x = vector.xyz.d1 * xyz.d1;
        double y = vector.xyz.d2 * xyz.d2;
        double z = vector.xyz.d3 * xyz.d3;
        return x + y + z;
    }

    /**
     * Calculates the cross product of this vector with another vector.
     *
     * @param u the other vector to calculate the cross product with.
     * @return the cross product of the two vectors.
     */
    public Vector crossProduct(Vector u) {
        double x = xyz.d2 * u.xyz.d3 - xyz.d3 * u.xyz.d2;
        double y = xyz.d3 * u.xyz.d1 - xyz.d1 * u.xyz.d3;
        double z = xyz.d1 * u.xyz.d2 - xyz.d2 * u.xyz.d1;

        return new Vector(x, y, z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Vector other)) return false;
        return this.xyz.equals(other.xyz);
    }

    @Override
    public String toString() {
        return super.toString();
    }

    /**
     * Adding a vector to this vector.
     *
     * @param vector the vector that added.
     * @return the result (a new vector) of the adding.
     */
    public Vector add(Vector vector) {
        return new Vector(xyz.add(vector.xyz));
    }

    /**
     * Calculates the product of this vector with some number.
     *
     * @param num the number to calculate the cross product with.
     * @return the vector that created.
     */
    public Vector scale(double num) {
        return new Vector(xyz.d1 * num, xyz.d2 * num, xyz.d3 * num);
    }
    public Vector turn(double angle, Vector ortho){
        ortho = ortho.changeLength(this.length());
        double ortho_scaler = Math.sin(angle);
        double this_scaler = Math.cos(angle);
        if (Util.isZero(ortho_scaler)){
            return this.scale(this_scaler).changeLength(this.length());
        }else if(Util.isZero(this_scaler)){
            return ortho.scale(ortho_scaler).changeLength(this.length());
        }
        return ortho.scale(Math.sin(angle)).add(this.scale(Math.cos(angle))).changeLength(this.length());
    }
    private Vector changeLength(double newLength)
    {
        return this.normalize().scale(newLength);
    }

}
