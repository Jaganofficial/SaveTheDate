package com.example.savethedate;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.session.PlaybackState;
import android.net.Uri;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.NotificationCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.sql.Blob;
import java.util.ArrayList;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class Adapter extends RecyclerView.Adapter<Adapter.MyViewHolder> {
   private Context context;
   private ArrayList dname,dnickname,ddate,dnote,dphonenumber,id;
   private ArrayList image,millies;
   private Activity activity;
   private Animation animation1;


   Adapter(Activity activity,Context context1,ArrayList id,ArrayList a,ArrayList b,ArrayList c,ArrayList d,ArrayList f,ArrayList h,ArrayList m)
   {
       this.id = id;
       dname = a;
       dnickname=b;
       ddate=c;
       dnote=d;
       dphonenumber=f;

       image =h;
       context=context1;
       millies=m;
       this.activity=activity;
   }

    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.layoutofdatas,parent,false);
        return new MyViewHolder(view);

    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        holder.nickname.setText(String.valueOf(dnickname.get(position)));
        holder.name.setText(String.valueOf(dname.get(position)));
        holder.date.setText(String.valueOf(ddate.get(position)));
        Uri uri = Uri.parse(String.valueOf(image.get(position)));
        holder.photoinmain.setImageURI(uri);

        // Retrieve the selected image as byte[]
      //  byte[] data = (byte[]) image.get(position);
        // Convert to Bitmap
        //Bitmap image1 = toBitmap(data);
        // Set to the imgPlace
      //  holder.photoinmain.setImageBitmap(image1);


        holder.layout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(context, UpdateActivity.class);
                i.putExtra("Id", String.valueOf(id.get(position)));
                i.putExtra("Name", String.valueOf(dname.get(position)));
                i.putExtra("NickName", String.valueOf(dnickname.get(position)));
                i.putExtra("Date", String.valueOf(ddate.get(position)));
                i.putExtra("Note", String.valueOf(dnote.get(position)));
                i.putExtra("PhoneNumber", String.valueOf(dphonenumber.get(position)));
                // i.putExtra("Imagedata",String.valueOf(image.get(position)));
                activity.startActivityForResult(i, 1);
            }
        });
      /*  BitmapFactory.decodeByteArray(image, 0, image.length);
        Picasso.with(context).load().into(new Target() {
            @Override
            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                // Set it in the ImageView
                imageView.setImageBitmap(bitmap);
            }
            @Override
            public void onBitmapFailed(Drawable errorDrawable) {
            }
            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        });*/



    }

    private Bitmap toBitmap(byte[] data) {
        return BitmapFactory.decodeByteArray(data, 0, data.length);
    }

    @Override
    public int getItemCount() {
        return dname.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
       TextView name,nickname,date,days;
       CircleImageView photoinmain;
       ConstraintLayout layout;
        public MyViewHolder( View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.Nameatmain);
            nickname=itemView.findViewById(R.id.Nicknameatmain);
            date=itemView.findViewById(R.id.Dateofbirthatmain);
            //days=itemView.findViewById(R.id.Days);
            photoinmain=itemView.findViewById(R.id.photoinmainactivity);
            layout = itemView.findViewById(R.id.constraintlayout);
            animation1 = AnimationUtils.loadAnimation(context,R.anim.animation);
            layout.setAnimation(animation1);
        }
    }


}
