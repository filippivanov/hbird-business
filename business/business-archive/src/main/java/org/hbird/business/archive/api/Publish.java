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
package org.hbird.business.archive.api;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.camel.CamelContext;
import org.hbird.business.api.HbirdApi;
import org.hbird.business.api.IPublisher;
import org.hbird.business.archive.ArchiveComponent;
import org.hbird.exchange.commandrelease.CommandRequest;
import org.hbird.exchange.core.Binary;
import org.hbird.exchange.core.Command;
import org.hbird.exchange.core.CommandArgument;
import org.hbird.exchange.core.EntityInstance;
import org.hbird.exchange.core.Label;
import org.hbird.exchange.core.Metadata;
import org.hbird.exchange.core.Parameter;
import org.hbird.exchange.core.State;
import org.hbird.exchange.dataaccess.CommitRequest;
import org.hbird.exchange.navigation.TleOrbitalParameters;
import org.hbird.exchange.tasking.Task;

/**
 * API for publishing different kinds of objects to the system.
 * 
 * @author Gert Villemos
 * 
 */
public class Publish extends HbirdApi implements IPublisher {

    /**
     * Constructor.
     * 
     * @param issuedBy The name/ID of the component that is using the API to send requests.
     */
    public Publish(String issuedBy) {
        super(issuedBy, ArchiveComponent.ARCHIVE_NAME);
    }

    public Publish(String issuedBy, CamelContext context) {
        super(issuedBy, ArchiveComponent.ARCHIVE_NAME, context);
    }

    /**
     * @see org.hbird.business.api.IPublisher#publish(org.hbird.exchange.core.Named)
     */
    @Override
    public EntityInstance publish(EntityInstance object) {
        // set issuedBy if missing
        if (object.getIssuedBy() == null) {
            object.setIssuedBy(issuedBy);
        }

        if (object instanceof Command) {
            // set destination if missing
            Command cmd = (Command) object;
            if (cmd.getDestination() == null) {
                cmd.setDestination(destination);
            }
        }

        template.sendBody(inject, object);
        return object;
    }
    @Override
    public void commit(String ID) {
        template.sendBody(inject, new CommitRequest(ID));
    }
}
