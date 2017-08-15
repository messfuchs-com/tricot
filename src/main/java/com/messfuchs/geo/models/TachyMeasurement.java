package com.messfuchs.geo.models;

public class TachyMeasurement extends Measurement implements Comparable{

    public TachyResponse response;

    public TachyMeasurement(String station, String target, TachyResponse tachyResponse) {
        this.station = station;
        this.target = target;
        this.response = tachyResponse;
        this.generateUuid();
    }

    public String getCompareString() {
        return this.station + this.target + this.getUuid();
    }
}
