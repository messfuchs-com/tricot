package com.messfuchs.geo.models;

public abstract class DataWriter extends DataStreamer implements Writable {

    public String outputFileCoordinates, outputFileMeasurements;

    public DataWriter (String outputFileCoordinates, String outputFileMeasurements) {
        this.outputFileCoordinates = outputFileCoordinates;
        this.outputFileMeasurements = outputFileMeasurements;
    }
}
