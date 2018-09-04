package manuelocc.messages.mtv;

import manuelocc.messages.Message;
import manuelocc.utils.MessageDecoder;

import java.awt.image.BufferedImage;

public class MTVMessageImage extends Message {
    private BufferedImage image;

    public MTVMessageImage(BufferedImage image){
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    @Override
    public void acceptVisitor(MessageDecoder decoder) {
        decoder.visit(this);
    }
}
