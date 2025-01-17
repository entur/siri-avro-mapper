package org.entur.avro.realtime.siri.converter.avro2jaxb;

import org.entur.avro.realtime.siri.model.AccessibilityAssessmentRecord;
import org.entur.avro.realtime.siri.model.AccessibilityLimitationRecord;
import org.entur.avro.realtime.siri.model.AdviceRecord;
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
import org.entur.avro.realtime.siri.model.ConsequenceRecord;
import org.entur.avro.realtime.siri.model.IndirectSectionRefRecord;
import org.entur.avro.realtime.siri.model.InfoLinkRecord;
import org.entur.avro.realtime.siri.model.PtSituationElementRecord;
import org.entur.avro.realtime.siri.model.SituationExchangeDeliveryRecord;
import org.entur.avro.realtime.siri.model.SourceRecord;
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
import uk.org.siri.siri21.PtAdviceStructure;
import uk.org.siri.siri21.PtConsequenceStructure;
import uk.org.siri.siri21.PtConsequencesStructure;
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
import java.util.Collections;
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
            sxDelivery.setVersion(delivery.getVersion().toString());
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

    static PtSituationElement convert(PtSituationElementRecord situation) {
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
            element.setProgress(convertProgress(situation.getProgress()));
        }
        if (situation.getVersionedAtTime() != null) {
            element.setVersionedAtTime(convertDate(situation.getVersionedAtTime()));
        }
        if (!isNullOrEmpty(situation.getValidityPeriods())) {
            element.getValidityPeriods().addAll(
                    convertValidityPeriods(situation.getValidityPeriods())
            );
        }
        if (situation.getUndefinedReason() != null) {
            // (TODO: Hard-coded as defined in Nordic SIRI profile - should probably be more generic...
            element.setUndefinedReason(situation.getUndefinedReason().toString());
        }
        if (situation.getSeverity() != null) {
            element.setSeverity(convertSeverity(situation.getSeverity()));
        }
        if (situation.getPriority() != null) {
            element.setPriority(BigInteger.valueOf(situation.getPriority()));
        }
        if (situation.getReportType() != null) {
            element.setReportType(convertReportType(situation.getReportType()));
        }
        if (situation.getKeywords() != null) {
            element.getKeywords().addAll(convertStringList(situation.getKeywords()));
        }
        if (situation.getPlanned() != null) {
            element.setPlanned(situation.getPlanned());
        }
        if (!isNullOrEmpty(situation.getSummaries())) {
            element.getSummaries().addAll(setTranslatedValues(DefaultedTextStructure.class, situation.getSummaries()));
        }
        if (!isNullOrEmpty(situation.getDescriptions())) {
            element.getDescriptions().addAll(setTranslatedValues(DefaultedTextStructure.class, situation.getDescriptions()));
        }
        if (!isNullOrEmpty(situation.getDetails())) {
            element.getDetails().addAll(setTranslatedValues(DefaultedTextStructure.class, situation.getDetails()));
        }
        if (!isNullOrEmpty(situation.getAdvices())) {
            element.getAdvices().addAll(setTranslatedValues(DefaultedTextStructure.class, situation.getAdvices()));
        }
        if (!isNullOrEmpty(situation.getInfoLinks())) {
            element.setInfoLinks(convertInfoLinks(situation.getInfoLinks()));
        }
        if (situation.getAffects() != null) {
            element.setAffects(convert(situation.getAffects()));
        }
        if (!isNullOrEmpty(situation.getConsequences())) {
            element.setConsequences(convertConsequences(situation.getConsequences()));
        }
        return element;
    }

    private static PtConsequencesStructure convertConsequences(List<ConsequenceRecord> consequences) {
        if (consequences == null) {
            return null;
        }
        PtConsequencesStructure consequencesStructure = new PtConsequencesStructure();
        for (ConsequenceRecord consequence : consequences) {
            consequencesStructure.getConsequences().add(convert(consequence));
        }
        return consequencesStructure;
    }

    private static PtConsequenceStructure convert(ConsequenceRecord consequence) {
        if (consequence == null) {
            return null;
        }
        PtConsequenceStructure consequenceStructure = new PtConsequenceStructure();
        consequenceStructure.setAdvice(convert(consequence.getAdvice()));
        return consequenceStructure;
    }

    private static PtAdviceStructure convert(AdviceRecord advice) {
        if (advice == null) {
            return null;
        }
        PtAdviceStructure adviceStructure = new PtAdviceStructure();
        adviceStructure.setAdviceType(convertAdviceType(advice.getAdviceType()));
        return adviceStructure;
    }


    private static AffectsScopeStructure convert(AffectsRecord affects) {
        if (affects == null) {
            return null;
        }
        AffectsScopeStructure affectsScopeStructure = new AffectsScopeStructure();
        if (!isNullOrEmpty(affects.getNetworks())) {
            affectsScopeStructure.setNetworks(convertNetworks(affects.getNetworks()));
        }
        if (!isNullOrEmpty(affects.getStopPlaces())) {
            affectsScopeStructure.setStopPlaces(convertStopPlaces(affects.getStopPlaces()));
        }
        if (!isNullOrEmpty(affects.getStopPoints())) {
            affectsScopeStructure.setStopPoints(convertStopPoints(affects.getStopPoints()));
        }
        if (!isNullOrEmpty(affects.getVehicleJourneys())) {
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
        if (!isNullOrEmpty(network.getAffectedOperators())) {
            result.getAffectedOperators().addAll(
                    convertAffectedOperators(network.getAffectedOperators())
            );
        }
        if (network.getNetworkRef() != null) {
            result.setNetworkRef(setValue(NetworkRefStructure.class, network.getNetworkRef()));
        }
        if (network.getVehicleMode() != null) {
            result.setVehicleMode(convertVehicleMode(network.getVehicleMode()));
        }
        if (network.getAirSubmode() != null) {
            result.setAirSubmode(convertAirSubmode(network.getAirSubmode()));
        }
        if (network.getBusSubmode() != null) {
            result.setBusSubmode(convertBusSubmode(network.getBusSubmode()));
        }
        if (network.getCoachSubmode() != null) {
            result.setCoachSubmode(convertCoachSubmode(network.getCoachSubmode()));
        }
        if (network.getMetroSubmode() != null) {
            result.setMetroSubmode(convertMetroSubmode(network.getMetroSubmode()));
        }
        if (network.getRailSubmode() != null) {
            result.setRailSubmode(convertRailSubmode(network.getRailSubmode()));
        }
        if (network.getTramSubmode() != null) {
            result.setTramSubmode(convertTramSubmode(network.getTramSubmode()));
        }
        if (network.getWaterSubmode() != null) {
            result.setWaterSubmode(convertWaterSubmode(network.getWaterSubmode()));
        }
        if (!isNullOrEmpty(network.getAffectedLines())) {
            result.getAffectedLines()
                    .addAll(
                            convertLines(network.getAffectedLines())
                    );
        }
        return result;
    }

    private static List<AffectedOperatorStructure> convertAffectedOperators(List<AffectedOperatorRecord> affectedOperators) {
        List<AffectedOperatorStructure> result = new ArrayList<>();
        for (AffectedOperatorRecord affectedOperator : affectedOperators) {
            result.add(convert(affectedOperator));
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
        if (!isNullOrEmpty(affectedLine.getRoutes())) {
            result.setRoutes(new AffectedLineStructure.Routes());
            result.getRoutes().getAffectedRoutes()
                    .addAll(convertRoutes(affectedLine.getRoutes()));
        }
        if (!isNullOrEmpty(affectedLine.getSections())) {
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
            componentStructure.setComponentType(convertStopPlaceComponentType(component.getComponentType()));
        }
        if (component.getAccessFeatureType() != null) {
            componentStructure.setAccessFeatureType(convertAccessibilityFeature(component.getAccessFeatureType()));
        }
        return componentStructure;
    }

    private static AccessibilityFeatureEnumeration convertAccessibilityFeature(CharSequence accessFeatureType) {
        return AccessibilityFeatureEnumeration.valueOf(accessFeatureType.toString());
    }

    private static StopPlaceComponentTypeEnumeration convertStopPlaceComponentType(CharSequence componentType) {
        return StopPlaceComponentTypeEnumeration.valueOf(componentType.toString());
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
            result.setWheelchairAccess(setValue(AccessibilityStructure.class, limitation.getWheelchairAccess().toString()));
        }
        if (limitation.getStepFreeAccess() != null) {
            result.setStepFreeAccess(setValue(AccessibilityStructure.class, limitation.getStepFreeAccess().toString()));
        }
        if (limitation.getEscalatorFreeAccess() != null) {
            result.setEscalatorFreeAccess(setValue(AccessibilityStructure.class, limitation.getEscalatorFreeAccess().toString()));
        }
        if (limitation.getLiftFreeAccess() != null) {
            result.setLiftFreeAccess(setValue(AccessibilityStructure.class, limitation.getLiftFreeAccess().toString()));
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
        if (!isNullOrEmpty(vehicleJourney.getVehicleJourneyRefs())) {
            result.getVehicleJourneyReves()
                    .addAll(
                            setValues(
                                    VehicleJourneyRef.class,
                                    vehicleJourney.getVehicleJourneyRefs()
                            )
                    );
        }
        if (!isNullOrEmpty(vehicleJourney.getDatedVehicleJourneyRefs())) {
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

        if (!isNullOrEmpty(vehicleJourney.getRoutes())) {
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
        if (route.getStopPoints() != null && !isNullOrEmpty(route.getStopPoints().getStopPoints())) {
            result.setStopPoints(convertRouteStopPoints(route.getStopPoints()));
        }
        if (!isNullOrEmpty(route.getSections())) {
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
        if (!isNullOrEmpty(stopPoint.getStopPointNames())) {
            result.getStopPointNames().addAll(
                    setTranslatedValues(NaturalLanguageStringStructure.class, stopPoint.getStopPointNames())
            );
        }
        if (!isNullOrEmpty(stopPoint.getStopConditions())) {
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
        if (!isNullOrEmpty(operator.getOperatorNames())) {
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
            result.setUri(infoLink.getUri().toString());
        }
        if (!isNullOrEmpty(infoLink.getLabels())) {
            result.getLabels().addAll(setTranslatedValues(NaturalLanguageStringStructure.class, infoLink.getLabels()));
        }
        return result;
    }

    private static ReportTypeEnumeration convertReportType(CharSequence reportType) {
        if (reportType == null) {
            return null;
        }
        return ReportTypeEnumeration.valueOf(reportType.toString());
    }

    private static List<HalfOpenTimestampOutputRangeStructure> convertValidityPeriods(List<ValidityPeriodRecord> validityPeriods) {
        if (validityPeriods == null) {
            return Collections.emptyList();
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
            result.setSourceType(convertSourceType(source.getSourceType().toString()));
        }
        return result;
    }

}
