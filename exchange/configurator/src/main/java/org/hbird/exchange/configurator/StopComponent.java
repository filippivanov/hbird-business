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

public class StopComponent extends Command {

    public static final String DESCRIPTION = "A command to stop a component in the configurator.";
    public static final String ARGUMENT_DESCRIPTION_ENTITY_ID = "ID of the Entity to be stopped.";

    private static final long serialVersionUID = -2447358927849450763L;

    public StopComponent(String ID) {
        super(ID, StopComponent.class.getSimpleName());
        setDescription(DESCRIPTION);
    }

    /**
     * @see org.hbird.exchange.core.Command#getArgumentDefinitions()
     */
    @Override
    protected List<CommandArgument> getArgumentDefinitions(List<CommandArgument> args) {
        args.add(new CommandArgument(StandardArguments.ENTITY_ID, ARGUMENT_DESCRIPTION_ENTITY_ID, String.class, true));
        return args;
    }

    public String getEntityID() {
        return getArgumentValue(StandardArguments.ENTITY_ID, String.class);
    }

    public void setEntityID(String entityID) {
        setArgumentValue(StandardArguments.ENTITY_ID, entityID);
    }
}
