package manuelocc.model;

import manuelocc.controller.StorageManager;
import manuelocc.messages.Message;
import manuelocc.messages.mtv.MTVMessageJuliaImage;
import manuelocc.messages.mtv.MTVMessageImage;
import manuelocc.model.ColourCretors.BasicColourCreator;
import manuelocc.model.ColourCretors.ColourCreator;
import manuelocc.model.ColourCretors.SmoothedColourCreator;
import manuelocc.utils.Observable;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.time.Duration;
import java.time.Instant;


/**
 * Class to generates a Mandelbrot set Image related to a position (MandelbrotInfo)
 */
public class Mandelbrot extends Observable<Message> {

    private MandelbrotInfo info;
    private BufferedImage mandelbrotImage;

    private ColourCreator colourCreator;

    /**
     * Thread to generates a portion of an image
     */
    private class MandelThread implements Runnable{

        int minY;
        int maxY;
        boolean finished;

        /**
         * Set the limits of the portion of image
         */
        public MandelThread(int minY, int maxY){
            this.minY = minY;
            this.maxY = maxY;
            finished = false;
        }


        /**
         * Generates the portion of image
         */
        @Override
        public void run() {
            double zx, zy, cX, cY, tmp;
            for (int y = minY; y < maxY; y++) {
                for (int x = 0; x < getWidth(); x++) {
                    zx = zy = 0;
                    cX = pixelsToReal(x);
                    cY = pixelsToImag(y);
                    int iter = 0;
                    while ( zx * zx + zy * zy < 4 && iter < getMaxIterations()) {
                        tmp = zx * zx - zy * zy + cX;
                        zy = 2.0 * zx * zy + cY;
                        zx = tmp;
                        iter++;
                    }
                    mandelbrotImage.setRGB(x, y, (getColour(iter, getMaxIterations(), zx, zy)).getRGB());
                }
            }
            finished = true;
        }
    }

    /**
     * Create a Mandelbrot object based on the mandelbrotInfo
     * @param info
     */
    public Mandelbrot(MandelbrotInfo info){
        this.info = info;
        colourCreator = new BasicColourCreator();
    }

    /**
     * Create a Mandelbrot object in default position
     */
    public Mandelbrot(int width, int height) {
        info = new MandelbrotInfo(width,height,100,
                                    -2.5,1.5,-2,2);
        colourCreator = new SmoothedColourCreator();
    }


    /**
     * Create a new Image from the set parameter and notify the observer
     * @deprecated
     */
    public void refresh_old(){
        Instant earlier = Instant.now() ;  // Capture the current moment in UTC.
        mandelbrotImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        double zx, zy, cX, cY, tmp;
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                zx = zy = 0;
                cX = pixelsToReal(x);
                cY = pixelsToImag(y);
                int iter = 0;
                while ( zx * zx + zy * zy < 4 && iter < getMaxIterations()) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter++;
                }
                mandelbrotImage.setRGB(x, y, (getColour(iter, getMaxIterations(), zx, zy)).getRGB());

            }
        }
        Instant later = Instant.now() ;
        Duration d = Duration.between( earlier , later ) ;
        System.out.println(d.toString());
        this.notify(new MTVMessageImage(mandelbrotImage));
    }

    /**
     * Create a new Image from the set parameter and notify the observer
     */
    public void refresh(){
        Instant earlier = Instant.now() ;  // Capture the current moment in UTC.

        mandelbrotImage = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        int y1, y2, y3, y4;
        y1 = getHeight() / 4;
        y2 = y1*2;
        y3 = y1*3;
        y4 = getHeight();

        MandelThread mt1 = new MandelThread(0,y1);
        MandelThread mt2 = new MandelThread(y1,y2);
        MandelThread mt3 = new MandelThread(y2,y3);
        MandelThread mt4 = new MandelThread(y3,y4);
        Thread t1 = new Thread(mt1);
        Thread t2 = new Thread(mt2);
        Thread t3 = new Thread(mt3);
        Thread t4 = new Thread(mt4);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            t4.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant later = Instant.now() ;
        Duration d = Duration.between( earlier , later ) ;
        System.out.println(d.toString());
        this.notify(new MTVMessageImage(mandelbrotImage));
    }

    /**
     * Evaluate Julia's set on the selected pixel
     */
    public void evaluateJuliaSet(double x, double y){
        Julia julia = new Julia(this, x,y);
        BufferedImage juliaImage = julia.getJuliaImage();
        this.notify(new MTVMessageJuliaImage(juliaImage, pixelsToReal(x), pixelsToImag(y)));
    }

    /**
     * get a colour from the number of iterations
     * @param iter
     * @return Color for the iteration
     */
    public Color getColour(int iter, int maxIter, double re, double im){
        if (iter == 0) return Color.BLACK;
        return colourCreator.getColour(iter, maxIter, re, im);
    }

    /**
     * @param x the pixel analysed
     * @return the r value related to the pixel
     */
    public double pixelsToReal(double x){
        return  info.MinR + x*(info.MaxR - info.MinR)/ info.width;
    }

    /**
     * @param y the pixel analysed
     * @return the i value related to the pixel
     */
    public double pixelsToImag(double y) {
        return info.MinI + (y) * (info.MaxI - info.MinI) / info.height;
    }

    @Override
    public String toString() {
        String delim = StorageManager.delimInfo;
        return getWidth() + delim + getHeight() + delim + getMaxR() + delim + getMaxI() + delim + getMaxIterations();
    }

    //--------------------------------------- SETTER ---------------------------------------------------------

    public void setMinR(double minR) {
        info.MinR = minR;
    }

    public void setMaxR(double maxR) {
        info.MaxR = maxR;
    }

    public void setMinI(double minI) {
        info.MinI = minI;
    }

    public void setMaxI(double maxI) {
        info.MaxI = maxI;
    }

    public void setWidth(int width) {
        info.width = width;
    }

    public void setHeight(int height) {
        info.height = height;
    }

    public void setMaxIterations(int maxIterations) {
        info.MaxIterations = maxIterations;
    }


    public void setColourCreator(ColourCreator colourCreator) {
        this.colourCreator = colourCreator;
        refresh();
    }


    // -------------------------------------- GETTER ---------------------------------------------------------


    public MandelbrotInfo getInfo() {
        return info;
    }

    public int getWidth() {
        return info.width;
    }

    public int getHeight(){
        return info.height;
    }

    public double getMinR() {
        return info.MinR;
    }

    public double getMaxR() {
        return info.MaxR;
    }

    public double getMinI() {
        return info.MinI;
    }

    public double getMaxI() {
        return info.MaxI;
    }

    public int getMaxIterations() {
        return info.MaxIterations;
    }

    public BufferedImage getMandelbrotImage() {
        return mandelbrotImage;
    }

    public ColourCreator getColourCreator() {
        return colourCreator;
    }
}
