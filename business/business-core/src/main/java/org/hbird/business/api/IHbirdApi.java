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
package org.hbird.business.api;

/**
 * @author Gert Villemos
 * 
 */
public interface IHbirdApi {

    public String getIssuedBy();

    public void setIssuedBy(String issuedBy);

    public String getDestination();

    public void setDestination(String destination);

    /**
     * Should dispose all resources allocated by the {@link IHbirdApi} instance.
     * 
     * In case called on shared instance will stop the shared instance and all components will fail after this call.
     * 
     * Use at your own risk!
     * 
     * @throws Exception
     * @Deprecated
     */
    @Deprecated
    public void dispose() throws Exception;
}
