package demo;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicPan3 {


    AtomicInteger count = new AtomicInteger(0);
//    AtomicCASIntPan count = new AtomicCASIntPan(0);

    public void add() {
        Integer temp = null;
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count.getAndAdd(1);
        }

    }

    public static void main(String[] args) throws InterruptedException {
        AtomicPan3 pan = new AtomicPan3();
        Thread t1 = new Thread(pan::add, "t1");
        Thread t2 = new Thread(pan::add, "t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(pan.count);
    }

}
