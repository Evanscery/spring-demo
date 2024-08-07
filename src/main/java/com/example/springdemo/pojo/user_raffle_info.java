package com.example.springdemo.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

public class user_raffle_info {

    @Resource(name = "jdbcTemplate")
    private Logger logger = LoggerFactory.getLogger(getClass());
    @JsonIgnore
    private String user_id;
    private Integer ibean_balance;

    public user_raffle_info() {
        this.ibean_balance = 0;
        this.user_id = "";
    }

    public user_raffle_info(String user_id, Integer balance) {
        this.user_id = user_id;
        this.ibean_balance = balance;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getIbean_balance() {
        return ibean_balance;
    }

    public void setIbean_balance(Integer ibean_balance) {
        this.ibean_balance = ibean_balance;
    }

    public void fromDb(JdbcTemplate jdbcTemplate, String user_id) {
        logger.info("使用user_id获取balance,user_id = " + user_id);
        String sql = "SELECT ibean_balance FROM user_ibean_info WHERE user_id = '" + user_id + "'";
        Integer balance = jdbcTemplate.queryForObject(sql, Integer.class);
        this.ibean_balance = balance;
    }

}

