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

import java.util.Objects;


/**
 *
 * @author jurgen
 */
public class LocalCoordinate implements  StringComparable {
    
    public Double east, north, height;
    public String name, code;
    public boolean useThis = true;
    private final double EPS = 1e-1;
    
    public LocalCoordinate(String name, Double east, Double north, Double height, String code) {
        this.name = name;
        this.east = east;
        this.north = north;
        this.height = height;
        this.code = code;
    }

    public LocalCoordinate(String name, Double east, Double north, Double height) {
        this(name, east, north, height, null);
    }

    public LocalCoordinate(String name, Double east, Double north) {
        this(name, east, north, null);
    }

    public LocalCoordinate(Double east, Double north, Double height) {
        this("LocalCoordinate", east, north, height, null);
    }

    public LocalCoordinate(Double east, Double north) {
        this(east, north, null);
    }

    public LocalCoordinate(String name) {
        this();
        this.name = name;
    }
    
    public LocalCoordinate(String name, double[] coords) {
        this.name = name;
        this.east = coords[0];
        this.north = coords[1];
        this.height = coords[1];
        this.code = null;
    }
    
    public static LocalCoordinate zero(String name) {
        return new LocalCoordinate(name, 0.0, 0.0, 0.0, null);
    }

    public LocalCoordinate add(LocalCoordinate other) {
        return new LocalCoordinate(
                this.getName(),
                this.getEast() + other.getEast(),
                this.getNorth() + other.getNorth(),
                this.getHeight() + other.getHeight()
        );
    }

    public LocalCoordinate subtract(LocalCoordinate other) {
        // System.out.println(this + " - " + other);
        
        LocalCoordinate tmpCoordinate = new LocalCoordinate(
                "",
                this.getEast() - other.getEast(),
                this.getNorth() - other.getNorth(),
                this.getHeight() - other.getHeight()
        );
        tmpCoordinate.setName(this.getName() + "_sub_" + other.getName());
        // System.out.println("Diff: " + tmpCoordinate);
        return tmpCoordinate;
    }

    public LocalCoordinate divide(double divisor) {
        Double e = null;
        Double n = null;
        Double h = null;
        if (divisor != 0.0 && this.getEast() != null) e = this.getEast() / divisor;
        if (divisor != 0.0 && this.getNorth() != null) n = this.getNorth() / divisor;
        if (divisor != 0.0 && this.getHeight() != null) h = this.getHeight() / divisor;
        return new LocalCoordinate(this.getName(), e, n, h, this.code);
    }
    
    public LocalCoordinate() {
        this(null, null, null, null);
    }

    @Override
    public String toString() {
        return String.format("LocalCoordinate{name=%s, code=%s, east=%.3f, north=%.3f, height=%.3f}", name, code, east, north, height);
    }

    @Override
    public String getCompareString() {return this.getName(); }

    public Double getEast() {
        return east;
    }

    public void setEast(Double east) {
        this.east = east;
    }

    public Double getNorth() {
        return north;
    }

    public void setNorth(Double north) {
        this.north = north;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
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
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getEast());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getNorth());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getHeight());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + getName().hashCode();
        result = 31 * result + getCode().hashCode();
        return result;
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
        final LocalCoordinate other = (LocalCoordinate) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }

        if (this.east != null ? (other.getEast() != null ? (Math.abs(this.east - other.getEast())>EPS) : false) : (other.getEast() != null)) return false;
        if (this.north != null ? (other.getNorth() != null ? (Math.abs(this.north - other.getNorth())>EPS) : false) : (other.getNorth() != null)) return false;
        if (this.height != null ? (other.getHeight() != null ? (Math.abs(this.height - other.getHeight())>EPS) : false) : (other.getHeight() != null)) return false;
        
        return true; 
    }
}
