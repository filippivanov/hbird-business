/**
 * Licensed to the Hummingbird Foundation (HF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The HF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.hbird.business.navigation.orekit;

import java.util.Date;

import org.hbird.business.navigation.request.PredictionRequest;
import org.hbird.exchange.navigation.TleOrbitalParameters;
import org.orekit.errors.OrekitException;
import org.orekit.orbits.KeplerianOrbit;
import org.orekit.orbits.Orbit;
import org.orekit.propagation.Propagator;
import org.orekit.propagation.analytical.KeplerianPropagator;
import org.orekit.propagation.analytical.tle.TLE;
import org.orekit.propagation.analytical.tle.TLEPropagator;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;
import org.orekit.utils.PVCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 */
public class KeplerianTlePropagatorProvider implements IPropagatorProvider {

    private static final Logger LOG = LoggerFactory.getLogger(KeplerianTlePropagatorProvider.class);

    /**
     * @throws OrekitException
     * @see org.hbird.business.navigation.orekit.IPropagatorProvider#getPropagator(org.hbird.business.navigation.request.PredictionRequest)
     */
    @Override
    public Propagator getPropagator(PredictionRequest<?> request) throws OrekitException {

        long start = request.getStartTime();
        TleOrbitalParameters tleParameters = request.getTleParameters();
        AbsoluteDate startDate = new AbsoluteDate(new Date(start), TimeScalesFactory.getUTC());

        LOG.trace("Propagation starting from {}", startDate);

        /* Create propagator. */
        TLE tle = new TLE(tleParameters.getTleLine1(), tleParameters.getTleLine2());
        // TODO - 20.06.2013, kimmell - set the law and the space craft mass here
        TLEPropagator tlePropagator = TLEPropagator.selectExtrapolator(tle);
        PVCoordinates initialOrbitalState = tlePropagator.getPVCoordinates(startDate);
        Orbit initialOrbit = new KeplerianOrbit(initialOrbitalState, Constants.FRAME, startDate, Constants.MU);
        KeplerianPropagator propagator = new KeplerianPropagator(initialOrbit);
        return propagator;
    }
}
