package com.messfuchs.geo.math;

import com.messfuchs.geo.models.IdenticalPoint;
import com.messfuchs.geo.models.LocalCoordinate;

import java.util.ArrayList;
import java.lang.Math;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;


public class PlaneSimilarity {
    
    public static final double MAX_DIFF_EAST = 0.05;
    public static final double MAX_DIFF_NORTH = 0.05;
        
    private ArrayList<IdenticalPoint> identicalPointList;
    private ArrayList<IdenticalPoint> residualPointList;
    private ArrayList<LocalCoordinate> targetPointAccuracyList = null;
    private IdenticalPoint identicalAveragePoint;
    private double a0, a1, b0, b1;
    private double te, tn, r, s;
    private double param1 = 0., param2 = 0., param3 = 0., param4 = 0., param5 = 0.;
    private double param6 = 0., param7 = 0., m0, mp;
    private ArrayList<String> skippedIdenticalPoint = new ArrayList<>();
    
    private static final Logger LOG = LogManager.getLogger(PlaneSimilarity.class.getName());

    public PlaneSimilarity(ArrayList<IdenticalPoint> identicalPointList) {
        this.identicalPointList = identicalPointList;
        this.residualPointList = null;
    }

    public PlaneSimilarity(double a0, double a1, double b0, double b1) {
        this.a0 = a0;
        this.a1 = a1;
        this.b0 = b0;
        this.b1 = b1;
        this.te = 0.0;
        this.tn = 0.0;
        this.r = 0.0;
        this.s = 1.0;
        this.identicalPointList = new ArrayList<>();
        this.residualPointList = new ArrayList<>();
    }

    public PlaneSimilarity() {
        this(0., 0., 0., 0.);
    }

    public void addIdenticalPoint(IdenticalPoint other) {
        this.identicalPointList.add(other);
    }
    
    public int size() {
        return this.identicalPointList.size();
    }
    
    public boolean setPointUsage(String name, boolean usage) {
        for (IdenticalPoint ip: this.identicalPointList) {
            if (ip.getSourcePoint().getName().equals(name)) {
                ip.usePair = usage;
                return true;
            }
        }
        return false;
    }

    public void calculate() {
        
        // for (String skippedPoint)

        // compute average
        LOG.info("Compute Average");
        this.identicalAveragePoint = this.computeAverageIdenticalPoint();

        // compute residuals to average
        LOG.info("Compute Residuals to Average");
        this.residualPointList = this.computeResiduals();

        // compute a0, a1, b0, b1
        LOG.info("Compute a0, a1, b0, b1");
        this.computeParams();

        // compute accuracy
        LOG.info("Compute Accuracy Assesment");
        this.computeAccuracy();

   }
    public void computeParams() {
        // compute a1, b1
        for (IdenticalPoint rp: this.residualPointList) {
            param1 = param1 + rp.getSourcePoint().getEast() * rp.getTargetPoint().getEast();
            param2 = param2 + rp.getSourcePoint().getNorth() * rp.getTargetPoint().getNorth();
            param3 = param3 + rp.getSourcePoint().getEast() * rp.getTargetPoint().getNorth();
            param4 = param4 + rp.getSourcePoint().getNorth() * rp.getTargetPoint().getEast();
            param5 = param5 + Math.pow(rp.getSourcePoint().getEast(), 2) + Math.pow(rp.getSourcePoint().getNorth(), 2);
        }
        LOG.debug("Parameters: ");
        LOG.debug("Param I:   " + param1);
        LOG.debug("Param II:  " + param2);
        LOG.debug("Param III: " + param3);
        LOG.debug("Param VI:  " + param4);
        LOG.debug("Param V:   " + param5);
        this.a1 = (param1 + param2) / param5;
        this.b1 = (param3 - param4) / param5;
        
        this.s = Math.sqrt(Math.pow(this.a1,2) + Math.pow(this.b1, 2));
        this.r = Math.atan2(this.b1, this.a1);

        // compute a0, b0
        this.a0 = identicalAveragePoint.getTargetPoint().getEast() - this.a1*identicalAveragePoint.getSourcePoint().getEast() + this.b1*identicalAveragePoint.getSourcePoint().getNorth();
        this.b0 = identicalAveragePoint.getTargetPoint().getNorth() - this.b1*identicalAveragePoint.getSourcePoint().getEast() - this.a1*identicalAveragePoint.getSourcePoint().getNorth();

        System.out.println("");
        LOG.debug(String.format("a0:        %20.8f", this.a0));
        LOG.debug(String.format("b0:        %20.8f", this.b0));
        LOG.debug(String.format("a1:        %20.8f", this.a1));
        LOG.debug(String.format("b1:        %20.8f", this.b1));
        System.out.println("");
        
        /*double r = Math.toDegrees(this.r);
        double r_deg = Math.floor(Math.abs(r));
        double r_min = (r - r_deg) * 60;
        double r_sec = (r_min - Math.floor(r_min)) * 60;
        r_min = Math.floor(r_min);
        System.out.println(String.format("%.0f %.0f %.2f", r_deg, r_min, r_sec));*/
        
        this.te = this.a0;
        this.tn = this.b0;
        
        LOG.debug(this.showHelmertParams());
    }
    
    public String showHelmertParams() {
        return String.format("tEast: %10.3f, tNorth: %10.3f, rot: %10.10f, scale: %10.10f (%.1f ppm)", this.te, this.tn, this.r*180/Math.PI, this.s, (1-this.s)*1e6);
    }

    public ArrayList<IdenticalPoint> computeResiduals() {
        ArrayList<IdenticalPoint> tmpResidualPointList = new ArrayList<>();
        for (IdenticalPoint ip: this.identicalPointList) {
            if (ip.usePair == true) {
                tmpResidualPointList.add(ip.subtract(this.identicalAveragePoint));                
            }
        }
        return tmpResidualPointList;
    }

    public IdenticalPoint computeAverageIdenticalPoint() {
        IdenticalPoint identicalAveragePoint = new IdenticalPoint(
                new LocalCoordinate("sourceAverage", 0., 0., 0.),
                new LocalCoordinate("targetAverage", 0., 0., 0.)
        );
        for (IdenticalPoint ip: this.identicalPointList) {
            if (ip.usePair = true ) {
                identicalAveragePoint = identicalAveragePoint.add(ip);
            }
        }
        identicalAveragePoint = identicalAveragePoint.divide(identicalPointList.size());
        return identicalAveragePoint;
    }

    public LocalCoordinate getCalculatedCoordinate(LocalCoordinate sourceCoordinate) {
        return new LocalCoordinate(
            sourceCoordinate.getName() + "_calc",
            this.a0 + this.a1*sourceCoordinate.getEast() - this.b1*sourceCoordinate.getNorth(),
            this.b0 + this.b1*sourceCoordinate.getEast() + this.a1*sourceCoordinate.getNorth(),
            0.0
        );
    }

    public void computeAccuracy() {
        ArrayList<LocalCoordinate> calculatedTargetPointList = new ArrayList<>();
        this.targetPointAccuracyList = new ArrayList<>();

        for (IdenticalPoint ip: this.identicalPointList) {
            if (ip.usePair == true) {
                LocalCoordinate calc = this.getCalculatedCoordinate(ip.getSourcePoint());
                // LOG.debug("Calculated: " + calc);
                calculatedTargetPointList.add(calc);
                LocalCoordinate diff = ip.getTargetPoint().subtract(calc);
                diff.setName(ip.getTargetPoint().getName() + "_imp");
                String outString = String.format("Point: %15s, d_East: %6.3f, d_North: %6.3f", diff.getName(), diff.getEast() , diff.getNorth());
                if (Math.abs(diff.getEast()) >= MAX_DIFF_EAST) {
                    outString = outString + ", EAST_EXCEEDED";
                }
                if (Math.abs(diff.getNorth()) >= MAX_DIFF_NORTH) {
                    outString = outString + ", NORTH_EXCEEDED";
                }
                LOG.debug(outString);
                this.targetPointAccuracyList.add(diff);
                param6 = param6 + Math.pow(diff.getEast(), 2);
                param7 = param7 + Math.pow(diff.getNorth(), 2);   
            }
        }
        m0 = Math.sqrt((param6 + param7)/(2*this.identicalPointList.size()-4));
        mp = m0 * Math.sqrt(2);

        LOG.debug("m0:        " + m0);
        LOG.debug("mp:        " + mp);

    }

    public ArrayList<IdenticalPoint> getResidualPointList() {
        return residualPointList;
    }

    public void setResidualPointList(ArrayList<IdenticalPoint> residualPointList) {
        this.residualPointList = residualPointList;
    }

    public ArrayList<LocalCoordinate> getTargetPointAccuracyList() {
        return targetPointAccuracyList;
    }

    public void setTargetPointAccuracyList(ArrayList<LocalCoordinate> targetPointAccuracyList) {
        this.targetPointAccuracyList = targetPointAccuracyList;
    }

    public IdenticalPoint getIdenticalAveragePoint() {
        return identicalAveragePoint;
    }

    public void setIdenticalAveragePoint(IdenticalPoint identicalAveragePoint) {
        this.identicalAveragePoint = identicalAveragePoint;
    }

    public ArrayList<IdenticalPoint> getIdenticalPointList() {
        return identicalPointList;
    }
    
}
