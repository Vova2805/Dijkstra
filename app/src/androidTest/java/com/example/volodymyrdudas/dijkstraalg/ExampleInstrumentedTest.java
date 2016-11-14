package com.example.volodymyrdudas.dijkstraalg;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;

import com.example.volodymyrdudas.dijkstraalg.db.DatabaseHelper;
import com.example.volodymyrdudas.dijkstraalg.model.City;
import com.example.volodymyrdudas.dijkstraalg.model.Graph;
import com.example.volodymyrdudas.dijkstraalg.model.Road;
import com.example.volodymyrdudas.dijkstraalg.sqlimpl.DijkstraAlgorithmSQL;
import com.example.volodymyrdudas.dijkstraalg.tests.DijkstraAlgorithmJava;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
    private DatabaseHelper mDatabaseHelper;
    private SQLiteDatabase mSqLiteDatabase;
    private List<City> cities;
    private List<Road> roads;
    private Context appContext;

    @Before
    public void init() {
        appContext = InstrumentationRegistry.getTargetContext();
        cities = new ArrayList<>();
        roads = new ArrayList<>();

        cities.add(new City(1, "1"));
        cities.add(new City(2, "2"));
        cities.add(new City(3, "3"));
        cities.add(new City(4, "4"));
        cities.add(new City(5, "5"));
        cities.add(new City(6, "6"));
        cities.add(new City(7, "7"));
        cities.add(new City(8, "8"));
        cities.add(new City(9, "9"));
        cities.add(new City(10, "10"));

        roads.add(new Road(1, cities.get(0), cities.get(5), 40));
        roads.add(new Road(2, cities.get(0), cities.get(1), 100));
        roads.add(new Road(3, cities.get(0), cities.get(7), 10));

        roads.add(new Road(4, cities.get(1), cities.get(6), 35));
        roads.add(new Road(5, cities.get(1), cities.get(2), 4));

        roads.add(new Road(6, cities.get(2), cities.get(4), 43));
        roads.add(new Road(7, cities.get(2), cities.get(3), 654));
        roads.add(new Road(8, cities.get(2), cities.get(5), 56));

        roads.add(new Road(9, cities.get(3), cities.get(9), 342));
        roads.add(new Road(10, cities.get(3), cities.get(5), 23));
        roads.add(new Road(11, cities.get(3), cities.get(8), 87));

        roads.add(new Road(12, cities.get(4), cities.get(6), 32));
        roads.add(new Road(13, cities.get(4), cities.get(7), 765));

        roads.add(new Road(14, cities.get(6), cities.get(9), 40));

        roads.add(new Road(15, cities.get(7), cities.get(8), 50));

        roads.add(new Road(16, cities.get(5), cities.get(0), 40));
        roads.add(new Road(17, cities.get(1), cities.get(0), 100));
        roads.add(new Road(18, cities.get(7), cities.get(0), 10));

        roads.add(new Road(19, cities.get(6), cities.get(1), 35));
        roads.add(new Road(20, cities.get(2), cities.get(1), 4));

        roads.add(new Road(21, cities.get(4), cities.get(2), 43));
        roads.add(new Road(22, cities.get(2), cities.get(3), 654));
        roads.add(new Road(23, cities.get(5), cities.get(2), 56));

        roads.add(new Road(24, cities.get(9), cities.get(3), 342));
        roads.add(new Road(25, cities.get(5), cities.get(3), 23));
        roads.add(new Road(26, cities.get(8), cities.get(3), 87));

        roads.add(new Road(27, cities.get(6), cities.get(4), 32));
        roads.add(new Road(28, cities.get(7), cities.get(4), 765));

        roads.add(new Road(29, cities.get(9), cities.get(6), 40));

        roads.add(new Road(30, cities.get(8), cities.get(7), 50));
        for (Road road : roads) {
            road.getFromCity().getRoads().add(road);
            road.getToCity().getRoads().add(road);
        }
    }

    @Test
    public void useAppContext() throws Exception {
        appContext = InstrumentationRegistry.getTargetContext();
        assertEquals("com.example.volodymyrdudas.dijkstraalg", appContext.getPackageName());
    }

    @After
    public void end() {
        cities = null;
        roads = null;
    }

    @Test
    public void JAVA_execute() throws Exception {
        Graph graph = new Graph(cities, roads);
        DijkstraAlgorithmJava dijkstraAlgorithmJava = new DijkstraAlgorithmJava(graph);
        long time = dijkstraAlgorithmJava.execute(cities.get(0));
        System.out.println(time);
    }

    @Test
    public void SQL_execute() throws Exception {
        mDatabaseHelper = new DatabaseHelper(appContext);
        mSqLiteDatabase = mDatabaseHelper.getWritableDatabase();
        mSqLiteDatabase.execSQL("DELETE FROM City;");
        mSqLiteDatabase.execSQL("DELETE FROM Road;");
        for (City city : cities) {
            mDatabaseHelper.cityDAO.create(city);
        }
        for (Road road : roads) {
            mDatabaseHelper.roadDAO.create(road);
        }
        DijkstraAlgorithmSQL dijkstraAlgorithmSQL = new DijkstraAlgorithmSQL(mSqLiteDatabase);
        long time = dijkstraAlgorithmSQL.execute(1);
        System.out.println(time);
    }
}
