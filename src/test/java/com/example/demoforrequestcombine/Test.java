package com.example.demoforrequestcombine;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Test {

    Lock lock = new ReentrantLock();

    public void test() throws Exception {
        lock.lockInterruptibly();
        try {
            System.out.println(Thread.currentThread().getName() + "get Lock");
            try {
                Thread.sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        } finally {

            System.out.println(Thread.currentThread().getName() + "release Lock");
            lock.unlock();
        }
    }

    public static void main(String[] args) throws Exception {
        final Test t = new Test();
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    t.test();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        Thread t1 = new Thread(runnable, "t1");
        Thread t2 = new Thread(runnable, "t2");

        t1.start();

        Thread.sleep(500);

        t2.start();
        Thread.sleep(1000);
        t2.interrupt();
        System.out.println("end process");

    }
}