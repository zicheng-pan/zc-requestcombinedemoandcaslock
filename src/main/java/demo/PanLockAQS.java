package demo;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class PanLockAQS implements Lock {


    AQSPan aqsPan = new AQSPan() {

        @Override
        public boolean tryAcquire() {
            return owner.compareAndSwap(null, Thread.currentThread());
        }

        @Override
        public boolean tryRelease() {
            return owner.compareAndSwap(Thread.currentThread(), null);
        }

    };

    @Override
    public void lock() {
        aqsPan.acquire();
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }

    @Override
    public boolean tryLock() {
        return aqsPan.tryAcquire();
    }

    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }

    @Override
    public void unlock() {
        aqsPan.release();
    }

    @Override
    public Condition newCondition() {
        return null;
    }
}
