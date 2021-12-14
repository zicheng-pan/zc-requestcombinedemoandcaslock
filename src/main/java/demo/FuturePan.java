package demo;

import java.util.Iterator;
import java.util.concurrent.Callable;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.locks.LockSupport;

public class FuturePan<T> implements Runnable {

    T result;

    Callable<T> callable;

    String state;

    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    public FuturePan(Callable<T> callable) {
        this.callable = callable;
    }

    public T get() {
        if ("END".equals(state)) {
            return result;
        }
        waiters.offer(Thread.currentThread());
        LockSupport.park();
        return result;
    }

    @Override
    public void run() {
        try {
            result = this.callable.call();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            state = "END";
        }
        Iterator<Thread> iterator = waiters.iterator();
        while (iterator.hasNext()) {
            LockSupport.unpark(iterator.next());
        }
    }
}
