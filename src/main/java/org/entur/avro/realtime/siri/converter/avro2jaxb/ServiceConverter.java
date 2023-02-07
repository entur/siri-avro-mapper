package org.entur.avro.realtime.siri.converter.avro2jaxb;

import org.entur.avro.realtime.siri.converter.CommonConverter;
import org.entur.avro.realtime.siri.model.EstimatedTimetableDeliveryRecord;
import org.entur.avro.realtime.siri.model.ServiceDeliveryRecord;
import org.entur.avro.realtime.siri.model.ServiceRequestRecord;
import org.entur.avro.realtime.siri.model.SituationExchangeDeliveryRecord;
import org.entur.avro.realtime.siri.model.VehicleMonitoringDeliveryRecord;
import uk.org.siri.siri21.MessageRefStructure;
import uk.org.siri.siri21.RequestorRef;
import uk.org.siri.siri21.ServiceDelivery;
import uk.org.siri.siri21.ServiceRequest;

import java.util.List;

public class ServiceConverter extends CommonConverter {
    static ServiceRequest convert(ServiceRequestRecord serviceRequest) {
        if (serviceRequest == null) {
            return null;
        }
        ServiceRequest result = new ServiceRequest();
        if (serviceRequest.getRequestTimestamp() != null) {
            result.setRequestTimestamp(convertDate(serviceRequest.getRequestTimestamp()));
        }
        if (serviceRequest.getRequestorRef() != null) {
            result.setRequestorRef(setValue(RequestorRef.class, serviceRequest.getRequestorRef()));
        }
        if (!isNullOrEmpty(serviceRequest.getEstimatedTimetableRequests())) {
            result.getEstimatedTimetableRequests().addAll(
                    SubscriptionConverter.convertEtServiceRequests(serviceRequest.getEstimatedTimetableRequests())
            );
        }
        if (!isNullOrEmpty(serviceRequest.getVehicleMonitoringRequests())) {
            result.getVehicleMonitoringRequests().addAll(
                    SubscriptionConverter.convertVmServiceRequests(serviceRequest.getVehicleMonitoringRequests())
            );
        }
        if (!isNullOrEmpty(serviceRequest.getSituationExchangeRequests())) {
            result.getSituationExchangeRequests().addAll(
                    SubscriptionConverter.convertSxServiceRequests(serviceRequest.getSituationExchangeRequests())
            );
        }
        return result;
    }

    static ServiceDelivery convert(ServiceDeliveryRecord serviceDelivery) {
        if (serviceDelivery == null) {
            return null;
        }
        ServiceDelivery mapped = new ServiceDelivery();

        if (serviceDelivery.getResponseTimestamp() != null) {
            mapped.setResponseTimestamp(CommonConverter.convertDate(serviceDelivery.getResponseTimestamp()));
        }


        if (serviceDelivery.getProducerRef() != null) {
            mapped.setProducerRef(
                    CommonConverter.setValue(RequestorRef.class, serviceDelivery.getProducerRef())
            );
        }

        if (serviceDelivery.getRequestMessageRef() != null) {
            mapped.setRequestMessageRef(
                    CommonConverter.setValue(MessageRefStructure.class, serviceDelivery.getRequestMessageRef())
            );
        }

        if (serviceDelivery.getMoreData() != null) {
            mapped.setMoreData(serviceDelivery.getMoreData());
        }

        List<EstimatedTimetableDeliveryRecord> estimatedTimetableDeliveries = serviceDelivery.getEstimatedTimetableDeliveries();
        if (!isNullOrEmpty(estimatedTimetableDeliveries)) {
            mapped.getEstimatedTimetableDeliveries().addAll(EstimatedTimetableDeliveryConverter.convert(estimatedTimetableDeliveries));
        }

        List<SituationExchangeDeliveryRecord> sxDeliveries = serviceDelivery.getSituationExchangeDeliveries();
        if (!isNullOrEmpty(sxDeliveries)) {
          mapped.getSituationExchangeDeliveries().addAll(SituationExchangeDeliveryConverter.convert(sxDeliveries));
        }


        List<VehicleMonitoringDeliveryRecord> vmDeliveries = serviceDelivery.getVehicleMonitoringDeliveries();
        if (!isNullOrEmpty(vmDeliveries)) {
            mapped.getVehicleMonitoringDeliveries().addAll(VehicleMonitoringDeliveryConverter.convert(vmDeliveries));
        }

        return mapped;
    }
}
