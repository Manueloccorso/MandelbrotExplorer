package manuelocc.controller;

import manuelocc.model.Mandelbrot;

/**
 * Class to manage the zooming of a Mandelbrot class
 */
public class Zoomer {
    double zoom;

    /**
     * default constructor:
     *  zoom higher than 1 : zoomin
     *  zoom lower than 1 : zoomout
     */
    public Zoomer(double zoom){
        this.zoom = zoom;
    }

    /**
     * zoom the mandelbrot position in the new center x, y
     * @param mandelbrot to be zoomed
     * @param x of the new center
     * @param y of the new center
     */
    public void zoom(Mandelbrot mandelbrot, double x, double y){
        double widthR = mandelbrot.getMaxR() - mandelbrot.getMinR();
        double posX = mandelbrot.pixelsToReal(x);

        double nminR = posX - (widthR/2)/zoom;
        mandelbrot.setMinR(nminR);

        double nmaxR = posX + (widthR/2)/zoom;
        mandelbrot.setMaxR(nmaxR   );


        double heightI = mandelbrot.getMaxI() - mandelbrot.getMinI();
        double posY = mandelbrot.pixelsToImag(y);

        double nminI = posY - (heightI/2)/zoom;
        mandelbrot.setMinI(nminI);


        double nmaxI = posY + (heightI/2)/zoom;
        mandelbrot.setMaxI(nmaxI   );
    }

}
