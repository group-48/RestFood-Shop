package com.restfood.myapplication;

import java.util.List;

public class OrderData {
    boolean Done;
    String Shop;  //this is for shop id
    String Status;
    int Total;
    String User;      //id of user
    List<String> Food_Names;
    List<String> Qty_List;

    public OrderData(boolean done, String shop, String status, int total, String user) {
        Done = done;
        Shop = shop;
        Status = status;
        Total = total;
        User = user;
    }

    public OrderData(boolean done, String shop, String status, int total, String user, List<String> foodList, List<String> quanList) {
        Done = done;
        Shop = shop;
        Status = status;
        Total = total;
        User = user;
        this.Food_Names = foodList;
        this.Qty_List = quanList;
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

    public List<String> getFood_Names() {
        return Food_Names;
    }

    public void setFood_Names(List<String> food_Names) {
        Food_Names = food_Names;
    }

    public List<String> getQty_List() {
        return Qty_List;
    }

    public void setQty_List(List<String> qty_List) {
        Qty_List = qty_List;
    }
}
