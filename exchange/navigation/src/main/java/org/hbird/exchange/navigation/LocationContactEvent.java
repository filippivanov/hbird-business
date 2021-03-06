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
package org.hbird.exchange.navigation;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.hbird.exchange.core.Event;
import org.hbird.exchange.interfaces.IDerivedFrom;
import org.hbird.exchange.interfaces.IGroundStationSpecific;
import org.hbird.exchange.interfaces.ISatelliteSpecific;
import org.hbird.exchange.util.Dates;

/**
 * The ground station control will, based on the mission timeline, orchestra the ground station.
 * 
 * The specific commands are generated in the drivers. The station control will trigger the actions
 * of the drivers as necessary.
 * 
 * The ground station will
 * - Retrieve the mission timeline, in particular the assigned contact events.
 * - Constraint Propagation. Apply rules and constraints, such as that a specific request must be send to drive X, Y
 * seconds prior to/after contact.
 * - Execute activities.
 * 
 * An event, signaling the establishment or loss of contact between a satellite and
 * a location on earth. The location would typically be a ground station, but could also
 * be a ship, or anything else.
 * 
 * The location event is typically calculated by a prediction module or could be reported
 * by a station or satellite.
 * 
 * @author Gert Villemos
 */
public class LocationContactEvent extends Event implements IGroundStationSpecific, ISatelliteSpecific, IDerivedFrom {

    private static final long serialVersionUID = -7200905393604330565L;

    public static final String DESCRIPTION = "A contact event between a satellite and a location";

    protected long startTime;

    protected long endTime;

    /** The location that contact has been established /lost with. */
    protected final String groundStationId;

    /** The satellite that contact has been established /lost with. */
    protected final String satelliteId;

    /** ID of calculation source. Eg ID of TLE. */
    protected String derivedFromId;

    protected final long orbitNumber;

    protected boolean inSunLigth;

    protected ContactParameterRange azimuth;

    protected ExtendedContactParameterRange elevation;

    protected ContactParameterRange doppler;

    protected ExtendedContactParameterRange uplinkSignalLoss;

    protected ExtendedContactParameterRange downlinkSignalLoss;

    protected ExtendedContactParameterRange range;

    protected ExtendedContactParameterRange signalDelay;

    /** The state of the satellite as the event occurs. */
    protected OrbitalState satelliteStateAtStart;

    /**
     * Constructor of a location contact event.
     * 
     * @param name The name of the location event.
     * @param description A description of the event.
     * @param timestamp The time that this event occured or is predicted to occure.
     * @param location The location to which contact has been established / lost.
     * @param satelliteId The satellite to which contact has been established / lost.
     */
    public LocationContactEvent(String groundStationId, String satelliteId, long orbitNumber) {
        super(new StringBuilder()
                .append(groundStationId)
                .append(INSTANCE_ID_SEPARATOR)
                .append(satelliteId)
                .toString(), LocationContactEvent.class.getSimpleName());
        this.groundStationId = groundStationId;
        this.satelliteId = satelliteId;
        this.orbitNumber = orbitNumber;
        setDescription(DESCRIPTION);
    }

    /**
     * @see org.hbird.exchange.core.EntityInstance#getInstanceID()
     */
    @Override
    public String getInstanceID() {
        return new StringBuilder()
                .append(ID)
                .append(INSTANCE_ID_SEPARATOR)
                .append(orbitNumber)
                .toString();
    }

    @Override
    public String getGroundStationID() {
        return groundStationId;
    }

    @Override
    public String getSatelliteID() {
        return satelliteId;
    }

    /**
     * @return the inSunLigth
     */
    public boolean isInSunLigth() {
        return inSunLigth;
    }

    /**
     * @param inSunLigth the inSunLigth to set
     */
    public void setInSunLigth(boolean inSunLigth) {
        this.inSunLigth = inSunLigth;
    }

    /**
     * @return the azimuth
     */
    public ContactParameterRange getAzimuth() {
        return azimuth;
    }

    /**
     * @param azimuth the azimuth to set
     */
    public void setAzimuth(ContactParameterRange azimuth) {
        this.azimuth = azimuth;
    }

    /**
     * @return the elevation
     */
    public ExtendedContactParameterRange getElevation() {
        return elevation;
    }

    /**
     * @param elevation the elevation to set
     */
    public void setElevation(ExtendedContactParameterRange elevation) {
        this.elevation = elevation;
    }

    /**
     * @return the uplinkDoppler
     */
    public ContactParameterRange getDoppler() {
        return doppler;
    }

    /**
     * @param uplinkDoppler the uplinkDoppler to set
     */
    public void setDoppler(ContactParameterRange doppler) {
        this.doppler = doppler;
    }

    /**
     * @return the uplinkSignalLoss
     */
    public ExtendedContactParameterRange getUplinkSignalLoss() {
        return uplinkSignalLoss;
    }

    /**
     * @param uplinkSignalLoss the uplinkSignalLoss to set
     */
    public void setUplinkSignalLoss(ExtendedContactParameterRange uplinkSignalLoss) {
        this.uplinkSignalLoss = uplinkSignalLoss;
    }

    /**
     * @return the downlinkSignalLoss
     */
    public ExtendedContactParameterRange getDownlinkSignalLoss() {
        return downlinkSignalLoss;
    }

    /**
     * @param downlinkSignalLoss the downlinkSignalLoss to set
     */
    public void setDownlinkSignalLoss(ExtendedContactParameterRange downlinkSignalLoss) {
        this.downlinkSignalLoss = downlinkSignalLoss;
    }

    /**
     * @return the range
     */
    public ExtendedContactParameterRange getRange() {
        return range;
    }

    /**
     * @param range the range to set
     */
    public void setRange(ExtendedContactParameterRange range) {
        this.range = range;
    }

    /**
     * @return the signalDelay
     */
    public ExtendedContactParameterRange getSignalDelay() {
        return signalDelay;
    }

    /**
     * @param signalDelay the signalDelay to set
     */
    public void setSignalDelay(ExtendedContactParameterRange signalDelay) {
        this.signalDelay = signalDelay;
    }

    /**
     * @return the startTime
     */
    public long getStartTime() {
        return startTime;
    }

    /**
     * @return the endTime
     */
    public long getEndTime() {
        return endTime;
    }

    /**
     * @return the orbitNumber
     */
    public long getOrbitNumber() {
        return orbitNumber;
    }

    /**
     * @return the satelliteStateAtStart
     */
    public OrbitalState getSatelliteStateAtStart() {
        return satelliteStateAtStart;
    }

    /**
     * @param satelliteStateAtStart the satelliteStateAtStart to set
     */
    public void setSatelliteStateAtStart(OrbitalState satelliteStateAtStart) {
        this.satelliteStateAtStart = satelliteStateAtStart;
    }

    /**
     * @return the derivedFromId
     */
    @Override
    public String getDerivedFromId() {
        return derivedFromId;
    }

    /**
     * @param derivedFromId the derivedFromId to set
     */
    public void setDerivedFromId(String derivedFromId) {
        this.derivedFromId = derivedFromId;
    }

    /**
     * @param startTime the startTime to set
     */
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    /**
     * @param endTime the endTime to set
     */
    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SHORT_PREFIX_STYLE);
        builder.append("eventId", getInstanceID());
        builder.append("timestamp", getTimestamp());
        builder.append("start", Dates.toDefaultDateFormat(startTime));
        builder.append("end", Dates.toDefaultDateFormat(endTime));
        builder.append("gs", groundStationId);
        builder.append("sat", satelliteId);
        builder.append("orbit", orbitNumber);
        return builder.build();
    }
}
