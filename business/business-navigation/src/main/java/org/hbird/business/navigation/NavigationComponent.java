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
package org.hbird.business.navigation;

import java.util.List;

import org.hbird.business.core.StartablePart;
import org.hbird.business.navigation.orekit.NavigationComponentDriver;
import org.hbird.exchange.core.Command;
import org.hbird.exchange.dataaccess.TlePropagationRequest;
import org.hbird.exchange.interfaces.IPart;

/**
 * 
 * 
 * @author Gert Villemos
 * 
 */
public class NavigationComponent extends StartablePart {

    /**
	 * 
	 */
    private static final long serialVersionUID = -148692363129164616L;

    public static final String ORBIT_PROPAGATOR_NAME = "OrbitPropagator";
    public static final String ORBIT_PROPAGATOR_DESC = "Component for performing TLE based orbit prediction, including contact events and orbital states.";
    public static final String DEFAULT_DRIVER = NavigationComponentDriver.class.getName();

    public NavigationComponent(IPart isPartOf) {
        super(isPartOf, ORBIT_PROPAGATOR_NAME, ORBIT_PROPAGATOR_DESC, DEFAULT_DRIVER);
    }

    /**
     * @see org.hbird.business.core.StartablePart#createCommandList()
     */
    @Override
    protected List<Command> createCommandList(List<Command> commands) {
        commands.add(new TlePropagationRequest("", ""));
        return commands;
    }
}
