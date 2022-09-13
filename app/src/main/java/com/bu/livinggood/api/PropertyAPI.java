package com.bu.livinggood.api;

import android.util.Log;

import com.bu.livinggood.api.service.PropertyService;
import com.bu.livinggood.bean.PropertyResponse;
import com.bu.livinggood.tools.SerializeTools;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class PropertyAPI {

    private String BASEURL = "https://realty-mole-property-api.p.rapidapi.com/";

    /**
     * @param longitude OPTIONAL. Specify a latitude, longitude and radius to search for properties in a particular area.
     * @param latitude  OPTIONAL. Specify a latitude, longitude and radius to search for properties in a particular area.
     * @param radius    OPTIONAL. filter listings within this specified number of kilometers.
     *                  You must provide either an address or latitude/longitude parameters when using a radius search.
     * @param state     OPTIONAL. filter results for a specific state. Use the two character state abbreviation
     * @param city      OPTIONAL. filter results for a specific city
     * @param address   OPTIONAL. pass an optional address for looking up a specific property.
     *                  The address should be formatted as street address, city, state zip (ie 123 Main St, Conway, SC 29526)
     * @param bedrooms  OPTIONAL. filter results for rental listings with a specific number of bedrooms
     * @param bathrooms OPTIONAL. filter results for rental listings with a specific number of bathrooms
     * @param limit     OPTIONAL. maximum number of records to return. must be less than or equal to 50
     * @param offset    OPTIONAL. skip the specified number of properties. use this to page through large sets of properties.
     * @param callback
     * @return
     */
    public void request(
            Double longitude, Double latitude, Double radius, String state, String city, String address,
            Integer bedrooms, Integer bathrooms, Integer limit, Integer offset,
            Callback<List<PropertyResponse>> callback) {
        // Initialize API instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create Instance
        PropertyService service = retrofit.create(PropertyService.class);

        // Make request
        Call<List<PropertyResponse>> call = service.getPropertyList(longitude, latitude, radius,
                state, city, address, bedrooms, bathrooms, limit, offset);
        // synchronous
//        try {
//            Response<List<PropertyResponse>> response = call.execute();
//            assert response.body() != null;
//            saveData(response.body());
//            return response.body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }

        Log.i(PropertyAPI.class.getSimpleName(), "property call: " + call.request().url());
        // Asynchronous
        call.enqueue(callback);
//        call.enqueue(new Callback<List<PropertyResponse>>() {
//            @Override
//            public void onResponse(Call<List<PropertyResponse>> call, Response<List<PropertyResponse>> response) {
//                assert response.body() != null;
//                saveData(response.body());
//            }
//
//            @Override
//            public void onFailure(Call<List<PropertyResponse>> call, Throwable t) {
//                t.printStackTrace();
//            }
//        });
    }

    public static void main(String[] args) throws IOException, ClassNotFoundException {
        PropertyAPI api = new PropertyAPI();

        api.request(-71.1077, 42.3498, 1.60934, null, null,
                null, null, null, null, null,
                new Callback<List<PropertyResponse>>() {
                    @Override
                    public void onResponse(Call<List<PropertyResponse>> call, Response<List<PropertyResponse>> response) {
                        List<PropertyResponse> propertyResponses = response.body();
                        try {
                            SerializeTools.writeToFile(new File("data/property.ser"), propertyResponses);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(Call<List<PropertyResponse>> call, Throwable t) {
                        t.printStackTrace();
                    }
                }
        );

//        File file = new File("data/property.ser");
//        // 1. read saved object
//        List<PropertyResponse> saved = (List<PropertyResponse>) SerializeTools.readFromFile(file);
//        // 2. add response to data
//        List<PropertyResponse> data = saved.subList(0, 16);
//        // 3. write to file
//        SerializeTools.writeToFile(file, data);
    }
}
