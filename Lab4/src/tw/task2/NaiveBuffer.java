package tw.task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NaiveBuffer implements IBuffer {
    private final Lock lock = new ReentrantLock();
    private final Condition prodCond = lock.newCondition();
    private final Condition condCond = lock.newCondition();

    int maxSize;
    int currSize;

    public NaiveBuffer(int M) {
        this.maxSize = M;
        this.currSize = 0;
    }

    @Override
    public void put(int size)  {
        lock.lock();
        try {
            while(maxSize - currSize < size) {
                prodCond.await();
            }
            currSize += size;

            condCond.signal();
//            prodCond.signal();
        }   catch (InterruptedException e){
            e.printStackTrace();
        }
        finally {
//            System.out.println(this.getCurrSize() + "/" + this.getMaxSize() + " <- added " + size);
            lock.unlock();
        }
    }

    @Override
    public void take(int size) {
        lock.lock();
        try {
            while (currSize < size) {
                condCond.await();
            }
            currSize -= size;

            prodCond.signal();
//            condCond.signal();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
//            System.out.println(this.getCurrSize() + "/" + this.getMaxSize() + " <- taken " + size);
            lock.unlock();
        }
    }

    @Override
    public int getCurrSize() {
        return currSize;
    }
    @Override
    public int getMaxSize() {
        return maxSize;
    }
}
