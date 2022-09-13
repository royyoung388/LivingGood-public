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

// A class that parses and holds JSON objects returned by http response
public class Place implements Serializable {

    @SerializedName("html_attributions")
    private List<?> htmlAttributions;
    @SerializedName("next_page_token")
    private String nextPageToken;
    @SerializedName("results")
    private List<ResultsDTO> results;
    @SerializedName("status")
    private String status;

    public static Place objectFromData(String str) {

        return new Gson().fromJson(str, Place.class);
    }

    public static Place objectFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);

            return new Gson().fromJson(jsonObject.getString(str), Place.class);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static List<Place> arraybeanFromData(String str) {

        Type listType = new TypeToken<ArrayList<Place>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }

    public static List<Place> arraybeanFromData(String str, String key) {

        try {
            JSONObject jsonObject = new JSONObject(str);
            Type listType = new TypeToken<ArrayList<Place>>() {
            }.getType();

            return new Gson().fromJson(jsonObject.getString(str), listType);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return new ArrayList();


    }

    public List<?> getHtmlAttributions() {
        return htmlAttributions;
    }

    public void setHtmlAttributions(List<?> htmlAttributions) {
        this.htmlAttributions = htmlAttributions;
    }

    public String getNextPageToken() {
        return nextPageToken;
    }

    public void setNextPageToken(String nextPageToken) {
        this.nextPageToken = nextPageToken;
    }

    public List<ResultsDTO> getResults() {
        return results;
    }

    public void setResults(List<ResultsDTO> results) {
        this.results = results;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public static class ResultsDTO implements Serializable {
        @SerializedName("business_status")
        private String businessStatus;
        @SerializedName("geometry")
        private GeometryDTO geometry;
        @SerializedName("icon")
        private String icon;
        @SerializedName("icon_background_color")
        private String iconBackgroundColor;
        @SerializedName("icon_mask_base_uri")
        private String iconMaskBaseUri;
        @SerializedName("name")
        private String name;
        @SerializedName("opening_hours")
        private OpeningHoursDTO openingHours;
        @SerializedName("photos")
        private List<PhotosDTO> photos;
        @SerializedName("place_id")
        private String placeId;
        @SerializedName("plus_code")
        private PlusCodeDTO plusCode;
        @SerializedName("price_level")
        private Integer priceLevel;
        @SerializedName("rating")
        private Double rating;
        @SerializedName("reference")
        private String reference;
        @SerializedName("scope")
        private String scope;
        @SerializedName("types")
        private List<String> types;
        @SerializedName("user_ratings_total")
        private Integer userRatingsTotal;
        @SerializedName("vicinity")
        private String vicinity;
        @SerializedName("permanently_closed")
        private Boolean permanentlyClosed;

        public static ResultsDTO objectFromData(String str) {

            return new Gson().fromJson(str, ResultsDTO.class);
        }

        public static ResultsDTO objectFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);

                return new Gson().fromJson(jsonObject.getString(str), ResultsDTO.class);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return null;
        }

        public static List<ResultsDTO> arrayResultsDTOFromData(String str) {

            Type listType = new TypeToken<ArrayList<ResultsDTO>>() {
            }.getType();

            return new Gson().fromJson(str, listType);
        }

        public static List<ResultsDTO> arrayResultsDTOFromData(String str, String key) {

            try {
                JSONObject jsonObject = new JSONObject(str);
                Type listType = new TypeToken<ArrayList<ResultsDTO>>() {
                }.getType();

                return new Gson().fromJson(jsonObject.getString(str), listType);

            } catch (JSONException e) {
                e.printStackTrace();
            }

            return new ArrayList();


        }

        public String getBusinessStatus() {
            return businessStatus;
        }

        public void setBusinessStatus(String businessStatus) {
            this.businessStatus = businessStatus;
        }

        public GeometryDTO getGeometry() {
            return geometry;
        }

        public void setGeometry(GeometryDTO geometry) {
            this.geometry = geometry;
        }

        public String getIcon() {
            return icon;
        }

        public void setIcon(String icon) {
            this.icon = icon;
        }

        public String getIconBackgroundColor() {
            return iconBackgroundColor;
        }

        public void setIconBackgroundColor(String iconBackgroundColor) {
            this.iconBackgroundColor = iconBackgroundColor;
        }

        public String getIconMaskBaseUri() {
            return iconMaskBaseUri;
        }

        public void setIconMaskBaseUri(String iconMaskBaseUri) {
            this.iconMaskBaseUri = iconMaskBaseUri;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public OpeningHoursDTO getOpeningHours() {
            return openingHours;
        }

        public void setOpeningHours(OpeningHoursDTO openingHours) {
            this.openingHours = openingHours;
        }

        public List<PhotosDTO> getPhotos() {
            return photos;
        }

        public void setPhotos(List<PhotosDTO> photos) {
            this.photos = photos;
        }

        public String getPlaceId() {
            return placeId;
        }

        public void setPlaceId(String placeId) {
            this.placeId = placeId;
        }

        public PlusCodeDTO getPlusCode() {
            return plusCode;
        }

        public void setPlusCode(PlusCodeDTO plusCode) {
            this.plusCode = plusCode;
        }

        public Integer getPriceLevel() {
            return priceLevel;
        }

        public void setPriceLevel(Integer priceLevel) {
            this.priceLevel = priceLevel;
        }

        public Double getRating() {
            return rating;
        }

        public void setRating(Double rating) {
            this.rating = rating;
        }

        public String getReference() {
            return reference;
        }

        public void setReference(String reference) {
            this.reference = reference;
        }

        public String getScope() {
            return scope;
        }

        public void setScope(String scope) {
            this.scope = scope;
        }

        public List<String> getTypes() {
            return types;
        }

        public void setTypes(List<String> types) {
            this.types = types;
        }

        public Integer getUserRatingsTotal() {
            return userRatingsTotal;
        }

        public void setUserRatingsTotal(Integer userRatingsTotal) {
            this.userRatingsTotal = userRatingsTotal;
        }

        public String getVicinity() {
            return vicinity;
        }

        public void setVicinity(String vicinity) {
            this.vicinity = vicinity;
        }

        public Boolean getPermanentlyClosed() {
            return permanentlyClosed;
        }

        public void setPermanentlyClosed(Boolean permanentlyClosed) {
            this.permanentlyClosed = permanentlyClosed;
        }

        public static class GeometryDTO  implements Serializable{
            @SerializedName("location")
            private LocationDTO location;
            @SerializedName("viewport")
            private ViewportDTO viewport;

            public static GeometryDTO objectFromData(String str) {

                return new Gson().fromJson(str, GeometryDTO.class);
            }

            public static GeometryDTO objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), GeometryDTO.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<GeometryDTO> arrayGeometryDTOFromData(String str) {

                Type listType = new TypeToken<ArrayList<GeometryDTO>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<GeometryDTO> arrayGeometryDTOFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<GeometryDTO>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public LocationDTO getLocation() {
                return location;
            }

            public void setLocation(LocationDTO location) {
                this.location = location;
            }

            public ViewportDTO getViewport() {
                return viewport;
            }

            public void setViewport(ViewportDTO viewport) {
                this.viewport = viewport;
            }

            public static class LocationDTO implements Serializable{
                @SerializedName("lat")
                private Double lat;
                @SerializedName("lng")
                private Double lng;

                public static LocationDTO objectFromData(String str) {

                    return new Gson().fromJson(str, LocationDTO.class);
                }

                public static LocationDTO objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), LocationDTO.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<LocationDTO> arrayLocationDTOFromData(String str) {

                    Type listType = new TypeToken<ArrayList<LocationDTO>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<LocationDTO> arrayLocationDTOFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<LocationDTO>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }

                public Double getLat() {
                    return lat;
                }

                public void setLat(Double lat) {
                    this.lat = lat;
                }

                public Double getLng() {
                    return lng;
                }

                public void setLng(Double lng) {
                    this.lng = lng;
                }
            }

            public static class ViewportDTO implements Serializable{
                @SerializedName("northeast")
                private NortheastDTO northeast;
                @SerializedName("southwest")
                private SouthwestDTO southwest;

                public static ViewportDTO objectFromData(String str) {

                    return new Gson().fromJson(str, ViewportDTO.class);
                }

                public static ViewportDTO objectFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);

                        return new Gson().fromJson(jsonObject.getString(str), ViewportDTO.class);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return null;
                }

                public static List<ViewportDTO> arrayViewportDTOFromData(String str) {

                    Type listType = new TypeToken<ArrayList<ViewportDTO>>() {
                    }.getType();

                    return new Gson().fromJson(str, listType);
                }

                public static List<ViewportDTO> arrayViewportDTOFromData(String str, String key) {

                    try {
                        JSONObject jsonObject = new JSONObject(str);
                        Type listType = new TypeToken<ArrayList<ViewportDTO>>() {
                        }.getType();

                        return new Gson().fromJson(jsonObject.getString(str), listType);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    return new ArrayList();


                }

                public NortheastDTO getNortheast() {
                    return northeast;
                }

                public void setNortheast(NortheastDTO northeast) {
                    this.northeast = northeast;
                }

                public SouthwestDTO getSouthwest() {
                    return southwest;
                }

                public void setSouthwest(SouthwestDTO southwest) {
                    this.southwest = southwest;
                }

                public static class NortheastDTO implements Serializable {
                    @SerializedName("lat")
                    private Double lat;
                    @SerializedName("lng")
                    private Double lng;

                    public static NortheastDTO objectFromData(String str) {

                        return new Gson().fromJson(str, NortheastDTO.class);
                    }

                    public static NortheastDTO objectFromData(String str, String key) {

                        try {
                            JSONObject jsonObject = new JSONObject(str);

                            return new Gson().fromJson(jsonObject.getString(str), NortheastDTO.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    public static List<NortheastDTO> arrayNortheastDTOFromData(String str) {

                        Type listType = new TypeToken<ArrayList<NortheastDTO>>() {
                        }.getType();

                        return new Gson().fromJson(str, listType);
                    }

                    public static List<NortheastDTO> arrayNortheastDTOFromData(String str, String key) {

                        try {
                            JSONObject jsonObject = new JSONObject(str);
                            Type listType = new TypeToken<ArrayList<NortheastDTO>>() {
                            }.getType();

                            return new Gson().fromJson(jsonObject.getString(str), listType);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return new ArrayList();


                    }

                    public Double getLat() {
                        return lat;
                    }

                    public void setLat(Double lat) {
                        this.lat = lat;
                    }

                    public Double getLng() {
                        return lng;
                    }

                    public void setLng(Double lng) {
                        this.lng = lng;
                    }
                }

                public static class SouthwestDTO implements Serializable {
                    @SerializedName("lat")
                    private Double lat;
                    @SerializedName("lng")
                    private Double lng;

                    public static SouthwestDTO objectFromData(String str) {

                        return new Gson().fromJson(str, SouthwestDTO.class);
                    }

                    public static SouthwestDTO objectFromData(String str, String key) {

                        try {
                            JSONObject jsonObject = new JSONObject(str);

                            return new Gson().fromJson(jsonObject.getString(str), SouthwestDTO.class);
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return null;
                    }

                    public static List<SouthwestDTO> arraySouthwestDTOFromData(String str) {

                        Type listType = new TypeToken<ArrayList<SouthwestDTO>>() {
                        }.getType();

                        return new Gson().fromJson(str, listType);
                    }

                    public static List<SouthwestDTO> arraySouthwestDTOFromData(String str, String key) {

                        try {
                            JSONObject jsonObject = new JSONObject(str);
                            Type listType = new TypeToken<ArrayList<SouthwestDTO>>() {
                            }.getType();

                            return new Gson().fromJson(jsonObject.getString(str), listType);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        return new ArrayList();


                    }

                    public Double getLat() {
                        return lat;
                    }

                    public void setLat(Double lat) {
                        this.lat = lat;
                    }

                    public Double getLng() {
                        return lng;
                    }

                    public void setLng(Double lng) {
                        this.lng = lng;
                    }
                }
            }
        }

        public static class OpeningHoursDTO implements Serializable{
            @SerializedName("open_now")
            private Boolean openNow;

            public static OpeningHoursDTO objectFromData(String str) {

                return new Gson().fromJson(str, OpeningHoursDTO.class);
            }

            public static OpeningHoursDTO objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), OpeningHoursDTO.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<OpeningHoursDTO> arrayOpeningHoursDTOFromData(String str) {

                Type listType = new TypeToken<ArrayList<OpeningHoursDTO>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<OpeningHoursDTO> arrayOpeningHoursDTOFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<OpeningHoursDTO>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public Boolean getOpenNow() {
                return openNow;
            }

            public void setOpenNow(Boolean openNow) {
                this.openNow = openNow;
            }
        }

        public static class PlusCodeDTO implements Serializable{
            @SerializedName("compound_code")
            private String compoundCode;
            @SerializedName("global_code")
            private String globalCode;

            public static PlusCodeDTO objectFromData(String str) {

                return new Gson().fromJson(str, PlusCodeDTO.class);
            }

            public static PlusCodeDTO objectFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);

                    return new Gson().fromJson(jsonObject.getString(str), PlusCodeDTO.class);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return null;
            }

            public static List<PlusCodeDTO> arrayPlusCodeDTOFromData(String str) {

                Type listType = new TypeToken<ArrayList<PlusCodeDTO>>() {
                }.getType();

                return new Gson().fromJson(str, listType);
            }

            public static List<PlusCodeDTO> arrayPlusCodeDTOFromData(String str, String key) {

                try {
                    JSONObject jsonObject = new JSONObject(str);
                    Type listType = new TypeToken<ArrayList<PlusCodeDTO>>() {
                    }.getType();

                    return new Gson().fromJson(jsonObject.getString(str), listType);

                } catch (JSONException e) {
                    e.printStackTrace();
                }

                return new ArrayList();


            }

            public String getCompoundCode() {
                return compoundCode;
            }

            public void setCompoundCode(String compoundCode) {
                this.compoundCode = compoundCode;
            }

            public String getGlobalCode() {
                return globalCode;
            }

            public void setGlobalCode(String globalCode) {
                this.globalCode = globalCode;
            }
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

    // returns only the name of the places, can change to any other types of information as needed
    @Override
    public String toString() {

        int index = 1;
        String result = results.get(0).getName().toString();

        while (index < results.size()) {
            result += ", " + results.get(index).getName().toString();
            index++;
        }

        return result + "\n\nTotal Number: " + index;
    }
}
