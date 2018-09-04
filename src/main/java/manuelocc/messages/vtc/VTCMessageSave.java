package manuelocc.messages.vtc;

import manuelocc.messages.Message;
import manuelocc.utils.MessageDecoder;

public class VTCMessageSave extends Message {

    public String path;
    public boolean multicolour, locinfo, zip;

    public VTCMessageSave(String path, boolean multic, boolean locinfo, boolean zip) {
        this.path = path;
        multicolour = multic;
        this.locinfo = locinfo;
        this.zip = zip;
    }

    @Override
    public void acceptVisitor(MessageDecoder decoder) {
        decoder.visit(this);
    }
}
