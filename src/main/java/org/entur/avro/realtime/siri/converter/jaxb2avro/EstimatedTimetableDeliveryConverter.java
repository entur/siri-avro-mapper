package org.entur.avro.realtime.siri.converter.jaxb2avro;

import org.entur.avro.realtime.siri.model.AlightingActivityEnum;
import org.entur.avro.realtime.siri.model.BoardingActivityEnum;
import org.entur.avro.realtime.siri.model.CallStatusEnum;
import org.entur.avro.realtime.siri.model.EstimatedCallRecord;
import org.entur.avro.realtime.siri.model.EstimatedJourneyVersionFrameRecord;
import org.entur.avro.realtime.siri.model.EstimatedTimetableDeliveryRecord;
import org.entur.avro.realtime.siri.model.EstimatedVehicleJourneyRecord;
import org.entur.avro.realtime.siri.model.FramedVehicleJourneyRefRecord;
import org.entur.avro.realtime.siri.model.OccupancyEnum;
import org.entur.avro.realtime.siri.model.PassengerCapacityRecord;
import org.entur.avro.realtime.siri.model.RecordedCallRecord;
import org.entur.avro.realtime.siri.model.StopAssignmentRecord;
import org.entur.avro.realtime.siri.model.VehicleOccupancyRecord;
import org.w3c.dom.Element;
import uk.org.siri.siri21.ArrivalBoardingActivityEnumeration;
import uk.org.siri.siri21.CallStatusEnumeration;
import uk.org.siri.siri21.DepartureBoardingActivityEnumeration;
import uk.org.siri.siri21.EstimatedCall;
import uk.org.siri.siri21.EstimatedTimetableDeliveryStructure;
import uk.org.siri.siri21.EstimatedVehicleJourney;
import uk.org.siri.siri21.EstimatedVersionFrameStructure;
import uk.org.siri.siri21.Extensions;
import uk.org.siri.siri21.FramedVehicleJourneyRefStructure;
import uk.org.siri.siri21.PassengerCapacityStructure;
import uk.org.siri.siri21.RecordedCall;
import uk.org.siri.siri21.StopAssignmentStructure;
import uk.org.siri.siri21.VehicleOccupancyStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EstimatedTimetableDeliveryConverter extends Jaxb2AvroEnumConverter {

    static List<EstimatedTimetableDeliveryRecord> convert(List<EstimatedTimetableDeliveryStructure> estimatedTimetableDeliveries) {
        if (estimatedTimetableDeliveries == null || estimatedTimetableDeliveries.isEmpty()) {
            return null;
        }

        List<EstimatedTimetableDeliveryRecord> records = new ArrayList<>();

        for (EstimatedTimetableDeliveryStructure delivery : estimatedTimetableDeliveries) {

            List<EstimatedJourneyVersionFrameRecord> estimatedJourneyVersionFrames = new ArrayList<>();
            for (EstimatedVersionFrameStructure versionFrame : delivery.getEstimatedJourneyVersionFrames()) {

                estimatedJourneyVersionFrames.add(
                            EstimatedJourneyVersionFrameRecord.newBuilder()
                                    .setRecordedAtTime(convert(versionFrame.getRecordedAtTime()))
                                    .setEstimatedVehicleJourneys(convertJourneys(versionFrame.getEstimatedVehicleJourneies()))
                                    .build()
                    );
            }

            records.add(
                EstimatedTimetableDeliveryRecord.newBuilder()
                        .setVersion(delivery.getVersion())
                        .setResponseTimestamp(convert(delivery.getResponseTimestamp()))
                        .setEstimatedJourneyVersionFrames(estimatedJourneyVersionFrames)
                        .build()
            );
        }

        return records;
    }

    static List<EstimatedVehicleJourneyRecord> convertJourneys(List<EstimatedVehicleJourney> estimatedVehicleJourneys) {
        if (estimatedVehicleJourneys == null) {
            return Collections.emptyList();
        }

        List<EstimatedVehicleJourneyRecord> records = new ArrayList<>();
        for (EstimatedVehicleJourney estimatedVehicleJourney : estimatedVehicleJourneys) {
            records.add(convert(estimatedVehicleJourney));
        }
        return records;
    }

    static EstimatedVehicleJourneyRecord convert(EstimatedVehicleJourney estimatedVehicleJourney) {
        return EstimatedVehicleJourneyRecord.newBuilder()
                .setRecordedAtTime(convert(estimatedVehicleJourney.getRecordedAtTime()))
                .setLineRef(getValue(estimatedVehicleJourney.getLineRef()))
                .setCancellation(estimatedVehicleJourney.isCancellation())
                .setExtraJourney(estimatedVehicleJourney.isExtraJourney())
                .setPredictionInaccurate(estimatedVehicleJourney.isPredictionInaccurate())
                .setDirectionRef(getValue(estimatedVehicleJourney.getDirectionRef()))
                .setDatedVehicleJourneyRef(getValue(estimatedVehicleJourney.getDatedVehicleJourneyRef()))
                .setFramedVehicleJourneyRef(convert(estimatedVehicleJourney.getFramedVehicleJourneyRef()))
                .setEstimatedVehicleJourneyCode(estimatedVehicleJourney.getEstimatedVehicleJourneyCode())
                .setJourneyPatternRef(getValue(estimatedVehicleJourney.getJourneyPatternRef()))
                .setRouteRef(getValue(estimatedVehicleJourney.getRouteRef()))
                .setPublishedLineNames(convertNames(estimatedVehicleJourney.getPublishedLineNames()))
                .setDestinationDisplayAtOrigins(convertNames(estimatedVehicleJourney.getDestinationDisplayAtOrigins()))
                .setGroupOfLinesRef(getValue(estimatedVehicleJourney.getGroupOfLinesRef()))
                .setExternalLineRef(getValue(estimatedVehicleJourney.getExternalLineRef()))
                .setVehicleModes(convertVehicleModes(estimatedVehicleJourney.getVehicleModes()))
                .setOriginNames(convertNames(estimatedVehicleJourney.getOriginNames()))
                .setOriginRef(getValue(estimatedVehicleJourney.getOriginRef()))
                .setDestinationNames(convertNames(estimatedVehicleJourney.getDestinationNames()))
                .setDestinationRef(getValue(estimatedVehicleJourney.getDestinationRef()))
                .setOperatorRef(getValue(estimatedVehicleJourney.getOperatorRef()))
                .setOriginAimedDepartureTime(convert(estimatedVehicleJourney.getOriginAimedDepartureTime()))
                .setDestinationAimedArrivalTime(convert(estimatedVehicleJourney.getDestinationAimedArrivalTime()))
                .setVehicleFeatureRefs(getValues(estimatedVehicleJourney.getVehicleFeatureReves()))
                .setServiceFeatureRefs(getValues(estimatedVehicleJourney.getServiceFeatureReves()))
                .setProductCategoryRef(getValue(estimatedVehicleJourney.getProductCategoryRef()))
                .setMonitored(estimatedVehicleJourney.isMonitored())
                .setExtraJourney(estimatedVehicleJourney.isExtraJourney())
                .setDataSource(estimatedVehicleJourney.getDataSource())
                .setOccupancy(convert(estimatedVehicleJourney.getOccupancy()))
                .setBlockRef(getValue(estimatedVehicleJourney.getBlockRef()))
                .setVehicleJourneyRef(getValue(estimatedVehicleJourney.getVehicleJourneyRef()))
                .setAdditionalVehicleJourneyRef(convertFramedVehicleJourneys(estimatedVehicleJourney.getAdditionalVehicleJourneyReves()))
                .setVehicleRef(getValue(estimatedVehicleJourney.getVehicleRef()))
                .setRecordedCalls(convert(estimatedVehicleJourney.getRecordedCalls()))
                .setEstimatedCalls(convert(estimatedVehicleJourney.getEstimatedCalls()))
                .setIsCompleteStopSequence(estimatedVehicleJourney.isIsCompleteStopSequence())
                .build();

    }

    private static List<FramedVehicleJourneyRefRecord> convertFramedVehicleJourneys(List<FramedVehicleJourneyRefStructure> refs) {
        if (refs == null || refs.isEmpty()) {
            return Collections.emptyList();
        }
        List<FramedVehicleJourneyRefRecord> records = new ArrayList<>();
        for (FramedVehicleJourneyRefStructure ref : refs) {
            records.add(convert(ref));
        }

        return records;
    }

    private static List<EstimatedCallRecord> convert(EstimatedVehicleJourney.EstimatedCalls estimatedCalls) {
        List<EstimatedCallRecord> calls = new ArrayList<>();
        if (estimatedCalls != null &&
                estimatedCalls.getEstimatedCalls() != null &&
                !estimatedCalls.getEstimatedCalls().isEmpty()) {
            for (EstimatedCall call : estimatedCalls.getEstimatedCalls()) {
                calls.add(convert(call));
            }
        }
        return calls;
    }

    private static List<RecordedCallRecord> convert(EstimatedVehicleJourney.RecordedCalls recordedCalls) {
        List<RecordedCallRecord> calls = new ArrayList<>();
        if (recordedCalls != null &&
                recordedCalls.getRecordedCalls() != null &&
                !recordedCalls.getRecordedCalls().isEmpty()) {
            for (RecordedCall call : recordedCalls.getRecordedCalls()) {
                calls.add(convert(call));
            }
        }
        return calls;
    }

    private static RecordedCallRecord convert(RecordedCall call) {

        return RecordedCallRecord.newBuilder()
                .setStopPointRef(getValue(call.getStopPointRef()))
                .setStopPointNames(convertNames(call.getStopPointNames()))
                .setOrder(convert(call.getOrder()))
                .setCancellation(call.isCancellation())
                .setRequestStop(call.isRequestStop())
                .setExtraCall(call.isExtraCall())
                .setPredictionInaccurate(call.isPredictionInaccurate())
                .setOccupancy(convert(call.getOccupancy()))
                .setDestinationDisplays(convertNames(call.getDestinationDisplaies()))

                .setAimedArrivalTime(convert(call.getAimedArrivalTime()))
                .setExpectedArrivalTime(convert(call.getExpectedArrivalTime()))
                .setActualArrivalTime(convert(call.getActualArrivalTime()))
                .setArrivalPlatformName(getValue(call.getArrivalPlatformName()))
                .setArrivalStatus(convert(call.getArrivalStatus()))
                .setArrivalBoardingActivity(convert(call.getArrivalBoardingActivity()))
                .setArrivalStopAssignments(convertStopAssignments(call.getArrivalStopAssignments()))

                .setAimedDepartureTime(convert(call.getAimedDepartureTime()))
                .setExpectedDepartureTime(convert(call.getExpectedDepartureTime()))
                .setActualDepartureTime(convert(call.getActualDepartureTime()))
                .setDeparturePlatformName(getValue(call.getDeparturePlatformName()))
                .setDepartureStatus(convert(call.getDepartureStatus()))
                .setDepartureBoardingActivity(convert(call.getDepartureBoardingActivity()))
                .setDepartureStopAssignments(convertStopAssignments(call.getDepartureStopAssignments()))
                .setRecordedDepartureOccupancies(convertOccupancies(call.getRecordedDepartureOccupancies()))
                .setRecordedDepartureCapacities(convertCapacities(call.getRecordedDepartureCapacities()))
//                .setExtensions(convert(call.getExtensions()))
                .build();
    }

    private static BoardingActivityEnum convert(DepartureBoardingActivityEnumeration departureBoardingActivity) {
        if (departureBoardingActivity == null) {
            return null;
        }
        return BoardingActivityEnum.valueOf(departureBoardingActivity.name());
    }

    private static AlightingActivityEnum convert(ArrivalBoardingActivityEnumeration arrivalBoardingActivity) {
        if (arrivalBoardingActivity == null) {
            return null;
        }
        return AlightingActivityEnum.valueOf(arrivalBoardingActivity.name());
    }

    static EstimatedCallRecord convert(EstimatedCall call) {
        if (call == null) {
            return null;
        }
        return EstimatedCallRecord.newBuilder()
                .setStopPointRef(getValue(call.getStopPointRef()))
                .setStopPointNames(convertNames(call.getStopPointNames()))
                .setOrder(convert(call.getOrder()))
                .setCancellation(call.isCancellation())
                .setRequestStop(call.isRequestStop())
                .setExtraCall(call.isExtraCall())
                .setPredictionInaccurate(call.isPredictionInaccurate())
                .setOccupancy(call.getOccupancy() != null ? OccupancyEnum.valueOf(call.getOccupancy().name()):null)
                .setDestinationDisplays(convertNames(call.getDestinationDisplaies()))

                .setAimedArrivalTime(convert(call.getAimedArrivalTime()))
                .setExpectedArrivalTime(convert(call.getExpectedArrivalTime()))
                .setArrivalPlatformName(getValue(call.getArrivalPlatformName()))
                .setArrivalStatus(convert(call.getArrivalStatus()))
                .setArrivalBoardingActivity(convert(call.getArrivalBoardingActivity()))
                .setArrivalStopAssignments(convertStopAssignments(call.getArrivalStopAssignments()))

                .setAimedDepartureTime(convert(call.getAimedDepartureTime()))
                .setExpectedDepartureTime(convert(call.getExpectedDepartureTime()))
                .setDeparturePlatformName(getValue(call.getDeparturePlatformName()))
                .setDepartureStatus(convert(call.getDepartureStatus()))
                .setDepartureBoardingActivity(convert(call.getDepartureBoardingActivity()))
                .setDepartureStopAssignments(convertStopAssignments(call.getDepartureStopAssignments()))
                .setExpectedDepartureOccupancies(convertOccupancies(call.getExpectedDepartureOccupancies()))
                .setExpectedDepartureCapacities(convertCapacities(call.getExpectedDepartureCapacities()))
//                .setExtensions(convert(call.getExtensions()))
                .build();
    }

    private static List<PassengerCapacityRecord> convertCapacities(List<PassengerCapacityStructure> passengerCapacities) {
        if (passengerCapacities != null && !passengerCapacities.isEmpty()) {
            List<PassengerCapacityRecord> records = new ArrayList<>();
            for (PassengerCapacityStructure capacityStructure : passengerCapacities) {
                records.add(convert(capacityStructure));
            }
        }
        return Collections.emptyList();
    }

    private static PassengerCapacityRecord convert(PassengerCapacityStructure capacity) {
        return PassengerCapacityRecord.newBuilder()
                .setCompoundTrainRef(getValue(capacity.getCompoundTrainRef()))
                .setTrainRef(getValue(capacity.getTrainRef()))
                .setTrainComponentRef(getValue(capacity.getTrainComponentRef()))
                .setEntranceToVehicleRef(getValue(capacity.getEntranceToVehicleRef()))
                .setPassengerCategory(getValue(capacity.getPassengerCategory()))
                .setTotalCapacity(convert(capacity.getTotalCapacity()))
                .setSeatingCapacity(convert(capacity.getSeatingCapacity()))
                .setStandingCapacity(convert(capacity.getStandingCapacity()))
                .setPushchairCapacity(convert(capacity.getPushchairCapacity()))
                .setWheelchairPlaceCapacity(convert(capacity.getWheelchairPlaceCapacity()))
                .setPramPlaceCapacity(convert(capacity.getPramPlaceCapacity()))
                .setBicycleRackCapacity(convert(capacity.getBicycleRackCapacity()))
                .build();
    }

    private static List<VehicleOccupancyRecord> convertOccupancies(List<VehicleOccupancyStructure> occupancies) {
        if (occupancies != null && !occupancies.isEmpty()) {
            List<VehicleOccupancyRecord> records = new ArrayList<>();
            for (VehicleOccupancyStructure occupancy : occupancies) {
                records.add(convert(occupancy));
            }
        }
        return Collections.emptyList();
    }

    private static VehicleOccupancyRecord convert(VehicleOccupancyStructure occupancy) {
        return VehicleOccupancyRecord.newBuilder()
                .setCompoundTrainRef(getValue(occupancy.getCompoundTrainRef()))
                .setTrainRef(getValue(occupancy.getTrainRef()))
                .setTrainComponentRef(getValue(occupancy.getTrainComponentRef()))
                .setEntranceToVehicleRef(getValue(occupancy.getEntranceToVehicleRef()))
                .setPassengerCategory(getValue(occupancy.getPassengerCategory()))
                .setOccupancyLevel(convert(occupancy.getOccupancyLevel()))
                .setOccupancyPercentage(convert(occupancy.getOccupancyPercentage()))
                .setAlightingCount(convert(occupancy.getAlightingCount()))
                .setBoardingCount(convert(occupancy.getBoardingCount()))
                .setOnboardCount(convert(occupancy.getOnboardCount()))
                .setPushchairsOnboardCount(convert(occupancy.getPushchairsOnboardCount()))
                .setWheelchairsOnboardCount(convert(occupancy.getWheelchairsOnboardCount()))
                .setPramsOnboardCount(convert(occupancy.getPramsOnboardCount()))
                .setBicycleOnboardCount(convert(occupancy.getBicycleOnboardCount()))
                .setTotalNumberOfReservedSeats(convert(occupancy.getTotalNumberOfReservedSeats()))
                .build()
                ;
    }

    private static Map<CharSequence, CharSequence> convert(Extensions extensions) {
        Map<CharSequence, CharSequence> extensionMap = new HashMap<>();
        if (extensions != null && extensions.getAnies() != null) {
            for (Element any : extensions.getAnies()) {
                String localName = any.getLocalName();
                String nodeValue = any.getFirstChild().getNodeValue();

                extensionMap.put(localName, nodeValue);
            }
        }
        return extensionMap;
    }

    private static List<StopAssignmentRecord> convertStopAssignments(List<StopAssignmentStructure> stopAssignments) {
        if (stopAssignments != null && !stopAssignments.isEmpty()) {
            List<StopAssignmentRecord> records = new ArrayList<>();
            for (StopAssignmentStructure stopAssignment : stopAssignments) {
                records.add(StopAssignmentRecord.newBuilder()
                        .setAimedQuayRef(getValue(stopAssignment.getAimedQuayRef()))
                        .setExpectedQuayRef(getValue(stopAssignment.getExpectedQuayRef()))
                        .build()
                );
            }
            return records;
        }
        return Collections.emptyList();
    }

    private static CallStatusEnum convert(CallStatusEnumeration arrivalStatus) {
        if (arrivalStatus == null) {
            return null;
        }
        return CallStatusEnum.valueOf(arrivalStatus.name());
    }

}
