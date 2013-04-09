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
package org.hbird.business.core;

import org.hbird.exchange.configurator.StartComponent;
import org.hbird.exchange.configurator.StopComponent;
import org.hbird.exchange.core.BusinessCard;
import org.hbird.exchange.core.Part;
import org.hbird.exchange.interfaces.IStartablePart;

/**
 * @author Gert Villemos
 * 
 */
public class StartablePart extends CommandablePart implements IStartablePart {

    /** Default heart beat interval in millis. */
    public static final long DEFAULT_HEARTBEAT = 5000L;

    /**
	 * 
	 */
    private static final long serialVersionUID = 8396148214309147407L;

    /** The class name of the driver that can 'execute' this part. */
    protected String driverName = null;

    /** The location (name of a Configurator) where the component should be started. */
    protected String configurator = null;

    protected long heartbeat = DEFAULT_HEARTBEAT;

    protected BusinessCard card = null;
    {
    	this.commands.add(new StartComponent("", null));
    	this.commands.add(new StopComponent("", ""));

    	card = new BusinessCard(name, heartbeat, commands);
    }

    /**
     * @param name
     * @param description
     */
    public StartablePart(String ID, String name, String description, String driverName) {
        super(ID, name, description);
        this.driverName = driverName;
    }

    @Override
    public BusinessCard getBusinessCard() {
        return card.touch();
    }

    @Override
    public String getDriverName() {
        return driverName;
    }

    @Override
    public void setDriverName(String driverName) {
        this.driverName = driverName;
    }

    @Override
    public String getConfigurator() {
        return configurator;
    }

    @Override
    public void setConfigurator(String configurator) {
        this.configurator = configurator;
    }

    @Override
    public long getHeartbeat() {
        return heartbeat;
    }

    @Override
    public void setHeartbeat(long heartbeat) {
        this.heartbeat = heartbeat;
    }
}