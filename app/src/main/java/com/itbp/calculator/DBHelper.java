package com.itbp.calculator;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.ArrayList;

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "calculator";
    public static Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        this.context = context;
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();
            db.execSQL("create table if not exists results (id INTEGER PRIMARY KEY AUTOINCREMENT, a text, b text, c text, result text)");
            db.setTransactionSuccessful();
        } catch (Exception e) {
            Log.d("sql create", e.toString());
        } finally {
            db.endTransaction();
        }

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        /*db.execSQL("DROP TABLE IF EXISTS checks");
        onCreate(db);*/
    }



    public boolean executeSQL(String SQL) {
        try {
            SQLiteDatabase db = this.getWritableDatabase();
            db.execSQL(SQL);
        }catch (Exception e) {
            Log.d("sql error", e.toString());
            return false;
        }
        return true;
    }


    public ArrayList<String[]> rawQuery(String SQL) {
        try {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor c = db.rawQuery(SQL,null);
            ArrayList<String[]> res = new ArrayList<String[]>();
            Log.d("raw_query",String.valueOf(SQL) );

            c.moveToFirst();

            while(c.isAfterLast() == false) {
                String column1 = c.getString(c.getColumnIndex("id"));
                String column2 = c.getString(c.getColumnIndex("a"));
                String column3 = c.getString(c.getColumnIndex("b"));
                String column4 = c.getString(c.getColumnIndex("c"));
                String column5 = c.getString(c.getColumnIndex("result"));
                String[] data = {column1,column2,column3,column4,column5};
                res.add(data);
                c.moveToNext();
            }


            return res;
        }catch (Exception e) {
            Log.d("sql error",e.toString());
        }
        return new ArrayList<String[]>();
    }


}
