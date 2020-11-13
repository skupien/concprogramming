package tw.OLD.task3;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class KelnerMonitor {
    final private Lock lock = new ReentrantLock();

    final private Condition waitForPair  = lock.newCondition();
    final private Condition waitForTable = lock.newCondition();

    private final Queue<Integer> queue = new LinkedList<>();
    private final Set<Integer> set = new HashSet<>();
    private int peopleAtTable = 0;

    public void orderTable(int x) throws InterruptedException {
        lock.lock();
        try {
            if (set.add(x)) {
//                queue.add(x);
                while (set.contains(x)) {
                    waitForPair.await();
                }
            } else {
                queue.add(x);
                while (queue.element() != x) {
                    waitForTable.await();
                }
                set.remove(x);
                waitForPair.signalAll();
//                waitForPair.signal();
            }
            peopleAtTable++;
        } finally {
            lock.unlock();
        }
    }

    public void freeTable() throws Exception {
        lock.lock();
        try {
            if(peopleAtTable == 0) {
                throw new Exception("Tried freeing empty table");
            }
            peopleAtTable--;
            if(peopleAtTable == 0)  {
                int x = queue.element();
                queue.remove(x);
                waitForTable.signal();
//                waitForTable.signalAll();
            }
        } finally {
            lock.unlock();
        }
    }
}
