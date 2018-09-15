package com.nibble.skedaddle.nibble.classes;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by Chris on 2018/09/15.
 */

public class SaveSharedPreference
{
    static final String PREF_USER_NAME= "username";
    static final String PREF_USER_PASS= "password";
    static final String PREF_USER_BAL= "bAL";

    static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    public static void setUserName(Context ctx, String userName)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_NAME, userName);
        editor.commit();
    }
    public static void setPassWord(Context ctx, String passWord)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_PASS, passWord);
        editor.commit();
    }
    public static void setbAL(Context ctx, String bAL)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_USER_BAL, bAL);
        editor.commit();
    }


    public static String getUserName(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_NAME, "");
    }

    public static String getPassWord(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_PASS, "");
    }
    public static String getbAL(Context ctx)
    {
        return getSharedPreferences(ctx).getString(PREF_USER_BAL, "");
    }

    public static void clearUserName(Context ctx)
    {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear(); //clear all stored data
        editor.commit();
    }

}