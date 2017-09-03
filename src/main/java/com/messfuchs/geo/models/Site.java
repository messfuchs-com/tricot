package com.messfuchs.geo.models;

import java.util.Set;

public class Site implements Comparable {
    public String name;
    public LocalCoordinateSet localCoordinateSet = new LocalCoordinateSet();
    public GeocentricCoordinateSet geocentricCoordinateSet = new GeocentricCoordinateSet();
    public TachyMeasurementSet measurementSet = new TachyMeasurementSet();

    public Site (String name) {
        this.name = name;
    }

    public Site ( ) {
        this("Unknown");
    }

    public void addLocalCoordinate(LocalCoordinate coordinate) {
        this.localCoordinateSet.add(coordinate);
    }
    
    public void addGeocentricCoordinate(GeocentricCoordinate coordinate) {
        this.geocentricCoordinateSet.add(coordinate);
    }
    
    public void addGeocentricCoordinate(String name, Double x, Double y, Double z) {
        this.geocentricCoordinateSet.add(new GeocentricCoordinate(name, x, y, z));
    }

    public void addMeasurement(TachyMeasurement measurement) {
        this.measurementSet.add(measurement);
    }

    @Override
    public String toString() {
        return "Site{" +
                "name='" + name + '\'' +
                ", coordinateSet=" + localCoordinateSet +
                ", measurementSet=" + measurementSet +
                '}';
    }

    public String getName() {
        return name;
    }

    @Override
    public String getCompareString() { return this.getName(); }

    public void setName(String name) {
        this.name = name;
    }

    public Set<LocalCoordinate> getLocalCoordinateSet() {
        return localCoordinateSet.getSet();
    }

    public void setLocalCoordinateSet(LocalCoordinateSet coordinateSet) {
        this.localCoordinateSet = coordinateSet;
    }

    public Set<TachyMeasurement> getMeasurementSet() {
        return measurementSet.getSet();
    }

    public void setMeasurementSet(TachyMeasurementSet measurementSet) {
        this.measurementSet = measurementSet;
    }

    public Set<GeocentricCoordinate> getGeocentricCoordinateSet() {
        return geocentricCoordinateSet.getSet();
    }

    public void setGeocentricCoordinateSet(GeocentricCoordinateSet geocentricCoordinateSet) {
        this.geocentricCoordinateSet = geocentricCoordinateSet;
    }
    
    public java.util.ArrayList<CoordinatePair> getCoordinatePairs() {
        java.lang.StringBuilder s = new java.lang.StringBuilder();
        java.util.Formatter f = new java.util.Formatter(s);
        
        java.util.ArrayList<CoordinatePair> arrayListCoordinatePair = new java.util.ArrayList<>();
        
        for (LocalCoordinate local: this.getLocalCoordinateSet()) {
            for (GeocentricCoordinate geocentric: this.getGeocentricCoordinateSet()) {
                if (!local.getName().equals(geocentric.getName())) continue;
                
                if (local.getHeight() == null) {
                    local.setHeight(geocentric.elev);
                }
                
                arrayListCoordinatePair.add(
                        new CoordinatePair(local, geocentric)
                );

                f.format("%s;%.3f;%.3f;%.3f;%.3f;%.3f;%.3f\n", 
                        local.getName(),
                        geocentric.getX(), geocentric.getY(), geocentric.getZ(),
                        local.getEast(), local.getNorth(), local.getHeight()
                );
            }
        }
        
        System.out.println(s.toString());
        return arrayListCoordinatePair;
    }
    
}
