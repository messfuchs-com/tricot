/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.messfuchs.geo.models;

// import at.messfuchs.geo.models.GeocentricCoordinate;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author jurgen
 */
public class GeocentricCoordinateSet {
    
    private final Set<GeocentricCoordinate> set = new TreeSet<>(new CompareSorter());

    public boolean add(GeocentricCoordinate coordinate) {
        return this.set.add(coordinate);
    }

    public GeocentricCoordinate findGeocentricCoordinatebyName(String name) {
        Iterator<GeocentricCoordinate> iterator = set.iterator();
        while(iterator.hasNext()) {
            GeocentricCoordinate coordinate = iterator.next();
            if (coordinate.getName().equals(name)) {
                return coordinate;
            }
        }
        return null;
    }

    public int size() {
        return this.getSet().size();
    }

    public Set<GeocentricCoordinate> getSet() {
        return set;
    }
}
