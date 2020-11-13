package lab2;

import static java.lang.Thread.sleep;

public class Main {

    private static void presentThreads() throws InterruptedException {
        Counter counter = new Counter();
        BinarySemaphore semaphore = new BinarySemaphore();

        MyThread t = new MyThread(counter,true, semaphore);
        MyThread t2 = new MyThread(counter, false, semaphore);
        new Thread(t).start();
        new Thread(t2).start();
    }

    private static void presentShop() {
        Shop shop = new Shop(8,3);
        shop.runShop();
    }

    public static void main(String[] args) throws InterruptedException {
        //Main.presentThreads();
        Main.presentShop();
    }
}
