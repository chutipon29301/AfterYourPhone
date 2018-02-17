package com.example.afteryourphone.manager;

import com.example.afteryourphone.R;
import com.example.afteryourphone.manager.http.ApiService;
import com.example.afteryourphone.util.Contextor;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by admin on 9/12/2017 AD.
 */

public class HttpManager {
    private static final String TAG = "HttpManager";
    private static  HttpManager instance;
    private ApiService apiService;

    private HttpManager(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://prod-18.southeastasia.logic.azure.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);
    }

    public static HttpManager getInstance(){
        if (instance == null){
            instance = new HttpManager();
        }
        return instance;
    }

    public ApiService getApiService(){
        return apiService;
    }


}
