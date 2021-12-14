package com.example.demoforrequestcombine.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class QueryServiceRemotCall {

    @Autowired
    DBDao dao;

    // start mysql docker run --name mysql -d -it -p 3306:3306 -e MYSQL_ROOT_PASSWORD=root mysql:5.7
    // mycli -u root -h localhost
    public List<Phone> queryCommandByBatch(List<String> codes) {
        return dao.getPhones(codes);
    }

    public Phone queryCommandByCode(String code) {
        return dao.get(code);
    }
}
