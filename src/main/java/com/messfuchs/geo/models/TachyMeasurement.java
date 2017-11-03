package com.messfuchs.geo.models;

// import at.messfuchs.geo.models.StringComparable
        ;

public class TachyMeasurement extends Measurement implements StringComparable{

    public TachyResponse response;

    public TachyMeasurement(String station, String target, TachyResponse tachyResponse) {
        this.station = station;
        this.target = target;
        this.response = tachyResponse;
        this.generateUuid();
    }

    @Override
    public String getCompareString() {
        return this.station + this.target + this.getUuid();
    }
}
