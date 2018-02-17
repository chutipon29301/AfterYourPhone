package com.example.afteryourphone.activity;

import android.annotation.SuppressLint;
import android.location.Location;

import android.os.Bundle;

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
        LocationManager location = LocationManager.getInstance();
        speakerbox.play("Tab long press to listen to the instructions");
//        speakerbox.play("Current Temperature is"+ HttpManager.getInstance().getApiService().getTemp(new LocationDao(location.getlatitude(),location.getlongtitude())));

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

            PlaceListDetailDao current = PlaceListDataManager.getInstance().current();
            if(current==null)return;
            speakerbox.play(current.getName());
            Log.d("gesture", "tap");
        }

        @Override public void onSwipe(int swipeDirection) {
            switch (swipeDirection) {
                case TouchTypeDetector.SWIPE_DIR_UP:
                    // Swipe Up
                    PlaceListDetailDao current = PlaceListDataManager.getInstance().current();
                    if(current==null)return;
                    PlaceDetailDataManager.getInstance().getPlaceDetail(current.getPlaceId(),
                        LocationManager.getInstance().getlatitude(),LocationManager.getInstance().getlongtitude(),MainActivity.this);
                    Log.d("id", "onSwipe: "+current.getPlaceId());
                    break;
                case TouchTypeDetector.SWIPE_DIR_DOWN:
                    // Swipe Down
                    break;
                case TouchTypeDetector.SWIPE_DIR_LEFT:

                    PlaceListDetailDao next = PlaceListDataManager.getInstance().next();
                    if(next!=null) {
                        speakerbox.play("Next place, " + next.getName());
                        return;
                    }else{
                        speakerbox.play("There's no place left to show");
                    }
                    // Swipe Left
                    break;
                case TouchTypeDetector.SWIPE_DIR_RIGHT:
                    PlaceListDetailDao previous = PlaceListDataManager.getInstance().previous();
                    if(previous==null){
                        speakerbox.play("There's no previous place to show");
                        return;
                    }else {
                        speakerbox.play("Previous place, " + previous.getName());
                    }
                    // Swipe Right
                    break;
                default:
                    //do nothing
                    break;
            }
        }

        @Override public void onLongPress() {
            Log.d("gesture", "longpress");

            speakerbox.play("Here's the instructions,"+" Please swipe your finger on the screen to the left for visit the next place, and" +
                    "swipe to the right to revisit the previous one, Swipe up to listen to detail of that particular place and tab on the screen to listen again");

        }
    };


    @Override
    public void onLoad(PlaceDetailDao placeDetail) {

        speakerbox.play(PlaceListDataManager.getInstance().current().getName()+
                ", Distance "+ Math.round(placeDetail.getDistance()/100)/10.0 + " kilometers" +
                ", Estimated time "+ Math.round(placeDetail.getTime() / 6)/10.0 + " minutes" );
    }
}
