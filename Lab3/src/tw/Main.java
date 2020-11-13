package tw;

import tw.task1.BoundedBuffer;
import tw.task1.Consumer;
import tw.task1.Producer;
import tw.task2.Printer;
import tw.task2.PrinterMonitor;
import tw.task3.KelnerMonitor;
import tw.task3.Person;

import static java.lang.Thread.sleep;

public class Main {
    public static final int ILOSC = 100;


    private static void presentBuffer() throws InterruptedException {
        BoundedBuffer buff = new BoundedBuffer();
        new Thread(new Producer(buff)).start();
        new Thread(new Consumer(buff)).start();
    }

    private static void presentPrinters(int n, int m) throws InterruptedException {
        PrinterMonitor monitor = new PrinterMonitor(m);
        for(int i = 0; i<n; i++) new Thread(new Printer(monitor, i)).start();
    }

    private static void presentKelner(int n) {
        KelnerMonitor kelner = new KelnerMonitor();
        for(int i=0; i<n; i++) {
            new Thread(new Person(kelner, i)).start();
            new Thread(new Person(kelner, i)).start();
        }
    }


    public static void main(String[] args) throws InterruptedException {
//        Main.presentBuffer();
//        presentPrinters(10,3);
        presentKelner(8);
    }
}
