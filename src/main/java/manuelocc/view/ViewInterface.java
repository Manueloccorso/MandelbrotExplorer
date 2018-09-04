package manuelocc.view;

import manuelocc.messages.Message;
import manuelocc.messages.mtv.MTVMessageImage;
import manuelocc.utils.Observer;

import java.awt.image.BufferedImage;


public interface ViewInterface extends Observer<Message>{

    void refreshCanvas(BufferedImage image);

    int getCanvasWidth();
    int getCanvasHeight();
}
