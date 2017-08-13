package com.messfuchs.geo.model;

import java.util.Comparator;
import java.util.TreeSet;

class SortByStation implements Comparator<Measurement> {
    public int compare(Measurement m1, Measurement m2) {
        return m1.getCompareString().compareTo(m2.getCompareString());
    }
}

class SortByName implements Comparator<Coordinate> {
    public int compare(Coordinate c1, Coordinate c2) {
        return c1.getName().compareTo(c2.getName());
    }
}

public class Site {
    private String name;
    private TreeSet<Coordinate> coordinates = new TreeSet<Coordinate>(new SortByName());
    private TreeSet<Measurement> measurements = new TreeSet<Measurement>(new SortByStation());

    public Site (String name) {
        this.name = name;
    }

    public Site ( ) {
        this("Unknown");
    }

    public void addCoordinate(Coordinate coordinate) {
        this.coordinates.add(coordinate);
    }

    public void addMeasurment(Measurement measurement) {
        this.measurements.add(measurement);
    }

    @Override
    public String toString() {
        return "Site{" +
                "name='" + name + '\'' +
                ", coordinates=" + coordinates +
                ", measurements=" + measurements +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TreeSet<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(TreeSet<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public TreeSet<Measurement> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(TreeSet<Measurement> measurements) {
        this.measurements = measurements;
    }
}
