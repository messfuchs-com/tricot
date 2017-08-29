/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.messfuchs.geo.models;

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
    public Coordinate parseCoordinateLine(String line) {
        // TODO ..
        return null;
    }

    @Override
    public Site parseSiteLine(String line) {
        return null;
    }
}
