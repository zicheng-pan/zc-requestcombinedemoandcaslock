package demo;

import java.util.LinkedList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class SynchronizedPanQueue02<T> {

    final int MAX_SIZE = 10;

    private int count = 0;

    final LinkedList<T> linkedList = new LinkedList<>();

    private ReentrantLock lock = new ReentrantLock();
    private Condition provider = lock.newCondition();
    private Condition consumer = lock.newCondition();

    public void put(T t) {
        try {
            lock.lock();
            while (linkedList.size() >= MAX_SIZE) {
                try {
                    provider.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            linkedList.add(t);
            count++;
            consumer.signalAll();
        } finally {
            lock.unlock();
        }
    }

    public synchronized T get() {
        try {
            T t = null;
            while (linkedList.size() <= 0) {
                try {
                    consumer.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            t = linkedList.removeFirst();
            count--;
            provider.signalAll();
            return t;
        } finally {
            lock.unlock();
        }

    }
}
