package primitives;

public class Vector extends Point{

    public Vector(double x, double y, double z) {
        super(x, y, z);
        if (xyz.equals(Double3.ZERO))
        {
            throw new IllegalArgumentException("vector cannot be vector(0,0,0)");
        }
    }

    Vector(Double3 double3) {
  //      super(double3);
  //      if (double3.equals(Double3.ZERO)) {
  //          throw new IllegalArgumentException("vector cannot be vector(0,0,0)");
  //      }
         this (double3.d1,double3.d2, double3.d3);
    }

    public double length() {
        return Math.sqrt(lengthSquared());
    }

    public double lengthSquared() {
        double x = xyz.d1;
        double y = xyz.d2;
        double z = xyz.d3;

        return x*x + y*y +z*z;
    }

    public Vector normalize() {
        double len = length();
        //return new Vector(xyz.reduce(len));
        double x = xyz.d1/len;
        double y = xyz.d2/len;
        double z = xyz.d3/len;
        return new Vector(x,y,z);
    }

    public double dotProduct(Vector vector) {
        double x = vector.xyz.d1 * xyz.d1;
        double y = vector.xyz.d2 * xyz.d2;
        double z = vector.xyz.d3 * xyz.d3;
        return  x+y+z;
    }

    public Vector crossProduct(Vector u) {
        double x = xyz.d2*u.xyz.d3 -xyz.d3*u.xyz.d2;
        double y = xyz.d3* u.xyz.d1 - xyz.d1*u.xyz.d3;
        double z = xyz.d1* u.xyz.d2 - xyz.d2* u.xyz.d1;
        return new Vector(x,y,z);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj instanceof Vector other)
            return this.xyz.equals(other.xyz);
        return false;
    }

    @Override
    public String toString() {
        return super.toString();
    }


    public Vector add(Vector vector) {
        double x= vector.xyz.d1 + xyz.d1;
        double y= vector.xyz.d2 + xyz.d2;
        double z= vector.xyz.d3 + xyz.d3;
        return new Vector(x,y,z);
    }
    public Vector scale(double num)
    {
        return new Vector(xyz.d1*num, xyz.d2*num,xyz.d3*num);
    }

}
