package com.example.volodymyrdudas.dijkstraalg.javaimpl.model;

import com.example.volodymyrdudas.dijkstraalg.model.City;
import com.example.volodymyrdudas.dijkstraalg.model.Road;

import java.util.List;

public class Graph {
    private final List<City> cities;
    private final List<Road> roads;

    public Graph(List<City> cities, List<Road> roads) {
        this.cities = cities;
        this.roads = roads;
    }

    public List<City> getCities() {
        return cities;
    }

    public List<Road> getRoads() {
        return roads;
    }
}