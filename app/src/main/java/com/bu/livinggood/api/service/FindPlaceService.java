package com.bu.livinggood.api.service;

import com.bu.livinggood.bean.FindPlaceResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface FindPlaceService {
    @GET("place/findplacefromtext/json")
    Call<FindPlaceResponse> findPlace(@Query("input") String input,
                                      @Query("inputtype") String inputtype,
                                      @Query("fields") String fields,
                                      @Query("key") String key);
}
