package com.restfood.myapplication;

import com.restfood.myapplication.ui.dashboard.OrderFoodData;

import java.util.ArrayList;
import java.util.List;


public class OrderData {
    private boolean done;
    private String notes;
    private String orderId;
    private String paymentMode;
    private String paymentStatus;
    private String status;
    private int total;
    private String user;


    //this is to retreve data as a string
    private String tempName;
    private String tempQty;



    //for food orderfoodata
    //private ArrayList<OrderFoodData> foodList;


    //this is to convert string to list
    public List<String> convertFoodName()
    {
        List<String> foodName=new ArrayList<>();      //this is to assign
        String temp;
        //temp=tempName.substring(1,)
//        int len=tempName.length();      //this is length of string
//        int i;      //this is to run a loop
//        char temp;      //get char and assign to this variable
//
//        //this is to get the index
//        int start,end;
//
//        //this loop travase all the string
//        for (i=0;i<len;i++)
//        {
//            temp=tempName.charAt(i);
//
//            if (i==len-1)
//            {
//
//            }
//
//
//        }


        return foodName;
    }


    public String getTempName() {
        return tempName;
    }

    public void setTempName(String tempName) {
        this.tempName = tempName;
    }

    public String getTempQty() {
        return tempQty;
    }

    public void setTempQty(String tempQty) {
        this.tempQty = tempQty;
    }

    public OrderData(boolean done, String notes, String orderId, String paymentMode, String paymentStatus, String status, int total, String user) {
        this.done = done;
        this.notes = notes;
        this.orderId = orderId;
        this.paymentMode = paymentMode;
        this.paymentStatus = paymentStatus;
        this.status = status;
        this.total = total;
        this.user = user;
    }

    //seeter and getter

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPaymentMode() {
        return paymentMode;
    }

    public void setPaymentMode(String paymentMode) {
        this.paymentMode = paymentMode;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

//    public ArrayList<OrderFoodData> getFoodNameList() {
//        return foodList;
//    }
//
//    public void setFoodNameList(ArrayList<OrderFoodData> foodNameList) {
//        this.foodList = foodNameList;
//    }
//
//    //this is to add food object to this
//    public void addFood(OrderFoodData obj)
//    {
//        this.foodList.add(obj);
//    }
}