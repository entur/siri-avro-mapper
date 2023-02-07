package org.entur.avro.realtime.siri.converter.jaxb2avro;

import org.entur.avro.realtime.siri.converter.CommonConverter;
import org.entur.avro.realtime.siri.model.ServiceDeliveryRecord;
import org.entur.avro.realtime.siri.model.ServiceRequestRecord;
import uk.org.siri.siri21.ServiceDelivery;
import uk.org.siri.siri21.ServiceRequest;

public class ServiceConverter extends CommonConverter {
    static ServiceRequestRecord convert(ServiceRequest serviceRequest) {
        if (serviceRequest == null) {
            return null;
        }
        return ServiceRequestRecord.newBuilder()
                .setRequestTimestamp(CommonConverter.convert(serviceRequest.getRequestTimestamp()))
                .setRequestorRef(CommonConverter.getValue(serviceRequest.getRequestorRef()))
                .setEstimatedTimetableRequests(SubscriptionConverter.convertEtServiceRequests(serviceRequest.getEstimatedTimetableRequests()))
                .setVehicleMonitoringRequests(SubscriptionConverter.convertVmServiceRequests(serviceRequest.getVehicleMonitoringRequests()))
                .setSituationExchangeRequests(SubscriptionConverter.convertSxServiceRequests(serviceRequest.getSituationExchangeRequests()))
                .build();
    }

    static ServiceDeliveryRecord convert(ServiceDelivery serviceDelivery) {
        if (serviceDelivery == null) {
            return null;
        }
        return ServiceDeliveryRecord.newBuilder()
                .setResponseTimestamp(CommonConverter.convert(serviceDelivery.getResponseTimestamp()))
                .setProducerRef(CommonConverter.getValue(serviceDelivery.getProducerRef()))
                .setRequestMessageRef(CommonConverter.getValue(serviceDelivery.getRequestMessageRef()))
                .setMoreData(serviceDelivery.isMoreData())
                .setEstimatedTimetableDeliveries(EstimatedTimetableDeliveryConverter.convert(serviceDelivery.getEstimatedTimetableDeliveries()))
                .setSituationExchangeDeliveries(SituationExchangeDeliveryConverter.convert(serviceDelivery.getSituationExchangeDeliveries()))
                .setVehicleMonitoringDeliveries(VehicleMonitoringDeliveryConverter.convert(serviceDelivery.getVehicleMonitoringDeliveries()))
                .build();
    }
}
