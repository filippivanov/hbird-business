package org.hbird.business.navigation.orekit;

import org.apache.commons.math.geometry.Vector3D;
import org.hbird.exchange.core.D3Vector;
import org.hbird.exchange.navigation.GeoLocation;
import org.hbird.exchange.navigation.OrbitalState;
import org.orekit.bodies.CelestialBodyFactory;
import org.orekit.bodies.GeodeticPoint;
import org.orekit.errors.OrekitException;
import org.orekit.frames.LocalOrbitalFrame;
import org.orekit.frames.LocalOrbitalFrame.LOFType;
import org.orekit.frames.TopocentricFrame;
import org.orekit.frames.Transform;
import org.orekit.orbits.CartesianOrbit;
import org.orekit.orbits.Orbit;
import org.orekit.propagation.Propagator;
import org.orekit.propagation.SpacecraftState;
import org.orekit.propagation.analytical.KeplerianPropagator;
import org.orekit.time.AbsoluteDate;
import org.orekit.time.TimeScalesFactory;
import org.orekit.time.UTCScale;
import org.orekit.tle.TLE;
import org.orekit.utils.PVCoordinates;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author Gert Villemos
 * 
 */
public class NavigationUtilities {

    private static Logger LOG = LoggerFactory.getLogger(NavigationUtilities.class);

    /** UTC scale to use. Don't access directly use {@link #getScale()} instead. */
    protected static UTCScale scale = null;

    protected static UTCScale getScale() {
        if (scale == null) {
            try {
                scale = TimeScalesFactory.getUTC();
            }
            catch (OrekitException e) {
                LOG.error("Failed to load UTC Scale", e);
            }
        }
        return scale;
    }

    public static PVCoordinates toPVCoordinates(D3Vector pos, D3Vector vel) {

        Vector3D position = new Vector3D(pos.getP1(), pos.getP2(), pos.getP3());
        Vector3D velocity = new Vector3D(vel.getP1(), vel.getP2(), vel.getP3());

        return new PVCoordinates(position, velocity);
    }

    public static synchronized OrbitalState toOrbitalState(SpacecraftState state, String satelliteId, String derivedFrom) {

        Vector3D pvcPosition = state.getOrbit().getPVCoordinates().getPosition();
        Vector3D pvcVelocity = state.getOrbit().getPVCoordinates().getVelocity();
        Vector3D pvcMomentum = state.getOrbit().getPVCoordinates().getMomentum();

        /* Create position vector. */
        D3Vector position = new D3Vector("", "Position");
        position.setDescription("The orbital position of the satellite at the given time.");
        position.setP1(pvcPosition.getX());
        position.setP2(pvcPosition.getY());
        position.setP3(pvcPosition.getZ());

        /* Create velocity vector. */
        D3Vector velocity = new D3Vector("", "Velocity");
        velocity.setDescription("The orbital velocity of the satellite at the given time.");
        velocity.setP1(pvcVelocity.getX());
        velocity.setP2(pvcVelocity.getY());
        velocity.setP3(pvcVelocity.getZ());

        /* Create momentum vector. */
        D3Vector momentum = new D3Vector("", "Momentum");
        momentum.setDescription("The orbital momentum of the satellite at the given time.");
        momentum.setP1(pvcMomentum.getX());
        momentum.setP2(pvcMomentum.getY());
        momentum.setP3(pvcMomentum.getZ());

        OrbitalState result = new OrbitalState(satelliteId + "/OrbitalState", OrbitalState.class.getSimpleName());
        result.setDescription("Orbital state of satellite");
        result.setTimestamp(state.getDate().toDate(getScale()).getTime());
        result.setSatelliteId(satelliteId);
        result.setPosition(position);
        result.setVelocity(velocity);
        result.setMomentum(momentum);
        result.setDerivedFromId(derivedFrom);

        try {
            result.setInSunLight(inSunLight(state));
        }
        catch (OrekitException e) {
            LOG.error("Failed to calculate inSunLight from the SpaceCraftState", e);
        }

        try {
            GeoLocation geoLocation = toGeoLocation(state);
            result.setGeoLocation(geoLocation);
        }
        catch (OrekitException e) {
            LOG.error("Failed to calculate GeoLocation from the SpaceCraftState", e);
        }

        // TODO - 16.05.2013, kimmell - add range
        // result.setRange(range);

        return result;
    }

    public static GeoLocation toGeoLocation(SpacecraftState state) throws OrekitException {
        AbsoluteDate date = state.getDate();
        PVCoordinates pvInert = state.getPVCoordinates();
        Transform t = state.getFrame().getTransformTo(Constants.earth.getBodyFrame(), date);
        Vector3D p = t.transformPosition(pvInert.getPosition());
        GeodeticPoint center = Constants.earth.transform(p, Constants.earth.getBodyFrame(), date);
        GeoLocation geoLocation = new GeoLocation(Math.toDegrees(center.getLatitude()), Math.toDegrees(center.getLongitude()), center.getAltitude());
        return geoLocation;
    }

    protected static double calculateAzimuth(PVCoordinates state, TopocentricFrame locationOnEarth, AbsoluteDate absoluteDate) throws OrekitException {
        return Math.toDegrees(locationOnEarth.getAzimuth(state.getPosition(), Constants.FRAME, absoluteDate));
    }

    protected static double calculateElevation(PVCoordinates state, TopocentricFrame locationOnEarth, AbsoluteDate absoluteDate) throws OrekitException {
        return Math.toDegrees(locationOnEarth.getElevation(state.getPosition(), Constants.FRAME, absoluteDate));
    }

    protected static double calculateDoppler(PVCoordinates state, TopocentricFrame locationOnEarth, AbsoluteDate absoluteDate) throws OrekitException {

        // an orbit defined by the position and the velocity of the satellite in the inertial FRAME at the date.
        // TODO - 01.05.2013, kimmell - which Orbit and Frame to use here?
        Orbit initialOrbit = new CartesianOrbit(state, Constants.FRAME, absoluteDate, Constants.MU);

        // as a propagator, we consider a simple KeplerianPropagator.
        Propagator propagator = new KeplerianPropagator(initialOrbit);

        // local orbital FRAME.
        LocalOrbitalFrame lof = new LocalOrbitalFrame(Constants.FRAME, LOFType.QSW, propagator, "QSW");

        PVCoordinates pv = locationOnEarth.getTransformTo(lof, absoluteDate).transformPVCoordinates(PVCoordinates.ZERO);
        return Vector3D.dotProduct(pv.getPosition(), pv.getVelocity()) / pv.getPosition().getNorm();
    }

    public static double calculateDopplerShift(double doppler, double frequency) {
        return ((1 - (doppler / Constants.SPEED_OF_LIGHT)) * frequency) - frequency;
    }

    public static int calculateOrbitNumber(TLE tle, AbsoluteDate date) throws OrekitException {
        // calculations for original recovered mean motion.
        final double a1 = Math.pow(Constants.XKE / (tle.getMeanMotion() * 60.0), 2.0 / 3.0);
        final double cosi0 = Math.cos(tle.getI());
        final double theta2 = cosi0 * cosi0;
        final double x3thm1 = 3.0 * theta2 - 1.0;
        final double e0sq = tle.getE() * tle.getE();
        final double beta02 = 1.0 - e0sq;
        final double beta0 = Math.sqrt(beta02);
        final double tval = Constants.CK2 * 1.5 * x3thm1 / (beta0 * beta02);
        final double delta1 = tval / (a1 * a1);
        final double a0 = a1 * (1.0 - delta1 * (1.0 / 3.0 + delta1 * (1.0 + 134.0 / 81.0 * delta1)));
        final double delta0 = tval / (a0 * a0);

        // recover original mean motion :
        final double xn0dp = tle.getMeanMotion() * 60.0 / (delta0 + 1.0);

        double age = date.durationFrom(tle.getDate()) / Constants.SECONDS_PER_DAY;

        int orbitNum = (int) (Math.floor((xn0dp * (Constants.MINUTES_PER_DAY / (Math.PI * 2)) + age * tle.getBStar() * 1.0) * age
                + tle.getMeanAnomaly() / (Math.PI * 2))
                + tle.getRevolutionNumberAtEpoch() - 1);
        return orbitNum;
    }

    public static boolean inSunLight(SpacecraftState s) throws OrekitException {
        return calculateEclipse(s) > 0.0D;
    }

    public static double calculateEclipse(SpacecraftState s) throws OrekitException {
        double occultedRadius = Constants.EQUATORIAL_RADIUS_OF_SUN;
        double occultingRadius = Constants.EQUATORIAL_RADIUS_OF_THE_EARTH;
        final Vector3D pted = CelestialBodyFactory.getSun().getPVCoordinates(s.getDate(), s.getFrame()).getPosition();
        final Vector3D ping = CelestialBodyFactory.getEarth().getPVCoordinates(s.getDate(), s.getFrame()).getPosition();
        final Vector3D psat = s.getPVCoordinates().getPosition();
        final Vector3D ps = pted.subtract(psat);
        final Vector3D po = ping.subtract(psat);
        final double angle = Vector3D.angle(ps, po);
        final double rs = Math.asin(occultedRadius / ps.getNorm());
        final double ro = Math.asin(occultingRadius / po.getNorm());
        return angle - ro - rs;
    }
}
