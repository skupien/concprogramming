package lab2;


import java.util.Random;

import static java.lang.Thread.sleep;

public class ClientThread implements Runnable {
    CountingSemaphore semaphore;

    ClientThread (CountingSemaphore semaphore) {
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        semaphore.acquire();
        int time = new Random().nextInt(10)+1;
        System.out.println("Acquired cart, time in shop: " + time + " sec");
        try {
            sleep(time*1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("\tLeaving, time taken: " + time + " sec");
        semaphore.release();
    }

}
