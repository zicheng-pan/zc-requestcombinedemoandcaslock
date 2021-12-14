package demo;

import java.util.LinkedList;

public class SynchronizedPanQueue01<T> {

    final int MAX_SIZE = 10;

    private int count = 0;

    final LinkedList<T> linkedList = new LinkedList<>();

    public synchronized void put(T t) {
        while (linkedList.size() >= MAX_SIZE) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        linkedList.add(t);
        count++;
        this.notifyAll();
    }

    public synchronized T get() {
        T t = null;
        while (linkedList.size() <= 0) {
            try {
                wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        t = linkedList.removeFirst();
        count--;
        this.notifyAll();
        return t;
    }

}
