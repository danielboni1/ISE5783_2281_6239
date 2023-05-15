package lighting;

import primitives.Color;
import primitives.Double3;

public class AmbientLight {
    Color intensity;

    public static AmbientLight NONE = new AmbientLight(Color.BLACK, Double3.ZERO);

    public AmbientLight(Color IA, Double3 KA) {
        intensity = IA.scale(KA);
    }

    public AmbientLight(Color IA, Double KA) {
        intensity = IA.scale(KA);
    }

    public static AmbientLight getNONE() {
        return NONE;
    }

   public Color getIntensity()
    {
        return intensity;
    }
}
