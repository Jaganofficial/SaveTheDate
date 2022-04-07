package com.example.savethedate;

import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;
import androidx.core.net.ParseException;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageDecoder;
import android.icu.util.Calendar;
import android.icu.util.TimeZone;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;
import android.widget.Toolbar;

import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import de.hdodenhof.circleimageview.CircleImageView;

public class addnewdataoffriends extends AppCompatActivity {
    private CircleImageView imageView;
    private EditText name,nickname,note,phonenumber;
    private Button add,date,time;

    private  static final int Camerrequestcode=100;
    private  static final int Storagerequestcode=101;
    private  static final int Imagepickcode=102;
    private  static final int galleryrequestcode=103;

    private String[] camerapermission;
    private  String[] storagepermission;
    private long millisec;
    private Uri imageuri;
    private Bitmap bitmap1;
    int sh,sm;

    private Calendar c;

    private NotificationHelper nh ;
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addnewdataoffriends);
        imageView=findViewById(R.id.uphoto);
        name=findViewById(R.id.uName);
        nickname=findViewById(R.id.uNickName);
        date=findViewById(R.id.uDate);
        note=findViewById(R.id.uNotes);
        phonenumber=findViewById(R.id.uPhonenumber);
        add=findViewById(R.id.uadddata);
        time = findViewById(R.id.uTime);
        camerapermission = new String[]{Manifest.permission.CAMERA,Manifest.permission.WRITE_EXTERNAL_STORAGE};
        storagepermission = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE};
        nh = new NotificationHelper(this);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imagepicker();
            }
        });
        add.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.O)
            @Override
            public void onClick(View v) {
                MyDatabase db = new MyDatabase(addnewdataoffriends.this);
                db.adddata(name.getText().toString(),nickname.getText().toString(),date.getText().toString(),note.getText().toString(),phonenumber.getText().toString(),""+imageuri,millisec);
                AlermReciver am = new AlermReciver();

                am.AlermReciverdata(name.getText().toString(),
                        nickname.getText().toString(),
                        note.getText().toString(),bitmap1);

                shownotification(name.getText().toString(),
                        nickname.getText().toString(),
                        note.getText().toString(),bitmap1);
                finish();
            }
        });

        date.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                DatePickerDialog datepicker = new DatePickerDialog(addnewdataoffriends.this,
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                String dateformate = dayOfMonth + "/" + (monthOfYear + 1) + "/" + year;
                                        date.setText(dateformate);
                                Date date = new Date();
                                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                                try {
                                    date = formatter.parse(dateformate);
                                } catch (ParseException | java.text.ParseException e) {
                                    e.printStackTrace();
                                }

                                millisec =  date.getTime();
                            }
                        }, year, month, day);
                datepicker.show();

            }
        });
        time.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int hour = cldr.get(Calendar.HOUR_OF_DAY);
                int minutes = cldr.get(Calendar.MINUTE);
                // time picker dialog
                TimePickerDialog picker = new TimePickerDialog(addnewdataoffriends.this,
                        new TimePickerDialog.OnTimeSetListener() {
                            @Override
                            public void onTimeSet(TimePicker tp, int sHour, int sMinute) {
                                int hour = sHour;
                                int minutes = sMinute;

                                String timeSet = "";
                                if (hour > 12) {
                                    hour -= 12;
                                    timeSet = "PM";
                                } else if (hour == 0) {
                                    hour += 12;
                                    timeSet = "AM";
                                } else if (hour == 12){
                                    timeSet = "PM";
                                }else{
                                    timeSet = "AM";
                                }

                                String min = "";
                                if (minutes < 10)
                                    min = "0" + minutes ;
                                else
                                    min = String.valueOf(minutes);

                                String mTime = new StringBuilder().append(hour).append(':')
                                        .append(min ).append(" ").append(timeSet).toString();

                                time.setText(mTime);
                                sh=sHour;
                                sm=sMinute;
                            }

                        }, hour, minutes, false);
                picker.show();

                c = Calendar.getInstance();
               c.set(Calendar.HOUR_OF_DAY,sh);
               c.set(Calendar.MINUTE,sm);
               c.set(Calendar.SECOND,0);

            }

        });

    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /*if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                Uri resultUri = result.getUri();
                imageView.setImageURI(resultUri);

                try {
                    mbitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), resultUri);

                } catch (IOException e) {
                    e.printStackTrace();
                }
                bytearray = getBytes(mbitmap);

            } else if (resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception error = result.getError();
            }*/

        if(resultCode == RESULT_OK)
        {
            if(requestCode == galleryrequestcode)
            {
                CropImage.activity(data.getData()).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
            }
            else if(requestCode == Imagepickcode)
            {
                CropImage.activity(imageuri).setGuidelines(CropImageView.Guidelines.ON).setAspectRatio(1,1).start(this);
            }
            else if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE){
                CropImage.ActivityResult result = CropImage.getActivityResult(data);
                if(resultCode == RESULT_OK)
                {
                    Uri cropuri = result.getUri();
                    imageuri = cropuri;
                    try {
                        bitmap1 = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageuri);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    imageView.setImageURI(imageuri);
                }
                else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE)
                {
                    Exception e = result.getError();
                    Toast.makeText(this, ""+e, Toast.LENGTH_SHORT).show();
                }
            }
        }





    }
    public static byte[] getBytes(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);
        return stream.toByteArray();
    }
    private boolean checkStoragepermission()
    {

        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return  result;
    }
    private void requeststoragepermission()
    {
        ActivityCompat.requestPermissions(this,storagepermission,Storagerequestcode);
    }
    private boolean checkCamerapermission()
    {
        Toast.makeText(this, "2", Toast.LENGTH_SHORT).show();
        boolean result = ContextCompat.checkSelfPermission(this,Manifest.permission.CAMERA)==(PackageManager.PERMISSION_GRANTED);
        boolean result1=ContextCompat.checkSelfPermission(this,Manifest.permission.WRITE_EXTERNAL_STORAGE)==(PackageManager.PERMISSION_GRANTED);
        return result && result1;
    }
    private void requestcamerapermission()
    {
        //Toast.makeText(this, "method", Toast.LENGTH_SHORT).show();
        ActivityCompat.requestPermissions(this,camerapermission,Camerrequestcode);
    }
    private void imagepicker()
    {
        String[] option = {"Camera", "Gallery"};
        AlertDialog.Builder build = new AlertDialog.Builder(this);

        build.setTitle("Select for Image");
        build.setItems(option, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                if(which == 0)
                {
                    if(!checkCamerapermission()){
                        requestcamerapermission();

                    }
                    else
                    {
                        try {
                            pickFromCamera();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                }
                else if (which == 1)
                {
                    if(!checkStoragepermission()){
                        requeststoragepermission();
                    }
                    else {
                        pickFromStorage();                   }
                }

            }


        });
        build.create().show();
    }
    private void pickFromCamera() throws IOException {
        ContentValues values = new ContentValues();
        values.put(MediaStore.Images.Media.TITLE,"Image title");
        values.put(MediaStore.Images.Media.DESCRIPTION,"Image description");

        imageuri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI,values);

        Intent camerapicker = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        camerapicker.putExtra(MediaStore.EXTRA_OUTPUT,imageuri);
        startActivityForResult(camerapicker,Imagepickcode);


    }


    private void pickFromStorage() {
    Intent galleryintent = new Intent(Intent.ACTION_PICK);
    galleryintent.setType("image/*");
    startActivityForResult(galleryintent,galleryrequestcode);
    }



    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull  String[] permissions, @NonNull  int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case Camerrequestcode: {
                if (grantResults.length > 0) {
                    boolean cameraaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    boolean storagepermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;

                    if (cameraaccepted && storagepermission) {
                        try {
                            pickFromCamera();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else {
                        Toast.makeText(this, "Camera Permission Required!", Toast.LENGTH_SHORT).show();
                    }
                }
                break;
            }
            case Storagerequestcode: {
                if (grantResults.length > 0) {
                    boolean storageaaccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;

                    if (storageaaccepted) {
                        pickFromStorage();
                    } else {
                        Toast.makeText(this, "Storage Permission Required!", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.O)
    @SuppressLint("ObsoleteSdkInt")
    private void shownotification(String name, String nickname,String note,Bitmap bitmap2)
    {

      startalarm();
       // NotificationCompat.Builder nb = nh.getchannelnotification(name,nickname,note,bitmap2);
        //nh.getManager().notify(1,nb.build());

        /*int notificationId=new Random().nextInt(100);
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
        //builder.setStyle(new NotificationCompat.BigTextStyle().bigText(note).setBigContentTitle("Today is "+ name+"'s Birthday"));
        builder.setLargeIcon(bitmap2);
        builder.setStyle(new NotificationCompat.BigPictureStyle().bigPicture(bitmap2).bigLargeIcon(null));
        builder.setContentIntent(pendingIntent);
        builder.setAutoCancel(true);
        builder.setLargeIcon(bitmap2);
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
        };*/
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    public  void startalarm()
    {
        AlarmManager alarmManager= (AlarmManager)getSystemService(Context.ALARM_SERVICE);
       // am.AlermReciverdata(name.getText().toString(),
        //        nickname.getText().toString(),
          //      note.getText().toString(),bitmap1);
        Intent i =new Intent(this,AlermReciver.class);
        i.putExtra("Name", name.getText().toString());
        i.putExtra("NickName",nickname.getText().toString());
        i.putExtra("Note",note.getText().toString());
        i.putExtra("Bitmap",bitmap1);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(this,1,i,0);

        alarmManager.setExact(AlarmManager.RTC_WAKEUP,c.getTimeInMillis(),pendingIntent);
    }


}