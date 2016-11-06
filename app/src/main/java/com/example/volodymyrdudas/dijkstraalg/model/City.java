package com.example.volodymyrdudas.dijkstraalg.model;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "City")
public class City {
    @DatabaseField(id = true, canBeNull = false, columnName = "CityId")
    private Integer cityId;
    @DatabaseField(canBeNull = false, columnName = "Name")
    private String name;

    public City() {
    }

    public City(int cityId, String name) {
        this.cityId = cityId;
        this.name = name;
    }

    public Integer getCityId() {
        return cityId;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((cityId == null) ? 0 : cityId.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        City other = (City) obj;
        if (cityId == null) {
            if (other.cityId != null)
                return false;
        } else if (!cityId.equals(other.cityId))
            return false;
        return true;
    }

    @Override
    public String toString() {
        return name;
    }
}
