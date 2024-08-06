package com.example.springdemo.pojo;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Activity {
    private String activityId;
    private String title;
    private String subtitle;
    private Boolean isHighlight;
    private String iconUrl;

    public Activity(){}

    public Activity(String activityId, String title, String subtitle, Boolean isHighlight, String iconUrl) {
        this.activityId = activityId;
        this.title = title;
        this.subtitle = subtitle;
        this.isHighlight = isHighlight;
        this.iconUrl = iconUrl;
    }

    public void fromDb(JdbcTemplate jdbcTemplate, String activityId) {
        this.activityId = activityId;
        String sql = "select * from activities where activity_id = \'"+activityId+"\'";
        SqlRowSet sqlRowSet = jdbcTemplate.queryForRowSet(sql);
        if(sqlRowSet.next()){
            this.title = sqlRowSet.getString("title");
            this.subtitle = sqlRowSet.getString("subtitle");
            this.isHighlight = sqlRowSet.getBoolean("is_highlight");
            this.iconUrl = sqlRowSet.getString("icon_url");
        }
    }

    public String getActivityId() {return activityId;}
    public void setActivityId(String activityId) {this.activityId = activityId;}
    public String getTitle() {return title;}
    public void setTitle(String title) {this.title = title;}
    public String getSubtitle() {return subtitle;}
    public void setSubtitle(String subtitle) {this.subtitle = subtitle;}
    public Boolean getIsHighlight() {return isHighlight;}
    public void setIsHighlight(Boolean isHighlight) {this.isHighlight = isHighlight;}
    public String getIconUrl() {return iconUrl;}
    public void setIconUrl(String iconUrl) {this.iconUrl = iconUrl;}

    public String toUpdateSql()
    {
        return "UPDATE activities SET title =\'" + this.title +"\',subtitle = \'"+this.subtitle+"\',is_highlight = " + this.highlightStr() +
                " ,icon_url =\'" + this.iconUrl +"\' WHERE activity_id=\'" + this.activityId+"\'";
    }

    private String highlightStr()
    {
        if(this.isHighlight)
            return "1";
        else return "0";
    }


}
