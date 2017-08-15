package com.messfuchs.geo;

import com.messfuchs.geo.models.*;
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

        TachyMeasurement m1 = new TachyMeasurement("p1", "p2", new TachyResponse("p2", 121.1, 99.112, 11.12));
        TachyMeasurement m2 = new TachyMeasurement("p1", "p2", new TachyResponse("p2", 122.1, 99.122, 12.12));
        TachyMeasurement m3 = new TachyMeasurement("p2", "p2", new TachyResponse("p2", 123.1, 99.132, 13.12));
        TachyMeasurement m4 = new TachyMeasurement("p3", "p2", new TachyResponse("p2", 124.1, 99.142, 14.12));
        TachyMeasurement m5 = new TachyMeasurement("p3", "p2", new TachyResponse("p2", 125.1, 99.152, 15.12));
        TachyMeasurement m6 = new TachyMeasurement("p3", "p2", new TachyResponse("p2", 126.1, 99.162, 16.12));

        s.addCoordinate(c1);
        s.addCoordinate(c2);

        s.addMeasurement(m1);
        s.addMeasurement(m2);
        s.addMeasurement(m3);
        s.addMeasurement(m4);
        s.addMeasurement(m5);
        s.addMeasurement(m6);

        System.out.println("Create Site: " + s);

        CSVWriter csvWriter = new CSVWriter();
        csvWriter.addSite(s);
        System.out.println("\n\n--- Generate CSV\n");
        System.out.println(csvWriter.writeData());
        System.out.println("\n");

        GSIWriter gsiWriter = new GSIWriter( "src/test/resources/IKNO.GSI");
        gsiWriter.addSite(s);
        System.out.println("\n\n--- Generate GSI\n");
        System.out.println(gsiWriter.writeData());
        System.out.println("\n");

        assertTrue(true);
    }

    /**
     * Test GSIReader
     */
    public void testDataReaderGSI()
    {
        String gsiFilePath = "src/test/resources/21ER.GSI";
        GSIReader gsiReader = new GSIReader(gsiFilePath);
        gsiReader.readData();
        System.out.println(gsiReader.siteSet);

        assertTrue( true );
    }
}
