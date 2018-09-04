package manuelocc.model;

import java.io.Serializable;


/**
 * Store the info of a position for Mandelbrot set.
 */
public class MandelbrotInfo implements Serializable {

    public double MinR;
    public double MaxR;
    public double MinI;
    public double MaxI;

    public int width;
    public int height;

    public int MaxIterations;

    public MandelbrotInfo(int width, int height, int MaxIterations,
                          double MinR, double MaxR, double MinI, double MaxI){
        this.width = width;
        this.height = height;
        this.MaxIterations = MaxIterations;
        this.MinR = MinR;
        this.MaxR = MaxR;
        this.MinI   = MinI;
        this.MaxI = MaxI;
    }

}

