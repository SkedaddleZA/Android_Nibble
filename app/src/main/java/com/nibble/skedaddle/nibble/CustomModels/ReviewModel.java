package com.nibble.skedaddle.nibble.CustomModels;

/**
 * Created by Chris on 2018/07/31.
 */

public class ReviewModel {

    String body;
    String date;
    String author;
    String rating;


    public ReviewModel(String body, String date, String author, String rating) {
        this.body=body;
        this.date=date;
        this.author=author;
        this.rating=rating;

    }

    public String getBody() {
        return body;
    }

    public String getDate() {
        return date;
    }

    public String getAuthor() {
        return author;
    }


    public String getRating() {
        return rating;
    }




}
