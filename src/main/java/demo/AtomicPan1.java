package demo;

public class AtomicPan1 {

    int count = 0;

    public void add() {
        Integer temp = null;
        for (int i = 0; i < 1000; i++) {
            try {
                Thread.sleep(12);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            count++;
        }
    }

    public static void main(String[] args) throws InterruptedException {
        AtomicPan1 pan = new AtomicPan1();
        Thread t1 = new Thread(pan::add, "t1");
        Thread t2 = new Thread(pan::add, "t2");
        t1.start();
        t2.start();

        t1.join();
        t2.join();

        System.out.println(pan.count);
    }

}
