package com.restfood.myapplication;

import java.util.List;

public class OrderData {
    boolean Done;
    String Shop;  //this is for shop id
    String Status;
    int Total;
    String User;      //id of user
    List<String> foodList;
    List<String> quanList;

    public OrderData(boolean done, String shop, String status, int total, String user, List<String> foodList, List<String> quanList) {
        Done = done;
        Shop = shop;
        Status = status;
        Total = total;
        User = user;
        this.foodList = foodList;
        this.quanList = quanList;
    }

    public List<String> getFoodList() {
        return foodList;
    }

    public void setFoodList(List<String> foodList) {
        this.foodList = foodList;
    }

    public List<String> getQuanList() {
        return quanList;
    }

    public void setQuanList(List<String> quanList) {
        this.quanList = quanList;
    }

    public boolean isDone() {
        return Done;
    }

    public void setDone(boolean done) {
        Done = done;
    }

    public String getShop() {
        return Shop;
    }

    public void setShop(String shop) {
        Shop = shop;
    }

    public String getStatus() {
        return Status;
    }

    public void setStatus(String status) {
        Status = status;
    }

    public int getTotal() {
        return Total;
    }

    public void setTotal(int total) {
        Total = total;
    }

    public String getUser() {
        return User;
    }

    public void setUser(String user) {
        User = user;
    }
}
