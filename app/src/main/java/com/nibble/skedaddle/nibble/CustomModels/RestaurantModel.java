package com.nibble.skedaddle.nibble.CustomModels;

/**
 * Created by Chris on 2018/07/31.
 */

public class RestaurantModel {

        String name;
        String suburb;
        String rating;


        public RestaurantModel(String name, String suburb, String rating) {
            this.name=name;
            this.suburb=suburb;
            this.rating=rating;

        }

        public String getName() {
            return name;
        }

        public String getSuburb() {
            return suburb;
        }

         public String getRating() {
        return rating;
    }





}
