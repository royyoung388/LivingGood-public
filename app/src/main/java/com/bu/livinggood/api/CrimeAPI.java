package com.bu.livinggood.api;

import com.bu.livinggood.api.service.CrimeService;
import com.bu.livinggood.bean.CrimeData;
import com.bu.livinggood.tools.SerializeTools;

import java.io.File;
import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CrimeAPI {
    private String BASEURL = "https://api.crimeometer.com/v1/incidents/";

    /**
     * @param lon          REQUIRED. Specify a latitude, longitude and radius to search for properties in a particular area.
     * @param lat          REQUIRED. Specify a latitude, longitude and radius to search for properties in a particular area.
     * @param distance     REQUIRED. radio around lat and long coordinates to filter reports, in Miles, Yards, Feet, Kilometers or Meters.
     *                     Example: 10mi, 10yd, 10ft, 10km, 10m respectively.
     * @param datetime_ini REQUIRED. filter results for a specific time period
     * @param datetime_end REQUIRED.
     * @param source       REQUIRED. Specify a source
     * @return
     */
    public void request(
            String lon, String lat, String distance,
            String datetime_ini, String datetime_end, Integer source, Callback<CrimeData> callback) {

        // Initialize API instance
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASEURL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // Create Instance
        CrimeService service = retrofit.create(CrimeService.class);

        // Make request
        Call<CrimeData> call = service.getIncidentsNum(lon, lat, distance, datetime_ini, datetime_end, source
        );
        // synchronous
//        try {
//            Response<CrimeData> response = call.execute();
//            assert response.body() != null;
//            //saveData(response.body());
//            return response.body();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        // Asynchronous
        call.enqueue(callback);
    }

    // save data to file
    private void saveData(CrimeData data) {
        try {
            // save path
            File file = new File("data/property.ser");
            if (file.exists()) {
                // 1. read saved object
                List<CrimeData> saved = (List<CrimeData>) SerializeTools.readFromFile(file);
                // 2. add response to data
                saved.add(data);
                // 3. write to file
                SerializeTools.writeToFile(file, data);
            } else {
                // 1. write to file
                SerializeTools.writeToFile(file, data);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    /*
    public static void main(String[] args) {
        CrimeAPI api = new CrimeAPI();


        CrimeData data = api.request( "-71.1077","42.3498", "1mi",
                "2021-06-01T00:00:00.000Z", "2022-01-01T00:00:00.000Z",1
                );


    }
    */
}
