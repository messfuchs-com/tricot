package com.messfuchs.geo.models;

import java.util.Formatter;
import java.util.Set;
import java.util.TreeSet;


public class DataStreamer {

    public Set<Site> siteSet = new TreeSet<Site>(new CompareSorter());

    public boolean addSite (Site site) {
        return siteSet.add(site);
    }

    public boolean addSite (String siteName) {
        return this.addSite(new Site(siteName));
    }

    public void initSite (String siteName) {
        if (!siteSet.contains(new Site(siteName))) {
            this.addSite(siteName);
        }
    }

    public void addCoordinate (String siteName, Coordinate coordinate) {
        this.initSite(siteName);
        for (Site site: siteSet) {
            if (site.getName() == siteName) {
                site.addCoordinate(coordinate);
                continue;
            }
        }
    }

    public void addMeasurement (String siteName, TachyMeasurement measurement) {
        this.initSite(siteName);
        for (Site site: siteSet) {
            if (site.getName() == siteName) {
                site.addMeasurement(measurement);
                continue;
            }
        }
    }

    public String getSiteSummary() {
        StringBuilder s = new StringBuilder();
        Formatter f = new Formatter(s);
        s.append("Summary\n");
        f.format("%15s | %13s | %14s\n", "Site", "# Coordinates", "# Measurements");
        s.append(" ---------------+---------------+---------------\n");
        for (Site site: this.siteSet) {
            f.format("%15s | %13d | %14d\n", site.name, site.coordinateSet.size(), site.measurementSet.size());
        }
        return s.toString();
    }
}
