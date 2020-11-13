package lab2;

public class BinarySemaphore {
    private boolean released = true;

    public synchronized void acquire() {
        while(!released) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        released = false;
    }

    public synchronized void release() {
        if(released) throw new RuntimeException("Tried to release released semaphore");
        released = true;
        notifyAll();
    }

}
