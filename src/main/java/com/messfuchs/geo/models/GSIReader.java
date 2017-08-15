package com.messfuchs.geo.models;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class GSIReader extends DataReader {

    private static final String REGEX_WI16_LINE41 = "(?<lineCode>\\*41)(?<lineCount>\\d{4})\\+(?<contextInfo>\\w{16})(?<wiWord>(\\ (?<wiCode>4[2-8]\\.{4})(?<dataSign>[\\+\\-])(?<dataValue>[\\w\\-\\+\\.]{16}))+)";
    private static final String REGEX_WI16_LINE11 = "(?<lineCode>\\*11)(?<lineCount>\\d{4})\\+(?<contextInfo>\\w{16})(?<wiWord>(\\ (?<wiCode>[\\d]{2}[\\d\\.])(?<compensatorInfo>[03\\.])(?<dataSource>[0-4\\.])(?<dataUnit>[0-8\\.])(?<dataSign>[\\+\\-])(?<dataValue>[\\w\\-\\+\\.]{16}))+)";
    private static final String REGEX_WI16_WIWORD = "(?<wiWord>(\\ (?<wiCode>[\\d]{2}[\\d\\.])(?<compensatorInfo>[03\\.])(?<dataSource>[0-4\\.])(?<dataUnit>[0-8\\.])(?<dataSign>[\\+\\-])(?<dataValue>[\\w\\-\\+\\.]{16})))";
    // private static final String REGEX_WI08 = "(?<lineCode>\\*11)(?<lineCount>\\d{4})\\+(?<contextInfo>\\w{8})(?<wiWord>(\\ (?<wiCode>[\\d]{2}[\\d\\.])(?<compensatorInfo>[03\\.])(?<dataSource>[0-4\\.])(?<dataUnit>[0-8\\.])(?<dataSign>[\\+\\-])(?<dataValue>[\\w\\-\\+\\.]{8}))+)";

    public GSIReader (String inputFile) {
        super(inputFile);
    }

    public static double getScale(String dataUnit) {
        double scale = 1e+0;
        switch (dataUnit) {
            case "0": scale = 1e-3; break;
            case "1": scale = 1e-3; break;
            case "2": scale = 1e-5; break;
            case "3": scale = 1e-5; break;
            case "4": scale = 1e-4; break;
            case "5": scale = 1e-3; break;
            case "6": scale = 1e-4; break;
            case "7": scale = 1e-4; break;
            case "8": scale = 1e-5; break;
            default: break;
        }
        return scale;
    }

    public double generateValue(String sign, String value, String scale) {
        double scaling = getScale(scale);
        return Math.round(Double.parseDouble(sign + value)*scaling)/scaling;
    }

    @Override
    public TachyResponse parseResponseLine(String line) {
        Matcher m = Pattern.compile(REGEX_WI16_LINE11).matcher(line);
        if (m.find()) {

            String contextInfo = m.group("contextInfo").replaceFirst("^0+(?!$)", "");
            String gsiLine = m.group();
            m = Pattern.compile(REGEX_WI16_WIWORD).matcher(gsiLine);

            TachyResponse tachyResponse = new TachyResponse(contextInfo);

            while (m.find()) {
                String wiCode = m.group("wiCode").replace(".", "");
                String dataSign = m.group("dataSign");
                String dataValue = m.group("dataValue");
                String dataUnit = m.group("dataUnit");
                Double value = null;
                switch (wiCode) {
                    case "21":  // Azimuth
                        value = generateValue(dataSign, dataValue, dataUnit);
                        tachyResponse.azimuth = new Angle(value); break;
                    case "22":  // Zenith
                        value = generateValue(dataSign, dataValue, dataUnit);
                        tachyResponse.zenith =  new Angle(value); break;
                    case "31":  // Slope Distance
                        value = generateValue(dataSign, dataValue, dataUnit);
                        tachyResponse.slopeDistance = value; break;
                    case "58":  // Reflector Additional Constant
                        value = generateValue(dataSign, dataValue, dataUnit);
                        tachyResponse.reflectorAddConst = value; break;
                    case "59":  // PPM
                        value = generateValue(dataSign, dataValue, dataUnit);
                        tachyResponse.ppm = value; break;
                    case "61":  // Compensator Length
                        value = generateValue(dataSign, dataValue, dataUnit);
                        tachyResponse.compensatorCross = new Angle(value); break;
                    case "62":  // Compensator Cross
                        value = generateValue(dataSign, dataValue, dataUnit);
                        tachyResponse.compensatorLength = new Angle(value); break;
                    case "87":  // Reflector Height
                        value = generateValue(dataSign, dataValue, dataUnit);
                        tachyResponse.reflectorHeight = value; break;
                    case "88":  // Instrumental Height
                        value = generateValue(dataSign, dataValue, dataUnit);
                        tachyResponse.instrumentalHeight = value; break;
                    case "71":  // Code
                        tachyResponse.code = dataValue; break;
                }
            }
            return tachyResponse;
        }
        return null;
    }


    @Override
    public Coordinate parseCoordinateLine(String line) {
        Matcher m = Pattern.compile(REGEX_WI16_LINE11).matcher(line);
        if (m.find()) {

            String contextInfo = m.group("contextInfo").replaceFirst("^0+(?!$)", "");
            String gsiLine = m.group();
            m = Pattern.compile(REGEX_WI16_WIWORD).matcher(gsiLine);

            Coordinate coordinate = new Coordinate();
            coordinate.setName(contextInfo);
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

            String contextInfo = m.group("contextInfo").replaceFirst("^0+(?!$)", "");
            String gsiLine = m.group();

            if ( !contextInfo.equals("1") ) { return null; }
            Site site = new Site();

            m = Pattern.compile(REGEX_WI16_WIWORD).matcher(gsiLine);
            while (m.find()) {
                String wiCode = m.group("wiCode").replace(".", "");
                String dataSign = m.group("dataSign");
                String dataValue = m.group("dataValue").replaceFirst("^0+(?!$)", "");
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

            String contextInfo = m.group("contextInfo").replaceFirst("^0+(?!$)", "");
            String gsiLine = m.group();

            if ( !contextInfo.equals("11") ) { return null; }
            Station station = new Station();

            m = Pattern.compile(REGEX_WI16_WIWORD).matcher(gsiLine);
            while (m.find()) {
                String wiCode = m.group("wiCode").replace(".", "");
                String dataSign = m.group("dataSign");
                String dataValue = m.group("dataValue").replaceFirst("^0+(?!$)", "");
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
