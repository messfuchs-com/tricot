package com.messfuchs.geo.models;

import java.util.Set;

public class Site implements Comparable {
    public String name;
    public LocalCoordinateSet coordinateSet = new LocalCoordinateSet();
    public TachyMeasurementSet measurementSet = new TachyMeasurementSet();

    public Site (String name) {
        this.name = name;
    }

    public Site ( ) {
        this("Unknown");
    }

    public void addCoordinate(LocalCoordinate coordinate) {
        this.coordinateSet.add(coordinate);
    }

    public void addMeasurement(TachyMeasurement measurement) {
        this.measurementSet.add(measurement);
    }

    @Override
    public String toString() {
        return "Site{" +
                "name='" + name + '\'' +
                ", coordinateSet=" + coordinateSet +
                ", measurementSet=" + measurementSet +
                '}';
    }

    public String getName() {
        return name;
    }

    public String getCompareString() { return this.getName(); }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LocalCoordinate> getCoordinateSet() {
        return coordinateSet.getSet();
    }

    public void setCoordinateSet(LocalCoordinateSet coordinateSet) {
        this.coordinateSet = coordinateSet;
    }

    public Set<TachyMeasurement> getMeasurementSet() {
        return measurementSet.getSet();
    }

    public void setMeasurementSet(TachyMeasurementSet measurementSet) {
        this.measurementSet = measurementSet;
    }
}
