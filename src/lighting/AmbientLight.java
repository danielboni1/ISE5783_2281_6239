package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight extends Light{
   //protected final Color intensity;

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    public AmbientLight(Color IA, Double3 KA) {
        super(IA.scale(KA));
    }
    public AmbientLight(Color intensity) {
        super(Color.BLACK);
    }


    public static AmbientLight getNONE() {
        return NONE;
    }

   public Color getIntensity()
    {
        return intensity;
    }
}
