package tw.task3;

import tw.task3.KelnerMonitor;

import java.util.Random;

import static tw.Main.ILOSC;

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

