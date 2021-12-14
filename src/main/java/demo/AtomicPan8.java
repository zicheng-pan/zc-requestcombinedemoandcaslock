package demo;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class AtomicPan8 {

    int count = 0;
    //    PanLock lock = new PanLock();
    Lock lock = new PanLockAQS();

    public void add() {
        lock.lock();

        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(12);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
        lock.unlock();
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicPan8 pan = new AtomicPan8();
        Thread t1 = new Thread(pan::add, "t1");
        Thread t2 = new Thread(pan::add, "t2");
        Thread t3 = new Thread(pan::add, "t2");
        t1.start();
        t2.start();
        t3.start();

        t1.join();
        t2.join();
        t3.join();
        System.out.println(pan.count);
    }

}
