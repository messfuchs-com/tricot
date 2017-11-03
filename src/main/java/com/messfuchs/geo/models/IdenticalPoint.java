package com.messfuchs.geo.models;


public class IdenticalPoint implements StringComparable {

    private LocalCoordinate sourcePoint;
    private LocalCoordinate targetPoint;
    
    public boolean usePair;

    
    public IdenticalPoint(LocalCoordinate sourcePoint, LocalCoordinate targetPoint, boolean usePair) {
        this.sourcePoint = sourcePoint;
        this.targetPoint = targetPoint;
        this.usePair = usePair;
    }
    
    public IdenticalPoint(LocalCoordinate sourcePoint, LocalCoordinate targetPoint) {
        this(sourcePoint, targetPoint, true);
    }

    public LocalCoordinate getSourcePoint() {
        return sourcePoint;
    }

    public void setSourcePoint(LocalCoordinate sourcePoint) {
        this.sourcePoint = sourcePoint;
    }

    public LocalCoordinate getTargetPoint() {
        return targetPoint;
    }

    public void setTargetPoint(LocalCoordinate targetPoint) {
        this.targetPoint = targetPoint;
    }

    public IdenticalPoint add(IdenticalPoint other) {
        return new IdenticalPoint(
                this.sourcePoint.add(other.getSourcePoint()),
                this.targetPoint.add(other.getTargetPoint())
        );
    }

    public IdenticalPoint divide(double divisor) {
        return new IdenticalPoint(
            this.sourcePoint.divide(divisor),
            this.targetPoint.divide(divisor)
        );
    }

    public IdenticalPoint subtract(IdenticalPoint other) {
        return new IdenticalPoint(
                this.sourcePoint.subtract(other.getSourcePoint()),
                this.targetPoint.subtract(other.getTargetPoint())
        );
    }

    @Override
    public String getCompareString() {
        return sourcePoint.getName() + "->" + targetPoint.getName();
    }

    @Override
    public String toString() {
        return "IdenticalPoint{" +
                "sourcePoint=" + sourcePoint +
                ", targetPoint=" + targetPoint +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IdenticalPoint that = (IdenticalPoint) o;

        if (getSourcePoint() != null ? !getSourcePoint().equals(that.getSourcePoint()) : that.getSourcePoint() != null)
            return false;
        return getTargetPoint() != null ? getTargetPoint().equals(that.getTargetPoint()) : that.getTargetPoint() == null;
    }

    @Override
    public int hashCode() {
        int result = getSourcePoint() != null ? getSourcePoint().hashCode() : 0;
        result = 31 * result + (getTargetPoint() != null ? getTargetPoint().hashCode() : 0);
        return result;
    }
}
