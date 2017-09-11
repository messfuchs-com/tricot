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
package com.messfuchs.geo;

import com.messfuchs.geo.models.BEVMerger;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.util.Formatter;

/**
 *
 * @author jurgen
 */
public class BEVMergerTest extends TestCase{
    
    private final String resourcePath = "src/test/resources";
    
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public BEVMergerTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( BEVMergerTest.class );
    }
    
    public void testBEVInput() {
        BEVMerger bm = new BEVMerger(
                this.resourcePath + "/" + "0004769401_MGI_PUNKTART_EP.csv",
                this.resourcePath + "/" + "0004769401_ETRS_PUNKTART_EP.csv",
                this.resourcePath + "/" + "0004769401_MERGE_PUNKTART_EP.csv"
        );
        try {
            String bmText = bm.convert();
        } catch (java.io.IOException e)  {
            e.printStackTrace();
        } catch (java.lang.IllegalArgumentException e) {
            int i = e.toString().indexOf("Mapping for");
            if (i >= 0) {
                int j = e.toString().indexOf("not found, expected");
            } else {
                assertTrue(false);
            } 
        }
        assertTrue(bm.getIdenticPoints() == 6);        
    }
    
    public void testCustomTP() {
        BEVMerger bm = new BEVMerger(
                this.resourcePath + "/" + "Steingrube_TP_MGI.csv",
                this.resourcePath + "/" + "Steingrube_TP_ETRS.csv",
                this.resourcePath + "/" + "Steingrube_TP_MERGE.csv"
        );
        try {
            String bmText = bm.convert();
        } catch (java.io.IOException e)  {
            e.printStackTrace();
        } catch (java.lang.IllegalArgumentException e) {
            int i = e.toString().indexOf("Mapping for");
            if (i >= 0) {
                int j = e.toString().indexOf("not found, expected");
            } else {
                assertTrue(false);
            } 
        }
        assertTrue(bm.getIdenticPoints() == 2);        
    }
    
    public void testCustomEP() {
        BEVMerger bm = new BEVMerger(
                this.resourcePath + "/" + "Steingrube_EP_MGI.csv",
                this.resourcePath + "/" + "Steingrube_EP_ETRS.csv",
                this.resourcePath + "/" + "Steingrube_EP_MERGE.csv"
        );
        try {
            String bmText = bm.convert();
        } catch (java.io.IOException e)  {
            e.printStackTrace();
        } catch (java.lang.IllegalArgumentException e) {
            int i = e.toString().indexOf("Mapping for");
            if (i >= 0) {
                int j = e.toString().indexOf("not found, expected");
            } else {
                assertTrue(false);
            } 
        }
        assertTrue(bm.getIdenticPoints() == 4);        
    }
}