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

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
// import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

// import android.util.Log;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.lang.StringUtils;
// import java.lang.IllegalArgumentException;

import com.messfuchs.geo.math.Cadastre;
import java.util.Arrays;


import java.util.Locale;
import java.util.logging.Level;
import org.cts.CoordinateDimensionException;


/**
 *
 * @author jurgen
 */

enum outHeaderBEVGeocentric {
    POINTNAME, ECEF_X, ECEF_Y, ECEF_Z, MGI_AUTO_EAST, MGI_AUTO_NORTH, LOCAL_HEIGHT
}
enum outHeaderBEVGeographic {
    POINTNAME, LATITUDE, LONGITUDE, ELEVATION, MGI_AUTO_EAST, MGI_AUTO_NORTH, LOCAL_HEIGHT
}

enum PointType {
    TP, EP
}


public class BEVMerger {
    
    private String inFileMGI, inFileETRS, outFileMerged, outFileMergedReport;
    public File fileMGI, fileETRS, fileMERGE;
    public InputStream isMGI, isETRS;
    public OutputStream osMERGE;
    public Reader rMGI, rETRS;
    private Integer coupledPoints = 0;
    private ArrayList<CoordinatePair> coordinatePairList;
    private final ArrayList<GeocentricCoordinate> geocentricCoordinates;
    private final ArrayList<LocalCoordinate> localCoordinates;
    private final ArrayList<GeographicCoordinate> geographicCoordinates;
    private CoordinateType coordinateType = CoordinateType.Geocentric;
    private Cadastre cadastre = null;
    
    private final ArrayList<String> inputMgiFiles = new ArrayList<>();
    private final ArrayList<String> inputEtrsFiles = new ArrayList<>();

    private static final String TAG = "BEVMerger";

    private static final String NEW_LINE_SEPARATOR = "\n";
    
    private static final Logger LOG = LogManager.getLogger(BEVMerger.class.getName());

    public BEVMerger (String inFileMGI, String inFileETRS, String outFileMerged) {
        this.inFileMGI = inFileMGI;
        this.inFileETRS = inFileETRS;
        this.outFileMerged = outFileMerged;
        this.outFileMergedReport = this.outFileMerged + ".html";
        this.fileMERGE = new File(outFileMerged);
        this.fileETRS = new File(inFileETRS);
        this.fileMGI = new File(inFileMGI);
        String[] files = null;
        
        try {
            // check all Inputs of MGI
            files = this.inFileMGI.split("\\s*;\\s*");
            Arrays.asList(files).stream().forEach(f -> {
                this.inputMgiFiles.add(f);
            });
            this.isMGI = new FileInputStream(this.fileMGI);
        } catch (FileNotFoundException e) {
            System.out.println("In File MGI not found");
            e.printStackTrace();
        }
        try {
            files = this.inFileETRS.split("\\s*;\\s*");
            Arrays.asList(files).stream().forEach(f -> {
                this.inputEtrsFiles.add(f);
            });
            this.isETRS = new FileInputStream(this.fileETRS);
        } catch (FileNotFoundException e) {
            System.out.println("In File ETRS not found");
            e.printStackTrace();
        }

        this.coordinatePairList = new ArrayList<>();
        this.geocentricCoordinates = new ArrayList<>();
        this.localCoordinates = new ArrayList<>();
        this.geographicCoordinates = new ArrayList<>();
    }

    public BEVMerger (InputStream isMGI, InputStream isETRS) {
        this.isETRS = isETRS;
        this.isMGI = isMGI;
        // this.osMERGE = osMERGE;

        this.coordinatePairList = new ArrayList<>();
        this.geocentricCoordinates = new ArrayList<>();
        this.geographicCoordinates = new ArrayList<>();
        this.localCoordinates = new ArrayList<>();
    }

    public BEVMerger (File fileMGI, File fileETRS, File fileMERGE) {
        this.fileMGI = fileMGI;
        this.fileETRS = fileETRS;
        this.fileMERGE = fileMERGE;
        this.coordinatePairList = new ArrayList<>();
        this.geocentricCoordinates = new ArrayList<>();
        this.geographicCoordinates = new ArrayList<>();
        this.localCoordinates = new ArrayList<>();
    }

    public CoordinateType getCoordinateType() {
        return coordinateType;
    }

    public void setCoordinateType(CoordinateType coordinateType) {
        this.coordinateType = coordinateType;
    }

    public int getIdenticPoints() {
        return this.coordinatePairList.size();
    }
    
    public String parseDoubleMM(Double d) {
        // NumberFormat nf = NumberFormat.getNumberInstance(Locale.GERMAN);
        Double dMM = null;
        try {
            dMM =  Double.parseDouble(String.format(Locale.US, "%.03f", d));
        } 
        finally {
            if (dMM == null) {
                return "";
            }
            return dMM.toString();
        }
    }
    
    public ArrayList<CoordinatePair> getCoordinatePairList() {
        return this.coordinatePairList;
    }
    
    public Double parseDouble(String s) {
        if (s == null || s.isEmpty() || s.equals("")) {
            return null;
        } else {
            return Double.parseDouble(s);
        }
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

    public String createEpName (CSVRecord record) {
        // return record.get("KG_NUMMER") + "_" + record.get("PUNKTNUMMER") + "_" + record.get("KENNZEICHEN");
        return "EP_" + StringUtils.leftPad(record.get("KG_NUMMER"), 5, "0") + StringUtils.leftPad(record.get("PUNKTNUMMER"), 5, "_") + StringUtils.leftPad(record.get("KENNZEICHEN"), 3, "_");
    }
    
    public String createTpName (CSVRecord record) {
        //return record.get("OeK50_BMN_NR") + "_" + record.get("PUNKTNUMMER") + "_" + record.get("KENNZEICHEN");
        return "TP_" + StringUtils.leftPad(record.get("OeK50_BMN_NR"), 5, "0") + StringUtils.leftPad(record.get("PUNKTNUMMER"), 5, "_") + StringUtils.leftPad(record.get("KENNZEICHEN"), 3, "_");
    }
    
    public void convertETRS() throws IOException {
        this.inputEtrsFiles.stream().forEach(f -> {
            try {
                LOG.debug("[BEVMerger.java::convertETRS] Processing file " + f);
                FileInputStream fs = new FileInputStream(f);
                try (BufferedReader inFile = new BufferedReader(new InputStreamReader(fs))) {
                    Iterable<CSVRecord> records = CSVFormat.RFC4180.withDelimiter(';').withFirstRecordAsHeader().parse(inFile);
                    
                    for (CSVRecord record : records) {
                        Double lat = null, lon = null;
                        String pointName = null;
                        String pointType = record.get("PUNKTTYP");
                        if (pointType.equals("EP")) {
                            pointName = this.createEpName(record);
                        } else if (pointType.equals("TP")) {
                            pointName = this.createTpName(record);
                        }
                        LOG.debug("Parsing ETRS '" + pointName + "'");
                        Double geoX = this.parseDouble(record.get("X"));
                        Double geoY = this.parseDouble(record.get("Y"));
                        Double geoZ = this.parseDouble(record.get("Z"));
                        // Double east = Double.parseDouble(record.get("RW"));
                        // Double north = Double.parseDouble(record.get("HW"));
                        Double height = this.parseDouble(record.get("HOEHE"));
                        Double undul_grs80 = this.parseDouble(record.get("UNDULATION_GRS80"));
                        Double undul_bessel = this.parseDouble(record.get("UNDULATION_BESSEL"));
                        if (record.isMapped("BREITE")) {
                            lat = this.parseDouble(record.get("BREITE"));
                        } else {
                            LOG.debug("Latitude not mapped");
                        }
                        if (record.isMapped("LAENGE")) {
                            lon = this.parseDouble(record.get("LAENGE"));
                        } else {
                            LOG.debug("Longitude not mapped");
                        }
                        
                        Double elev = null;
                        if ( height != null && undul_grs80 != null && undul_bessel != null && !height.isNaN() && !undul_grs80.isNaN() && !undul_bessel.isNaN() ) {
                            // LOG.debug("Height: " + height);
                            // LOG.debug("N_GRS80: " + undul_grs80);
                            // LOG.debug("N_BESSEL: " + undul_bessel);
                            elev = height + undul_grs80 + undul_bessel;
                        }
                        GeocentricCoordinate tempGeoce = new GeocentricCoordinate(
                                pointName, geoX, geoY, geoZ
                        );
                        GeographicCoordinate tempGeogr = new GeographicCoordinate(
                                pointName, lat, lon, elev
                        );
                        
                        this.geocentricCoordinates.add(tempGeoce);
                        this.geographicCoordinates.add(tempGeogr);
                        LOG.debug("Added ETRS '" + tempGeoce.name + "'");
                    }
                }
            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(BEVMerger.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(BEVMerger.class.getName()).log(Level.SEVERE, null, ex);
            } 
        });
    }

    public void convertMGI() throws IOException {            
        this.inputMgiFiles.stream().forEach(f -> {
            try {
                LOG.debug("[BEVMerger.java::convertMGI] Processing file " + f);
                FileInputStream fs = new FileInputStream(f);
                try (BufferedReader inFile = new BufferedReader(new InputStreamReader(fs))) {
                    Iterable<CSVRecord> records = CSVFormat.RFC4180.withDelimiter(';').withFirstRecordAsHeader().parse(inFile);
                    
                    for (CSVRecord record : records) {
                        // System.out.println("record: " + record);
                        String pointName = null;
                        String pointType = record.get("PUNKTTYP");
                        if (pointType.equals("EP")) {
                            pointName = this.createEpName(record);
                        } else if (pointType.equals("TP")) {
                            pointName = this.createTpName(record);
                        }
                        LOG.debug("Parsing MGI '" + pointName + "'");
                        Double east = Double.parseDouble(record.get("RECHTSWERT"));
                        Double north = Double.parseDouble(record.get("HOCHWERT"));
                        Double height = this.parseDouble(record.get("HOEHE"));
                        
                        LocalCoordinate tempCoord = new LocalCoordinate(
                                pointName, east, north, height
                        );
                        
                        this.localCoordinates.add(tempCoord);
                        // System.out.println("Added MGI '" + tempGeoce.name + "'");
                        
                    }
                }

            } catch (FileNotFoundException ex) {
                java.util.logging.Logger.getLogger(BEVMerger.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                java.util.logging.Logger.getLogger(BEVMerger.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }
    
    public Integer getCoupledPoints() {
        return this.coupledPoints;
    }
    
    public String convert() throws IOException {
        
        String resultText = "";
        int identicalPoints = 0;
        
        LOG.info("ETRS Coordinate Type: " + this.getCoordinateType());

        try {
            this.convertMGI();
        } catch (java.lang.IllegalArgumentException e) {
            LOG.error("Error with MGI File: " + e.toString());
            System.out.println("Error with MGI File: " + e.toString());
            e.printStackTrace();
            throw e;
        }
        
        try {
            this.convertETRS();
        } catch (java.lang.IllegalArgumentException e) {
            LOG.error("Error with ETRS File: " + e.toString());
            System.out.println("Error with ETRS File: " + e.toString());
            e.printStackTrace();
            throw e;
        }
        
        // Update Local Height
        this.coordinatePairList = this.getCoordinatePairs();
        
        if (this.coordinatePairList.isEmpty()) {
            resultText = "No valid data found";
            System.out.println("No valid data found");
            LOG.error("No valid data found");
            return resultText;
        }
        
        // thx to https://examples.javacodegeeks.com/core-java/apache/commons/csv-commons/writeread-csv-files-with-apache-commons-csv-example/

        FileWriter fileWriter = null;
        CSVPrinter csvFilePrinter = null;
        CSVFormat csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR).withDelimiter(';').withHeader(outHeaderBEVGeocentric.class);

        if (this.coordinateType.equals(CoordinateType.Geographic)) {
            csvFileFormat = CSVFormat.DEFAULT.withRecordSeparator(NEW_LINE_SEPARATOR).withDelimiter(';').withHeader(outHeaderBEVGeographic.class);
        }
        
        try {
            
            // initialize FileWriter object
            // fileWriter = new FileWriter(this.outFileMerged);
            fileWriter = new FileWriter(this.fileMERGE);
            
            // initialize CSVPrinter object
            csvFilePrinter = new CSVPrinter(fileWriter, csvFileFormat);
            for (CoordinatePair cp: this.coordinatePairList) {
                java.util.List coordinateRecord = new java.util.ArrayList();
                coordinateRecord.add(cp.getLocal().getName());
                if (this.coordinateType.equals(CoordinateType.Geocentric)) {
                    coordinateRecord.add(this.parseDoubleMM(cp.getGeocentric().getX()));
                    coordinateRecord.add(this.parseDoubleMM(cp.getGeocentric().getY()));
                    coordinateRecord.add(this.parseDoubleMM(cp.getGeocentric().getZ()));
                }
                if (this.coordinateType.equals(CoordinateType.Geographic) && (cp.getGeographic() != null)) {
                    coordinateRecord.add(this.parseDouble(cp.getGeographic().getLat().toString()));
                    coordinateRecord.add(this.parseDouble(cp.getGeographic().getLon().toString()));
                    coordinateRecord.add(this.parseDoubleMM(cp.getGeographic().getElev()));                    
                }
                
                coordinateRecord.add(this.parseDoubleMM(cp.getLocal().getEast()));
                coordinateRecord.add(this.parseDoubleMM(cp.getLocal().getNorth()));
                coordinateRecord.add(this.parseDoubleMM(cp.getLocal().getHeight()));
                csvFilePrinter.printRecord(coordinateRecord);
                identicalPoints++;
                LOG.debug("Added " + cp.getName());
            }
            resultText = "Merged " + identicalPoints + " identical Points";
            this.coupledPoints = identicalPoints;

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
    
    public java.util.ArrayList<CoordinatePair> getCoordinatePairs() {
        java.lang.StringBuilder s = new java.lang.StringBuilder();
        java.util.Formatter f = new java.util.Formatter(s);
        
        java.util.ArrayList<CoordinatePair> arrayListCoordinatePair = new java.util.ArrayList<>();
        
        for (LocalCoordinate local: this.localCoordinates) {
            for (GeocentricCoordinate geocentric: this.geocentricCoordinates) {
                // System.out.println("Compare " + local.getName() + " == " + geocentric.getName() + " ? " + local.getName().equals(geocentric.getName()));
                if (!local.getName().equals(geocentric.getName())) continue;
                GeographicCoordinate geographic = new GeographicCoordinate(geocentric.getName());
                for (GeographicCoordinate tg: this.geographicCoordinates) {
                    if (tg.getName().equals(local.getName())) {
                        geographic = tg;
                    }
                }

                if (local.getHeight() == null) {
                    local.setHeight(geographic.elev);
                }
                
                arrayListCoordinatePair.add(new CoordinatePair(local, geocentric, geographic));
                
                f.format("%s;", local.getName());
                if (this.coordinateType.equals(CoordinateType.Geocentric)) {
                    f.format("%.3f;%.3f;%.3f;", geocentric.getX(), geocentric.getY(), geocentric.getZ());
                } else {
                    f.format("%.8f;%.8f;%.8f;", geographic.getLat(), geographic.getLon(), geographic.getElev());
                }
                f.format("%.3f;%.3f;%.3f\n", local.getEast(), local.getNorth(), local.getHeight());
            }
        }
        
        System.out.println(s.toString());
        return arrayListCoordinatePair;
    }
    
    public void calculateLocalisation() throws CoordinateDimensionException, Exception {
        this.cadastre = new Cadastre();
        this.cadastre.setReportOutputFile(this.outFileMergedReport);
        this.getCoordinatePairs().stream()
            .forEach(cp -> {
                this.cadastre.addCoordinatePair(cp);
            });
        try {
            this.cadastre.execute();
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(BEVMerger.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
