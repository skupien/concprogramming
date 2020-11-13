package tw.task2;

public interface IBuffer {
    void put(int size);

    void take(int size);

    int getCurrSize();

    int getMaxSize();
}
