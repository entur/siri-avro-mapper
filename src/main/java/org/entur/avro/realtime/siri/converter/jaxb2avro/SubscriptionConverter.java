package org.entur.avro.realtime.siri.converter.jaxb2avro;

import org.entur.avro.realtime.siri.converter.CommonConverter;
import org.entur.avro.realtime.siri.model.EstimatedTimetableRequestRecord;
import org.entur.avro.realtime.siri.model.EstimatedTimetableSubscriptionRecord;
import org.entur.avro.realtime.siri.model.HeartbeatNotificationRecord;
import org.entur.avro.realtime.siri.model.ResponseStatusRecord;
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
import uk.org.siri.siri21.ResponseStatus;
import uk.org.siri.siri21.ServiceDeliveryErrorConditionElement;
import uk.org.siri.siri21.SituationExchangeRequestStructure;
import uk.org.siri.siri21.SituationExchangeSubscriptionStructure;
import uk.org.siri.siri21.SubscriptionContextStructure;
import uk.org.siri.siri21.SubscriptionRequest;
import uk.org.siri.siri21.SubscriptionResponseStructure;
import uk.org.siri.siri21.TerminateSubscriptionRequestStructure;
import uk.org.siri.siri21.TerminateSubscriptionResponseStructure;
import uk.org.siri.siri21.TerminationResponseStatusStructure;
import uk.org.siri.siri21.VehicleMonitoringRequestStructure;
import uk.org.siri.siri21.VehicleMonitoringSubscriptionStructure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class SubscriptionConverter extends CommonConverter{
    static HeartbeatNotificationRecord convert(HeartbeatNotificationStructure heartbeatNotification) {
        if (heartbeatNotification == null) {
            return null;
        }
        return HeartbeatNotificationRecord.newBuilder()
                .setProducerRef(CommonConverter.getValue(heartbeatNotification.getProducerRef()))
                .setRequestTimestamp(CommonConverter.convert(heartbeatNotification.getRequestTimestamp()))
                .setServiceStartedTime(CommonConverter.convert(heartbeatNotification.getServiceStartedTime()))
                .setStatus(heartbeatNotification.isStatus())
                .build();
    }

    public static SubscriptionRequestRecord convert(SubscriptionRequest subscriptionRequest) {
        if (subscriptionRequest == null) {
            return null;
        }
        return SubscriptionRequestRecord.newBuilder()
                .setRequestTimestamp(convert(subscriptionRequest.getRequestTimestamp()))
                .setAddress(subscriptionRequest.getAddress())
                .setConsumerAddress(subscriptionRequest.getConsumerAddress())
                .setMessageIdentifier(getValue(subscriptionRequest.getMessageIdentifier()))
                .setSubscriptionContext(convert(subscriptionRequest.getSubscriptionContext()))
                .setRequestorRef(getValue(subscriptionRequest.getRequestorRef()))
                .setEstimatedTimetableSubscriptionRequests(
                        convertEtSubRequests(subscriptionRequest.getEstimatedTimetableSubscriptionRequests())
                )
                .setVehicleMonitoringSubscriptionRequests(
                        convertVmSubRequests(subscriptionRequest.getVehicleMonitoringSubscriptionRequests())
                )
                .setSituationExchangeSubscriptionRequests(
                        convertSxSubRequests(subscriptionRequest.getSituationExchangeSubscriptionRequests())
                )
                .build();
    }

    private static List<VehicleMonitoringSubscriptionRecord> convertVmSubRequests(List<VehicleMonitoringSubscriptionStructure> subscriptionRequests) {
        if (isNullOrEmpty(subscriptionRequests)) {
            return null;
        }
        List<VehicleMonitoringSubscriptionRecord> records = new ArrayList<>();
        for (VehicleMonitoringSubscriptionStructure request : subscriptionRequests) {
            records.add(convert(request));
        }
        return records;
    }

    private static VehicleMonitoringSubscriptionRecord convert(VehicleMonitoringSubscriptionStructure request) {
        if (request == null) {
            return null;
        }
        return VehicleMonitoringSubscriptionRecord.newBuilder()
                .setSubscriberRef(getValue(request.getSubscriberRef()))
                .setSubscriptionIdentifier(getValue(request.getSubscriptionIdentifier()))
                .setIncrementalUpdates(request.isIncrementalUpdates())
                .setInitialTerminationTime(convert(request.getInitialTerminationTime()))
                .setChangeBeforeUpdates(convert(request.getChangeBeforeUpdates()))
                .setVehicleMonitoringRequest(convert(request.getVehicleMonitoringRequest()))
                .setUpdateInterval(convert(request.getUpdateInterval()))
                .build();
    }

    private static VehicleMonitoringRequestRecord convert(VehicleMonitoringRequestStructure request) {
        if (request == null) {
            return null;
        }
        return VehicleMonitoringRequestRecord.newBuilder()
                .setVersion(request.getVersion())
                .setMessageIdentifier(getValue(request.getMessageIdentifier()))
                .setRequestTimestamp(convert(request.getRequestTimestamp()))
                .build();
    }

    private static List<SituationExchangeSubscriptionRecord> convertSxSubRequests(List<SituationExchangeSubscriptionStructure> subscriptionRequests) {
        if (isNullOrEmpty(subscriptionRequests)) {
            return null;
        }
        List<SituationExchangeSubscriptionRecord> records = new ArrayList<>();
        for (SituationExchangeSubscriptionStructure request : subscriptionRequests) {
            records.add(convert(request));
        }
        return records;
    }

    private static SituationExchangeSubscriptionRecord convert(SituationExchangeSubscriptionStructure request) {
        if (request == null) {
            return null;
        }
        return SituationExchangeSubscriptionRecord.newBuilder()
                .setSubscriberRef(getValue(request.getSubscriberRef()))
                .setSubscriptionIdentifier(getValue(request.getSubscriptionIdentifier()))
                .setIncrementalUpdates(request.isIncrementalUpdates())
                .setInitialTerminationTime(convert(request.getInitialTerminationTime()))
                .setSituationExchangeRequest(convert(request.getSituationExchangeRequest()))
                .build();
    }

    private static SituationExchangeRequestRecord convert(SituationExchangeRequestStructure request) {
        if (request == null) {
            return null;
        }
        return SituationExchangeRequestRecord.newBuilder()
                .setVersion(request.getVersion())
                .setMessageIdentifier(getValue(request.getMessageIdentifier()))
                .setRequestTimestamp(convert(request.getRequestTimestamp()))
                .setPreviewInterval(convert(request.getPreviewInterval()))
                .build();
    }

    private static List<EstimatedTimetableSubscriptionRecord> convertEtSubRequests(List<EstimatedTimetableSubscriptionStructure> subscriptionRequests) {
        if (isNullOrEmpty(subscriptionRequests)) {
            return null;
        }
        List<EstimatedTimetableSubscriptionRecord> records = new ArrayList<>();
        for (EstimatedTimetableSubscriptionStructure request : subscriptionRequests) {
            records.add(convert(request));
        }
        return records;
    }

    private static EstimatedTimetableSubscriptionRecord convert(EstimatedTimetableSubscriptionStructure request) {
        if (request == null) {
            return null;
        }
        return EstimatedTimetableSubscriptionRecord.newBuilder()
                .setSubscriberRef(getValue(request.getSubscriberRef()))
                .setSubscriptionIdentifier(getValue(request.getSubscriptionIdentifier()))
                .setIncrementalUpdates(request.isIncrementalUpdates())
                .setChangeBeforeUpdates(convert(request.getChangeBeforeUpdates()))
                .setInitialTerminationTime(convert(request.getInitialTerminationTime()))
                .setEstimatedTimetableRequest(convert(request.getEstimatedTimetableRequest()))
                .build();
    }

    private static EstimatedTimetableRequestRecord convert(EstimatedTimetableRequestStructure request) {
        if (request == null) {
            return null;
        }
        return EstimatedTimetableRequestRecord.newBuilder()
                .setVersion(request.getVersion())
                .setRequestTimestamp(convert(request.getRequestTimestamp()))
                .setPreviewInterval(convert(request.getPreviewInterval()))
                .setMessageIdentifier(getValue(request.getMessageIdentifier()))
                .build();
    }


    private static SubscriptionContextRecord convert(SubscriptionContextStructure subscriptionContext) {
        if (subscriptionContext == null) {
            return null;
        }
        return SubscriptionContextRecord.newBuilder()
                .setHeartbeatInterval(convert(subscriptionContext.getHeartbeatInterval()))
                .build();
    }

    public static SubscriptionResponseRecord convert(SubscriptionResponseStructure response) {
        if (response == null) {
            return null;
        }
        return SubscriptionResponseRecord.newBuilder()
                .setRequestMessageRef(getValue(response.getRequestMessageRef()))
                .setResponderRef(getValue(response.getResponderRef()))
                .setResponseTimestamp(convert(response.getResponseTimestamp()))
                .setResponseStatuses(convertResponseStatuses(response.getResponseStatuses()))
                .build();
    }

    private static List<ResponseStatusRecord> convertResponseStatuses(List<ResponseStatus> statuses) {
        if (isNullOrEmpty(statuses)) {
            return Collections.emptyList();
        }
        List<ResponseStatusRecord> records = new ArrayList<>();
        for (ResponseStatus status : statuses) {
            records.add(convert(status));
        }
        return records;
    }

    private static ResponseStatusRecord convert(ResponseStatus status) {
        return ResponseStatusRecord.newBuilder()
                .setResponseTimestamp(convert(status.getResponseTimestamp()))
                .setRequestMessageRef(getValue(status.getRequestMessageRef()))
                .setStatus(status.isStatus())
                .setErrorText(convert(status.getErrorCondition()))
                .build();
    }

    private static CharSequence convert(ServiceDeliveryErrorConditionElement errorCondition) {
        if (errorCondition == null || errorCondition.getOtherError() == null) {
            return null;
        }

        return errorCondition.getOtherError().getErrorText();
    }

    public static TerminateSubscriptionRequestRecord convert(TerminateSubscriptionRequestStructure request) {
        if (request == null) {
            return null;
        }
        return TerminateSubscriptionRequestRecord.newBuilder()
                .setMessageIdentifier(getValue(request.getMessageIdentifier()))
                .setRequestorRef(getValue(request.getRequestorRef()))
                .setRequestTimestamp(convert(request.getRequestTimestamp()))
                .setSubscriptionRefs(getValues(request.getSubscriptionReves()))
                .build();
    }

    public static TerminateSubscriptionResponseRecord convert(TerminateSubscriptionResponseStructure request) {
        if (request == null) {
            return null;
        }
        return TerminateSubscriptionResponseRecord.newBuilder()
                .setResponseTimestamp(convert(request.getResponseTimestamp()))
                .setStatuses(convertStatuses(request.getTerminationResponseStatuses()))
                .build();
    }

    private static List<TerminationResponseStatusRecord> convertStatuses(List<TerminationResponseStatusStructure> statuses) {
        if (isNullOrEmpty(statuses)) {
            return Collections.emptyList();
        }
        List<TerminationResponseStatusRecord> records = new ArrayList<>();
        for (TerminationResponseStatusStructure status : statuses) {
            records.add(convert(status));
        }
        return records;
    }

    private static TerminationResponseStatusRecord convert(TerminationResponseStatusStructure status) {
        return TerminationResponseStatusRecord.newBuilder()
                .setResponseTimestamp(convert(status.getResponseTimestamp()))
                .setStatus(status.isStatus())
                .setSubscriberRef(getValue(status.getSubscriberRef()))
                .setRequestMessageRef(getValue(status.getRequestMessageRef()))
                .setErrorText(convert(status.getErrorCondition()))
                .build();
    }

    private static String convert(TerminationResponseStatusStructure.ErrorCondition errorCondition) {
        if (errorCondition != null && errorCondition.getOtherError() != null) {
            if (errorCondition.getOtherError() != null) {
                return errorCondition.getOtherError().getErrorText();
            }
        }
        return null;
    }

    static List<VehicleMonitoringRequestRecord> convertVmServiceRequests(List<VehicleMonitoringRequestStructure> requests) {
        if (isNullOrEmpty(requests)) {
            return null;
        }
        List<VehicleMonitoringRequestRecord> result = new ArrayList<>();
        for (VehicleMonitoringRequestStructure request : requests) {
            result.add(convert(request));
        }
        return result;
    }
    static List<EstimatedTimetableRequestRecord> convertEtServiceRequests(List<EstimatedTimetableRequestStructure> requests) {
        if (isNullOrEmpty(requests)) {
            return null;
        }
        List<EstimatedTimetableRequestRecord> result = new ArrayList<>();
        for (EstimatedTimetableRequestStructure request : requests) {
            result.add(convert(request));
        }
        return result;
    }

    static List<SituationExchangeRequestRecord> convertSxServiceRequests(List<SituationExchangeRequestStructure> requests) {
        if (isNullOrEmpty(requests)) {
            return null;
        }
        List<SituationExchangeRequestRecord> result = new ArrayList<>();
        for (SituationExchangeRequestStructure request : requests) {
            result.add(convert(request));
        }
        return result;
    }
}
