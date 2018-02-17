package com.example.afteryourphone.activity;

import android.location.Location;
import android.os.Bundle;
<<<<<<<HEAD
import android.support.annotation.NonNull;
=======

        >>>>>>>75b128482170eaf9a9b38af77ca8d9c8f57dbd5e
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MotionEvent;

import com.example.afteryourphone.R;
<<<<<<<HEAD
import com.example.afteryourphone.dao.PlaceDetailDao;
import com.example.afteryourphone.dao.PlaceListDetailDao;
=======

import com.example.afteryourphone.dao.PlaceDetailDao;
import com.example.afteryourphone.dao.PlaceListDetailDao;

>>>>>>>75b128482170eaf9a9b38af77ca8d9c8f57dbd5e
import com.example.afteryourphone.manager.LocationManager;
import com.example.afteryourphone.manager.PlaceDetailDataManager;
import com.example.afteryourphone.manager.PlaceListDataManager;
import com.github.nisrulz.sensey.Sensey;
import com.github.nisrulz.sensey.TouchTypeDetector;
import com.mapzen.speakerbox.Speakerbox;

public class MainActivity extends AppCompatActivity implements PlaceDetailDataManager.onLoadDistance, LocationManager.onLocationLoad {
    private static String TAG = "MainActivity";
    private static final int REQUEST_LIST = 0;
    private static final int REQUEST_DETAIL = 1;

    Speakerbox speakerbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Sensey.getInstance().init(this);
        Sensey.getInstance().startTouchTypeDetection(this, touchTypListener);

<<<<<<<HEAD
                speakerbox = new Speakerbox(getApplication());

        LocationManager.getInstance().getLocation(this, this, REQUEST_LIST);

//        FusedLocationProviderClient mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
//        mFusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
//                    @Override
//                    public void onSuccess(Location location) {
//                        if (location != null) {
//                            Log.d(TAG, "onSuccess: " + location);
//                            PlaceListDataManager.getInstance().getPlace(location.getLatitude(), location.getLongitude());
//                            LocationManager.getInstance().setLocation(location);
//                        }
//                    }
//                });
//        LocationManager location = LocationManager.getInstance();
//        speakerbox.play("Current Temperature is" + HttpManager.getInstance().getApiService().getTemp(new LocationDao(location.getlatitude(), location.getlongtitude())));
=======

        speakerbox = new Speakerbox(getApplication());

        mFusedLocationClient = LocationServices.getFusedLocationProviderClient(this);
        mFusedLocationClient.getLastLocation()
                .addOnSuccessListener(this, new OnSuccessListener<Location>() {
                    @Override
                    public void onSuccess(Location location) {
                        if (location != null) {
                            Log.d(TAG, "onSuccess: " + location);
                            PlaceListDataManager.getInstance().getPlace(location.getLatitude(), location.getLongitude());
                            LocationManager.getInstance().setLocation(location);
                        }
                    }
                });
        LocationManager location = LocationManager.getInstance();
        speakerbox.play("Tab long press to listen to the instructions");
//        speakerbox.play("Current Temperature is"+ HttpManager.getInstance().getApiService().getTemp(new LocationDao(location.getlatitude(),location.getlongtitude())));
>>>>>>>75 b128482170eaf9a9b38af77ca8d9c8f57dbd5e

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
            speakerbox.play("You just tab with 2 Fingers,");
        }

        @Override
        public void onThreeFingerSingleTap() {
            // Three fingers single tap]
//            Intent intent = new Intent(Intent.ACTION_CALL);
//
//            intent.setData(Uri.parse("tel:0814953366"));
//            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
//                // TODO: Consider calling
//                //    ActivityCompat#requestPermissions
//                // here to request the missing permissions, and then overriding
//                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
//                //                                          int[] grantResults)
//                // to handle the case where the user grants the permission. See the documentation
//                // for ActivityCompat#requestPermissions for more details.
//                return;
//            }
//            startActivity(intent);
            speakerbox.play("You just tab with 3 fingers,");
        }

        @Override
        public void onDoubleTap() {
            // Double tap
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
            switch (swipeDirection) {
                case TouchTypeDetector.SWIPE_DIR_UP:
                    // Swipe Up
                    PlaceListDetailDao current = PlaceListDataManager.getInstance().current();
                    if (current == null) return;
                    LocationManager.getInstance().getLocation(MainActivity.this, MainActivity.this, REQUEST_DETAIL);
//                    LocationManager.getInstance().getLocation(MainActivity.this, MainActivity.this);
//                    PlaceDetailDataManager.getInstance().getPlaceDetail(current.getPlaceId(),
//                            LocationManager.getInstance().getlatitude(), LocationManager.getInstance().getlongtitude(), MainActivity.this);
                    Log.d("id", "onSwipe: " + current.getPlaceId());
                    Log.d(TAG, "onSwipe: up");
                    break;
                case TouchTypeDetector.SWIPE_DIR_DOWN:
                    // Swipe Down
                    Log.d(TAG, "onSwipe: down");
                    break;
                case TouchTypeDetector.SWIPE_DIR_LEFT:
                    PlaceListDetailDao next = PlaceListDataManager.getInstance().next();
                    if (next != null) {
                        speakerbox.play("Next place, " + next.getName());
                    } else {
                        speakerbox.play("There's no place left to show");
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
            Log.d("gesture", "longpress");

<<<<<<<HEAD
            speakerbox.play("Here's the instructions. Please swipe your finger on the screen to the left for visit the next place, and" +
                    "swipe to the right to revisit the previous one. Swipe up to listen to detail of that particular place and tab on the screen to listen again");
=======
            speakerbox.play("Here's the instructions," + " Please swipe your finger on the screen to the left for visit the next place, and" +
                    "swipe to the right to revisit the previous one, Swipe up to listen to detail of that particular place and tab on the screen to listen again");
>>>>>>>75 b128482170eaf9a9b38af77ca8d9c8f57dbd5e

        }
    };

    @Override
    public void onLoad(PlaceDetailDao placeDetail) {
<<<<<<<HEAD
        speakerbox.play(PlaceListDataManager.getInstance().current().getName() +
                ", Distance " + placeDetail.getDistance() +
                ", Time " + placeDetail.getTime());
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
    public void onLoad(Location location, int requestID) {
        switch (requestID) {
            case REQUEST_LIST:
                PlaceListDataManager.getInstance().getPlace(location.getLatitude(), location.getLongitude());
                break;
            case REQUEST_DETAIL:
                PlaceDetailDataManager.getInstance().getPlaceDetail(PlaceListDataManager.getInstance().current().getPlaceId(), location.getLatitude(), location.getLongitude(), this);
                break;
        }
=======

        speakerbox.play(PlaceListDataManager.getInstance().current().getName() +
                ", Distance " + Math.round(placeDetail.getDistance() / 100) / 10.0 + " kilometers" +
                ", Estimated time " + Math.round(placeDetail.getTime() / 6) / 10.0 + " minutes");
>>>>>>>75 b128482170eaf9a9b38af77ca8d9c8f57dbd5e
    }
}
