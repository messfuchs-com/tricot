package com.messfuchs.geo.math;

import com.messfuchs.geo.models.IdenticalPoint;
import com.messfuchs.geo.models.LocalCoordinate;

import java.util.ArrayList;
import java.lang.Math;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class PlaneSimilarity {
    
    public static final double MAX_DIFF_EAST = 0.05;
    public static final double MAX_DIFF_NORTH = 0.05;
    public static final double MAX_DIFF_LENGTH = 0.05;
        
    private ArrayList<IdenticalPoint> identicalPointList;
    private ArrayList<IdenticalPoint> residualPointList;
    private ArrayList<LocalCoordinate> targetPointAccuracyList = new ArrayList<>();
    private ArrayList<LocalCoordinate> correctedPointList = new ArrayList<>();
    private LocalCoordinate maxPS, minPS, avgPS;
    private IdenticalPoint identicalAveragePoint;
    private double a0, a1, b0, b1;
    private double te, tn, r, s;
    private double param1 = 0., param2 = 0., param3 = 0., param4 = 0., param5 = 0.;
    private double param6 = 0., param7 = 0., m0, mp;
    private final ArrayList<String> skippedIdenticalPoint = new ArrayList<>();
    
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
        this.maxPS = new LocalCoordinate("Max", null, null, null);
        this.minPS = new LocalCoordinate("Min", null, null, null);
        this.avgPS = new LocalCoordinate("Avg", null, null, null);
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
        this.residualPointList.stream().forEach(rp -> {
            param1 = param1 + rp.getSourcePoint().getEast() * rp.getTargetPoint().getEast();
            param2 = param2 + rp.getSourcePoint().getNorth() * rp.getTargetPoint().getNorth();
            param3 = param3 + rp.getSourcePoint().getEast() * rp.getTargetPoint().getNorth();
            param4 = param4 + rp.getSourcePoint().getNorth() * rp.getTargetPoint().getEast();
            param5 = param5 + Math.pow(rp.getSourcePoint().getEast(), 2) + Math.pow(rp.getSourcePoint().getNorth(), 2);
        });
        LOG.debug("Parameters: ");
        LOG.debug("Param I:   " + param1);
        LOG.debug("Param II:  " + param2);
        LOG.debug("Param III: " + param3);
        LOG.debug("Param VI:  " + param4);
        LOG.debug("Param V:   " + param5);
        this.a1 = (param1 + param2) / param5;
        this.b1 = (param3 - param4) / param5;
        
        this.s = (1 - Math.sqrt(Math.pow(this.a1,2) + Math.pow(this.b1, 2)))*10e6;
        this.r = Math.toDegrees(Math.atan2(this.b1, this.a1))*3600;

        // compute a0, b0
        LOG.debug(identicalAveragePoint.getSourcePoint());
        LOG.debug(identicalAveragePoint.getTargetPoint());
        
        this.a0 = identicalAveragePoint.getTargetPoint().getEast() - this.a1*identicalAveragePoint.getSourcePoint().getEast() + this.b1*identicalAveragePoint.getSourcePoint().getNorth();
        this.b0 = identicalAveragePoint.getTargetPoint().getNorth() - this.b1*identicalAveragePoint.getSourcePoint().getEast() - this.a1*identicalAveragePoint.getSourcePoint().getNorth();

        System.out.println("");
        LOG.debug(String.format("a0:        %20.8f", this.a0));
        LOG.debug(String.format("b0:        %20.8f", this.b0));
        LOG.debug(String.format("a1:        %20.8f", this.a1));
        LOG.debug(String.format("b1:        %20.8f", this.b1));
        System.out.println("");

        this.te = this.a0;
        this.tn = this.b0;
        
        LOG.debug(this.showHelmertParams());
    }
    
    public String showHelmertParams() {
        return String.format("tEast: %10.3f m, tNorth: %10.3f m, rot: %10.3f deg_sec, scale: %.4f ppm", this.te, this.tn, this.r, this.s);
    }

    public ArrayList<IdenticalPoint> computeResiduals() {
        ArrayList<IdenticalPoint> tmpResidualPointList = new ArrayList<>();
        this.identicalPointList
            .stream()
            .filter(ip -> ip.usePair == true)
            .forEach(ip -> {
                tmpResidualPointList.add(ip.subtract(this.identicalAveragePoint));                
        });
        return tmpResidualPointList;
    }

    public IdenticalPoint computeAverageIdenticalPoint() {
        IdenticalPoint identicalAveragePoint = new IdenticalPoint(
                new LocalCoordinate("sourceAverage", 0., 0., 0.),
                new LocalCoordinate("targetAverage", 0., 0., 0.)
        );
        for (IdenticalPoint ip: this.identicalPointList) {
            if (ip.usePair = true ) {
                // LOG.info("Add point for average: " + ip);
                identicalAveragePoint = identicalAveragePoint.add(ip);
            }
        }
        identicalAveragePoint = identicalAveragePoint.divide(identicalPointList.size());
        LOG.debug(identicalAveragePoint.getSourcePoint());
        LOG.debug(identicalAveragePoint.getTargetPoint());
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
        // ArrayList<LocalCoordinate> calculatedTargetPointList = new ArrayList<>();
        this.targetPointAccuracyList = new ArrayList<>();

        this.identicalPointList.stream()
            .filter(ip -> ip.usePair == true)
            .forEach(ip -> {
                LocalCoordinate calc = this.getCalculatedCoordinate(ip.getSourcePoint());
                calc.setName(ip.getSourcePoint().getName());
                this.correctedPointList.add(calc);
                // LOG.debug("Calculated: " + calc);
                // calculatedTargetPointList.add(calc);
                LocalCoordinate diff = calc.subtract(ip.getTargetPoint());
                diff.setName(ip.getTargetPoint().getName() + "_imp");
                String outString = String.format("Point: %15s, d_East: %6.3f, d_North: %6.3f", diff.getName(), diff.getEast() , diff.getNorth());
                diff.setCode("");

                if (Math.abs(diff.getEast()) >= MAX_DIFF_EAST) {
                    diff.setCode(diff.getCode() + "EAST_EXCEEDED");
                    outString = outString + ", EAST_EXCEEDED";
                }

                if (Math.abs(diff.getNorth()) >= MAX_DIFF_NORTH) {
                    diff.setCode((diff.getCode() + " NORTH_EXCEEDED").trim());
                    outString = outString + ", NORTH_EXCEEDED";
                }

                if (Math.abs(diff.getHorizontalLength()) >= MAX_DIFF_LENGTH) {
                    diff.setCode((diff.getCode() + " HORIZONAL_LENGTH_EXCEEDED").trim());
                    outString = outString + ", HORIZONAL_LENGTH_EXCEEDED";
                }

                LOG.debug(outString);
                diff.setHeight(null);
                this.targetPointAccuracyList.add(diff);
                param6 = param6 + Math.pow(diff.getEast(), 2);
                param7 = param7 + Math.pow(diff.getNorth(), 2);   
        });
        
        this.targetPointAccuracyList.stream().forEach(ac -> {
            
            // initialize statistics
            if (this.maxPS.getEast() == null) this.maxPS.setEast(ac.getEast());
            if (this.maxPS.getNorth() == null) this.maxPS.setNorth(ac.getNorth());
            if (this.minPS.getEast() == null) this.minPS.setEast(ac.getEast());
            if (this.minPS.getNorth() == null) this.minPS.setNorth(ac.getNorth());
            if (this.avgPS.getEast() == null) this.avgPS.setEast(0.0);
            if (this.avgPS.getNorth() == null) this.avgPS.setNorth(0.0);
            
            // update max
            if (Math.abs(this.maxPS.getEast()) < Math.abs(ac.getEast())) this.maxPS.setEast(ac.getEast());
            if (Math.abs(this.maxPS.getNorth()) < Math.abs(ac.getNorth())) this.maxPS.setNorth(ac.getNorth());
            
            // update min
            if (Math.abs(this.minPS.getEast()) > Math.abs(ac.getEast())) this.minPS.setEast(ac.getEast());
            if (Math.abs(this.minPS.getNorth()) > Math.abs(ac.getNorth())) this.minPS.setNorth(ac.getNorth());
            
            // update avg
            this.avgPS.setEast(this.avgPS.getEast() + ac.getEast());
            this.avgPS.setNorth(this.avgPS.getEast() + ac.getNorth());
        });
        
        // update avg
        this.avgPS.divide(this.targetPointAccuracyList.size());
        
        
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

    public ArrayList<LocalCoordinate> getCorrectedPointList() {
        return correctedPointList;
    }
    
    public double[] getTransformationInfo() {
        return new double[]{this.te,this.tn, this.r, this.s, this.targetPointAccuracyList.size()};
    }

    public LocalCoordinate getMax() {
        return maxPS;
    }

    public LocalCoordinate getMin() {
        return minPS;
    }

    public LocalCoordinate getAvg() {
        return avgPS;
    }   
}
