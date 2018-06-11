package com.nibble.skedaddle.nibble.classes;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

import java.sql.Time;
import java.util.Date;

/**
 * Created by marno on 2018/06/03.
 */

public class BookingRequest {
    public BookingRequest(final String BookingRequestID,final String CustomerID, final String NumOFGuests, final String Comment, final String Status, final String Accepted, final String Date, final String Time)
    {
        this.BookingRequestID=BookingRequestID;
        this.CustomerID=CustomerID;
        this.NumOFGuests=NumOFGuests;
        this.Comment=Comment;
        this.Status=Status;
        this.Accepted=Accepted;
        this.Date=Date;
        this.Time=Time;

    }
    public BookingRequest()
    {

    }
    DAL dl = new DAL();

    private String BookingRequestID;
    private String CustomerID;
    private String NumOFGuests;
    private String Comment;
    private String Status;
    private String Accepted;
    private String Date;
    private String Time;

    public String getBookingRequestID() {
        return BookingRequestID;
    }
    public void setBookingRequestID(String BookingRequestID) {
        BookingRequestID = BookingRequestID;
    }

    public String getCustomerID() {
        return CustomerID;
    }
    public void setCustomerID(String CustomerID) {
        CustomerID = CustomerID;
    }

    public String getNumOFGuests() {
        return NumOFGuests;
    }
    public void setNumOFGuests(String NumOFGuests) {
        NumOFGuests = NumOFGuests;
    }

    public String getComment() {
        return Comment;
    }
    public void setComment(String Comment) {
        Comment = Comment;
    }

    public String getStatus() {
        return Status;
    }
    public void setStatus(String Status) {
        Status = Status;
    }

    public String getAccepted() {
        return Accepted;
    }
    public void setAccepted(String Accepted) {
        Accepted = Accepted;
    }

    public String getDate() {
        return Date;
    }
    public void setDate(String Date) {
        Date = Date;
    }

    public String getTime() {
        return Time;
    }
    public void setTime(String Time) {
        Time = Time;
    }

    public void UpdateBookingStatus(final String BookingRequestID, final String Status, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        dl.UpdateBookingStatus(BookingRequestID, Status, listener, requestQueue);
    }

    public void GetBookingRequests(final String CustomerID, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        dl.GetBookingRequests(CustomerID, listener, requestQueue);
    }

    public void GetBookingDetails(final String BookingRequestID, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        dl.GetBookingDetails(BookingRequestID, listener, requestQueue);
    }


}
