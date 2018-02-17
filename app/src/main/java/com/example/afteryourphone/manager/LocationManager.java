package com.example.afteryourphone.manager;

import android.location.Location;

/**
 * Created by admin on 17/2/2018 AD.
 */

public class LocationManager {
    private static LocationManager instance;

    public void setLocation(Location location) {
        this.location = location;
    }

    private Location location;

    public static LocationManager getInstance(){
        if(instance==null){
            instance = new LocationManager();
        }
        return instance;
    }
    public Location getlocation(){
        return location;
    }
    public double getlatitude(){
        return location.getLatitude();
    } public double getlongtitude(){
        return location.getLongitude();
    }

}
