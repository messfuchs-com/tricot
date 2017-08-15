package com.messfuchs.geo.models;

public class Angle {

    public final static double RHO_RAD = Math.PI / 200; // GON -> RAD
    public final static double RHO_DEG = 180 / 200;     // GON -> DEG

    public Double value;

    public Angle(Double value) {
        this.value = value;
    }

    public Double toRad() {
        return this.value * RHO_RAD;
    }

    public void fromRad(double value) {
        this.value = value / RHO_RAD;
    }

    public Double toDeg() {
        return this.value * RHO_DEG;
    }

    public void fromDeg(double value) {
        this.value = value / RHO_DEG;
    }

    @Override
    public String toString() {
        return value.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Angle angle = (Angle) o;

        return value != null ? value.equals(angle.value) : angle.value == null;
    }

    @Override
    public int hashCode() {
        return value != null ? value.hashCode() : 0;
    }
}
