package com.messfuchs.geo.models;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class LocalCoordinateSet {

    private Set<LocalCoordinate> set = new TreeSet<LocalCoordinate>(new CompareSorter());

    public boolean add(LocalCoordinate coordinate) {
        return this.set.add(coordinate);
    }

    public LocalCoordinate findLocalCoordinatebyName(String name) {
        Iterator<LocalCoordinate> iterator = set.iterator();
        while(iterator.hasNext()) {
            LocalCoordinate coordinate = iterator.next();
            if (coordinate.getName() == name) {
                return coordinate;
            }
        }
        return null;
    }

    public int size() {
        return this.getSet().size();
    }

    public Set<LocalCoordinate> getSet() {
        return set;
    }
}
