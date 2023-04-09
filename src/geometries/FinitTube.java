package geometries;

import primitives.Ray;
import primitives.Point;
import primitives.Ray;
import primitives.Vector;
public class FinitTube extends Tube{
    Double height;

    public FinitTube(double radius, Ray axisRay, Double height) {
        super(radius, axisRay);
        this.height = height;
    }

    public Double getHeight() {
        return height;
    }
    @Override
    public Vector getNormal(Point point) {
        return null;
    }
}
