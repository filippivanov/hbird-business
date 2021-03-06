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
package org.hbird.business.systemmonitoring.bean;

import org.hbird.business.api.IdBuilder;

public class Monitor {

    public static final String UNIT_PERCENTAGE = "%";
    public static final String UNIT_BYTE = "Byte";
    public static final String UNIT_COUNT = "Count";
    public static final String UNIT_MILLISECONDS = "ms";
    public static final String UNIT_LOAD_AVG = "Load Avg.";

    protected String componentId;

    protected final IdBuilder idBuilder;

    public Monitor(String componentId, IdBuilder idBuilder) {
        this.componentId = componentId;
        this.idBuilder = idBuilder;
    }

    public IdBuilder getIdBuilder() {
        return this.idBuilder;
    }
}
