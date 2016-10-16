package com.example.volodymyrdudas.dijkstraalg.sqlite;

import android.content.Context;
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
        db.execSQL(ConfigParams.NODE_TABLE_CREATE_SCRIPT);
        db.execSQL(ConfigParams.EDGE_TABLE_CREATE_SCRIPT);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w("SQLite", "Update version " + oldVersion + " to version " + newVersion);
        db.execSQL("DROP TABLE IF IT EXISTS " + ConfigParams.DATABASE_NAME);
        onCreate(db);
    }
}
