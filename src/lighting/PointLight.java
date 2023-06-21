package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;


/**
 * The PointLight class represents a point light source in a scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class PointLight extends Light implements LightSource {
    /**
     * The size of the plane that I generate vectors from (for soft shadow).
     */
    public double SIZE = 5;

    private Point position;
    private double Kc = 1;
    private double Kl = 0;
    private double Kq = 0;

    /**
     * Constructs a PointLight object with the given intensity and position.
     *
     * @param intensity the intensity of the light as a Color object.
     * @param position  the position of the light as a Point object.
     */
    public PointLight(Color intensity, Point position) {
        super(intensity);
        this.position = position;
    }

    /**
     * Sets the constant attenuation factor of the light.
     *
     * @param Kc the constant attenuation factor.
     * @return the PointLight object.
     */
    public PointLight setKc(double Kc) {
        this.Kc = Kc;
        return this;
    }

    /**
     * Sets the linear attenuation factor of the light.
     *
     * @param Kl the linear attenuation factor.
     * @return the PointLight object.
     */
    public PointLight setKl(double Kl) {
        this.Kl = Kl;
        return this;
    }

    /**
     * Sets the quadratic attenuation factor of the light.
     *
     * @param Kq the quadratic attenuation factor.
     * @return the PointLight object.
     */
    public PointLight setKq(double Kq) {
        this.Kq = Kq;
        return this;
    }

    @Override
    public Color getIntensity(Point point) {
        Color Ic = getIntensity();
        double distance = point.distance(position);
        double distancesquared = point.distanceSquared(position);

        double factor = (Kc + (Kl * distance)) + (Kq * (distancesquared));

        return Ic.reduce(factor);
    }

    @Override
    public Vector getL(Point point) {
        Vector s;
        try {
            s = point.subtract(position).normalize();
        } catch (IllegalArgumentException ex) {
            return null;
        }
        return s;
    }

    @Override
    public double getDistance(Point point) {
        return point.distance(position);
    }

    @Override
    public List<Vector> getLightVectors(Point p) {
        List<Vector> vectors = new LinkedList();
        //grid of vectors around the light
        for (double i = -SIZE; i < SIZE; i += SIZE / 10) {
            for (double j = -SIZE; j < SIZE; j += SIZE / 10) {
                if (i != 0 && j != 0) {
                    //create a point on the grid
                    Point point = position.add(new Vector(i, 0.1d, j));
                    if (point.equals(position)) {
                        //if the point is the same as the light position,
                        // add the vector from the point to the light
                        vectors.add(p.subtract(point).normalize());
                    } else {
                        try {
                            if (point.subtract(position).dotProduct(point.subtract(position))
                                    <= SIZE * SIZE) {
                                //if the point is in the radius of the light,
                                // add the vector from the point to the light
                                vectors.add(p.subtract(point).normalize());
                            }
                        } catch (Exception e) {
                            vectors.add(p.subtract(point).normalize());
                        }

                    }
                }
            }
        }
        vectors.add(getL(p));
        return vectors;
    }
}
