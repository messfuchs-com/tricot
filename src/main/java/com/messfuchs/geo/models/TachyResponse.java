package com.messfuchs.geo.models;

class PrismConstants {
    public static final double REFLESS = 0.0344;
    public static final double MINI_PRISM = 0.0175;
    public static final double ROUND_PRISM = 0.0;
}


public class TachyResponse extends Response {

    public Angle azimuth, zenith;
    public Angle compensatorCross = new Angle(0.);
    public Angle compensatorLength = new Angle(0.);
    public Double slopeDistance, ppm = 0., instrumentalHeight = 0., reflectorHeight = 0.;
    public Double reflectorAddConst = PrismConstants.REFLESS;
    public String code = "50-0";

    public TachyResponse(String target, Angle azimuth, Angle zenith, Double slopeDistance,
                         Double instrumentalHeight, Double reflectorHeight, Double reflectorAddConst,
                         Angle compensatorCross, Angle compensatorLength, Double ppm) {
        super(target);
        this.azimuth = azimuth;
        this.zenith = zenith;
        this.compensatorCross = compensatorCross;
        this.compensatorLength = compensatorLength;
        this.slopeDistance = slopeDistance;
        this.ppm = ppm;
        this.instrumentalHeight = instrumentalHeight;
        this.reflectorHeight = reflectorHeight;
        this.reflectorAddConst = reflectorAddConst;
    }

    public TachyResponse(Angle azimuth, Angle zenith, Double slopeDistance, Double instrumentalHeight, Double reflectorHeight) {
        this("target", azimuth, zenith, slopeDistance,
                instrumentalHeight, reflectorHeight, PrismConstants.REFLESS,
                null, null, 0.);
    }

    public TachyResponse(String target, Angle azimuth, Angle zenith, Double slopeDistance) {
        this(azimuth, zenith, slopeDistance, 0., 0.);
        this.target = target;
    }

    public TachyResponse(String target, Double azimuth, Double zenit, Double slopeDistance) {
        this(target, new Angle(azimuth), new Angle(zenit), slopeDistance);
    }

    public TachyResponse() {
        this(new Angle(0.), new Angle(100.), 0.,
                0., 0.);
    }

    public TachyResponse(String target) {
        this();
        this.target = target;
    }

}
