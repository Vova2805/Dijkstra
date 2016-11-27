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
import com.example.volodymyrdudas.dijkstraalg.model.City;
import com.example.volodymyrdudas.dijkstraalg.implAlgorithm.DijkstraAlgorithmSQL;
import com.example.volodymyrdudas.dijkstraalg.implAlgorithm.DijkstraAlgorithmJava;

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

    public void onClickGenerate(View view) {
        TextView quantityTextView = (TextView) findViewById(R.id.quantityTextView);
        if (quantityTextView != null) {
            Integer generateCount = Integer.parseInt(quantityTextView.getText().toString());
            if (currentDBContentCountCities > generateCount) {
                mSqLiteDatabase.execSQL("DELETE FROM City;");
                mSqLiteDatabase.execSQL("DELETE FROM Road;");
                Generator.generate(mSqLiteDatabase, generateCount);
            } else if (currentDBContentCountCities < generateCount) {
                Generator.generate(mSqLiteDatabase, generateCount - currentDBContentCountCities);
            }
            showToast("Generated");
            updateCount();
        }
    }

    public void onClickJava(View view) {
        TextView infoTextView = (TextView) findViewById(R.id.javaResultTextView);
        if (infoTextView != null) {
            infoTextView.setText("");
            infoTextView.setText("Elapsed time is : ");
        }
        long startTime = System.currentTimeMillis();
        DijkstraAlgorithmJava dijkstraAlgorithmJava = new DijkstraAlgorithmJava();
        List<City> cities = dijkstraAlgorithmJava.init(mDatabaseHelper);
        if (cities.size() > 0) {
            dijkstraAlgorithmJava.execute(cities.get(0));
            long time = System.currentTimeMillis() - startTime;
            if (infoTextView != null)
                infoTextView.setText("Elapsed time is : " + String.valueOf(time / 1000.0) + " sec");
        } else {
            showToast("There is no city in the db");
        }
        showToast("Calculated");
    }

    public void onClickSQL(View view) {
        TextView infoTextView = (TextView) findViewById(R.id.SQLResultTextView);
        if (infoTextView != null) {
            infoTextView.setText("");
        }
        if (currentDBContentCountCities > 0) {
            long startTime = System.currentTimeMillis();
            DijkstraAlgorithmSQL dijkstraAlgorithmSQL = new DijkstraAlgorithmSQL(mSqLiteDatabase);
            dijkstraAlgorithmSQL.execute(1);
            long time = System.currentTimeMillis() - startTime;
            if (infoTextView != null) {
                infoTextView.setText("Elapsed time is : " + String.valueOf(time / 1000.0) + " sec");
            }
        } else {
            showToast("There is no city in the db");
        }
        showToast("Calculated");
    }

    private void updateCount() {
        Cursor cursor = mSqLiteDatabase.rawQuery("SELECT * FROM " + ConfigParams.CITY_TABLE, null);
        currentDBContentCountCities = cursor.getCount();
        cursor = mSqLiteDatabase.rawQuery("SELECT * FROM " + ConfigParams.ROAD_TABLE, null);
        currentDBContentCountRoads = cursor.getCount();
        TextView infoTextView = (TextView) findViewById(R.id.infoTextView);
        if (infoTextView != null)
            infoTextView.setText("Current db amount : Cities - " + String.valueOf(currentDBContentCountCities) + " Roads - " + String.valueOf(currentDBContentCountRoads));
        cursor.close();
    }

    private void showToast(String text) {
        Toast toast = Toast.makeText(getApplicationContext(), text, Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 0);
        toast.show();
    }
}