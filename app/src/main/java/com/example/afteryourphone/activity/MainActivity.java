package com.example.afteryourphone.activity;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.example.afteryourphone.R;
import com.example.afteryourphone.dao.PlaceDetailDao;
import com.example.afteryourphone.dao.PlaceListDetailDao;
import com.example.afteryourphone.dao.TempResponseDao;
import com.example.afteryourphone.manager.LocationManager;
import com.example.afteryourphone.manager.PlaceDetailDataManager;
import com.example.afteryourphone.manager.PlaceListDataManager;
import com.example.afteryourphone.manager.TempManager;
import com.example.afteryourphone.util.Contextor;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.mapzen.speakerbox.Speakerbox;

public class MainActivity extends AppCompatActivity implements PlaceDetailDataManager.onLoadDistance, LocationManager.onLocationLoad, TempManager.onLoadComplete {
    private static String TAG = "MainActivity";
    private static final int REQUEST_LIST = 0;
    private static final int REQUEST_DETAIL = 1;
    private static final int REQUEST_TEMP = 2;
    private static final int REQUEST_CALL_PERMISSION = 3;
    private boolean firstSwipe = true;

    Speakerbox speakerbox;
    Vibrator v;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sensey.getInstance().init(this);
        Sensey.getInstance().startTouchTypeDetection(this, touchTypListener);
        speakerbox = new Speakerbox(getApplication());
        LocationManager.getInstance().getLocation(this, this, REQUEST_LIST);
        LocationManager.getInstance().getLocation(this, this, REQUEST_TEMP);
        v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        // Setup onTouchEvent for detecting type of touch gesture
        Sensey.getInstance().setupDispatchTouchEvent(event);
        return super.dispatchTouchEvent(event);
    }

    TouchTypeDetector.TouchTypListener touchTypListener = new TouchTypeDetector.TouchTypListener() {
        @Override
        public void onTwoFingerSingleTap() {
            // Two fingers single tap
//            v.vibrate(500);
//            speakerbox.play("You just tab with 2 Fingers,");
        }

        @Override
        public void onThreeFingerSingleTap() {
            // Three fingers single tap]
            Intent intent = new Intent(Intent.ACTION_CALL);
            v.vibrate(500);
            intent.setData(Uri.parse("tel:0814953366"));
            if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED) {
                if (ActivityCompat.shouldShowRequestPermissionRationale(MainActivity.this, Manifest.permission.CALL_PHONE)) {
                    speakerbox.play("Please allow calling permission");
                    ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
                } else {
                    speakerbox.play("Calling");
                    startActivity(intent);
                }
            } else {
                speakerbox.play("Please allow calling permission");
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CALL_PERMISSION);
            }
        }

        @Override
        public void onDoubleTap() {
            // Double tap
            v.vibrate(100);
            speakerbox.play("You just do double tabs, ");
        }

        @Override
        public void onScroll(int scrollDirection) {
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

        @Override
        public void onSingleTap() {
            // Single tap
            PlaceListDetailDao current = PlaceListDataManager.getInstance().current();
            if (current == null) return;
            speakerbox.play(current.getName());
            Log.d("gesture", "tap");
        }

        @Override
        public void onSwipe(int swipeDirection) {
            // Vibrate for 500 milliseconds
            v.vibrate(50);

            switch (swipeDirection) {
                case TouchTypeDetector.SWIPE_DIR_UP:
                    if (PlaceListDataManager.getInstance().current() == null) return;
                    LocationManager.getInstance().getLocation(MainActivity.this, MainActivity.this, REQUEST_DETAIL);
                    break;
                case TouchTypeDetector.SWIPE_DIR_DOWN:
                    // Swipe Down
                    Log.d(TAG, "onSwipe: down");
                    break;
                case TouchTypeDetector.SWIPE_DIR_LEFT:
                    PlaceListDetailDao next = PlaceListDataManager.getInstance().next();
                    Log.i(TAG, "onSwipe: " + speakerbox);
                    if (next == null) {
                        speakerbox.play("There's no place left to show");
                    } else {
                        if (firstSwipe) {
                            speakerbox.play(next.getName());
                            firstSwipe = !firstSwipe;
                        } else speakerbox.play("Next place, " + next.getName());
                    }
                    // Swipe Left
                    break;
                case TouchTypeDetector.SWIPE_DIR_RIGHT:
                    PlaceListDetailDao previous = PlaceListDataManager.getInstance().previous();
                    if (previous == null) {
                        speakerbox.play("There's no previous place to show");
                    } else {
                        speakerbox.play("Previous place, " + previous.getName());
                    }
                    // Swipe Right
                    break;
                default:
                    //do nothing
                    break;
            }
        }

        @Override
        public void onLongPress() {
            v.vibrate(600);
            Log.d("gesture", "longpress");
            speakerbox.play("Here's the instructions," + " Please swipe your finger on the screen to the left for visit the next place, and" +
                    "swipe to the right to revisit the previous one, Swipe up to listen to detail of that particular place and tab on the screen to listen again" + "Tab 3 fingers to call emergency contact");

        }
    };

    @Override
    public void onLoad(PlaceDetailDao placeDetail) {
        speakerbox.play(PlaceListDataManager.getInstance().current().getName() +
                ", Distance " + Math.round(placeDetail.getDistance() / 100) / 10.0 + " kilometers" +
                ", Estimated time " + Math.round(placeDetail.getTime() / 6) / 10.0 + " minutes");
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case LocationManager.LOCATION_REQUEST:
                LocationManager.getInstance().getLocation(this, this, REQUEST_LIST);
                break;
        }
    }

    @Override
    public void onLoadLocation(Location location, int requestID) {
        if (location == null) return;
        Log.i(TAG, "onLoad: requestID = " + requestID);
        switch (requestID) {
            case REQUEST_LIST:
                PlaceListDataManager.getInstance().getPlace(location.getLatitude(), location.getLongitude());
                break;
            case REQUEST_DETAIL:
                PlaceDetailDataManager.getInstance().getPlaceDetail(PlaceListDataManager.getInstance().current().getPlaceId(), location.getLatitude(), location.getLongitude(), this);
                break;
            case REQUEST_TEMP:
                TempManager.getInstance().getTemp(location.getLatitude(), location.getLongitude(), this);
                break;
        }
    }

    @Override
    public void onLoadTemp(TempResponseDao temp) {
//        speakerbox.play("The temperature in " + temp.getLocation().substring(0, temp.getLocation().indexOf('\'')));
        speakerbox.play("Currently, It's " + temp.getRain() + " around you and the temperature is " + temp.getTemp() + " degree celsius, Long press for help ");
    }


}
