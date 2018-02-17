package com.example.afteryourphone.manager.http;

import com.example.afteryourphone.dao.LocationDao;
import com.example.afteryourphone.dao.PlaceDao;
import com.example.afteryourphone.dao.PlaceDetailDao;
import com.example.afteryourphone.dao.PlaceListDao;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.POST;

/**
 * Created by admin on 9/12/2017 AD.
 */

public interface ApiService {

    @POST("https://prod-18.southeastasia.logic.azure.com/workflows/5e98e1bc5e2a463c86c494ab59e358d9/triggers/manual/paths/invoke/location?api-version=2016-10-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=XJ9Fhh2BmefTg_tufIHxyiJGIfu6n8jLRFv7et822X8")
    Observable<PlaceListDao> getPlaceList(@Body LocationDao locationDao);

    @POST("https://prod-12.southeastasia.logic.azure.com/workflows/dab9f33e82824571a277d1e0abe68dcb/triggers/manual/paths/invoke/locationdata?api-version=2016-10-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=QmRDrmxflAoEz4gr3Xi7nVPD0BHtv26EYvE9brBE-oE")
    Observable<PlaceDetailDao> getPlaceDetail(@Body PlaceDao placeDao);

    @POST("https://prod-24.southeastasia.logic.azure.com/workflows/9377fa40e5f24c00bc9b4fc84ecbcb1d/triggers/manual/paths/invoke/weather?api-version=2016-10-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=PcxhUj3ErMvdVuG4EXms_3H5ZxxSRbpWzSzJQ_kPPpk")
    Observable<PlaceListDao> getTemp(@Body LocationDao locationDao);

}
