package tw.OLD.task3;

import java.util.Random;

public class Person implements Runnable {
    private KelnerMonitor kelner;
    private int pairNumber;

    public Person(KelnerMonitor kelner, int pairNumber) {
        this.kelner = kelner;
        this.pairNumber = pairNumber;
    }

    public void run() {
        int time;
        try {
            time = new Random().nextInt(10)+1;
            Thread.sleep(time*100);

            System.out.println("Pair " + pairNumber + " -> reserves");
            kelner.orderTable(pairNumber);

            System.out.println("Pair " + pairNumber + " -> got table");
            time = new Random().nextInt(10)+1;
            Thread.sleep(time*100);

            System.out.println("Pair " + pairNumber + " -> frees table");
            kelner.freeTable();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

