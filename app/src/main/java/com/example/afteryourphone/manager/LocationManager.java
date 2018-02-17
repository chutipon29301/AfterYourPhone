package com.example.afteryourphone.manager;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.util.Log;

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
    private static Location location;
    private static final String TAG = "LocationManager";

    public interface onLocationLoad {
        void onLoadLocation(Location location, int requestID);
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
            callback.onLoadLocation(getLoadedLocation(), requestID);
        }
        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(Contextor.getInstance().getContext());
        if (ActivityCompat.checkSelfPermission(Contextor.getInstance().getContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            Log.i(TAG, "getLocation: called");
            if (ActivityCompat.shouldShowRequestPermissionRationale(activity,
                    Manifest.permission.ACCESS_FINE_LOCATION)) {

                mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        Log.i(TAG, "onSuccess: called");
                        if (location != null) {
                            LocationManager.location = location;
                            callback.onLoadLocation(location, requestID);
                        }else {
                            callback.onLoadLocation(null, requestID);
                        }
                    }
                });
            } else {
                ActivityCompat.requestPermissions(activity,
                        new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                        LOCATION_REQUEST);
            }
        }else {
            Log.i(TAG, "getLocation: else");
            mFusedLocationClient.getLastLocation().addOnSuccessListener(new OnSuccessListener<Location>() {
                @Override
                public void onSuccess(Location location) {
                    Log.i(TAG, "onSuccess: called");
                    if (location != null) {
                        LocationManager.location = location;
                        callback.onLoadLocation(location, requestID);
                    }else {
                        callback.onLoadLocation(null, requestID);
                    }
                }
            });
        }
    }

    private Location getLoadedLocation(){
        return location;
    }
}
