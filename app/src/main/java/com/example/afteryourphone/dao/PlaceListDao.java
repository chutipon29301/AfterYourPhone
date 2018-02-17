package com.example.afteryourphone.dao;

import com.google.gson.annotations.SerializedName;

/**
 * Created by admin on 17/2/2018 AD.
 */

public class PlaceListDao {
    @SerializedName("next_page_token")      String nextPageToken;
    @SerializedName("results")              PlaceListDetailDao[] results;

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public PlaceListDetailDao[] getResults() {
        return results;
    }

    public void setResults(PlaceListDetailDao[] results) {
        this.results = results;
    }
}
