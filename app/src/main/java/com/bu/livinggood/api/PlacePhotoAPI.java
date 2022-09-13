package com.bu.livinggood.api;

import static com.bu.livinggood.controller.APIController.GMAP_KEY;

import com.bu.livinggood.api.service.PlacePhotoService;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PlacePhotoAPI {
    private String BASEURL = "https://maps.googleapis.com/maps/api/";

    /**
     *
     * @param photoRef  A string identifier that uniquely identifies a photo
     * @param maxHeight Specifies the maximum desired height, in pixels
     * @param maxWidth  Specifies the maximum desired width, in pixels
     * @param callback
     */
    public void request(String photoRef, Integer maxHeight, Integer maxWidth,
                        Callback<ResponseBody> callback) {
        // Initialize API instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create Instance
        PlacePhotoService service = retrofit.create(PlacePhotoService.class);

        // Make request
        Call<ResponseBody> call = service.getPhoto(photoRef, maxHeight, maxWidth, GMAP_KEY);

        // Asynchronous
        call.enqueue(callback);
    }

}
