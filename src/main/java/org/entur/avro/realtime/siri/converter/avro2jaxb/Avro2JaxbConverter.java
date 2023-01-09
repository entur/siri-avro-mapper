package org.entur.avro.realtime.siri.converter.avro2jaxb;

import org.entur.avro.realtime.siri.converter.CommonConverter;
import org.entur.avro.realtime.siri.model.EstimatedTimetableDeliveryRecord;
import org.entur.avro.realtime.siri.model.ServiceDeliveryRecord;
import org.entur.avro.realtime.siri.model.SiriRecord;
import org.entur.avro.realtime.siri.model.SituationExchangeDeliveryRecord;
import org.entur.avro.realtime.siri.model.VehicleMonitoringDeliveryRecord;
import uk.org.siri.siri21.RequestorRef;
import uk.org.siri.siri21.ServiceDelivery;
import uk.org.siri.siri21.Siri;

import java.util.List;
public class Avro2JaxbConverter extends CommonConverter {
    public static Siri convert(SiriRecord siriRecord) {
        Siri siri = new Siri();
        if (siriRecord.getServiceDelivery() != null) {
            siri.setServiceDelivery(convert(siriRecord.getServiceDelivery()));
        }
        siri.setVersion(siri.getVersion());

        return siri;
    }

    static ServiceDelivery convert(ServiceDeliveryRecord serviceDelivery) {
        ServiceDelivery mapped = new ServiceDelivery();

        if (serviceDelivery.getResponseTimestamp() != null) {
            mapped.setResponseTimestamp(convertDate(serviceDelivery.getResponseTimestamp()));
        }


        if (serviceDelivery.getProducerRef() != null) {
            mapped.setProducerRef(
                    setValue(RequestorRef.class, serviceDelivery.getProducerRef())
            );
        }

        if (serviceDelivery.getMoreData() != null) {
            mapped.setMoreData(serviceDelivery.getMoreData());
        }

        List<EstimatedTimetableDeliveryRecord> estimatedTimetableDeliveries = serviceDelivery.getEstimatedTimetableDeliveries();
        if (estimatedTimetableDeliveries != null && !estimatedTimetableDeliveries.isEmpty()) {
            mapped.getEstimatedTimetableDeliveries().addAll(EstimatedTimetableDeliveryConverter.convert(estimatedTimetableDeliveries));
        }

        List<SituationExchangeDeliveryRecord> sxDeliveries = serviceDelivery.getSituationExchangeDeliveries();
        if (sxDeliveries != null && !sxDeliveries.isEmpty()) {
          mapped.getSituationExchangeDeliveries().addAll(SituationExchangeDeliveryConverter.convert(sxDeliveries));
        }


        List<VehicleMonitoringDeliveryRecord> vmDeliveries = serviceDelivery.getVehicleMonitoringDeliveries();
        if (vmDeliveries != null && !vmDeliveries.isEmpty()) {
            mapped.getVehicleMonitoringDeliveries().addAll(VehicleMonitoringDeliveryConverter.convert(vmDeliveries));
        }

        return mapped;
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
