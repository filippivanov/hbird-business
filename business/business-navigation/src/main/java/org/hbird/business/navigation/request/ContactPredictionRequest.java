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
package org.hbird.business.navigation.request;

import java.util.List;

import org.hbird.business.navigation.configuration.ContactPredictionConfiguration;
import org.hbird.exchange.groundstation.GroundStation;

/**
 *
 */
public class ContactPredictionRequest<T> extends PredictionRequest<ContactPredictionConfiguration> {

    /** List of resolved ground stations. */
    protected List<GroundStation> groundStations;

    protected List<T> predictedEvents;

    /**
     * @param startTime
     */
    public ContactPredictionRequest(long startTime) {
        super(startTime);
    }

    /**
     * @return the groundStations
     */
    public List<GroundStation> getGroundStations() {
        return groundStations;
    }

    /**
     * @param groundStations the groundStations to set
     */
    public void setGroundStations(List<GroundStation> groundStations) {
        this.groundStations = groundStations;
    }

    /**
     * @return the predictedEvents
     */
    public List<T> getPredictedEvents() {
        return predictedEvents;
    }

    /**
     * @param predictedEvents the predictedEvents to set
     */
    public void setPredictedEvents(List<T> predictedEvents) {
        this.predictedEvents = predictedEvents;
    }
}
