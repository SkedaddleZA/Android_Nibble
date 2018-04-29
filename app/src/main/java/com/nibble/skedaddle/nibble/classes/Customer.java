package com.nibble.skedaddle.nibble.classes;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

/**
 * Created by s216431174 on 2018/04/13.
 */

public class Customer {

    public Customer(final int CustomerID, final String FirstName, final String LastName, final String Email, final String Phone, final String Password)
    {
        this.CustomerID = CustomerID;
         this.FirstName = FirstName;
         this.LastName = LastName;
         this.Email = Email;
         this.Phone = Phone;
         this.Password = Password;
    }

    public Customer()
    {

    }

    DAL dl = new DAL();

    private int CustomerID;
    private String FirstName;
    private String LastName;
    private String Email;
    private String Phone;
    private String Password;



    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }



    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }

    public void InsertCustomer(final String FirstName, final String LastName, final String Email, final String Phone, final String Password, Response.Listener<String> listener, RequestQueue requestQueue)
    {
       dl.InsertCustomer(FirstName, LastName, Email, Phone, Password, listener, requestQueue);
    }
    public void LoginCustomer(final String Email, final String Password, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        dl.LoginCustomer(Email, Password, listener, requestQueue);
    }
    public void RequestBooking(final String CustomerID, final String RestaurantID, final String DateTime, final String NumOfGuests, final String Comment, final String RequestDateTime, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        dl.RequestBooking(CustomerID, RestaurantID, DateTime, NumOfGuests, Comment, RequestDateTime, listener, requestQueue);
    }



}
