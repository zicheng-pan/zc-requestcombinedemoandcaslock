package com.example.demoforrequestcombine;

import com.example.demoforrequestcombine.com.service.DBDao;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InsertDB {

    @Autowired
    DBDao dao;

    @Test
    public void initDB() {
        dao.initDB();
    }
}
