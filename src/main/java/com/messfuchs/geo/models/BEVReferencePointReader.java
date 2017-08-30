/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.messfuchs.geo.models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author jurgen
 */
public class BEVReferencePointReader {
    
    public String inFileNameMGI, inFileNameETRS;
    private Site site;
    

    public BEVReferencePointReader (String inFileNameMGI, String inFileNameETRS) {
        this.inFileNameETRS = inFileNameETRS;
        this.inFileNameMGI = inFileNameMGI;
        this.site.setName("BEV Import");
    }
    
    public void readDataETRS() throws IOException {
        try {
            Reader inFile = new FileReader(this.inFileNameETRS);
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
    
    public void readData() throws IOException {
       
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
