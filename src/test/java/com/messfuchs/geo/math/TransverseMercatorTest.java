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

import com.messfuchs.geo.models.GeographicCoordinate;
import com.messfuchs.geo.models.LocalCoordinate;
import junit.framework.TestCase;

import org.cts.datum.PrimeMeridian;
import org.cts.datum.Ellipsoid;

/**
 *
 * @author jurgen
 */
public class TransverseMercatorTest extends TestCase {
    
    private LocalCoordinate localCoord;
    private GeographicCoordinate geographicCoord;
    private Ellipsoid ellipsoid;
    
    public TransverseMercatorTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        
        this.ellipsoid = Ellipsoid.BESSEL1841;
        
        this.localCoord = new LocalCoordinate(
                "BEV",
                -63711.7214, 5214564.6797, 492.1248,
                "GK"
        );
        this.geographicCoord = new GeographicCoordinate(
                "BEV",
                47.0675249, 15.49447719, 492.1248,
                "MGI"
        );
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of toProjected method, of class TransverseMercator.
     */
    public void testToProjected() {
        System.out.println("toProjected");
        GeographicCoordinate coord = this.geographicCoord;
        TransverseMercator instance = new TransverseMercator(
                this.ellipsoid,
                16.0+20.0/60,
                0,
                1, 
                0, 
                0
        );
        instance.primeMeridian = PrimeMeridian.GREENWICH;
        instance.projectionName = "GK";
        LocalCoordinate expResult = this.localCoord;
        LocalCoordinate result = instance.toProjected(coord);
                
        System.out.println("Expected: \n" + expResult);
        System.out.println("Got: \n" + result + "\n");
        
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
    }
    
}
