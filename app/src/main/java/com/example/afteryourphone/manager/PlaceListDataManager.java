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

    private PlaceListDataManager() {
        placeList = new ArrayList<>();
    }

    public static PlaceListDataManager getInstance() {
        if (instance == null) {
            instance = new PlaceListDataManager();
        }
        return instance;
    }

    public void getPlace(double lat, double lon) {
        HttpManager.getInstance().getApiService().getPlaceList(new LocationDao(lat, lon))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PlaceListDao>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PlaceListDao value) {
                placeList.addAll(Arrays.asList(value.getResults()));
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public PlaceListDetailDao next() {
        if (counter >= placeList.size()) return null;
        Log.d("list", "next: "+counter+","+placeList.size());
        return placeList.get(++counter);
    }

    public PlaceListDetailDao previous() {
        if (counter <= 0) return null;

        Log.d("list", "next: "+counter+","+placeList.size());
        return placeList.get(--counter);
    }

}
