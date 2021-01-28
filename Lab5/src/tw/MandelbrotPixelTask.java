package tw;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFrame;

import static java.lang.Math.sqrt;

public class MandelbrotPixelTask extends JFrame {

    private final int MAX_ITER = Main.MAX_ITER;
    private final int THREADS = Main.THREADS;
    private final double ZOOM = 150;
    private BufferedImage I;

    public MandelbrotPixelTask(List<Long> values) throws Exception {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        List<Future<Integer>> list = new LinkedList<>();

        long startTime = System.nanoTime();

        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                Callable<Integer> callable = new PixelCallable(MAX_ITER, x, y, ZOOM);
                Future<Integer> future = pool.submit(callable);
                list.add(future);
            }
        }
        Iterator<Future<Integer>> it = list.iterator();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                int i = it.next().get();
                I.setRGB(x, y, i | (i << 8));
            }
        }

        long stopTime = System.nanoTime();

        System.out.println((stopTime - startTime)/1000000 + "ms");
        values.add((stopTime - startTime)/1000000);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static List<Long> main() throws Exception {
        List<Long> values = new LinkedList<>();
        for(int i =0; i<12; i++) {
            if (i == 2) System.out.println("  breakpoint");
            new MandelbrotPixelTask(values).setVisible(true);
        }
        long avg = (long) values.stream().skip(2).mapToDouble(a->a).average().getAsDouble();
        double stdDeviation = sqrt(values.stream().skip(2).mapToDouble(a->(a-avg)*(a-avg)).sum() / ((double)values.size()-2.0));
        values.set(0, avg);
        values.set(1, (long)stdDeviation);
        return values;
    }
}