package com.nibble.skedaddle.nibble.classes;

import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.view.View;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;


import java.util.HashMap;
import java.util.Map;

/**
 * Created by s216431174 on 2018/04/13.
 */

class DAL {
    //declaration of global variables

    private static final String insertUrl = "http://SICT-IIS.nmmu.ac.za/skedaddle/registerCustomer.php";
    private static final String loginUrl = "http://SICT-IIS.nmmu.ac.za/skedaddle/loginCustomer.php";
    private static final String restaurantTypeUrl = "http://SICT-IIS.nmmu.ac.za/skedaddle/restaurantTypes.php";
    private static final String restaurantsByTypeUrl ="http://SICT-IIS.nmmu.ac.za/skedaddle/getRestaurantsByType.php";
    private static final String restaurantsByFoodTypeUrl ="http://SICT-IIS.nmmu.ac.za/skedaddle/getRestaurantsByFoodType.php";
    private static final String requestBookingUrl = "http://SICT-IIS.nmmu.ac.za/skedaddle/bookingRequest.php";
    private static final String restaurantDetails ="http://SICT-IIS.nmmu.ac.za/skedaddle/getRestaurantDetails.php";
    private static final String myBookingrequest ="http://SICT-IIS.nmmu.ac.za/skedaddle/getBookingRequest.php";
    private static final String updateStatus="http://SICT-IIS.nmmu.ac.za/skedaddle/UpdateStatus.php";
    private static final String menuInfo="http://SICT-IIS.nmmu.ac.za/skedaddle/getMenuInfo.php";
    private static final String foodType="http://SICT-IIS.nmmu.ac.za/skedaddle/foodType.php";
    private static final String menuCategory="http://SICT-IIS.nmmu.ac.za/skedaddle/getMenuCategory.php";
    private static final String locationsUrl ="http://SICT-IIS.nmmu.ac.za/skedaddle/getLocations.php";
    private static final String restaurantsByLocationUrl ="http://SICT-IIS.nmmu.ac.za/skedaddle/getRestaurantsByLocation.php";
    private static final String getBookingDetails = "http://SICT-IIS.nmmu.ac.za/skedaddle/getBookingDetails.php";

   //Insert customer procedure
    public void InsertCustomer(final String FirstName, final String LastName, final String Email, final String Phone, final String Password, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, insertUrl, listener, new Response.ErrorListener() {//Create a StringRequest which POSTS data to the database then records the response in listener variable
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("firstname", FirstName);
                parameters.put("lastname", LastName);
                parameters.put("email", Email);
                parameters.put("phone", Phone);
                parameters.put("password", Password);

                return parameters;

            }
        };
        requestQueue.add(request);//Runs the request which POSTS the data
    }

    public void LoginCustomer(final String Email, final String Password, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, loginUrl, listener ,new Response.ErrorListener() {
        @Override
        public void onErrorResponse(VolleyError error) {

        }
    }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("email", Email);
                parameters.put("password", Password);

                return parameters;

            }

        };
        requestQueue.add(request);
    }

    public void GetRestaurantTypes(Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.GET, restaurantTypeUrl, listener ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
        };
        requestQueue.add(request);
    }

    public void GetRestaurants(final String RestaurantType, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, restaurantsByTypeUrl, listener ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("restauranttype", RestaurantType);

                return parameters;

            }

        };
        requestQueue.add(request);
    }

    public void GetRestaurantDetails(final String RestaurantID, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, restaurantDetails, listener ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("restaurantid", RestaurantID);

                return parameters;

            }

        };
        requestQueue.add(request);
    }

    public void RequestBooking(final String CustomerID, final String RestaurantID, final String NumOfGuests, final String Comment, final String RequestDateTime, final String Date, final String Time, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, requestBookingUrl, listener, new Response.ErrorListener() {//Create a StringRequest which POSTS data to the database then records the response in listener variable
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("customerid", CustomerID);
                parameters.put("restaurantid", RestaurantID);
                parameters.put("numofguests", NumOfGuests);
                parameters.put("comment", Comment);
                parameters.put("requestdatetime", RequestDateTime);
                parameters.put("date", Date);
                parameters.put("time", Time);

                return parameters;

            }
        };
        requestQueue.add(request);//Runs the request which POSTS the data
    }

    public void UpdateBookingStatus(final String BookingRequestID, final String Status, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, updateStatus, listener, new Response.ErrorListener() {//Create a StringRequest which POSTS data to the database then records the response in listener variable
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("bookingrequestid", BookingRequestID);
                parameters.put("status", Status);

                return parameters;

            }
        };
        requestQueue.add(request);//Runs the request which POSTS the data
    }

    public void GetMenuInfo(final String Name, final String RestaurantID, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, menuInfo, listener ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("name", Name);
                parameters.put("restaurantid", RestaurantID);

                return parameters;

            }

        };
        requestQueue.add(request);
    }

    public void GetRestaurantsByFoodType(final String TypeName, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, restaurantsByFoodTypeUrl, listener ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("typename", TypeName);

                return parameters;

            }

        };
        requestQueue.add(request);
    }

    public void GetBookingRequests(final String CustomerID, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, myBookingrequest, listener ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("customerid", CustomerID);

                return parameters;

            }

        };
        requestQueue.add(request);
    }

    public void GetFoodTypes(Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.GET, foodType, listener ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
        };
        requestQueue.add(request);
    }

    public void GetMenuCategory(final String RestaurantID, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, menuCategory, listener ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("restaurantid", RestaurantID);

                return parameters;

            }
        };
        requestQueue.add(request);
    }

    public void GetLocations(Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.GET, locationsUrl, listener ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
        };
        requestQueue.add(request);
    }

    public void GetRestaurantsByLocation(final String SuburbName, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, restaurantsByLocationUrl, listener ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("suburbname", SuburbName);

                return parameters;

            }

        };
        requestQueue.add(request);
    }

    public void GetBookingDetails(final String BookingRequestID, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        StringRequest request = new StringRequest(Request.Method.POST, getBookingDetails, listener ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<String, String>();
                parameters.put("bookingrequestid", BookingRequestID);

                return parameters;

            }

        };
        requestQueue.add(request);
    }



}



