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

import org.apache.camel.model.ProcessorDefinition;
import org.hbird.business.api.ApiFactory;
import org.hbird.business.api.ICatalogue;
import org.hbird.business.api.IDataAccess;
import org.hbird.business.api.IPublish;
import org.hbird.business.core.SoftwareComponentDriver;
import org.hbird.exchange.configurator.StandardEndpoints;
import org.hbird.exchange.dataaccess.TlePropagationRequest;

/**
 * Component builder to create a Navigation Component
 * 
 * @author Gert Villemos
 * 
 */
public class NavigationComponentDriver extends SoftwareComponentDriver {

    @Override
    public void doConfigure() {

        String partName = part.getName();

        KeplerianOrbitPredictor predictor = new KeplerianOrbitPredictor();
        IDataAccess dao = ApiFactory.getDataAccessApi(partName);
        ICatalogue catalogue = ApiFactory.getCatalogueApi(partName);
        IPublish publisher = ApiFactory.getPublishApi(partName);
        TleOrbitalPredictor tlePredictor = new TleOrbitalPredictor(dao, catalogue, publisher, predictor);

        /** Create the route and the interface for getting the Locations. */
        // from("direct:locationstore").to("solr:locationstore");

        /** Route for commands to this component, requests for predictions. */
        from(StandardEndpoints.COMMANDS + "?" + addClassSelector(TlePropagationRequest.class.getSimpleName()))
                .bean(tlePredictor);

        ProcessorDefinition<?> route = from("direct:navigationinjection");
        addInjectionRoute(route);
    }
}
