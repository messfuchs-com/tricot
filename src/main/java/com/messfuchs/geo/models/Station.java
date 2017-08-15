package com.messfuchs.geo.models;

public class Station extends Coordinate {

    public double instrumentalHeight = 0.0;
    private TachyMeasurementSet measurementSet = new TachyMeasurementSet();

    public Station(String name, double east, double north, double height, String code, double instrumentalHeight) {
        super(name, east, north, height, code);
        this.instrumentalHeight = instrumentalHeight;
    }

    public Station() {
        super();
    }

    public void addTachyMeasurement(TachyMeasurement tachyMeasurement) {
        if ((tachyMeasurement.station.equals(this.name)) &&
                (tachyMeasurement.response.instrumentalHeight == this.instrumentalHeight)){
            this.measurementSet.add(tachyMeasurement);
        }
    }
}
