package com.messfuchs.geo.math;

import static com.messfuchs.geo.math.Constants.BESSEL1841;
import static com.messfuchs.geo.math.Constants.GREENWICH;
import static com.messfuchs.geo.math.Constants.falseEasting;
import static com.messfuchs.geo.math.Constants.falseNorthing;
import static com.messfuchs.geo.math.Constants.originLatitude;
import static com.messfuchs.geo.math.Constants.originLongitude;
import static com.messfuchs.geo.math.Constants.originScale;
import com.messfuchs.geo.models.LocalCoordinate;
import com.messfuchs.geo.models.GeographicCoordinate;
import java.util.HashMap;
import org.apache.commons.math3.util.FastMath;

import org.cts.datum.Ellipsoid;
import org.cts.CoordinateDimensionException;
import org.cts.Parameter;
import org.cts.units.Measure;
import org.cts.units.Unit;
import org.cts.datum.PrimeMeridian;


public class TransverseMercator extends Projection {

    public Ellipsoid ellipsoid;
    public double originLongitude = 0.0;
    public double falseEasting = 0.0;
    public double falseNorthing = 0.0;
    public double originScale = 1.0;
    public double originLatitude = 0.0;
    public PrimeMeridian primeMeridian = PrimeMeridian.GREENWICH;
    public String projectionName = "Projection";

    public TransverseMercator(Ellipsoid ellipsoid) {
        this.ellipsoid = ellipsoid;
    }
    
    public TransverseMercator(Ellipsoid ellipsoid, double originLongitude, double originLatitude, double originScale, double falseEasting, double falseNorthing) {
        this.ellipsoid = ellipsoid;
        this.originLongitude = originLongitude;
        this.originLatitude = originLatitude;
        this.originScale = originScale;
        this.falseEasting = falseEasting;
        this.falseNorthing = falseNorthing;
    }
    
    
    private LocalCoordinate ell2xyMethodCTS(GeographicCoordinate coord) {
        org.cts.op.projection.TransverseMercator tm = new org.cts.op.projection.TransverseMercator(
                ellipsoid, new HashMap<String, Measure>(){{
                    put(Parameter.SCALE_FACTOR, new Measure(originScale, Unit.UNIT));
                    put(Parameter.CENTRAL_MERIDIAN, new Measure(originLongitude, Unit.DEGREE));
                    put(Parameter.FALSE_EASTING, new Measure(falseEasting, Unit.METER));
                    put(Parameter.FALSE_NORTHING, new Measure(falseNorthing, Unit.METER));
                    put(Parameter.LATITUDE_OF_ORIGIN, new Measure(originLatitude, Unit.DEGREE));
        }});
    
    
        double[] coordArray = null;
        
        //System.out.println(coord);
        
        try {
            coordArray = tm.transform(coord.asArrayRadians());
        } catch (CoordinateDimensionException e) {
            // System.out.println("Array failed: " + coordArray.toString());
            System.out.println(e);
        }
        
        return new LocalCoordinate(
                coord.getName(),
                coordArray[0],
                coordArray[1],
                coord.getElev(),
                coord.getCode()
        );
    }
    
    public double[] getBigLetterParams() {
        double A, B, C, D;
        double a = ellipsoid.getSemiMajorAxis();
        double b = ellipsoid.getSemiMinorAxis();
        double e2 = (Math.pow(a,2)-Math.pow(b,2))/Math.pow(b,2);
        double e = Math.sqrt(e2);
        double e4 = Math.pow(e, 4);
        double e6 = Math.pow(e, 6);
        double e8 = Math.pow(e, 8);
        
        A = e2;
        B = 1/6 * (5*e4 - e6);
        C = 1/120 * (104*e6 - 45*e8);
        D = 1/1260 * (1237*e8);

        return new double[] {A,B,C,D};
    }
    
    private double getPhiStar(double phi) {
        double[] p = getBigLetterParams();
        double A,B,C,D;
        A = p[0];
        B = p[1];
        C = p[2];
        D = p[3];
        
        return (phi - 
                Math.sin(phi)*Math.cos(phi)*
                (
                    A +
                    B*Math.pow(Math.sin(phi), 2) +
                    C*Math.pow(Math.sin(phi), 4) +
                    D*Math.pow(Math.sin(phi), 6)
                ));
    }
    
    public double[] getGreekParams() {
        
        double alpha, beta, gama, delta;
        double A, B, C, D;
        
        double a = ellipsoid.getSemiMajorAxis();
        double b = ellipsoid.getSemiMinorAxis();
        double rho = 180/Math.PI;
        //double e2 = ellipsoid.getSquareEccentricity();
        double e2 = (Math.pow(a,2)-Math.pow(b,2))/Math.pow(b,2);
        // double e = Math.sqrt(e2);
        double e4 = Math.pow(e2, 2);
        double e6 = Math.pow(e2, 3);
        double e8 = Math.pow(e2, 4);
        double e10 = Math.pow(e2, 5);
        double c = 1-e2;
     
        //System.out.println(a);
        //System.out.println(b);
                
        
        A = 1 + 3/4 * e2 + 45/64 * e4 + 175/256 * e6 + 11025/16384 * e8 +  43659/65536 * e10;
        B =     3/4 * e2 + 15/16 * e4 + 525/512 * e6 +   2205/2048 * e8 +  72765/65536 * e10;
        C =                15/64 * e4 + 105/256 * e6 +   2205/4096 * e8 +  10395/16384 * e10;
        D =                              35/512 * e6 +    315/2048 * e8 + 31185/131072 * e10;
        
        System.out.println(A);
        System.out.println(B);
        System.out.println(C);
        System.out.println(D);
        
        alpha = A*a*c/rho;
        beta = B/2*a*c;
        gama = C/4*a*c;
        delta = D/6*a*c;
        
        return new double[] {alpha,beta,gama,delta};
    }
    
    public double[] getBetaParams() {
        double f = ellipsoid.getFlattening();
        double n = f/(2-f);
        double n2 = Math.pow(n, 2);
        double n3 = Math.pow(n, 3);
        double n4 = Math.pow(n, 4);
        double b1, b2, b3, b4;
        
        b1 = 1/2*n -   2/3*n2 +   5/16*n3 +       41/180*n4;
        b2 =         13/48*n2 -    3/5*n3 +     557/1440*n4;
        b3 =                    61/240*n3 -      103/140*n4;
        b4 =                                49561/161280*n4;
        
        return new double[] {b1,b2,b3,b4};
    }
    
    private LocalCoordinate ell2xyMethodEPSG(GeographicCoordinate coord) {
        double k0 = 1;
        double fE = this.falseEasting;
        double fN = this.falseNorthing;
        double A, C, M, T, v;
        double a = ellipsoid.getSemiMajorAxis();
        double e2 = ellipsoid.getSquareEccentricity();
        double e4 = Math.pow(e2, 2);
        double e6 = Math.pow(e2, 3);
        double e21= ellipsoid.getSquareEccentricity();
        double e22 = ellipsoid.getSecondEccentricitySquared();
        double east, north=0;
        double phi = Math.toRadians(coord.lat);
        double lam = Math.toRadians(coord.lon);
        double lam0 = Math.toRadians(originLongitude);
        
        T = Math.pow(Math.tan(phi), 2);
        C = e21 * Math.pow(Math.cos(phi), 2) / (1-e2);
        A = (lam - lam0) * Math.cos(phi);
        v = a / Math.sqrt(1 - e21 *Math.pow(Math.sin(phi), 2));
        M = a * (
                (1 - e2/4 - 3*e4/64 -   5*e6/256) * phi -
                (          15*e4/256 + 45*e6/1024) * Math.sin(2*phi) +
                (35*e6/3072)* Math.sin(6*phi)
            );
        
        east = fE + k0*v*(
                    A + 
                    (1 - T + C)*Math.pow(A, 3)/6 + 
                    (5 - 18*T + Math.pow(T, 2) + 72*C - 58*e22)*Math.pow(A, 5)/120
                );
        
        north = fN + k0*(
                    lam - lam0 + v*Math.tan(phi) *
                    (
                        Math.pow(A, 2)/2 + 
                        (5 - T + 9*C + 4*Math.pow(C, 2))*Math.pow(A, 4)/24 +
                        (61 - 58*T + Math.pow(T, 2) + 600*C - 330*e22)*Math.pow(A, 6)/720
                    )
                );
        
        return new LocalCoordinate(coord.getName(), east, north, coord.getElev());
    }
    
    public LocalCoordinate ell2xyMethodBEV(GeographicCoordinate coord) {

        double x, y, h=coord.getElev();
        
        double a = ellipsoid.getSemiMajorAxis();
        double b = ellipsoid.getSemiMinorAxis();
        //double e21 = ellipsoid.getSquareEccentricity();
        double e22 = ellipsoid.getSecondEccentricitySquared();
        double c = Math.pow(a, 2)/b;
        
        double phi = Math.toRadians(coord.lat);
        double phi0 = Math.toRadians(originLatitude);
        double deltaLambda = Math.toRadians(coord.lon - originLongitude);
        
        double sPhi = Math.sin(phi);
        double cPhi = Math.cos(phi);
        
        double eta2 = e22*Math.pow(cPhi, 2);
        double eta4 = Math.pow(eta2, 2);
        
        double V = Math.sqrt(1+eta2);
        double M = c/Math.pow(V, 3);
        double N = c/V;  
        
        double t = Math.tan(phi);
        double t2 = Math.pow(t, 2);
        double t3 = Math.pow(t, 3);
        double t4 = Math.pow(t, 4);
        double t6 = Math.pow(t, 6);
        
        double l1 = deltaLambda;
        double l2 = Math.pow(deltaLambda, 2);
        double l3 = Math.pow(deltaLambda, 3);
        double l4 = Math.pow(deltaLambda, 4);
        double l5 = Math.pow(deltaLambda, 5);
        double l6 = Math.pow(deltaLambda, 6);
        double l7 = Math.pow(deltaLambda, 7);
        
        double[] greekParams = this.getGreekParams();       
        double alpha = greekParams[0];
        double beta = greekParams[1];
        double gama = greekParams[2];
        double delta = greekParams[3]; 
        
        double Bphi = alpha * phi0 - 
                beta*Math.sin(2*phi) +
                gama*Math.sin(4*phi) -
                delta*Math.sin(6*phi);
        
        x = Bphi + N/2*l2*sPhi*cPhi +
                N/24*l4*sPhi*Math.pow(cPhi,3) * (5 - t2 + 9*eta2 + 4*eta4) +
                N/720*l6*sPhi*Math.pow(cPhi,5) * (61 - 58*t2 + t4);
        
        y = N         * l1 * cPhi + 
                N/6   * l3 * Math.pow(cPhi, 3) * (1-t2+eta2) +
                N/120 * l5 * Math.pow(cPhi, 5)*(5-18*t2+t4+14*eta2-58*eta2*t2)+
                N/5040* l7 * Math.pow(cPhi, 7)*(61-479*t2+179*t4-t6);
        
        return new LocalCoordinate(coord.getName(), y, x, h);
    }
    
    public LocalCoordinate ell2yxMethodSWE(GeographicCoordinate coord) {
        double phi = Math.toRadians(coord.lat);
        double phiStar = this.getPhiStar(phi);
        double deltaLambda = Math.toRadians(coord.lon - Constants.originLongitude);
        
        double k0 = originScale;

        double xi = Math.atan2(Math.tan(phiStar),
                Math.cos(deltaLambda));
        double eta = FastMath.atanh(Math.cos(phiStar)*
                Math.sin(deltaLambda));
        
        double a = ellipsoid.getSemiMajorAxis();
        double f = ellipsoid.getFlattening();
        double n = f/(2-f);
        double n2 = Math.pow(n, 2);
        double n4 = Math.pow(n, 4);
        double ad = a/(1+n)*(1 + 1/4*n2 + 1/64*n4);

        double[] betaParams = this.getBetaParams();
        double b1, b2, b3, b4;
        double x, y, h=coord.getElev();
        b1 = betaParams[0];
        b2 = betaParams[1];
        b3 = betaParams[2];
        b4 = betaParams[3];
        double cX;
        double cY;

        cX = xi + b1*Math.sin(2*xi)*FastMath.cosh(2*eta) +
                b2*Math.sin(4*xi)*FastMath.cosh(4*eta) +
                b3*Math.sin(6*xi)*FastMath.cosh(6*eta) +
                b4*Math.sin(8*xi)*FastMath.cosh(8*eta);
        
        cY = eta + b1*Math.cos(2*xi)*FastMath.sinh(2*eta) +
                b2*Math.cos(4*xi)*FastMath.sinh(4*eta) + 
                b3*Math.cos(6*xi)*FastMath.sinh(6*eta) +
                b4*Math.cos(8*xi)*FastMath.sinh(8*eta);                
        
        x = k0*ad*cX + this.falseNorthing;
        y = k0*ad*cY + this.falseEasting;
        
        return new LocalCoordinate(coord.getName(), y, x, h);
    }
    
    public LocalCoordinate toProjected(GeographicCoordinate coord) {
        
        String approach = "cts";
        LocalCoordinate projected = null;
        
        switch (approach) {
            case "aut":
                projected = this.ell2xyMethodBEV(coord);
                break;
            case "swe":
                projected = this.ell2yxMethodSWE(coord);
                break;
            case "epsg":
                projected = this.ell2xyMethodEPSG(coord);
                break;
            case "cts":
                projected = this.ell2xyMethodCTS(coord);
                projected.setCode(this.projectionName);
            default:
                break;
        }
                
        return projected;

    }
}
