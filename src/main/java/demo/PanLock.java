package demo;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;

public class PanLock implements Lock {

    AtomicCASObjPan<Thread> owner = new AtomicCASObjPan<>();

    //asume linked queue with synchronized
    LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();


    @Override
    public void lock() {

        while (!tryLock()) {
            waiters.offer(Thread.currentThread());
            LockSupport.park();
            waiters.remove(Thread.currentThread());
        }

    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        if (owner.obj == null) {
            return owner.compareAndSwap(null, Thread.currentThread());
        }
        return false;
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {

        if (owner.compareAndSwap(Thread.currentThread(), null)) {
            Iterator<Thread> iterator = waiters.iterator();
            while (iterator.hasNext()) {
                // fair lock or unfair lock
                LockSupport.unpark(iterator.next());
            }
        }
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
