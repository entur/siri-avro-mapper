package org.entur.avro.realtime.siri.converter.jaxb2avro;

import org.entur.avro.realtime.siri.converter.CommonConverter;
import org.entur.avro.realtime.siri.model.ServiceDeliveryRecord;
import org.entur.avro.realtime.siri.model.SiriRecord;
import uk.org.siri.siri21.ServiceDelivery;
import uk.org.siri.siri21.Siri;

public class Jaxb2AvroConverter extends CommonConverter {

    public static SiriRecord convert(Siri siri) {
        return SiriRecord.newBuilder()
                .setVersion(siri.getVersion())
                .setServiceDelivery(convert(siri.getServiceDelivery()))
                .build();
    }

    static ServiceDeliveryRecord convert(ServiceDelivery serviceDelivery) {
        if (serviceDelivery == null) {
            return null;
        }
        return ServiceDeliveryRecord.newBuilder()
                .setResponseTimestamp(convert(serviceDelivery.getResponseTimestamp()))
                .setProducerRef(getValue(serviceDelivery.getProducerRef()))
                .setMoreData(serviceDelivery.isMoreData())
                .setEstimatedTimetableDeliveries(EstimatedTimetableDeliveryConverter.convert(serviceDelivery.getEstimatedTimetableDeliveries()))
                .setSituationExchangeDeliveries(SituationExchangeDeliveryConverter.convert(serviceDelivery.getSituationExchangeDeliveries()))
                .setVehicleMonitoringDeliveries(VehicleMonitoringDeliveryConverter.convert(serviceDelivery.getVehicleMonitoringDeliveries()))
                .build();
    }

}
