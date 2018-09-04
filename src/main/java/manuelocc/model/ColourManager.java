package manuelocc.model;

import manuelocc.model.ColourCretors.BasicColourCreator;
import manuelocc.model.ColourCretors.ColourCreator;
import manuelocc.model.ColourCretors.MonochromaticColourCreator;
import manuelocc.model.ColourCretors.SmoothedColourCreator;

/**
 * Manage all the possibile kind of colouring, giving them an order and making them accessibile statically.
 */
public class ColourManager {

    /**
     * List of all the Colour Creators
     */
    public static ColourCreator[] ColourCreators = {
            new BasicColourCreator(),
            new MonochromaticColourCreator(true, false, false),
            new MonochromaticColourCreator(false, true, false),
            new MonochromaticColourCreator(false, false, true),
            new MonochromaticColourCreator( true, true, false),
            new MonochromaticColourCreator(false, true, true),
            new MonochromaticColourCreator(true, false, true),
            new MonochromaticColourCreator(true, true, true),
            new SmoothedColourCreator()
    };

    /**
     * get the index of a colourCreator
     */
    public static int getIndexOf(ColourCreator c){
        int i = 0;
        for(ColourCreator cc : ColourCreators){
            if(cc.equals(c)) return i;
            i++;
        }
        return -1;
    }

    /**
     * get the index of a colourCreator based on his ID (.toString)
     */
    public static int getIndexOf(String c){
        int i = 0;
        for(ColourCreator cc : ColourCreators){
            if(cc.toString().equals(c)) return i;
            i++;
        }
        return -1;
    }

    /**
     * Get a Colour Creator by index
     */
    public static ColourCreator getColourCreator(int index){
        if(index < ColourCreators.length)
            return ColourCreators[index];
        else return null;
    }
}
