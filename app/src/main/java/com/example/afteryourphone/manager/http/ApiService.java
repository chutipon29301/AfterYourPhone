package com.example.afteryourphone.manager.http;

import com.example.afteryourphone.dao.LocationDao;
import com.example.afteryourphone.dao.PlaceListDao;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * Created by admin on 9/12/2017 AD.
 */

public interface ApiService {
//    @FormUrlEncoded
//    @POST("/post/v1/checkExistUser")
//    Observable<CheckExistUserDao> checkExistUser(@Field("facebookID") String facebookID);
//
//    @POST("/post/v1/addUser")
//    Observable<GeneralResponseDao> addUser(@Body PreferenceListDao preferenceListDao);
//
//    @POST("/post/v1/listGenre")
//    Observable<GenreListDao> listGenre();
//
//    @FormUrlEncoded
//    @POST("/post/v1/listMovieSuggestion")
//    Observable<MovieSuggestionListDao> listMovieSuggestion(@Field("userID") String userID);
//
//    @POST("/post/v1/addReview")
//    Observable<GeneralResponseDao> addReview(@Body AddReviewDao addReviewDao);
//
//    @POST("/post/v1/listLocation")
//    Observable<LocationListDao> getLocation();
//
//    @FormUrlEncoded
//    @POST("/post/v1/searchMovie")
//    Observable<SearchResultListDao> searchMovie(@Field("key") String key);
//
//    @FormUrlEncoded
//    @POST("/post/v1/listReviewForMovie")
//    Observable<MovieReviewListDao> getMovieReview(@Field("movieID") int movieID);
//
//    @FormUrlEncoded
//    @POST("/post/v1/getRandomMovie")
//    Observable<MovieSuggestionInfoDao> getRandomMovie(@Field("userID") String userID);
//
//    @FormUrlEncoded
//    @POST("/post/v1/getMovieInfo")
//    Observable<MovieInfoDao> getMovieInfo(@Field("movieID") int movieID);
//
//    @FormUrlEncoded
//    @POST("/post/v1/addReadLater")
//    Observable<GeneralResponseDao> addReadLater(@Field("userID") String userID, @Field("reviewID") String reviewID);
//
//    @FormUrlEncoded
//    @POST("/post/v1/deleteReadLater")
//    Observable<GeneralResponseDao> deleteReadLater(@Field("userID") String userID, @Field("reviewID") String reviewID);
//
//    @FormUrlEncoded
//    @POST("/post/v1/listReadLaterReview")
//    Observable<MovieReviewListDao> getReadLaterMovieReviewList(@Field("userID") String userID);
//
//    @FormUrlEncoded
//    @POST("/post/v1/getReview")
//    Observable<MovieReviewInfoDao> getReview(@Field("reviewID") String reviewID);
//
//    @FormUrlEncoded
//    @POST("/post/v1/listMyReview")
//    Observable<MovieReviewListDao> getMyReview(@Field("userID") String userID);
//
//    @FormUrlEncoded
//    @POST("/post/v1/isInReadLater")
//    Observable<CheckReadLaterDao> checkReadLater(@Field("userID") String userID, @Field("reviewID") String reviewID);

    @POST("https://prod-18.southeastasia.logic.azure.com/workflows/5e98e1bc5e2a463c86c494ab59e358d9/triggers/manual/paths/invoke/location?api-version=2016-10-01&sp=%2Ftriggers%2Fmanual%2Frun&sv=1.0&sig=XJ9Fhh2BmefTg_tufIHxyiJGIfu6n8jLRFv7et822X8")
    Observable<PlaceListDao> getPlaceList(@Body LocationDao locationDao);
}
