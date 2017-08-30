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
package com.messfuchs.geo.models;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;

/**
 *
 * @author jurgen
 */
public class CoordinatePairSet {
        
    private Set<CoordinatePair> set = new TreeSet<>(new CompareSorter());

    public boolean add(CoordinatePair coordinate) {
        return this.set.add(coordinate);
    }

    public CoordinatePair findCoordinatePairbyName(String name) {
        Iterator<CoordinatePair> iterator = set.iterator();
        while(iterator.hasNext()) {
            CoordinatePair coordinate = iterator.next();
            if (coordinate.getName().equals(name)) {
                return coordinate;
            }
        }
        return null;
    }

    public int size() {
        return this.getSet().size();
    }

    public Set<CoordinatePair> getSet() {
        return set;
    }
}
