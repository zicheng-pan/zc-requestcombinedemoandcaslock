package com.example.demoforrequestcombine.com.service;

import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

public class PhoneRowMapper implements RowMapper<Phone> {
    @Override
    public Phone mapRow(ResultSet resultSet, int i) throws SQLException {
        Phone phone = new Phone();
        phone.code = resultSet.getString("code");
        phone.phone = resultSet.getString("phone");
        phone.price = resultSet.getInt("price");
        phone.type = resultSet.getString("type");
        return phone;
    }
}
