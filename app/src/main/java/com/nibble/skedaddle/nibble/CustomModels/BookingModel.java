package com.nibble.skedaddle.nibble.CustomModels;

/**
 * Created by Chris on 2018/07/31.
 */

public class BookingModel {

    String restaurantname;
    String date;
    String guests;
    String time;
    String status;


    public BookingModel(String restaurantname, String date, String guests, String time, String status) {
        this.restaurantname=restaurantname;
        this.date=date;
        this.guests=guests;
        this.time=time;
        this.status=status;

    }

    public String getRestaurantName() {
        return restaurantname;
    }

    public String getDate() {
        return date;
    }

    public String getGuests() {
        return guests;
    }

    public String getTime() {
        return time;
    }

    public String getStatus() {
        return status;
    }




}
