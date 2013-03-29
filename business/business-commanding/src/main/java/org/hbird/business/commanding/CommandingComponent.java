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
package org.hbird.business.commanding;

import org.hbird.business.commanding.bean.CommandingComponentDriver;
import org.hbird.exchange.configurator.StartablePart;


/**
 * @author Admin
 *
 */
public class CommandingComponent extends StartablePart {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2735008464467737242L;

	public static final String DEFAULT_NAME = "CommandReleaser";
	public static final String DEFAULT_DESCRIPTION = "A component for managing the release of commands.";
	public static final String DEFAULT_DRIVER = CommandingComponentDriver.class.getName();
	
	public CommandingComponent() {
		super(DEFAULT_NAME, DEFAULT_NAME, DEFAULT_DESCRIPTION, DEFAULT_DRIVER);
	}
}
