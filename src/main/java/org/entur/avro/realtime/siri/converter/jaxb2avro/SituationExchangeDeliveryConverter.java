package org.entur.avro.realtime.siri.converter.jaxb2avro;

import org.entur.avro.realtime.siri.model.AccessibilityAssessmentRecord;
import org.entur.avro.realtime.siri.model.AccessibilityEnum;
import org.entur.avro.realtime.siri.model.AccessibilityFeatureEnum;
import org.entur.avro.realtime.siri.model.AccessibilityLimitationRecord;
import org.entur.avro.realtime.siri.model.AffectedComponentsRecord;
import org.entur.avro.realtime.siri.model.AffectedLineRecord;
import org.entur.avro.realtime.siri.model.AffectedNetworkRecord;
import org.entur.avro.realtime.siri.model.AffectedOperatorRecord;
import org.entur.avro.realtime.siri.model.AffectedRouteRecord;
import org.entur.avro.realtime.siri.model.AffectedSectionRecord;
import org.entur.avro.realtime.siri.model.AffectedStopPlaceComponentRecord;
import org.entur.avro.realtime.siri.model.AffectedStopPlaceRecord;
import org.entur.avro.realtime.siri.model.AffectedStopPointRecord;
import org.entur.avro.realtime.siri.model.AffectedVehicleJourneyRecord;
import org.entur.avro.realtime.siri.model.AffectsRecord;
import org.entur.avro.realtime.siri.model.IndirectSectionRefRecord;
import org.entur.avro.realtime.siri.model.InfoLinkRecord;
import org.entur.avro.realtime.siri.model.PtSituationElementRecord;
import org.entur.avro.realtime.siri.model.SituationExchangeDeliveryRecord;
import org.entur.avro.realtime.siri.model.SourceRecord;
import org.entur.avro.realtime.siri.model.SourceTypeEnum;
import org.entur.avro.realtime.siri.model.StopPlaceComponentTypeEnum;
import org.entur.avro.realtime.siri.model.StopPointsRecord;
import org.entur.avro.realtime.siri.model.ValidityPeriodRecord;
import uk.org.acbs.siri21.AccessibilityAssessmentStructure;
import uk.org.acbs.siri21.AccessibilityLimitationStructure;
import uk.org.acbs.siri21.AccessibilityStructure;
import uk.org.ifopt.siri21.StopPlaceComponentTypeEnumeration;
import uk.org.siri.siri21.AccessibilityFeatureEnumeration;
import uk.org.siri.siri21.AffectedLineStructure;
import uk.org.siri.siri21.AffectedOperatorStructure;
import uk.org.siri.siri21.AffectedRouteStructure;
import uk.org.siri.siri21.AffectedSectionStructure;
import uk.org.siri.siri21.AffectedStopPlaceComponentStructure;
import uk.org.siri.siri21.AffectedStopPlaceStructure;
import uk.org.siri.siri21.AffectedStopPointStructure;
import uk.org.siri.siri21.AffectedVehicleJourneyStructure;
import uk.org.siri.siri21.AffectsScopeStructure;
import uk.org.siri.siri21.HalfOpenTimestampOutputRangeStructure;
import uk.org.siri.siri21.InfoLinkStructure;
import uk.org.siri.siri21.PtSituationElement;
import uk.org.siri.siri21.SituationExchangeDeliveryStructure;
import uk.org.siri.siri21.SituationSourceStructure;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SituationExchangeDeliveryConverter extends Jaxb2AvroEnumConverter {
    public static List<SituationExchangeDeliveryRecord> convert(List<SituationExchangeDeliveryStructure> deliveries) {
        if (isNullOrEmpty(deliveries)) {
            return Collections.emptyList();
        }

        List<SituationExchangeDeliveryRecord> records = new ArrayList<>();

        for (SituationExchangeDeliveryStructure delivery : deliveries) {
            SituationExchangeDeliveryStructure.Situations situations = delivery.getSituations();
            if (situations != null) {
                records.add(
                    SituationExchangeDeliveryRecord.newBuilder()
                        .setVersion(delivery.getVersion())
                        .setResponseTimestamp(convert(delivery.getResponseTimestamp()))
                        .setSituations(process(situations.getPtSituationElements()))
                        .build()
                );
            }
        }
        return records;
    }

    private static List<PtSituationElementRecord> process(List<PtSituationElement> elements) {
        if (elements == null) {
            return Collections.emptyList();
        }
        List<PtSituationElementRecord> records = new ArrayList<>();
        for (PtSituationElement element : elements) {
            records.add(convert(element));
        }
        return records;
    }

    static PtSituationElementRecord convert(PtSituationElement element) {
        if (element == null) {
            return null;
        }
        return PtSituationElementRecord.newBuilder()
                .setCreationTime(convert(element.getCreationTime()))
                .setParticipantRef(getValue(element.getParticipantRef()))
                .setSituationNumber(getValue(element.getSituationNumber()))
                .setVersion(convert(element.getVersion()))
                .setSource(convert(element.getSource()))
                .setVersionedAtTime(convert(element.getVersionedAtTime()))
                .setProgress(convert(element.getProgress()))
                .setValidityPeriods(convertValidityPeriods(element.getValidityPeriods()))
                .setUndefinedReason(element.getUndefinedReason())
                .setSeverity(convert(element.getSeverity()))
                .setPriority(convert(element.getPriority()))
                .setReportType(convert(element.getReportType()))
                .setKeywords(new ArrayList<>(element.getKeywords()))
                .setPlanned(element.isPlanned())
                .setSummaries(getTranslatedValues(element.getSummaries()))
                .setDescriptions(getTranslatedValues(element.getDescriptions()))
                .setDetails(getTranslatedValues(element.getDetails()))
                .setAdvices(getTranslatedValues(element.getAdvices()))
                .setInfoLinks(convert(element.getInfoLinks()))
                .setAffects(convert(element.getAffects()))
                .build();
    }

    private static AffectsRecord convert(AffectsScopeStructure affects) {
        if (affects == null) {
            return null;
        }
        return AffectsRecord.newBuilder()
                .setNetworks(convert(affects.getNetworks()))
                .setStopPlaces(convert(affects.getStopPlaces()))
                .setStopPoints(convert(affects.getStopPoints()))
                .setVehicleJourneys(convert(affects.getVehicleJourneys()))
                .build();
    }

    private static List<AffectedVehicleJourneyRecord> convert(AffectsScopeStructure.VehicleJourneys vehicleJourneys) {
        if (vehicleJourneys == null) {
            return Collections.emptyList();
        }
        List<AffectedVehicleJourneyRecord> records = new ArrayList<>();
        for (AffectedVehicleJourneyStructure affectedVehicleJourney : vehicleJourneys.getAffectedVehicleJourneies()) {
            records.add(
                    AffectedVehicleJourneyRecord.newBuilder()
                            .setVehicleJourneyRefs(getValues(affectedVehicleJourney.getVehicleJourneyReves()))
                            .setFramedVehicleJourneyRef(convert(affectedVehicleJourney.getFramedVehicleJourneyRef()))
                            .setDatedVehicleJourneyRefs(getValues(affectedVehicleJourney.getDatedVehicleJourneyReves()))
                            .setOperator(convert(affectedVehicleJourney.getOperator()))
                            .setLineRef(getValue(affectedVehicleJourney.getLineRef()))
                            .setRoutes(convertRoutes(affectedVehicleJourney.getRoutes()))
                            .setOriginAimedDepartureTime(convert(affectedVehicleJourney.getOriginAimedDepartureTime()))
                            .build()
            );
        }
        return records;
    }

    private static List<AffectedRouteRecord> convertRoutes(List<AffectedRouteStructure> routes) {
        List<AffectedRouteRecord> records = new ArrayList<>();
        for (AffectedRouteStructure route : routes) {
            records.add(convert(route));
        }
        return records;
    }

    private static List<AffectedStopPointRecord> convert(AffectsScopeStructure.StopPoints stopPoints) {
        if (stopPoints == null) {
            return Collections.emptyList();
        }
        List<AffectedStopPointRecord> records = new ArrayList<>();
        for (AffectedStopPointStructure affectedStopPoint : stopPoints.getAffectedStopPoints()) {
            records.add(
                    AffectedStopPointRecord.newBuilder()
                            .setStopPointRef(getValue(affectedStopPoint.getStopPointRef()))
                            .setStopPointNames(convertNames(affectedStopPoint.getStopPointNames()))
                            .setStopConditions(convertStopConditions(affectedStopPoint.getStopConditions()))
                            .build()
            );
        }
        return records;
    }

    private static List<AffectedStopPlaceRecord> convert(AffectsScopeStructure.StopPlaces stopPlaces) {
        if (stopPlaces == null) {
            return Collections.emptyList();
        }
        List<AffectedStopPlaceRecord> records = new ArrayList<>();
        for (AffectedStopPlaceStructure affectedStopPlace : stopPlaces.getAffectedStopPlaces()) {
            records.add(
                    AffectedStopPlaceRecord.newBuilder()
                            .setAccessibilityAssessment(convert(affectedStopPlace.getAccessibilityAssessment()))
                            .setStopPlaceRef(getValue(affectedStopPlace.getStopPlaceRef()))
                            .setPlaceNames(getTranslatedValues(affectedStopPlace.getPlaceNames()))
                            .setAffectedComponent(convert(affectedStopPlace.getAffectedComponents()))
                            .build()
            );
        }

        return records;
    }

    private static AffectedComponentsRecord convert(AffectedStopPlaceStructure.AffectedComponents affectedComponents) {
        if (affectedComponents == null) {
            return null;
        }
        return AffectedComponentsRecord.newBuilder()
                .setComponents(convertComponents(affectedComponents.getAffectedComponents()))
                .build()
        ;
    }

    private static List<AffectedStopPlaceComponentRecord> convertComponents(List<AffectedStopPlaceComponentStructure> components) {
        if (components == null) {
            return Collections.emptyList();
        }
        List<AffectedStopPlaceComponentRecord> records = new ArrayList<>();
        for (AffectedStopPlaceComponentStructure component : components) {
            records.add(convert(component));
        }
        return records;
    }

    private static AffectedStopPlaceComponentRecord convert(AffectedStopPlaceComponentStructure component) {
        return AffectedStopPlaceComponentRecord.newBuilder()
                .setComponentRef(getValue(component.getComponentRef()))
                .setAccessFeatureType(convert(component.getAccessFeatureType()))
                .setComponentType(convert(component.getComponentType()))
                .build();
    }

    private static StopPlaceComponentTypeEnum convert(StopPlaceComponentTypeEnumeration componentType) {
        if (componentType == null) {
            return null;
        }
        return StopPlaceComponentTypeEnum.valueOf(componentType.name());
    }

    private static AccessibilityFeatureEnum convert(AccessibilityFeatureEnumeration accessFeatureType) {
        if (accessFeatureType == null) {
            return null;
        }
        return AccessibilityFeatureEnum.valueOf(accessFeatureType.name());
    }

    private static AccessibilityAssessmentRecord convert(AccessibilityAssessmentStructure accessibilityAssessment) {
        if (accessibilityAssessment == null) {
            return null;
        }
        return AccessibilityAssessmentRecord.newBuilder()
                .setMobilityImpairedAccess(accessibilityAssessment.isMobilityImpairedAccess())
                .setLimitations(convert(accessibilityAssessment.getLimitations()))
                .build();
    }

    private static List<AccessibilityLimitationRecord> convert(AccessibilityAssessmentStructure.Limitations limitations) {
        if (limitations == null) {
            return Collections.emptyList();
        }
        List<AccessibilityLimitationRecord> records = new ArrayList<>();
        for (AccessibilityLimitationStructure accessibilityLimitation : limitations.getAccessibilityLimitations()) {
            records.add(
                    AccessibilityLimitationRecord.newBuilder()
                        .setEscalatorFreeAccess(convert(accessibilityLimitation.getEscalatorFreeAccess()))
                        .setStepFreeAccess(convert(accessibilityLimitation.getStepFreeAccess()))
                        .setEscalatorFreeAccess(convert(accessibilityLimitation.getEscalatorFreeAccess()))
                        .setLiftFreeAccess(convert(accessibilityLimitation.getLiftFreeAccess()))
                        .build()
            );
        }

        return records;
    }

    private static AccessibilityEnum convert(AccessibilityStructure accessibilityStructure) {
        if (accessibilityStructure == null) {
            return null;
        }
        return AccessibilityEnum.valueOf(accessibilityStructure.getValue().name());
    }

    private static List<AffectedNetworkRecord> convert(AffectsScopeStructure.Networks networks) {
        if (networks == null) {
            return Collections.emptyList();
        }
        List<AffectedNetworkRecord> records = new ArrayList<>();
        for (AffectsScopeStructure.Networks.AffectedNetwork affectedNetwork : networks.getAffectedNetworks()) {
            records.add(convert(affectedNetwork));
        }
        return records;
    }

    private static AffectedNetworkRecord convert(AffectsScopeStructure.Networks.AffectedNetwork affectedNetwork) {
        if (affectedNetwork == null) {
            return null;
        }
        return AffectedNetworkRecord.newBuilder()
                .setAffectedOperators(convertOperators(affectedNetwork.getAffectedOperators()))
                .setNetworkRef(getValue(affectedNetwork.getNetworkRef()))
                .setVehicleMode(convert(affectedNetwork.getVehicleMode()))
                .setAirSubmode(convert(affectedNetwork.getAirSubmode()))
                .setBusSubmode(convert(affectedNetwork.getBusSubmode()))
                .setCoachSubmode(convert(affectedNetwork.getCoachSubmode()))
                .setMetroSubmode(convert(affectedNetwork.getMetroSubmode()))
                .setRailSubmode(convert(affectedNetwork.getRailSubmode()))
                .setTramSubmode(convert(affectedNetwork.getTramSubmode()))
                .setWaterSubmode(convert(affectedNetwork.getWaterSubmode()))
                .setAffectedLines(convertLines(affectedNetwork.getAffectedLines()))
                .setAllLines(affectedNetwork.getAllLines())
                .build();
    }

    private static List<AffectedLineRecord> convertLines(List<AffectedLineStructure> affectedLines) {
        List<AffectedLineRecord> records = new ArrayList<>();
        for (AffectedLineStructure affectedLine : affectedLines) {
            records.add(convert(affectedLine));
        }
        return records;
    }

    private static AffectedLineRecord convert(AffectedLineStructure affectedLine) {
        if (affectedLine == null) {
            return null;
        }
        return AffectedLineRecord.newBuilder()
                .setLineRef(getValue(affectedLine.getLineRef()))
                .setRoutes(convert(affectedLine.getRoutes()))
                .setSections(convert(affectedLine.getSections()))
                .build();
    }

    private static List<AffectedRouteRecord> convert(AffectedLineStructure.Routes routes) {
        if (routes == null) {
            return Collections.emptyList();
        }
        List<AffectedRouteRecord> records = new ArrayList<>();
        for (AffectedRouteStructure affectedRoute : routes.getAffectedRoutes()) {
            records.add(convert(affectedRoute));
        }

        return records;
    }

    private static AffectedRouteRecord convert(AffectedRouteStructure affectedRoute) {
        if (affectedRoute == null) {
            return null;
        }
        return AffectedRouteRecord.newBuilder()
                .setRouteRef(getValue(affectedRoute.getRouteRef()))
                .setStopPoints(convert(affectedRoute.getStopPoints()))
                .setSections(convert(affectedRoute.getSections()))
                .build();
    }

    private static List<AffectedSectionRecord> convert(AffectedRouteStructure.Sections sections) {
        if (sections == null) {
            return Collections.emptyList();
        }
        List<AffectedSectionRecord> records = new ArrayList<>();
        for (AffectedSectionStructure affectedSection : sections.getAffectedSections()) {
            records.add(convert(affectedSection));
        }

        return records;
    }


    private static List<AffectedSectionRecord> convert(AffectedLineStructure.Sections sections) {
        if (sections == null) {
            return Collections.emptyList();
        }
        List<AffectedSectionRecord> records = new ArrayList<>();
        for (AffectedSectionStructure affectedSection : sections.getAffectedSections()) {
            records.add(convert(affectedSection));
        }

        return records;
    }

    private static AffectedSectionRecord convert(AffectedSectionStructure affectedSection) {
        if (affectedSection == null) {
            return null;
        }
        return AffectedSectionRecord.newBuilder()
                .setIndirectSectionRef(convert(affectedSection.getIndirectSectionRef()))
                .build();
    }

    private static IndirectSectionRefRecord convert(AffectedSectionStructure.IndirectSectionRef indirectSectionRef) {
        if (indirectSectionRef == null) {
            return null;
        }

        return IndirectSectionRefRecord.newBuilder()
                    .setFirstQuayRef(getValue(indirectSectionRef.getFirstQuayRef()))
                    .setLastQuayRef(getValue(indirectSectionRef.getLastQuayRef()))
                    .build();
    }

    private static StopPointsRecord convert(AffectedRouteStructure.StopPoints stopPoints) {
        if (stopPoints == null) {
            return null;
        }

        return StopPointsRecord.newBuilder()
                .setAffectedOnly(stopPoints.isAffectedOnly())
                .setStopPoints(convertRouteStopPoints(stopPoints.getAffectedStopPointsAndLinkProjectionToNextStopPoints()))
                .build();
    }

    private static List<AffectedStopPointRecord> convertRouteStopPoints(List<Serializable> stopPoints) {
        List<AffectedStopPointRecord> records = new ArrayList<>();
        for (Serializable affectedStopPoint : stopPoints) {
            if (affectedStopPoint instanceof AffectedStopPointStructure) {
                records.add(convert((AffectedStopPointStructure)affectedStopPoint));
            }
        }
        return records;
    }

    private static AffectedStopPointRecord convert(AffectedStopPointStructure affectedStopPoint) {
        if (affectedStopPoint == null) {
            return null;
        }
        return AffectedStopPointRecord.newBuilder()
                .setStopPointRef(getValue(affectedStopPoint.getStopPointRef()))
                .setStopPointNames(getTranslatedValues(affectedStopPoint.getStopPointNames()))
                .setStopConditions(convertStopConditions(affectedStopPoint.getStopConditions()))
                .build();
    }


    private static List<AffectedOperatorRecord> convertOperators(List<AffectedOperatorStructure> affectedOperators) {
        if (affectedOperators == null) {
            return Collections.emptyList();
        }
        List<AffectedOperatorRecord> records = new ArrayList<>();
        for (AffectedOperatorStructure affectedOperator : affectedOperators) {
            records.add(convert(affectedOperator));
        }
        return records;
    }

    private static AffectedOperatorRecord convert(AffectedOperatorStructure affectedOperator) {
        if (affectedOperator == null) {
            return null;
        }
        return AffectedOperatorRecord.newBuilder()
                .setOperatorRef(getValue(affectedOperator.getOperatorRef()))
                .setOperatorNames(getTranslatedValues(affectedOperator.getOperatorNames()))
                .build();
    }

    private static List<InfoLinkRecord> convert(PtSituationElement.InfoLinks infoLinks) {
        if (infoLinks == null) {
            return Collections.emptyList();
        }
        List<InfoLinkRecord> records = new ArrayList<>();
        for (InfoLinkStructure infoLink : infoLinks.getInfoLinks()) {
            records.add(convert(infoLink));
        }
        return records;
    }

    private static InfoLinkRecord convert(InfoLinkStructure infoLink) {
        if (infoLink == null) {
            return null;
        }
        return InfoLinkRecord.newBuilder()
                .setLabels(getTranslatedValues(infoLink.getLabels()))
                .setUri(infoLink.getUri())
                .build();
    }

    private static List<ValidityPeriodRecord> convertValidityPeriods(List<HalfOpenTimestampOutputRangeStructure> periods) {
        if (periods == null) {
            return Collections.emptyList();
        }
        List<ValidityPeriodRecord> records = new ArrayList<>();
        for (HalfOpenTimestampOutputRangeStructure period : periods) {
            records.add(convert(period));
        }
        return records;
    }

    private static ValidityPeriodRecord convert(HalfOpenTimestampOutputRangeStructure period) {
        if (period == null) {
            return null;
        }
        return ValidityPeriodRecord.newBuilder()
                .setStartTime(convert(period.getStartTime()))
                .setEndTime(convert(period.getEndTime()))
                .build();
    }

    private static SourceRecord convert(SituationSourceStructure source) {
        if (source == null) {
            return null;
        }
        return SourceRecord.newBuilder()
                .setSourceType(SourceTypeEnum.valueOf(source.getSourceType().name()))
                .build();
    }

}
