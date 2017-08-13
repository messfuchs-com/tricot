package com.messfuchs.geo.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashSet;
import java.util.Scanner;

public abstract class DataReader extends DataStreamer implements Readable {

    public String inputFile;

    public DataReader (Site site, String inputFile) {
        super(site);
        this.inputFile = inputFile;
    }

    public abstract Measurement parseMeasurementLine(String line);

    public abstract Coordinate parseCoordinateLine(String line);

    public abstract Site parseSiteLine(String line);

    public abstract Station parseStationLine(String line);

    public void readData() {
        File file = new File(this.inputFile);
        HashSet<Site> siteList = new HashSet<Site>();
        HashSet<Station> stations = new HashSet<Station>();
        Site site = new Site();
        siteList.add(site);
        Station station = new Station();

        int i = 0, j = 0;
        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {

                // Define Site

                String line = sc.nextLine();
                if (i == 0) {
                    siteList.add(site);
                }
                i++;
                Site sit = parseSiteLine(line);
                if (sit != null) {
                    site = sit;
                    siteList.add(site);
                }


                // Define Station

                Station stn = new Station();
                if (j == 0) {
                    station = stn;
                }
                j++;
                Station s = parseStationLine(line);
                if (stn != null) {
                    station = stn;
                }


                // Define Measurement & Coordinate

                Measurement measurement = parseMeasurementLine(line);
                if (measurement == null) {
                    Coordinate coordinate = parseCoordinateLine(line);
                    if (coordinate != null) {
                        this.addCoordinate(coordinate);
                    }
                } else {
                    measurement.setStation(station.getName());
                    this.addMeasurement(measurement);
                }

            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
