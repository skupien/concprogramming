package tw.task2;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Consumer implements Runnable {
    private final IBuffer buffer;
    int M;
    List<Long> avgTimeS;
    List<Long> avgTimeM;
    List<Long> avgTimeL;

    public Consumer(IBuffer buffer, int maxSize, List<Long> avgTimeS, List<Long> avgTimeM, List<Long> avgTimeL) {
        this.buffer = buffer;
        this.M = maxSize;
        this.avgTimeS = avgTimeS;
        this.avgTimeM = avgTimeM;
        this.avgTimeL = avgTimeL;
    }

    public void run() {
        while(!Thread.interrupted()) {
            int rand = new Random().nextInt(this.M) + 1;
            long startTime = System.nanoTime();
            buffer.take(rand);
            long stopTime = System.nanoTime();

            if (rand < M/3) {
                this.avgTimeS.add(stopTime - startTime);
            }
            else if (rand < M * 2 / 3) {
                this.avgTimeM.add(stopTime - startTime);
            }
            else {
                this.avgTimeL.add(stopTime - startTime);
            }
        }
    }
}
