package com.example.volodymyrdudas.dijkstraalg;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.volodymyrdudas.dijkstraalg.config.ConfigParams;
import com.example.volodymyrdudas.dijkstraalg.db.DatabaseHelper;
import com.example.volodymyrdudas.dijkstraalg.generator.Generator;
import com.example.volodymyrdudas.dijkstraalg.javaimpl.impl.DijkstraAlgorithm;
import com.example.volodymyrdudas.dijkstraalg.javaimpl.model.Graph;
import com.example.volodymyrdudas.dijkstraalg.model.City;
import com.example.volodymyrdudas.dijkstraalg.model.Road;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private int currentDBContentCountCities = 0;
    private int currentDBContentCountRoads = 0;
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mDatabaseHelper = new DatabaseHelper(this);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        updateCount();
    }

    private long dijkstraAlgSQL() {
        int startSity = 1;
        long startTime = System.currentTimeMillis();
        mSqLiteDatabase.beginTransaction();
        mSqLiteDatabase.execSQL("CREATE TABLE CityList" +
                "(" +
                "CityId INTEGER NOT NULL," +
                "Estimate INTEGER NOT NULL," +
                "Predecessor INTEGER NULL," +
                "Done BIT NOT NULL" +
                ")");
        mSqLiteDatabase.execSQL("INSERT INTO CityList (CityId, Estimate, Predecessor, Done)" +
                "SELECT CityId, 2147483647, NULL, 0 FROM City");
        Cursor cursor;
        mSqLiteDatabase.execSQL("UPDATE CityList SET Estimate = 0 WHERE CityID = " + startSity);

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
            mSqLiteDatabase.execSQL("UPDATE CityList SET Done = 1 WHERE CityId = " + fromCity);
            mSqLiteDatabase.execSQL("UPDATE CityList SET Estimate = " + currentEstimate + " + (SELECT Distance FROM Road WHERE ToCity = CityId)," +
                    " Predecessor = " + fromCity +
                    " WHERE CityId IN (SELECT ToCity FROM Road " +
                    " WHERE FromCity = " + fromCity + " AND (" + currentEstimate + " + Distance) < Estimate)");
            cursor.close();
//            cursor = mSqLiteDatabase.rawQuery("SELECT * FROM CityList", null);
//            if (cursor != null && cursor.moveToFirst()) {
//                try {
//                    while (!cursor.isAfterLast()) {
//                        System.out.println(cursor.getString(cursor.getColumnIndex("CityId")) + " " +
//                                cursor.getString(cursor.getColumnIndex("Estimate")) + " " +
//                                cursor.getString(cursor.getColumnIndex("Predecessor")) + " " +
//                                cursor.getString(cursor.getColumnIndex("Done")));
//                        cursor.moveToNext();
//                    }
//                } catch (Exception e) {
//                    System.out.println("ERROR");
//                }
//            }
        }
        cursor = mSqLiteDatabase.rawQuery("SELECT * FROM CityList", null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            System.out.println(cursor.getString(cursor.getColumnIndex("CityId")) + " " +
                    cursor.getString(cursor.getColumnIndex("Estimate")) + " " +
                    cursor.getString(cursor.getColumnIndex("Predecessor")));
            cursor.moveToNext();
        }
        cursor.close();
        mSqLiteDatabase.execSQL("DROP TABLE CityList");
        mSqLiteDatabase.endTransaction();
        return System.currentTimeMillis() - startTime;
    }

    public void onClickGenerate(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantityTextView);
        Integer generateCount = Integer.parseInt(quantityTextView.getText().toString());
        if (generateCount != null) {
            if (currentDBContentCountCities > generateCount) {
                mSqLiteDatabase.execSQL("DELETE FROM City;");
                mSqLiteDatabase.execSQL("DELETE FROM Road;");
                Generator.generate(mSqLiteDatabase, generateCount);
            } else if (currentDBContentCountCities < generateCount) {
                Generator.generate(mSqLiteDatabase, generateCount - currentDBContentCountCities);
            }
        }
        showToast("Generated");
        updateCount();
    }

    public void onClickJava(View view) {
        List<City> cities = new ArrayList<>();
        List<Road> roads = new ArrayList<>();
        try {
            QueryBuilder<City, Integer> queryBuilder = mDatabaseHelper.cityDAO.queryBuilder();
            queryBuilder.orderBy(ConfigParams.CITY_TABLE_ID, true);
            PreparedQuery<City> preparedQuery = queryBuilder.prepare();
            cities = mDatabaseHelper.cityDAO.query(preparedQuery);
            QueryBuilder<Road, Integer> queryBuilderRoad = mDatabaseHelper.roadDAO.queryBuilder();
            PreparedQuery<Road> preparedQueryRoad = queryBuilderRoad.prepare();
            roads = mDatabaseHelper.roadDAO.query(preparedQueryRoad);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        TextView infoTextView = (TextView) findViewById(R.id.javaResultTextView);
        Graph graph = new Graph(cities, roads);
        DijkstraAlgorithm dijkstraAlgorithm = new DijkstraAlgorithm(graph);
        if (cities.size() > 0) {
            long time = dijkstraAlgorithm.execute(cities.get(0));
            infoTextView.setText("Elapsed time is : " + String.valueOf(time / 1000.0) + " sec");
        } else {
            showToast("There is no city in the db");
        }
    }

    public void onClickSQL(View view) {
        if (currentDBContentCountCities > 0) {
            TextView infoTextView = (TextView) findViewById(R.id.SQLResultTextView);
            long time = dijkstraAlgSQL();
            infoTextView.setText("Elapsed time is : " + String.valueOf(time / 1000.0) + " sec");
        } else {
            showToast("There is no city in the db");
        }
    }

    private void updateCount() {
        Cursor cursor = mSqLiteDatabase.rawQuery("SELECT * FROM " + ConfigParams.CITY_TABLE, null);
        currentDBContentCountCities = cursor.getCount();
        cursor = mSqLiteDatabase.rawQuery("SELECT * FROM " + ConfigParams.ROAD_TABLE, null);
        currentDBContentCountRoads = cursor.getCount();
        TextView infoTextView = (TextView) findViewById(R.id.infoTextView);
        infoTextView.setText("Current db amount : Cities - " + String.valueOf(currentDBContentCountCities) + " Roads - " + String.valueOf(currentDBContentCountRoads));
        cursor.close();
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}