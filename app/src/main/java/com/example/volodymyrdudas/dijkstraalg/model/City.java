package com.example.volodymyrdudas.dijkstraalg.model;

import com.j256.ormlite.field.DataType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "City")
public class City {
    @DatabaseField(id = true, generatedId = true, canBeNull = false, columnName = "CityId", dataType = DataType.INTEGER)
    private int cityId;
    @DatabaseField(canBeNull = false, columnName = "Name", dataType = DataType.STRING)
    private int name;

    public City() {
    }

    public int getCityId() {
        return cityId;
    }

    public void setCityId(int cityId) {
        this.cityId = cityId;
    }
}
