package manuelocc.model.ColourCretors;

import java.awt.*;


/**
 * Monochromatic Colouring:
 *  The colour can be choosen using the constructor:
 *      red: true, false, false
 *      green : false, true, false,
 *      ...
 *      yellow : true, true, false
 *      ...
 *      B&W: true, true, true
 */
public class MonochromaticColourCreator extends ColourCreator {

    int red, green, blue;
    public MonochromaticColourCreator(boolean r, boolean g, boolean b){
        if (r) red = 1; else red = 0;
        if (g) green = 1; else green = 0;
        if (b) blue = 1; else blue = 0;
    }

    @Override
    public Color getColour(int iter, int maxIter, double re, double im) {
        double mu = (double) iter / maxIter;
        int shade = Math.round((float)(mu*255));
        return new Color(shade*red, shade*green,shade*blue);
    }

    @Override
    public String toString() {
        return "Monochromatic Colour " +
                "r=" + red + ";g=" + green + ";b=" + blue;
    }
}
