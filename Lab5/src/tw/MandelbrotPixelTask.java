package tw;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFrame;

public class MandelbrotPixelTask extends JFrame {

    private final int MAX_ITER = 5700;
    private final double ZOOM = 150;
    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;

    public MandelbrotPixelTask() throws Exception {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        ExecutorService pool = Executors.newFixedThreadPool(10);
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
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(I, 0, 0, this);
    }

    public static void main(String[] args) throws Exception {
        new MandelbrotPixelTask().setVisible(true);
    }
}