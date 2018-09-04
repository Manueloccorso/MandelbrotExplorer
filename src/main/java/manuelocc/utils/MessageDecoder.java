package manuelocc.utils;

import manuelocc.messages.mtv.MTVMessageJuliaImage;
import manuelocc.messages.mtv.MTVMessageImage;
import manuelocc.messages.vtc.*;


/**
 * Interface to realize Visitor Pattern
 */
public interface MessageDecoder {

    default void visit(MTVMessageImage m){ throw new IllegalStateException(); }

    default void visit(VTCMessageClick m){ throw new IllegalStateException(); }

    default void visit(VTCMessageIterations vtcMessageIterations){ throw new IllegalStateException(); }

    default void visit(VTCMessageZoom vtcMessageZoom){ throw new IllegalStateException(); }

    default void visit(MTVMessageJuliaImage MTVMessageJuliaImage){ throw new IllegalStateException(); }

    default void visit(VTCMessageJulia vtcMessageJulia){ throw new IllegalStateException(); }

    default void visit(VTCMessageColourMode vtcMessageColourMode){ throw new IllegalStateException(); }

    default void visit(VTCMessageSave vtcMessageSave){ throw new IllegalStateException(); }

    default void visit(VTCMessageLoad vtcMessageLoad){ throw new IllegalStateException(); }
}
