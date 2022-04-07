package com.example.savethedate;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

public class MainActivity extends AppCompatActivity {
    private FloatingActionButton add1;
    private RecyclerView recyclerView;
    private Adapter adapter;
    MyDatabase myDatabase;
    ArrayList<String> dname,dnickname,dnote,dphonenumber,ddate,did;
    ArrayList<String> dimage;
    ArrayList<Long>millies;
    MyDatabase database;
    ImageView introimage,ballons;
    TextView t1,t2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        add1 = findViewById(R.id.datafrienddataofbirth);
        recyclerView=findViewById(R.id.RecyclerView);
        myDatabase = new MyDatabase(MainActivity.this);
        dname = new ArrayList<>();
        dnickname = new ArrayList<>();
        dnote = new ArrayList<>();
        ddate = new ArrayList<>();
        dphonenumber = new ArrayList<>();
        dimage =  new ArrayList<>();
        millies=new ArrayList<>();
        introimage = findViewById(R.id.introimage);
        ballons=findViewById(R.id.imageView4);
        t1=findViewById(R.id.introtext);
        t2=findViewById(R.id.introtext2);
        did=new ArrayList<>();
        display();
        adapter = new Adapter(MainActivity.this,MainActivity.this,did,dname,dnickname,ddate,dnote,dphonenumber,dimage,millies);
        database=new MyDatabase(MainActivity.this);
        add1.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MainActivity.this,addnewdataoffriends.class);
                startActivityForResult(i,2);

            }
        });
        //ActionBar a1=getSupportActionBar();
        //a1.hide();

        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable  Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 || requestCode == 2)
        {
            recreate();
        }
    }




    void display()
{
    introimage = findViewById(R.id.introimage);
    t1=findViewById(R.id.introtext);
    t2=findViewById(R.id.introtext2);
    Cursor c = myDatabase.readalldata();
    if(c.getCount()==0)
    {

    }
    else
    {
        introimage.setVisibility(View.GONE);
        t1.setVisibility(View.GONE);
        t2.setVisibility(View.GONE);
        ballons.setVisibility(View.GONE);

        while (c.moveToNext())
        {
            did.add(c.getString(0));
            dname.add(c.getString(1));
            dnickname.add(c.getString(2));
            dnote.add(c.getString(3));
            dphonenumber.add(c.getString(4));
            ddate.add(c.getString(5));
            dimage.add(c.getString(6) );
            millies.add(c.getLong(7));
        }
    }
}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.mymenu,menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.removeall)
        {
            database.DeleteAll();
            Intent i = new Intent(MainActivity.this,MainActivity.class);
            startActivity(i);
            finish();

        }
        return super.onOptionsItemSelected(item);
    }


}
