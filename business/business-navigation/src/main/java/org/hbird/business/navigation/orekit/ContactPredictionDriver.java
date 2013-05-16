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

import org.apache.camel.CamelContext;
import org.apache.camel.model.ProcessorDefinition;
import org.hbird.business.api.ApiFactory;
import org.hbird.business.api.ICatalogue;
import org.hbird.business.api.IDataAccess;
import org.hbird.business.api.IPublish;
import org.hbird.business.core.SoftwareComponentDriver;
import org.hbird.business.navigation.PredictionComponent;
import org.hbird.business.navigation.configuration.ContactPredictionConfiguration;
import org.hbird.business.navigation.processors.ContactPredictionRequestCreator;
import org.hbird.business.navigation.processors.GroundStationResolver;
import org.hbird.business.navigation.processors.ResultExctractor;
import org.hbird.business.navigation.processors.TimeRangeCalulator;
import org.hbird.business.navigation.processors.TleResolver;
import org.hbird.business.navigation.processors.orekit.OrekitContactPredictor;

/**
 *
 */
public class ContactPredictionDriver extends SoftwareComponentDriver {

    /**
     * @see org.hbird.business.core.SoftwareComponentDriver#doConfigure()
     */
    @Override
    protected void doConfigure() {

        // setup from component
        PredictionComponent component = (PredictionComponent) entity;
        ContactPredictionConfiguration config = (ContactPredictionConfiguration) component.getConfiguration();

        // dependencies
        String serviceId = config.getServiceId();
        CamelContext ctx = component.getContext();
        IDataAccess dao = ApiFactory.getDataAccessApi(serviceId, ctx);
        ICatalogue catalogue = ApiFactory.getCatalogueApi(serviceId, ctx);
        IPublish publisher = ApiFactory.getPublishApi(serviceId, ctx);
        IPropagatorProvider propagatorProvider = new KeplerianTlePropagatorProvider();

        // processors
        ContactPredictionRequestCreator requestCreator = new ContactPredictionRequestCreator(config);
        TleResolver tleResolver = new TleResolver(dao);
        GroundStationResolver gsResolver = new GroundStationResolver(dao, catalogue);
        TimeRangeCalulator timeRangeCalculator = new TimeRangeCalulator();
        OrekitContactPredictor predictor = new OrekitContactPredictor(propagatorProvider, publisher);
        ResultExctractor extractor = new ResultExctractor();

        // actual route
        ProcessorDefinition<?> route = from(addTimer(config.getServiceId(), config.getPredictionInterval()))
                .bean(requestCreator)
                .bean(tleResolver)
                .bean(gsResolver)
                .bean(timeRangeCalculator)
                .bean(predictor)
                .bean(extractor)
                .split(body())
                .to("log:org.hbird.prediction.contact.stats?level=DEBUG&groupInterval=60000&groupDelay=60000&groupActiveOnly=false");

        addInjectionRoute(route);
    }
}
