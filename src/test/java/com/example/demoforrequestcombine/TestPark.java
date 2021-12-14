package com.example.demoforrequestcombine;

public class TestPark {
    public static Object baozi = null;

    public final Object lock = new Object();

    public void testWait() {
        Thread t1 = new Thread(() -> {
            synchronized (lock) {
                while (baozi == null) {
                    try {
                        lock.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                System.out.println("eat baozi");
            }
        });

        t1.start();
        Thread t2 = new Thread(() -> {
            synchronized (lock) {
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                baozi = new Object();
                lock.notifyAll();
                System.out.println("generate a baozi");
            }
        });
        t2.start();
    }



    public static void main(String[] args) {
//        new TestPark().test();
    }

}
