package com.messfuchs.geo.models;

import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class CoordinateSet {

    private Set<Coordinate> set = new TreeSet<Coordinate>(new CompareSorter());

    public boolean add(Coordinate coordinate) {
        return this.set.add(coordinate);
    }

    public Coordinate findCoordinatebyName(String name) {
        Iterator<Coordinate> iterator = set.iterator();
        while(iterator.hasNext()) {
            Coordinate coordinate = iterator.next();
            if (coordinate.getName() == name) {
                return coordinate;
            }
        }
        return null;
    }

    public Set<Coordinate> getSet() {
        return set;
    }
}
