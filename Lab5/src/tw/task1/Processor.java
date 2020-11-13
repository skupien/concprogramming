package tw.task1;

import java.util.Random;

public class Processor implements Runnable {
    private final int buffSize;
    private final Buffer buffer;
    int index;
    int time;

    public Processor(Buffer buffer, int index, int buffSize) {
        this.buffer = buffer;
        this.index = index;
        this.time = new Random().nextInt(10)+1;
//        if(index == 0) this.time = 0;
        this.buffSize = buffSize;
    }

    public void run() {
        int j = 0;


        while(true) {
//        for(int k=0; k<10; k++) {
            try {
                buffer.take(index);

                Thread.sleep(time*100);
                buffer.incrementTape(j);

                buffer.put(index);

                j= (j+1)%buffSize;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
