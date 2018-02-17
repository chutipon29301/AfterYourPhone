package com.example.afteryourphone.activity;

import android.annotation.SuppressLint;
import android.location.Location;
import android.os.Bundle;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.example.afteryourphone.R;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnSuccessListener;
import com.mapzen.speakerbox.Speakerbox;

public class MainActivity extends AppCompatActivity {

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
                        // Got last known location. In some rare situations this can be null.
                        if (location != null) {
                            // Logic to handle location object
                            Log.d(TAG, "onSuccess: "+location);
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
            speakerbox.play("I love cherprang");
        }

        @Override public void onThreeFingerSingleTap() {
            // Three fingers single tap
            speakerbox.play("rely on file");
        }

        @Override public void onDoubleTap() {
            // Double tap

            speakerbox.play("BNK FORTY EIGHT");
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
                    Log.d("gesture", "onSwipe: up");
                    break;
                case TouchTypeDetector.SWIPE_DIR_DOWN:
                    // Swipe Down
                    break;
                case TouchTypeDetector.SWIPE_DIR_LEFT:
                    // Swipe Left
                    break;
                case TouchTypeDetector.SWIPE_DIR_RIGHT:
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

}
