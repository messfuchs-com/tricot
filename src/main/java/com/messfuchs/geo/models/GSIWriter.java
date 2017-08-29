package com.messfuchs.geo.models;

import java.util.Formatter;

public class GSIWriter extends DataWriter {

    public int gsiSize;
    public static int SITE_CODE = 1;
    public static int STATION_CODE = 11;

    public GSIWriter(int gsiSize, String outputFileGSI) {
        super(outputFileGSI, outputFileGSI);
        this.gsiSize = gsiSize;
    }

    public GSIWriter(String outputFileGSI) {
        this(16, outputFileGSI);
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
        for (Site site: this.siteSet) {
            f.format("*41%04d%s 42....%s\n", lineCount, this.toGSI(SITE_CODE), this.toGSI(site.name));
            lineCount++;

            for (LocalCoordinate coordinate : site.getCoordinateSet()) {
                f.format("*11%04d%s", lineCount, this.toGSI(coordinate.getName()));
                f.format(" 81..40%+0" + (this.gsiSize+1) + ".0f", coordinate.getEast()*1e+3);
                f.format(" 82..40%+0" + (this.gsiSize+1) + ".0f", coordinate.getNorth()*1e+3);
                f.format(" 83..40%+0" + (this.gsiSize+1) + ".0f", coordinate.getHeight()*1e+3);
                f.format(" 71....%s", this.toGSI(coordinate.getCode()));

                s.append("\n");
                lineCount++;
            }

            for (TachyMeasurement measurement : site.getMeasurementSet()) {
                if (!measurement.station.equals(recentStation)) {
                    recentStation = measurement.station;
                    f.format("*41%04d%s 42....%s\n", lineCount, this.toGSI(STATION_CODE), this.toGSI(recentStation));
                    lineCount++;
                }
                f.format("*11%04d%s", lineCount, this.toGSI(measurement.target));
                f.format(" 87..10%+0" + (this.gsiSize+1) + ".0f", measurement.response.reflectorHeight*1e+3);
                f.format(" 58..16%+0" + (this.gsiSize+1) + ".0f", measurement.response.reflectorAddConst*1e+4);
                f.format(" 21.322%+0" + (this.gsiSize+1) + ".0f", measurement.response.azimuth.value*1e+5);
                f.format(" 22.322%+0" + (this.gsiSize+1) + ".0f", measurement.response.zenith.value*1e+5);
                f.format(" 31..00%+0" + (this.gsiSize+1) + ".0f", measurement.response.slopeDistance*1e+3);
                f.format(" 59..16%+0" + (this.gsiSize+1) + ".0f", measurement.response.ppm*1e+4);

                s.append("\n");
                lineCount++;
            }
        }
        return s.toString();
    }
}
