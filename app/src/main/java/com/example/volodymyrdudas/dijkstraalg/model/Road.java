package com.example.volodymyrdudas.dijkstraalg.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "Road")
public class Road {
    @DatabaseField(id = true, canBeNull = false, columnName = "RoadId", dataType = DataType.INTEGER)
    private int roadId;
    @DatabaseField(canBeNull = false, columnName = "FromCity", foreign = true)
    private City fromCity;
    @DatabaseField(canBeNull = false, columnName = "ToCity", foreign = true)
    private City ToCity;
    @DatabaseField(canBeNull = false, columnName = "Distance", dataType = DataType.DOUBLE)
    private double distance;

    public Road() {
    }

    public Road(int roadId, City fromCity, City toCity, double distance) {
        this.roadId = roadId;
        this.fromCity = fromCity;
        ToCity = toCity;
        this.distance = distance;
    }

    public int getRoadId() {
        return roadId;
    }

    public City getFromCity() {
        return fromCity;
    }

    public City getToCity() {
        return ToCity;
    }

    public double getDistance() {
        return distance;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Road road = (Road) o;

        if (roadId != road.roadId) return false;
        if (Double.compare(road.distance, distance) != 0) return false;
        if (!fromCity.equals(road.fromCity)) return false;
        return ToCity.equals(road.ToCity);

    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        result = roadId;
        result = 31 * result + fromCity.hashCode();
        result = 31 * result + ToCity.hashCode();
        temp = Double.doubleToLongBits(distance);
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        return result;
    }
}