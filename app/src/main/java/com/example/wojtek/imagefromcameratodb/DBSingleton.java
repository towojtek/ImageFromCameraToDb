package com.example.wojtek.imagefromcameratodb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Wojtek on 2016-01-19.
 */
public class DBSingleton extends SQLiteOpenHelper {
    private static DBSingleton instance;

    private DBSingleton(Context context,String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);
    }
    public static synchronized DBSingleton getInstance (Context context){
        if (instance == null){
            instance = new DBSingleton(context.getApplicationContext(),"test",null,1);
        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.openOrCreateDatabase("test.db", null);
        db.execSQL("create table if not exists tb (a blob)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
