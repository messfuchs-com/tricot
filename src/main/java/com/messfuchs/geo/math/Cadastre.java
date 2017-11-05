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
import com.messfuchs.geo.models.LocalCoordinate;
import com.messfuchs.geo.models.IdenticalPoint;
import com.messfuchs.geo.models.CoordinateComplex;

import java.util.ArrayList;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import org.cts.datum.Ellipsoid;
import org.cts.op.transformation.SevenParameterTransformation;
import org.cts.op.Geocentric2Geographic;
import org.cts.IllegalCoordinateException;
import org.cts.CoordinateDimensionException;
import org.cts.op.projection.TransverseMercator;
import org.cts.op.NonInvertibleOperationException;

/**
 *
 * @author jurgen
 */
public class Cadastre {
    
    public static Ellipsoid SOURCE_ELLIPSOID = Constants.GRS80;
    public static Ellipsoid TARGET_ELLIPSOID = Constants.BESSEL1841;
    public SevenParameterTransformation TRANSFOMATION;
    public final TransverseMercator PROJECTION = Constants.MGI;
    public Geocentric2Geographic gz2Gg;
    
    private final ArrayList<CoordinateComplex> cordComplexList;
    
    private final ArrayList<GeocentricCoordinate> geocentricCoordinateList;
    private final ArrayList<LocalCoordinate> calcLocalCoordinateList;
    private final ArrayList<LocalCoordinate> realLocalCoordinateList;
    
    private static final Logger LOG = LogManager.getLogger(Cadastre.class.getName());
    
    public PlaneSimilarity planeSimilarity = new PlaneSimilarity();
    
    public Cadastre() {
        this.geocentricCoordinateList = new ArrayList<>();
        this.calcLocalCoordinateList = new ArrayList<>();
        this.realLocalCoordinateList = new ArrayList<>();
        this.cordComplexList = new ArrayList<>();
        
        this.gz2Gg = new Geocentric2Geographic(Constants.BESSEL1841);   
        try {
            this.TRANSFOMATION = Constants.MGI_TO_ETRS89_TRANSFORMATION.inverse();
        } catch (NonInvertibleOperationException e) {
            LOG.error(e);
        }
    }

    public Cadastre(ArrayList<GeocentricCoordinate> geocentricCoordinateList) throws NonInvertibleOperationException {
        this.geocentricCoordinateList = geocentricCoordinateList;
        this.calcLocalCoordinateList = new ArrayList<>();
        this.realLocalCoordinateList = new ArrayList<>();
        this.gz2Gg = new Geocentric2Geographic(Constants.BESSEL1841);
        this.TRANSFOMATION = Constants.MGI_TO_ETRS89_TRANSFORMATION.inverse();
        this.cordComplexList = new ArrayList<>();
    }
    
    // TODO add coordinates
    public void addCoordinatePair(LocalCoordinate lo, GeocentricCoordinate gl) {
        this.realLocalCoordinateList.add(lo);
        this.geocentricCoordinateList.add(gl);
    }
    
    public void addCoordinatePair(LocalCoordinate lo, GeographicCoordinate gl) {
        try {
            GeocentricCoordinate temp = new GeocentricCoordinate(
                gl.getName(), this.gz2Gg.transform(gl.asArray()));
            this.realLocalCoordinateList.add(lo);
            this.geocentricCoordinateList.add(temp);
            
        } catch (IllegalCoordinateException e) {
            LOG.error("Error while adding GeographicCoordinate");
            LOG.error(e);
        }
    }
    
    public boolean execute() throws IOException {
        LOG.debug("Compute Identical Local Points");
        this.geocentricCoordinateList.stream().forEach(gc -> {
            
            this.cordComplexList.add(new CoordinateComplex(gc));
                  
            double[] targetGeocentric = null;
            double[] targetGeographic = null;
            double[] targetProjected;
                    
            // SOURCE GEOCENTRIC -> TARGET GEOCENTRIC (ETRS89/GRS80 -> MGI/BESSEL1841)
            try {
                targetGeocentric = this.TRANSFOMATION.transform(gc.asArray());
            } catch (IllegalCoordinateException e) {
                LOG.error("Error while Helmert Transformation");
                LOG.error(e);
            }
            
            // TARGET GEOCENTRIC -> TARGET GEOGRAPHIC
            try {
                targetGeographic = this.gz2Gg.transform(targetGeocentric);
            } catch (IllegalCoordinateException e) {
                LOG.error("Error while Geocentric2Geographic");
                LOG.error(e);
            }
            
            // TARGET GEOGRAPHIC -> TARGET PROJECTED
            try {
                targetProjected = this.PROJECTION.transform(targetGeographic);
                this.calcLocalCoordinateList.add(new LocalCoordinate(
                    gc.getName(), targetProjected));
            } catch (CoordinateDimensionException e) {
                LOG.error("Error while Projection");
                LOG.error(e);
            }
        });
        
        // PLANE SIMILARITY
        LOG.debug("Compute Plane Similarity");
        
        this.realLocalCoordinateList.stream().forEach(real -> {
            this.calcLocalCoordinateList.stream()
                .filter(calc -> calc.getName().equals(real.getName())).
                forEach(calc -> {
                    this.planeSimilarity.addIdenticalPoint(
                        new IdenticalPoint(calc, real));
                });
        });
        
        LOG.info("PlaneSimilarity Class got Identical Points: " + planeSimilarity.size());
        
        if (planeSimilarity.size() < 4) {
            LOG.warn("Not enough identical Points to calculate LocalTransformation");
            return false;
        } else {
            LOG.info("Enough identical Points to calculate LocalTransformation");
        }
        
        planeSimilarity.calculate();
        this.createReport("src/test/resources/CadastreReport.html");
        
        return true;
    }
    
    public void createReport(String outFileName) throws IOException{
        VelocityEngine ve = new VelocityEngine();
        ve.init();
        
        Template t = ve.getTemplate("src/main/resources/CadastreReport.html");
        VelocityContext vc = new VelocityContext();
        vc.put("geocentric", this.geocentricCoordinateList);
        vc.put("local", this.realLocalCoordinateList);
        vc.put("identicalPoints", this.planeSimilarity.getIdenticalPointList());
        vc.put("results", this.planeSimilarity.getTargetPointAccuracyList());
        vc.put("str", String.class);
        
        StringWriter sw = new StringWriter();
        t.merge(vc, sw);
        try (FileWriter fw = new FileWriter(outFileName)) {
            fw.write(sw.toString());
        }
    }
}
