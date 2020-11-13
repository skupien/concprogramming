package tw;

public class Buffer {

    private String message;
    private boolean holdsMessage = false;

    public synchronized String take() {
        while(!holdsMessage) {
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        holdsMessage = false;
        notifyAll();
        return message;
    }

    public synchronized void put(String message) {
        while(holdsMessage) {
            try {
                wait();
            } catch (InterruptedException e) { e.printStackTrace(); }
        }
        holdsMessage = true;
        this.message = message;
        notifyAll();
    }

}
