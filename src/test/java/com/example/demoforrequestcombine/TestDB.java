package com.example.demoforrequestcombine;

import com.example.demoforrequestcombine.com.service.DBDao;
import com.example.demoforrequestcombine.com.service.Phone;
import com.example.demoforrequestcombine.com.service.QueryServiceRemotCall;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ToStringBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;


@SpringBootTest
public class TestDB {

    @Autowired
    QueryServiceRemotCall queryServiceRemotCall;
    @Autowired
    DBDao dao;

    @Test
    public void testGet() {
        Phone phone = dao.get("code-9");
        System.out.println(new ToStringBuilder(phone).toString());
    }

    @Test
    public void testGets() {
        String[] data = new String[]{"code-9", "code-10", "code-11"};
        List<Phone> phones = dao.getPhones(Arrays.asList(data));
        System.out.println(phones.size());
    }

    @Test
    public void testCall() {
        Phone phone = queryServiceRemotCall.queryCommandByCode("code-9");
        System.out.println(new ToStringBuilder(phone).toString());
    }


    @Test
    public void testCalls() {
        String[] data = new String[]{"code-9", "code-10", "code-11"};
        List<Phone> phones = queryServiceRemotCall.queryCommandByBatch(Arrays.asList(data));
        System.out.println(phones.size());
    }
}
