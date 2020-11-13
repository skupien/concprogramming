package tw.OLD.task2;

import java.util.Random;

public class Printer implements Runnable {
    private PrinterMonitor monitor;
    private int threadNo;

    public Printer(PrinterMonitor monitor, int threadNo) {
        this.monitor = monitor;
        this.threadNo = threadNo;
    }

    public void run() {
        int time = new Random().nextInt(10)+1;
        try {
            Thread.sleep(time*100);
            int printer_no = 0;
            System.out.println("#" + threadNo + " Reserves");
            printer_no = monitor.reserve();
            System.out.println("#" + threadNo + " Printing via " + printer_no + " printer");
            time = new Random().nextInt(10)+1;
            Thread.sleep(time*100);
            System.out.println("\t#" + threadNo + " Releasing " + printer_no + " printer");
            monitor.release(printer_no);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
