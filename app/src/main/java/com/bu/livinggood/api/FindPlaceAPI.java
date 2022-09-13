package com.bu.livinggood.api;

import static com.bu.livinggood.controller.APIController.GMAP_KEY;

import com.bu.livinggood.api.service.FindPlaceService;
import com.bu.livinggood.bean.FindPlaceResponse;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FindPlaceAPI {
    private String BASEURL = "https://maps.googleapis.com/maps/api/";

    /**
     *
     * @param input The text string on which to search
     * @param callback
     */
    public void request(String input, Callback<FindPlaceResponse> callback) {
        // Initialize API instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create Instance
        FindPlaceService service = retrofit.create(FindPlaceService.class);

        // Make request
        Call<FindPlaceResponse> call = service.findPlace(input, "textquery", "photo", GMAP_KEY);

        // Asynchronous
        call.enqueue(callback);
    }
}
