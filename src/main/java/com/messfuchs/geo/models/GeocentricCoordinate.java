/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.messfuchs.geo.models;

/**
 *
 * @author jurgen
 */
public class GeocentricCoordinate implements Comparable {
    public Double x,y,z, lat, lon, elev, undul, height;
    public String name, code;
   
    public GeocentricCoordinate(String name, Double x, Double y, Double z, String code, Double elev) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.code = code;
        this.elev = elev;
    }
    
    public GeocentricCoordinate(String name, Double x, Double y, Double z, Double elev) {
        this(name, x, y, z, null, elev);
    }

    public GeocentricCoordinate(String name, Double x, Double y, Double z) {
        this(name, x, y, z, null);
    }

    public GeocentricCoordinate(String name, Double x, Double y) {
        this(name, x, y, 0.0);
    }

    public GeocentricCoordinate(Double x, Double y, Double z) {
        this("Coordinate", x, y, z, null);
    }

    public GeocentricCoordinate(Double x, Double y) {
        this(x, y, 0.0);
    }

    public GeocentricCoordinate(String name) {
        this();
        this.name = name;
    }

    public GeocentricCoordinate() {
        this(null, null);
    }

    @Override
    public String getCompareString() {return this.getName(); }

    public Double getX() {
        return x;
    }

    public void setX(Double x) {
        this.x = x;
    }

    public Double getY() {
        return y;
    }

    public void setY(Double y) {
        this.y = y;
    }

    public Double getZ() {
        return z;
    }

    public void setZ(Double z) {
        this.z = z;
    }

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
    
    

    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        if (!super.equals(object)) return false;

        GeocentricCoordinate that = (GeocentricCoordinate) object;

        if (x != null ? !x.equals(that.x) : that.x != null) return false;
        if (y != null ? !y.equals(that.y) : that.y != null) return false;
        if (z != null ? !z.equals(that.z) : that.z != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (code != null ? !code.equals(that.code) : that.code != null) return false;

        return true;
    }

    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (x != null ? x.hashCode() : 0);
        result = 31 * result + (y != null ? y.hashCode() : 0);
        result = 31 * result + (z != null ? z.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (code != null ? code.hashCode() : 0);
        return result;
    }

}
