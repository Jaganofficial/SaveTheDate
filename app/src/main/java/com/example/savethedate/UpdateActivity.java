package com.example.savethedate;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.io.InputStream;
import java.net.URL;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class UpdateActivity extends AppCompatActivity {
    private CircleImageView imageView;
    private EditText name,nickname,date,note,phonenumber;
    private Button add , delete;
    private String sname,snickname,sdate,snote,sphonenumber,sid;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);
        imageView=findViewById(R.id.photo);
        name=findViewById(R.id.Name);
        nickname=findViewById(R.id.NickName);
        date=findViewById(R.id.Date);
        note=findViewById(R.id.Notes);
        phonenumber=findViewById(R.id.Phonenumber);
        add=findViewById(R.id.adddata);
        delete = findViewById(R.id.Delete);
        MyDatabase database = new MyDatabase(UpdateActivity.this);
        getdata();
        shownotification(name.getText().toString(),
                nickname.getText().toString(),
                note.getText().toString());
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                database.updatedata(sid, name.getText().toString(),
                nickname.getText().toString(),
                note.getText().toString(),
                date.getText().toString(),
                phonenumber.getText().toString());
                finish();
            }
        });
        delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               database.DeleteRow(sid);
               finish();
            }
        });

    }
    void getdata()
    {
        if(getIntent().hasExtra("Name") &&getIntent().hasExtra("NickName"))
        {
         sid=getIntent().getStringExtra("Id");
         sname=getIntent().getStringExtra("Name");
         snickname=getIntent().getStringExtra("NickName");
         snote=getIntent().getStringExtra("Note");
         sdate=getIntent().getStringExtra("Date");
         sphonenumber=getIntent().getStringExtra("PhoneNumber");
         ActionBar a1 = getSupportActionBar();
         if(a1!=null)
             a1.setTitle(snickname);
         name.setText(sname);
         nickname.setText(snickname);
         note.setText(snote);
         date.setText(sdate);
         phonenumber.setText(sphonenumber);
        }
        else
        {
            Toast.makeText(this, "No data", Toast.LENGTH_SHORT).show();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ObsoleteSdkInt")
    private void shownotification(String name, String nickname,String note)
    {
        int notificationId=new Random().nextInt(100);
        String channelID = "notification_channel_1";
        NotificationManager notificationManager=(NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        Intent i = new Intent(getApplicationContext(),UpdateActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(getApplicationContext(),0,i,PendingIntent.FLAG_UPDATE_CURRENT);
        NotificationCompat.Builder builder = new NotificationCompat.Builder(getApplicationContext(),channelID);
        builder.setSmallIcon(R.drawable.ic_baseline_notifications_active_24);
        builder.setDefaults(NotificationCompat.DEFAULT_ALL);
        builder.setContentTitle("Wish "+nickname);
    //    builder.setContentText();
        builder.setStyle(new NotificationCompat.BigTextStyle().bigText(note).setBigContentTitle("Today is "+ name+"'s Birthday"));
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setPriority(NotificationCompat.PRIORITY_MAX);

        if(notificationManager != null && notificationManager.getNotificationChannel(channelID) == null){
            NotificationChannel notificationChannel=new NotificationChannel(
                    channelID,"Notification Channel 1",NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription("This notification channel is used to notify you...:)");
            notificationChannel.enableVibration(true);
            notificationChannel.enableLights(true);

            notificationManager.createNotificationChannel(notificationChannel);
        }
        Notification notification = builder.build();
        if(notificationManager !=null)
        {
            notificationManager.notify(notificationId,notification);
        }
    }
    @SuppressLint("StaticFieldLeak")
    private void shownotificationwithimage()
    {
        new AsyncTask<String, Void , Bitmap>(){
            @Override
            protected Bitmap doInBackground(String... strings) {
                InputStream inputStream;
                try{
                    URL url = new URL(strings[0]);

                }
                catch (Exception e )
                {
                    //Toast.makeText(MainActivity.this, e.getMessage().toString(), Toast.LENGTH_SHORT).show();
                }

                return null;
            }
        };
    }
}