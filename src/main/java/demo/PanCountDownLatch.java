package demo;

public class PanCountDownLatch {

    //1、 init aqs method
    AQSPan aqsPan = new AQSPan() {

        // 共享资源就是判断state
        // 独占资源就是判断owner   --->  对应读写锁
        @Override
        public int tryAcquireShared() {
            return this.getState().count == 0 ? 1 : -1;  // is it synchronized? --> LockSupport park unpark 没有顺序
        }

        @Override
        public boolean tryReleaseShared() {
            this.getState().getAndAdd(-1);
            return this.getState().count == 0;
        }
    };


    //2、 simulate countdownlatch construct
    public PanCountDownLatch(int count) {
        aqsPan.state = new AtomicCASIntPan(count);
    }


    public void await() {
        aqsPan.acquireShared();
    }

    public void countDown() {
        aqsPan.releaseShared();
    }

}
