package com.example.volodymyrdudas.dijkstraalg.javaimpl;

import com.example.volodymyrdudas.dijkstraalg.model.City;
import com.example.volodymyrdudas.dijkstraalg.model.Graph;
import com.example.volodymyrdudas.dijkstraalg.model.Road;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class DijkstraAlgorithmJavaTest {
    private List<City> cities;
    private List<Road> roads;

    @Before
    public void init() {
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

        roads.add(new Road(10, cities.get(3), cities.get(9), 342));
        roads.add(new Road(11, cities.get(3), cities.get(5), 23));
        roads.add(new Road(12, cities.get(3), cities.get(8), 87));

        roads.add(new Road(13, cities.get(4), cities.get(6), 32));
        roads.add(new Road(14, cities.get(4), cities.get(7), 765));

        roads.add(new Road(17, cities.get(6), cities.get(9), 40));
    }

    @After
    public void end() {
        cities = null;
        roads = null;
    }

    @Test
    public void execute() throws Exception {
        Graph graph = new Graph(cities, roads);
        DijkstraAlgorithmJava dijkstraAlgorithmJava = new DijkstraAlgorithmJava(graph);
        long time = dijkstraAlgorithmJava.execute(cities.get(0));
    }
}