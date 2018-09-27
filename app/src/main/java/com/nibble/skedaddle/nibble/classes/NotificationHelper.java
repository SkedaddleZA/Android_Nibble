package com.nibble.skedaddle.nibble.classes;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.os.Build;
import android.support.v4.app.NotificationCompat;

import com.nibble.skedaddle.nibble.R;
import com.nibble.skedaddle.nibble.activities.HomeActivity;

/**
 * Created by Chris on 2018/08/26.
 */

public class NotificationHelper extends ContextWrapper {
    public static final String channel1ID = "channel1ID";
    public static final String channel1Name = "channel 1";
    public static final String channel2ID = "channel2ID";
    public static final String channel2Name = "channel 2";
    private NotificationManager mManager;
    public NotificationHelper(Context base) {
        super(base);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            createChannels();}
    }
    @TargetApi(Build.VERSION_CODES.O)
    public void createChannels(){
        NotificationChannel channel1 = new NotificationChannel(channel1ID, channel1Name,
                NotificationManager.IMPORTANCE_DEFAULT);
        channel1.enableLights(true);
        channel1.enableVibration(true);
        channel1.setLightColor(R.color.colorPrimary);
        channel1.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);
        getManager().createNotificationChannel(channel1);
    }
    public NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);}
        return mManager;
    }
    public NotificationCompat.Builder getChannel1Notification(String name, String datetime) {
        return new NotificationCompat.Builder(getApplicationContext(), channel1ID)
                .setContentTitle("Reminder: You have booking at " + name)
                .setContentText("At " + datetime + " today.")
                .setSmallIcon(R.drawable.nibble_icon);
    }

}
