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
package eu.estcube.gs.radio;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.hbird.business.api.IDataAccess;
import org.hbird.business.api.IOrbitPrediction;
import org.hbird.exchange.core.CommandBase;
import org.hbird.exchange.groundstation.GroundStation;
import org.hbird.exchange.groundstation.Stop;
import org.hbird.exchange.groundstation.Track;
import org.hbird.exchange.navigation.LocationContactEvent;
import org.hbird.exchange.navigation.PointingData;
import org.hbird.exchange.navigation.Satellite;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import eu.estcube.gs.base.HamlibNativeCommand;
import eu.estcube.gs.base.IPointingDataOptimizer;
import eu.estcube.gs.configuration.RadioDriverConfiguration;
import eu.estcube.gs.radio.nativecommands.SetFrequency;
import eu.estcube.gs.radio.nativecommands.SetVfo;

/**
 *
 */
@RunWith(MockitoJUnitRunner.class)
public class HamlibRadioPartTest {

    private static final long NOW = System.currentTimeMillis();
    private static final long DELAY = 25L;
    private static final String STAGE = "someStage";
    private static final long UPLINK_FREQUENCY = 122311212L;
    private static final long DOWNLINK_FREQUENCY = 7990013312110L;
    private static final String ID = "ID-OF-TRACK-COMMAND";
    private static final long PRE_DELTA = 30000L;
    private static final double DOPPLER_1 = 123.213D;
    private static final double DOPPLER_2 = -123.213D;
    private static final double DOPPLER_3 = 0.0002D;

    @Mock
    private Satellite satellite;

    @Mock
    private Track track;

    @Mock
    private RadioDriverConfiguration config;

    @Mock
    private IDataAccess dataAccess;

    @Mock
    private IOrbitPrediction prediction;

    @Mock
    private IPointingDataOptimizer<RadioDriverConfiguration> optimizer;

    @Mock
    private PointingData pd1;

    @Mock
    private PointingData pd2;

    @Mock
    private PointingData pd3;

    @Mock
    private Stop stop;

    @Mock
    private GroundStation gs;

    @Mock
    private LocationContactEvent start;

    @Mock
    private LocationContactEvent end;

    private List<PointingData> pointingData;

    private HamlibRadioPart part;

    private InOrder inOrder;

    /**
     * @throws java.lang.Exception
     */
    @Before
    public void setUp() throws Exception {
        part = new HamlibRadioPart("test", config, dataAccess, prediction, optimizer);
        pointingData = Arrays.asList(pd1, pd2, pd3);
        inOrder = inOrder(satellite, track, config, dataAccess, prediction, optimizer, pd1, pd2, pd3, stop, start, end);
        when(satellite.getUplinkFrequency()).thenReturn(UPLINK_FREQUENCY);
        when(satellite.getDownlinkFrequency()).thenReturn(DOWNLINK_FREQUENCY);
        when(track.getID()).thenReturn(ID);
        when(config.getDelayInCommandGroup()).thenReturn(DELAY);
        when(config.getPreContactDelta()).thenReturn(PRE_DELTA);
        when(pd1.getDoppler()).thenReturn(DOPPLER_1);
        when(pd2.getDoppler()).thenReturn(DOPPLER_2);
        when(pd3.getDoppler()).thenReturn(DOPPLER_3);
        when(pd1.getTimestamp()).thenReturn(NOW + 1);
        when(pd2.getTimestamp()).thenReturn(NOW + 2);
        when(pd3.getTimestamp()).thenReturn(NOW + 3);
    }

    /**
     * Test method for
     * {@link eu.estcube.gs.radio.HamlibRadioPart#createCommands(org.hbird.exchange.navigation.Satellite, org.hbird.exchange.navigation.PointingData, org.hbird.exchange.groundstation.Track, java.lang.String, long)}
     * .
     */
    @Test
    public void testCreateCommands() {
        List<CommandBase> commands = part.createCommands(satellite, pd1, track, STAGE, DELAY);
        assertNotNull(commands);
        assertEquals(4, commands.size());

        HamlibNativeCommand cmd = (HamlibNativeCommand) commands.get(0);
        assertEquals(SetVfo.FOR_UPLINK, cmd.getCommandToExecute());
        assertEquals(NOW + 1, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(STAGE, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(1);
        assertEquals(SetFrequency.createCommand(UPLINK_FREQUENCY, DOPPLER_1), cmd.getCommandToExecute());
        assertEquals(NOW + 1 + DELAY, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(STAGE, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(2);
        assertEquals(SetVfo.FOR_DOWNLINK, cmd.getCommandToExecute());
        assertEquals(NOW + 1 + DELAY * 2, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(STAGE, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(3);
        assertEquals(SetFrequency.createCommand(DOWNLINK_FREQUENCY, DOPPLER_1), cmd.getCommandToExecute());
        assertEquals(NOW + 1 + DELAY * 3, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(STAGE, cmd.getStage());

        inOrder.verify(pd1, times(1)).getTimestamp();
        inOrder.verify(pd1, times(1)).getDoppler();
        inOrder.verify(track, times(1)).getID();
        inOrder.verify(satellite, times(1)).getUplinkFrequency();
        inOrder.verify(satellite, times(1)).getDownlinkFrequency();
        inOrder.verifyNoMoreInteractions();
    }

    /**
     * Test method for {@link eu.estcube.gs.radio.HamlibRadioPart#emergencyStop(org.hbird.exchange.groundstation.Stop)}
     * .
     */
    @Test
    public void testEmergencyStop() {
        List<CommandBase> result = part.emergencyStop(stop);
        assertNotNull(result);
        assertEquals(0, result.size());
    }

    /**
     * Test method for
     * {@link eu.estcube.gs.radio.HamlibRadioPart#createPreContactCommands(org.hbird.exchange.groundstation.GroundStation, Satellite, List, RadioDriverConfiguration, Track)}
     * .
     */
    @Test
    public void testCreatePreContactCommands() {
        List<CommandBase> commands = part.createPreContactCommands(gs, satellite, pointingData, config, track);
        assertNotNull(commands);
        assertEquals(4, commands.size());

        HamlibNativeCommand cmd = (HamlibNativeCommand) commands.get(0);
        assertEquals(SetVfo.FOR_UPLINK, cmd.getCommandToExecute());
        assertEquals(NOW + 1, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_PRE_TRACKING, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(1);
        assertEquals(SetFrequency.createCommand(UPLINK_FREQUENCY, DOPPLER_1), cmd.getCommandToExecute());
        assertEquals(NOW + 1 + DELAY, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_PRE_TRACKING, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(2);
        assertEquals(SetVfo.FOR_DOWNLINK, cmd.getCommandToExecute());
        assertEquals(NOW + 1 + DELAY * 2, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_PRE_TRACKING, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(3);
        assertEquals(SetFrequency.createCommand(DOWNLINK_FREQUENCY, DOPPLER_1), cmd.getCommandToExecute());
        assertEquals(NOW + 1 + DELAY * 3, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_PRE_TRACKING, cmd.getStage());

        inOrder.verify(config, times(1)).getDelayInCommandGroup();
        inOrder.verify(pd1, times(1)).getTimestamp();
        inOrder.verify(pd1, times(1)).getDoppler();
        inOrder.verify(track, times(1)).getID();
        inOrder.verify(satellite, times(1)).getUplinkFrequency();
        inOrder.verify(satellite, times(1)).getDownlinkFrequency();
        inOrder.verifyNoMoreInteractions();
    }

    /**
     * Test method for
     * {@link eu.estcube.gs.radio.HamlibRadioPart#createContactCommands(org.hbird.exchange.groundstation.GroundStation, Satellite, List, RadioDriverConfiguration, Track)}
     * .
     */
    @Test
    public void testCreateContactCommands() {
        List<CommandBase> commands = part.createContactCommands(gs, satellite, pointingData, config, track);
        assertNotNull(commands);
        assertEquals(8, commands.size());

        HamlibNativeCommand cmd = (HamlibNativeCommand) commands.get(0);
        assertEquals(SetVfo.FOR_UPLINK, cmd.getCommandToExecute());
        assertEquals(NOW + 2, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_TRACKING, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(1);
        assertEquals(SetFrequency.createCommand(UPLINK_FREQUENCY, DOPPLER_2), cmd.getCommandToExecute());
        assertEquals(NOW + 2 + DELAY, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_TRACKING, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(2);
        assertEquals(SetVfo.FOR_DOWNLINK, cmd.getCommandToExecute());
        assertEquals(NOW + 2 + DELAY * 2, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_TRACKING, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(3);
        assertEquals(SetFrequency.createCommand(DOWNLINK_FREQUENCY, DOPPLER_2), cmd.getCommandToExecute());
        assertEquals(NOW + 2 + DELAY * 3, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_TRACKING, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(4);
        assertEquals(SetVfo.FOR_UPLINK, cmd.getCommandToExecute());
        assertEquals(NOW + 3, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_TRACKING, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(5);
        assertEquals(SetFrequency.createCommand(UPLINK_FREQUENCY, DOPPLER_3), cmd.getCommandToExecute());
        assertEquals(NOW + 3 + DELAY, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_TRACKING, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(6);
        assertEquals(SetVfo.FOR_DOWNLINK, cmd.getCommandToExecute());
        assertEquals(NOW + 3 + DELAY * 2, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_TRACKING, cmd.getStage());

        cmd = (HamlibNativeCommand) commands.get(7);
        assertEquals(SetFrequency.createCommand(DOWNLINK_FREQUENCY, DOPPLER_3), cmd.getCommandToExecute());
        assertEquals(NOW + 3 + DELAY * 3, cmd.getExecutionTime());
        assertEquals(ID, cmd.getDerivedfrom());
        assertEquals(HamlibNativeCommand.STAGE_TRACKING, cmd.getStage());

        inOrder.verify(config, times(1)).getDelayInCommandGroup();
        inOrder.verify(pd2, times(1)).getTimestamp();
        inOrder.verify(pd2, times(1)).getDoppler();
        inOrder.verify(track, times(1)).getID();
        inOrder.verify(satellite, times(1)).getUplinkFrequency();
        inOrder.verify(satellite, times(1)).getDownlinkFrequency();
        inOrder.verify(pd3, times(1)).getTimestamp();
        inOrder.verify(pd3, times(1)).getDoppler();
        inOrder.verify(track, times(1)).getID();
        inOrder.verify(satellite, times(1)).getUplinkFrequency();
        inOrder.verify(satellite, times(1)).getDownlinkFrequency();
        inOrder.verifyNoMoreInteractions();
    }

    /**
     * Test method for
     * {@link eu.estcube.gs.radio.HamlibRadioPart#isTrackingPossible(org.hbird.exchange.navigation.LocationContactEvent, org.hbird.exchange.navigation.LocationContactEvent, GroundStation, Satellite)}
     * .
     */
    @Test
    public void testIsTrackingPossible() {
        assertTrue(part.isTrackingPossible(start, end, gs, satellite));
        inOrder.verifyNoMoreInteractions();
    }
}
