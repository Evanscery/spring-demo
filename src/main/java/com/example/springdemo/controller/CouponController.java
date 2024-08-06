package com.example.springdemo.controller;

import com.example.springdemo.pojo.Activity;
import com.example.springdemo.pojo.Reward;
import com.example.springdemo.service.WechatService;
import com.example.springdemo.utils.TokenUtil;
import com.example.springdemo.utils.jsonUtils.JSONResult;
import jakarta.validation.constraints.NotBlank;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

/**
 *
 * @Title: CouponController.java
 * @Package com.example.springdemo.controller
 * @Description: 处理访问/coupon url的接口请求
 */
@RestController
public class CouponController {
    private Logger logger = LoggerFactory.getLogger(getClass());
    @Autowired
    private WechatService wechatService;
    @Autowired
    private ThreadPoolTaskExecutor threadPool;
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @RequestMapping(path = "/coupon",method = RequestMethod.POST)
    public CompletableFuture<JSONResult> postGetCoupon(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("user_id") @NotBlank(message = "user_id should not be null or empty") String user_id,
            @RequestParam("activity_id") @NotBlank(message = "activity_id should not be null or empty") String coupon_id,
            @RequestParam("token") String token) {
        /*TODO*/
        logger.info("读取头信息，权限认证");
        TokenUtil tokenUtil = new TokenUtil(token);
        switch (tokenUtil.parse())
        {
            case 502:{
                return CompletableFuture.completedFuture(JSONResult.errorTokenMsg("Token is wrong"));
            }
            default:{
                String pattern = "yyyy.MM.dd HH:mm:ss";
                DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern(pattern);
                LocalDateTime now = LocalDateTime.now();
               threadPool.execute(()->{updateDate(dateTimeFormatter.format(now),user_id,coupon_id);});
               return wechatService.receiveCoupon(user_id,coupon_id);
            }
        }
    }

    @RequestMapping(path = "/coupons",method = RequestMethod.PUT)
    public JSONResult putCoupon(
            @RequestHeader("Authorization") String authorization,
            @RequestParam("token")  String token,
            @RequestParam("activity_id") @NotBlank(message = "a parameter is empty or null") String id,
            @RequestParam("title") @NotBlank(message = "a parameter is empty or null") String title,
            @RequestParam("subtitle") @NotBlank(message = "a parameter is empty or null") String subtitle,
            @RequestParam("highlight") Boolean highlight,
            @RequestParam("icon_url") @NotBlank(message = "a parameter is empty or null") String icon_url
            )

    {
            logger.info("配置领券活动");
            Activity activity = new Activity(id, title, subtitle, highlight, icon_url);
            threadPool.execute(()->{setActivity(activity);});
            return JSONResult.ok();
    }

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void setActivity(Activity activity) {
        int res = jdbcTemplate.update(activity.toUpdateSql());
        if(res==0)
        {
            String sql = "INSERT INTO activities VALUES(?,?,?,?,?)";
            res = jdbcTemplate.update(sql,activity.getActivityId(),activity.getTitle(),activity.getSubtitle(),activity.getIsHighlight(),activity.getIconUrl());
        }
        logger.info(res+"条语句受到影响");
    }

    @RequestMapping(path = "/coupons",method = RequestMethod.GET)
    public JSONResult getCoupons(
            @RequestParam("token") String token,
            @RequestParam("user_id") @NotBlank(message = "a parameter is empty or null") String user_id
            )
    {
        logger.info("查询活动列表");
        TokenUtil tokenUtil = new TokenUtil(token);
        switch(tokenUtil.parse())
        {
            case 502:{
                return JSONResult.errorTokenMsg("Token is wrong");
            }
            default:
            {
                List activities= new ArrayList<>();
                    SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("SELECT * FROM user_activities WHERE user_id='"+user_id+"'");
                    while(sqlRowSet.next()){
                        String last_time = sqlRowSet.getString("last_involve_time");
                        if(last_time != null)
                        {
                            /*TODO*/
                            logger.info("用户："+user_id+" 上次领券时间："+last_time);
                        }
                        String act_id = sqlRowSet.getString("activity_id");
                        Activity activity = new Activity();
                        activity.fromDb(jdbcTemplate,act_id);
                        if(activity.getActivityId()!=null)
                        {
                            activities.add(activity);
                        }
                    }
                return JSONResult.ok(activities);
            }
        }

    }

    @Transactional(rollbackFor = Exception.class,propagation = Propagation.REQUIRED)
    public void updateDate(String date,String user_id,String activity_id) {
        String sql = "UPDATE user_activities SET last_involve_time="+"\'"+date+"\' WHERE user_id= \'"+user_id+"\' AND activity_id= \'"+activity_id+"\'";
        jdbcTemplate.update(sql);
    }
}



