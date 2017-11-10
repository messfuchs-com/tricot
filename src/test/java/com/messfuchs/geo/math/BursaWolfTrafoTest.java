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
import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.fail;
import junit.framework.TestCase;
import org.cts.op.transformation.SevenParameterTransformation;
import org.cts.IllegalCoordinateException;

/**
 *
 * @author jurgen
 */
public class BursaWolfTrafoTest  extends TestCase {
    
    public BursaWolfTrafoTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    /**
     * Test of toGeographic method, of class CoordinateConversion.
     */
    public void testMGItoETRS89() {
        System.out.println("toGeographic");
        SevenParameterTransformation trafo = Constants.ETRS89_TO_MGI_TRANSFORMATION;
        GeocentricCoordinate mgiCalc = null;
        double[] mgiCalcList = null;
        
        GeocentricCoordinate etrs89 = new GeocentricCoordinate(
                "BEV", 
                //4197199.204, 1157414.925, 4645810.139,
                4194424.040, 1162702.484, 4647245.282,
                "ETRS89"
        );
        GeocentricCoordinate mgiReal = new GeocentricCoordinate(
                "BEV", 
                //4196608.231, 1157329.909, 4645336.130, 
                4193833.195, 1162617.551, 4646771.124,
                "MGI"
        );
        
        try {
            mgiCalcList = trafo.transform(etrs89.asArray());
            mgiCalc = new GeocentricCoordinate(
            "BEV", mgiCalcList[0], mgiCalcList[1], mgiCalcList[2], "MGI");
        } catch (IllegalCoordinateException e) {
            fail(e.toString());
        }
        
        GeocentricCoordinate mgiDiff = mgiCalc.subtract(mgiReal);
        mgiDiff.setCode("MGI");
        
        assertTrue(mgiDiff.getNorm() <= 0.1);
    }
}
