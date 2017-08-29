package com.messfuchs.geo.models;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public abstract class DataReader extends DataStreamer implements Readable {

    public String inputFile;
    public DataReader (String inputFile) {
        this.inputFile = inputFile;
    }

    public abstract TachyResponse parseResponseLine(String line);

    public abstract LocalCoordinate parseCoordinateLine(String line);

    public abstract Site parseSiteLine(String line);

    public abstract Station parseStationLine(String line);

    public void readData() {
        File file = new File(this.inputFile);
        Site site = null;
        Station station = null;

        try {
            Scanner sc = new Scanner(file);
            while (sc.hasNextLine()) {

                String line = sc.nextLine();

                // Define Site

                Site tempSite = parseSiteLine(line);
                if (tempSite != null) {
                    site = tempSite;
                    this.siteSet.add(site);
                    continue;
                }

                // Define Station

                Station tempStation = parseStationLine(line);
                if (tempStation != null) {

                    if (site == null) {
                        site = new Site();
                        this.siteSet.add(site);
                        System.out.println("No Site definition before Station definition, creating dummy");
                    }

                    station = tempStation;
                    continue;
                }

                // Define TachyMeasurement & LocalCoordinate

                TachyResponse tachyResponse = parseResponseLine(line);
                if (tachyResponse != null) {

                    if (station == null) {
                        station = new Station();
                        System.out.println("No Station definition before Response definition, creating dummy");
                    }

                    TachyMeasurement tachyMeasurement = new TachyMeasurement(station.name, tachyResponse.target, tachyResponse);
                    station.addTachyMeasurement(tachyMeasurement);
                    site.addMeasurement(tachyMeasurement);
                    continue;
                }

                LocalCoordinate coordinate = parseCoordinateLine(line);
                if (coordinate != null) {
                    site.addCoordinate(coordinate);
                    continue;
                }
            }
            sc.close();

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
