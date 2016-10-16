package com.example.volodymyrdudas.dijkstraalg;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.volodymyrdudas.dijkstraalg.config.ConfigParams;
import com.example.volodymyrdudas.dijkstraalg.sqlite.DatabaseHelper;

public class MainActivity extends AppCompatActivity {
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mDatabaseHelper = new DatabaseHelper(this);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
    }

    public void onClick(View view) {
        int quantity = -1;
        mSqLiteDatabase.beginTransaction();
        String[] whereArgs = new String[] {};
        Cursor cursor = mSqLiteDatabase.rawQuery("SELECT * FROM "+ConfigParams.TRIGGER_TABLE,whereArgs);
        mSqLiteDatabase.endTransaction();
        cursor.moveToFirst();
        quantity = cursor.getCount();
        TextView infoTextView = (TextView)findViewById(R.id.textView);
        infoTextView.setText(quantity+"");
        cursor.close();
    }
}
