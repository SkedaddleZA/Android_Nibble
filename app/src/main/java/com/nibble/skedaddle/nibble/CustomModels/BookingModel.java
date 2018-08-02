package com.nibble.skedaddle.nibble.CustomModels;

/**
 * Created by Chris on 2018/07/31.
 */

public class BookingModel {

    String restaurantname;
    String date;
    String guests;
    String time;


    public BookingModel(String restaurantname, String date, String guests, String time) {
        this.restaurantname=restaurantname;
        this.date=date;
        this.guests=guests;
        this.time=time;

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




}
