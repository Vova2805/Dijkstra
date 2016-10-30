package com.example.volodymyrdudas.dijkstraalg.sqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.BaseColumns;
import android.util.Log;

import com.example.volodymyrdudas.dijkstraalg.config.ConfigParams;

public class DatabaseHelper extends SQLiteOpenHelper implements BaseColumns {

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version, DatabaseErrorHandler errorHandler) {
        super(context, name, factory, version, errorHandler);
    }

    public DatabaseHelper(Context context) {
        super(context, ConfigParams.DATABASE_NAME, null, ConfigParams.DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(ConfigParams.CITY_CREATE_TABLE_SCRIPT);
        db.execSQL(ConfigParams.ROAD_CREATE_TABLE_SCRIPT);
        db.execSQL(ConfigParams.INSERT_DATA_SCRIPT);
        db.execSQL(ConfigParams.INSERT_DATA_SCRIPT1);
        ContentValues values = new ContentValues();
        Cursor cursor = db.rawQuery("SELECT * FROM City", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            System.out.println(cursor.getString(cursor.getColumnIndex("CityId")) + " " +
                    cursor.getString(cursor.getColumnIndex("Name")));
            cursor.moveToNext();
        }
        cursor = db.rawQuery("SELECT * FROM Road", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            System.out.println(cursor.getString(cursor.getColumnIndex("RoadId")) + " " +
                    cursor.getString(cursor.getColumnIndex("FromCity")) + " " +
                    cursor.getString(cursor.getColumnIndex("ToCity")) + " " +
                    cursor.getString(cursor.getColumnIndex("Distance")));
            cursor.moveToNext();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Update version " + oldVersion + " to version " + newVersion);
        db.execSQL("DROP TABLE IF IT EXISTS " + ConfigParams.DATABASE_NAME);
        onCreate(db);
    }
}
/*
Script for generating the test data inserts.

SELECT 'INSERT INTO dbo.[City] VALUES (' + CAST(CityID AS Varchar(MAX)) + ', ''' + Name + ''')' FROM City
SELECT 'INSERT INTO dbo.[Road] VALUES (' + CAST(FromCity AS Varchar(MAX)) + ', ' + CAST(ToCity AS Varchar(MAX)) + ', ' + CAST(Distance AS Varchar(MAX)) + ')' FROM Road
*/
