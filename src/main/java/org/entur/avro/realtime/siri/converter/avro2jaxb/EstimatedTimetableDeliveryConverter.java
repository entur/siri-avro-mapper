package org.entur.avro.realtime.siri.converter.avro2jaxb;

import org.entur.avro.realtime.siri.model.EstimatedCallRecord;
import org.entur.avro.realtime.siri.model.EstimatedJourneyVersionFrameRecord;
import org.entur.avro.realtime.siri.model.EstimatedTimetableDeliveryRecord;
import org.entur.avro.realtime.siri.model.EstimatedVehicleJourneyRecord;
import org.entur.avro.realtime.siri.model.FramedVehicleJourneyRefRecord;
import org.entur.avro.realtime.siri.model.PassengerCapacityRecord;
import org.entur.avro.realtime.siri.model.RecordedCallRecord;
import org.entur.avro.realtime.siri.model.StopAssignmentRecord;
import org.entur.avro.realtime.siri.model.VehicleModeEnum;
import org.entur.avro.realtime.siri.model.VehicleOccupancyRecord;
import uk.org.siri.siri21.ArrivalBoardingActivityEnumeration;
import uk.org.siri.siri21.BlockRefStructure;
import uk.org.siri.siri21.CallStatusEnumeration;
import uk.org.siri.siri21.CompoundTrainRef;
import uk.org.siri.siri21.DatedVehicleJourneyRef;
import uk.org.siri.siri21.DepartureBoardingActivityEnumeration;
import uk.org.siri.siri21.DestinationRef;
import uk.org.siri.siri21.DirectionRefStructure;
import uk.org.siri.siri21.EntranceToVehicleRef;
import uk.org.siri.siri21.EstimatedCall;
import uk.org.siri.siri21.EstimatedTimetableDeliveryStructure;
import uk.org.siri.siri21.EstimatedVehicleJourney;
import uk.org.siri.siri21.EstimatedVersionFrameStructure;
import uk.org.siri.siri21.GroupOfLinesRefStructure;
import uk.org.siri.siri21.JourneyPatternRef;
import uk.org.siri.siri21.JourneyPlaceRefStructure;
import uk.org.siri.siri21.LineRef;
import uk.org.siri.siri21.NaturalLanguagePlaceNameStructure;
import uk.org.siri.siri21.NaturalLanguageStringStructure;
import uk.org.siri.siri21.OperatorRefStructure;
import uk.org.siri.siri21.PassengerCapacityStructure;
import uk.org.siri.siri21.ProductCategoryRefStructure;
import uk.org.siri.siri21.QuayRefStructure;
import uk.org.siri.siri21.RecordedCall;
import uk.org.siri.siri21.RouteRefStructure;
import uk.org.siri.siri21.ServiceFeatureRef;
import uk.org.siri.siri21.StopAssignmentStructure;
import uk.org.siri.siri21.StopPointRefStructure;
import uk.org.siri.siri21.TrainComponentRef;
import uk.org.siri.siri21.TrainRef;
import uk.org.siri.siri21.VehicleFeatureRefStructure;
import uk.org.siri.siri21.VehicleJourneyRef;
import uk.org.siri.siri21.VehicleModesEnumeration;
import uk.org.siri.siri21.VehicleOccupancyStructure;
import uk.org.siri.siri21.VehicleRef;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class EstimatedTimetableDeliveryConverter extends Avro2JaxbEnumConverter {
    static List<EstimatedTimetableDeliveryStructure> convert(List<EstimatedTimetableDeliveryRecord> estimatedTimetableDeliveries) {
        List<EstimatedTimetableDeliveryStructure> results = new ArrayList<>();

        for (EstimatedTimetableDeliveryRecord delivery : estimatedTimetableDeliveries) {

            EstimatedTimetableDeliveryStructure estimatedTimetableDeliveryStructure = new EstimatedTimetableDeliveryStructure();
            estimatedTimetableDeliveryStructure.setResponseTimestamp(convertDate(delivery.getResponseTimestamp()));
            estimatedTimetableDeliveryStructure.setVersion((String) delivery.getVersion());

            for (EstimatedJourneyVersionFrameRecord versionFrame : delivery.getEstimatedJourneyVersionFrames()) {
                estimatedTimetableDeliveryStructure
                        .getEstimatedJourneyVersionFrames()
                        .add(convert(versionFrame));
            }
            results.add(estimatedTimetableDeliveryStructure);
        }

        return results;
    }

    private static EstimatedVersionFrameStructure convert(EstimatedJourneyVersionFrameRecord versionFrame) {
        EstimatedVersionFrameStructure estimatedVersionFrameStructure = new EstimatedVersionFrameStructure();
        estimatedVersionFrameStructure.setRecordedAtTime(convertDate(versionFrame.getRecordedAtTime()));
        estimatedVersionFrameStructure
                .getEstimatedVehicleJourneies()
                .addAll(convertVehicleJourneys(
                        versionFrame.getEstimatedVehicleJourneys())
                );
        return estimatedVersionFrameStructure;
    }

    private static List<EstimatedVehicleJourney> convertVehicleJourneys(List<EstimatedVehicleJourneyRecord> estimatedVehicleJourneies) {

        List<EstimatedVehicleJourney> estimatedVehicleJourneyRecords = new ArrayList<>();
        for (EstimatedVehicleJourneyRecord estimatedVehicleJourney : estimatedVehicleJourneies) {
            estimatedVehicleJourneyRecords.add(convertJourney(estimatedVehicleJourney));
        }

        return estimatedVehicleJourneyRecords;
    }

    private static EstimatedVehicleJourney convertJourney(EstimatedVehicleJourneyRecord estimatedVehicleJourney) {
        EstimatedVehicleJourney mapped = new EstimatedVehicleJourney();

        if (estimatedVehicleJourney.getRecordedAtTime() != null) {
            mapped.setRecordedAtTime(convertDate(estimatedVehicleJourney.getRecordedAtTime()));
        }
        if (estimatedVehicleJourney.getLineRef() != null) {
            mapped.setLineRef(setValue(LineRef.class, estimatedVehicleJourney.getLineRef()));
        }
        if (estimatedVehicleJourney.getCancellation() != null) {
            mapped.setCancellation(estimatedVehicleJourney.getCancellation());
        }
        if (estimatedVehicleJourney.getExtraJourney() != null) {
            mapped.setExtraJourney(estimatedVehicleJourney.getExtraJourney());
        }
        if (estimatedVehicleJourney.getPredictionInaccurate() != null) {
            mapped.setPredictionInaccurate(estimatedVehicleJourney.getPredictionInaccurate());
        }
        if (estimatedVehicleJourney.getDirectionRef() != null) {
            mapped.setDirectionRef(setValue(DirectionRefStructure.class, estimatedVehicleJourney.getDirectionRef()));
        }
        if (estimatedVehicleJourney.getDatedVehicleJourneyRef() != null) {
            mapped.setDatedVehicleJourneyRef(setValue(DatedVehicleJourneyRef.class, estimatedVehicleJourney.getDatedVehicleJourneyRef()));
        }
        if (estimatedVehicleJourney.getFramedVehicleJourneyRef() != null) {
            mapped.setFramedVehicleJourneyRef(convert(estimatedVehicleJourney.getFramedVehicleJourneyRef()));
        }
        if (estimatedVehicleJourney.getEstimatedVehicleJourneyCode() != null) {
            mapped.setEstimatedVehicleJourneyCode((String) estimatedVehicleJourney.getEstimatedVehicleJourneyCode());
        }
        if (estimatedVehicleJourney.getJourneyPatternRef() != null) {
            mapped.setJourneyPatternRef(setValue(
                    JourneyPatternRef.class,
                    estimatedVehicleJourney.getJourneyPatternRef())
            );
        }
        if (estimatedVehicleJourney.getRouteRef() != null) {
            mapped.setRouteRef(setValue(
                    RouteRefStructure.class,
                    estimatedVehicleJourney.getRouteRef())
            );
        }
        if (estimatedVehicleJourney.getPublishedLineNames() != null &&
                !estimatedVehicleJourney.getPublishedLineNames().isEmpty()) {
            mapped.getPublishedLineNames()
                    .addAll(setTranslatedValues(
                            NaturalLanguageStringStructure.class,
                            estimatedVehicleJourney.getPublishedLineNames())
                    );
        }
        if (estimatedVehicleJourney.getDestinationDisplayAtOrigins() != null &&
                !estimatedVehicleJourney.getDestinationDisplayAtOrigins().isEmpty()) {
            mapped.getDestinationDisplayAtOrigins()
                    .addAll(setTranslatedValues(
                            NaturalLanguagePlaceNameStructure.class,
                            estimatedVehicleJourney.getDestinationDisplayAtOrigins())
                    );
        }
        if (estimatedVehicleJourney.getGroupOfLinesRef() != null) {
            mapped.setGroupOfLinesRef(setValue(
                    GroupOfLinesRefStructure.class,
                    estimatedVehicleJourney.getGroupOfLinesRef())
            );
        }
        if (estimatedVehicleJourney.getExternalLineRef() != null) {
            mapped.setExternalLineRef(
                    setValue(
                            LineRef.class,
                            estimatedVehicleJourney.getExternalLineRef())
            );
        }
        if (estimatedVehicleJourney.getVehicleModes() != null) {
            mapped.getVehicleModes().addAll(
                    convertVehicleMode(estimatedVehicleJourney.getVehicleModes())
            );
        }
        if (estimatedVehicleJourney.getOriginNames() != null  &&
                !estimatedVehicleJourney.getOriginNames().isEmpty()) {
            mapped.getOriginNames()
                    .addAll(setTranslatedValues(NaturalLanguagePlaceNameStructure.class,
                            estimatedVehicleJourney.getOriginNames())
                    );
        }
        if (estimatedVehicleJourney.getOriginRef() != null) {
            mapped.setOriginRef(
                    setValue(JourneyPlaceRefStructure.class, estimatedVehicleJourney.getOriginRef()));
        }
        if (estimatedVehicleJourney.getDestinationNames() != null  &&
                !estimatedVehicleJourney.getDestinationNames().isEmpty()) {
            mapped.getDestinationNames().addAll(
                    setTranslatedValues(
                            NaturalLanguageStringStructure.class,
                            estimatedVehicleJourney.getDestinationNames()
                    )
            );
        }
        if (estimatedVehicleJourney.getDestinationRef() != null) {
            mapped.setDestinationRef(setValue(DestinationRef.class, estimatedVehicleJourney.getDestinationRef()));
        }
        if (estimatedVehicleJourney.getOperatorRef() != null) {
            mapped.setOperatorRef(setValue(OperatorRefStructure.class, estimatedVehicleJourney.getOperatorRef()));
        }
        if (estimatedVehicleJourney.getOriginAimedDepartureTime() != null) {
            mapped.setOriginAimedDepartureTime(convertDate(estimatedVehicleJourney.getOriginAimedDepartureTime()));
        }
        if (estimatedVehicleJourney.getDestinationAimedArrivalTime() != null) {
            mapped.setDestinationAimedArrivalTime(convertDate(estimatedVehicleJourney.getDestinationAimedArrivalTime()));
        }
        if (estimatedVehicleJourney.getVehicleFeatureRefs() != null) {
            for (CharSequence ref : estimatedVehicleJourney.getVehicleFeatureRefs()) {
                mapped.getVehicleFeatureReves()
                        .add(
                                setValue(VehicleFeatureRefStructure.class, ref)
                        );
            }
        }
        if (estimatedVehicleJourney.getProductCategoryRef() != null) {
            mapped.setProductCategoryRef(
                    setValue(ProductCategoryRefStructure.class, estimatedVehicleJourney.getProductCategoryRef())
            );
        }
        if (estimatedVehicleJourney.getServiceFeatureRefs() != null) {
            for (CharSequence ref : estimatedVehicleJourney.getServiceFeatureRefs()) {
                mapped.getServiceFeatureReves()
                        .add(
                                setValue(ServiceFeatureRef.class, ref)
                        );
            }
        }
        if (estimatedVehicleJourney.getMonitored() != null) {
            mapped.setMonitored(estimatedVehicleJourney.getMonitored());
        }
        if (estimatedVehicleJourney.getExtraJourney() != null) {
            mapped.setExtraJourney(estimatedVehicleJourney.getExtraJourney());
        }
        if (estimatedVehicleJourney.getDataSource() != null) {
            mapped.setDataSource((String) estimatedVehicleJourney.getDataSource());
        }
        if (estimatedVehicleJourney.getOccupancy() != null) {
            mapped.setOccupancy(convert(estimatedVehicleJourney.getOccupancy()));
        }
        if (estimatedVehicleJourney.getBlockRef() != null) {
            mapped.setBlockRef(
                    setValue(
                            BlockRefStructure.class,
                            estimatedVehicleJourney.getBlockRef())
            );
        }
        if (estimatedVehicleJourney.getVehicleJourneyRef() != null) {
            mapped.setVehicleJourneyRef(
                    setValue(
                            VehicleJourneyRef.class,
                            estimatedVehicleJourney.getVehicleJourneyRef())
            );
        }
        if (estimatedVehicleJourney.getAdditionalVehicleJourneyRef() != null &&
                !estimatedVehicleJourney.getAdditionalVehicleJourneyRef().isEmpty()) {
            for (FramedVehicleJourneyRefRecord refRecord : estimatedVehicleJourney.getAdditionalVehicleJourneyRef()) {
                mapped.getAdditionalVehicleJourneyReves()
                        .add(convert(refRecord));
            }
        }
        if (estimatedVehicleJourney.getVehicleRef() != null) {
            mapped.setVehicleRef(
                    setValue(VehicleRef.class, estimatedVehicleJourney.getVehicleRef()));
        }
        if (estimatedVehicleJourney.getRecordedCalls() != null &&
                !estimatedVehicleJourney.getRecordedCalls().isEmpty()) {
            mapped.setRecordedCalls(convertRecordedCalls(estimatedVehicleJourney.getRecordedCalls()));
        }
        if (estimatedVehicleJourney.getEstimatedCalls() != null &&
                !estimatedVehicleJourney.getEstimatedCalls().isEmpty()) {
            mapped.setEstimatedCalls(convertEstimatedCalls(estimatedVehicleJourney.getEstimatedCalls()));
        }
        if (estimatedVehicleJourney.getIsCompleteStopSequence() != null) {
            mapped.setIsCompleteStopSequence(estimatedVehicleJourney.getIsCompleteStopSequence());
        }
        return mapped;

    }

    private static EstimatedVehicleJourney.RecordedCalls convertRecordedCalls(List<RecordedCallRecord> calls) {
        List<RecordedCall> recordedCallList = new ArrayList<>();
        if (calls != null) {
            for (RecordedCallRecord call : calls) {
                recordedCallList.add(convert(call));
            }
        }

        if (recordedCallList.isEmpty()) {
            return null;
        }
        EstimatedVehicleJourney.RecordedCalls recordedCalls = new EstimatedVehicleJourney.RecordedCalls();
        recordedCalls.getRecordedCalls().addAll(recordedCallList);
        return recordedCalls;
    }

    private static EstimatedVehicleJourney.EstimatedCalls convertEstimatedCalls(List<EstimatedCallRecord> calls) {
        List<EstimatedCall> estimatedCallList = new ArrayList<>();
        if (calls != null && !calls.isEmpty()) {
            for (EstimatedCallRecord call : calls) {
                estimatedCallList.add(convert(call));
            }
        }

        EstimatedVehicleJourney.EstimatedCalls estimatedCalls = new EstimatedVehicleJourney.EstimatedCalls();
        estimatedCalls.getEstimatedCalls().addAll(estimatedCallList);
        return estimatedCalls;
    }

    private static Collection<VehicleModesEnumeration> convertVehicleMode(List<VehicleModeEnum> vehicleModes) {
        List<VehicleModesEnumeration> modes = new ArrayList<>();
        for (VehicleModeEnum vehicleMode : vehicleModes) {
            modes.add(VehicleModesEnumeration.valueOf(vehicleMode.name()));
        }
        return modes;
    }

    private static RecordedCall convert(RecordedCallRecord call) {
        RecordedCall mapped = new RecordedCall();

        mapped.setStopPointRef(setValue(StopPointRefStructure.class, call.getStopPointRef()));

        if (call.getStopPointNames() != null &&
                !call.getStopPointNames().isEmpty()) {
            mapped.getStopPointNames().addAll(setTranslatedValues(NaturalLanguageStringStructure.class,
                    call.getStopPointNames())
            );
        }
        if (call.getOrder() != null) {
            mapped.setOrder(BigInteger.valueOf(call.getOrder()));
        }
        if (call.getCancellation() != null) {
            mapped.setCancellation(call.getCancellation());
        }
        if (call.getRequestStop() != null) {
            mapped.setRequestStop(call.getRequestStop());
        }
        if (call.getExtraCall() != null) {
            mapped.setExtraCall(call.getExtraCall());
        }
        if (call.getDestinationDisplays() != null &&
                !call.getDestinationDisplays().isEmpty()) {
            mapped.getDestinationDisplaies()
                    .addAll(setTranslatedValues(NaturalLanguageStringStructure.class, call.getDestinationDisplays())
                    );
        }
        if (call.getAimedArrivalTime() != null) {
            mapped.setAimedArrivalTime(convertDate(call.getAimedArrivalTime()));
        }
        if (call.getExpectedArrivalTime() != null) {
            mapped.setExpectedArrivalTime(convertDate(call.getExpectedArrivalTime()));
        }
        if (call.getActualArrivalTime() != null) {
            mapped.setActualArrivalTime(convertDate(call.getActualArrivalTime()));
        }
        if (call.getArrivalStatus() != null) {
            mapped.setArrivalStatus(CallStatusEnumeration.valueOf(call.getArrivalStatus().name()));
        }
        if (call.getArrivalBoardingActivity() != null) {
            mapped.setArrivalBoardingActivity(ArrivalBoardingActivityEnumeration.valueOf(call.getArrivalBoardingActivity().name()));
        }
        if (call.getArrivalStopAssignments() != null &&
                !call.getArrivalStopAssignments().isEmpty()) {
            mapped.getArrivalStopAssignments().addAll(convertStopAssignments(call.getArrivalStopAssignments()));
        }

        if (call.getArrivalPlatformName() != null) {
            mapped.setArrivalPlatformName(setValue(NaturalLanguageStringStructure.class, call.getArrivalPlatformName()));
        }
        if (call.getAimedDepartureTime() != null) {
            mapped.setAimedDepartureTime(convertDate(call.getAimedDepartureTime()));
        }
        if (call.getExpectedDepartureTime() != null) {
            mapped.setExpectedDepartureTime(convertDate(call.getExpectedDepartureTime()));
        }
        if (call.getActualDepartureTime() != null) {
            mapped.setActualDepartureTime(convertDate(call.getActualDepartureTime()));
        }
        if (call.getDeparturePlatformName() != null) {
            mapped.setDeparturePlatformName(setValue(NaturalLanguageStringStructure.class, call.getDeparturePlatformName()));
        }
        if (call.getDepartureStatus() != null) {
            mapped.setDepartureStatus(CallStatusEnumeration.valueOf(call.getDepartureStatus().name()));
        }
        if (call.getDepartureBoardingActivity() != null) {
            mapped.setDepartureBoardingActivity(
                    DepartureBoardingActivityEnumeration.valueOf(call.getDepartureBoardingActivity().name())
            );
        }
        if (call.getDepartureStopAssignments() != null &&
                !call.getDepartureStopAssignments().isEmpty()) {
            mapped.getDepartureStopAssignments().addAll(convertStopAssignments(call.getDepartureStopAssignments()));
        }
        if (call.getRecordedDepartureOccupancies() != null &&
                !call.getRecordedDepartureOccupancies().isEmpty()) {
            mapped.getRecordedDepartureOccupancies().addAll(convertOccupancies(call.getRecordedDepartureOccupancies()));
        }

        if (call.getRecordedDepartureCapacities() != null &&
                !call.getRecordedDepartureCapacities().isEmpty()) {
            mapped.getRecordedDepartureCapacities().addAll(convertCapacities(call.getRecordedDepartureCapacities()));
        }


//        if (call.getExtensions() != null && !call.getExtensions().isEmpty()) {
//            mapped.setExtensions(convert(call.getExtensions()));
//        }

        return mapped;
    }

    private static List<PassengerCapacityStructure> convertCapacities(List<PassengerCapacityRecord> capacities) {
        List<PassengerCapacityStructure> capacityStructures = new ArrayList<>();
        if (capacities != null && !capacities.isEmpty()) {
            for (PassengerCapacityRecord capacity : capacities) {
                capacityStructures.add(convert(capacity));
            }
        }

        return capacityStructures;
    }

    private static PassengerCapacityStructure convert(PassengerCapacityRecord capacity) {
        PassengerCapacityStructure mapped = new PassengerCapacityStructure();
        mapped.setCompoundTrainRef(setValue(CompoundTrainRef.class, capacity.getCompoundTrainRef()));
        mapped.setTrainRef(setValue(TrainRef.class, capacity.getTrainRef()));
        mapped.setTrainComponentRef(setValue(TrainComponentRef.class, capacity.getTrainComponentRef()));
        mapped.setEntranceToVehicleRef(setValue(EntranceToVehicleRef.class, capacity.getEntranceToVehicleRef()));
        mapped.setPassengerCategory(setValue(NaturalLanguageStringStructure.class, capacity.getPassengerCategory()));
        mapped.setTotalCapacity(convert(capacity.getTotalCapacity()));
        mapped.setSeatingCapacity(convert(capacity.getSeatingCapacity()));
        mapped.setStandingCapacity(convert(capacity.getStandingCapacity()));
        mapped.setPushchairCapacity(convert(capacity.getPushchairCapacity()));
        mapped.setWheelchairPlaceCapacity(convert(capacity.getWheelchairPlaceCapacity()));
        mapped.setPramPlaceCapacity(convert(capacity.getPramPlaceCapacity()));
        mapped.setBicycleRackCapacity(convert(capacity.getBicycleRackCapacity()));

        return mapped;

    }

    private static List<VehicleOccupancyStructure> convertOccupancies(List<VehicleOccupancyRecord> occupancies) {
        List<VehicleOccupancyStructure> vehicleOccupancies = new ArrayList<>();
        if (occupancies != null && !occupancies.isEmpty()) {
            for (VehicleOccupancyRecord occupancy : occupancies) {
                vehicleOccupancies.add(convert(occupancy));
            }
        }

        return vehicleOccupancies;
    }

    private static VehicleOccupancyStructure convert(VehicleOccupancyRecord occupancy) {
        VehicleOccupancyStructure mapped = new VehicleOccupancyStructure();
        mapped.setCompoundTrainRef(setValue(CompoundTrainRef.class, occupancy.getCompoundTrainRef()));
        mapped.setTrainRef(setValue(TrainRef.class, occupancy.getTrainRef()));
        mapped.setTrainComponentRef(setValue(TrainComponentRef.class, occupancy.getTrainComponentRef()));
        mapped.setEntranceToVehicleRef(setValue(EntranceToVehicleRef.class, occupancy.getEntranceToVehicleRef()));
        mapped.setPassengerCategory(setValue(NaturalLanguageStringStructure.class, occupancy.getPassengerCategory()));
        mapped.setOccupancyLevel(convert(occupancy.getOccupancyLevel()));
        mapped.setOccupancyPercentage(convert(occupancy.getOccupancyPercentage()));
        mapped.setAlightingCount(convert(occupancy.getAlightingCount()));
        mapped.setBoardingCount(convert(occupancy.getBoardingCount()));
        mapped.setOnboardCount(convert(occupancy.getOnboardCount()));
        mapped.setPushchairsOnboardCount(convert(occupancy.getPushchairsOnboardCount()));
        mapped.setWheelchairsOnboardCount(convert(occupancy.getWheelchairsOnboardCount()));
        mapped.setPramsOnboardCount(convert(occupancy.getPramsOnboardCount()));
        mapped.setBicycleOnboardCount(convert(occupancy.getBicycleOnboardCount()));
        mapped.setTotalNumberOfReservedSeats(convert(occupancy.getTotalNumberOfReservedSeats()));

        return mapped;
    }

    private static EstimatedCall convert(EstimatedCallRecord call) {
        EstimatedCall mapped = new EstimatedCall();

        mapped.setStopPointRef(setValue(StopPointRefStructure.class, call.getStopPointRef()));
        if (call.getStopPointNames() != null &&
                !call.getStopPointNames().isEmpty()) {
            mapped.getStopPointNames().addAll(setTranslatedValues(NaturalLanguageStringStructure.class,
                    call.getStopPointNames())
            );
        }
        if (call.getOrder() != null) {
            mapped.setOrder(BigInteger.valueOf(call.getOrder()));
        }
        if (call.getCancellation() != null) {
            mapped.setCancellation(call.getCancellation());
        }
        if (call.getRequestStop() != null) {
            mapped.setRequestStop(call.getRequestStop());
        }
        if (call.getPredictionInaccurate() != null) {
            mapped.setPredictionInaccurate(call.getPredictionInaccurate());
        }
        if (call.getExtraCall() != null) {
            mapped.setExtraCall(call.getExtraCall());
        }
        if (call.getDestinationDisplays() != null &&
                !call.getDestinationDisplays().isEmpty()) {
            mapped.getDestinationDisplaies().addAll(
                    setTranslatedValues(
                            NaturalLanguageStringStructure.class,
                            call.getDestinationDisplays()
                    )
            );
        }
        if (call.getAimedArrivalTime() != null) {
            mapped.setAimedArrivalTime(convertDate(call.getAimedArrivalTime()));
        }
        if (call.getExpectedArrivalTime() != null) {
            mapped.setExpectedArrivalTime(convertDate(call.getExpectedArrivalTime()));
        }
        if (call.getArrivalStatus() != null) {
            mapped.setArrivalStatus(CallStatusEnumeration.valueOf(call.getArrivalStatus().name()));
        }
        if (call.getArrivalBoardingActivity() != null) {
            mapped.setArrivalBoardingActivity(
                    ArrivalBoardingActivityEnumeration.valueOf(call.getArrivalBoardingActivity().name())
            );
        }
        if (call.getArrivalStopAssignments() != null &&
                !call.getArrivalStopAssignments().isEmpty()) {
            mapped.getArrivalStopAssignments().addAll(convertStopAssignments(call.getArrivalStopAssignments()));
        }
        if (call.getArrivalPlatformName() != null) {
            mapped.setArrivalPlatformName(
                    setValue(NaturalLanguageStringStructure.class, call.getArrivalPlatformName())
            );
        }

        if (call.getAimedDepartureTime() != null) {
            mapped.setAimedDepartureTime(convertDate(call.getAimedDepartureTime()));
        }
        if (call.getExpectedDepartureTime() != null) {
            mapped.setExpectedDepartureTime(convertDate(call.getExpectedDepartureTime()));
        }
        if (call.getDepartureStatus() != null) {
            mapped.setDepartureStatus(CallStatusEnumeration.valueOf(call.getDepartureStatus().name()));
        }
        if (call.getDepartureBoardingActivity() != null) {
            mapped.setDepartureBoardingActivity(
                    DepartureBoardingActivityEnumeration.valueOf(call.getDepartureBoardingActivity().name())
            );
        }
        if (call.getDepartureStopAssignments() != null &&
                !call.getDepartureStopAssignments().isEmpty()) {
            mapped.getDepartureStopAssignments().addAll(convertStopAssignments(call.getDepartureStopAssignments()));
        }
        if (call.getDeparturePlatformName() != null) {
            mapped.setDeparturePlatformName(
                    setValue(NaturalLanguageStringStructure.class, call.getDeparturePlatformName())
            );
        }
        if (call.getExpectedDepartureOccupancies() != null && !call.getExpectedDepartureOccupancies().isEmpty()) {
            mapped.getExpectedDepartureOccupancies().addAll(
                    convertOccupancies(call.getExpectedDepartureOccupancies())
            );
        }
        if (call.getExpectedDepartureCapacities() != null && !call.getExpectedDepartureCapacities().isEmpty()) {
            mapped.getExpectedDepartureCapacities().addAll(
                    convertCapacities(call.getExpectedDepartureCapacities())
            );
        }

        return mapped;
    }

    private static List<StopAssignmentStructure> convertStopAssignments(List<StopAssignmentRecord> stopAssignmentRecords) {
        List<StopAssignmentStructure> stopAssignments = new ArrayList<>();
        if (stopAssignmentRecords != null && !stopAssignmentRecords.isEmpty()) {
            for (StopAssignmentRecord assignmentRecord : stopAssignmentRecords) {
                stopAssignments.add(convert(assignmentRecord));
            }
        }

        return stopAssignments;
    }

    private static StopAssignmentStructure convert(StopAssignmentRecord stopAssignment) {
        StopAssignmentStructure stopAssignmentStructure = new StopAssignmentStructure();
        stopAssignmentStructure.setAimedQuayRef(
                setValue(QuayRefStructure.class, stopAssignment.getAimedQuayRef())
        );
        stopAssignmentStructure.setExpectedQuayRef(
                setValue(QuayRefStructure.class, stopAssignment.getExpectedQuayRef())
        );
        return stopAssignmentStructure;
    }
}
