package com.example.springdemo.controller;

import com.example.springdemo.pojo.Banner;
import com.example.springdemo.pojo.user_raffle_info;
import com.example.springdemo.utils.TokenUtil;
import com.example.springdemo.utils.jsonUtils.JSONResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.Future;


/**
 *
 * @Title: BannerController.java
 * @Package com.example.springdemo.controller
 * @Description: 处理访问/banner url的接口请求
 */
@RestController
public class BannerController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private ThreadPoolTaskExecutor threadPool;

    @GetMapping("/banner")
    public JSONResult getBanner(
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
                Banner banner = new Banner();
                banner.getFromDb(jdbcTemplate);
                return JSONResult.ok(banner);
            }
        }

    }
}
