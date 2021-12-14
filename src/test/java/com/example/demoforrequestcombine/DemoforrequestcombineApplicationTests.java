package com.example.demoforrequestcombine;

import com.example.demoforrequestcombine.com.service.DBService;
import com.example.demoforrequestcombine.com.service.Phone;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

@SpringBootTest
class DemoforrequestcombineApplicationTests {


    public static final int REQUEST_COUNT = 1000;
    private final CountDownLatch countDownLatch = new CountDownLatch(1);
    private long timed;

    @Autowired
    private DBService dbService;

    @BeforeEach
    public void start() {
        timed = System.currentTimeMillis();
    }

    @AfterEach
    public void end() {
        System.out.println("time:" + (System.currentTimeMillis() - timed));
    }

    @Test
    void benchmark() throws Exception {

        List<Thread> list = new ArrayList();
        for (int i = 0; i < REQUEST_COUNT; i++) {

            String code = "code-" + i;  // the parameter of request
            Thread thread = new Thread(() -> {

                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Phone phone = dbService.queryPhone(code.toString());

            });
            list.add(thread);
            thread.setName("Thread-" + i);
            thread.start();
            countDownLatch.countDown();

        }

        for (Thread t : list) {
            t.join();
        }
    }


    @Test
    void testSimgleRequest() throws Exception {

        List<Thread> list = new ArrayList();
        for (int i = 0; i < REQUEST_COUNT; i++) {

            String code = "code-" + i;  // the parameter of request
            Thread thread = new Thread(() -> {

                try {
                    countDownLatch.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Phone phone = dbService.querySinglePhone(code.toString());

            });
            list.add(thread);
            thread.setName("Thread-" + i);
            thread.start();
            countDownLatch.countDown();

        }

        for (Thread t : list) {
            t.join();
        }
    }

}
