package com.example.volodymyrdudas.dijkstraalg.generator;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Random;

import static com.example.volodymyrdudas.dijkstraalg.config.ConfigParams.CITY_TABLE;
import static com.example.volodymyrdudas.dijkstraalg.config.ConfigParams.CITY_TABLE_ID;
import static com.example.volodymyrdudas.dijkstraalg.config.ConfigParams.CITY_TABLE_NAME;
import static com.example.volodymyrdudas.dijkstraalg.config.ConfigParams.ROAD_DISTANCE;
import static com.example.volodymyrdudas.dijkstraalg.config.ConfigParams.ROAD_FROM_CITY;
import static com.example.volodymyrdudas.dijkstraalg.config.ConfigParams.ROAD_TABLE;
import static com.example.volodymyrdudas.dijkstraalg.config.ConfigParams.ROAD_TABLE_ID;
import static com.example.volodymyrdudas.dijkstraalg.config.ConfigParams.ROAD_TO_CITY;

public final class Generator {
    private static final int MAX_ROAD_FROM_CITY = 6;

    public static void generate(SQLiteDatabase mSqLiteDatabase, int amount) {
        boolean createSuccessful = false;
        int maxId = 0;
        //generate cities
        Cursor cursor = mSqLiteDatabase.rawQuery("SELECT * FROM " + CITY_TABLE, null);
        if (cursor.getCount() > 0) {
            Cursor cursor1 = mSqLiteDatabase.rawQuery("SELECT MAX(" + CITY_TABLE_ID + ") AS MAX_ID FROM " + CITY_TABLE, null);
            cursor1.moveToFirst();
            maxId = cursor1.getInt(0);
            cursor1.close();
        }
        cursor.close();
        ContentValues values = new ContentValues();
        for (int i = 1; i <= amount; i++) {
            values.put(CITY_TABLE_ID, i + maxId);
            values.put(CITY_TABLE_NAME, RandomStringUtils.random(5));
            mSqLiteDatabase.insert(CITY_TABLE, null, values);
        }

        //generate roads between them
        cursor = mSqLiteDatabase.rawQuery("SELECT * FROM " + ROAD_TABLE, null);
        if (cursor.getCount() > 0) {
            Cursor cursor1 = mSqLiteDatabase.rawQuery("SELECT MAX(" + ROAD_TABLE_ID + ") AS MAX_ID FROM " + ROAD_TABLE, null);
            cursor1.moveToFirst();
            maxId = cursor1.getInt(0);
            cursor1.close();
        }
        Random random = new Random(System.currentTimeMillis());
        values = new ContentValues();
        int id = 1;
        for (int i = 1; i <= amount; i++) {
            int quantityOfRoad = random.nextInt(MAX_ROAD_FROM_CITY);
            int fromCity = maxId + getCorrectValue(random.nextInt(amount), 1, maxId + amount);
            for (int j = 1; j <= quantityOfRoad; j++) {
                values.put(ROAD_TABLE_ID, (id++) + maxId);
                int toCity = getCorrectValue(random.nextInt(maxId + amount), 1, maxId + amount);
                toCity = getCorrectValue(toCity == fromCity ? (toCity > 1 ? toCity - 1 : amount - 1) : toCity, 1, maxId + amount);
                values.put(ROAD_FROM_CITY, fromCity);
                values.put(ROAD_TO_CITY, toCity);
                values.put(ROAD_DISTANCE, String.valueOf(random.nextInt(1000) / 10.0));
                mSqLiteDatabase.insert(ROAD_TABLE, null, values);
                values.put(ROAD_TABLE_ID, (id++) + maxId);
                values.put(ROAD_FROM_CITY, toCity);
                values.put(ROAD_TO_CITY, fromCity);
                mSqLiteDatabase.insert(ROAD_TABLE, null, values);
            }
        }
        cursor.close();
    }

    private static int getCorrectValue(int value, int from, int to) {
        return value > to ? to : value < from ? from : value;
    }
}

