package com.messfuchs.geo.model;

import java.util.UUID;

public class Measurement {

    private static final Double REFLECTOR_ADDITIONAL_CONSTANT = 0.0344;

    private Double azimuth, zenith, slopeDistance, compensatorCross, compensatorLength, ppm,
            instrumentalHeight, reflectorHeight, reflectorAddConst;
    private String station, target, uuid;

    public Measurement(String station, String target, Double azimuth, Double zenith, Double slopeDistance,
                       Double instrumentalHeight, Double reflectorHeight,
                       Double compensatorCross, Double compensatorLength, Double ppm, Double reflectorAddConst) {
        this.station = station;
        this.target = target;
        this.azimuth = azimuth;
        this.zenith = zenith;
        this.slopeDistance = slopeDistance;
        this.compensatorCross = compensatorCross;
        this.compensatorLength = compensatorLength;
        this.ppm = ppm;
        this.instrumentalHeight = instrumentalHeight;
        this.reflectorAddConst = reflectorAddConst;
        this.reflectorHeight = reflectorHeight;
        this.uuid = UUID.randomUUID().toString();
    }

    public String getCompareString() {
        String s = "";
        s = s + station + target + uuid;
        return s;
    }

    public Measurement(String station, String target, Double azimuth, Double zenith, Double slopeDistance, Double instrumentalHeight, Double reflectorHeight) {
        this(station, target, azimuth, zenith, slopeDistance, instrumentalHeight, reflectorHeight, 0.0, 0.0, 0.0, REFLECTOR_ADDITIONAL_CONSTANT);
    }

    public Measurement(Double azimuth, Double zenith, Double slopeDistance, Double instrumentalHeight, Double reflectorHeight) {
        this("fromStation", "toTarget", azimuth, zenith, slopeDistance, instrumentalHeight, reflectorHeight);
    }

    public Measurement(Double azimuth, Double zenith, Double slopeDistance) {
        this(azimuth, zenith, slopeDistance, 0., 0.);
    }

    public Measurement(String station, String target, Double azimuth, Double zenith, Double slopeDistance) {
        this(station, target, azimuth, zenith, slopeDistance, 0.0, 0.0);
    }
    
    public Measurement() {
        this("aStation", "aTarget", null, null, null, null, null, null, null, null, null);
    }

    @Override
    public String toString() {
        return "Measurement{" +
                "azimuth=" + azimuth +
                ", zenith=" + zenith +
                ", slopeDistance=" + slopeDistance +
                ", compensatorCross=" + compensatorCross +
                ", compensatorLength=" + compensatorLength +
                ", ppm=" + ppm +
                ", instrumentalHeight=" + instrumentalHeight +
                ", reflectorHeight=" + reflectorHeight +
                ", reflectorAddConst=" + reflectorAddConst +
                ", station='" + station + '\'' +
                ", target='" + target + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Measurement that = (Measurement) o;

        if (Double.compare(that.getAzimuth(), getAzimuth()) != 0) return false;
        if (Double.compare(that.getZenith(), getZenith()) != 0) return false;
        if (Double.compare(that.getSlopeDistance(), getSlopeDistance()) != 0) return false;
        if (Double.compare(that.getCompensatorCross(), getCompensatorCross()) != 0) return false;
        if (Double.compare(that.getCompensatorLength(), getCompensatorLength()) != 0) return false;
        if (Double.compare(that.getPpm(), getPpm()) != 0) return false;
        if (Double.compare(that.getInstrumentalHeight(), getInstrumentalHeight()) != 0) return false;
        if (Double.compare(that.getReflectorHeight(), getReflectorHeight()) != 0) return false;
        if (Double.compare(that.getReflectorAddConst(), getReflectorAddConst()) != 0) return false;
        if (getStation() != null ? !getStation().equals(that.getStation()) : that.getStation() != null) return false;
        return getTarget() != null ? getTarget().equals(that.getTarget()) : that.getTarget() == null;
    }

    @Override
    public int hashCode() {
        int result;
        long temp;
        temp = Double.doubleToLongBits(getAzimuth());
        result = (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getZenith());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getSlopeDistance());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getCompensatorCross());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getCompensatorLength());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getPpm());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getInstrumentalHeight());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getReflectorHeight());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        temp = Double.doubleToLongBits(getReflectorAddConst());
        result = 31 * result + (int) (temp ^ (temp >>> 32));
        result = 31 * result + (getStation() != null ? getStation().hashCode() : 0);
        result = 31 * result + (getTarget() != null ? getTarget().hashCode() : 0);
        return result;
    }

    public Double getAzimuth() {

        return azimuth;
    }

    public void setAzimuth(Double azimuth) {
        this.azimuth = azimuth;
    }

    public Double getZenith() {
        return zenith;
    }

    public void setZenith(Double zenith) {
        this.zenith = zenith;
    }

    public Double getSlopeDistance() {
        return slopeDistance;
    }

    public void setSlopeDistance(Double slopeDistance) {
        this.slopeDistance = slopeDistance;
    }

    public Double getCompensatorCross() {
        return compensatorCross;
    }

    public void setCompensatorCross(Double compensatorCross) {
        this.compensatorCross = compensatorCross;
    }

    public Double getCompensatorLength() {
        return compensatorLength;
    }

    public void setCompensatorLength(Double compensatorLength) {
        this.compensatorLength = compensatorLength;
    }

    public Double getPpm() {
        return ppm;
    }

    public void setPpm(Double ppm) {
        this.ppm = ppm;
    }

    public Double getInstrumentalHeight() {
        return instrumentalHeight;
    }

    public void setInstrumentalHeight(Double instrumentalHeight) {
        this.instrumentalHeight = instrumentalHeight;
    }

    public Double getReflectorHeight() {
        return reflectorHeight;
    }

    public void setReflectorHeight(Double reflectorHeight) {
        this.reflectorHeight = reflectorHeight;
    }

    public Double getReflectorAddConst() {
        return reflectorAddConst;
    }

    public void setReflectorAddConst(Double reflectorAddConst) {
        this.reflectorAddConst = reflectorAddConst;
    }

    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getTarget() {
        return target;
    }

    public void setTarget(String target) {
        this.target = target;
    }
}
