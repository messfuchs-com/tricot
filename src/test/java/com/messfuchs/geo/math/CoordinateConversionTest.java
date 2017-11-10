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
package com.messfuchs.geo.math;

import com.messfuchs.geo.models.GeocentricCoordinate;
import com.messfuchs.geo.models.GeographicCoordinate;
import junit.framework.TestCase;
import org.cts.datum.Ellipsoid;

/**
 *
 * @author jurgen
 */
public class CoordinateConversionTest extends TestCase {
    
    private GeocentricCoordinate geocentricCoord;
    private GeographicCoordinate geographicCoord;
    private Ellipsoid ellipsoid;
    
    public CoordinateConversionTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        geocentricCoord = new GeocentricCoordinate(
            "BEV", 
            4193833.195, 1162617.551, 4646771.124, 
            //4217534.793, 907752.004, 4682895.595,
            //4194424.040, 1162702.484, 4647245.282,
            "MGI-XYZ-EXP"
        );
        
        geographicCoord = new GeographicCoordinate(
            "BEV",
            47+4.0/60+3.09178/3600, 15+29.0/60+40.11787/3600, 492.1248,
            //47.53888, 12.14663, 648.413,
            //47.06712813, 15.49347681, 538.302,  //47+4/60+1.6612/3600, 15+29/60+36.5165/3600, 538.302,
            "MGI-ELL-EXP"
        );
        
        ellipsoid = Constants.BESSEL1841;
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of toGeographic method, of class CoordinateConversion.
     */
    public void testToGeographic() {
        System.out.println("\ntoGeographic");
        
        CoordinateConversion instance = new CoordinateConversion(ellipsoid, 0.0);
        GeographicCoordinate expResult = geographicCoord;
        
        GeographicCoordinate result = instance.toGeographic(geocentricCoord);
        
        System.out.println("Expected: \n" + expResult);
        System.out.println("Got: \n" + result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }

    /**
     * Test of toGeocentric method, of class CoordinateConversion.
     */
    public void testToCartesian() {
        System.out.println("\ntoCartesian");
        
        GeographicCoordinate coord = geographicCoord;
        CoordinateConversion instance = new CoordinateConversion(ellipsoid, 0.0);
        GeocentricCoordinate expResult = geocentricCoord;
        GeocentricCoordinate result = instance.toGeocentric(coord);
        
        System.out.println("Expected: \n" + expResult);
        System.out.println("Got: \n" + result);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
