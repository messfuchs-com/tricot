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
public class GeocentricCoordinate implements StringComparable {
    public Double x,y,z, undul, height;
    public String name, code;
    private final double EPS = 1e-3;
   
    public GeocentricCoordinate(String name, Double x, Double y, Double z, String code) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.code = code;
    }
    
    public static GeocentricCoordinate zero(String name) {
        return new GeocentricCoordinate(name, 0.0, 0.0, 0.0, null);
    }
    
    public GeocentricCoordinate divide(double divisor) {
        return new GeocentricCoordinate(
                this.getName(),
                this.getX() / divisor,
                this.getY() / divisor,
                this.getZ() / divisor,
                this.code
        );
    }
    
    public GeocentricCoordinate subtract(GeocentricCoordinate other) {
        GeocentricCoordinate tmpCoordinate = new GeocentricCoordinate(
                this.getName() + "_sub_" + other.getName(),
                this.getX() - other.getX(),
                this.getY() - other.getY(),
                this.getZ() - other.getZ()
        );
        return tmpCoordinate;
    }
    
    public GeocentricCoordinate add(GeocentricCoordinate other) {
        GeocentricCoordinate tmpCoordinate = new GeocentricCoordinate(
                this.getName() + "_add_" + other.getName(),
                this.getX() + other.getX(),
                this.getY() + other.getY(),
                this.getZ() + other.getZ()
        );
        return tmpCoordinate;
    }    
    
    public double getNorm() {
        return Math.sqrt(
            Math.pow(this.getX(), 2)+
            Math.pow(this.getY(), 2)+
            Math.pow(this.getZ(), 2)
        );
    }
    
    public double[] asArray() {
        double[] a = {this.x, this.y, this.z};
        return a;
    }

    @Override
    public String toString() {
        return String.format("GeocentricCoordinate{name=%s, code=%s, x=%.3f, y=%.3f, z=%.3f}", name, code, x, y, z);
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
    
    public GeocentricCoordinate(String name, double[] coord) {
        this.name = name;
        this.x = coord[0];
        this.y = coord[1];
        this.z = coord[2];
    }

    public GeocentricCoordinate(String name) {
        this();
        this.name = name;
    }

    public GeocentricCoordinate() {
        this(null, null, null, null);
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
        final GeocentricCoordinate other = (GeocentricCoordinate) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!(Math.abs(this.getX() - other.getX()) <= EPS)) {
            return false;
        }
        if (!(Math.abs(this.getY() - other.getY()) <= EPS)) {
            return false;
        }
        if (!(Math.abs(this.getZ() - other.getZ()) <= EPS)) {
            return false;
        }
        return true;
    }
    

    @Override
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
