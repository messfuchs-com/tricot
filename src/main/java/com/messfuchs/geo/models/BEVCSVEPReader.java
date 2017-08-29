/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.messfuchs.geo.models;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.csv.CSVFormat;


/**
 *
 * @author jurgen
 */
public class BEVCSVEPReader {
    
    public void readData () throws FileNotFoundException {
    
        Reader inFile = new FileReader("path/to/file.csv");

        try {
            Iterable<CSVRecord> records = CSVFormat.RFC4180.withHeader("ID", "CustomerNo", "Name").parse(inFile);

            for (CSVRecord record : records) {
                String id = record.get("ID");
                String customerNo = record.get("CustomerNo");
                String name = record.get("Name");
            }
        } catch (FileNotFoundException e) {
                e.printStackTrace();
        }
    
    }
     
}
