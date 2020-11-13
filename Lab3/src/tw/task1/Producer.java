package tw.task1;

import tw.task1.BoundedBuffer;

import static tw.Main.ILOSC;

public class Producer implements Runnable {
    private BoundedBuffer buffer;

    public Producer(BoundedBuffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < ILOSC;   i++) {
            try {
                buffer.put("message "+i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
