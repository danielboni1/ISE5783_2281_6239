package geometries;

import primitives.Vector;

import java.awt.*;

public class Sphere extends RadialGeometry{
    final Point center;
    final Double radios;

    public Sphere(double radius, Point center, Double radios) {
        super(radius);
        this.center = center;
        this.radios = radios;
    }

    public Point getCenter() {
        return center;
    }

    public Double getRadios() {
        return radios;
    }

    @Override
    public Vector getNormal(primitives.Point point) {
        return null;
    }
}
