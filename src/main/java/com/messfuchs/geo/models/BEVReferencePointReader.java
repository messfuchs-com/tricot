/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.messfuchs.geo.models;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author jurgen
 */
public class BEVReferencePointReader extends DataReader {
    
    private static final String REGEX_EP = "(?<lineCode>\\*41)(?<lineCount>\\d{4})\\+(?<contextInfo>\\w{16})(?<wiWord>(\\ (?<wiCode>4[2-8]\\.{4})(?<dataSign>[\\+\\-])(?<dataValue>[\\w\\-\\+\\.]{16}))+)";

    public BEVReferencePointReader (String inputFile) {
        super(inputFile);
    }
    
    @Override
    public Station parseStationLine(String line) {
        return null;
    }
    
    @Override
    public TachyResponse parseResponseLine(String line) {
        return null;
    }

    @Override
    public LocalCoordinate parseCoordinateLine(String line) {
        return null;
    }

    @Override
    public Site parseSiteLine(String line) {
        return null;
    }
    
    @Override
    public void readData() {
       
        try {
            Reader inFile = new FileReader(this.inputFile);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(inFile);
            for (CSVRecord record : records) {
                String pointName = record.get("KG_NUMMER") + record.get("PUNKTNUMMER");
                Double geoX = Double.parseDouble(record.get("X"));
                Double geoY = Double.parseDouble(record.get("Y"));
                Double geoZ = Double.parseDouble(record.get("Z"));
                Double east = Double.parseDouble(record.get("RW"));
                Double north = Double.parseDouble(record.get("HW"));
                Double elev = Double.parseDouble(record.get("HOEHE"));
                Double undul = Double.parseDouble(record.get("UNDULATION"));
                Double height = elev + undul;
                
                
            }
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
    }
}
