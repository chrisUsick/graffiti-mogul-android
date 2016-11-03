package com.cu_dev.graffitimogul.domain;

import com.google.android.gms.maps.model.LatLng;

/**
 * Created by chris on 03-Nov-16.
 */

public class Tag {
    private LatLng latLng;
    private double price;
    private String name;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
