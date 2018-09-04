package manuelocc.messages.vtc;

import manuelocc.messages.Message;
import manuelocc.utils.MessageDecoder;

public class VTCMessageClick extends Message {

    public double x, y;
    public Mode mode;

    public VTCMessageClick(double x, double y, Mode mode){
        this.x = x;
        this.y = y;
        this.mode = mode;
    }

    @Override
    public void acceptVisitor(MessageDecoder decoder) {
        decoder.visit(this);
    }

    public enum Mode { Right, Left}
}
