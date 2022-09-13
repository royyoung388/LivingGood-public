package com.bu.livinggood.api.service;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import retrofit2.http.Streaming;

public interface PlacePhotoService {
    @Streaming
    @GET("place/photo")
    Call<ResponseBody> getPhoto(@Query("photo_reference") String photoRef,
                                @Query("maxheight") Integer maxHeight,
                                @Query("maxwidth") Integer maxWidth,
                                @Query("key") String key);
}
