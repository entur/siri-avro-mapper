package org.entur.avro.realtime.siri.converter.avro2jaxb;

import org.entur.avro.realtime.siri.converter.CommonConverter;
import org.entur.avro.realtime.siri.model.EstimatedTimetableRequestRecord;
import org.entur.avro.realtime.siri.model.EstimatedTimetableSubscriptionRecord;
import org.entur.avro.realtime.siri.model.HeartbeatNotificationRecord;
import org.entur.avro.realtime.siri.model.ResponseStatusRecord;
import org.entur.avro.realtime.siri.model.ServiceRequestRecord;
import org.entur.avro.realtime.siri.model.SituationExchangeRequestRecord;
import org.entur.avro.realtime.siri.model.SituationExchangeSubscriptionRecord;
import org.entur.avro.realtime.siri.model.SubscriptionContextRecord;
import org.entur.avro.realtime.siri.model.SubscriptionRequestRecord;
import org.entur.avro.realtime.siri.model.SubscriptionResponseRecord;
import org.entur.avro.realtime.siri.model.TerminateSubscriptionRequestRecord;
import org.entur.avro.realtime.siri.model.TerminateSubscriptionResponseRecord;
import org.entur.avro.realtime.siri.model.TerminationResponseStatusRecord;
import org.entur.avro.realtime.siri.model.VehicleMonitoringRequestRecord;
import org.entur.avro.realtime.siri.model.VehicleMonitoringSubscriptionRecord;
import uk.org.siri.siri21.EstimatedTimetableRequestStructure;
import uk.org.siri.siri21.EstimatedTimetableSubscriptionStructure;
import uk.org.siri.siri21.HeartbeatNotificationStructure;
import uk.org.siri.siri21.MessageQualifierStructure;
import uk.org.siri.siri21.MessageRefStructure;
import uk.org.siri.siri21.OtherErrorStructure;
import uk.org.siri.siri21.RequestorRef;
import uk.org.siri.siri21.ResponseStatus;
import uk.org.siri.siri21.ServiceDeliveryErrorConditionElement;
import uk.org.siri.siri21.ServiceRequest;
import uk.org.siri.siri21.SituationExchangeRequestStructure;
import uk.org.siri.siri21.SituationExchangeSubscriptionStructure;
import uk.org.siri.siri21.SubscriptionContextStructure;
import uk.org.siri.siri21.SubscriptionQualifierStructure;
import uk.org.siri.siri21.SubscriptionRequest;
import uk.org.siri.siri21.SubscriptionResponseStructure;
import uk.org.siri.siri21.TerminateSubscriptionRequestStructure;
import uk.org.siri.siri21.TerminateSubscriptionResponseStructure;
import uk.org.siri.siri21.TerminationResponseStatusStructure;
import uk.org.siri.siri21.VehicleMonitoringRequestStructure;
import uk.org.siri.siri21.VehicleMonitoringSubscriptionStructure;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SubscriptionConverter extends CommonConverter {
    static HeartbeatNotificationStructure convert(HeartbeatNotificationRecord heartbeatNotification) {
        if (heartbeatNotification == null) {
            return null;
        }
        HeartbeatNotificationStructure result = new HeartbeatNotificationStructure();
        result.setStatus(heartbeatNotification.getStatus());
        if (heartbeatNotification.getProducerRef() != null) {
            result.setProducerRef(setValue(RequestorRef.class, heartbeatNotification.getProducerRef()));
        }
        if (heartbeatNotification.getRequestTimestamp() != null) {
            result.setRequestTimestamp(convertDate(heartbeatNotification.getRequestTimestamp()));
        }
        if (heartbeatNotification.getServiceStartedTime() != null) {
            result.setServiceStartedTime(convertDate(heartbeatNotification.getServiceStartedTime()));
        }

        return result;
    }

    public static SubscriptionRequest convert(SubscriptionRequestRecord subscriptionRequest) {
        if (subscriptionRequest == null) {
            return null;
        }

        SubscriptionRequest result = new SubscriptionRequest();

        if (subscriptionRequest.getRequestTimestamp() != null) {
            result.setRequestTimestamp(convertDate(subscriptionRequest.getRequestTimestamp()));
        }
        if (subscriptionRequest.getRequestorRef() != null) {
            result.setRequestorRef(setValue(RequestorRef.class, subscriptionRequest.getRequestorRef()));
        }
        if (subscriptionRequest.getAddress() != null) {
            result.setAddress(subscriptionRequest.getAddress().toString());
        }
        if (subscriptionRequest.getConsumerAddress() != null) {
            result.setConsumerAddress(subscriptionRequest.getConsumerAddress().toString());
        }
        if (subscriptionRequest.getMessageIdentifier() != null) {
            result.setMessageIdentifier(setValue(MessageQualifierStructure.class, subscriptionRequest.getMessageIdentifier()));
        }
        if (subscriptionRequest.getSubscriptionContext() != null) {
            result.setSubscriptionContext(convert(subscriptionRequest.getSubscriptionContext()));
        }
        if (!isNullOrEmpty(subscriptionRequest.getEstimatedTimetableSubscriptionRequests())) {
            result.getEstimatedTimetableSubscriptionRequests().addAll(
                    convertEtSubRequests(subscriptionRequest.getEstimatedTimetableSubscriptionRequests())
            );
        }
        if (!isNullOrEmpty(subscriptionRequest.getVehicleMonitoringSubscriptionRequests())) {
            result.getVehicleMonitoringSubscriptionRequests().addAll(
                    convertVmSubRequests(subscriptionRequest.getVehicleMonitoringSubscriptionRequests())
            );
        }
        if (!isNullOrEmpty(subscriptionRequest.getSituationExchangeSubscriptionRequests())) {
            result.getSituationExchangeSubscriptionRequests().addAll(
                    convertSxSubRequests(subscriptionRequest.getSituationExchangeSubscriptionRequests())
            );
        }

        return result;
    }

    private static List<VehicleMonitoringSubscriptionStructure> convertVmSubRequests(List<VehicleMonitoringSubscriptionRecord> subscriptionRequests) {
        if (isNullOrEmpty(subscriptionRequests)) {
            return Collections.emptyList();
        }
        List<VehicleMonitoringSubscriptionStructure> records = new ArrayList<>();
        for (VehicleMonitoringSubscriptionRecord request : subscriptionRequests) {
            records.add(convert(request));
        }
        return records;
    }

    private static VehicleMonitoringSubscriptionStructure convert(VehicleMonitoringSubscriptionRecord request) {
        if (request == null) {
            return null;
        }
        VehicleMonitoringSubscriptionStructure result = new VehicleMonitoringSubscriptionStructure();

        if (request.getSubscriptionIdentifier() != null) {
            result.setSubscriptionIdentifier(setValue(
                            SubscriptionQualifierStructure.class,
                            request.getSubscriptionIdentifier()
                    ));
        }
        if (request.getSubscriberRef() != null) {
            result.setSubscriberRef(setValue(
                    RequestorRef.class,
                    request.getSubscriberRef()
            ));
        }
        if (request.getIncrementalUpdates() != null) {
            result.setIncrementalUpdates(request.getIncrementalUpdates());
        }
        if (request.getInitialTerminationTime() != null) {
            result.setInitialTerminationTime(convertDate(request.getInitialTerminationTime()));
        }
        if (request.getChangeBeforeUpdates() != null) {
            result.setChangeBeforeUpdates(convertDuration(request.getChangeBeforeUpdates()));
        }
        if (request.getUpdateInterval() != null) {
            result.setUpdateInterval(convertDuration(request.getUpdateInterval()));
        }
        if (request.getVehicleMonitoringRequest() != null) {
            result.setVehicleMonitoringRequest(convert(request.getVehicleMonitoringRequest()));
        }
        return result;
    }

    private static VehicleMonitoringRequestStructure convert(VehicleMonitoringRequestRecord request) {
        if (request == null) {
            return null;
        }
        VehicleMonitoringRequestStructure result = new VehicleMonitoringRequestStructure();
        if (request.getVersion() != null) {
            result.setVersion(request.getVersion().toString());
        }
        if (request.getMessageIdentifier() != null) {
            result.setMessageIdentifier(setValue(MessageQualifierStructure.class, request.getMessageIdentifier()));
        }
        if (request.getRequestTimestamp() != null) {
            result.setRequestTimestamp(convertDate(request.getRequestTimestamp()));
        }
        return result;
    }

    private static List<SituationExchangeSubscriptionStructure> convertSxSubRequests(List<SituationExchangeSubscriptionRecord> subscriptionRequests) {
        if (isNullOrEmpty(subscriptionRequests)) {
            return Collections.emptyList();
        }
        List<SituationExchangeSubscriptionStructure> records = new ArrayList<>();
        for (SituationExchangeSubscriptionRecord request : subscriptionRequests) {
            records.add(convert(request));
        }
        return records;
    }

    private static SituationExchangeSubscriptionStructure convert(SituationExchangeSubscriptionRecord request) {
        if (request == null) {
            return null;
        }
        SituationExchangeSubscriptionStructure result = new SituationExchangeSubscriptionStructure();

        if (request.getSubscriberRef() != null) {
            result.setSubscriberRef(setValue(RequestorRef.class, request.getSubscriberRef()));
        }
        if (request.getSubscriptionIdentifier() != null) {
            result.setSubscriptionIdentifier(setValue(SubscriptionQualifierStructure.class, request.getSubscriptionIdentifier()));
        }
        result.setIncrementalUpdates(request.getIncrementalUpdates());
        if (request.getInitialTerminationTime() != null) {
            result.setInitialTerminationTime(convertDate(request.getInitialTerminationTime()));
        }
        if (request.getSituationExchangeRequest() != null) {
            result.setSituationExchangeRequest(convert(request.getSituationExchangeRequest()));
        }
        return result;
    }

    private static SituationExchangeRequestStructure convert(SituationExchangeRequestRecord request) {
        if (request == null) {
            return null;
        }
        SituationExchangeRequestStructure result = new SituationExchangeRequestStructure();
        if (request.getVersion() != null) {
            result.setVersion( request.getVersion().toString());
        }
        if (request.getMessageIdentifier() != null) {
            result.setMessageIdentifier(setValue(MessageQualifierStructure.class, request.getMessageIdentifier()));
        }
        if (request.getRequestTimestamp() != null) {
            result.setRequestTimestamp(convertDate(request.getRequestTimestamp()));
        }
        if (request.getPreviewInterval() != null) {
            result.setPreviewInterval(convertDuration(request.getPreviewInterval()));
        }

        return result;
    }

    private static List<EstimatedTimetableSubscriptionStructure> convertEtSubRequests(List<EstimatedTimetableSubscriptionRecord> subscriptionRequests) {
        if (isNullOrEmpty(subscriptionRequests)) {
            return Collections.emptyList();
        }
        List<EstimatedTimetableSubscriptionStructure> records = new ArrayList<>();
        for (EstimatedTimetableSubscriptionRecord request : subscriptionRequests) {
            records.add(convert(request));
        }
        return records;
    }

    private static EstimatedTimetableSubscriptionStructure convert(EstimatedTimetableSubscriptionRecord request) {
        if (request == null) {
            return null;
        }
        EstimatedTimetableSubscriptionStructure result = new EstimatedTimetableSubscriptionStructure();

        if (request.getSubscriberRef() != null){
            result.setSubscriberRef(setValue(RequestorRef.class, request.getSubscriberRef()));
        }
        if (request.getSubscriptionIdentifier() != null) {
            result.setSubscriptionIdentifier(setValue(SubscriptionQualifierStructure.class, request.getSubscriptionIdentifier()));
        }
        result.setIncrementalUpdates(request.getIncrementalUpdates());
        if (request.getChangeBeforeUpdates() != null) {
            result.setChangeBeforeUpdates(convertDuration(request.getChangeBeforeUpdates()));
        }
        if (request.getInitialTerminationTime() != null) {
            result.setInitialTerminationTime(convertDate(request.getInitialTerminationTime()));
        }
        if (request.getEstimatedTimetableRequest() != null) {
            result.setEstimatedTimetableRequest(convert(request.getEstimatedTimetableRequest()));
        }
        return result;
    }

    private static EstimatedTimetableRequestStructure convert(EstimatedTimetableRequestRecord request) {
        if (request == null) {
            return null;
        }
        EstimatedTimetableRequestStructure result = new EstimatedTimetableRequestStructure();
        if (request.getVersion() != null) {
            result.setVersion(request.getVersion().toString());
        }
        if (request.getRequestTimestamp() != null) {
            result.setRequestTimestamp(convertDate(request.getRequestTimestamp()));
        }
        if (request.getPreviewInterval() != null) {
            result.setPreviewInterval(convertDuration(request.getPreviewInterval()));
        }
        if (request.getMessageIdentifier() != null) {
            result.setMessageIdentifier(setValue(MessageQualifierStructure.class, request.getMessageIdentifier()));
        }
        return result;
    }


    private static SubscriptionContextStructure convert(SubscriptionContextRecord subscriptionContext) {
        if (subscriptionContext == null) {
            return null;
        }
        SubscriptionContextStructure result = new SubscriptionContextStructure();
        if (subscriptionContext.getHeartbeatInterval() != null) {
            result.setHeartbeatInterval(convertDuration(subscriptionContext.getHeartbeatInterval()));
        }
        return result;
    }

    public static SubscriptionResponseStructure convert(SubscriptionResponseRecord response) {
        if (response == null) {
            return null;
        }
        SubscriptionResponseStructure result = new SubscriptionResponseStructure();
        if (response.getRequestMessageRef() != null) {
            result.setRequestMessageRef(setValue(MessageRefStructure.class, response.getRequestMessageRef()));
        }
        if (response.getResponderRef() != null) {
            result.setResponderRef(setValue(RequestorRef.class, response.getResponderRef()));
        }
        if (response.getResponseTimestamp() != null) {
            result.setResponseTimestamp(convertDate(response.getResponseTimestamp()));
        }
        if (!isNullOrEmpty(response.getResponseStatuses())) {
            result.getResponseStatuses().addAll(convertResponseStatuses(response.getResponseStatuses()));
        }
        return result;
    }

    private static List<ResponseStatus> convertResponseStatuses(List<ResponseStatusRecord> statuses) {
        if (isNullOrEmpty(statuses)) {
            return Collections.emptyList();
        }
        List<ResponseStatus> records = new ArrayList<>();
        for (ResponseStatusRecord status : statuses) {
            records.add(convert(status));
        }
        return records;
    }

    private static ResponseStatus convert(ResponseStatusRecord status) {
        if (status == null) {
            return null;
        }
        ResponseStatus result = new ResponseStatus();

        if (status.getResponseTimestamp() != null) {
            result.setResponseTimestamp(convertDate(status.getResponseTimestamp()));
        }
        if (status.getRequestMessageRef() != null) {
            result.setRequestMessageRef(setValue(MessageRefStructure.class, status.getRequestMessageRef()));
        }
        result.setStatus(status.getStatus());

        if (status.getErrorText() != null) {
            ServiceDeliveryErrorConditionElement errorCondition = new ServiceDeliveryErrorConditionElement();
            OtherErrorStructure otherError = new OtherErrorStructure();
            otherError.setErrorText(status.getErrorText().toString());
            errorCondition.setOtherError(otherError);
            result.setErrorCondition(errorCondition);
        }

        return result;
    }

    public static TerminateSubscriptionRequestStructure convert(TerminateSubscriptionRequestRecord request) {
        if (request == null) {
            return null;
        }
        TerminateSubscriptionRequestStructure result = new TerminateSubscriptionRequestStructure();
        if (request.getMessageIdentifier() != null) {
            result.setMessageIdentifier(setValue(MessageQualifierStructure.class, request.getMessageIdentifier()));
        }
        if (request.getRequestorRef() != null) {
            result.setRequestorRef(setValue(RequestorRef.class, request.getRequestorRef()));
        }
        if (request.getRequestTimestamp() != null) {
            result.setRequestTimestamp(convertDate(request.getRequestTimestamp()));
        }
        if (!isNullOrEmpty(request.getSubscriptionRefs())) {
            result.getSubscriptionReves().addAll(setValues(SubscriptionQualifierStructure.class, request.getSubscriptionRefs()));
        }
        return result;
    }

    public static TerminateSubscriptionResponseStructure convert(TerminateSubscriptionResponseRecord request) {
        if (request == null) {
            return null;
        }
        TerminateSubscriptionResponseStructure result = new TerminateSubscriptionResponseStructure();
        if (request.getResponseTimestamp() != null) {
            result.setResponseTimestamp(convertDate(request.getResponseTimestamp()));
        }
        result.getTerminationResponseStatuses().addAll(convertStatuses(request.getStatuses()));
        return result;
    }

    private static List<TerminationResponseStatusStructure> convertStatuses(List<TerminationResponseStatusRecord> statuses) {
        if (isNullOrEmpty(statuses)) {
            return Collections.emptyList();
        }
        List<TerminationResponseStatusStructure> records = new ArrayList<>();
        for (TerminationResponseStatusRecord status : statuses) {
            records.add(convert(status));
        }
        return records;
    }

    private static TerminationResponseStatusStructure convert(TerminationResponseStatusRecord status) {
        if (status == null) {
            return null;
        }
        TerminationResponseStatusStructure result = new TerminationResponseStatusStructure();

        result.setResponseTimestamp(convertDate(status.getResponseTimestamp()));
        result.setStatus(status.getStatus());
        result.setSubscriberRef(setValue(RequestorRef.class, status.getSubscriberRef()));
        result.setRequestMessageRef(setValue(MessageRefStructure.class, status.getRequestMessageRef()));
        result.setErrorCondition(createErrorCondition(status.getErrorText()));
        return result;
    }

    private static TerminationResponseStatusStructure.ErrorCondition createErrorCondition(CharSequence errorText) {
        if (errorText == null) {
            return null;
        }
        TerminationResponseStatusStructure.ErrorCondition errorCondition = new TerminationResponseStatusStructure.ErrorCondition();
        OtherErrorStructure otherError = new OtherErrorStructure();
        otherError.setErrorText(errorText.toString());
        errorCondition.setOtherError(otherError);

        return errorCondition;
    }

    public static ServiceRequest convert(ServiceRequestRecord serviceRequest) {
        if (serviceRequest == null) {
            return null;
        }
        ServiceRequest result = new ServiceRequest();
        if (serviceRequest.getRequestorRef() != null) {
            result.setRequestorRef(setValue(RequestorRef.class, serviceRequest.getRequestorRef()));
        }
        if (serviceRequest.getRequestTimestamp() != null) {
            result.setRequestTimestamp(convertDate(serviceRequest.getRequestTimestamp()));
        }
        if (isNullOrEmpty(serviceRequest.getVehicleMonitoringRequests())) {
            result.getVehicleMonitoringRequests().addAll(convertVmServiceRequests(serviceRequest.getVehicleMonitoringRequests()));
        }
        if (isNullOrEmpty(serviceRequest.getEstimatedTimetableRequests())) {
            result.getEstimatedTimetableRequests().addAll(convertEtServiceRequests(serviceRequest.getEstimatedTimetableRequests()));
        }
        if (isNullOrEmpty(serviceRequest.getSituationExchangeRequests())) {
            result.getSituationExchangeRequests().addAll(convertSxServiceRequests(serviceRequest.getSituationExchangeRequests()));
        }
        return result;
    }

    static List<VehicleMonitoringRequestStructure> convertVmServiceRequests(List<VehicleMonitoringRequestRecord> requests) {
        if (isNullOrEmpty(requests)) {
            return Collections.emptyList();
        }
        List<VehicleMonitoringRequestStructure> result = new ArrayList<>();
        for (VehicleMonitoringRequestRecord request : requests) {
            result.add(convert(request));
        }
        return result;
    }
    static List<EstimatedTimetableRequestStructure> convertEtServiceRequests(List<EstimatedTimetableRequestRecord> requests) {
        if (isNullOrEmpty(requests)) {
            return Collections.emptyList();
        }
        List<EstimatedTimetableRequestStructure> result = new ArrayList<>();
        for (EstimatedTimetableRequestRecord request : requests) {
            result.add(convert(request));
        }
        return result;
    }
    static List<SituationExchangeRequestStructure> convertSxServiceRequests(List<SituationExchangeRequestRecord> requests) {
        if (isNullOrEmpty(requests)) {
            return Collections.emptyList();
        }
        List<SituationExchangeRequestStructure> result = new ArrayList<>();
        for (SituationExchangeRequestRecord request : requests) {
            result.add(convert(request));
        }
        return result;
    }
}
