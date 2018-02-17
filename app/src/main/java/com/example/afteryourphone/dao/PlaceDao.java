package com.example.afteryourphone.dao;

/**
 * Created by admin on 17/2/2018 AD.
 */

public class PlaceDao {
    final String destination_id;
    final LocationDao origin;

    public PlaceDao(String destination_id, double lat, double lon) {
        this.destination_id = destination_id;
        this.origin = new LocationDao(lat, lon);
    }
}
