package manuelocc.messages.vtc;

import manuelocc.messages.Message;
import manuelocc.utils.MessageDecoder;

public class VTCMessageJulia extends Message {
    public boolean juliaActive;
    public VTCMessageJulia(boolean selected) {
        juliaActive = selected;
    }

    @Override
    public void acceptVisitor(MessageDecoder decoder) {
        decoder.visit(this);
    }
}
