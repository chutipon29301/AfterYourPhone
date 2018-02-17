package com.example.afteryourphone.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 17/2/2018 AD.
 */

public class PlaceListDetailDao {
    @SerializedName("name")         String name;
    @SerializedName("place_id")     String placeId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }
}
