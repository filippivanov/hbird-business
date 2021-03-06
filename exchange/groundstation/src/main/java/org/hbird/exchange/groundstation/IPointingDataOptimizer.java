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
package org.hbird.exchange.groundstation;

import java.util.List;

import org.hbird.exchange.navigation.PointingData;

/**
 * Interface for {@link PointingData} optimizers.
 */
public interface IPointingDataOptimizer<C extends GroundStationConfigurationBase> {

    /**
     * Optimizes given {@link PointingData} entries.
     * 
     * @param pointingData {@link PointingData} to optimize
     * @param configuration {@link GroundStationDriverConfiguration} to use for optimization
     * @return optimized list of {@link PointingData} entries
     * @throws Exception in case optimization fails
     */
    public List<PointingData> optimize(List<PointingData> pointingData, C configuration) throws Exception;
}
