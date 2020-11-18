package tw;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.*;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import javax.swing.JFrame;

public class MandelbrotSingleTask extends JFrame {

    private final int MAX_ITER = 5700;
    private final double ZOOM = 150;

    private final int THREADS = 10;

    private BufferedImage I;
    private double zx, zy, cX, cY, tmp;

    public MandelbrotSingleTask() throws Exception {
        super("Mandelbrot Set");
        setBounds(100, 100, 800, 600);
        setResizable(false);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        I = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);


        ExecutorService pool = Executors.newFixedThreadPool(THREADS);
        List<Future<List<Integer>>> list = new LinkedList<>();

        long startTime = System.nanoTime();

        for(int i=0; i<THREADS; i++) {
            Callable<List<Integer>> callable = new SingleTaskCallable(
                    MAX_ITER, getWidth(), i*getHeight()/THREADS,
                    (i+1)*getHeight()/THREADS, ZOOM);
            Future<List<Integer>> future = pool.submit(callable);
            list.add(future);
        }


        Iterator<Future<List<Integer>>> it = list.iterator();
        List<Integer> outputList = it.next().get();
        Iterator <Integer> listIterator = outputList.iterator();
        for (int y = 0; y < getHeight(); y++) {
            for (int x = 0; x < getWidth(); x++) {
                if(!listIterator.hasNext()) {
                    outputList = it.next().get();
                    listIterator = outputList.iterator();
                }
                int i = listIterator.next();
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
        new MandelbrotSingleTask().setVisible(true);
    }
}