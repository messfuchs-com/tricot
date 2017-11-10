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

import com.messfuchs.geo.models.BEVMerger;
import com.messfuchs.geo.models.GeocentricCoordinate;
import com.messfuchs.geo.math.TransverseMercator;

import junit.framework.TestCase;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.cts.op.transformation.SevenParameterTransformation;
// import org.cts.op.Geocentric2Geographic;
import org.cts.IllegalCoordinateException;
import org.cts.CoordinateDimensionException;


/**
 *
 * @author jurgen
 */
public class CadastreTest extends TestCase {
    
    private Cadastre cadastre;
    private BEVMerger bevMerger;
    
    private final String resourcePath = "src/test/resources";
    private static final Logger LOG = LogManager.getLogger(CadastreTest.class.getName());

    public CadastreTest(String testName) {
        super(testName);
    }
    
    @Override
    protected void setUp() throws Exception {
        super.setUp();
        this.bevMerger = new BEVMerger(
            this.resourcePath + "/" + "0004769401_MGI_PUNKTART_EP.csv",
            this.resourcePath + "/" + "0004769401_ETRS_PUNKTART_EP.csv",
            this.resourcePath + "/" + "0004769401_MERGE_PUNKTART_EP.csv"
        );
        this.cadastre = new Cadastre();
    }
    
    public void testPlaneSimilarity() throws IOException, java.lang.IllegalArgumentException, CoordinateDimensionException {
        LOG.debug("Load Data from File");
        try {
            String bmText = this.bevMerger.convert();
            LOG.debug(bmText);
        } /*catch (java.lang.IllegalArgumentException e) {
            int i = e.toString().indexOf("Mapping for");
            if (i >= 0) {
                i = e.toString().indexOf("not found but expected");
            } else {
                assertTrue(false);
            } 
        } */
        finally {}
        LOG.debug("Got " + this.bevMerger.getIdenticPoints() + " identical points");
        assertTrue(this.bevMerger.getIdenticPoints() == 6); 
        
        LOG.info("Testing Plane Similarity");
        this.bevMerger.getCoordinatePairList().stream().forEach((cp) -> {
            this.cadastre.addCoordinatePair(cp.getLocal(), cp.getGeocentric());
        });
        /*for (CoordinatePair cp: this.bevMerger.getCoordinatePairList()) {
            this.cadastre.addCoordinatePair(cp.getLocal(), cp.getGeocentric());
        }*/
        this.cadastre.execute();
        assertTrue(true);
    }
    
    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }

    // TODO add test methods here. The name must begin with 'test'. For example:
    // public void testHello() {}
}
