package org.entur.avro.realtime.siri.converter.avro2jaxb;

import org.entur.avro.realtime.siri.model.AccessibilityAssessmentRecord;
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
import org.entur.avro.realtime.siri.model.ReportTypeEnum;
import org.entur.avro.realtime.siri.model.SituationExchangeDeliveryRecord;
import org.entur.avro.realtime.siri.model.SourceRecord;
import org.entur.avro.realtime.siri.model.StopPlaceComponentTypeEnum;
import org.entur.avro.realtime.siri.model.StopPointsRecord;
import org.entur.avro.realtime.siri.model.ValidityPeriodRecord;
import uk.org.acbs.siri21.AccessibilityAssessmentStructure;
import uk.org.acbs.siri21.AccessibilityLimitationStructure;
import uk.org.acbs.siri21.AccessibilityStructure;
import uk.org.ifopt.siri21.StopPlaceComponentRefStructure;
import uk.org.ifopt.siri21.StopPlaceComponentTypeEnumeration;
import uk.org.ifopt.siri21.StopPlaceRef;
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
import uk.org.siri.siri21.DatedVehicleJourneyRef;
import uk.org.siri.siri21.DefaultedTextStructure;
import uk.org.siri.siri21.HalfOpenTimestampOutputRangeStructure;
import uk.org.siri.siri21.InfoLinkStructure;
import uk.org.siri.siri21.LineRef;
import uk.org.siri.siri21.NaturalLanguageStringStructure;
import uk.org.siri.siri21.NetworkRefStructure;
import uk.org.siri.siri21.OperatorRefStructure;
import uk.org.siri.siri21.PtSituationElement;
import uk.org.siri.siri21.QuayRefStructure;
import uk.org.siri.siri21.ReportTypeEnumeration;
import uk.org.siri.siri21.RequestorRef;
import uk.org.siri.siri21.RouteRefStructure;
import uk.org.siri.siri21.SituationExchangeDeliveryStructure;
import uk.org.siri.siri21.SituationNumber;
import uk.org.siri.siri21.SituationSourceStructure;
import uk.org.siri.siri21.StopPointRefStructure;
import uk.org.siri.siri21.VehicleJourneyRef;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SituationExchangeDeliveryConverter extends Avro2JaxbEnumConverter {

    public static Collection<SituationExchangeDeliveryStructure> convert(List<SituationExchangeDeliveryRecord> deliveries) {
        List<SituationExchangeDeliveryStructure> results = new ArrayList<>();


        for (SituationExchangeDeliveryRecord delivery : deliveries) {

            SituationExchangeDeliveryStructure sxDelivery = convert(delivery);
            if (delivery.getResponseTimestamp() != null) {
                sxDelivery.setResponseTimestamp(convertDate(delivery.getResponseTimestamp()));
            }
            if (delivery.getSituations() != null) {
                SituationExchangeDeliveryStructure.Situations situations = new SituationExchangeDeliveryStructure.Situations();
                for (PtSituationElementRecord situation : delivery.getSituations()) {
                    situations.getPtSituationElements().add(convert(situation));
                }
                sxDelivery.setSituations(situations);
            }
            results.add(sxDelivery);
        }

        return results;
    }

    private static SituationExchangeDeliveryStructure convert(SituationExchangeDeliveryRecord delivery) {
        if (delivery == null) {
            return null;
        }
        SituationExchangeDeliveryStructure sxDelivery = new SituationExchangeDeliveryStructure();
        if (delivery.getVersion() != null) {
            sxDelivery.setVersion((String)delivery.getVersion());
        }
        if (delivery.getResponseTimestamp() != null) {
            sxDelivery.setResponseTimestamp(convertDate(delivery.getResponseTimestamp()));
        }
        if (delivery.getSituations() != null) {
            sxDelivery.setSituations(process(delivery.getSituations()));
        }
        return sxDelivery;
    }

    private static SituationExchangeDeliveryStructure.Situations process(List<PtSituationElementRecord> situations) {
        if (situations == null) {
            return null;
        }
        SituationExchangeDeliveryStructure.Situations result = new SituationExchangeDeliveryStructure.Situations();
        for (PtSituationElementRecord situation : situations) {
            result.getPtSituationElements().add(convert(situation));
        }
        return result;
    }

    private static PtSituationElement convert(PtSituationElementRecord situation) {
        if (situation == null) {
            return null;
        }
        PtSituationElement element = new PtSituationElement();
        if (situation.getCreationTime() != null) {
            element.setCreationTime(convertDate(situation.getCreationTime()));
        }
        if (situation.getParticipantRef() != null) {
            element.setParticipantRef(setValue(RequestorRef.class, situation.getParticipantRef()));
        }
        if (situation.getSituationNumber() != null) {
            element.setSituationNumber(setValue(SituationNumber.class, situation.getSituationNumber()));
        }
        if (situation.getVersion() != null) {
            element.setVersion(convertVersion(situation.getVersion()));
        }
        if (situation.getSource() != null) {
            element.setSource(convert(situation.getSource()));
        }
        if (situation.getProgress() != null) {
            element.setProgress(convert(situation.getProgress()));
        }
        if (situation.getVersionedAtTime() != null) {
            element.setVersionedAtTime(convertDate(situation.getVersionedAtTime()));
        }
        if (situation.getValidityPeriods() != null && !situation.getValidityPeriods().isEmpty()) {
            element.getValidityPeriods().addAll(
                    convertValidityPeriods(situation.getValidityPeriods())
            );
        }
        if (situation.getUndefinedReason() != null) {
            // (TODO: Hard-coded as defined in Nordic SIRI profile - should probably be more generic...
            element.setUndefinedReason((String) situation.getUndefinedReason());
        }
        if (situation.getSeverity() != null) {
            element.setSeverity(convert(situation.getSeverity()));
        }
        if (situation.getPriority() != null) {
            element.setPriority(BigInteger.valueOf(situation.getPriority()));
        }
        if (situation.getReportType() != null) {
            element.setReportType(convert(situation.getReportType()));
        }
        if (situation.getKeywords() != null) {
            element.getKeywords().addAll(convertStringList(situation.getKeywords()));
        }
        if (situation.getPlanned() != null) {
            element.setPlanned(situation.getPlanned());
        }
        if (situation.getSummaries() != null && !situation.getSummaries().isEmpty()) {
            element.getSummaries().addAll(setTranslatedValues(DefaultedTextStructure.class, situation.getSummaries()));
        }
        if (situation.getDescriptions() != null && !situation.getDescriptions().isEmpty()) {
            element.getDescriptions().addAll(setTranslatedValues(DefaultedTextStructure.class, situation.getDescriptions()));
        }
        if (situation.getDetails() != null && !situation.getDetails().isEmpty()) {
            element.getDetails().addAll(setTranslatedValues(DefaultedTextStructure.class, situation.getDetails()));
        }
        if (situation.getAdvices() != null && !situation.getAdvices().isEmpty()) {
            element.getAdvices().addAll(setTranslatedValues(DefaultedTextStructure.class, situation.getAdvices()));
        }
        if (situation.getInfoLinks() != null && !situation.getInfoLinks().isEmpty()) {
            element.setInfoLinks(convertInfoLinks(situation.getInfoLinks()));
        }
        if (situation.getAffects() != null) {
            element.setAffects(convert(situation.getAffects()));
        }

        return element;
    }

    private static AffectsScopeStructure convert(AffectsRecord affects) {
        if (affects == null) {
            return null;
        }
        AffectsScopeStructure affectsScopeStructure = new AffectsScopeStructure();
        if (affects.getNetworks() != null && !affects.getNetworks().isEmpty()) {
            affectsScopeStructure.setNetworks(convertNetworks(affects.getNetworks()));
        }
        if (affects.getStopPlaces() != null && !affects.getStopPlaces().isEmpty()) {
            affectsScopeStructure.setStopPlaces(convertStopPlaces(affects.getStopPlaces()));
        }
        if (affects.getStopPoints() != null && !affects.getStopPoints().isEmpty()) {
            affectsScopeStructure.setStopPoints(convertStopPoints(affects.getStopPoints()));
        }
        if (affects.getVehicleJourneys() != null && !affects.getVehicleJourneys().isEmpty()) {
            affectsScopeStructure.setVehicleJourneys(convertVehicleJourneys(affects.getVehicleJourneys()));
        }
        return affectsScopeStructure;
    }

    private static AffectsScopeStructure.Networks convertNetworks(List<AffectedNetworkRecord> networks) {
        AffectsScopeStructure.Networks result = new AffectsScopeStructure.Networks();
        for (AffectedNetworkRecord network : networks) {
            result.getAffectedNetworks().add(
                    convert(network)
            );
        }

        return result;
    }

    private static AffectsScopeStructure.Networks.AffectedNetwork convert(AffectedNetworkRecord network) {
        AffectsScopeStructure.Networks.AffectedNetwork result = new AffectsScopeStructure.Networks.AffectedNetwork();
        if (network.getAffectedOperators() != null && !network.getAffectedOperators().isEmpty()) {
            result.getAffectedOperators().addAll(
                    setValues(AffectedOperatorStructure.class, network.getAffectedOperators())
            );
        }
        if (network.getNetworkRef() != null) {
            result.setNetworkRef(setValue(NetworkRefStructure.class, network.getNetworkRef()));
        }
        if (network.getVehicleMode() != null) {
            result.setVehicleMode(convert(network.getVehicleMode()));
        }
        if (network.getAirSubmode() != null) {
            result.setAirSubmode(convert(network.getAirSubmode()));
        }
        if (network.getBusSubmode() != null) {
            result.setBusSubmode(convert(network.getBusSubmode()));
        }
        if (network.getCoachSubmode() != null) {
            result.setCoachSubmode(convert(network.getCoachSubmode()));
        }
        if (network.getMetroSubmode() != null) {
            result.setMetroSubmode(convert(network.getMetroSubmode()));
        }
        if (network.getRailSubmode() != null) {
            result.setRailSubmode(convert(network.getRailSubmode()));
        }
        if (network.getTramSubmode() != null) {
            result.setTramSubmode(convert(network.getTramSubmode()));
        }
        if (network.getWaterSubmode() != null) {
            result.setWaterSubmode(convert(network.getWaterSubmode()));
        }
        if (network.getAffectedLines() != null && !network.getAffectedLines().isEmpty()) {
            result.getAffectedLines()
                    .addAll(
                            convertLines(network.getAffectedLines())
                    );
        }
        return result;
    }

    private static List<AffectedLineStructure> convertLines(List<AffectedLineRecord> affectedLines) {
        List<AffectedLineStructure> result = new ArrayList<>();
        for (AffectedLineRecord affectedLine : affectedLines) {
            result.add(convert(affectedLine));
        }
        return result;
    }

    private static AffectedLineStructure convert(AffectedLineRecord affectedLine) {
        AffectedLineStructure result = new AffectedLineStructure();
        if (affectedLine.getLineRef() != null) {
            result.setLineRef(setValue(LineRef.class, affectedLine.getLineRef()));
        }
        if (affectedLine.getRoutes() != null && !affectedLine.getRoutes().isEmpty()) {
            result.setRoutes(new AffectedLineStructure.Routes());
            result.getRoutes().getAffectedRoutes()
                    .addAll(convertRoutes(affectedLine.getRoutes()));
        }
        if (affectedLine.getSections() != null && !affectedLine.getSections().isEmpty()) {
            result.setSections(convertLineSections(affectedLine.getSections()));
        }
        return result;
    }

    private static AffectsScopeStructure.StopPlaces convertStopPlaces(List<AffectedStopPlaceRecord> stopPlaces) {
        AffectsScopeStructure.StopPlaces result = new AffectsScopeStructure.StopPlaces();
        for (AffectedStopPlaceRecord stopPlace : stopPlaces) {
            result.getAffectedStopPlaces().add(
                    convert(stopPlace)
            );
        }
        return result;
    }

    private static AffectedStopPlaceStructure convert(AffectedStopPlaceRecord stopPlace) {
        AffectedStopPlaceStructure result = new AffectedStopPlaceStructure();
        if (stopPlace.getStopPlaceRef() != null) {
            result.setStopPlaceRef(setValue(StopPlaceRef.class, stopPlace.getStopPlaceRef()));
        }
        if (stopPlace.getAccessibilityAssessment() != null) {
            result.setAccessibilityAssessment(convert(stopPlace.getAccessibilityAssessment()));
        }
        if (stopPlace.getAffectedComponent() != null) {
            result.setAffectedComponents(convert(stopPlace.getAffectedComponent()));
        }
        return result;
    }

    private static AffectedStopPlaceStructure.AffectedComponents convert(AffectedComponentsRecord affectedComponent) {
        AffectedStopPlaceStructure.AffectedComponents result = new AffectedStopPlaceStructure.AffectedComponents();
        for (AffectedStopPlaceComponentRecord component : affectedComponent.getComponents()) {
            result.getAffectedComponents().add(convert(component));
        }
        return result;
    }

    private static AffectedStopPlaceComponentStructure convert(AffectedStopPlaceComponentRecord component) {
        AffectedStopPlaceComponentStructure componentStructure = new AffectedStopPlaceComponentStructure();
        if (component.getComponentRef() != null) {
            componentStructure.setComponentRef(setValue(StopPlaceComponentRefStructure.class, component.getComponentRef()));
        }
        if (component.getComponentType() != null) {
            componentStructure.setComponentType(convert(component.getComponentType()));
        }
        if (component.getAccessFeatureType() != null) {
            componentStructure.setAccessFeatureType(convert(component.getAccessFeatureType()));
        }
        return componentStructure;
    }

    private static AccessibilityFeatureEnumeration convert(AccessibilityFeatureEnum accessFeatureType) {
        return AccessibilityFeatureEnumeration.valueOf(accessFeatureType.name());
    }

    private static StopPlaceComponentTypeEnumeration convert(StopPlaceComponentTypeEnum componentType) {
        return StopPlaceComponentTypeEnumeration.valueOf(componentType.name());
    }

    private static AccessibilityAssessmentStructure convert(AccessibilityAssessmentRecord record) {
        AccessibilityAssessmentStructure result = new AccessibilityAssessmentStructure();
        if (record.getMobilityImpairedAccess() != null) {
            result.setMobilityImpairedAccess(record.getMobilityImpairedAccess());
        }
        if (record.getLimitations() != null) {
            result.setLimitations(convertLimitations(record.getLimitations()));
        }
        return result;
    }

    private static AccessibilityAssessmentStructure.Limitations convertLimitations(List<AccessibilityLimitationRecord> limitations) {
        AccessibilityAssessmentStructure.Limitations result = new AccessibilityAssessmentStructure.Limitations();
        for (AccessibilityLimitationRecord limitation : limitations) {
            result.getAccessibilityLimitations().add(convert(limitation));
        }
        return result;
    }

    private static AccessibilityLimitationStructure convert(AccessibilityLimitationRecord limitation) {
        AccessibilityLimitationStructure result = new AccessibilityLimitationStructure();
        if (limitation.getWheelchairAccess() != null) {
            result.setWheelchairAccess(setValue(AccessibilityStructure.class, limitation.getWheelchairAccess().name()));
        }
        if (limitation.getStepFreeAccess() != null) {
            result.setStepFreeAccess(setValue(AccessibilityStructure.class, limitation.getStepFreeAccess().name()));
        }
        if (limitation.getEscalatorFreeAccess() != null) {
            result.setEscalatorFreeAccess(setValue(AccessibilityStructure.class, limitation.getEscalatorFreeAccess().name()));
        }
        if (limitation.getLiftFreeAccess() != null) {
            result.setLiftFreeAccess(setValue(AccessibilityStructure.class, limitation.getLiftFreeAccess().name()));
        }
        return result;
    }

    private static AffectsScopeStructure.StopPoints convertStopPoints(List<AffectedStopPointRecord> stopPoints) {
        AffectsScopeStructure.StopPoints result = new AffectsScopeStructure.StopPoints();
        for (AffectedStopPointRecord stopPoint : stopPoints) {
            result.getAffectedStopPoints().add(
                    convert(stopPoint)
            );
        }
        return result;
    }

    private static AffectsScopeStructure.VehicleJourneys convertVehicleJourneys(List<AffectedVehicleJourneyRecord> vehicleJourneys) {
        if (vehicleJourneys == null) {
            return null;
        }
        AffectsScopeStructure.VehicleJourneys result = new AffectsScopeStructure.VehicleJourneys();
        for (AffectedVehicleJourneyRecord vehicleJourney : vehicleJourneys) {
            result.getAffectedVehicleJourneies()
                    .add(convert(vehicleJourney));
        }
        return result;
    }

    private static AffectedVehicleJourneyStructure convert(AffectedVehicleJourneyRecord vehicleJourney) {
        if (vehicleJourney == null) {
            return null;
        }
        AffectedVehicleJourneyStructure result = new AffectedVehicleJourneyStructure();
        if (vehicleJourney.getVehicleJourneyRefs() != null && !vehicleJourney.getVehicleJourneyRefs().isEmpty()) {
            result.getVehicleJourneyReves()
                    .addAll(
                            setValues(
                                    VehicleJourneyRef.class,
                                    vehicleJourney.getVehicleJourneyRefs()
                            )
                    );
        }
        if (vehicleJourney.getDatedVehicleJourneyRefs() != null && !vehicleJourney.getDatedVehicleJourneyRefs().isEmpty()) {
            result.getDatedVehicleJourneyReves()
                    .addAll(
                            setValues(
                                    DatedVehicleJourneyRef.class,
                                    vehicleJourney.getDatedVehicleJourneyRefs()
                            )
                    );
        }
        if (vehicleJourney.getFramedVehicleJourneyRef() != null) {
            result.setFramedVehicleJourneyRef(convert(vehicleJourney.getFramedVehicleJourneyRef()));
        }

        if (vehicleJourney.getOperator() != null) {
            result.setOperator(convert(vehicleJourney.getOperator()));
        }

        if (vehicleJourney.getLineRef() != null) {
            result.setLineRef(setValue(LineRef.class, vehicleJourney.getLineRef()));
        }

        if (vehicleJourney.getRoutes() != null && !vehicleJourney.getRoutes().isEmpty()) {
            result.getRoutes().addAll(
                    convertRoutes(vehicleJourney.getRoutes())
            );
        }

        if (vehicleJourney.getOriginAimedDepartureTime() != null) {
            result.setOriginAimedDepartureTime(convertDate(vehicleJourney.getOriginAimedDepartureTime()));
        }
        return result;
    }

    private static List<AffectedRouteStructure> convertRoutes(List<AffectedRouteRecord> routes) {
        List<AffectedRouteStructure> result = new ArrayList<>();
        for (AffectedRouteRecord route : routes) {
            result.add(convert(route));
        }
        return result;
    }

    private static AffectedRouteStructure convert(AffectedRouteRecord route) {
        AffectedRouteStructure result = new AffectedRouteStructure();
        if (route.getRouteRef() != null) {
            result.setRouteRef(setValue(RouteRefStructure.class, route.getRouteRef()));
        }
        if (route.getStopPoints() != null && !route.getStopPoints().getStopPoints().isEmpty()) {
            result.setStopPoints(convertRouteStopPoints(route.getStopPoints()));
        }
        if (route.getSections() != null && !route.getSections().isEmpty()) {
            result.setSections(convertRouteSections(route.getSections()));
        }
        return result;
    }

    private static AffectedRouteStructure.Sections convertRouteSections(List<AffectedSectionRecord> sections) {
        AffectedRouteStructure.Sections result = new AffectedRouteStructure.Sections();
        for (AffectedSectionRecord section : sections) {
            result.getAffectedSections()
                    .add(convert(section));
        }
        return result;
    }

    private static AffectedLineStructure.Sections convertLineSections(List<AffectedSectionRecord> sections) {
        AffectedLineStructure.Sections result = new AffectedLineStructure.Sections();
        for (AffectedSectionRecord section : sections) {
            result.getAffectedSections().add(convert(section));
        }
        return result;
    }

    private static AffectedSectionStructure convert(AffectedSectionRecord section) {
        AffectedSectionStructure result = new AffectedSectionStructure();
        if (section.getIndirectSectionRef() != null) {
            result.setIndirectSectionRef(convert(section.getIndirectSectionRef()));
        }
        return result;
    }

    private static AffectedSectionStructure.IndirectSectionRef convert(IndirectSectionRefRecord indirectSectionRef) {
        AffectedSectionStructure.IndirectSectionRef result = new AffectedSectionStructure.IndirectSectionRef();
        if (indirectSectionRef.getFirstQuayRef() != null) {
            result.setFirstQuayRef(setValue(QuayRefStructure.class, indirectSectionRef.getFirstQuayRef()));
        }
        if (indirectSectionRef.getLastQuayRef() != null) {
            result.setLastQuayRef(setValue(QuayRefStructure.class, indirectSectionRef.getLastQuayRef()));
        }
        return result;
    }

    private static AffectedRouteStructure.StopPoints convertRouteStopPoints(StopPointsRecord stopPoints) {
        AffectedRouteStructure.StopPoints result = new AffectedRouteStructure.StopPoints();
        for (AffectedStopPointRecord stopPoint : stopPoints.getStopPoints()) {
            result.getAffectedStopPointsAndLinkProjectionToNextStopPoints()
                    .add(convert(stopPoint));
        }
        if (stopPoints.getAffectedOnly() != null) {
            result.setAffectedOnly(stopPoints.getAffectedOnly());
        }
        return result;
    }

    private static AffectedStopPointStructure convert(AffectedStopPointRecord stopPoint) {
        AffectedStopPointStructure result = new AffectedStopPointStructure();
        if (stopPoint.getStopPointRef() != null) {
            result.setStopPointRef(setValue(StopPointRefStructure.class, stopPoint.getStopPointRef()));
        }
        if (stopPoint.getStopPointNames() != null && !stopPoint.getStopPointNames().isEmpty()) {
            result.getStopPointNames().addAll(
                    setTranslatedValues(NaturalLanguageStringStructure.class, stopPoint.getStopPointNames())
            );
        }
        if (stopPoint.getStopConditions() != null && !stopPoint.getStopConditions().isEmpty()) {
            result.getStopConditions()
                    .addAll(convertStopConditions(stopPoint.getStopConditions()));
        }
        return result;
    }

    private static AffectedOperatorStructure convert(AffectedOperatorRecord operator) {
        if (operator == null) {
            return null;
        }
        AffectedOperatorStructure result = new AffectedOperatorStructure();
        if (operator.getOperatorRef() != null) {
            result.setOperatorRef(
                    setValue(
                            OperatorRefStructure.class,
                            operator.getOperatorRef()
                    )
            );
        }
        if (operator.getOperatorNames() != null && !operator.getOperatorNames().isEmpty()) {
            result.getOperatorNames()
                    .addAll(
                            setTranslatedValues(
                                    NaturalLanguageStringStructure.class,
                                    operator.getOperatorNames()
                            )
                    );
        }
        return result;
    }

    private static PtSituationElement.InfoLinks convertInfoLinks(List<InfoLinkRecord> infoLinks) {
        if (infoLinks == null) {
            return null;
        }
        PtSituationElement.InfoLinks result = new PtSituationElement.InfoLinks();
        for (InfoLinkRecord infoLink : infoLinks) {
            result.getInfoLinks().add(convert(infoLink));
        }
        return result;
    }

    private static InfoLinkStructure convert(InfoLinkRecord infoLink) {
        if (infoLink == null) {
            return null;
        }
        InfoLinkStructure result = new InfoLinkStructure();
        if (infoLink.getUri() != null) {
            result.setUri((String) infoLink.getUri());
        }
        if (infoLink.getLabels() != null && !infoLink.getLabels().isEmpty()) {
            result.getLabels().addAll(setTranslatedValues(NaturalLanguageStringStructure.class, infoLink.getLabels()));
        }
        return result;
    }

    private static ReportTypeEnumeration convert(ReportTypeEnum reportType) {
        if (reportType == null) {
            return null;
        }
        return ReportTypeEnumeration.valueOf(reportType.name());
    }

    private static List<HalfOpenTimestampOutputRangeStructure> convertValidityPeriods(List<ValidityPeriodRecord> validityPeriods) {
        if (validityPeriods == null) {
            return null;
        }
        List<HalfOpenTimestampOutputRangeStructure> result = new ArrayList<>();
        for (ValidityPeriodRecord validityPeriod : validityPeriods) {
            HalfOpenTimestampOutputRangeStructure period = new HalfOpenTimestampOutputRangeStructure();
            if (validityPeriod.getStartTime() != null) {
                period.setStartTime(convertDate(validityPeriod.getStartTime()));
            }
            if (validityPeriod.getEndTime() != null) {
                period.setEndTime(convertDate(validityPeriod.getEndTime()));
            }
            result.add(period);
        }

        return result;
    }

    private static SituationSourceStructure convert(SourceRecord source) {
        if (source == null) {
            return null;
        }
        SituationSourceStructure result = new SituationSourceStructure();
        if (source.getSourceType() != null) {
            result.setSourceType(convert(source.getSourceType()));
        }
        return result;
    }

}
