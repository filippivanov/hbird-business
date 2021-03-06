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

import java.util.ArrayList;
import java.util.List;

import org.hbird.exchange.core.Command;
import org.hbird.exchange.core.EntityInstance;
import org.hbird.exchange.interfaces.ICommandablePart;

/**
 * A part that can be commanded, i.e. which can receive requests.
 * 
 * @author Gert Villemos
 * 
 */
public class CommandableEntity extends EntityInstance implements ICommandablePart {

    private static final long serialVersionUID = 4725490371074035117L;

    protected List<Command> commands = createCommandList(new ArrayList<Command>());

    /**
     * @param name
     * @param description
     */
    public CommandableEntity(String ID, String name) {
        super(ID, name);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hbird.exchange.interfaces.ICommandablePart#setCommands(java.util.List)
     */
    @Override
    public void setCommands(List<Command> commands) {
        this.commands = commands;
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.hbird.exchange.interfaces.ICommandablePart#getCommands()
     */
    @Override
    public List<Command> getCommands() {
        return commands;
    }

    public void addCommand(Command command) {
        this.commands.add(command);
    }

    protected List<Command> createCommandList(List<Command> commands) {
        return commands;
    }
}
