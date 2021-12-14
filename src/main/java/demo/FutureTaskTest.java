package demo;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class FutureTaskTest {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        FuturePan<String> futureTask = new FuturePan<String>(() -> {
            Thread.sleep(1000);
            return "result";
        });

        new Thread(futureTask).start();

        System.out.println(futureTask.get());

    }
}
