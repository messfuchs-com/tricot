package com.messfuchs.geo.model;

public class Station extends Coordinate {
    private double instrumentalHeight;

    public Station(String name, double east, double north, double height, String code, double instrumentalHeight) {
        super(name, east, north, height, code);
        this.instrumentalHeight = instrumentalHeight;
    }

    public Station() {
        super();
    }

    public double getInstrumentalHeight() {
        return instrumentalHeight;
    }

    public void setInstrumentalHeight(double instrumentalHeight) {
        this.instrumentalHeight = instrumentalHeight;
    }
}
