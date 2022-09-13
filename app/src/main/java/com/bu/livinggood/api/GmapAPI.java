package com.bu.livinggood.api;

import com.bu.livinggood.api.service.GmapService;
import com.bu.livinggood.bean.Place;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// A class that defines how the request is sent


public class GmapAPI {
    private String BaseURL = "https://maps.googleapis.com/maps/api/place/";

    /**
     * @param location REQUIRED. Specify a location in format of "latitude, longitude"
     * @param radius   Optional. Defines the distance (in meters). Specify a radius to search for places around a specific location(Recommend 500 to 2000)
     * @param keyword  Optional. Specify places that contain this word in their names
     * @param maxprice Optional. Specify price range. Valid values range between 0 (most affordable) to 4 (most expensive)
     * @param minprice Optional. Specify price range. Valid values range between 0 (most affordable) to 4 (most expensive)
     * @param type     Optional. Specify the type of places returned
     *                 Recommend Values: "restaurant" for Restaurants, "bus_station" for Transportation, "supermarket" for Store
     *                 Some other good options: convenience_store, light_rail_station, hospital, school, university, movie_theater, museum
     * @param opennow  Optional. Specify if a place is open now
     * @param rankby   Optional. Rank return places by either prominence (default, based on their importance)
     *                 or distance(in ascending order by their distance from the specified location)
     * @param key      REQUIRED. API key: "AIzaSyAhD8wNcegW74ZRQdnWjMIsaY3I8mu6qlU"
     * @param callback REQUIRED. callback method
     * @return
     */

    // 1. request method that takes filter conditions as input and send the request to Gmap Places to get results
    public void request(String location,
                        Integer radius, String keyword,
                        Integer maxprice, Integer minprice,
                        String type, Boolean opennow,
                        String rankby, String key, Callback<Place> callback) {
        // Initialize API instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BaseURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create Instance
        GmapService service = retrofit.create(GmapService.class);

        // Make request
        Call<Place> call = service.getParam(location, radius,
                keyword, maxprice, minprice, type, opennow, rankby, key);

        // Asynchronous
        call.enqueue(callback);
    }
}
