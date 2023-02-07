package org.entur.avro.realtime.siri.converter.avro2jaxb;

import org.entur.avro.realtime.siri.converter.CommonConverter;
import org.entur.avro.realtime.siri.model.EstimatedVehicleJourneyRecord;
import org.entur.avro.realtime.siri.model.PtSituationElementRecord;
import org.entur.avro.realtime.siri.model.SiriRecord;
import org.entur.avro.realtime.siri.model.VehicleActivityRecord;
import uk.org.siri.siri21.EstimatedVehicleJourney;
import uk.org.siri.siri21.PtSituationElement;
import uk.org.siri.siri21.Siri;
import uk.org.siri.siri21.VehicleActivityStructure;

public class Avro2JaxbConverter extends CommonConverter {
    public static Siri convert(SiriRecord siriRecord) {
        Siri siri = new Siri();
        if (siriRecord.getServiceDelivery() != null) {
            siri.setServiceDelivery(ServiceConverter.convert(siriRecord.getServiceDelivery()));
        }
        if (siriRecord.getServiceRequest() != null) {
            siri.setServiceRequest(ServiceConverter.convert(siriRecord.getServiceRequest()));
        }
        if (siriRecord.getHeartbeatNotification() != null) {
            siri.setHeartbeatNotification(SubscriptionConverter.convert(siriRecord.getHeartbeatNotification()));
        }
        if (siriRecord.getSubscriptionRequest() != null) {
            siri.setSubscriptionRequest(SubscriptionConverter.convert(siriRecord.getSubscriptionRequest()));
        }
        if (siriRecord.getSubscriptionResponse() != null) {
            siri.setSubscriptionResponse(SubscriptionConverter.convert(siriRecord.getSubscriptionResponse()));
        }
        if (siriRecord.getTerminateSubscriptionRequest() != null) {
            siri.setTerminateSubscriptionRequest(SubscriptionConverter.convert(siriRecord.getTerminateSubscriptionRequest()));
        }
        if (siriRecord.getTerminateSubscriptionResponse() != null) {
            siri.setTerminateSubscriptionResponse(SubscriptionConverter.convert(siriRecord.getTerminateSubscriptionResponse()));
        }
        siri.setVersion(siri.getVersion());

        return siri;
    }


    public static EstimatedVehicleJourney convert(EstimatedVehicleJourneyRecord et) {
        return EstimatedTimetableDeliveryConverter.convert(et);
    }
    public static VehicleActivityStructure convert(VehicleActivityRecord vm) {
        return VehicleMonitoringDeliveryConverter.convert(vm);
    }
    public static PtSituationElement convert(PtSituationElementRecord sx) {
        return SituationExchangeDeliveryConverter.convert(sx);
    }


    //    private Extensions convert(Map<CharSequence, CharSequence> extensionsMap) {
//        Extensions extensions = new Extensions();
//        Document document = getDocument();
//        for (Map.Entry<CharSequence, CharSequence> entry : extensionsMap.entrySet()) {
//            Element element = document.createElement((String) entry.getKey());
//            element.appendChild(document.createTextNode((String)entry.getValue()));
//            extensions.getAnies().add(element);
//        }
//        return extensions;
//    }
//
//    private static Document getDocument() {
//        try {
//            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
//            DocumentBuilder db = dbf.newDocumentBuilder();
//            Document document = db.newDocument();
//            return document;
//        } catch (ParserConfigurationException e) {
//            throw new RuntimeException(e);
//        }
//    }

}
