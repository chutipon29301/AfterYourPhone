package com.example.afteryourphone.activity;

import android.annotation.SuppressLint;
import android.location.Location;

import android.os.Bundle;

import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.example.afteryourphone.R;
import com.example.afteryourphone.dao.PlaceDetailDao;
import com.example.afteryourphone.dao.PlaceListDetailDao;
import com.example.afteryourphone.manager.LocationManager;
import com.example.afteryourphone.manager.PlaceDetailDataManager;
import com.example.afteryourphone.manager.PlaceListDataManager;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mapzen.speakerbox.Speakerbox;

public class MainActivity extends AppCompatActivity implements PlaceDetailDataManager.onLoadDistance {
    public final MainActivity mainact = this;
    private static String TAG = "MainActivity";

    private FusedLocationProviderClient mFusedLocationClient;
    Speakerbox speakerbox;
    GoogleApiClient mGoogleApiClient;



    @SuppressLint("MissingPermission")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sensey.getInstance().init(this);
        Sensey.getInstance().startTouchTypeDetection(this, touchTypListener);



        speakerbox = new Speakerbox(getApplication());
        speakerbox.play("Hello Non, Wakada forever!");

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d(TAG, "onSuccess: "+location);
                            PlaceListDataManager.getInstance().getPlace(location.getLatitude(),location.getLongitude());
                            LocationManager.getInstance().setLocation(location);
                        }
                    }
                });
    }

    @Override public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    TouchTypeDetector.TouchTypListener touchTypListener = new TouchTypeDetector.TouchTypListener() {
        @Override public void onTwoFingerSingleTap() {
            // Two fingers single tap
            speakerbox.play("You just tab with 2 Fingers,");
        }

        @Override public void onThreeFingerSingleTap() {
            // Three fingers single tap]
            speakerbox.play("You just tab with 3 fingers,");
        }

        @Override public void onDoubleTap() {
            // Double tap
            speakerbox.play("You just do double tabs, ");
        }

        @Override public void onScroll(int scrollDirection) {
            switch (scrollDirection) {
                case TouchTypeDetector.SCROLL_DIR_UP:
                    // Scrolling Up
                    break;
                case TouchTypeDetector.SCROLL_DIR_DOWN:
                    // Scrolling Down
                    break;
                case TouchTypeDetector.SCROLL_DIR_LEFT:
                    // Scrolling Left
                    break;
                case TouchTypeDetector.SCROLL_DIR_RIGHT:
                    // Scrolling Right
                    break;
                default:
                    // Do nothing
                    break;
            }
        }

        @Override public void onSingleTap() {
            // Single tap
            speakerbox.play("Hello Non, Wakada forever!");
            Log.d("gesture", "tap");
        }

        @Override public void onSwipe(int swipeDirection) {
            switch (swipeDirection) {
                case TouchTypeDetector.SWIPE_DIR_UP:
                    // Swipe Up
                    PlaceListDetailDao current = PlaceListDataManager.getInstance().current();
                    PlaceDetailDataManager.getInstance().getPlaceDetail(current.getPlaceId(),
                        LocationManager.getInstance().getlatitude(),LocationManager.getInstance().getlongtitude(),MainActivity.this);
                    Log.d("id", "onSwipe: "+current.getPlaceId());
                    break;
                case TouchTypeDetector.SWIPE_DIR_DOWN:
                    // Swipe Down
                    break;
                case TouchTypeDetector.SWIPE_DIR_LEFT:

                    speakerbox.play("Next place, "+ PlaceListDataManager.getInstance().next().getName());
                    // Swipe Left
                    break;
                case TouchTypeDetector.SWIPE_DIR_RIGHT:

                    speakerbox.play("Previous place, "+PlaceListDataManager.getInstance().previous().getName());
                    // Swipe Right
                    break;
                default:
                    //do nothing
                    break;
            }
        }

        @Override public void onLongPress() {
            Log.d("gesture", "longpress");
            // Long press
        }
    };


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = MotionEventCompat.getActionMasked(event);

        switch (action) {
            case MotionEvent.ACTION_DOWN:
                Log.d(TAG, "onTouchEvent: actionDown");
                return true;
        }
        return super.onTouchEvent(event);
    }

    @Override
    public void onLoad(PlaceDetailDao placeDetail) {

        speakerbox.play(PlaceListDataManager.getInstance().current().getName()+
                ", Distance "+ Math.round(placeDetail.getDistance()/100)/10.0 + " kilometers" +
                ", Estimated time "+ Math.round(placeDetail.getTime() / 6)/10.0 + " minutes" );
    }
}
