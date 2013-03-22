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
package org.hbird.exchange.configurator;

import java.util.List;

import org.hbird.exchange.constants.StandardArguments;
import org.hbird.exchange.core.Command;
import org.hbird.exchange.core.CommandArgument;
import org.hbird.exchange.interfaces.IStartablePart;

public class StartComponent extends Command {

    private static final long serialVersionUID = 3880066748415223278L;

    public static final String DESC_STRING = "Request to start a component.";
    
    public StartComponent(String issuedBy, String destination, IStartablePart part) {
        super(issuedBy, destination, "StartComponent", DESC_STRING);
        addPart(part);
    }

    public StartComponent(String issuedBy, IStartablePart part) {
        super(issuedBy, "any", "StartComponent", DESC_STRING);
        addPart(part);
    }

    /**
     * @see org.hbird.exchange.core.Command#getArgumentDefinitions()
     */
    @Override
    protected List<CommandArgument> getArgumentDefinitions(List<CommandArgument> args) {
        args.add(new CommandArgument(StandardArguments.PART,
                "The system part that this command requests the start of.", 
                IStartablePart.class,
                true));
        args.add(new CommandArgument(StandardArguments.HEART_BEAT, "The period between heartbeat signals (BusinessCards) from this component.", Long.class,
                "Milliseconds", 5000L, true));
        return args;
    }

    protected void addPart(IStartablePart part) {
        setArgumentValue(StandardArguments.PART, part);
    }

    protected void addHeartbeat(long period) {
        setArgumentValue(StandardArguments.HEART_BEAT, period);
    }

    public void setHeartbeat(long period) {
        addHeartbeat(period);
    }

    public long getHeartbeat() {
        return getArgumentValue(StandardArguments.HEART_BEAT, Long.class);
    }
    
    public IStartablePart getPart() {
        return getArgumentValue(StandardArguments.PART, IStartablePart.class);
    }
}
