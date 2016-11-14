package com.example.volodymyrdudas.dijkstraalg.model;

import java.util.List;

public class Graph {
    private final List<City> cities;

    public Graph(List<City> cities) {
        this.cities = cities;
    }

    public List<City> getCities() {
        return cities;
    }
}