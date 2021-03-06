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
package org.hbird.business.groundstation.base;

import java.util.List;

import org.hbird.business.core.StartableEntity;
import org.hbird.business.groundstation.configuration.GroundStationDriverConfiguration;
import org.hbird.exchange.core.Command;
import org.hbird.exchange.groundstation.Stop;
import org.hbird.exchange.groundstation.Track;

/**
 *
 */
public class GroundStationPart<C extends GroundStationDriverConfiguration> extends StartableEntity {

    private static final long serialVersionUID = 3285012782965015995L;

    protected final C configuration;

    public GroundStationPart(String ID, String name, String description, String driverName, C configuration) {
        super(ID, name);
        this.description = description;
        this.driverName = driverName;
        this.configuration = configuration;
    }

    /**
     * @return the configuration
     */
    public C getConfiguration() {
        return configuration;
    }

    /**
     * @see org.hbird.business.core.StartableEntity#getHeartbeat()
     */
    @Override
    public long getHeartbeat() {
        return configuration.getHeartBeatInterval();
    }

    /**
     * @see org.hbird.business.core.StartableEntity#setHeartbeat(long)
     */
    @Override
    public void setHeartbeat(long heartbeat) {
        configuration.setHeartBeatInterval(heartbeat);
    }

    /**
     * @see org.hbird.business.core.StartableEntity#createCommandList(java.util.List)
     */
    @Override
    protected List<Command> createCommandList(List<Command> commands) {
        commands = super.createCommandList(commands);
        commands.add(new Track(""));
        commands.add(new Stop(""));
        return commands;
    }
}
