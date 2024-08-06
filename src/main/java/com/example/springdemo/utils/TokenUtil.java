package com.example.springdemo.utils;

import org.apache.el.parser.Token;

/**
 *
 * @Title: TokenUtil.java
 * @Package com.example.springdemo.utils
 * @Description: Token的拦截器，拦截非法访问
 */
public class TokenUtil {

    private String token;
    public TokenUtil(){
        token = null;
    }

    public TokenUtil(String token)
    {
        this.token = token;
    }

        /**
     *
     * @Description: 解析token，返回状态码
     * @param
     * @return status
     *
     */
    public Integer parse()
    {
        Integer status;


        if(!token.isEmpty()&&token.length() >=10)
            return 200;
        else
            return 502;
    }

}
