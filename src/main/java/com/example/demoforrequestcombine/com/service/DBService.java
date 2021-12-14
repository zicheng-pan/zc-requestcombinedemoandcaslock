package com.example.demoforrequestcombine.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.*;

@Service
public class DBService {

    // asynic queue
    private LinkedBlockingDeque<Request> queue = new LinkedBlockingDeque();

    @Autowired
    QueryServiceRemotCall queryServiceRemotCall;

    public void sendRequest() {
        System.out.println(Thread.currentThread().getName() + ":abc");
    }

    @PostConstruct
    public void init() {

        ScheduledExecutorService executorService = Executors.newScheduledThreadPool(10);
        //per 10 MILLISECONDS execute only one request
        executorService.scheduleAtFixedRate(() -> {
            int size = queue.size();
            if (size == 0) {
                return;
            }
            List<Request> requests = new ArrayList<>();
            for (int i = 0; i < size; i++) {
                Request request = queue.poll();
                requests.add(request);
            }

            System.out.println("combined " + size + " requests");
            List<String> codes = new ArrayList<>();
            for (Request request : requests) {
                codes.add(request.requestCode);
            }


            List<Phone> responses = queryServiceRemotCall.queryCommandByBatch(codes);
            // use the identified query value marked as response id.
            Map<String, Phone> responseMap = new HashMap<>();
            for (Phone phone : responses) {
                responseMap.put(phone.code, phone);
            }

            // return request result
            for (Request request : requests) {
                request.future.complete(responseMap.get(request.requestCode));
            }

        }, 0, 10, TimeUnit.MILLISECONDS);

    }

    public Phone queryPhone(String code) {
        try {
            Request request = new Request();
            request.requestCode = code;
            request.future = new CompletableFuture();
            queue.add(request);
            // bock here
            return request.future.get();
        } catch (Exception e) {
            throw new RuntimeException("error with db query");
        }
    }

    public Phone querySinglePhone(String code) {
        return queryServiceRemotCall.queryCommandByCode(code);
    }

}
