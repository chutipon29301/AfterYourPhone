package com.example.afteryourphone.manager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;

import com.example.afteryourphone.activity.MainActivity;
import com.example.afteryourphone.util.Contextor;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;

/**
 * Created by admin on 17/2/2018 AD.
 */

public class LocationManager {

    private FusedLocationProviderClient mFusedLocationClient;
    public static final int LOCATION_REQUEST = 0;
    private Location location;

    public interface onLocationLoad {
        public void onLoad(Location location, int requestID);
    }

    private static LocationManager instance;

    private LocationManager() {
    }


    public static LocationManager getInstance() {
        if (instance == null) {
            instance = new LocationManager();
        }
        return instance;
    }

    public void getLocation(Activity activity, final onLocationLoad callback, final int requestID) {
        if (location != null) {
            callback.onLoad(getLoadedLocation(), requestID);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Contextor.getInstance().getContext());
        if (ActivityCompat.checkSelfPermission(Contextor.getInstance().getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED
                && ActivityCompat.checkSelfPermission(Contextor.getInstance().getContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            callback.onLoad(location, requestID);
                        }
                    }
                });
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST);
            }
        }
    }

    private Location getLoadedLocation(){
        return location;
    }
}
