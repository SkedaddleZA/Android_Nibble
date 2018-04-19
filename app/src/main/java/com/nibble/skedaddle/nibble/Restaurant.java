package com.nibble.skedaddle.nibble;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

/**
 * Created by Chris on 2018/04/19.
 */

public class Restaurant {

    public Restaurant(final int RestaurantID, final String RestaurantName, final String Address, final String Phone, final String RestaurantType, final int RestaurantAdminID, final String GPSLocation, final String Email)
    {
        this.RestaurantID = RestaurantID;
        this.RestaurantName = RestaurantName;
        this.Address = Address;
        this.Phone = Phone;
        this.RestaurantType = RestaurantType;
        this.RestaurantAdminID = RestaurantAdminID;
        this.GPSLocation = GPSLocation;
        this.Email = Email;

    }

    public Restaurant()
    {

    }

    DAL dl = new DAL();

    public int getRestaurantID() {
        return RestaurantID;
    }

    public void setRestaurantID(int restaurantID) {
        RestaurantID = restaurantID;
    }

    public String getRestaurantName() {
        return RestaurantName;
    }

    public void setRestaurantName(String restaurantName) {
        RestaurantName = restaurantName;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getRetaurantType() {
        return RestaurantType;
    }

    public void setRetaurantType(String retaurantType) {
        RestaurantType = retaurantType;
    }

    public int getRestaurantAdminID() {
        return RestaurantAdminID;
    }

    public void setRestaurantAdminID(int restaurantAdminID) {
        RestaurantAdminID = restaurantAdminID;
    }

    public String getGPSLocation() {
        return GPSLocation;
    }

    public void setGPSLocation(String GPSLocation) {
        this.GPSLocation = GPSLocation;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    private int RestaurantID;
    private String RestaurantName;
    private String Address;
    private String Phone;
    private String RestaurantType;
    private int RestaurantAdminID;
    private String GPSLocation;
    private String Email;


    public void GetRestaurantTypes(Response.Listener<String> listener, RequestQueue requestQueue)
    {
        dl.GetRestaurantTypes(listener, requestQueue);
    }
    public void GetRestaurants(final String RestaurantType,Response.Listener<String> listener, RequestQueue requestQueue)
    {
        dl.GetRestaurants(RestaurantType, listener, requestQueue);
    }



}
