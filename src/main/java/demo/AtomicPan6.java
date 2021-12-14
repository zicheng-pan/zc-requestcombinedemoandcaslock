package demo;

public class AtomicPan6 {

    AtomicCASObjPan<Integer> atomicCASObjPan = new AtomicCASObjPan<>(0);

    public void add() {
        Integer temp = null;
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            do {
                temp = atomicCASObjPan.getObj();
            }
            while (!atomicCASObjPan.compareAndSwap(temp, temp + 1));
        }

    }

    public static void main(String[] args) throws InterruptedException {
        AtomicPan6 pan = new AtomicPan6();
        Thread t1 = new Thread(pan::add, "t1");
        Thread t2 = new Thread(pan::add, "t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(pan.atomicCASObjPan);
    }

}
