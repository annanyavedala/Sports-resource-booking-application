
package com.example.sportsapp;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BookedResource {

    @SerializedName("booking_time")
    @Expose
    private Object bookingTime;
    @SerializedName("day")
    @Expose
    private String day;
    @SerializedName("r_id")
    @Expose
    private Integer rId;
    @SerializedName("reservation_time")
    @Expose
    private String reservationTime;
    @SerializedName("resource_name")
    @Expose
    private String resourceName;
    @SerializedName("return_day")
    @Expose
    private Object returnDay;
    @SerializedName("return_time")
    @Expose
    private Object returnTime;
    @SerializedName("status")
    @Expose
    private Integer status;
    @SerializedName("user_id")
    @Expose
    private String userId;

    public Object getBookingTime() {
        return bookingTime;
    }

    public void setBookingTime(Object bookingTime) {
        this.bookingTime = bookingTime;
    }

    public String getDay() {
        return day;
    }

    public void setDay(String day) {
        this.day = day;
    }

    public Integer getRId() {
        return rId;
    }

    public void setRId(Integer rId) {
        this.rId = rId;
    }

    public String getReservationTime() {
        return reservationTime;
    }

    public void setReservationTime(String reservationTime) {
        this.reservationTime = reservationTime;
    }

    public String getResourceName() {
        return resourceName;
    }

    public void setResourceName(String resourceName) {
        this.resourceName = resourceName;
    }

    public Object getReturnDay() {
        return returnDay;
    }

    public void setReturnDay(Object returnDay) {
        this.returnDay = returnDay;
    }

    public Object getReturnTime() {
        return returnTime;
    }

    public void setReturnTime(Object returnTime) {
        this.returnTime = returnTime;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

}
