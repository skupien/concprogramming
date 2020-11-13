package tw;

import tw.OLD.task1.*;
import tw.OLD.task2.*;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Thread.sleep;

    public class Main {
        private static void presentTask1(int size, int processNum) throws InterruptedException {
            Buffer buffer = new Buffer(size, processNum);
            for(int i=0; i<processNum; i++) {
                new Thread(new Processor(buffer, i, size)).start();
            }
        }

        private static void presentTask2(int M, int P, int K) throws InterruptedException {

            List<Long> avgTimeS = new ArrayList<>();
            List<Long> avgTimeM = new ArrayList<>();
            List<Long> avgTimeL = new ArrayList<>();

//            IBuffer buffer = new PortionBuffer(2*M);
            IBuffer buffer = new NaiveBuffer(2*M);
            for(int i=0; i<P; i++) {
                new Thread(new Producer(buffer, M, avgTimeS, avgTimeM, avgTimeL)).start();
            }
            for(int i=0; i<K; i++) {
                new Thread(new Consumer(buffer, M, avgTimeS, avgTimeM, avgTimeL)).start();
            }

            while(avgTimeL.size() <= 100000) {
                sleep(1000);
            }
            var listL = List.copyOf(avgTimeL).stream().mapToDouble(a->a).summaryStatistics();
            var listM = List.copyOf(avgTimeM).stream().mapToDouble(a->a).summaryStatistics();
            var listS = List.copyOf(avgTimeS).stream().mapToDouble(a->a).summaryStatistics();
            System.out.println("Large  batch time: " + listL.getAverage() + ", size: " + listL.getCount());
            System.out.println("Medium batch time: " + listM.getAverage() + ", size: " + listM.getCount());
            System.out.println("Small  batch time: " + listS.getAverage() + ", size: " + listS.getCount());
        }


        public static void main(String[] args) throws InterruptedException {
//            Main.presentTask1(20,7);
            Main.presentTask2(1000, 10, 10);
        }
    }