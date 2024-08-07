package com.example.springdemo.utils;

/**
 * @Title: TokenUtil.java
 * @Package com.example.springdemo.utils
 * @Description: Token的拦截器，拦截非法访问
 */
public class TokenUtil {

    private final String token;

    public TokenUtil() {
        token = null;
    }

    public TokenUtil(String token) {
        this.token = token;
    }

    /**
     * @param
     * @return status
     * @Description: 解析token，返回状态码
     */
    public Integer parse() {
        Integer status;


        if (!token.isEmpty() && token.length() >= 10)
            return 200;
        else
            return 502;
    }

}
