package com.example.volodymyrdudas.dijkstraalg.implAlgorithm;

import com.example.volodymyrdudas.dijkstraalg.config.ConfigParams;
import com.example.volodymyrdudas.dijkstraalg.db.DatabaseHelper;
import com.example.volodymyrdudas.dijkstraalg.model.City;
import com.example.volodymyrdudas.dijkstraalg.model.Road;
import com.j256.ormlite.stmt.PreparedQuery;
import com.j256.ormlite.stmt.QueryBuilder;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DijkstraAlgorithmJava {
    private Set<City> settledNodes;
    private Set<City> unSettledNodes;
    private Map<City, City> predecessors;
    private Map<City, Double> distance;

    public List<City> init(DatabaseHelper mDatabaseHelper) {
        List<City> cities = new ArrayList<>();
        try {
            QueryBuilder<City, Integer> queryBuilder = mDatabaseHelper.cityDAO.queryBuilder();
            queryBuilder.orderBy(ConfigParams.CITY_TABLE_ID, true);
            PreparedQuery<City> preparedQuery = queryBuilder.prepare();
            cities = mDatabaseHelper.cityDAO.query(preparedQuery);
            QueryBuilder<Road, Integer> queryBuilderRoad = mDatabaseHelper.roadDAO.queryBuilder();
            PreparedQuery<Road> preparedQueryRoad;
            for (City city : cities) {
                preparedQueryRoad = queryBuilderRoad.where().eq("FromCity", city.getCityId()).or().eq("ToCity", city.getCityId()).prepare();
                city.getRoads().addAll(mDatabaseHelper.roadDAO.query(preparedQueryRoad));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return cities;
    }

    public void execute(City source) {
        settledNodes = new HashSet<City>();
        unSettledNodes = new HashSet<City>();
        distance = new HashMap<City, Double>();
        predecessors = new HashMap<City, City>();
        distance.put(source, 0.0);
        unSettledNodes.add(source);
        while (unSettledNodes.size() > 0) {
            City node = getMinimum(unSettledNodes);
            settledNodes.add(node);
            unSettledNodes.remove(node);
            findMinimalDistances(node);
        }
    }

    private void findMinimalDistances(City node) {
        List<City> adjacentNodes = getNeighbors(node);
        for (City target : adjacentNodes) {
            if (getShortestDistance(target) > getShortestDistance(node)
                    + getDistance(node, target)) {
                distance.put(target, getShortestDistance(node)
                        + getDistance(node, target));
                predecessors.put(target, node);
                unSettledNodes.add(target);
            }
        }
    }

    private double getDistance(City node, City target) {
        for (Road road : node.getRoads()) {
            if (road.getToCity().equals(target)) {
                return road.getDistance();
            }
        }
        return Integer.MAX_VALUE;
    }

    private List<City> getNeighbors(City node) {
        List<City> neighbors = new ArrayList<>();
        for (Road road : node.getRoads()) {
            City neighbor = road.getToCity();
            if (!isSettled(neighbor)) {
                neighbors.add(road.getToCity());
            }
        }
        return neighbors;
    }

    private City getMinimum(Set<City> vertexes) {
        City minimum = null;
        for (City vertex : vertexes) {
            if (minimum == null) {
                minimum = vertex;
            } else {
                if (getShortestDistance(vertex) < getShortestDistance(minimum)) {
                    minimum = vertex;
                }
            }
        }
        return minimum;
    }

    private boolean isSettled(City vertex) {
        return settledNodes.contains(vertex);
    }

    private double getShortestDistance(City destination) {
        Double d = distance.get(destination);
        if (d == null) {
            return Integer.MAX_VALUE;
        } else {
            return d;
        }
    }

    public LinkedList<City> getPath(City target) {
        LinkedList<City> path = new LinkedList<City>();
        City step = target;
        if (predecessors.get(step) == null) {
            return null;
        }
        path.add(step);
        while (predecessors.get(step) != null) {
            step = predecessors.get(step);
            path.add(step);
        }
        Collections.reverse(path);
        return path;
    }
}
