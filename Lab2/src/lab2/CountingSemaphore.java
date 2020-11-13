package lab2;

public class CountingSemaphore {
    private int permits;

    public CountingSemaphore(int permits) {
        this.permits = permits;
    }

    private void increasePermit() {
        permits++;
    }
    private void decreasePermit() {
        if(permits == 0) throw new RuntimeException("Tried to decrease permit while it was 0");
        permits--;
    }


    public synchronized void acquire() {
        while(permits<=0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        this.decreasePermit();
    }

    public synchronized void release() {
        increasePermit();
        notifyAll();
    }
}
