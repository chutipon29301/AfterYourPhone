package com.example.afteryourphone.manager;

import com.example.afteryourphone.dao.LocationDao;
import com.example.afteryourphone.dao.PlaceListDao;
import com.example.afteryourphone.dao.TempResponseDao;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by admin on 18/2/2018 AD.
 */

public class TempManager {
    public interface onLoadComplete {
        void onLoadTemp(TempResponseDao temp);
    }

    private static TempManager instance;

    private TempManager() {
    }

    public static TempManager getInstance() {
        if (instance == null) {
            instance = new TempManager();
        }
        return instance;
    }

    public void getTemp(double lat, double lon, final onLoadComplete callback) {
        HttpManager.getInstance().getApiService().getTemp(new LocationDao(lat, lon))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<TempResponseDao>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(TempResponseDao value) {
                        callback.onLoadTemp(value);
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
