package manuelocc.model;

import java.awt.image.BufferedImage;

/**
 * Class to generate Julia's Set
 */
public class Julia {

    private BufferedImage julia;

    public Julia(Mandelbrot mandelbrot, double xx, double yy){
        double r, i;
        r = mandelbrot.pixelsToReal(xx);
        i = mandelbrot.pixelsToImag(yy);
        julia = new BufferedImage(mandelbrot.getWidth(), mandelbrot.getHeight(), BufferedImage.TYPE_INT_RGB);
        double newRe, newIm, tmp;
        for (int y = 0; y < mandelbrot.getHeight(); y++) {
            for (int x = 0; x < mandelbrot.getWidth(); x++) {
                newRe = mandelbrot.pixelsToReal(x);
                newIm = mandelbrot.pixelsToImag(y);
                int iter = mandelbrot.getMaxIterations();
                while (newRe * newRe + newIm * newIm < 4 && iter > 0) {
                    tmp = newRe * newRe - newIm * newIm + r;
                    newIm = 2.0 * newRe * newIm + i;
                    newRe = tmp;
                    iter--;
                }
                julia.setRGB(x, y, mandelbrot.getColour(iter, mandelbrot.getMaxIterations(), newRe, newIm).getRGB());
            }
        }
    }

    public BufferedImage getJuliaImage() {
        return julia;
    }
}

