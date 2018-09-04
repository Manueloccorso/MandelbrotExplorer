package manuelocc.model.ColourCretors;

import java.awt.*;


/**
 * Basic Couloring:
 * a default palette and not smoothed.
 */
public class BasicColourCreator extends ColourCreator {

    Color[] palette;

    public BasicColourCreator(){
        setDefaultPalette();
    }

    public BasicColourCreator(Color[] palette){
        if(palette == null || palette.length == 0) throw new NullPointerException();
        this.palette = palette;
    }

    @Override
    public Color getColour(int iter,  int maxIter, double re, double im) {
        if(iter < maxIter) {
            int i = iter % palette.length;
            return palette[i];
        }else return Color.BLACK;
    }

    public void setPalette(Color[] palette){
        if(palette == null || palette.length == 0) throw new NullPointerException();
        this.palette = palette;
    }

    public void setDefaultPalette(){
        palette = new Color[16];
        palette[0] = new Color(66, 30, 15);
        palette[1] = new Color(25, 7, 26);
        palette[2] = new Color(9, 1, 47);
        palette[3] = new Color(4, 4, 73);
        palette[4] = new Color(0, 7, 100);
        palette[5] = new Color(12, 44, 138);
        palette[6] = new Color(24, 82, 177);
        palette[7] = new Color(57, 125, 209);
        palette[8] = new Color(134, 181, 229);
        palette[9] = new Color(211, 236, 248);
        palette[10] = new Color(241, 233, 191);
        palette[11] = new Color(248, 201, 95);
        palette[12] = new Color(255, 170, 0);
        palette[13] = new Color(204, 128, 0);
        palette[14] = new Color(153, 87, 0);
        palette[15] = new Color(106, 52, 3);
    }


    @Override
    public String toString() {
        Color[] old_p = palette;
        setDefaultPalette();
        String ending;
        if(old_p.equals(palette)) ending = "(default)";
        else ending = "(custom)";
        palette = old_p;
        return "Basic Colour "+ ending;
    }
}
