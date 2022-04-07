package com.example.savethedate;

import android.app.AlarmManager;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Build;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.core.app.NotificationCompat;

public class AlermReciver extends BroadcastReceiver {
    private String name="hiii check",nickname="hii",note="1";
    private Bitmap bitmap;


    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        NotificationHelper nh = new NotificationHelper(context);
        name=intent.getStringExtra("Name");
        nickname=intent.getStringExtra("NickName");
        note=intent.getStringExtra("Note");
        bitmap=(Bitmap) intent.getParcelableExtra("Bitmap");
        NotificationCompat.Builder nb = nh.getchannelnotification(name,nickname,note,bitmap);
        nh.getManager().notify(1,nb.build());

    }
    public  void AlermReciverdata(String name, String nickname, String note, Bitmap bitmap)
    {
        this.name =name;
        this.nickname = nickname;
        this.note=note;
        this.bitmap=bitmap;
    }

}
