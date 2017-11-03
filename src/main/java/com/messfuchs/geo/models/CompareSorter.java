package com.messfuchs.geo.models;

import java.util.Comparator;
// import at.messfuchs.geo.models.StringComparable;

public class CompareSorter implements Comparator<StringComparable> {
    public int compare(StringComparable o1, StringComparable o2) {
        return o1.getCompareString().compareTo(o2.getCompareString());
    }
}