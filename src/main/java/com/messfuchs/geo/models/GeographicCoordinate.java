/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.messfuchs.geo.models;

import java.util.Objects;

/**
 *
 * @author jurgen
 */
public class GeographicCoordinate implements StringComparable {
    public Double lat, lon, elev, undul, height;
    public String name, code;
   
    public GeographicCoordinate(String name, Double lat, Double lon, Double elev, String code) {
        this.name = name;
        this.lat = lat;
        this.lon = lon;
        this.code = code;
        this.elev = elev;
        this.undul = 0.;
    }
    
    public GeographicCoordinate(String name, Double lat, Double lon, Double elev) {
        this(name, lat, lon, elev, null);
    }
    
    public GeographicCoordinate(String name) {
        this(name, null, null, null);
    }
    
    public Double getHeight() {
        if ( this.elev == null ) {
            return null;
        }
        return this.elev + this.undul;
    }
    
    public double[] asArray() {
        double[] a = {this.lat, this.lon, this.elev};
        return a;
    }

    @Override
    public String toString() {
        return "GeographicCoordinate{name=" + name + ", code=" + code + ", lat=" + lat + ", lon=" + lon + ", elev=" + elev + ", undul=" + undul + ", height=" + height + '}';
    }

    @Override
    public String getCompareString() {return this.getName(); }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Double getLon() {
        return lon;
    }

    public void setLon(Double lon) {
        this.lon = lon;
    }

    public Double getElev() {
        return elev;
    }

    public void setElev(Double elev) {
        this.elev = elev;
    }

    public Double getUndul() {
        return undul;
    }

    public void setUndul(Double undul) {
        this.undul = undul;
    }
    
    

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 53 * hash + Objects.hashCode(this.lat);
        hash = 53 * hash + Objects.hashCode(this.lon);
        hash = 53 * hash + Objects.hashCode(this.elev);
        hash = 53 * hash + Objects.hashCode(this.name);
        hash = 53 * hash + Objects.hashCode(this.code);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final GeographicCoordinate other = (GeographicCoordinate) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.lat, other.lat)) {
            return false;
        }
        if (!Objects.equals(this.lon, other.lon)) {
            return false;
        }
        if (!Objects.equals(this.elev, other.elev)) {
            return false;
        }
        return true;
    }
    

}
