package manuelocc.controller;

import manuelocc.messages.Message;
import manuelocc.messages.vtc.*;
import manuelocc.model.ColourCretors.ColourCreator;
import manuelocc.model.ColourManager;
import manuelocc.model.Mandelbrot;
import manuelocc.utils.MessageDecoder;
import manuelocc.utils.Observer;
import manuelocc.view.GUI;


/**
 * Main Controller:
 *      Manage clicks on the mandelbrot canvas, manage mode (zooming, Julia, etc)
 *      Manage Saving and Loading
 *      Manage Colour mode selection
 *
 * @author Manuel Occorso
 */
public class MainController
            implements Observer<Message>, MessageDecoder {

    //Mandelbrot to manage
    Mandelbrot mandelbrot;

    //View
    GUI gui;

    //Zoom multiplier
    double zoom;

    //dim of the canvas
    int width, height;

    //Mode
    private boolean activeJulia;


    /**
     * Main constructor:
     *      zoom = 1.5
     *      zooming mode
     * @param view to control
     */
    public MainController(GUI view){
        this.zoom = 1.5;
        width = view.getCanvasWidth();
        height = view.getCanvasHeight();
        gui = view;
        mandelbrot = new Mandelbrot(width, height);
        mandelbrot.addObserver(view);
        mandelbrot.refresh();
    }

    //--------------------------------MESSAGES------------------------------------------------------

    /**
     * Get a message from the View, start decoding
     * @param message container of the informations to be managed.
     */
    @Override
    public void update(Message message) {
        message.acceptVisitor(this);
    }

    /**
     * manage a click on the canvas:
     *  if the mode is set to Julia, generates an image of the Julias set for z
     *  if the mode is in zooming, zoom to z (left zoomin, right zoomout)
     * @param message contains x and y of the clicked pixel (related to a point z)
     */
    @Override
    public void visit(VTCMessageClick message) {
        if(activeJulia) {
            //generates julia's set
            mandelbrot.evaluateJuliaSet(message.x, message.y);
        }else {
            //zoom
            Zoomer z;
            if(message.mode == VTCMessageClick.Mode.Left) { //zoomin
                z = new Zoomer(zoom);
            }else{                                          //zoomout
                z = new Zoomer(1/zoom);
            }
            z.zoom(mandelbrot, message.x, message.y);
            mandelbrot.refresh();
        }
    }

    /**
     * change the maximum iteration
     * @param message
     */
    @Override
    public void visit(VTCMessageIterations message) {
        mandelbrot.setMaxIterations(message.iterations);
        mandelbrot.refresh();
    }

    /**
     * change the zoom ( >1 : zoom in ; <1 : zoom out)
     * @param vtcMessageZoom
     */
    @Override
    public void visit(VTCMessageZoom vtcMessageZoom) {
        zoom = vtcMessageZoom.zoom;
    }

    /**
     * activate Julias Set Mode:
     *  on click, will generate the julias set for the selected point, instead of zooming
     */
    @Override
    public void visit(VTCMessageJulia vtcMessageJulia) {
        activeJulia = vtcMessageJulia.juliaActive;
    }

    /**
     * set the colour mode
     * @param vtcMessageColourMode
     */
    @Override
    public void visit(VTCMessageColourMode vtcMessageColourMode) {
        mandelbrot.setColourCreator(vtcMessageColourMode.colourCreator);
    }

    /**
     * Save the Mandelbrot Image
     * @param vtcMessageSave
     */
    @Override
    public void visit(VTCMessageSave vtcMessageSave) {
        if(vtcMessageSave.zip){
            StorageManager.saveZipToDisk(vtcMessageSave.path, mandelbrot);
        }else {
            if (!vtcMessageSave.multicolour) {
                StorageManager.saveImageToDisk(vtcMessageSave.path, mandelbrot.getMandelbrotImage());
            } else {
                ColourCreator oldC = mandelbrot.getColourCreator();
                for (ColourCreator c : ColourManager.ColourCreators) {
                    mandelbrot.setColourCreator(c);
                    StorageManager.saveImageToDisk(vtcMessageSave.path, mandelbrot.getMandelbrotImage());
                }
            }
            if (vtcMessageSave.locinfo) {
                StorageManager.saveInfoToDisk(vtcMessageSave.path, mandelbrot);
            }
        }
    }

    /**
     * Load mandelbrot position froma  previously saved info file
     * @param vtcMessageLoad
     */
    @Override
    public void visit(VTCMessageLoad vtcMessageLoad) {
        mandelbrot = StorageManager.loadInfoFromDisk(vtcMessageLoad.path);
        mandelbrot.addObserver(gui);
        mandelbrot.refresh();

    }

    //------------------------------------ ACCESSIBILITY AND UTILITY ---------------------------------


    public double pixelsToReal(double x) {
        return mandelbrot.pixelsToReal(x);
    }

    public double pixelsToImag(double y) {
        return mandelbrot.pixelsToImag(y);
    }
}
