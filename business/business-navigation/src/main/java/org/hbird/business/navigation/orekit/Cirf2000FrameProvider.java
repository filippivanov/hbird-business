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
package org.hbird.business.navigation.orekit;

import org.orekit.errors.OrekitException;
import org.orekit.frames.Frame;
import org.orekit.frames.FramesFactory;
import org.orekit.frames.Predefined;

/**
 *
 */
public class Cirf2000FrameProvider implements IFrameProvider {

    /**
     * @see org.hbird.business.navigation.orekit.IFrameProvider#getInertialFrame()
     */
    @Override
    public Frame getInertialFrame() throws OrekitException {
        // TODO - 20.06.2013, kimmell - or CIRF_2000_CONV_2010?
        return FramesFactory.getFrame(Predefined.CIRF_2000_CONV_2003);
    }
}
