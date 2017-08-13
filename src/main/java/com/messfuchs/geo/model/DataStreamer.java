package com.messfuchs.geo.model;

public class DataStreamer {

    private Site site;

    public DataStreamer (Site site) {
        this.site = site;
    }

    public Site getSite() {
        return site;
    }

    public void addCoordinate (Coordinate coordinate) {
        this.site.addCoordinate(coordinate);
    }

    public void addMeasurement (Measurement measurement) {
        this.site.addMeasurment(measurement);
    }
}
