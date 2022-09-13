package com.bu.livinggood.controller;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.bu.livinggood.bean.Place;
import com.bu.livinggood.bean.PropertyResponse;
import com.bu.livinggood.bean.RatingSet;
import com.bu.livinggood.bean.RawCrimeData;
import com.bu.livinggood.tools.Tools;
import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class RatingController {

    private static final int RADIUS = 1;

    public static int PREF_FOOD = 1;
    public static int PREF_SECURITY = 2;
    public static int PREF_STORE = 4;
    public static int PREF_TRANSIT = 8;

    private int preference;

    private List<PropertyResponse> propertyResponses;
    private Place food, transit, store;
    private RawCrimeData rawCrimeData;
    private LatLng center;
    private int min_price;
    private int max_price;

    public RatingController(LatLng center, List<PropertyResponse> propertyResponseList,
                            Place food, Place transit, Place store,
                            RawCrimeData rawCrimeData, int min_price, int max_price) {
        this.center = center;
        this.propertyResponses = propertyResponseList;
        this.food = food;
        this.transit = transit;
        this.store = store;
        this.rawCrimeData = rawCrimeData;
        this.min_price = min_price;
        this.max_price = max_price;
    }

    public void setPreference(int preference) {
        this.preference |= preference;
    }

    // Function for determine distance between 2 locations
    private double calcDistance(LatLng center, LatLng location) {
        final int R = 6371; // Radius of the earth
        double lat1 = center.latitude;
        double lon1 = center.longitude;

        double lat2 = location.latitude;
        double lon2 = location.longitude;

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000 / 1609.344; // convert to mile


        distance = Math.pow(distance, 2);

        return Math.sqrt(distance);
    }

    public List<PropertyResponse> getPropertyByRadius(double radius) {
        List<PropertyResponse> propertyInRange = new ArrayList<>();

        // how to use LatLng:
        // LatLng latLng = new LatLng(propertyResponses.get(0).getLatitude(), propertyResponses.get(0).getLongitude());
        int resultSize = propertyResponses.size();
        for (int i = 0; i < resultSize; i++) {
            LatLng latLng = new LatLng(propertyResponses.get(i).getLatitude(), propertyResponses.get(i).getLongitude());
            double distance = calcDistance(center, latLng);
            if (distance < radius) {
                int price = propertyResponses.get(i).getPrice();
                if (price <= max_price && price >= min_price) {
                    propertyInRange.add(propertyResponses.get(i));
                }
            }
        }
        return propertyInRange;
    }

    public Place getFoodByRadius(LatLng apartment, double radius) {
        Place foodInRange = new Place();
        foodInRange.setResults(new ArrayList<>());

        // number of results
        int resultSize = food.getResults().size();
        for (int i = 0; i < resultSize; i++) {
            double lat = food.getResults().get(i).getGeometry().getLocation().getLat();
            double lon = food.getResults().get(i).getGeometry().getLocation().getLng();
            // latitude and longitude
            LatLng foodLoc = new LatLng(lat, lon);
            //calculate the distance
            double distance = calcDistance(apartment, foodLoc);
            if (distance < radius) {
                foodInRange.getResults().add(food.getResults().get(i));
            }
        }
        return foodInRange;
    }

    public Place getTransitByRadius(LatLng apartment, double radius) {
        Place transitInRange = new Place();
        transitInRange.setResults(new ArrayList<>());
        // number of results
        int resultSize = transit.getResults().size();
        for (int i = 0; i < resultSize; i++) {
            double lat = transit.getResults().get(i).getGeometry().getLocation().getLat();
            double lon = transit.getResults().get(i).getGeometry().getLocation().getLng();
            // latitude and longitude
            LatLng transitLoc = new LatLng(lat, lon);
            //calculate the distance
            double distance = calcDistance(apartment, transitLoc);
            if (distance < radius) {
                transitInRange.getResults().add(transit.getResults().get(i));
            }
        }
        return transitInRange;
    }

    public Place getStoreByRadius(LatLng apartment, double radius) {
        Place storeInRange = new Place();
        storeInRange.setResults(new ArrayList<>());
        // number of results
        int resultSize = store.getResults().size();
        for (int i = 0; i < resultSize; i++) {
            double lat = store.getResults().get(i).getGeometry().getLocation().getLat();
            double lon = store.getResults().get(i).getGeometry().getLocation().getLng();
            // latitude and longitude
            LatLng storeLoc = new LatLng(lat, lon);
            //calculate the distance
            double distance = calcDistance(apartment, storeLoc);
            if (distance < radius) {
                storeInRange.getResults().add(store.getResults().get(i));
            }
        }
        return storeInRange;
    }

    public RawCrimeData getRawCrimeDataByRadius(LatLng apartment, double radius) {
        RawCrimeData crimelistInRange = new RawCrimeData();
        crimelistInRange.setIncidents(new ArrayList<>());

        int resultSize = rawCrimeData.getIncidents().size();

        for (int i = 0; i < resultSize; i++) {

            double lat = rawCrimeData.getIncidents().get(i).getIncidentLatitude();
            double lon = rawCrimeData.getIncidents().get(i).getIncidentLongitude();

            LatLng crimeLoc = new LatLng(lat, lon);
            double distance = calcDistance(apartment, crimeLoc);
            if (distance < radius) {
                crimelistInRange.getIncidents().add(rawCrimeData.getIncidents().get(i));
            }
        }
        crimelistInRange.setTotalIncidents(crimelistInRange.getIncidents().size());
        return crimelistInRange;
    }

    /**
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.N)
    public List<RatingSet> getRatingSet(double radius) {
        List<PropertyResponse> propertyInRange = getPropertyByRadius(radius);

        List<RatingSet> ratingSetInRange = new ArrayList<>(propertyInRange.size());
        for (PropertyResponse property : propertyInRange) {
            // Get apartment lat and lon
            LatLng apartmentLoc = new LatLng(property.getLatitude(), property.getLongitude());
            // Calculate all in range security, food, transit, store
            RawCrimeData crimelistInRange = getRawCrimeDataByRadius(apartmentLoc, RADIUS);
            Place foodInRange = getFoodByRadius(apartmentLoc, RADIUS);
            Place transitInRange = getTransitByRadius(apartmentLoc, RADIUS);
            Place storeInRange = getStoreByRadius(apartmentLoc, RADIUS);

            // crime rating
            //double crimeRating = 5 - crimelistInRange.getTotalIncidents() / 1000.;
            double crimeRating = 5 - crimelistInRange.getIncidents().size() / 16.;

            crimeRating = round(crimeRating);
            crimeRating = crimeRating > 5 ? 5.0 : crimeRating;

            // restaurant rating
            double restaurantRating = foodInRange.getResults().size() / 3.0;
            restaurantRating = round(restaurantRating);
            restaurantRating = restaurantRating > 5 ? 5.0 : restaurantRating;

            // station rating
            double stationRating = transitInRange.getResults().size() / 3.0;
            stationRating = round(stationRating);
            stationRating = stationRating > 5 ? 5.0 : stationRating;

            // store rating
            double storeRating = storeInRange.getResults().size() / 3.0;
            storeRating = round(storeRating);
            storeRating = storeRating > 5 ? 5 : storeRating;

            // overall rating
            double overall = Tools.round((crimeRating + restaurantRating + stationRating + storeRating) / 4.0);

            RatingSet ratingSet = new RatingSet(property, crimeRating, restaurantRating, stationRating, storeRating, overall);
            // set number
            ratingSet.setSecurityNum(crimelistInRange.getIncidents().size());
            ratingSet.setFoodNum(foodInRange.getResults().size());
            ratingSet.setTransitNum(transitInRange.getResults().size());
            ratingSet.setStoreNum(storeInRange.getResults().size());
            ratingSetInRange.add(ratingSet);
        }

        Collections.sort(ratingSetInRange, new ratingComparator().reversed());
        return ratingSetInRange;
    }

    public static double round(double value) {
        return (double) Math.round(value * 100) / 100;
    }

    private class ratingComparator implements Comparator<RatingSet> {
        @Override
        public int compare(RatingSet r1, RatingSet r2) {
            // security, transit, food, store
            if ((preference & PREF_SECURITY) == PREF_SECURITY &&
                    r1.getSecurity_score() != r2.getSecurity_score()) {
                return Double.compare(r1.getSecurity_score(), r2.getSecurity_score());
            }

            if ((preference & PREF_TRANSIT) == PREF_TRANSIT &&
                    r1.getTransportation_score() != r2.getTransportation_score()) {
                return Double.compare(r1.getTransportation_score(), r2.getTransportation_score());
            }

            if ((preference & PREF_FOOD) == PREF_FOOD &&
                    r1.getFoods_score() != r2.getFoods_score()) {
                return Double.compare(r1.getFoods_score(), r2.getFoods_score());
            }

            if ((preference & PREF_STORE) == PREF_STORE &&
                    r1.getStores_score() != r2.getStores_score()) {
                return Double.compare(r1.getStores_score(), r2.getStores_score());
            }

            return Double.compare(r1.getOverallScore(), r2.getOverallScore());
        }
    }
}