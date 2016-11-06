package com.example.volodymyrdudas.dijkstraalg.javaimpl;

import com.example.volodymyrdudas.dijkstraalg.model.City;
import com.example.volodymyrdudas.dijkstraalg.model.Graph;
import com.example.volodymyrdudas.dijkstraalg.model.Road;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DijkstraAlgorithmJava {
    private final List<City> cities;
    private final List<Road> roads;
    private Set<City> settledNodes;
    private Set<City> unSettledNodes;
    private Map<City, City> predecessors;
    private Map<City, Double> distance;

    public DijkstraAlgorithmJava(Graph graph) {
        this.cities = new ArrayList<City>(graph.getCities());
        this.roads = new ArrayList<Road>(graph.getRoads());
    }

    public long execute(City source) {
        long startTime = System.currentTimeMillis();
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
        return System.currentTimeMillis() - startTime;
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
        for (Road edge : roads) {
            if (edge.getFromCity().equals(node)
                    && edge.getToCity().equals(target)) {
                return edge.getDistance();
            }
        }
        throw new RuntimeException("Should not happen");
    }

    private List<City> getNeighbors(City node) {
        List<City> neighbors = new ArrayList<City>();
        for (Road edge : roads) {
            if (edge.getFromCity().equals(node)
                    && !isSettled(edge.getToCity())) {
                neighbors.add(edge.getToCity());
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
