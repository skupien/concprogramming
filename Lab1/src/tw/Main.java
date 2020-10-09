package tw;

public class Main {

    public static void main(String[] args) throws InterruptedException {

        MyThread t = new MyThread(true);
        MyThread t2 = new MyThread(false);
        new Thread(t).start();
        new Thread(t2).start();

    }
}
