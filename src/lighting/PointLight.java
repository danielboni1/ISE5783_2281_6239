package lighting;

import geometries.Plane;
import primitives.Color;
import primitives.Point;
import primitives.Vector;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;


/**
 * The PointLight class represents a point light source in a scene.
 * It extends the Light class and implements the LightSource interface.
 */
public class PointLight extends Light implements LightSource {
    /**
     * The size of the plane that I generate vectors from (for soft shadow).
     */
    public double SIZE = 4;
    private final Random rand = new Random();
    private Point position;
    private double Kc = 1;
    private double Kl = 0;
    private double Kq = 0;
    private List<Point> points = null;

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

  /*  @Override
    public List<Vector> getLightVectors(Point p) {
        List<Vector> vectors = new LinkedList();
        //grid of vectors around the light
        for (double z = -SIZE; z < SIZE; z ++)
        {
        for (double i = -SIZE; i < SIZE; i ++) {
            for (double j = -SIZE; j < SIZE; j ++) {
                if (i != 0 && j != 0 && z!=0) {
                    //create a point on the grid
                    Point point = position.add(new Vector(i, z, j));
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
        }}
        vectors.add(getL(p));
        return vectors;
    }*/
    /**
     * Get the array of points that will cast shadow rays
     *
     * @return The array of point
     */
    public List<Point> getPoints(Point p, int numOfPoints) {
        if (SIZE == 0) return null;
        if (this.points != null)
            return this.points;
        Point[] points = new Point[numOfPoints];
        Vector to = p.subtract(position).normalize();
        Vector vX = to.getOrthogonal().normalize();
        Vector vY = vX.crossProduct(to).normalize();
        double x, y, radius;
        for (int i = 0; i < numOfPoints; i += 4) {
            radius = rand.nextDouble(SIZE) + 0.1;
            x = rand.nextDouble(radius) + 0.1;
            y = radius * radius - x * x;//getCircleScale(x, radius);
            for (int j = 0; j < 4; j++) {
                //in this part we mirror the point we got 4 times, to each quarter of the grid
                points[i + j]= position.add(vX.scale(j % 2 == 0 ? x : -x)).add(vY.scale((j <= 1 ? -y : y)));
            }
        }
        List<Point> np = new LinkedList<>();
        for (int i = 0; i<points.length; i++){
            np.add(points[i]);
        }
        this.points = np;
        return np;
    }
    @Override
    public List<Vector> getLightVectors(Point p) {
        List<Vector> lstVec = new LinkedList<>();
        if(points == null){
            getPoints(p, 1000);
        }
        for (var po: points) {
            lstVec.add(p.subtract(po).normalize());
        }
        return lstVec;
    }
}
