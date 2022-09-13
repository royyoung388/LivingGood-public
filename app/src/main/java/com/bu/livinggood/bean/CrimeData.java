package com.bu.livinggood.bean;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

// data class for general crime description
public class CrimeData implements Serializable {

    @SerializedName("total_incidents")
    @Expose
    private Integer totalIncidents;
    @SerializedName("report_types")
    @Expose
    private List<ReportType> reportTypes = null;
    @SerializedName("total_csi")
    @Expose
    private String totalCsi;
//    @SerializedName("total_pages")
//    private Integer totalPages;
//    @SerializedName("incidents")
//    private List<IncidentsDTO> incidents;

    public Integer getTotalIncidents() {
        return totalIncidents;
    }

    public void setTotalIncidents(Integer totalIncidents) {
        this.totalIncidents = totalIncidents;
    }

    public CrimeData withTotalIncidents(Integer totalIncidents) {
        this.totalIncidents = totalIncidents;
        return this;
    }

    /*public static List<CrimeData> arrayPropertyRequestFromData(String str) {

        Type listType = new TypeToken<ArrayList<CrimeData>>() {
        }.getType();

        return new Gson().fromJson(str, listType);
    }
     */

    public List<ReportType> getReportTypes() {
        return reportTypes;
    }

    public void setReportTypes(List<ReportType> reportTypes) {
        this.reportTypes = reportTypes;
    }

    public CrimeData withReportTypes(List<ReportType> reportTypes) {
        this.reportTypes = reportTypes;
        return this;
    }

    public String getTotalCsi() {
        return totalCsi;
    }

    public void setTotalCsi(String totalCsi) {
        this.totalCsi = totalCsi;
    }

    public CrimeData withTotalCsi(String totalCsi) {
        this.totalCsi = totalCsi;
        return this;
    }

//    public Integer getTotalPages() {
//        return totalPages;
//    }
//
//    public void setTotalPages(Integer totalPages) {
//        this.totalPages = totalPages;
//    }
//
//    public List<IncidentsDTO> getIncidents() {
//        return incidents;
//    }
//
//    public void setIncidents(List<IncidentsDTO> incidents) {
//        this.incidents = incidents;
//    }
//
//    public static class IncidentsDTO {
//        @SerializedName("city_key")
//        private String cityKey;
//        @SerializedName("incident_code")
//        private String incidentCode;
//        @SerializedName("incident_date")
//        private String incidentDate;
//        @SerializedName("incident_type")
//        private String incidentType;
//        @SerializedName("incident_official_type")
//        private String incidentOfficialType;
//        @SerializedName("incident_source_name")
//        private String incidentSourceName;
//        @SerializedName("incident_description")
//        private String incidentDescription;
//        @SerializedName("incident_latitude")
//        private Double incidentLatitude;
//        @SerializedName("incident_longitude")
//        private Double incidentLongitude;
//        @SerializedName("incident_address")
//        private String incidentAddress;
//
//        public String getCityKey() {
//            return cityKey;
//        }
//
//        public void setCityKey(String cityKey) {
//            this.cityKey = cityKey;
//        }
//
//        public String getIncidentCode() {
//            return incidentCode;
//        }
//
//        public void setIncidentCode(String incidentCode) {
//            this.incidentCode = incidentCode;
//        }
//
//        public String getIncidentDate() {
//            return incidentDate;
//        }
//
//        public void setIncidentDate(String incidentDate) {
//            this.incidentDate = incidentDate;
//        }
//
//        public String getIncidentType() {
//            return incidentType;
//        }
//
//        public void setIncidentType(String incidentType) {
//            this.incidentType = incidentType;
//        }
//
//        public String getIncidentOfficialType() {
//            return incidentOfficialType;
//        }
//
//        public void setIncidentOfficialType(String incidentOfficialType) {
//            this.incidentOfficialType = incidentOfficialType;
//        }
//
//        public String getIncidentSourceName() {
//            return incidentSourceName;
//        }
//
//        public void setIncidentSourceName(String incidentSourceName) {
//            this.incidentSourceName = incidentSourceName;
//        }
//
//        public String getIncidentDescription() {
//            return incidentDescription;
//        }
//
//        public void setIncidentDescription(String incidentDescription) {
//            this.incidentDescription = incidentDescription;
//        }
//
//        public Double getIncidentLatitude() {
//            return incidentLatitude;
//        }
//
//        public void setIncidentLatitude(Double incidentLatitude) {
//            this.incidentLatitude = incidentLatitude;
//        }
//
//        public Double getIncidentLongitude() {
//            return incidentLongitude;
//        }
//
//        public void setIncidentLongitude(Double incidentLongitude) {
//            this.incidentLongitude = incidentLongitude;
//        }
//
//        public String getIncidentAddress() {
//            return incidentAddress;
//        }
//
//        public void setIncidentAddress(String incidentAddress) {
//            this.incidentAddress = incidentAddress;
//        }
//    }
}
