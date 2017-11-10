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
    private final double epsAngle = 1e-6;
    private final double epsDistance = 1e-2;
    
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
    
    public static String toDDMMSS(double angle) {
        double aDeg, aMin, aSec;
        
        aDeg = Math.floor(angle);
        aMin = (angle - aDeg)*60;
        aSec = (aMin - Math.floor(aMin))*60;
        aMin = Math.floor(aMin);
        
        return String.format("%.0fd%.0fm%.4fs", aDeg, aMin, aSec);
    }
    
    
    
    public boolean equals(GeographicCoordinate obj) {
        if (obj == null) return false;
        double dLat = this.lat - obj.lat;
        double dLon = this.lon - obj.lon;
        double dEle = this.elev - obj.elev;
        
        if (Math.abs(dLat) > epsAngle) return false;
        if (Math.abs(dLon) > epsAngle) return false;
        if (Math.abs(dEle) > epsDistance) return false;
        
        return true;
        
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
        return new double[] {this.lat, this.lon, this.elev};
    }
    
    public double[] asArrayRadians() {
        return new double[] {
            Math.toRadians(this.lat),
            Math.toRadians(this.lon),
            this.elev
        };
    }

    @Override
    public String toString() {
        return String.format(
                "GeographicCoordinate{name=%s, code=%s, lon=%s, lat=%s, ele=%.3f}", 
                name, code, 
                toDDMMSS(lon), 
                toDDMMSS(lat), elev);
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
        /*if (!Objects.equals(this.lat, other.lat)) {
            return false;
        }
        if (!Objects.equals(this.lon, other.lon)) {
            return false;
        }
        if (!Objects.equals(this.elev, other.elev)) {
            return false;
        }*/
        double dLat = this.lat - other.lat;
        double dLon = this.lon - other.lon;
        double dEle = this.elev - other.elev;
        
        if (Math.abs(dLat) > epsAngle) return false;
        if (Math.abs(dLon) > epsAngle) return false;
        if (Math.abs(dEle) > epsDistance) return false;
        return true;
    }
    

}
