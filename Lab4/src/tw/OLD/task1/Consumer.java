package tw.OLD.task1;



public class Consumer implements Runnable {
    private BoundedBuffer buffer;

    public Consumer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < 100;   i++) {
            String message = null;
            try {
                message = buffer.take();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println(message);
        }

    }
}
