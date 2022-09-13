package com.bu.livinggood.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class PropertyResponse implements Serializable {
    @SerializedName("bedrooms")
    private Integer bedrooms;
    @SerializedName("price")
    private Integer price;
    @SerializedName("rawAddress")
    private String rawAddress;
    @SerializedName("county")
    private String county;
    @SerializedName("addressLine1")
    private String addressLine1;
    @SerializedName("city")
    private String city;
    @SerializedName("state")
    private String state;
    @SerializedName("zipCode")
    private String zipCode;
    @SerializedName("formattedAddress")
    private String formattedAddress;
    @SerializedName("lastSeen")
    private String lastSeen;
    @SerializedName("listedDate")
    private String listedDate;
    @SerializedName("status")
    private String status;
    @SerializedName("removedDate")
    private Object removedDate;
    @SerializedName("daysOnMarket")
    private Integer daysOnMarket;
    @SerializedName("createdDate")
    private String createdDate;
    @SerializedName("id")
    private String id;
    @SerializedName("latitude")
    private Double latitude;
    @SerializedName("longitude")
    private Double longitude;
    @SerializedName("propertyType")
    private String propertyType;
    @SerializedName("addressLine2")
    private String addressLine2;
    @SerializedName("squareFootage")
    private Integer squareFootage;
    @SerializedName("bathrooms")
    private Double bathrooms;

    public static List<PropertyResponse> arrayPropertyRequestFromData(String str) {

        Type listType = new TypeToken<ArrayList<PropertyResponse>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }


    public Integer getBedrooms() {
        return bedrooms;
    }

    public void setBedrooms(Integer bedrooms) {
        this.bedrooms = bedrooms;
    }

    public Integer getPrice() {
        return price;
    }

    public void setPrice(Integer price) {
        this.price = price;
    }

    public String getRawAddress() {
        return rawAddress;
    }

    public void setRawAddress(String rawAddress) {
        this.rawAddress = rawAddress;
    }

    public String getCounty() {
        return county;
    }

    public void setCounty(String county) {
        this.county = county;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getFormattedAddress() {
        return formattedAddress;
    }

    public void setFormattedAddress(String formattedAddress) {
        this.formattedAddress = formattedAddress;
    }

    public String getLastSeen() {
        return lastSeen;
    }

    public void setLastSeen(String lastSeen) {
        this.lastSeen = lastSeen;
    }

    public String getListedDate() {
        return listedDate;
    }

    public void setListedDate(String listedDate) {
        this.listedDate = listedDate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getRemovedDate() {
        return removedDate;
    }

    public void setRemovedDate(Object removedDate) {
        this.removedDate = removedDate;
    }

    public Integer getDaysOnMarket() {
        return daysOnMarket;
    }

    public void setDaysOnMarket(Integer daysOnMarket) {
        this.daysOnMarket = daysOnMarket;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public String getPropertyType() {
        return propertyType;
    }

    public void setPropertyType(String propertyType) {
        this.propertyType = propertyType;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public Integer getSquareFootage() {
        return squareFootage;
    }

    public void setSquareFootage(Integer squareFootage) {
        this.squareFootage = squareFootage;
    }

    public Double getBathrooms() {
        return bathrooms;
    }

    public void setBathrooms(Double bathrooms) {
        this.bathrooms = bathrooms;
    }

    @Override
    public String toString() {
        return "PropertyResponse{" +
                "bedrooms=" + bedrooms +
                ", price=" + price +
                ", rawAddress='" + rawAddress + '\'' +
                ", addressLine1='" + addressLine1 + '\'' +
                ", city='" + city + '\'' +
                ", state='" + state + '\'' +
                ", latitude=" + latitude +
                ", longitude=" + longitude +
                ", propertyType='" + propertyType + '\'' +
                ", addressLine2='" + addressLine2 + '\'' +
                ", bathrooms=" + bathrooms +
                "}\n";
    }
}
