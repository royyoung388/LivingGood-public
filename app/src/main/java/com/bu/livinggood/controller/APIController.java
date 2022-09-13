package com.bu.livinggood.controller;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.bu.livinggood.BuildConfig;
import com.bu.livinggood.api.CrimeAPI;
import com.bu.livinggood.api.FindPlaceAPI;
import com.bu.livinggood.api.GmapAPI;
import com.bu.livinggood.api.PlacePhotoAPI;
import com.bu.livinggood.api.PropertyAPI;
import com.bu.livinggood.api.RawCrimeAPI;
import com.bu.livinggood.bean.CrimeData;
import com.bu.livinggood.bean.FindPlaceResponse;
import com.bu.livinggood.bean.Place;
import com.bu.livinggood.bean.PropertyResponse;
import com.bu.livinggood.bean.RawCrimeData;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class APIController {
    private static final String TAG = APIController.class.getSimpleName();

    public static final String GMAP_KEY = BuildConfig.MAPS_API_KEY;
    public static final int MAX = 5;

    private double radius;

    public double getRadius() {
        return radius;
    }

    public void setRadius(double radius) {
        this.radius = radius;
    }

    /**
     * request Place API to get Photo reference
     *
     * @param input
     * @param callback
     */
    public void requestFindPlace(String input, Callback<FindPlaceResponse> callback) {
        FindPlaceAPI findPlaceAPI = new FindPlaceAPI();
        findPlaceAPI.request(input, callback);
    }

    /**
     * get photo by photo reference
     *
     * @param photoRef
     * @param maxHeight
     * @param maxWidth
     * @param callback
     */
    public void requestPhoto(String photoRef, Integer maxHeight, Integer maxWidth,
                             Callback<ResponseBody> callback) {
        PlacePhotoAPI placePhotoAPI = new PlacePhotoAPI();
        placePhotoAPI.request(photoRef, maxHeight, maxWidth, callback);
    }

    /**
     * send property API to get apartment data
     *
     * @param longitude
     * @param latitude
     * @param bedroom
     * @param bathroom
     * @param callback
     */
    public void requestProperty(double longitude, double latitude, Integer bedroom, Integer bathroom, Callback<List<PropertyResponse>> callback) {
        requestProperty(longitude, latitude, bedroom, bathroom, 0, new ArrayList<>(), callback);
    }

    /**
     * send multiple property API until no more apartment data
     *
     * @param longitude
     * @param latitude
     * @param bedroom
     * @param bathroom
     * @param counter
     * @param result
     * @param callback
     */
    private void requestProperty(double longitude, double latitude,
                                 Integer bedroom, Integer bathroom,
                                 int counter,
                                 List<PropertyResponse> result,
                                 Callback<List<PropertyResponse>> callback) {
        // 1. check maximum counter
        if (counter >= MAX) {
            Log.i(TAG, "property counter: " + counter);
            callback.onResponse(null, Response.success(result));
            return;
        }

        // 2. construct API request
        PropertyAPI propertyAPI = new PropertyAPI();
        // 3. send API request with offset = result.size()
        propertyAPI.request(longitude, latitude, radius * 1.60934,
                null, null, null, bedroom, bathroom, null, result.size(),
                new Callback<List<PropertyResponse>>() {
                    @Override
                    public void onResponse(Call<List<PropertyResponse>> call, Response<List<PropertyResponse>> response) {
                        // 4. check length of response data. Finish when length = 0
                        if (response.body() == null || response.body().size() == 0) {
                            Log.i(TAG, "property response: " + response.body().size());
                            callback.onResponse(call, Response.success(result));
                            return;
                        }

                        // 5. Append data to result
                        result.addAll(response.body());
                        Log.i(TAG, "property result size: " + result.size());
                        // 6. The maximum length of response is 50.
                        // Therefore, when result.size() % 50 != 0, there is no more data
                        if (result.size() % 50 != 0) {
                            Log.i(TAG, "property result size not 50x: " + response.body().size());
                            callback.onResponse(call, Response.success(result));
                            return;
                        } else
                            // 7. resend request to get more data
                            requestProperty(longitude, latitude, bedroom, bathroom, counter + 1,
                                    result, callback);
                    }

                    @Override
                    public void onFailure(Call<List<PropertyResponse>> call, Throwable t) {
                        callback.onFailure(call, t);
                    }
                });
    }

    /**
     * get crime data
     *
     * @param longitude
     * @param latitude
     * @param callback
     */
    @Deprecated
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void requestCrimeData(double longitude, double latitude, Callback<CrimeData> callback) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(12);
        CrimeAPI crimeAPI = new CrimeAPI();
        crimeAPI.request(String.valueOf(longitude), String.valueOf(latitude),
                String.format(Locale.US, "%.1fmi", radius + 1),
                startDate.toString() + "T00:00:00.000Z",
                endDate + "T00:00:00.000Z",
                1, callback);
    }

    /**
     * get raw crime data
     *
     * @param longitude
     * @param latitude
     * @param callback
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    public void requestRawCrimeData(double longitude, double latitude, Callback<RawCrimeData
            > callback) {
        LocalDate endDate = LocalDate.now();
        LocalDate startDate = endDate.minusMonths(12);
        RawCrimeAPI crimeAPI = new RawCrimeAPI();
        crimeAPI.request(String.valueOf(longitude), String.valueOf(latitude),
                String.format(Locale.US, "%.1fmi", radius + 1),
                startDate.toString() + "T00:00:00.000Z",
                endDate + "T00:00:00.000Z",
                callback);
    }

    /**
     * get restaurant data
     *
     * @param longitude
     * @param latitude
     * @param callback
     */
    public void requestRestaurant(double longitude, double latitude, Callback<Place> callback) {
        GmapAPI gmapAPI = new GmapAPI();
        String location = String.format(Locale.US, "%f, %f", latitude, longitude);
        //"restaurant" for Restaurants, "bus_station" for Transportation, "supermarket" for Store
        gmapAPI.request(location, (int) (radius * 1609.34 + 1609.34), null,
                null, null, "restaurant", null, null,
                GMAP_KEY, callback);
    }

    /**
     * get transportation data
     *
     * @param longitude
     * @param latitude
     * @param callback
     */
    public void requestTransportation(double longitude, double latitude, Callback<Place> callback) {
        GmapAPI gmapAPI = new GmapAPI();
        String location = String.format(Locale.US, "%f, %f", latitude, longitude);
        //"restaurant" for Restaurants, "bus_station" for Transportation, "supermarket" for Store
        gmapAPI.request(location, (int) (radius * 1609.34 + 1609.34), null,
                null, null, "bus_station", null, null,
                GMAP_KEY, callback);
    }

    /**
     * get store data
     *
     * @param longitude
     * @param latitude
     * @param callback
     */
    public void requestStore(double longitude, double latitude, Callback<Place> callback) {
        GmapAPI gmapAPI = new GmapAPI();
        String location = String.format(Locale.US, "%f, %f", latitude, longitude);
        //"restaurant" for Restaurants, "bus_station" for Transportation, "supermarket" for Store
        gmapAPI.request(location, (int) (radius * 1609.34 + 1609.34), null,
                null, null, "supermarket", null, null,
                GMAP_KEY, callback);
    }
}
