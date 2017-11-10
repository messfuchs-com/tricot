/*
 * Copyright 2017 jurgen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.messfuchs.geo.models;

/**
 *
 * @author jurgen
 */
public class CoordinateComplex {
    public String name, comment;
    public LocalCoordinate real, calc, imp, medYX;
    public GeocentricCoordinate ecefXYZ, mgiXYZ = null;
    public GeographicCoordinate ecefEll = null, mgiEll = null;
    
    public boolean usage;

    public CoordinateComplex(String name, String comment, LocalCoordinate real, LocalCoordinate calc, LocalCoordinate imp, GeocentricCoordinate ecef, boolean usage) {
        this.name = name;
        this.comment = comment;
        this.real = real;
        this.calc = calc;
        this.imp = imp;
        this.ecefXYZ = ecef;
        this.usage = usage;
    }
    
    public CoordinateComplex(GeocentricCoordinate ecef){
        this.name = ecef.getName();
        this.ecefXYZ = ecef;
        this.comment = "";
        this.real = null;
        this.calc = null;
        this.imp = null;
        this.usage = false;
    }
    
    public CoordinateComplex(){
        this.name = null;
        this.ecefXYZ = null;
        this.comment = "";
        this.real = null;
        this.calc = null;
        this.imp = null;
        this.usage = false;
    }

    public String getName() {
        return name;
    }

    public String getComment() {
        return comment;
    }

    public LocalCoordinate getReal() {
        return real;
    }

    public LocalCoordinate getCalc() {
        return calc;
    }

    public LocalCoordinate getImp() {
        return imp;
    }

    public GeocentricCoordinate getEcef() {
        return ecefXYZ;
    }
    
    public GeocentricCoordinate getMgiXYZ() {
        return mgiXYZ;
    }
    
    public LocalCoordinate getMedYX() {
        return medYX;
    }

    public boolean isUsage() {
        return usage;
    }
    
    

    @Override
    public String toString() {
        return "CoordinateComplex{" + "name=" + name + ", comment=" + comment + ", real=" + real + ", calc=" + calc + ", imp=" + imp + ", ecef=" + ecefXYZ + ", usage=" + usage + '}';
    }
    
}
