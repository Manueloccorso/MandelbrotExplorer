package manuelocc.messages.vtc;

import manuelocc.messages.Message;
import manuelocc.utils.MessageDecoder;

public class VTCMessageIterations extends Message {
    public int iterations;
    public VTCMessageIterations(int iter) {
        iterations = iter;
    }

    @Override
    public void acceptVisitor(MessageDecoder decoder) {
        decoder.visit(this);
    }
}
