package manuelocc.messages.vtc;

import manuelocc.messages.Message;
import manuelocc.utils.MessageDecoder;

public class VTCMessageZoom extends Message {
    public double zoom;
    public VTCMessageZoom(double zoom) {
        this.zoom = zoom;
    }

    @Override
    public void acceptVisitor(MessageDecoder decoder) {
        decoder.visit(this);
    }
}
