package tw.OLD.task1;

import java.util.Arrays;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Buffer {
    int N; //buffer size
    int M; //num of processes
    private final Lock lock = new ReentrantLock();
    private final int[] semaphores;
    private final Condition[] conditions;

    private int[] tape;

    public Buffer(int size, int numOfProcesses) {
        this.N = size;
        this.M = numOfProcesses;

        this.tape = new int[N];

        this.semaphores = new int[M];
        this.conditions = new Condition[M];
        for(int i=0; i< M; i++) {
            conditions[i] = lock.newCondition();
        }

        semaphores[0] = N;
        for(int i=1; i<M; i++) {
            semaphores[i] = 0;
        }

        System.out.println("Processes: " + M);
    }

    public void put(int index) {
        lock.lock();
        try {
            semaphores[(index + 1) % M]++;
            conditions[(index + 1) % M].signal();
        } finally {
            lock.unlock();
        }

    }

    public void take(int index) throws InterruptedException {
        lock.lock();
        try {
            if (semaphores[index] == 0) {
                conditions[index].await();
            }
            semaphores[index]--;
        } finally {
            lock.unlock();
        }
    }

    public void incrementTape(int index) {
        this.tape[index]++;
        System.out.println("Semaphores: " + Arrays.toString(semaphores) + ";\tTape: " + Arrays.toString(tape));

    }

}
