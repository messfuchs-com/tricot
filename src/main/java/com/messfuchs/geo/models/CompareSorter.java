package com.messfuchs.geo.models;

import java.util.Comparator;

public class CompareSorter implements Comparator<Comparable> {
    public int compare(Comparable o1, Comparable o2) {
        return o1.getCompareString().compareTo(o2.getCompareString());
    }
}