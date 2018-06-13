package com.nibble.skedaddle.nibble.classes;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

/**
 * Created by marno on 2018/06/09.
 */

public class MenuCategory {
    public MenuCategory(final String MenuCategoryID, final String RestaurantID, final String Name, final String Description, final String Active)
    {
        this.MenuCategoryID=MenuCategoryID;
        this.RestaurantID=RestaurantID;
        this.Name=Name;
        this.Description=Description;
        this.Active=Active;
    }
    public MenuCategory()
    {

    }
    DAL dl = new DAL();
    private String MenuCategoryID;
    private String RestaurantID;
    private String Name;
    private String Description;
    private String Active;

    public String getMenuCategoryID() {
        return MenuCategoryID;
    }
    public void setMenuCategoryID(String MenuCategoryID) {
        MenuCategoryID = MenuCategoryID;
    }

    public String getRestaurantID() {
        return RestaurantID;
    }
    public void setRestaurantID(String RestaurantID) {
        RestaurantID = RestaurantID;
    }

    public String getName() {
        return Name;
    }
    public void setName(String Name) {
        Name = Name;
    }

    public String getDescription() {
        return Description;
    }
    public void setDescription(String Description) {
        Description = Description;
    }

    public String getActive() {
        return Active;
    }
    public void setActive(String Active) {
        Active = Active;
    }

    public void GetMenuCategory(final String RestaurantID, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        dl.GetMenuCategory(RestaurantID, listener, requestQueue);
    }
}
