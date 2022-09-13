package com.bu.livinggood.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class RawCrimeData implements Serializable {

    @SerializedName("total_incidents")
    private Integer totalIncidents;
    @SerializedName("total_pages")
    private Integer totalPages;
    @SerializedName("incidents")
    private List<IncidentsDTO> incidents;

    public Integer getTotalIncidents() {
        return totalIncidents;
    }

    public void setTotalIncidents(Integer totalIncidents) {
        this.totalIncidents = totalIncidents;
    }

    public Integer getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(Integer totalPages) {
        this.totalPages = totalPages;
    }

    public List<IncidentsDTO> getIncidents() {
        return incidents;
    }

    public void setIncidents(List<IncidentsDTO> incidents) {
        this.incidents = incidents;
    }

    public static class IncidentsDTO implements Serializable {
        @SerializedName("city_key")
        private String cityKey;
        @SerializedName("incident_code")
        private String incidentCode;
        @SerializedName("incident_date")
        private String incidentDate;
        @SerializedName("incident_offense")
        private String incidentOffense;
        @SerializedName("incident_offense_code")
        private String incidentOffenseCode;
        @SerializedName("incident_offense_description")
        private String incidentOffenseDescription;
        @SerializedName("incident_offense_detail_description")
        private String incidentOffenseDetailDescription;
        @SerializedName("incident_offense_crime_against")
        private String incidentOffenseCrimeAgainst;
        @SerializedName("incident_offense_action")
        private String incidentOffenseAction;
        @SerializedName("incident_source_original_type")
        private String incidentSourceOriginalType;
        @SerializedName("incident_source_name")
        private String incidentSourceName;
        @SerializedName("incident_latitude")
        private Double incidentLatitude;
        @SerializedName("incident_longitude")
        private Double incidentLongitude;
        @SerializedName("incident_address")
        private String incidentAddress;

        public String getCityKey() {
            return cityKey;
        }

        public void setCityKey(String cityKey) {
            this.cityKey = cityKey;
        }

        public String getIncidentCode() {
            return incidentCode;
        }

        public void setIncidentCode(String incidentCode) {
            this.incidentCode = incidentCode;
        }

        public String getIncidentDate() {
            return incidentDate;
        }

        public void setIncidentDate(String incidentDate) {
            this.incidentDate = incidentDate;
        }

        public String getIncidentOffense() {
            return incidentOffense;
        }

        public void setIncidentOffense(String incidentOffense) {
            this.incidentOffense = incidentOffense;
        }

        public String getIncidentOffenseCode() {
            return incidentOffenseCode;
        }

        public void setIncidentOffenseCode(String incidentOffenseCode) {
            this.incidentOffenseCode = incidentOffenseCode;
        }

        public String getIncidentOffenseDescription() {
            return incidentOffenseDescription;
        }

        public void setIncidentOffenseDescription(String incidentOffenseDescription) {
            this.incidentOffenseDescription = incidentOffenseDescription;
        }

        public String getIncidentOffenseDetailDescription() {
            return incidentOffenseDetailDescription;
        }

        public void setIncidentOffenseDetailDescription(String incidentOffenseDetailDescription) {
            this.incidentOffenseDetailDescription = incidentOffenseDetailDescription;
        }

        public String getIncidentOffenseCrimeAgainst() {
            return incidentOffenseCrimeAgainst;
        }

        public void setIncidentOffenseCrimeAgainst(String incidentOffenseCrimeAgainst) {
            this.incidentOffenseCrimeAgainst = incidentOffenseCrimeAgainst;
        }

        public String getIncidentOffenseAction() {
            return incidentOffenseAction;
        }

        public void setIncidentOffenseAction(String incidentOffenseAction) {
            this.incidentOffenseAction = incidentOffenseAction;
        }

        public String getIncidentSourceOriginalType() {
            return incidentSourceOriginalType;
        }

        public void setIncidentSourceOriginalType(String incidentSourceOriginalType) {
            this.incidentSourceOriginalType = incidentSourceOriginalType;
        }

        public String getIncidentSourceName() {
            return incidentSourceName;
        }

        public void setIncidentSourceName(String incidentSourceName) {
            this.incidentSourceName = incidentSourceName;
        }

        public Double getIncidentLatitude() {
            return incidentLatitude;
        }

        public void setIncidentLatitude(Double incidentLatitude) {
            this.incidentLatitude = incidentLatitude;
        }

        public Double getIncidentLongitude() {
            return incidentLongitude;
        }

        public void setIncidentLongitude(Double incidentLongitude) {
            this.incidentLongitude = incidentLongitude;
        }

        public String getIncidentAddress() {
            return incidentAddress;
        }

        public void setIncidentAddress(String incidentAddress) {
            this.incidentAddress = incidentAddress;
        }
    }
}
