package lighting;

import primitives.Color;
import primitives.Point;
import primitives.Vector;

import static primitives.Util.isZero;

/**
 * The SpotLight class represents a spot light source in a scene.
 * It extends the PointLight class.
 */
public class SpotLight extends PointLight {

     private Vector direction;
     private double NarrowBeam =1;

     /**
      * Constructs a SpotLight object with the given intensity, position, and direction.
      *
      * @param intensity  the intensity of the light as a Color object.
      * @param position   the position of the light as a Point object.
      * @param direction  the direction of the light as a Vector object.
      */
     public SpotLight(Color intensity, Point position, Vector direction) {
          super(intensity, position);
          this.direction = direction.normalize();
     }

     @Override
     public Color getIntensity(Point point) {
          Color Ic = super.getIntensity(point);
          double projection = getL(point).dotProduct(direction);

          if (isZero(projection)) {
               return Color.BLACK;
          }

          double factor = Math.max(0, projection);
          if (NarrowBeam!=1)
          {
               factor = Math.pow(factor, NarrowBeam);
          }
          return Ic.scale(factor);
     }

     public SpotLight setNarrowBeam(double narrowBeam) {
          NarrowBeam = narrowBeam;
          return this;
     }
}
