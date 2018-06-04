package com.nibble.skedaddle.nibble.classes;

import android.content.ClipData;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

/**
 * Created by marno on 2018/06/03.
 */

public class MenuItems {
    public MenuItems(final String MenuItemI, final String MenuCategoryID, final String ItemName, final String ItemDescription, final String ItemPrice)
    {
        this.MenuItemID=MenuItemID;
        this.ItemName=ItemName;
        this.ItemDescription=ItemDescription;
        this.ItemPrice=ItemPrice;
        this.MenuCategoryID=MenuCategoryID;
    }
    public MenuItems()
    {

    }
    DAL dl = new DAL();

    private String MenuItemID;
    private String ItemName;
    private String ItemDescription;
    private String ItemPrice;
    private String MenuCategoryID;

    public String getMenuItemID() {
        return MenuItemID;
    }
    public void setMenuItemID(String MenuItemID) {
        MenuItemID = MenuItemID;
    }

    public String getItemName() {
        return ItemName;
    }
    public void setItemName(String ItemName) {
        ItemName = ItemName;
    }

    public String getItemDescription() {
        return ItemDescription;
    }
    public void setItemDescription(String ItemDescription) {
        ItemDescription = ItemDescription;
    }

    public String getItemPrice() {
        return ItemPrice;
    }
    public void setItemPrice(String ItemPrice) {
        ItemPrice = ItemPrice;
    }

    public String getMenuCategoryID() {
        return MenuCategoryID;
    }
    public void setMenuCategoryID(String MenuCategoryID) {
        MenuCategoryID = MenuCategoryID;
    }

    public void GetMenuInfo(final String MenuCategoryID, Response.Listener<String> listener, RequestQueue requestQueue)
    {
        dl.GetMenuInfo(MenuCategoryID, listener, requestQueue);
    }

}
