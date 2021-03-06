/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.messfuchs.geo.models;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author jurgen
 */
public class LocalCoordinateSet {

    private Set<LocalCoordinate> set = new TreeSet<>(new CompareSorter());

    public boolean add(LocalCoordinate coordinate) {
        return this.set.add(coordinate);
    }

    public LocalCoordinate findLocalCoordinatebyName(String name) {
        Iterator<LocalCoordinate> iterator = set.iterator();
        while(iterator.hasNext()) {
            LocalCoordinate coordinate = iterator.next();
            if (coordinate.getName().equals(name)) {
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
