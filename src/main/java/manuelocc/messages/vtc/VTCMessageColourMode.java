package manuelocc.messages.vtc;

import manuelocc.messages.Message;
import manuelocc.model.ColourCretors.ColourCreator;
import manuelocc.model.ColourManager;
import manuelocc.utils.MessageDecoder;

import javax.print.attribute.standard.NumberUp;
import java.awt.image.ColorModel;

public class VTCMessageColourMode extends Message {

    public ColourCreator colourCreator;

    public VTCMessageColourMode(String mode){
        colourCreator = ColourManager.getColourCreator(ColourManager.getIndexOf(mode));
        if (colourCreator == null) throw new NullPointerException();
    }

    @Override
    public void acceptVisitor(MessageDecoder decoder) {
        decoder.visit(this);
    }
}
