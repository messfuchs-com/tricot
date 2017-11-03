package com.messfuchs.geo.models;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

public class StationSet {

    private final Set<TachyMeasurement> set = new TreeSet<>(new CompareSorter());

    public int size() {
        return set.size();
    }

    public boolean add(TachyMeasurement measurement) {
        return set.add(measurement);
    }

    public TachyMeasurementSet findTachyMeasurementSetStation(String name) {
        TachyMeasurementSet measurementSet = new TachyMeasurementSet();
        Iterator<TachyMeasurement> iterator = set.iterator();
        while(iterator.hasNext()) {
            TachyMeasurement measurement = iterator.next();
            if (measurement.station == name) {
                measurementSet.add(measurement);
            }
        }
        if (measurementSet.size() == 0) return null;
        return measurementSet;
    }

    public Set<TachyMeasurement> getSet() {
        return set;
    }
}