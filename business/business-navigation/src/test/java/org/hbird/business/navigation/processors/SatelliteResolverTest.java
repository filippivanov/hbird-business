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
package org.hbird.business.navigation.processors;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import org.hbird.business.api.IDataAccess;
import org.hbird.business.navigation.configuration.PredictionConfigurationBase;
import org.hbird.business.navigation.request.PredictionRequest;
import org.hbird.exchange.navigation.Satellite;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class SatelliteResolverTest {

    private static final String SAT_ID = "SAT-1";

    @Mock
    private IDataAccess dao;

    @Mock
    private Satellite sat;

    @Mock
    private PredictionRequest<PredictionConfigurationBase> request;

    @Mock
    private PredictionConfigurationBase config;

    private SatelliteResolver resolver;

    private InOrder inOrder;

    private RuntimeException exception;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        resolver = new SatelliteResolver(dao);
        exception = new RuntimeException("Muchos problemos");
        inOrder = inOrder(dao, sat, request, config);
        when(request.getConfiguration()).thenReturn(config);
        when(config.getSatelliteId()).thenReturn(SAT_ID);
    }

    @Test
    public void testResolve() throws Exception {
        when(dao.resolve(SAT_ID)).thenReturn(sat);
        assertEquals(request, resolver.resolve(request));
        inOrder.verify(request, times(1)).getConfiguration();
        inOrder.verify(config, times(1)).getSatelliteId();
        inOrder.verify(dao, times(1)).resolve(SAT_ID);
        inOrder.verify(request, times(1)).setSatellite(sat);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testResolveSatNotFound() throws Exception {
        when(dao.resolve(SAT_ID)).thenReturn(null);
        assertEquals(request, resolver.resolve(request));
        inOrder.verify(request, times(1)).getConfiguration();
        inOrder.verify(config, times(1)).getSatelliteId();
        inOrder.verify(dao, times(1)).resolve(SAT_ID);
        inOrder.verifyNoMoreInteractions();
    }

    @Test
    public void testResolveWithException() throws Exception {
        when(dao.resolve(SAT_ID)).thenThrow(exception);
        assertEquals(request, resolver.resolve(request));
        inOrder.verify(request, times(1)).getConfiguration();
        inOrder.verify(config, times(1)).getSatelliteId();
        inOrder.verify(dao, times(1)).resolve(SAT_ID);
        inOrder.verifyNoMoreInteractions();
    }
}
