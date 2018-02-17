package com.example.afteryourphone.manager;

import com.example.afteryourphone.dao.PlaceDao;
import com.example.afteryourphone.dao.PlaceDetailDao;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 17/2/2018 AD.
 */

public class PlaceDetailDataManager {

    public interface onLoadDistance {
        public void onLoad(PlaceDetailDao placeDetail);
    }

    private static PlaceDetailDataManager instance;

    private PlaceDetailDataManager() {
    }

    public static PlaceDetailDataManager getInstance() {
        if (instance == null) {
            instance = new PlaceDetailDataManager();
        }
        return instance;
    }

    public void getPlaceDetail(String placeID, double lat, double lon, final onLoadDistance callback) {
        HttpManager.getInstance().getApiService().getPlaceDetail(new PlaceDao(placeID, lat, lon))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<PlaceDetailDao>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(PlaceDetailDao value) {
                        callback.onLoad(value);
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
}
