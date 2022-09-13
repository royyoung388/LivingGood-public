package com.bu.livinggood.bean;

import com.google.gson.Gson;
import com.google.gson.annotations.SerializedName;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class FindPlaceResponse implements Serializable {

    @SerializedName("candidates")
    private List<CandidatesDTO> candidates;
    @SerializedName("status")
    private String status;

    public static FindPlaceResponse objectFromData(String str) {

        return new Gson().fromJson(str, FindPlaceResponse.class);
    }

    public static FindPlaceResponse objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), FindPlaceResponse.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<FindPlaceResponse> arrayFindPlaceResponseFromData(String str) {

        Type listType = new TypeToken<ArrayList<FindPlaceResponse>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<FindPlaceResponse> arrayFindPlaceResponseFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<FindPlaceResponse>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public List<CandidatesDTO> getCandidates() {
        return candidates;
    }

    public void setCandidates(List<CandidatesDTO> candidates) {
        this.candidates = candidates;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class CandidatesDTO implements Serializable {
        @SerializedName("photos")
        private List<PhotosDTO> photos;

        public static CandidatesDTO objectFromData(String str) {

            return new Gson().fromJson(str, CandidatesDTO.class);
        }

        public static CandidatesDTO objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), CandidatesDTO.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<CandidatesDTO> arrayCandidatesDTOFromData(String str) {

            Type listType = new TypeToken<ArrayList<CandidatesDTO>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<CandidatesDTO> arrayCandidatesDTOFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<CandidatesDTO>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public List<PhotosDTO> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosDTO> photos) {
            this.photos = photos;
        }

        public static class PhotosDTO implements Serializable {
            @SerializedName("height")
            private Integer height;
            @SerializedName("html_attributions")
            private List<String> htmlAttributions;
            @SerializedName("photo_reference")
            private String photoReference;
            @SerializedName("width")
            private Integer width;

            public static PhotosDTO objectFromData(String str) {

                return new Gson().fromJson(str, PhotosDTO.class);
            }

            public static PhotosDTO objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), PhotosDTO.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<PhotosDTO> arrayPhotosDTOFromData(String str) {

                Type listType = new TypeToken<ArrayList<PhotosDTO>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<PhotosDTO> arrayPhotosDTOFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<PhotosDTO>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public Integer getHeight() {
                return height;
            }

            public void setHeight(Integer height) {
                this.height = height;
            }

            public List<String> getHtmlAttributions() {
                return htmlAttributions;
            }

            public void setHtmlAttributions(List<String> htmlAttributions) {
                this.htmlAttributions = htmlAttributions;
            }

            public String getPhotoReference() {
                return photoReference;
            }

            public void setPhotoReference(String photoReference) {
                this.photoReference = photoReference;
            }

            public Integer getWidth() {
                return width;
            }

            public void setWidth(Integer width) {
                this.width = width;
            }
        }
    }
}
