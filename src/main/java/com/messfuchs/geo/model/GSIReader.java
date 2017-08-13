package com.messfuchs.geo.model;

import java.text.DecimalFormat;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GSIReader extends DataReader {

    private static final String REGEX_WI16_LINE41 = "(?<lineCode>\\*41)(?<lineCount>\\d{4})\\+(?<codeNumber>\\w{16})(?<wiWord>(\\ (?<wiCode>4[2-8]\\.\\.\\.\\.)(?<dataSign>[\\+\\-])(?<dataValue>[\\w\\-\\+\\.]{16}))+)";
    private static final String REGEX_WI16_LINE11 = "(?<lineCode>\\*11)(?<lineCount>\\d{4})\\+(?<pointNumber>\\w{16})(?<wiWord>(\\ (?<wiCode>[\\d]{2}[\\d\\.])(?<compensatorInfo>[03\\.])(?<dataSource>[0-4\\.])(?<dataUnit>[0-8\\.])(?<dataSign>[\\+\\-])(?<dataValue>[\\w\\-\\+\\.]{16}))+)";
    private static final String REGEX_WI16_WIWORD = "(?<wiWord>(\\ (?<wiCode>[\\d]{2}[\\d\\.])(?<compensatorInfo>[03\\.])(?<dataSource>[0-4\\.])(?<dataUnit>[0-8\\.])(?<dataSign>[\\+\\-])(?<dataValue>[\\w\\-\\+\\.]{16})))";
    // private static final String REGEX_WI08 = "(?<lineCode>\\*11)(?<lineCount>\\d{4})\\+(?<pointNumber>\\w{8})(?<wiWord>(\\ (?<wiCode>[\\d]{2}[\\d\\.])(?<compensatorInfo>[03\\.])(?<dataSource>[0-4\\.])(?<dataUnit>[0-8\\.])(?<dataSign>[\\+\\-])(?<dataValue>[\\w\\-\\+\\.]{8}))+)";

    public GSIReader (Site site, String inputFile) {
        super(site, inputFile);
    }

    public static double getScale(int dataUnit) {
        double scale = 1.;
        switch (dataUnit) {
            case 0: scale = 1e-3; break;
            case 1: scale = 1e-3; break;
            case 2: scale = 1e-5; break;
            case 3: scale = 1e-5; break;
            case 4: scale = 1e-4; break;
            case 5: scale = 1e-3; break;
            case 6: scale = 1e-4; break;
            case 7: scale = 1e-4; break;
            case 8: scale = 1e-5; break;
        }
        return scale;
    }

    public double generateValue(String sign, String value, String scale) {
        double scaling = getScale(Integer.parseInt(scale));
        return Math.round(Double.parseDouble(sign + value)*getScale(Integer.parseInt(scale))/scaling)*scaling;
    }

    @Override
    public Measurement parseMeasurementLine(String line) {
        Matcher m = Pattern.compile(REGEX_WI16_LINE11).matcher(line);
        if (m.find()) {
            String gsiLine = m.group();
            String target = m.group("pointNumber").replaceFirst("^0+(?!$)", "");
            // System.out.println("Found REGEX: " + gsiLine);
            m = Pattern.compile(REGEX_WI16_WIWORD).matcher(gsiLine);
            Measurement measurement = new Measurement();
            measurement.setTarget(target);
            while (m.find()) {
                String wiCode = m.group("wiCode").replace(".", "");
                String dataSign = m.group("dataSign");
                String dataValue = m.group("dataValue");
                String dataUnit = m.group("dataUnit");
                switch (wiCode) {
                    case "21":  // Azimuth
                        measurement.setAzimuth(generateValue(dataSign, dataValue, dataUnit)); break;
                    case "22":  // Zenith
                        measurement.setZenith(generateValue(dataSign, dataValue, dataUnit)); break;
                    case "31":  // Slope Distance
                        measurement.setSlopeDistance(generateValue(dataSign, dataValue, dataUnit)); break;
                    case "58":  // Reflector Additional Constant
                        measurement.setReflectorAddConst(generateValue(dataSign, dataValue, dataUnit)); break;
                    case "59":  // PPM
                        measurement.setPpm(generateValue(dataSign, dataValue, dataUnit)); break;
                    case "61":  // Compensator Length
                        measurement.setCompensatorLength(generateValue(dataSign, dataValue, dataUnit)); break;
                    case "62":  // Compensator Cross
                        measurement.setCompensatorLength(generateValue(dataSign, dataValue, dataUnit)); break;
                    case "87":  // Reflector Height
                        measurement.setReflectorHeight(generateValue(dataSign, dataValue, dataUnit)); break;
                    case "88":  // Instrumental Height
                        measurement.setInstrumentalHeight(generateValue(dataSign, dataValue, dataUnit)); break;
                }
            }
            return measurement;
        }
        return null;
    }


    @Override
    public Coordinate parseCoordinateLine(String line) {
        Matcher m = Pattern.compile(REGEX_WI16_LINE11).matcher(line);
        if (m.find()) {
            String gsiLine = m.group();
            String target = m.group("pointNumber").replaceFirst("^0+(?!$)", "");
            // System.out.println("Found REGEX: " + gsiLine);
            m = Pattern.compile(REGEX_WI16_WIWORD).matcher(gsiLine);
            Coordinate coordinate = new Coordinate();
            coordinate.setName(target);
            while (m.find()) {
                String wiCode = m.group("wiCode").replace(".", "");
                String dataSign = m.group("dataSign");
                String dataValue = m.group("dataValue");
                String dataUnit = m.group("dataUnit");
                switch (wiCode) {
                    case "81":  // East
                        coordinate.setEast(generateValue(dataSign, dataValue, dataUnit)); break;
                    case "82":  // North
                        coordinate.setNorth(generateValue(dataSign, dataValue, dataUnit)); break;
                    case "83":  // Height
                        coordinate.setHeight(generateValue(dataSign, dataValue, dataUnit)); break;
                }
            }
            return coordinate;
        }
        return null;
    }


    @Override
    public Site parseSiteLine(String line) {
        Matcher m = Pattern.compile(REGEX_WI16_LINE41).matcher(line);
        if (m.find()) {
            if ( !m.group("codeNumber").equals("1") ) { return null; }
            String gsiLine = m.group();
            Site site = new Site();

            m = Pattern.compile(REGEX_WI16_WIWORD).matcher(gsiLine);
            while (m.find()) {
                String wiCode = m.group("wiCode").replace(".", "");
                String dataSign = m.group("dataSign");
                String dataValue = m.group("dataValue");
                String dataUnit = m.group("dataUnit");
                switch (wiCode) {
                    case "42":
                        site.setName(dataValue);

                }
            }
            return site;
        }
        return null;
    }

    @Override
    public Station parseStationLine(String line) {
        Matcher m = Pattern.compile(REGEX_WI16_LINE41).matcher(line);
        if (m.find()) {
            if ( !m.group("codeNumber").equals("1") ) { return null; }
            String gsiLine = m.group();
            Station station = new Station();

            m = Pattern.compile(REGEX_WI16_WIWORD).matcher(gsiLine);
            while (m.find()) {
                String wiCode = m.group("wiCode").replace(".", "");
                String dataSign = m.group("dataSign");
                String dataValue = m.group("dataValue");
                String dataUnit = m.group("dataUnit");
                switch (wiCode) {
                    case "42":
                        station.setName(dataValue);
                }
            }
            return station;
        }
        return null;
    }
}
