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
package org.hbird.business.archive.api;

import org.hbird.business.api.ApiFactory;
import org.hbird.business.api.HbirdApi;
import org.hbird.business.api.IPartManager;
import org.hbird.business.api.IPublish;
import org.hbird.exchange.configurator.StartComponent;
import org.hbird.exchange.configurator.StopComponent;
import org.hbird.exchange.interfaces.IStartablePart;

/**
 * @author Admin
 *
 */
public class PartManager extends HbirdApi implements IPartManager {

	/**
	 * @param issuedBy
	 */
	public PartManager(String issuedBy) {
		super(issuedBy);
	}

	/* (non-Javadoc)
	 * @see org.hbird.business.api.IPartManager#start(org.hbird.exchange.interfaces.IStartablePart)
	 */
	@Override
	public void start(IStartablePart part) {
		IPublish api = ApiFactory.getPublishApi(issuedBy);
		api.publish(new StartComponent(issuedBy, part));
	}

	/* (non-Javadoc)
	 * @see org.hbird.business.api.IPartManager#stop(java.lang.String)
	 */
	@Override
	public void stop(String partName) {
		IPublish api = ApiFactory.getPublishApi(issuedBy);
		api.publish(new StopComponent(issuedBy, partName));
	}
}