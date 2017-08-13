package com.messfuchs.geo.model;

public class Coordinate {
    public Double east, north, height;
    public String name, code;

    public Coordinate(String name, Double east, Double north, Double height, String code) {
        this.name = name;
        this.east = east;
        this.north = north;
        this.height = height;
        this.code = code;
    }

    public Coordinate(String name, Double east, Double north, Double height) {
        this(name, east, north, height, null);
    }

    public Coordinate(String name, Double east, Double north) {
        this(name, east, north, 0.0);
    }

    public Coordinate(Double east, Double north, Double height) {
        this("Coordinate", east, north, height, null);
    }

    public Coordinate(Double east, Double north) {
        this(east, north, 0.0);
    }
    
    public Coordinate() {
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

        Coordinate point = (Coordinate) o;

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
