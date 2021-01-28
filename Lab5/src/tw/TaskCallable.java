package tw;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

public class TaskCallable implements Callable {

    private int maxIter;
    private int width;
    private int yMin;
    private int yMax;
    private double zoom;

    private List<Integer> iterList;

    public TaskCallable(int max_iter, int width, int yMin, int yMax, double zoom) {
        this.maxIter = max_iter;
        this.width = width;
        this.yMin = yMin;
        this.yMax = yMax;
        this.zoom = zoom;
        this.iterList = new LinkedList<>();
    }

    @Override
    public List<Integer> call() throws Exception {
        for (int y = yMin; y < yMax; y++) {
            for (int x = 0; x < this.width; x++) {
                double zx = 0;
                double zy = 0;
                double cX = (x - 400) / zoom;
                double cY = (y - 300) / zoom;
                int iter = maxIter;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }
                iterList.add(iter);
            }
        }
        return iterList;
    }
}
