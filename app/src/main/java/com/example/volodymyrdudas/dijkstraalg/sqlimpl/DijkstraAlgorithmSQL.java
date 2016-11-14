package com.example.volodymyrdudas.dijkstraalg.sqlimpl;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

public class DijkstraAlgorithmSQL {
    private SQLiteDatabase mSqLiteDatabase;

    public DijkstraAlgorithmSQL(SQLiteDatabase mSqLiteDatabase) {
        this.mSqLiteDatabase = mSqLiteDatabase;
    }

    public long execute(int startSity) {
        mSqLiteDatabase.execSQL("CREATE TABLE CityList" +
                "(" +
                "CityId INTEGER NOT NULL," +
                "Estimate INTEGER NOT NULL," +
                "Predecessor INTEGER NULL," +
                "Done BIT NOT NULL" +
                ")");
        mSqLiteDatabase.execSQL("INSERT INTO CityList (CityId, Estimate, Predecessor, Done)" +
                "SELECT CityId, 2147483647, NULL, 0 FROM City;");
        long startTime = System.currentTimeMillis();
        mSqLiteDatabase.execSQL("UPDATE CityList SET Estimate = 0 WHERE CityID = " + startSity);
        Cursor cursor;
        Integer fromCity;
        int currentEstimate = 0;
        while (true) {
            fromCity = null;
            cursor = mSqLiteDatabase.rawQuery("SELECT CityId, Estimate FROM CityList WHERE Done = 0 AND Estimate < 2147483647 ORDER BY Estimate LIMIT 1;", null);
            if (cursor.moveToFirst()) {
                fromCity = cursor.getInt(cursor.getColumnIndex("CityId"));
                currentEstimate = cursor.getInt(cursor.getColumnIndex("Estimate"));
            }
            if (fromCity == null) {
                break;
            }
            mSqLiteDatabase.execSQL(
                    " UPDATE CityList SET Done = 1 WHERE CityId = " + fromCity + ";");
            mSqLiteDatabase.execSQL(
                    " UPDATE CityList SET Estimate = " + currentEstimate + " + " +
                            "(SELECT Distance FROM Road WHERE ToCity = CityId and FromCity = " + fromCity + " LIMIT 1)," +
                            " Predecessor = " + fromCity +
                            " WHERE CityId IN " +
                            " (SELECT ToCity FROM Road WHERE FromCity = " + fromCity + " AND (" + currentEstimate + " + Distance) < Estimate);");
            cursor.close();
        }
        cursor.close();
        long time = System.currentTimeMillis() - startTime;
        mSqLiteDatabase.execSQL("DROP TABLE CityList;");
        return time;
    }

    //for debugging
    private void show() {
        System.out.println("\n\n");
        Cursor cursor = mSqLiteDatabase.rawQuery("SELECT * FROM CityList", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            System.out.println(cursor.getString(cursor.getColumnIndex("CityId")) + " " +
                    cursor.getString(cursor.getColumnIndex("Estimate")) + " " +
                    cursor.getString(cursor.getColumnIndex("Predecessor")) + " " +
                    cursor.getString(cursor.getColumnIndex("Done")));
            cursor.moveToNext();
        }
    }
}
