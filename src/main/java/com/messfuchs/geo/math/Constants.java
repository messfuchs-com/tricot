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
import org.cts.Parameter;
import org.cts.units.Measure;
import org.cts.units.Unit;

/**
 *
 * @author jurgen
 */

class Parameters {
    static Map<String, Measure> p;
    
    Parameters() {
        Map<String, Measure> tempMap = new HashMap<>();
        tempMap.put(Parameter.FALSE_EASTING, new Measure(0.0, Unit.METER));
        tempMap.put(Parameter.FALSE_NORTHING, new Measure(5000000.0, Unit.METER));
        tempMap.put(Parameter.CENTRAL_MERIDIAN, new Measure(0, Unit.DEGREE));
        tempMap.put(Parameter.SCALE_FACTOR, new Measure(1, Unit.UNIT));
        tempMap.put(Parameter.LATITUDE_OF_ORIGIN, new Measure(16.0 + 20.0/60.0, Unit.DEGREE));
        this.p = tempMap;
    }
}

public final class Constants {
    public final static SevenParameterTransformation MGI_TO_ETRS89_TRANSFORMATION = 
        SevenParameterTransformation.createSevenParameterTransformation(
            577.326, 90.129, 463.919,  // tx, ty, tz
            5.137, 1.474, 5.297,       // rx, ry, rz
            2.4232,                    // scale
            SevenParameterTransformation.COORDINATE_FRAME,
            SevenParameterTransformation.LINEARIZED,
            1.5);
        
    public final static Ellipsoid GRS80 = Ellipsoid.GRS80;
    public final static Ellipsoid BESSEL1841 = Ellipsoid.BESSEL1841;
    
    public final static TransverseMercator MGI = new TransverseMercator(Constants.BESSEL1841, new HashMap<String, Measure>(){{
            put(Parameter.SCALE_FACTOR, new Measure(1.0, Unit.UNIT));
            put(Parameter.CENTRAL_MERIDIAN, new Measure(13.0+20.0/60, Unit.DEGREE));
            put(Parameter.FALSE_EASTING, new Measure(0.0, Unit.METER));
            put(Parameter.FALSE_NORTHING, new Measure(-5000000, Unit.METER));
            put(Parameter.LATITUDE_OF_ORIGIN, new Measure(0, Unit.DEGREE));
        }});
}
