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
import com.messfuchs.geo.math.CoordinateConversion;
import com.messfuchs.geo.math.TransverseMercator;
import com.messfuchs.geo.models.CoordinatePair;

import java.util.ArrayList;
import java.io.StringWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.InputStream;
import java.io.BufferedReader;
import java.util.logging.Level;
import java.util.Properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.resource.util.StringResourceRepository;
import org.apache.velocity.runtime.resource.loader.StringResourceLoader;

import org.cts.datum.Ellipsoid;
import org.cts.op.transformation.SevenParameterTransformation;
import org.cts.op.Geocentric2Geographic;
import org.cts.IllegalCoordinateException;
import org.cts.CoordinateDimensionException;
import org.cts.op.NonInvertibleOperationException;


/**
 *
 * @author jurgen
 */
public class Cadastre {
    
    public static Ellipsoid SOURCE_ELLIPSOID = Constants.GRS80;
    public static Ellipsoid TARGET_ELLIPSOID = Constants.BESSEL1841;
    public final double[] originLongitudeArray = new double[] {10.0+20.0/60, 13.0+20.0/60, 16.0+20.0/60};
    
    public SevenParameterTransformation TRANSFOMATION = Constants.ETRS89_TO_MGI_TRANSFORMATION;
    public Geocentric2Geographic gz2Gg = new Geocentric2Geographic(org.cts.datum.Ellipsoid.BESSEL1841);  
    
    public String reportOutputFile;
    public static final String REPORT_TEMPLATE = "/Cadastre.velocity.html";
    
    public static final double FALSE_EASTING = 0.0;
    public static final double FALSE_NORTHING = -5000000.0;

    private final ArrayList<CoordinateComplex> cordComplexList;
    
    private final ArrayList<GeocentricCoordinate> geocentricCoordinateList;
    private final ArrayList<LocalCoordinate> calcLocalCoordinateList;
    private final ArrayList<LocalCoordinate> realLocalCoordinateList;
    
    private static final Logger LOG = LogManager.getLogger(Cadastre.class.getName());
    
    public PlaneSimilarity planeSimilarity = new PlaneSimilarity();
    
    private VelocityEngine velocityEngine = null;
    
    public Cadastre() {
        this.geocentricCoordinateList = new ArrayList<>();
        this.calcLocalCoordinateList = new ArrayList<>();
        this.realLocalCoordinateList = new ArrayList<>();
        this.cordComplexList = new ArrayList<>();
    }

    public Cadastre(ArrayList<GeocentricCoordinate> geocentricCoordinateList) throws NonInvertibleOperationException {
        this.geocentricCoordinateList = geocentricCoordinateList;
        this.calcLocalCoordinateList = new ArrayList<>();
        this.realLocalCoordinateList = new ArrayList<>();
        this.cordComplexList = new ArrayList<>();
    }
    
    // TODO add coordinates
    public void addCoordinatePair(LocalCoordinate lo, GeocentricCoordinate gl) {
        this.realLocalCoordinateList.add(lo);
        this.geocentricCoordinateList.add(gl);
    }
    
    public void addCoordinatePair(CoordinatePair cp) {
        this.realLocalCoordinateList.add(cp.getLocal());
        this.geocentricCoordinateList.add(cp.getGeocentric());
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
    
    public void setReportOutputFile(String outputFile) {
        this.reportOutputFile = outputFile;
    }
        
    public String getReportOutputFile() {
        return this.reportOutputFile;
    }
    
    public double getClosestOriginLongitude(double longitude) {
        double originLongitude = 0.0;
        double dL = longitude - originLongitude;

        for (double lon0: this.originLongitudeArray) {
            if (Math.abs(longitude-lon0) < Math.abs(dL)) {
                originLongitude = lon0;
                dL = longitude - originLongitude;
            }
        }
        return originLongitude;
    }
    
    public boolean execute() throws CoordinateDimensionException, IOException, Exception {
        LOG.debug("Compute Identical Local Points");
        this.geocentricCoordinateList.stream().forEach(gc -> {
            
            // Report ECEF
            LOG.info("Add ECEF " + gc.getName());
            gc.setCode("ETRS89");
            CoordinateComplex cc = new CoordinateComplex();
            cc.name = gc.getName();
                  
            double[] targetGeocentric = null;
            
            GeocentricCoordinate tGeocentric = null;
            GeographicCoordinate tGeographic = null;
            LocalCoordinate tProjected = null;
            // Topocentric2DPoint targetProjected;
                    
            // SOURCE GEOCENTRIC -> TARGET GEOCENTRIC (ETRS89/GRS80 -> MGI31/BESSEL1841)
            try {
                targetGeocentric = this.TRANSFOMATION.transform(gc.asArray());
                cc.ecefXYZ = gc;
                LOG.debug(gc);
                tGeocentric = new GeocentricCoordinate(gc.getName(), targetGeocentric[0], targetGeocentric[1], targetGeocentric[2], "MGI");
                cc.mgiXYZ = tGeocentric;
                LOG.debug(tGeocentric);                
            } catch (IllegalCoordinateException e) {
                LOG.error("Error while Helmert Transformation");
                LOG.error(e);
            }
            
            // TARGET GEOCENTRIC -> TARGET GEOGRAPHIC
            CoordinateConversion coordConv = new CoordinateConversion(TARGET_ELLIPSOID);
            tGeographic = coordConv.toGeographic(tGeocentric);

            LOG.debug(tGeographic);
            cc.mgiEll = tGeographic;

            
            // TARGET GEOGRAPHIC -> TARGET PROJECTED
            TransverseMercator tM = new TransverseMercator(
                Constants.BESSEL1841,
                this.getClosestOriginLongitude(tGeographic.getLon()),
                0,
                1, 
                FALSE_EASTING, 
                FALSE_NORTHING
            );
            tProjected = tM.toProjected(tGeographic);
            tProjected.setCode("MGI_GK");

            this.calcLocalCoordinateList.add(tProjected);
            cc.medYX = tProjected;
            LOG.debug(tProjected);
            
            this.cordComplexList.add(cc);
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
        
        for (CoordinateComplex cc: this.cordComplexList) {
            for (IdenticalPoint ip: this.planeSimilarity.getIdenticalPointList()) {
                if (ip.getSourcePoint().getName().equals(cc.name)) {
                    cc.real = ip.getTargetPoint();
                }
            }
            for (LocalCoordinate ac: this.planeSimilarity.getTargetPointAccuracyList()) {
                if (ac.getName().replace("_imp", "").equals(cc.name)) {
                    cc.imp = ac;
                    cc.usage = true;
                }
            }
            for (LocalCoordinate ac: this.planeSimilarity.getCorrectedPointList()) {
                if (ac.getName().equals(cc.name)) {
                    cc.calc = ac;
                    cc.usage = true;
                }
            }
        }

        this.createReport(this.reportOutputFile);
        
        return true;
    }
    
    public void createReport(String outFileName) throws IOException, Exception{
        
        String myTemplateBody;
        StringResourceRepository repository;

        velocityEngine = getVelocityEngine(this.velocityEngine);
        
        // Reading Template Body from the template file(.vm file) in the jar
        myTemplateBody = getTemplateFromJar();
        
        // Setting the template body in string repository with a template
        // name. Here the template name is used as a key for future mapping.
        repository = StringResourceLoader.getRepository();
        repository.putStringResource("myTemplateName", myTemplateBody);
                
        Template t = velocityEngine.getTemplate("myTemplateName");
        VelocityContext vc = this.getVelocityContext();
        
        LOG.debug("Write File to '" + outFileName + "'");
        StringWriter sw = new StringWriter();
        t.merge(vc, sw);
        try (FileWriter fw = new FileWriter(outFileName)) {
            fw.write(sw.toString());
        }
    }

    private VelocityContext getVelocityContext() {
        LocalCoordinate centroidSource = this.planeSimilarity.getIdenticalAveragePoint().getSourcePoint();
        LocalCoordinate centroidTarget = this.planeSimilarity.getIdenticalAveragePoint().getTargetPoint();
        LocalCoordinate centroidDiff = centroidTarget.subtract(centroidSource);
        LOG.debug(centroidDiff);
        VelocityContext vc = new VelocityContext();
        vc.put("geocentric", this.geocentricCoordinateList);
        vc.put("local", this.realLocalCoordinateList);
        vc.put("identicalPoints", this.planeSimilarity.getIdenticalPointList());
        vc.put("results", this.planeSimilarity.getTargetPointAccuracyList());
        vc.put("fullResult", this.cordComplexList);
        vc.put("localTrafoPar", this.planeSimilarity.getTransformationInfo());
        vc.put("localTrafoMax", this.planeSimilarity.getMax());
        vc.put("localTrafoMin", this.planeSimilarity.getMin());
        vc.put("localTrafoAvg", this.planeSimilarity.getAvg());
        vc.put("centroidSource", centroidSource);
        vc.put("centroidTarget", centroidTarget);
        vc.put("str", String.class);
        vc.put("centroidDiff", centroidDiff);
        return vc;
    }

    private String getTemplateFromJar() {
        // Reading the file contents from the JAR
        LOG.debug("Template: " + REPORT_TEMPLATE);
        InputStream inStream = Cadastre.class.getResourceAsStream(REPORT_TEMPLATE);
        LOG.debug("CP: " + this.getClass().getResource("/"));
        LOG.debug("InputStream: " + inStream);
        StringBuilder stringBuilder = new StringBuilder();
        InputStreamReader streamReader = new InputStreamReader(inStream);
        BufferedReader bufferedReader = new BufferedReader(streamReader);
        String line;

        try {
            while ((line = bufferedReader.readLine()) != null)
            {
                stringBuilder.append(line);
                stringBuilder.append("\n");
            }
        } catch (IOException ex) {
            java.util.logging.Logger.getLogger(Cadastre.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return stringBuilder.toString();
    }

    private VelocityEngine getVelocityEngine(Object object) throws Exception
    {
        // Initializes the velocity engine with properties. We should specify
        // the resource loader as string and the class for
        // string.resource.loader in properties
        Properties p = new Properties();

        p.setProperty("resource.loader", "string");
        p.setProperty("string.resource.loader.class",
            "org.apache.velocity.runtime.resource.loader.StringResourceLoader");
        velocityEngine = new VelocityEngine();
        velocityEngine.init(p);

        return (velocityEngine);
    }

}
