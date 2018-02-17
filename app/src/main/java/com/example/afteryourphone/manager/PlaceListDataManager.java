package com.example.afteryourphone.manager;

import com.example.afteryourphone.dao.LocationDao;
import com.example.afteryourphone.dao.PlaceListDao;
import com.example.afteryourphone.dao.PlaceListDetailDao;

import java.util.ArrayList;

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

    private PlaceListDataManager() {
        HttpManager.getInstance().getApiService().getPlaceList(new LocationDao(13.760053, 100.566896))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread()).subscribe(new Observer<PlaceListDao>() {
            @Override
            public void onSubscribe(Disposable d) {

            }

            @Override
            public void onNext(PlaceListDao value) {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

    public static PlaceListDataManager getInstance() {
        if (instance == null) {
            instance = new PlaceListDataManager();
        }
        return instance;
    }
}
