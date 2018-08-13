package com.nibble.skedaddle.nibble.classes;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

/**
 * Created by s216446546 on 2018/08/13.
 */

public class Review {
    public Review(final String reviewid, final String customerid, final String restaurantid, final String active, final String date, final String comment, final String rating )
    {
        this.reviewid=reviewid;
        this.customerid =customerid;
        this.restaurantid=restaurantid;
        this.active= active;
        this.date=date;
        this.comment=comment;
        this.rating=rating;

    }

    public Review()
    {

    }
    DAL dl= new DAL();
    private String reviewid;
    private String customerid;
    private String restaurantid;
    private String active;
    private String date;
    private String comment;
    private String rating;

    public String getReviewid() {
        return reviewid;
    }
    public void setReview(String reviewid) {
        reviewid = reviewid;
    }

    public String getCustomerid() {
        return customerid;
    }
    public void setCustomerid(String customerid) {
        customerid = customerid;
    }

    public String getRestaurantid() {
        return restaurantid;
    }
    public void setRestaurant(String restaurantid) {
        restaurantid = restaurantid;
    }

    public String getActivective() {
        return active;
    }
    public void setActive(String active) {
        active = active;
    }

    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        date = date;
    }

    public String getComment() {
        return comment;
    }
    public void setComment(String comment) {
        comment = comment;
    }

    public String getRating() {
        return rating;
    }
    public void setRating(String rating) {
        rating = rating;
    }

    public void GetReviews(final String Restaurantid, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        dl.GetReviews(Restaurantid, listener, requestQueue);
    }










}
