package tw.task2;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class PortionBuffer implements IBuffer{
    private final Lock lock = new ReentrantLock();

    private final Condition firstProd = lock.newCondition();
    private boolean isFirstProd;

    private final Condition restProd = lock.newCondition();
    private final Condition firstCons = lock.newCondition();
    private boolean isFirstCons;

    private final Condition restCons = lock.newCondition();

    int maxSize;
    int currSize;

    public PortionBuffer(int M) {
        this.maxSize = M;
        this.currSize = 0;

        isFirstCons = false;
        isFirstProd = false;
    }

    @Override
    public void put(int size)  {
        lock.lock();
        try {
            if (isFirstProd)  {
                restProd.await();
            }
            isFirstProd = true;
            while(maxSize - currSize < size) {
                firstProd.await();
            }
            isFirstProd = false;
            currSize += size;


            firstCons.signal();
            restProd.signal();
//            firstProd.signal();
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
            if (isFirstCons) {
                restCons.await();
            }
            isFirstCons = true;
            while (currSize < size) {
                firstCons.await();
            }
            isFirstCons = false;
            currSize -= size;


            firstProd.signal();
            restCons.signal();
//            firstCons.signal();
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
