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

import org.cts.datum.Ellipsoid;
import org.cts.op.Geocentric2Geographic;
import org.cts.IllegalCoordinateException;

/**
 *
 * @author jurgen
 */
public class CoordinateConversion {
    private Ellipsoid ellipsoid;
    private double primeMeridian = 0.0;

    public CoordinateConversion(Ellipsoid ellipsoid) {
        this.ellipsoid = ellipsoid;
    }
    
    public CoordinateConversion(Ellipsoid ellipsoid, double primeMeridian) {
        this.ellipsoid = ellipsoid;
        this.primeMeridian = primeMeridian;
    }

    public Ellipsoid getEllipsoid() {
        return ellipsoid;
    }
    
    public void setEllipsoid(Ellipsoid ellipsoid) {
        this.ellipsoid = ellipsoid;
    }
        
    public GeographicCoordinate xyz2ellMethodTRAFO(GeocentricCoordinate coord) {
        double x = coord.getX();
        double y = coord.getY();
        double z = coord.getZ();
        double p = Math.sqrt(
            Math.pow(x,2)+Math.pow(y,2)
        );
        double eps = 1e-20;
        double e2 = ellipsoid.getSquareEccentricity();
        double a = ellipsoid.getSemiMajorAxis();
                
        double phi0 = Math.atan2(
            z,
            (1 - e2)*p
        );
        double dPhi = 9999999;
        double lam = Math.atan2(y, x);
        double N, h = 0, phi = 0;
        int maxIterations = 1000;
        int i=0;
        
        while(Math.abs(dPhi) > eps && i<maxIterations) {
            i++;
            N = a / Math.sqrt(
                (1 - e2*Math.pow(Math.sin(phi0),2)));
            
            h = p/Math.cos(phi0) - N;
            
            phi = Math.atan2(
                z,
                (1-e2*(N/(N+h)))*p
            );
            dPhi = phi-phi0;
            phi0 = phi;
        }
        
        return new GeographicCoordinate(
            coord.getName(),
            Math.toDegrees(phi), 
            Math.toDegrees(lam) + this.primeMeridian, 
            h,
            coord.getCode()
        );
    }
    
    public GeographicCoordinate xyz2ellMethodCTS(GeocentricCoordinate coord) {
        Geocentric2Geographic conv = new Geocentric2Geographic(this.ellipsoid);
        double[] coordArray;
        try {
            coordArray = conv.transform(coord.asArray());
        } catch (IllegalCoordinateException e) {
            System.out.println(e);
            coordArray = new double[] {0, 0, 0};
        }
        return new GeographicCoordinate(
            coord.getName(),
            Math.toDegrees(coordArray[0]),
            Math.toDegrees(coordArray[1]),
            coordArray[2],
            coord.getCode()
        );
    }
    
    public GeographicCoordinate xyz2ellMethodBEV(GeocentricCoordinate coord) {
        double x = coord.getX();
        double y = coord.getY();
        double z = coord.getZ();
        
        double a = ellipsoid.getSemiMajorAxis();
        double b = ellipsoid.getSemiMinorAxis();
        double e2 = ellipsoid.getSquareEccentricity();
        double e22 = ellipsoid.getSecondEccentricitySquared();
        
        double theta = Math.atan2(
                z*a,
                Math.sqrt(x*x + y*y)*b
        );

        double lam = Math.atan2(y, x);
        
        double phi = Math.atan2(
                (z + e22 * b * Math.pow(Math.sin(theta),3)),
                Math.sqrt(x*x + y*y) - e2*a*Math.pow(Math.cos(theta),3)
        );
        
        double c = a*a/b;
        double etha2 = e22 * Math.pow(Math.cos(phi), 2);
        double V = Math.sqrt(1 + etha2);
        double N = c/V;
        double h = Math.sqrt(x*x + y*y)/Math.cos(phi) - N;
        
        return new GeographicCoordinate(
                coord.getName(),
                Math.toDegrees(phi), 
                Math.toDegrees(lam), 
                h,
                coord.getCode()
        );
    }
    
    public GeographicCoordinate toGeographic(GeocentricCoordinate coord) {
        String method = "BEV";
        GeographicCoordinate gc = null;
        
        switch (method) {
            case "TRAFO":
                gc = this.xyz2ellMethodTRAFO(coord);
                break;
            case "CTS":
                gc = this.xyz2ellMethodCTS(coord);
                break;
            case "BEV":
                gc = this.xyz2ellMethodBEV(coord);
                break;
            default:
                break;
        }
        gc.setCode(gc.getCode().replace("-EXP", "").replace("-XYZ", "-ELL-CLC"));
        //System.out.println("Method for Conversion: " + method);
        return gc;
    }
    
    public GeocentricCoordinate ell2xyzMethodBEV(GeographicCoordinate coord) {
        double e2 = ellipsoid.getSquareEccentricity();
        double a = ellipsoid.getSemiMajorAxis();
        double phi = Math.toRadians(coord.getLat());
        double lam = Math.toRadians(coord.getLon());
        Double h = coord.getHeight();
        if (h == null) {
            h=0.0;
        }
        
        double N = a/Math.sqrt(1-e2*Math.pow(Math.sin(phi),2));
        
        double x,y,z;
        
        x = (N+h)*Math.cos(phi)*Math.cos(lam);
        y = (N+h)*Math.cos(phi)*Math.sin(lam);
        z = ((1-e2)*N+h)*Math.sin(phi);
        
        return new GeocentricCoordinate(coord.getName(), x, y, z, coord.getCode());       
    }
    
    public GeocentricCoordinate toGeocentric(GeographicCoordinate coord) {
        String method = "BEV";
        GeocentricCoordinate gc = null;
        
        switch (method) {
            /*case "TRAFO":
                gc = this.xyz2ellMethodTRAFO(coord);
                break;
            case "CTS":
                gc = this.xyz2ellMethodCTS(coord);
                break;*/
            case "BEV":
                gc = this.ell2xyzMethodBEV(coord);
                break;
            default:
                break;
        }
        gc.setCode(gc.getCode().replace("-EXP", "").replace("-ELL", "-XYZ-CLC"));
        //System.out.println("Method for Conversion: " + method);
        return gc;
    }
    
}
