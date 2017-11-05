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
    public LocalCoordinate real, calc, imp;
    public GeocentricCoordinate ecef;
    public boolean usage;

    public CoordinateComplex(String name, String comment, LocalCoordinate real, LocalCoordinate calc, LocalCoordinate imp, GeocentricCoordinate ecef, boolean usage) {
        this.name = name;
        this.comment = comment;
        this.real = real;
        this.calc = calc;
        this.imp = imp;
        this.ecef = ecef;
        this.usage = usage;
    }
    
    public CoordinateComplex(GeocentricCoordinate ecef){
        this.name = ecef.getName();
        this.comment = "";
        this.real = null;
        this.calc = null;
        this.imp = null;
        this.usage = true;
    }
}
