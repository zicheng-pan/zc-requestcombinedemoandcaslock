package demo;

import java.util.Iterator;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.LockSupport;

//AbstractQueuedSynchronizer
//类如其名，抽象的队列式的同步器，AQS定义了一套多线程访问共享资源的同步器框架，许多同步类实现都依赖于它，如常用的ReentrantLock/Semaphore/CountDownLatch
public class AQSPan {
    // synchronization resource state
    volatile AtomicCASIntPan state = new AtomicCASIntPan(0);
    // the owner of this Lock
    protected volatile AtomicCASObjPan<Thread> owner = new AtomicCASObjPan<>();

    public volatile LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    // exclusive resource
    public void acquire() {
        waiters.offer(Thread.currentThread());
        while (!tryAcquire()) {
            LockSupport.park();
        }
        waiters.remove(Thread.currentThread());
    }

    public void release() {
        // cas 修改 owner 拥有者
        if (tryRelease()) {
            Thread waiter = waiters.peek();
            LockSupport.unpark(waiter); // 唤醒线程继续 抢锁
        }
    }

    public boolean tryAcquire() {
        throw new UnsupportedOperationException();
    }

    public boolean tryRelease() {
        throw new UnsupportedOperationException();
    }


    // shared resource
    public void acquireShared() {

        waiters.offer(Thread.currentThread());
        while (tryAcquireShared() < 0) {

            LockSupport.park();
        }

        waiters.remove(Thread.currentThread());
    }


    public void releaseShared() {

        if (tryReleaseShared()) {
            Thread waiter = waiters.peek();
            LockSupport.unpark(waiter);
        }
    }


    public int tryAcquireShared() {
        throw new UnsupportedOperationException();
    }

    public boolean tryReleaseShared() {
        throw new UnsupportedOperationException();
    }

    public AtomicCASIntPan getState() {
        return state;
    }

    public void setState(AtomicCASIntPan state) {
        this.state = state;
    }
}
