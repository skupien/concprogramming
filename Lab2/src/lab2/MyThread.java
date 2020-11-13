package lab2;

public class MyThread implements Runnable{
    private Counter counter;
    boolean isIncrementing;
    BinarySemaphore semaphore;

    MyThread(Counter counter, boolean isIncrementing, BinarySemaphore semaphore) {
        this.counter = counter;
        this.isIncrementing = isIncrementing;
        this.semaphore = semaphore;
    }

    @Override
    public void run() {
        if(this.isIncrementing)
            for(int i=0; i<10000000; i++) {
                semaphore.acquire();
                counter.increase();
                semaphore.release();
            }
        else
            for(int i=0; i<10000000; i++) {
                semaphore.acquire();
                counter.decrease();
                semaphore.release();
            }
        System.out.println(counter.getValue());
    }
}
