package com.nibble.skedaddle.nibble.CustomModels;

/**
 * Created by Chris on 2018/07/31.
 */

public class RestaurantModel {

        String name;
        String suburb;


        public RestaurantModel(String name, String suburb) {
            this.name=name;
            this.suburb=suburb;

        }

        public String getName() {
            return name;
        }

        public String getSuburb() {
            return suburb;
        }




}
