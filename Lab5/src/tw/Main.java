package tw;


import java.util.List;

public class Main {
    public static final int MAX_ITER = 570 * 64;
    public static final int THREADS = 8;
    public static final int TASKS = 10;

    public static void main(String[] args) throws Exception {
        System.out.println("\tSingle:");
        List<Long> single = MandelbrotSingleTask.main();
        System.out.println("\tMany:");
        List<Long> many = MandelbrotManyTask.main();
        System.out.println("\tPixel:");
        List<Long> pixels = MandelbrotPixelTask.main();

        System.out.println("   [Avg, SD, Nums...] \t MAX_ITER = " + MAX_ITER + "\t THREADS = " + THREADS);
        System.out.print("S: ");
        System.out.println(single.toString());
        System.out.print("M: ");
        System.out.println(many.toString());
        System.out.print("P: ");
        System.out.println(pixels.toString());
    }
}