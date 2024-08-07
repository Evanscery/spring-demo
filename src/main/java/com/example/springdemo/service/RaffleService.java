package com.example.springdemo.service;

import com.example.springdemo.pojo.Reward;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class RaffleService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    @Async
    public void receiveReward(Reward reward) {
        String info = "向奖品库申请奖品，id：" + reward.getId() + " 名称:" + reward.getDescription();
        logger.info(info);
        /*异步调用微信接口，并接收结果回调*/
    }
}
