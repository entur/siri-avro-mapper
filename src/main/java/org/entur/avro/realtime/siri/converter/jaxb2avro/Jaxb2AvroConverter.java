package org.entur.avro.realtime.siri.converter.jaxb2avro;

import org.entur.avro.realtime.siri.converter.CommonConverter;
import org.entur.avro.realtime.siri.model.EstimatedVehicleJourneyRecord;
import org.entur.avro.realtime.siri.model.PtSituationElementRecord;
import org.entur.avro.realtime.siri.model.SiriRecord;
import org.entur.avro.realtime.siri.model.VehicleActivityRecord;
import uk.org.siri.siri21.EstimatedVehicleJourney;
import uk.org.siri.siri21.PtSituationElement;
import uk.org.siri.siri21.Siri;
import uk.org.siri.siri21.VehicleActivityStructure;

public class Jaxb2AvroConverter extends CommonConverter {

    public static SiriRecord convert(Siri siri) {
        return SiriRecord.newBuilder()
                .setVersion(siri.getVersion())
                .setServiceDelivery(ServiceConverter.convert(siri.getServiceDelivery()))
                .setServiceRequest(ServiceConverter.convert(siri.getServiceRequest()))
                .setHeartbeatNotification(SubscriptionConverter.convert(siri.getHeartbeatNotification()))
                .setSubscriptionRequest(SubscriptionConverter.convert(siri.getSubscriptionRequest()))
                .setSubscriptionResponse(SubscriptionConverter.convert(siri.getSubscriptionResponse()))
                .setTerminateSubscriptionRequest(SubscriptionConverter.convert(siri.getTerminateSubscriptionRequest()))
                .setTerminateSubscriptionResponse(SubscriptionConverter.convert(siri.getTerminateSubscriptionResponse()))
                .build();
    }

    public static EstimatedVehicleJourneyRecord convert(EstimatedVehicleJourney et) {
        return EstimatedTimetableDeliveryConverter.convert(et);
    }
    public static VehicleActivityRecord convert(VehicleActivityStructure vm) {
        return VehicleMonitoringDeliveryConverter.convert(vm);
    }
    public static PtSituationElementRecord convert(PtSituationElement sx) {
        return SituationExchangeDeliveryConverter.convert(sx);
    }

}
