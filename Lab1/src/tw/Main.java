package tw;

import static java.lang.Thread.sleep;

public class Main {
    public static final int ILOSC = 10;

    private static void presentThreads() throws InterruptedException {
        MyThread t = new MyThread(true);
        MyThread t2 = new MyThread(false);
        new Thread(t).start();
        new Thread(t2).start();

        sleep(100);

        Object lock = new Object();
        MyThreadSynchronized ts = new MyThreadSynchronized(lock,true);
        MyThreadSynchronized ts2 = new MyThreadSynchronized(lock,false);
        new Thread(ts).start();
        new Thread(ts2).start();
    }

    private static void presentBuffer() throws InterruptedException {
        Buffer buff = new Buffer();
        new Thread(new Producer(buff)).start();
        new Thread(new Consumer(buff)).start();
    }


    public static void main(String[] args) throws InterruptedException {
        //Main.presentThreads();
        Main.presentBuffer();
    }
}
