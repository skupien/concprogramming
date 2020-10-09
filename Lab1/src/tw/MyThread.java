package tw;

public class MyThread implements Runnable {
    public static int num = 0;
    boolean isIncrementing;

    MyThread(boolean isIncrementing) {
        this.isIncrementing = isIncrementing;
    }

    public void run() {
        if(this.isIncrementing)
            for(int i=0; i<100000000; i++)
                this.increment();
        else
            for(int i=0; i<100000000; i++)
                this.decrement();

        System.out.println(num);
    }

    void increment() {
        num++;
    }

    void decrement() {
        num--;
    }

}
