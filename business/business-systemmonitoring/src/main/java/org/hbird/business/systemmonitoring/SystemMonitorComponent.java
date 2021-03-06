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
package org.hbird.business.systemmonitoring;

import org.hbird.business.core.StartableEntity;
import org.hbird.business.systemmonitoring.bean.SystemMonitorComponentDriver;

/**
 * @author Gert Villemos
 * 
 */
public class SystemMonitorComponent extends StartableEntity {

    private static final long serialVersionUID = -3066458562440110882L;

    public static final String SYSTEM_MONITORING_NAME = "SystemMonitoring";
    public static final String SYSTEM_MONITORING_DESC = "Component for monitoring the system resources, such as CPU and memory usages.";
    public static final String DEFAULT_DRIVER = SystemMonitorComponentDriver.class.getName();
    public static final long DEFAULT_MONITORING_INTERVAL = 1000 * 10;

    protected long monitoringInterval = DEFAULT_MONITORING_INTERVAL;

    public SystemMonitorComponent(String ID) {
        super(ID, SYSTEM_MONITORING_NAME);
        setDescription(SYSTEM_MONITORING_DESC);
        setDriverName(DEFAULT_DRIVER);
    }

    /**
     * @return the monitoringInterval
     */
    public long getMonitoringInterval() {
        return monitoringInterval;
    }

    /**
     * @param monitoringInterval the monitoringInterval to set
     */
    public void setMonitoringInterval(long monitoringInterval) {
        this.monitoringInterval = monitoringInterval;
    }
}
