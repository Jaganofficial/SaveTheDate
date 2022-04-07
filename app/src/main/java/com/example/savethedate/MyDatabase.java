

package com.example.savethedate;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.database.CharArrayBuffer;
import android.database.ContentObserver;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.annotation.Nullable;

import java.sql.Blob;

public class MyDatabase extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "birthdayremainder1.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME = "birthdays";
    private static final String COLUMN_ID = "_id";
    private static final String COLUMN_NAME = "Name";
    private static final String COLUMN_DATE= "DateofBirth";
    private static final String COLUMN_NOTE = "Notes";
    private static final String COLUMN_NICKNAME = "NickName";
    private static final String COLUMN_NUMBER="Number";
    private static final String COLUMN_IMAGES="Images";
    private static final String COLUMN_DATEINMILLIES= "Dateinmillies";

    public MyDatabase(@Nullable Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE "+ TABLE_NAME +" ( " +
                COLUMN_ID +" INTEGER PRIMARY KEY AUTOINCREMENT, "
                + COLUMN_NAME + " TEXT, "
                +COLUMN_NICKNAME + " TEXT, " +COLUMN_NOTE+" TEXT, "+COLUMN_NUMBER+" TEXT, "+COLUMN_DATE+" TEXT, "+COLUMN_IMAGES+" TEXT, "+COLUMN_DATEINMILLIES +" INTEGER );";
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
    void adddata(String name, String nickname, String date, String note, String number, String image1 , Long millies)//add photo
    {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv= new ContentValues();

        cv.put(COLUMN_NAME, name);
        cv.put(COLUMN_NICKNAME, nickname);
        cv.put(COLUMN_NOTE, note);
        cv.put(COLUMN_NUMBER, number);
        cv.put(COLUMN_DATE, date);
        cv.put(COLUMN_IMAGES,image1);
        cv.put(COLUMN_DATEINMILLIES,millies);

        long result = db.insert(TABLE_NAME,null,cv);
        if(result == -1)
        {
            Toast.makeText(context, image1.toString(),Toast.LENGTH_SHORT).show();
        }
        else

        {
            Toast.makeText(context, "Date Saved :)", Toast.LENGTH_SHORT).show();
        }
    }
    Cursor readalldata()
    {
       // String Query = "SELECT * FROM "+TABLE_NAME;
        String Query = "SELECT * FROM "+TABLE_NAME+" ORDER BY "+COLUMN_DATEINMILLIES+" ASC ";

        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = null;
        if (db != null)
        {
            cursor=db.rawQuery(Query,null);
        }
    return cursor;
    }
    void updatedata(String uid,String uname,String unickname,String udate,String unote,String uphonenumber) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues c = new ContentValues();

        c.put(COLUMN_NAME,uname);
        c.put(COLUMN_NICKNAME,unickname);
        c.put(COLUMN_NOTE,unote);
        c.put(COLUMN_NUMBER,uphonenumber);
        c.put(COLUMN_DATE,udate);

        long Result = db.update(TABLE_NAME,c,"_id=?",new String[]{uid});
        if(Result ==-1)
        {
            Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
        }
        else
        {
            Toast.makeText(context, "Updation Completed", Toast.LENGTH_SHORT).show();
        }

    }
    void DeleteRow(String id)
    {
        SQLiteDatabase db = getWritableDatabase();
        long result = db.delete(TABLE_NAME,"_id=?", new String[]{id});
        if(result==-1)
            Toast.makeText(context, "Try Again", Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(context, "Removed From The List", Toast.LENGTH_SHORT).show();
    }
    void DeleteAll()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_NAME);
    }
    void sort()
    {
        SQLiteDatabase db = getWritableDatabase();
       // db.execSQL("UPDATE "+TABLE_NAME+" ORDER BY "+ COLUMN_DATE +" desc");
    }
}

