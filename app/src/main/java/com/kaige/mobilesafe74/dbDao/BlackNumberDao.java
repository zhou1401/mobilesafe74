package com.kaige.mobilesafe74.dbDao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kaige.mobilesafe74.db.BlackNumberOpenHelper;
import com.kaige.mobilesafe74.db_domin.BlackNumberInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dell on 2017/3/29.
 */

public class BlackNumberDao {
    private BlackNumberOpenHelper openHelper;

    private BlackNumberDao(Context context){
        openHelper = new BlackNumberOpenHelper(context);
    }
    private static BlackNumberDao blackNumberDao;
    private ArrayList<BlackNumberInfo> blackNumberInfoList;
    public  static BlackNumberDao getInstance(Context context){
        if(blackNumberDao == null){
            blackNumberDao  = new BlackNumberDao(context);
        }
        return blackNumberDao;
    }

    public void insert(String mode,String phone){
        SQLiteDatabase db = openHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("mode", mode);
        contentValues.put("phone", phone);

        db.insert("blacknumber", null, contentValues);
        db.close();
    }

    public void delete(String phone){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        db.delete("blacknumber", "phone = ?", new String[]{phone});
        db.close();
    }

    public void update(String phone,String mode){
        SQLiteDatabase db = openHelper.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put("mode", mode);

        db.update("blacknumber", contentValues, "phone =?", new String[]{phone});
        db.close();
    }

    public List<BlackNumberInfo> findAll(){
        SQLiteDatabase db = openHelper.getWritableDatabase();

        Cursor cursor = db.query("blacknumber", new String[]{"phone","mode"}, null, null, null, null, "_id desc");

        blackNumberInfoList = new ArrayList<BlackNumberInfo>();
        while(cursor.moveToNext()){
            BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
            blackNumberInfo.phone = cursor.getString(0);
            blackNumberInfo.mode = cursor.getString(1);
            blackNumberInfoList.add(blackNumberInfo);
        }
        db.close();
        return blackNumberInfoList;
    }

    public List<BlackNumberInfo> find(int index){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select phone,mode from blacknumber order by _id desc limit ?,20;", new String[]{index+""});
        blackNumberInfoList = new ArrayList<BlackNumberInfo>();

        while(cursor.moveToNext()){
            BlackNumberInfo blackNumberInfo = new BlackNumberInfo();
            blackNumberInfo.phone = cursor.getString(0);
            blackNumberInfo.mode = cursor.getString(1);

            blackNumberInfoList.add(blackNumberInfo);
        }
        cursor.close();
        db.close();
        return blackNumberInfoList;
    }

    public int getCount(){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery("select count(*) from blacknumber;",null);
        int count = -1;
        if(cursor.moveToNext()){
            count = cursor.getInt(0);
        }
        cursor.close();
        db.close();
        return count;
    }
    public String getMode(String phone){
        SQLiteDatabase db = openHelper.getWritableDatabase();
        Cursor cursor = db.query("blacknumber", new String[]{"mode"},"phone = ?", new String[]{phone}, null, null, null);
        String mode = "";
        if(cursor.moveToNext()){
            mode = cursor.getString(0);
        }
        cursor.close();
        db.close();
        return mode;
    }
}
