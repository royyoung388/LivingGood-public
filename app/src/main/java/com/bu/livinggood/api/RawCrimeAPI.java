package com.bu.livinggood.api;

import android.util.Log;

import com.bu.livinggood.api.service.RawCrimeService;
import com.bu.livinggood.bean.RawCrimeData;
import com.bu.livinggood.tools.SerializeTools;

import java.io.File;
import java.io.IOException;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RawCrimeAPI {
    private String BASEURL = "https://api.crimeometer.com/v1/incidents/";

    /**
     * @param lon          REQUIRED. Specify a latitude, longitude and radius to search for properties in a particular area.
     * @param lat          REQUIRED. Specify a latitude, longitude and radius to search for properties in a particular area.
     * @param distance     REQUIRED. filter listings within this specified number of kilometers.
     *                     You must provide either an address or latitude/longitude parameters when using a radius search.
     * @param datetime_ini REQUIRED. filter results for a specific time period
     * @param datetime_end REQUIRED.
     * @return
     */
    public void request(
            String lon, String lat, String distance,
            String datetime_ini, String datetime_end, Callback<RawCrimeData> callback) {

        // Initialize API instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create Instance
        RawCrimeService service = retrofit.create(RawCrimeService.class);

        // Make request
        Call<RawCrimeData> call = service.getIncidentsNum(lon, lat, distance, datetime_ini, datetime_end);
        // synchronous
//        try {
//            Response<CrimeData> response = call.execute();
//            assert response.body() != null;
//            //saveData(response.body());
//            return response.body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        Log.i(RawCrimeData.class.getSimpleName(), "property call: " + call.request().url());
        // Asynchronous
        call.enqueue(callback);
    }

    public static void main(String[] args) {
        RawCrimeAPI api = new RawCrimeAPI();

        api.request("-71.1077", "42.3498", "2mi",
                "2021-06-01T00:00:00.000Z", "2022-01-01T00:00:00.000Z",
                new Callback<RawCrimeData>() {
                    @Override
                    public void onResponse(Call<RawCrimeData> call, Response<RawCrimeData> response) {
                        RawCrimeData rawCrimeData = response.body();
                        try {
                            SerializeTools.writeToFile(new File("data/rawcrime.ser"), rawCrimeData);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<RawCrimeData> call, Throwable t) {
                        t.printStackTrace();
                    }
                }
        );
    }
}
