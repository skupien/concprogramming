package tw;

import javafx.util.Pair;

import java.util.concurrent.Callable;

public class PixelCallable implements Callable {

    private int iter;
    private int x;
    private int y;
    private double zoom;

    public PixelCallable(int iter, int x, int y, double zoom) {
        this.iter = iter;
        this.x = x;
        this.y = y;
        this.zoom = zoom;
    }

    @Override
    public Integer call() throws Exception {
        double zx = 0;
        double zy = 0;
        double cX = (x - 400) / zoom;
        double cY = (y - 300) / zoom;
        while (zx * zx + zy * zy < 4 && iter > 0) {
            double tmp = zx * zx - zy * zy + cX;
            zy = 2.0 * zx * zy + cY;
            zx = tmp;
            iter--;
        }
        return iter;
    }
}
