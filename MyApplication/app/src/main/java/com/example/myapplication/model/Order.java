package com.example.myapplication.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Order {
    @SerializedName("orderId") private String orderId;
    @SerializedName("serviceTypeId") private Integer serviceTypeId;
    @SerializedName("serviceType") private String serviceType;
    @SerializedName("model") private String model;
    @SerializedName("startTime") private String startTime;
    @SerializedName("plates") private String plates;
    @SerializedName("pyramidColor") private String pyramidColor;
    @SerializedName("pyramidNumber") private Integer pyramidNumber;
    @SerializedName("orderStatusId") private Integer orderStatusId;
    @SerializedName("orderStatus") private String orderStatus;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getServiceTypeId() {
        return serviceTypeId;
    }

    public void setServiceTypeId(Integer serviceTypeId) {
        this.serviceTypeId = serviceTypeId;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getPlates() {
        return plates;
    }

    public void setPlates(String plates) {
        this.plates = plates;
    }

    public String getPyramidColor() {
        return pyramidColor;
    }

    public void setPyramidColor(String pyramidColor) {
        this.pyramidColor = pyramidColor;
    }

    public Integer getPyramidNumber() {
        return pyramidNumber;
    }

    public void setPyramidNumber(Integer pyramidNumber) {
        this.pyramidNumber = pyramidNumber;
    }

    public Integer getOrderStatusId() {
        return orderStatusId;
    }

    public void setOrderStatusId(Integer orderStatusId) {
        this.orderStatusId = orderStatusId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }
}
