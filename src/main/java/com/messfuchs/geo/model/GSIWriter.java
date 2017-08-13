package com.messfuchs.geo.model;

import java.util.Formatter;

public class GSIWriter extends DataWriter {

    public int gsiSize;
    public static int SITE_CODE = 1;
    public static int STATION_CODE = 11;

    public GSIWriter(Site site, int gsiSize, String outputFileGSI) {
        super(site, outputFileGSI, outputFileGSI);
        this.gsiSize = gsiSize;
    }

    public GSIWriter(Site site, String outputFileGSI) {
        this(site, 16, outputFileGSI);
    }


    public String toGSI(Object o) {
        StringBuilder s = new StringBuilder("+");
        String so = o.toString();
        int noZeros = this.gsiSize - so.length();
        while (noZeros > 0) {
            s.append('0');
            noZeros--;
        }
        s.append(o.toString());
        return s.toString();
    }

    public String writeData() {
        StringBuilder s = new StringBuilder("");
        Formatter f = new Formatter(s);
        int lineCount = 1;
        String recentStation = null;
        f.format("*41%04d%s 42....%s\n", lineCount, this.toGSI(SITE_CODE), this.toGSI(this.getSite().getName()));
        lineCount++;

        for (Coordinate coordinate : this.getSite().getCoordinates()) {
            f.format("*11%04d%s", lineCount, this.toGSI(coordinate.getName()));
            f.format(" 81..40%+0" + (this.gsiSize+1) + ".0f", coordinate.getEast()*1e+3);
            f.format(" 82..40%+0" + (this.gsiSize+1) + ".0f", coordinate.getNorth()*1e+3);
            f.format(" 83..40%+0" + (this.gsiSize+1) + ".0f", coordinate.getHeight()*1e+3);
            f.format(" 71....%s", this.toGSI(coordinate.getCode()));

            s.append("\n");
            lineCount++;
        }

        for (Measurement measurement : this.getSite().getMeasurements()) {
            if (!measurement.getStation().equals(recentStation)) {
                recentStation = measurement.getStation();
                f.format("*41%04d%s 42....%s\n", lineCount, this.toGSI(STATION_CODE), this.toGSI(recentStation));
                lineCount++;
            }
            f.format("*11%04d%s", lineCount, this.toGSI(measurement.getTarget()));
            f.format(" 87..10%+0" + (this.gsiSize+1) + ".0f", measurement.getReflectorHeight()*1e+3);
            f.format(" 58..16%+0" + (this.gsiSize+1) + ".0f", measurement.getReflectorAddConst()*1e+4);
            f.format(" 21.322%+0" + (this.gsiSize+1) + ".0f", measurement.getAzimuth()*1e+5);
            f.format(" 22.322%+0" + (this.gsiSize+1) + ".0f", measurement.getZenith()*1e+5);
            f.format(" 31..00%+0" + (this.gsiSize+1) + ".0f", measurement.getSlopeDistance()*1e+3);
            f.format(" 59..16%+0" + (this.gsiSize+1) + ".0f", measurement.getPpm()*1e+4);

            s.append("\n");
            lineCount++;
        }
        return s.toString();
    }
}
