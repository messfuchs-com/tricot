package com.messfuchs.geo.models;

public class CSVWriter extends DataWriter {

    public char quote, delimiter;

    public CSVWriter(String outputFileCoordinates, String outputFileMeasurements, char quote, char delimiter) {
        super(outputFileCoordinates, outputFileMeasurements);
        this.quote = quote;
        this.delimiter = delimiter;
    }

    public CSVWriter() {
        this(
            "csvWriter_crd.csv",
            "csvWriter_mea.csv",
            '"',
            ';'
        );
    }

    public String writeData() {
        // Exporting Coordinates & Measurements
        StringBuilder s = new StringBuilder();
        s.append("\n---- Write Coordinates\n");
        s.append(this.writeDataCoordinates()+"\n");
        s.append("\n---- Write Measurements\n");
        s.append(this.writeDataMeasurements()+"\n");
        return s.toString();
    }

    public String writeDataCoordinates() {
        StringBuilder s = new StringBuilder("\"PNR\";\"RW\";\"HW\";\"H\";\"CODE\";");
        for (Site site: this.siteSet) {
            for (LocalCoordinate coordinate : site.getLocalCoordinateSet()) {
                s.append("\n");
                s.append(this.quote + coordinate.getName() + this.quote + this.delimiter);
                s.append(coordinate.getEast().toString() + this.delimiter);
                s.append(coordinate.getNorth().toString() + this.delimiter);
                String h = (coordinate.getHeight() == null) ? "" : coordinate.getHeight().toString();
                s.append(h + this.delimiter);
                String c = ((coordinate.getCode() == null) ? "" : this.quote + coordinate.getCode() + this.quote);
                s.append(c + this.delimiter);
            }
        }
        return s.toString();
    }

    public String writeDataMeasurements() {
        StringBuilder s = new StringBuilder("\"STN\";\"TAR\";\"HZ\";\"V\";\"SD\";\"IH\";\"RH\";\"RC\";\"PPM\";");
        for (Site site: this.siteSet) {
            for (TachyMeasurement tachyMeasurement : site.getMeasurementSet()) {
                s.append("\n");
                s.append(this.quote + tachyMeasurement.station + this.quote + this.delimiter);
                s.append(this.quote + tachyMeasurement.target + this.quote + this.delimiter);
                s.append(tachyMeasurement.response.azimuth.toString() + this.delimiter);
                s.append(tachyMeasurement.response.zenith.toString() + this.delimiter);
                s.append(tachyMeasurement.response.slopeDistance.toString() + this.delimiter);
                s.append(tachyMeasurement.response.instrumentalHeight.toString() + this.delimiter);
                s.append(tachyMeasurement.response.reflectorHeight.toString() + this.delimiter);
                s.append(tachyMeasurement.response.reflectorAddConst.toString() + this.delimiter);
                s.append(tachyMeasurement.response.ppm.toString() + this.delimiter);
            }
        }
        return s.toString();
    }
}
