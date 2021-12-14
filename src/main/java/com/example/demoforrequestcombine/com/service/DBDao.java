package com.example.demoforrequestcombine.com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class DBDao {

    @Autowired
    JdbcTemplate jdbcTemplate;

    public Phone get(String code) {
        String sql = "select code,phone,price,type from tb_phone where code=?";
        System.out.println(sql);
        return jdbcTemplate.queryForObject(sql, new PhoneRowMapper(), code);
    }

    public List<Phone> getPhones(List<String> codes) {
        StringBuffer sql = new StringBuffer("select * from tb_phone where code =");
        for (String str : codes)
            sql.append("'" + str + "'" + " or code=");
        String sql_str = sql.toString().substring(0, sql.length() - 8);
        System.out.println(sql_str);
        return (List<Phone>) jdbcTemplate.query(sql_str, new PhoneRowMapper());
    }

    public void initDB() {
        for (int i = 0; i < 1000; i++) {
            String sql = "insert into tb_phone values(?,?,?,?)";

            jdbcTemplate.update(sql, new Object[]{"code-" + i, "huawei", 4000, "smartphone"});
        }
    }

}
