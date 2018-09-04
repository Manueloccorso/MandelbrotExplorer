package manuelocc.messages.mtv;

import manuelocc.messages.Message;
import manuelocc.utils.MessageDecoder;

import java.awt.image.BufferedImage;

public class MTVMessageJuliaImage extends Message {

    public BufferedImage juliaImage;
    public double r, i;


    public MTVMessageJuliaImage(BufferedImage juliaImage, double r, double i) {
        this.juliaImage = juliaImage;
        this.r = r;
        this.i = i;
    }

    @Override
    public void acceptVisitor(MessageDecoder decoder) {
        decoder.visit(this);
    }
}
