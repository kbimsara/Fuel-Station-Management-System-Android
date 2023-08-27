package com.example.fuelstationmanagementsystem;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class DBhelper extends SQLiteOpenHelper {

    private static final String DB_NAME = "fms_db";
    private static final String DB_TABLE = "vehicle_tb";
    private static final String DB_TABLE2 = "tank_tb";
    private static final int DB_VER = 1;


    //Connect Database
    public DBhelper(Context context) {
        super(context, DB_NAME, null, DB_VER);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE vehicle_tb (number_plate Text PRIMARY KEY,type Text NOT NULL,capacity Text NOT NULL,capacity2 Text);");
        db.execSQL("CREATE TABLE tank_tb (id INTEGER PRIMARY KEY AUTOINCREMENT,ftype Text NOT NULL,fcapacity Text NOT NULL,fcapacity2 Text);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS vehicle_tb");
        db.execSQL("DROP TABLE IF EXISTS tank_tb");
        onCreate(db);
    }

    //---------------------------This is Vehicle DB Handler Part/Start---------------------------

    //Insert Database-vehicle
    public boolean InsertData(String number_plate,  String type,  String capacity ) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("number_plate", number_plate);
        contentValues.put("type", type);
        contentValues.put("capacity", capacity);
        contentValues.put("capacity2", "0");

        long result = db.insert("vehicle_tb", null, contentValues);
        return result != -1;
    }

    //Search Data-vehicle
    public Cursor ViewDate(String numPlate) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM vehicle_tb WHERE number_plate=? ", new String[]{numPlate});
    }

    //Update Database-vehicle
    public boolean updateData(String numPlate, String type, String capacity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (type != null) {
            contentValues.put("type", type);
        }
        if (capacity != null) {
            contentValues.put("capacity", capacity);
        }

        @SuppressLint("") Cursor cursor = db.rawQuery("SELECT * FROM vehicle_tb WHERE number_plate=?", new String[]{numPlate});
        if (cursor.getCount() > 0) {
            long result = db.update("vehicle_tb", contentValues, "number_plate=?", new String[]{numPlate});
            return result != -1;
        }
        return false;
    }

    //Update Remaining Capacity-vehicle
    public boolean updateRemainCapacity(String numPlate, String capacity2) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("capacity2", capacity2);

        @SuppressLint("") Cursor cursor = db.rawQuery("SELECT * FROM vehicle_tb WHERE number_plate=?", new String[]{numPlate});
        if (cursor.getCount() > 0) {
            long result = db.update("vehicle_tb", contentValues, "number_plate=?", new String[]{numPlate});
            return result != -1;
        }
        return false;
    }

    //Delete Database Data-vehicle
    public boolean Delete(String numPlate) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM vehicle_tb WHERE number_plate=?", new String[]{numPlate});
        if (cursor.getCount() > 0) {
            cursor.close();
            long result = db.delete("vehicle_tb", "number_plate=?", new String[]{numPlate});
            return result != -1;
        }
        cursor.close();
        return false;
    }

    //View Data-vehicle
    public Cursor View() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM vehicle_tb ORDER BY number_plate",null);
        return cursor;
    }
    public Cursor View2(String np) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM vehicle_tb WHERE number_plate=?", new String[]{np});
        return cursor;
    }


    //---------------------------This is Vehicle DB Handler Part/End---------------------------

    //---------------------------This is Fuel DB Handler Part/Start---------------------------

    //Insert Database-Tank
    public boolean InsertDataf(String ftype,  String fcapacity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        contentValues.put("ftype", ftype);
        contentValues.put("fcapacity", fcapacity);

        long result = db.insert("tank_tb", null, contentValues);
        return result != -1;
    }

    //Search Data-Tank
    public Cursor ViewDatef(String type) {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM tank_tb WHERE ftype=? ", new String[]{type});
    }

    //Update Database-vehicle
    public boolean updateDataf(String ftype,String fcapacity,String frmcapacity) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        if (fcapacity != null) {
            contentValues.put("fcapacity", fcapacity);
        }
        if (frmcapacity != null) {
            contentValues.put("fcapacity2", frmcapacity);
        }

        @SuppressLint("") Cursor cursor = db.rawQuery("SELECT * FROM tank_tb WHERE ftype=?", new String[]{ftype});
        if (cursor.getCount() > 0) {
            long result = db.update("tank_tb", contentValues, "ftype=?", new String[]{ftype});
            return result != -1;
        }
        return false;
    }

    //Delete Database Data-vehicle
    public boolean Deletef(String ftype) {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM tank_tb WHERE ftype=?", new String[]{ftype});
        if (cursor.getCount() > 0) {
            cursor.close();
            long result = db.delete("tank_tb", "ftype=?", new String[]{ftype});
            return result != -1;
        }
        cursor.close();
        return false;
    }

    //View Data-Tank
    public Cursor Viewf() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor= db.rawQuery("SELECT * FROM tank_tb",null);
        return cursor;
    }

    //---------------------------This is Fuel DB Handler Part/End---------------------------

}
