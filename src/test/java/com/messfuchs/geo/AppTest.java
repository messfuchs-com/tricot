package com.messfuchs.geo;

import com.messfuchs.geo.model.*;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
        assertTrue( true );
    }

    /**
     * Basic Test
     */
    public void testBasic() {
        Site s = new Site("IKNO");
        
        Coordinate c2 = new Coordinate("p2", 111.941, 209.99, -10.41, "MP");
        Coordinate c1 = new Coordinate("p1", -102.293, 201.12, 11.31, "MP");

        Measurement m1 = new Measurement("p1", "p2", 121.1, 99.112, 11.12);
        Measurement m2 = new Measurement("p1", "p2", 122.1, 99.122, 12.12);
        Measurement m3 = new Measurement("p2", "p2", 123.1, 99.132, 13.12);
        Measurement m4 = new Measurement("p3", "p2", 124.1, 99.142, 14.12);
        Measurement m5 = new Measurement("p3", "p2", 125.1, 99.152, 15.12);
        Measurement m6 = new Measurement("p3", "p2", 126.1, 99.162, 16.12);

        s.addCoordinate(c1);
        s.addCoordinate(c2);

        s.addMeasurment(m1);
        s.addMeasurment(m2);
        s.addMeasurment(m3);
        s.addMeasurment(m4);
        s.addMeasurment(m5);
        s.addMeasurment(m6);

        System.out.println(s);

        CSVWriter csvExporter = new CSVWriter(s);
        System.out.println("\n");
        System.out.println(csvExporter.writeData());
        System.out.println("\n");

        GSIWriter gsiExporter = new GSIWriter(s, "src/test/resources/IKNO.GSI");
        System.out.println("\n");
        System.out.println(gsiExporter.writeData());
        System.out.println("\n");

        assertTrue(true);
    }

    /**
     * Test GSIReader
     */
    public void testDataReaderGSI()
    {
        String gsiFilePath = "src/test/resources/21ER.GSI";
        Site site = new Site("21ER");
        GSIReader gsiReader = new GSIReader(site, gsiFilePath);
        gsiReader.readData();

        assertTrue( true );
    }
}
