package com.example.afteryourphone.manager;

import android.util.Log;

import com.example.afteryourphone.dao.LocationDao;
import com.example.afteryourphone.dao.PlaceListDao;
import com.example.afteryourphone.dao.PlaceListDetailDao;

import java.util.ArrayList;
import java.util.Arrays;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 17/2/2018 AD.
 */

public class PlaceListDataManager {
    private static PlaceListDataManager instance;
    private ArrayList<PlaceListDetailDao> placeList;
    private int counter = 0;
    private static final String TAG = "PlaceListDataManager";

    private PlaceListDataManager() {
        placeList = new ArrayList<>();
    }

    public static PlaceListDataManager getInstance() {
        Log.i(TAG, "getInstance: called");
        if (instance == null) {
            instance = new PlaceListDataManager();
        }
        return instance;
    }

    public void getPlace(double lat, double lon) {
        Log.i(TAG, "getPlace: called");
        HttpManager.getInstance().getApiService().getPlaceList(new LocationDao(lat, lon))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PlaceListDao>() {
            @Override
            public void onSubscribe(Disposable d) {
                Log.d(TAG, "onSubscribe: called");
            }

            @Override
            public void onNext(PlaceListDao value) {
                Log.d(TAG, "onNext: called");
                placeList.addAll(Arrays.asList(value.getResults()));
            }

            @Override
            public void onError(Throwable e) {
                Log.e(TAG, "onError: ", e);
            }

            @Override
            public void onComplete() {
                Log.d(TAG, "onComplete: called");
            }
        });
    }

    public PlaceListDetailDao next() {
        Log.d("list", "next: " + counter + "," + placeList.size());
        if (counter >= placeList.size()) return null;
        return placeList.get(++counter);
    }

    public PlaceListDetailDao current() {
        Log.d("list", "next: " + counter + "," + placeList.size());
        if (counter >= placeList.size()) return null;
        return placeList.get(counter);
    }


    public PlaceListDetailDao previous() {
        Log.d("list", "next: " + counter + "," + placeList.size());
        if (counter <= 0) return null;
        return placeList.get(--counter);
    }

}
