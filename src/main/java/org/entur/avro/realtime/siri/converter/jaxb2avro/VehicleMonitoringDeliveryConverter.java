package org.entur.avro.realtime.siri.converter.jaxb2avro;

import org.entur.avro.realtime.siri.model.CallRecord;
import org.entur.avro.realtime.siri.model.LocationRecord;
import org.entur.avro.realtime.siri.model.MonitoredVehicleJourneyRecord;
import org.entur.avro.realtime.siri.model.ProgressBetweenStopsRecord;
import org.entur.avro.realtime.siri.model.VehicleActivityRecord;
import org.entur.avro.realtime.siri.model.VehicleMonitoringDeliveryRecord;
import uk.org.siri.siri21.LocationStructure;
import uk.org.siri.siri21.MonitoredCallStructure;
import uk.org.siri.siri21.ProgressBetweenStopsStructure;
import uk.org.siri.siri21.VehicleActivityStructure;
import uk.org.siri.siri21.VehicleMonitoringDeliveryStructure;

import java.util.ArrayList;
import java.util.List;

public class VehicleMonitoringDeliveryConverter extends Jaxb2AvroEnumConverter {


    static List<VehicleMonitoringDeliveryRecord> convert(List<VehicleMonitoringDeliveryStructure> deliveries) {
        if (deliveries == null) {
            return null;
        }

        List<VehicleMonitoringDeliveryRecord> records = new ArrayList<>();
        for (VehicleMonitoringDeliveryStructure vehicleActivityStructure : deliveries) {
            records.add(convert(vehicleActivityStructure));
        }
        return records;
    }

    static VehicleMonitoringDeliveryRecord convert(VehicleMonitoringDeliveryStructure deliveryStructure) {
        return VehicleMonitoringDeliveryRecord.newBuilder()
                .setVersion(deliveryStructure.getVersion())
                .setResponseTimestamp(convert(deliveryStructure.getResponseTimestamp()))
                .setVehicleActivities(convertVehicleActivities(deliveryStructure.getVehicleActivities()))
                .build();
    }

    private static List<VehicleActivityRecord> convertVehicleActivities(List<VehicleActivityStructure> vehicleActivities) {
        List<VehicleActivityRecord> records = new ArrayList<>();
        for (VehicleActivityStructure vehicleActivityStructure : vehicleActivities) {
            records.add(convert(vehicleActivityStructure));
        }
        return records;
    }

    static VehicleActivityRecord convert(VehicleActivityStructure vehicleActivityStructure) {
        if (vehicleActivityStructure == null) {
            return null;
        }
        return VehicleActivityRecord.newBuilder()
                .setRecordedAtTime(convert(vehicleActivityStructure.getRecordedAtTime()))
                .setValidUntilTime(convert(vehicleActivityStructure.getValidUntilTime()))
                .setProgressBetweenStops(convert(vehicleActivityStructure.getProgressBetweenStops()))
                .setMonitoredVehicleJourney(convert(vehicleActivityStructure.getMonitoredVehicleJourney()))
                .build();

    }

    private static MonitoredVehicleJourneyRecord convert(VehicleActivityStructure.MonitoredVehicleJourney monitoredVehicleJourney) {
        if (monitoredVehicleJourney == null) {
            return null;
        }
        return MonitoredVehicleJourneyRecord.newBuilder()
                .setLineRef(getValue(monitoredVehicleJourney.getLineRef()))
                .setDirectionRef(getValue(monitoredVehicleJourney.getDirectionRef()))
                .setFramedVehicleJourneyRef(convert(monitoredVehicleJourney.getFramedVehicleJourneyRef()))
                .setVehicleModes(convertVehicleModes(monitoredVehicleJourney.getVehicleModes()))
                .setPublishedLineNames(getTranslatedValues(monitoredVehicleJourney.getPublishedLineNames()))
                .setOperatorRef(getValue(monitoredVehicleJourney.getOperatorRef()))
                .setOriginRef(getValue(monitoredVehicleJourney.getOriginRef()))
                .setOriginNames(convertNames(monitoredVehicleJourney.getOriginNames()))
                .setDestinationRef(getValue(monitoredVehicleJourney.getDestinationRef()))
                .setDestinationNames(convertNames(monitoredVehicleJourney.getDestinationNames()))
                .setOriginAimedDepartureTime(convert(monitoredVehicleJourney.getOriginAimedDepartureTime()))
                .setDestinationAimedArrivalTime(convert(monitoredVehicleJourney.getDestinationAimedArrivalTime()))
                .setMonitored(monitoredVehicleJourney.isMonitored())
                .setDataSource(monitoredVehicleJourney.getDataSource())
                .setVehicleLocation(convert(monitoredVehicleJourney.getVehicleLocation()))
                .setLocationRecordedAtTime(convert(monitoredVehicleJourney.getLocationRecordedAtTime()))
                .setBearing(monitoredVehicleJourney.getBearing())
                .setVelocity(convert(monitoredVehicleJourney.getVelocity()))
                .setOccupancy(convert(monitoredVehicleJourney.getOccupancy()))
                .setDelay(convert(monitoredVehicleJourney.getDelay()))
                .setInCongestion(monitoredVehicleJourney.isInCongestion())
                .setVehicleStatus(convert(monitoredVehicleJourney.getVehicleStatus()))
                .setVehicleJourneyRef(getValue(monitoredVehicleJourney.getVehicleJourneyRef()))
                .setVehicleRef(getValue(monitoredVehicleJourney.getVehicleRef()))
                .setMonitoredCall(convert(monitoredVehicleJourney.getMonitoredCall()))
                .setIsCompleteStopSequence(monitoredVehicleJourney.isIsCompleteStopSequence())
                .build();
    }

    private static ProgressBetweenStopsRecord convert(ProgressBetweenStopsStructure progressBetweenStops) {
        if (progressBetweenStops == null) {
            return null;
        }
        return ProgressBetweenStopsRecord.newBuilder()
                .setLinkDistance(convert(progressBetweenStops.getLinkDistance()))
                .setPercentage(convert(progressBetweenStops.getPercentage()))
                .build();
    }

    private static CallRecord convert(MonitoredCallStructure monitoredCall) {
        if (monitoredCall == null) {
            return null;
        }

        return CallRecord.newBuilder()
                .setStopPointRef(getValue(monitoredCall.getStopPointRef()))
                .setOrder(convert(monitoredCall.getOrder()))
                .setStopPointNames(convertNames(monitoredCall.getStopPointNames()))
                .setVehicleAtStop(monitoredCall.isVehicleAtStop())
                .setVehicleLocationAtStop(convert(monitoredCall.getVehicleLocationAtStop()))
                .setDestinationDisplays(convertNames(monitoredCall.getDestinationDisplaies()))
                .build();
    }

    private static LocationRecord convert(LocationStructure vehicleLocation) {
        if (vehicleLocation == null) {
            return null;
        }
        return LocationRecord.newBuilder()
                .setSrsName(vehicleLocation.getSrsName())
                .setLatitude(convert(vehicleLocation.getLatitude()))
                .setLongitude(convert(vehicleLocation.getLongitude()))
                .build() ;
    }
}
