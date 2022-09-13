package com.bu.livinggood.api.service;

import com.bu.livinggood.bean.Place;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

// An interface that defines parameter needed in the request and to be sent to the server
public interface GmapService {
    @GET("nearbysearch/json")
    Call<Place> getParam (
            @Query(value = "location", encoded = true) String location,
            @Query("radius") Integer radius, @Query("keyword") String keyword,
            @Query("maxprice") Integer maxprice, @Query("minprice") Integer minprice,
            @Query("type") String type, @Query("opennow") Boolean opennow,
            @Query("rankby") String rankby, @Query("key") String key
    );
}
