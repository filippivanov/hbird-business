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
package org.hbird.business.groundstationcontrol;

import org.hbird.exchange.core.StartablePart;


/**
 * @author Admin
 *
 */
public class TrackingComponent extends StartablePart {

	/**
	 * 
	 */
	private static final long serialVersionUID = 8231598458372644L;

	protected String satellite;
	
	protected String antenna;
	
	/**
	 * @param name
	 * @param description
	 * @param commands
	 */
	public TrackingComponent(String name, String description, String satellite, String antenna, String driver) {
		super(name, description, driver);
		this.satellite = satellite;
		this.antenna = antenna;
	}

	public TrackingComponent(String name, String description, String satellite, String antenna) {
		super(name, description, TrackingDriver.class.getName());
		this.satellite = satellite;
		this.antenna = antenna;
	}

	public String getSatellite() {
		return satellite;
	}

	public String getAntenna() {
		return antenna;
	}
}