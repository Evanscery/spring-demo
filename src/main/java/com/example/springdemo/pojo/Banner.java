package com.example.springdemo.pojo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;


public class Banner {
    private Icon icon;
    private Navigation navigation;
    private static Logger logger = LoggerFactory.getLogger("Banner");



    public Banner() {
        icon = new Icon();
        navigation = new Navigation();
    }

    public Banner(Banner banner) {
        this.icon = banner.icon;
        this.navigation = banner.navigation;
    }
   public Banner(Icon icon, Navigation navigation) {
        this.icon = icon;
        this.navigation = navigation;
    }

    public void getFromDb(JdbcTemplate jdbcTemplate) {
        /*TODO*/
        logger.info("获取banner信息从表");
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet("select * from banner");
        sqlRowSet.next();
        String url = sqlRowSet.getString("icon_url");
        Integer width = sqlRowSet.getInt("width");
        Integer height = sqlRowSet.getInt("height");
        this.icon = new Icon(url, width, height);

        String nav_url = sqlRowSet.getString("nav_url");
        Integer nav_type = sqlRowSet.getInt("nav_type");
        String appid = sqlRowSet.getString("appid");
        this.navigation = new Navigation(nav_type,nav_url,appid);
    }

    public Icon getIcon() {
        return icon;
    }
    public void setIcon(Icon icon) {
        this.icon = icon;
    }
    public Navigation getNavigation() {
        return navigation;
    }
    public void setNavigation(Navigation navigation) {
        this.navigation = navigation;
    }
}

class Icon{
    public String url;
    public Integer width;
    public Integer height;

    public Icon(){
            url = null;
            width = 0;
            height = 0;
    };

    public Icon(String url, Integer width, Integer height){
        this.url = url;
        this.width = width;
        this.height = height;
    }
    public Icon(Icon icon){
        this.url = icon.url;
        this.width = icon.width;
        this.height = icon.height;
    }

}

class Navigation{
    public Integer type;
    public String url;
    public String appid;

    public Navigation(){
    type = 0;
    url = null;
    appid = null;
    }
    public Navigation(Integer type, String url, String appid){
        this.type = type;
        this.url = url;
        this.appid = appid;
    }

    public Navigation(Integer type, String link){
        this.type = type;
        switch(type)
        {
            case 1:{
                this.appid = link;
                this.url = null;
                break;
            }
            case 3:{
                this.appid = null;
                this.url = link;
                break;
            }
            default:{
                this.url = null;
                this.appid = null;
                break;
            }
        }
    }
    public Navigation(Navigation navigation){
        this.type = navigation.type;
        this.url = navigation.url;
        this.appid = navigation.appid;
    }
}