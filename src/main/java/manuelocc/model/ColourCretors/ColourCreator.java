package manuelocc.model.ColourCretors;

import java.awt.*;
import java.io.Serializable;

/**
 * Abstract class for a ColourCreator
 */
public abstract class ColourCreator implements Serializable {

    abstract public Color getColour(int iter, int maxIter, double re, double im);

    @Override
    public boolean equals(Object o){
        if(o == null) return false;
        if(this.getClass() != o.getClass()) return false;
        ColourCreator c = (ColourCreator) o;
        if(this.toString().equals(c.toString())) return true;
        return false;
    }
}
