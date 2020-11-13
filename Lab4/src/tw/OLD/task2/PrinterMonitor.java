package tw.OLD.task2;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PrinterMonitor {
    final Lock lock = new ReentrantLock();
    final Condition notAllBusy  = lock.newCondition();
//    final Condition notAllFree = lock.newCondition();
    boolean[] freePrinters;

    public PrinterMonitor(int number) {
        freePrinters = new boolean[number];
        Arrays.fill(freePrinters, Boolean.TRUE);
    }

    private int findFreePrinterIndex() {
        for(int i = 0; i < freePrinters.length; i++) {
            if(freePrinters[i]) return i;
        }
        return -1;
    }

//    private boolean allPrintersFree() {
//        for(boolean isFree : freePrinters) {
//            if(!isFree) return false;
//        }
//        return true;
//    }

    public int reserve() throws InterruptedException{
        lock.lock();
        int printerIndex;
        try {
            while ((printerIndex=findFreePrinterIndex()) == -1)
                notAllBusy.await();
            freePrinters[printerIndex] = false;
            //notAllFree.signal();
        } finally {
            lock.unlock();
        }
        return printerIndex;
    }

    public void release(int printer_no) throws InterruptedException{
        lock.lock();
        try {
//            while (allPrintersFree())
//                notAllFree.await(); // SHOULD IT MAYBE THROW ERROR?
            freePrinters[printer_no] = true;
            notAllBusy.signal();
        } finally {
            lock.unlock();
        }
    }

}
