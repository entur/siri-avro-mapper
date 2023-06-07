package org.entur.avro.realtime.siri.converter.avro2jaxb;

import org.entur.avro.realtime.siri.model.CallRecord;
import org.entur.avro.realtime.siri.model.LocationRecord;
import org.entur.avro.realtime.siri.model.MonitoredVehicleJourneyRecord;
import org.entur.avro.realtime.siri.model.ProgressBetweenStopsRecord;
import org.entur.avro.realtime.siri.model.VehicleActivityRecord;
import org.entur.avro.realtime.siri.model.VehicleModeEnum;
import org.entur.avro.realtime.siri.model.VehicleMonitoringDeliveryRecord;
import uk.org.siri.siri21.DestinationRef;
import uk.org.siri.siri21.DirectionRefStructure;
import uk.org.siri.siri21.JourneyPlaceRefStructure;
import uk.org.siri.siri21.LineRef;
import uk.org.siri.siri21.LocationStructure;
import uk.org.siri.siri21.MonitoredCallStructure;
import uk.org.siri.siri21.NaturalLanguagePlaceNameStructure;
import uk.org.siri.siri21.NaturalLanguageStringStructure;
import uk.org.siri.siri21.OperatorRefStructure;
import uk.org.siri.siri21.ProgressBetweenStopsStructure;
import uk.org.siri.siri21.StopPointRefStructure;
import uk.org.siri.siri21.VehicleActivityStructure;
import uk.org.siri.siri21.VehicleJourneyRef;
import uk.org.siri.siri21.VehicleModesEnumeration;
import uk.org.siri.siri21.VehicleMonitoringDeliveryStructure;
import uk.org.siri.siri21.VehicleRef;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class VehicleMonitoringDeliveryConverter extends Avro2JaxbEnumConverter {

    public static Collection<VehicleMonitoringDeliveryStructure> convert(List<VehicleMonitoringDeliveryRecord> vmDeliveries) {
        List<VehicleMonitoringDeliveryStructure> results = new ArrayList<>();

        for (VehicleMonitoringDeliveryRecord delivery : vmDeliveries) {

            VehicleMonitoringDeliveryStructure deliveryStructure = new VehicleMonitoringDeliveryStructure();
            if (delivery.getResponseTimestamp() != null) {
                deliveryStructure.setResponseTimestamp(convertDate(delivery.getResponseTimestamp()));
            }
            if (delivery.getVersion() != null) {
                deliveryStructure.setVersion((String) delivery.getVersion());
            }

            for (VehicleActivityRecord vehicleActivityRecord : delivery.getVehicleActivities()) {
                deliveryStructure
                        .getVehicleActivities()
                        .add(convert(vehicleActivityRecord));
            }
            results.add(deliveryStructure);
        }

        return results;
    }

    static VehicleActivityStructure convert(VehicleActivityRecord vehicleActivityRecord) {
        VehicleActivityStructure a = new VehicleActivityStructure();
        if (vehicleActivityRecord.getRecordedAtTime() != null) {
            a.setRecordedAtTime(convertDate(vehicleActivityRecord.getRecordedAtTime()));
        }
        if (vehicleActivityRecord.getValidUntilTime() != null) {
            a.setValidUntilTime(convertDate(vehicleActivityRecord.getValidUntilTime()));
        }
        if (vehicleActivityRecord.getProgressBetweenStops() != null) {
            a.setProgressBetweenStops(convert(vehicleActivityRecord.getProgressBetweenStops()));
        }
        if (vehicleActivityRecord.getMonitoredVehicleJourney() != null) {
            a.setMonitoredVehicleJourney(convert(vehicleActivityRecord.getMonitoredVehicleJourney()));
        }
        return a;
    }

    private static ProgressBetweenStopsStructure convert(ProgressBetweenStopsRecord progressBetweenStops) {
        ProgressBetweenStopsStructure progressBetweenStopsStructure = new ProgressBetweenStopsStructure();
        if (progressBetweenStops.getPercentage() != null) {
            progressBetweenStopsStructure.setPercentage(BigDecimal.valueOf(progressBetweenStops.getPercentage()));
        }
        if (progressBetweenStops.getLinkDistance() != null) {
            progressBetweenStopsStructure.setLinkDistance(BigDecimal.valueOf(progressBetweenStops.getLinkDistance()));
        }
        return progressBetweenStopsStructure;
    }

    private static VehicleActivityStructure.MonitoredVehicleJourney convert(MonitoredVehicleJourneyRecord rec) {
        VehicleActivityStructure.MonitoredVehicleJourney vehicleJourney = new VehicleActivityStructure.MonitoredVehicleJourney();
        if (rec.getLineRef() != null) {
            vehicleJourney.setLineRef(setValue(LineRef.class, rec.getLineRef()));
        }
        if (rec.getDirectionRef() != null) {
            vehicleJourney.setDirectionRef(setValue(DirectionRefStructure.class, rec.getDirectionRef()));
        }
        if (rec.getFramedVehicleJourneyRef() != null) {
            vehicleJourney.setFramedVehicleJourneyRef(convert(rec.getFramedVehicleJourneyRef()));
        }
        if (!isNullOrEmpty(rec.getVehicleModes())) {
            vehicleJourney.getVehicleModes().addAll(resolveVehicleModes(rec.getVehicleModes()));
        }
        if (!isNullOrEmpty(rec.getPublishedLineNames())) {
            vehicleJourney.getPublishedLineNames().addAll(
                    setTranslatedValues(NaturalLanguageStringStructure.class, rec.getPublishedLineNames())
            );
        }
        if (rec.getOperatorRef() != null) {
            vehicleJourney.setOperatorRef(setValue(OperatorRefStructure.class, rec.getOperatorRef()));
        }
        if (rec.getOriginRef() != null) {
            vehicleJourney.setOriginRef(setValue(JourneyPlaceRefStructure.class, rec.getOriginRef()));
        }
        if (!isNullOrEmpty(rec.getOriginNames())) {
            vehicleJourney.getOriginNames().addAll(setTranslatedValues(NaturalLanguagePlaceNameStructure.class, rec.getOriginNames()));
        }
        if (rec.getDestinationRef() != null) {
            vehicleJourney.setDestinationRef(setValue(DestinationRef.class, rec.getDestinationRef()));
        }
        if (!isNullOrEmpty(rec.getDestinationNames())) {
            vehicleJourney.getDestinationNames().addAll(setTranslatedValues(NaturalLanguageStringStructure.class, rec.getDestinationNames()));
        }
        if (rec.getOriginAimedDepartureTime() != null) {
            vehicleJourney.setOriginAimedDepartureTime(convertDate(rec.getOriginAimedDepartureTime()));
        }
        if (rec.getDestinationAimedArrivalTime() != null) {
            vehicleJourney.setDestinationAimedArrivalTime(convertDate(rec.getDestinationAimedArrivalTime()));
        }
        vehicleJourney.setMonitored(rec.getMonitored());
        if (rec.getDataSource() != null) {
            vehicleJourney.setDataSource((String)rec.getDataSource());
        }
        if (rec.getVehicleLocation() != null) {
            vehicleJourney.setVehicleLocation(convert(rec.getVehicleLocation()));
        }
        if (rec.getLocationRecordedAtTime() != null) {
            vehicleJourney.setLocationRecordedAtTime(convertDate(rec.getLocationRecordedAtTime()));
        }
        if (rec.getBearing() != null) {
            vehicleJourney.setBearing(rec.getBearing());
        }
        if (rec.getVelocity() != null) {
            vehicleJourney.setVelocity(BigInteger.valueOf(rec.getVelocity()));
        }
        if (rec.getOccupancy() != null) {
            vehicleJourney.setOccupancy(convert(rec.getOccupancy()));
        }
        if (rec.getDelay() != null) {
            vehicleJourney.setDelay(convertDuration(rec.getDelay()));
        }
        vehicleJourney.setInCongestion(rec.getInCongestion());
        if (rec.getVehicleStatus() != null) {
            vehicleJourney.setVehicleStatus(convert(rec.getVehicleStatus()));
        }
        if (rec.getVehicleJourneyRef() != null) {
            vehicleJourney.setVehicleJourneyRef(setValue(VehicleJourneyRef.class, rec.getVehicleJourneyRef()));
        }
        if (rec.getVehicleRef() != null) {
            vehicleJourney.setVehicleRef(setValue(VehicleRef.class, rec.getVehicleRef()));
        }
        if (rec.getMonitoredCall() != null) {
            vehicleJourney.setMonitoredCall(convert(rec.getMonitoredCall()));
        }
        vehicleJourney.setIsCompleteStopSequence(rec.getIsCompleteStopSequence());
        return vehicleJourney;
    }

    private static MonitoredCallStructure convert(CallRecord call) {
        MonitoredCallStructure monitoredCallStructure = new MonitoredCallStructure();
        if (call.getStopPointRef() != null) {
            monitoredCallStructure.setStopPointRef(setValue(StopPointRefStructure.class, call.getStopPointRef()));
        }
        if (call.getOrder() != null) {
            monitoredCallStructure.setOrder(BigInteger.valueOf(call.getOrder()));
        }
        if (!isNullOrEmpty(call.getStopPointNames())) {
            monitoredCallStructure.getStopPointNames()
                    .addAll(setTranslatedValues(NaturalLanguageStringStructure.class, call.getStopPointNames()));
        }
        if (call.getVehicleAtStop() != null) {
            monitoredCallStructure.setVehicleAtStop(call.getVehicleAtStop());
        }
        if (call.getVehicleLocationAtStop() != null) {
            monitoredCallStructure.setVehicleLocationAtStop(convert(call.getVehicleLocationAtStop()));
        }
        if (!isNullOrEmpty(call.getDestinationDisplays())) {
            monitoredCallStructure.getDestinationDisplaies()
                    .addAll(setTranslatedValues(NaturalLanguageStringStructure.class, call.getDestinationDisplays()));
        }
        return monitoredCallStructure;
    }

    private static LocationStructure convert(LocationRecord location) {
        LocationStructure loc = new LocationStructure();
        if (location.getSrsName() != null) {
            loc.setSrsName((String) location.getSrsName());
        }
        if (location.getLatitude() != null) {
            loc.setLatitude(convert(location.getLatitude()));
        }
        if (location.getLongitude() != null) {
            loc.setLongitude(convert(location.getLongitude()));
        }
        return loc;
    }

    private static List<VehicleModesEnumeration> resolveVehicleModes(List<VehicleModeEnum> vehicleModes) {
        if (vehicleModes == null) {
            return null;
        }
        List<VehicleModesEnumeration> modes = new ArrayList<>();
        for (VehicleModeEnum vehicleMode : vehicleModes) {
            modes.add(VehicleModesEnumeration.valueOf(vehicleMode.name()));
        }
        return modes;
    }
}
