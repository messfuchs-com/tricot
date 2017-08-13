package com.messfuchs.geo.model;

public class CSVWriter extends DataWriter {

    public char quote, delimiter;

    public CSVWriter(Site site, String outputFileCoordinates, String outputFileMeasurements, char quote, char delimiter) {
        super(site, outputFileCoordinates, outputFileMeasurements);
        this.quote = quote;
        this.delimiter = delimiter;
    }

    public CSVWriter(Site site) {
        this(
                site,
                site.getName().toLowerCase() + "_crd.csv",
                site.getName().toLowerCase() + "_mea.csv",
                '"',
                ';'
        );
    }

    public String writeData() {
        // Exporting Coordinates & Measurements
        return this.writeDataCoordinates() + "\n" + this.writeDataMeasurements();
    }

    public String writeDataCoordinates() {
        StringBuilder s = new StringBuilder("\"PNR\";\"RW\";\"HW\";\"H\";\"CODE\";");
        for (Coordinate coordinate: this.getSite().getCoordinates()) {
            s.append("\n");
            s.append(this.quote + coordinate.getName() + this.quote + this.delimiter);
            s.append(coordinate.getEast().toString() + this.delimiter);
            s.append(coordinate.getNorth().toString() + this.delimiter);
            String h = (coordinate.getHeight() ==  null) ? "" : coordinate.getHeight().toString();
            s.append(h + this.delimiter);
            String c = ((coordinate.getCode() == null) ? "" : this.quote + coordinate.getCode() + this.quote);
            s.append(c + this.delimiter);
        }
        return s.toString();
    }

    public String writeDataMeasurements() {
        StringBuilder s = new StringBuilder("\"STN\";\"TAR\";\"HZ\";\"V\";\"SD\";\"IH\";\"RH\";\"RC\";\"PPM\";");
        for (Measurement measurement: this.getSite().getMeasurements()) {
            s.append("\n");
            s.append(this.quote + measurement.getStation() + this.quote + this.delimiter);
            s.append(this.quote + measurement.getTarget() + this.quote + this.delimiter);
            s.append(measurement.getAzimuth().toString() + this.delimiter);
            s.append(measurement.getZenith().toString() + this.delimiter);
            s.append(measurement.getSlopeDistance().toString() + this.delimiter);
            s.append(measurement.getInstrumentalHeight().toString() + this.delimiter);
            s.append(measurement.getReflectorHeight().toString() + this.delimiter);
            s.append(measurement.getReflectorAddConst().toString() + this.delimiter);
            s.append(measurement.getPpm().toString() + this.delimiter);
        }
        return s.toString();
    }
}
