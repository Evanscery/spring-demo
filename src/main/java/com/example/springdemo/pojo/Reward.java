package com.example.springdemo.pojo;

import org.springframework.jdbc.support.rowset.SqlRowSet;

public class Reward {
    private int id;
    private String icon;
    private String description;
    private Integer percentage;
    private int stock;


    public Reward() {}
    public Reward(int id, String icon, String description, Integer percentage,int stock) {
        this.id = id;
        this.icon = icon;
        this.description = description;
        this.percentage = percentage;
        this.stock = stock;

            }
    public static Reward Reward(SqlRowSet sqlRowSet)
    {
        int id = sqlRowSet.getInt("id");
        String icon = sqlRowSet.getString("icon");
        String description = sqlRowSet.getString("description");
        Integer percentage = sqlRowSet.getInt("percentage");
        int stock = sqlRowSet.getInt("stock");

        return new Reward(id, icon, description, percentage,stock);
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
    public String getIcon() {
        return icon;
    }
    public void setIcon(String icon) {
        this.icon = icon;
    }
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public Integer getPercentage() {
        return percentage;
    }
    public void setPercentage(Integer percentage) {
        this.percentage = percentage;
    }
    public int getStock() {
        return stock;
    }
    public void setStock(int stock) {
        this.stock = stock;
    }
}
