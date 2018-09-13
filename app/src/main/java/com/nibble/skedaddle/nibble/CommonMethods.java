package com.nibble.skedaddle.nibble;

import android.content.Context;
import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.MotionEvent;
import android.view.View;

import com.nibble.skedaddle.nibble.activities.MyProfile;

import java.security.MessageDigest;

/**
 * Created by s216431174 on 2018/04/13.
 */

public class CommonMethods {
    public static void buttonEffect(View button){
        button.setOnTouchListener(new View.OnTouchListener() {

            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN: {
                        v.getBackground().setColorFilter(0x363e34, PorterDuff.Mode.SRC_ATOP);
                        v.invalidate();
                        break;
                    }
                    case MotionEvent.ACTION_UP: {
                        v.getBackground().clearColorFilter();
                        v.invalidate();
                        break;
                    }
                }
                return false;
            }
        });
    }

    public static String sha256(String base) {
        try{
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(base.getBytes("UTF-8"));
            StringBuffer hexString = new StringBuffer();

            for (int i = 0; i < hash.length; i++) {
                String hex = Integer.toHexString(0xff & hash[i]);
                if (hex.length() == 1) hexString.append('0');
                hexString.append(hex);
            }
            return hexString.toString();
        } catch (Exception ex){
            throw new RuntimeException(ex);
        }
    }

    public static boolean CheckPhone(String phone)
    {
        int count = 0;
        for (int i = 0, len = phone.length(); i < len; i++) {
            if (Character.isDigit(phone.charAt(i))) {
                count++;
            }
            else{
                return false;
            }
        }
        if(count == 10)
        {
            return true;
        }
        else
        {
            return false;
        }

    }

    public static boolean CheckEmail(CharSequence email)
    {
        return (!TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches());
    }



}
