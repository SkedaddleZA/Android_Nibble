package com.nibble.skedaddle.nibble.classes;

import com.android.volley.RequestQueue;
import com.android.volley.Response;

/**
 * Created by marno on 2018/06/05.
 */

public class FoodType {
    public FoodType(final String FoodTypeID, final String TypeName)
    {
        this.FoodtypeID=FoodTypeID;
        this.TypeName=TypeName;
    }
    public FoodType()
    {

    }

    DAL dl = new DAL();
    public String FoodtypeID;
    public String TypeName;

    public String getFoodtypeID() {
        return FoodtypeID;
    }
    public void setFoodtypeID(String FoodtypeID) {
        FoodtypeID = FoodtypeID;
    }

    public String getTypeName() {
        return TypeName;
    }
    public void setTypeName(String TypeName) {
        TypeName = TypeName;
    }

    public void GetFoodTypes(Response.Listener<String> listener, RequestQueue requestQueue)
    {
        dl.GetFoodTypes(listener, requestQueue);
    }


}
