package com.example.volodymyrdudas.dijkstraalg.config;

public class ConfigParams {
    public static final String DATABASE_NAME = "dijkstra.db";
    public static final int DATABASE_VERSION = 1;

    public static final String CITY_TABLE = "City";
    public static final String ROAD_TABLE = "Road";

    public static final String CITY_CREATE_TABLE_SCRIPT = "CREATE TABLE " + CITY_TABLE + "(" +
            " CityId INTEGER PRIMARY KEY AUTOINCREMENT," +
            " Name VARCHAR(50) NOT NULL)";
    public static final String ROAD_CREATE_TABLE_SCRIPT = "CREATE TABLE " + ROAD_TABLE + "(" +
            " RoadId INTEGER PRIMARY KEY AUTOINCREMENT," +
            " FromCity INTEGER NOT NULL," +
            " ToCity INTEGER NOT NULL," +
            " Distance INTEGER NOT NULL" +
            ")";
    public static final String INSERT_DATA_SCRIPT = "INSERT INTO " + CITY_TABLE + " VALUES (1, 'Trondheim')," +
            "(2, 'Bergen'),(3, 'Oslo'),(4, 'Fredrikstad'),(5, 'Kristiansand');";

    public static final String INSERT_DATA_SCRIPT1 = "INSERT INTO " + ROAD_TABLE + " VALUES " +
            "(1,1, 2, 2),(2,1, 3, 6),(3,2, 1, 2),(4,2, 3, 3),(5,2, 5, 4),(6,3, 1, 6),(7,3, 2, 3),(8,3, 4, 1)," +
            "(9,3, 5, 3),(10,4, 3, 1),(11,4, 5, 5),(12,5, 2, 4),(13,5, 3, 3),(14,5, 4, 5);";
}
