package com.example.springdemo.service;

import com.example.springdemo.utils.jsonUtils.JSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Service
public class WechatService {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Async
    public CompletableFuture<JSONResult> receiveCoupon(String user_id,String coupon_id)
    {
        logger.info("获取微信立减金");
        /*异步调用微信接口，并接收结果回调*/

        //这里直接随机产生回调结果
        Integer status = (int)(Math.random()*100) + 1;
        if(status>=20)
            return CompletableFuture.completedFuture(JSONResult.ok());
        else
            return CompletableFuture.failedFuture(new RuntimeException("Wechat error"));

    }
}
