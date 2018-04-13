package com.nibble.skedaddle.nibble;

import android.graphics.PorterDuff;
import android.support.v7.app.AlertDialog;
import android.view.MotionEvent;
import android.view.View;

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



}
