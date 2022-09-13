package com.bu.livinggood.api.service;

import com.bu.livinggood.BuildConfig;
import com.bu.livinggood.bean.PropertyResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Query;

public interface PropertyService {

    @Headers({
            "X-RapidAPI-Host: realty-mole-property-api.p.rapidapi.com",
            "X-RapidAPI-Key: " + BuildConfig.PROPERTY_API_KEY
    })
    @GET("rentalListings")
    Call<List<PropertyResponse>> getPropertyList(
            @Query("longitude") Double longitude, @Query("latitude") Double latitude,
            @Query("radius") Double radius, @Query("state") String state,
            @Query("city") String city, @Query("address") String address,
            @Query("bedrooms") Integer bedrooms, @Query("bathrooms") Integer bathrooms,
            @Query("limit") Integer limit, @Query("offset") Integer offset);
}
