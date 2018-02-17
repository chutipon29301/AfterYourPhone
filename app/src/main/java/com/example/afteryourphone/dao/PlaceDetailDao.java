package com.example.afteryourphone.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 17/2/2018 AD.
 */

public class PlaceDetailDao {
    @SerializedName("distance")     double distance;
    @SerializedName("time")         double time;

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getTime() {
        return time;
    }

    public void setTime(double time) {
        this.time = time;
    }

}
