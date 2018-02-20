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

import java.util.concurrent.ConcurrentHashMap;
import java.util.Map;
import java.util.HashMap;

import org.cts.op.transformation.SevenParameterTransformation;
import org.cts.datum.Ellipsoid;
import org.cts.op.projection.TransverseMercator;
import org.cts.datum.PrimeMeridian;
import org.cts.Parameter;
import org.cts.units.Measure;
import org.cts.units.Unit;

/*import org.opensextant.geodesy.Ellipsoid;
import org.opensextant.geodesy.TransverseMercator;
import org.opensextant.geodesy.Longitude;
import org.opensextant.geodesy.Latitude;*/

/**
 *
 * @author jurgen
 */


public final class Constants {
    
    public static final double tx = -577.326;
    public static final double ty = -90.129;
    public static final double tz = -463.919;
    public static final double rx = 5.137;
    public static final double ry = 1.474;
    public static final double rz = 5.297;
    public static final double ds = -2.4232;
    
    public static final double falseNorthing = -5000000.0;
    public static final double falseEasting = 0.0;
    public static final double originLatitude = 0.0;
    public static final double originLongitudeM28 = (13.0+20.0/60.0);
    public static final double originLongitudeM31 = (13.0+20.0/60.0);
    public static final double originLongitudeM34 = (13.0+20.0/60.0);
    public static final double originScale = 1.0;

    public final static SevenParameterTransformation ETRS89_TO_MGI_TRANSFORMATION = 
        SevenParameterTransformation.createSevenParameterTransformation(
            tx, ty, tz, rx, ry, rz, ds,
            1,
            SevenParameterTransformation.LINEARIZED,
            1.5
        );
        /*SevenParameterTransformation.createBursaWolfTransformation(
            tx, ty, tz, rx, ry, rz, ds
        );*/
        
    public final static Ellipsoid GRS80 = Ellipsoid.GRS80;
    public final static Ellipsoid BESSEL1841 = Ellipsoid.BESSEL1841;
    // public final static Ellipsoid GRS80 = Ellipsoid.getInstance("GRS80");
    // public final static Ellipsoid BESSEL1841 = Ellipsoid.getInstance("Bessel 1841");
    
    public final static PrimeMeridian GREENWICH = PrimeMeridian.GREENWICH;
    
    public final static TransverseMercator MGI28 = new TransverseMercator(BESSEL1841, new HashMap<String, Measure>(){{
            put(Parameter.SCALE_FACTOR, new Measure(originScale, Unit.UNIT));
            put(Parameter.CENTRAL_MERIDIAN, new Measure(originLongitudeM28 + GREENWICH.getLongitudeFromGreenwichInDegrees(), Unit.DEGREE));
            put(Parameter.FALSE_EASTING, new Measure(falseEasting, Unit.METER));
            put(Parameter.FALSE_NORTHING, new Measure(falseNorthing, Unit.METER));
            put(Parameter.LATITUDE_OF_ORIGIN, new Measure(originLatitude, Unit.DEGREE));
        }});

    public final static TransverseMercator MGI31 = new TransverseMercator(BESSEL1841, new HashMap<String, Measure>(){{
        put(Parameter.SCALE_FACTOR, new Measure(originScale, Unit.UNIT));
        put(Parameter.CENTRAL_MERIDIAN, new Measure(originLongitudeM31 + GREENWICH.getLongitudeFromGreenwichInDegrees(), Unit.DEGREE));
        put(Parameter.FALSE_EASTING, new Measure(falseEasting, Unit.METER));
        put(Parameter.FALSE_NORTHING, new Measure(falseNorthing, Unit.METER));
        put(Parameter.LATITUDE_OF_ORIGIN, new Measure(originLatitude, Unit.DEGREE));
    }});
    
    public final static TransverseMercator MGI34 = new TransverseMercator(BESSEL1841, new HashMap<String, Measure>(){{
        put(Parameter.SCALE_FACTOR, new Measure(originScale, Unit.UNIT));
        put(Parameter.CENTRAL_MERIDIAN, new Measure(originLongitudeM34 + GREENWICH.getLongitudeFromGreenwichInDegrees(), Unit.DEGREE));
        put(Parameter.FALSE_EASTING, new Measure(falseEasting, Unit.METER));
        put(Parameter.FALSE_NORTHING, new Measure(falseNorthing, Unit.METER));
        put(Parameter.LATITUDE_OF_ORIGIN, new Measure(originLatitude, Unit.DEGREE));
    }});
    
    /*public final static TransverseMercator getProjection() {
        TransverseMercator tm = new TransverseMercator(false);
        tm.setEllipsoid(BESSEL1841);
        tm.setScaleFactor(1.0);
        tm.setCentralMeridian(new Longitude(originLongitudeM31, org.opensextant.geodesy.Angle.DEGREES));
        tm.setOriginLatitude(new Latitude(originLatitude, org.opensextant.geodesy.Angle.DEGREES));
        return tm;
    }*/
}
