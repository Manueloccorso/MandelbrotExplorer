package manuelocc.messages.vtc;

import manuelocc.messages.Message;
import manuelocc.utils.MessageDecoder;

public class VTCMessageLoad extends Message {
    public String path;
    public VTCMessageLoad(String pathfile) {
        path = pathfile;
    }

    @Override
    public void acceptVisitor(MessageDecoder decoder) {
        decoder.visit(this);
    }
}
