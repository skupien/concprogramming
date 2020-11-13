package tw;

public class MyThreadSynchronized implements Runnable {
    public static int num = 0;
    boolean isIncrementing;

    Object lock;

    MyThreadSynchronized(Object lock, boolean isIncrementing) {
        this.lock = lock;
        this.isIncrementing = isIncrementing;
    }

    @Override
    public void run() {
        synchronized (lock) {
            if (this.isIncrementing)
                for (int i = 0; i < 100000000; i++)
                    this.increment();
            else
                for (int i = 0; i < 100000000; i++)
                    this.decrement();
        }

        System.out.print("Synchronized: " + num + "\n");
    }

    void increment() {
        num++;
    }

    void decrement() {
        num--;
    }

}
