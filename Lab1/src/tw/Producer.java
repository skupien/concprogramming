package tw;

import static tw.Main.ILOSC;

public class Producer implements Runnable {
    private Buffer buffer;

    public Producer(Buffer buffer) {
        this.buffer = buffer;
    }

    public void run() {

        for(int i = 0;  i < ILOSC;   i++) {
            buffer.put("message "+i);
        }

    }
}
