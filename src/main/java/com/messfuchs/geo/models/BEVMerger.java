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
import java.io.Reader;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

/**
 *
 * @author jurgen
 */
public class BEVMerger {
    
    private String inFileMGI, inFileETRS, outFileMerged;
    private Site site;
    
    public BEVMerger (String inFileMGI, String inFileETRS, String outFileMerged) {
        this.inFileMGI = inFileMGI;
        this.inFileETRS = inFileETRS;
        this.outFileMerged = outFileMerged;
        this.site = new Site("BEV Merged");
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
            Reader inFileETRS = new FileReader(this.inFileETRS);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(inFileETRS);
            
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
                
                GeocentricCoordinate tempCoord = new GeocentricCoordinate(
                        pointName, geoX, geoY, geoZ
                );
                
                this.site.addGeocentricCoordinate(tempCoord);
 
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void convertMGI() throws IOException {
        try {
            Reader inFile = new FileReader(this.inFileMGI);
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withFirstRecordAsHeader().parse(inFile);
            
            for (CSVRecord record : records) {
                String pointName = record.get("KG_NUMMER") + record.get("PUNKTNUMMER");
                Double east = Double.parseDouble(record.get("RECHTSWERT"));
                Double north = Double.parseDouble(record.get("HOCHWERT"));
                Double height = 0.0;
                
                LocalCoordinate tempCoord = new LocalCoordinate(
                        pointName, east, north, height
                );
                
                this.site.addLocalCoordinate(tempCoord);
 
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
