package com.bu.livinggood.bean;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

//data class for inicident detail
public class ReportType implements Serializable {
    
    @SerializedName("type")
    @Expose
    private String type;
    @SerializedName("count")
    @Expose
    private Integer count;
    @SerializedName("csi")
    @Expose
    private String csi;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ReportType withType(String type) {
        this.type = type;
        return this;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public ReportType withCount(Integer count) {
        this.count = count;
        return this;
    }

    public String getCsi() {
        return csi;
    }

    public void setCsi(String csi) {
        this.csi = csi;
    }

    public ReportType withCsi(String csi) {
        this.csi = csi;
        return this;
    }

}
