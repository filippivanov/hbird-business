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
package org.hbird.business.archive.solr;

import org.hbird.business.core.SoftwareComponentDriver;
import org.hbird.exchange.configurator.StandardEndpoints;

/**
 * Component builder to create an archive component.
 * 
 * @author Gert Villemos
 */
public class ArchiveComponentDriver extends SoftwareComponentDriver {

    /* (non-Javadoc)
     * @see org.hbird.business.core.SoftwareComponentDriver#doConfigure()
     */
    @Override
    protected void doConfigure() {    	
    	
    	/** The SOLR component */
    	from("direct:solr_storage").to("solr:storage");
    	
    	/** Routes for receiving data and requests. */
        from(StandardEndpoints.MONITORING).to("direct:solr_storage");
        from(StandardEndpoints.EVENTS).to("direct:solr_storage");
        from(StandardEndpoints.COMMANDS).to("direct:solr_storage");
        
        /** Route for submitting the documents as a batch to the SOLR server. */
        from(addTimer("solr_submit", 5000)).setHeader("SUBMIT", simple("true")).to("direct:solr_storage");
    };
}
