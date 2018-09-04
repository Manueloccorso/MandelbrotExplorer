package manuelocc.model.ColourCretors;

import manuelocc.model.ColourCretors.ColourCreator;

import java.awt.*;


/**
 * Smooth Colouring with a default palette
 */
public class SmoothedColourCreator extends ColourCreator {
    Color[] colors = new Color[768];

    /**
     * default constructor: creates a default gradient array
     */
    public SmoothedColourCreator(){
        for (int i=0;i<768;i++)
        {
            int colorValueR=0;
            int colorValueG=0;
            int colorValueB=0;
            if (i >= 512)
            {
                colorValueR = i - 512;
                colorValueG = 255 - colorValueR;
            }
            else if (i >= 256)
            {
                colorValueG = i - 256;
                colorValueB = 255 - colorValueG;
            }
            else
            {
                colorValueB = i;
            }
            colors[i] = new Color(colorValueR, colorValueG, colorValueB);
        }
    }

    @Override
    public Color getColour(int iter, int maxIter, double re, double im) {
        int colorIndex;
        if (iter < maxIter){
            double mu = iter - (Math.log(Math.log(Math.sqrt(re*re + im*im)))) / Math.log(2);
             colorIndex= (int)(mu / maxIter * 768);
            if (colorIndex >= 768) colorIndex = 0;
            if (colorIndex < 0) colorIndex = 0;
            return colors[colorIndex];
        }else{
            return Color.BLACK;
        }
    }

    @Override
    public String toString() {
        return "Smoothed Colour";
    }
}
