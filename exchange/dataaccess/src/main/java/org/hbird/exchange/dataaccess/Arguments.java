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
 * 
 */
package org.hbird.exchange.dataaccess;

import java.util.List;

import org.hbird.exchange.constants.StandardArguments;
import org.hbird.exchange.core.CommandArgument;
import org.hbird.exchange.core.NamedInstanceIdentifier;
import org.hbird.exchange.navigation.OrbitalState;
import org.hbird.exchange.navigation.TleOrbitalParameters;

/**
 * {@link CommandArgument} templates used in org.hbird.exchange.dataaccess package.
 */
public class Arguments {

    public static final CommandArgument APPLICABLE_TO = new CommandArgument(StandardArguments.APPLICABLE_TO,
            "The Named object the metadata must be appliable to.", NamedInstanceIdentifier.class, false);

    public static final CommandArgument CLASS = new CommandArgument(StandardArguments.CLASS, "The class of the Named object.", String.class, false);

    public static final CommandArgument CONTACT_DATA_STEP_SIZE = new CommandArgument(StandardArguments.CONTACT_DATA_STEP_SIZE,
            "The propagation step size when calculating Contact Data between a location and a satellite between which visibility exist.", Long.class,
            "Milliseconds", 500L, true);

    public static final CommandArgument DELETION_QUERY = new CommandArgument(StandardArguments.DELETION_QUERY,
            "The query based on which data will be deleted.", String.class, true);

    public static final CommandArgument DELTA_PROPAGATION = new CommandArgument(StandardArguments.DELTA_PROPAGATION,
            "The delta propagation from the starttime.", Long.class, "Seconds", 2 * 60 * 60d, true);

    public static final CommandArgument DERIVED_FROM = new CommandArgument(StandardArguments.DERIVED_FROM,
            "Identifier of the named object of which an object must be derived from.", NamedInstanceIdentifier.class, false);

    public static final CommandArgument FROM = new CommandArgument(StandardArguments.FROM, "The start of a range search on timestamp. Default to '*'.",
            Long.class, "Seconds", null, false);

    public static final CommandArgument GROUND_STATION_NAME = new CommandArgument(StandardArguments.GROUND_STATION_NAME, "The name of ground station.",
            String.class, "Name", null, true);

    public static final CommandArgument GROUND_STATION_NAMES = new CommandArgument(StandardArguments.GROUND_STATION_NAMES,
            "The name of the location(s) to which contact shall be calculated.", List.class, true);

    public static final CommandArgument INCLUDE_STATES = new CommandArgument(StandardArguments.INCLUDE_STATES,
            "Flag defining that all states applicable to the named objects should also be retrieved", Boolean.class, "", Boolean.FALSE, true);

    public static final CommandArgument INITIAL_STATE = new CommandArgument(StandardArguments.INITIAL_STATE,
            "The initial orbital state (time, position, velocity) from which shall be propagated. Default is last known state of the satellite.",
            OrbitalState.class, false);

    public static final CommandArgument INITIALIZATION = new CommandArgument(StandardArguments.INITIALIZATION,
            "If set to true, then the value below the 'to' time of each named object matching the search criterions will be retrieved.", Boolean.class, "",
            Boolean.FALSE, true);

    public static final CommandArgument IS_STATE_OF = new CommandArgument(StandardArguments.IS_STATE_OF, "Name of the object which this is the state of.",
            String.class, false);

    public static final CommandArgument NAMES = new CommandArgument(StandardArguments.NAMES, "List of names of named objects to be retrieved.", List.class,
            false);

    public static final CommandArgument PUBLISH = new CommandArgument(StandardArguments.PUBLISH,
            "Flag indicating that the resulting predictions should be published to the system instead of returned as a response.", Boolean.class, "",
            Boolean.class, false);

    public static final CommandArgument ROWS = new CommandArgument(StandardArguments.ROWS, "The maximum number of rows to be retrieved.", Integer.class, "",
            1000, true);

    public static final CommandArgument SATELLITE_NAME = new CommandArgument(StandardArguments.SATELLITE_NAME,
            "The name of satellite comming / leaving contact.", String.class, "Name", null, false);

    public static final CommandArgument SORT = new CommandArgument(StandardArguments.SORT, "The sort field. Default is timestamp.", String.class, "",
            StandardArguments.TIMESTAMP, true);

    public static final CommandArgument SORT_ORDER = new CommandArgument(StandardArguments.SORT_ORDER,
            "The order in which the returned data should be returned.", String.class, "", "ASC", true);

    public static final CommandArgument START_TIME = new CommandArgument(StandardArguments.START_TIME, "The start time of the propagation.", Long.class,
            "Seconds", null, true);

    public static final CommandArgument STEP_SIZE = new CommandArgument(StandardArguments.STEP_SIZE, "The propagation step size.", Long.class, "Seconds", 60L,
            true);

    public static final CommandArgument TLE_PARAMETERS = new CommandArgument(StandardArguments.TLE_PARAMETERS,
            "The two line elements of a specific satellite. If left empty the latest TLE for the satellite will be taken.", TleOrbitalParameters.class, false);

    public static final CommandArgument TO = new CommandArgument(StandardArguments.TO, "The end of a range search on timestamp. Default to '*'.", Long.class,
            "Seconds", null, false);

    public static final CommandArgument TYPE = new CommandArgument(StandardArguments.TYPE, "The type of the Named object.", String.class, false);

    public static final CommandArgument VISIBILITY = new CommandArgument(StandardArguments.VISIBILITY,
            "Whether the contact event is a start of contact (true) or end of contact (false).", Boolean.class, "", Boolean.TRUE, true);

    public static final CommandArgument IS_PART_OF = new CommandArgument(StandardArguments.IS_PART_OF,
            "The name of the object that the found objects must be defined as being a part of.", String.class, "", "", true);

    public static CommandArgument create(CommandArgument template) {
        return new CommandArgument(template);
    }
}