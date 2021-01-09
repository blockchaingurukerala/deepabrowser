package com.example.assessmentwebbrowser;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBBookmarkshandler extends SQLiteOpenHelper {

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "bookmarks.db";
    private static final String TABLE_BOOKMARK = "bookmarks";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_NAME = "url";

    public DBBookmarkshandler(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = "CREATE TABLE " +TABLE_BOOKMARK+ "(" +
                COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT ," +
                COLUMN_NAME + " TEXT" +
                ")";
        db.execSQL(query);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL(" DROP TABLE IF EXISTS " + TABLE_BOOKMARK);
        onCreate(db);
    }

    //Add new row to database

    public void addUrl(Websites website) {
        ContentValues values = new ContentValues();
        values.put(COLUMN_NAME, website.getUrl());
        SQLiteDatabase db = getWritableDatabase();
        db.insert(TABLE_BOOKMARK, null, values);
        db.close();
    }

    //delete from database
    public void deleteUrl(String urlName) {
        SQLiteDatabase db= getWritableDatabase();
        db.execSQL("DELETE FROM " + TABLE_BOOKMARK+" WHERE"+COLUMN_NAME+"-\""+urlName +"\";");
    }


    //Print history as String
    public List<String> databaseToString(){

        SQLiteDatabase db= getWritableDatabase();
        String query ="SELECT * FROM " + TABLE_BOOKMARK;
        List<String> dbString= new ArrayList<>();
        Cursor c= db.rawQuery(query, null);
        c.moveToFirst();
        int i=0;
        if (c.moveToFirst()){
            do{
                if (c.getString(c.getColumnIndex(COLUMN_NAME))!= null){
                    String bstring="";
                    bstring += c.getString(c.getColumnIndex("url"));
                    dbString.add(bstring);
                }
            } while (c.moveToNext());
        }
        return dbString;
    }
}
