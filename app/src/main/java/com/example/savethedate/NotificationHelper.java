package com.example.savethedate;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

public class NotificationHelper  extends ContextWrapper {
    public static String channel1ID="CHANNEL1";
    public static String channel1Name="CHANNEL1";
    private NotificationManager mManager;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public NotificationHelper(Context base) {
        super(base);
        createchannel();
    }
    @RequiresApi(api = Build.VERSION_CODES.O)
    public  void createchannel(){
        NotificationChannel channel = new NotificationChannel(channel1ID,channel1Name, NotificationManager.IMPORTANCE_DEFAULT);
        channel.enableLights(true);
        channel.setLightColor(R.color.purple_200);
        channel.setLockscreenVisibility(Notification.VISIBILITY_PRIVATE);

        getManager().createNotificationChannel(channel);

    }
    public  NotificationManager getManager() {
        if (mManager == null) {
            mManager = (NotificationManager) getSystemService((Context.NOTIFICATION_SERVICE));
        }
        return mManager;
    }

public NotificationCompat.Builder getchannelnotification(String name, String nickname, String note, Bitmap bitmap)
{

    Intent i = new Intent(getApplicationContext(),UpdateActivity.class);
    i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
    PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,i,PendingIntent.FLAG_UPDATE_CURRENT);
    return  new NotificationCompat.Builder(getApplicationContext(),channel1ID)
   .setSmallIcon(R.drawable.ic_baseline_notifications_active_24)
   .setDefaults(NotificationCompat.DEFAULT_ALL)
    .setContentTitle("Wish "+nickname)
    //    builder.setContentText();
    //builder.setStyle(new NotificationCompat.BigTextStyle().bigText(note).setBigContentTitle("Today is "+ name+"'s Birthday"));
   .setLargeIcon(bitmap)
    .setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap).bigLargeIcon(null))
    .setContentIntent(pendingIntent)
    .setAutoCancel(true)
    .setLargeIcon(bitmap)
   .setPriority(NotificationCompat.PRIORITY_MAX);

}

}
