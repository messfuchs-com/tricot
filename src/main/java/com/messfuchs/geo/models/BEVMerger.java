/*
 * Copyright 2017 jurgen.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.messfuchs.geo.models;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Locale;
import java.text.DecimalFormat;
import java.text.NumberFormat;

/**
 *
 * @author jurgen
 */

enum outHeaderBEV {
        POINTNAME, ECEF_X, ECEF_Y, ECEF_Z, MGI_AUTO_EAST, MGI_AUTO_NORTH, LOCAL_HEIGHT
}


public class BEVMerger {
    
    private String inFileMGI, inFileETRS, outFileMerged;
    private Site site;
    
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static final Logger LOG = LogManager.getLogger(BEVMerger.class);

    public BEVMerger (String inFileMGI, String inFileETRS, String outFileMerged) {
        this.inFileMGI = inFileMGI;
        this.inFileETRS = inFileETRS;
        this.outFileMerged = outFileMerged;
        this.site = new Site("BEV Merged");
    }
    
    public String parseDoubleMM(Double d) {
        NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        Double dMM = Double.parseDouble(String.format(Locale.US, "%.03f", d));
        return dMM.toString();
    }

    public String getInFileMGI() {
        return inFileMGI;
    }

    public void setInFileMGI(String inFileMGI) {
        this.inFileMGI = inFileMGI;
    }

    public String getInFileETRS() {
        return inFileETRS;
    }

    public void setInFileETRS(String inFileETRS) {
        this.inFileETRS = inFileETRS;
    }

    public String getOutFileMerged() {
        return outFileMerged;
    }

    public void setOutFileMerged(String outFileMerged) {
        this.outFileMerged = outFileMerged;
    }

    public void convertETRS() throws IOException {
        try {
            Reader inFileETRS = new FileReader(this.getInFileETRS());
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withDelimiter(';').withFirstRecordAsHeader().parse(inFileETRS);
            
            for (CSVRecord record : records) {
                String pointName = record.get("KG_NUMMER") + "-" + record.get("PUNKTNUMMER") + "-" + record.get("KENNZEICHEN");
                Double geoX = Double.parseDouble(record.get("X"));
                Double geoY = Double.parseDouble(record.get("Y"));
                Double geoZ = Double.parseDouble(record.get("Z"));
                Double east = Double.parseDouble(record.get("RW"));
                Double north = Double.parseDouble(record.get("HW"));
                Double height = Double.parseDouble(record.get("HOEHE"));
                Double undul_grs80 = Double.parseDouble(record.get("UNDULATION_GRS80"));
                Double undul_bessel = Double.parseDouble(record.get("UNDULATION_BESSEL"));
                Double elev = height + undul_grs80 + undul_bessel;
                
                GeocentricCoordinate tempCoord = new GeocentricCoordinate(
                        pointName, geoX, geoY, geoZ, elev
                );
                
                this.site.addGeocentricCoordinate(tempCoord);
 
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void convertMGI() throws IOException {
        try {
            Reader inFile = new FileReader(this.getInFileMGI());
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withDelimiter(';').withFirstRecordAsHeader().parse(inFile);
            
            for (CSVRecord record : records) {
                String pointName = record.get("KG_NUMMER") + "-" + record.get("PUNKTNUMMER") + "-" + record.get("KENNZEICHEN");
                Double east = Double.parseDouble(record.get("RECHTSWERT"));
                Double north = Double.parseDouble(record.get("HOCHWERT"));
                Double height = null;
                
                LocalCoordinate tempCoord = new LocalCoordinate(
                        pointName, east, north, height
                );
                
                this.site.addLocalCoordinate(tempCoord);
 
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    
    public String convert() throws IOException {
        this.convertETRS();
        this.convertMGI();
        String resultText = "";
        int identicalPoints = 0;
        
        // Update Local Height
        if (this.site.getCoordinatePairs().isEmpty()) {
            resultText = "No valid data found";
            return resultText;
        }
        
        // thx to https://examples.javacodegeeks.com/core-java/apache/commons/csv-commons/writeread-csv-files-with-apache-commons-csv-example/
        
        java.util.ArrayList<CoordinatePair> listCoordinatePair = this.site.getCoordinatePairs();
        
        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR).withDelimiter(';').withHeader(outHeaderBEV.class);
        
        try {
            
            // initialize FileWriter object
            fileWriter = new FileWriter(this.outFileMerged);
            
            // initialize CSVPrinter object
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            for (CoordinatePair cp: listCoordinatePair) {
                java.util.List coordinateRecord = new java.util.ArrayList();
                coordinateRecord.add(cp.getLocal().getName());
                coordinateRecord.add(this.parseDoubleMM(cp.getGeocentric().getX()));
                coordinateRecord.add(this.parseDoubleMM(cp.getGeocentric().getY()));
                coordinateRecord.add(this.parseDoubleMM(cp.getGeocentric().getZ()));
                coordinateRecord.add(this.parseDoubleMM(cp.getLocal().getEast()));
                coordinateRecord.add(this.parseDoubleMM(cp.getLocal().getNorth()));
                coordinateRecord.add(this.parseDoubleMM(cp.getLocal().getHeight()));
                csvFilePrinter.printRecord(coordinateRecord);
                identicalPoints++;
                LOG.debug("Added " + cp.toString());
            }
            resultText = "Merged " + identicalPoints + " identical Points";

            LOG.info("CSV file was created successfully !!!");
         
        } catch (Exception e) {
             LOG.error("Error while Writing CSV");
             e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
                csvFilePrinter.close();
            } catch (IOException e) {
                LOG.error("Error while flushing/closing fileWriter/csvPrinter");
                e.printStackTrace();
            }
        }  
        return resultText;
    }
}
