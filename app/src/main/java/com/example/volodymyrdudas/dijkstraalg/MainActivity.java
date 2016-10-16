package com.example.volodymyrdudas.dijkstraalg;

import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

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

//        ContentValues values = new ContentValues();
        // Задайте значения для каждого столбца
//        values.put(DatabaseHelper.CAT_NAME_COLUMN, "Рыжик");
//        values.put(DatabaseHelper.PHONE_COLUMN, "4954553443");
//        values.put(DatabaseHelper.AGE_COLUMN, "5");
        // Вставляем данные в таблицу
//        mSqLiteDatabase.insert("cats", null, values);
    }

    public void onClick(View view) {
//        Cursor cursor = mSqLiteDatabase.query("cats", new String[] {DatabaseHelper.CAT_NAME_COLUMN,
//                        DatabaseHelper.PHONE_COLUMN, DatabaseHelper.AGE_COLUMN},
//                null, null,
//                null, null, null) ;
//
//        cursor.moveToFirst();
//
//        String catName = cursor.getString(cursor.getColumnIndex(DatabaseHelper.CAT_NAME_COLUMN));
//        int phoneNumber = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.PHONE_COLUMN));
//        int age = cursor.getInt(cursor.getColumnIndex(DatabaseHelper.AGE_COLUMN));
//
//        TextView infoTextView = (TextView)findViewById(R.id.textView);
//        infoTextView.setText("Кот " + catName + " имеет телефон " + phoneNumber + " и ему " +
//                age + " лет");
//
//        // не забываем закрывать курсор
//        cursor.close();
    }
}
