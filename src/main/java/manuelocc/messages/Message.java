package manuelocc.messages;

import manuelocc.utils.MessageDecoder;

public abstract class Message {
    public abstract void acceptVisitor(MessageDecoder decoder);
}
