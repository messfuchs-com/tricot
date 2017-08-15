package com.messfuchs.geo.models;

public class Target extends Coordinate{

    public double reflectorHeight = 0.0;

    public Target(String name, double east, double north, double height, String code, double reflectorHeight) {
        super(name, east, north, height, code);
        this.reflectorHeight = reflectorHeight;
    }

    public Target(String name) {
        super();
    }

    public Target() {
        super();
    }
}
