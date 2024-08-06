package com.example.springdemo.utils;

import com.example.springdemo.pojo.Reward;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 抽奖算法工具类
 *
 * @author xiegege
 * @date 2021/1/7 17:02
 */
@Component
public class RaffleUtil {

    private static List<Reward> rewards = Collections.synchronizedList(new ArrayList<>());

    public  void InitialCache(JdbcTemplate jdbcTemplate) {
        if(rewards.isEmpty()) {
                SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from rewards");
        while(sqlRowSet.next()){
            rewards.add(Reward.Reward(sqlRowSet));
        }

    }
    }

    /**
     * 抽奖算法
     * @param
     * @return 物品的索引
     */
    @Async
    public Reward lottery() {
        // 计算总概率，这样可以保证不一定总概率是1
        Integer sumRate = 0;
        for (Reward item : rewards) {
            sumRate += item.getPercentage();
        }
        // 计算每个物品在总概率的基础下的概率情况
        List<Double> sortOriginRates = new ArrayList<>();
        Integer tempSumRate = 0;
        for (Reward item : rewards) {
            tempSumRate += item.getPercentage();
            sortOriginRates.add((double)tempSumRate / (double)sumRate);
        }
        // 根据区块值来获取抽取到的物品索引
        Double nextDouble = Math.random();
        sortOriginRates.add(nextDouble);
        Collections.sort(sortOriginRates);
        int index = sortOriginRates.indexOf(nextDouble);
        return rewards.get(index);
    }
}
