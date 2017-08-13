package com.messfuchs.geo.model;

public abstract class DataWriter extends DataStreamer implements Writable {

    public String outputFileCoordinates, outputFileMeasurements;

    public DataWriter (Site site, String outputFileCoordinates, String outputFileMeasurements) {
        super(site);
        this.outputFileCoordinates = outputFileCoordinates;
        this.outputFileMeasurements = outputFileMeasurements;
    }
}
