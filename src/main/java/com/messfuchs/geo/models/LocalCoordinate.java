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
public class LocalCoordinate implements  Comparable {
    
    public Double east, north, height;
    public String name, code;

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
        this("Coordinate", east, north, height, null);
    }

    public LocalCoordinate(Double east, Double north) {
        this(east, north, null);
    }

    public LocalCoordinate(String name) {
        this();
        this.name = name;
    }
    
    public LocalCoordinate() {
        this(null, null);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "east=" + east +
                ", north=" + north +
                ", height=" + height +
                ", name='" + name + '\'' +
                ", code='" + code + '\'' +
                '}';
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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        LocalCoordinate point = (LocalCoordinate) o;

        if (Double.compare(point.getEast(), getEast()) != 0) return false;
        if (Double.compare(point.getNorth(), getNorth()) != 0) return false;
        if (Double.compare(point.getHeight(), getHeight()) != 0) return false;
        if (!getName().equals(point.getName())) return false;
        return getCode().equals(point.getCode());
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
}
