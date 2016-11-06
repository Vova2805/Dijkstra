package com.example.volodymyrdudas.dijkstraalg.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Road")
public class Road {
    @DatabaseField(id = true, generatedId = true, canBeNull = false, columnName = "RoadId", dataType = DataType.INTEGER)
    private int roadId;
    @DatabaseField(canBeNull = false, columnName = "FromCity", dataType = DataType.INTEGER)
    private int fromCity;
    @DatabaseField(canBeNull = false, columnName = "ToCity", dataType = DataType.INTEGER)
    private int ToCity;
    @DatabaseField(canBeNull = false, columnName = "Distance", dataType = DataType.DOUBLE)
    private double distance;

    public Road() {
    }

    public int getRoadId() {
        return roadId;
    }

    public void setRoadId(int roadId) {
        this.roadId = roadId;
    }

    public int getFromCity() {
        return fromCity;
    }

    public void setFromCity(int fromCity) {
        this.fromCity = fromCity;
    }

    public int getToCity() {
        return ToCity;
    }

    public void setToCity(int toCity) {
        ToCity = toCity;
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }
}