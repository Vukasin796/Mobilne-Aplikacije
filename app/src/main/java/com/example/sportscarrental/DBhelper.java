package com.example.sportscarrental;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class DBhelper extends SQLiteOpenHelper {

    public DBhelper(@Nullable Context context) {
        super(context, "MyUserDatabase.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase DB) {
        DB.execSQL("CREATE TABLE UserRecord (SN INTEGER PRIMARY KEY, NAME TEXT, MAIl TEXT, DATE TEXT, CAR TEXT)");

    }

    @Override
    public void onUpgrade(SQLiteDatabase DB, int i, int i1) {
        DB.execSQL("drop table if exists UserRecord");

    }

    public List<String> getCarsList(){
        List<String> carsList = new ArrayList<>();
        carsList.add("Ferrari");
        carsList.add("Lamborghini");
        carsList.add("Mercedes");
        carsList.add("Bmw");

        return carsList;

    }



    public Boolean insertUserData(String name, String mail, String selectedDate, String selectedCar){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentvalue = new ContentValues();
        contentvalue.put("NAME", name);
        contentvalue.put("MAIL", mail);
        contentvalue.put("DATE", selectedDate);
        contentvalue.put("CAR", selectedCar);

        Long result = DB.insert("UserRecord", null, contentvalue);

        return result != -1;



    }
    public Boolean updateUserData(String name, String mail, String selectedDate, String selectedCar){
        SQLiteDatabase DB = this.getWritableDatabase();
        ContentValues contentvalue = new ContentValues();
        contentvalue.put("MAIL", mail);
        contentvalue.put("DATE", selectedDate);
        contentvalue.put("CAR", selectedCar);


        Cursor currentRecord = DB.rawQuery("select * from UserRecord where NAME =?", new String[]{name});

        if(currentRecord.getCount()>0){
            int result = DB.update("UserRecord", contentvalue, "NAME=?", new String[]{name});
            return result != -1;
        } else {
            return false;
        }
    }

    public Boolean deleteUserData(String name){
        SQLiteDatabase DB = this.getWritableDatabase();
        Cursor findRecord = DB.rawQuery("select * from UserRecord where NAME=?", new String[]{name});

        if (findRecord.getCount() > 0){
            int result = DB.delete("UserRecord", "NAME=?", new String[]{name});
            return result != -1;
        } else {
            return false;
        }
    }
    public Cursor viewUserData(String name){
        SQLiteDatabase DB = this.getWritableDatabase();
        String[] columns = { "NAME", "MAIL", "DATE", "CAR" };
        String selection = "NAME=?";
        String[] selectionArgs = { name };
        return DB.query("UserRecord", columns, selection, selectionArgs, null, null, null);




    }
}
