package com.bu.livinggood.api.service;

import com.bu.livinggood.BuildConfig;
import com.bu.livinggood.bean.RawCrimeData;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface RawCrimeService {
    @Headers({
            "Content-Type: application/json",
            "x-api-key: " + BuildConfig.CRIME_API_KEY
    })
    @GET("raw-data")
    Call<RawCrimeData> getIncidentsNum(
            @Query("lon") String lon, @Query("lat") String lat,
            @Query("distance") String radius, @Query(value = "datetime_ini", encoded = true) String datetime_ini,
            @Query(value = "datetime_end", encoded = true) String datetime_end);
}
