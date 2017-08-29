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
public class GeocentricCoordinate {
    public Double x,y,z;
    public String name, code;
   
        public GeocentricCoordinate(String name, Double x, Double y, Double z, String code) {
        this.name = name;
        this.x = x;
        this.y = y;
        this.z = z;
        this.code = code;
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

}
