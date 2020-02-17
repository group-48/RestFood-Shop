package com.restfood.myapplication.ui.notifications;

public class ReservationData {
    private String bookingId;
    private String date;
    private String guestno;
    private String shopId;
    private String shopName;
    private String status;
    private String time;
    private String userId;

    public ReservationData() {

    }

    public ReservationData(String bookingId, String date, String guestno, String shopId, String shopName, String status, String time, String userId) {
        this.bookingId = bookingId;
        this.date = date;
        this.guestno = guestno;
        this.shopId = shopId;
        this.shopName = shopName;
        this.status = status;
        this.time = time;
        this.userId = userId;
    }

    public String getBookingId() {
        return bookingId;
    }

    public void setBookingId(String bookingId) {
        this.bookingId = bookingId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getGuestno() {
        return guestno;
    }

    public void setGuestno(String guestno) {
        this.guestno = guestno;
    }

    public String getShopId() {
        return shopId;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
