package com.example.springdemo.controller;

import com.example.springdemo.pojo.Reward;
import com.example.springdemo.pojo.user_raffle_info;
import com.example.springdemo.service.RaffleService;
import com.example.springdemo.utils.RaffleUtil;
import com.example.springdemo.utils.TokenUtil;
import com.example.springdemo.utils.jsonUtils.JSONResult;
import jakarta.annotation.Resource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@RestController
public class RaffleController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private RaffleUtil raffleUtil = new RaffleUtil();

    @Autowired
    private ThreadPoolTaskExecutor threadPool;
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private RaffleService raffleService;


    @RequestMapping("/raffle/user")
    public JSONResult getUserInfo(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("user_id") String user_id,
                                @RequestParam("token") String token) {
        /*TODO*/
        logger.info("读取头信息，权限认证");

        TokenUtil tokenUtil = new TokenUtil(token);
        switch(tokenUtil.parse())
        {
            case 502:{
                return JSONResult.errorTokenMsg("Token is wrong");
            }
            default:
            {
                user_raffle_info user_raffle = new user_raffle_info();
                //放进线程池里
                    String sql = "select ibean_balance from user_ibean_info where user_id=\'"+user_id+"\'";
                    Integer balance = jdbcTemplate.queryForObject(sql, Integer.class);
                    if(balance!=null)
                    {
                        user_raffle.setIbean_balance(balance);
                    }
                    else
                    {
                        user_raffle.setIbean_balance(-1);
                    }

                if(user_raffle.getIbean_balance() > 0)
                    return JSONResult.ok(user_raffle);
                else
                    return JSONResult.errorMsg("User not found");
            }
        }

    }

    @RequestMapping("/raffle/rewards")
    public JSONResult getRewards(
            @RequestHeader("Authorization") String authorization,
                                @RequestParam("token") String token) {
        /*TODO*/
        logger.info("读取头信息，权限认证");

        TokenUtil tokenUtil = new TokenUtil(token);
        switch(tokenUtil.parse())
        {
            case 502:{
                return JSONResult.errorTokenMsg("Token is wrong");
            }
            default:
            {
                List rewards= new ArrayList<>();
                Object item;
                threadPool.execute(()->{
                    SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from rewards");
                    while(sqlRowSet.next()){
                            rewards.add(Reward.Reward(sqlRowSet));
                    }
                });
                return JSONResult.ok(rewards);
            }
        }

    }

    @RequestMapping("/raffle")
    public JSONResult postRaffle(
                        @RequestHeader("Authorization") String authorization,
                        @RequestParam("user_id") String user_id,
                        @RequestParam("token") String token
    )
    {
        /*TODO*/
        logger.info("读取头信息，权限认证");

        TokenUtil tokenUtil = new TokenUtil(token);
        switch(tokenUtil.parse())
        {
            case 502:{
                return JSONResult.errorTokenMsg("Token is wrong");
            }
            default:
            {
                int balance = jdbcTemplate.queryForObject("SELECT ibean_balance FROM user_ibean_info WHERE user_id='"+user_id+"'", Integer.class);
                if(balance>5)
                {
                    raffleUtil.InitialCache(jdbcTemplate);
                    Reward reward = raffleUtil.lottery();
                    String info = "用户："+user_id+"，抽奖获得奖品："+reward.getDescription();
                    logger.info(info);
                    if(reward.getId()!=1)
                    {
                        //获取奖品
                        raffleService.receiveReward(reward);
                        return JSONResult.ok(reward);
                    }
                    else
                        return JSONResult.ok();
                }
                else
                {
                    return JSONResult.errorMsg("Balance is not enough");
                }
            }
        }
    }


}
