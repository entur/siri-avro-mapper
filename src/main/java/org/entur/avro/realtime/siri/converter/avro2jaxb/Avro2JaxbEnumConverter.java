package org.entur.avro.realtime.siri.converter.avro2jaxb;

import org.entur.avro.realtime.siri.converter.CommonConverter;
import org.entur.avro.realtime.siri.model.AdviceTypeEnum;
import org.entur.avro.realtime.siri.model.AirSubmodesEnum;
import org.entur.avro.realtime.siri.model.BusSubmodesEnum;
import org.entur.avro.realtime.siri.model.CoachSubmodesEnum;
import org.entur.avro.realtime.siri.model.MetroSubmodesEnum;
import org.entur.avro.realtime.siri.model.OccupancyEnum;
import org.entur.avro.realtime.siri.model.RailSubmodesEnum;
import org.entur.avro.realtime.siri.model.RoutePointTypeEnum;
import org.entur.avro.realtime.siri.model.SeverityEnum;
import org.entur.avro.realtime.siri.model.SourceTypeEnum;
import org.entur.avro.realtime.siri.model.TramSubmodesEnum;
import org.entur.avro.realtime.siri.model.VehicleModeEnum;
import org.entur.avro.realtime.siri.model.VehicleStatusEnum;
import org.entur.avro.realtime.siri.model.WaterSubmodesEnum;
import org.entur.avro.realtime.siri.model.WorkflowStatusEnum;
import uk.org.siri.siri21.AdviceTypeEnumeration;
import uk.org.siri.siri21.AirSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.BusSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.CoachSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.MetroSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.OccupancyEnumeration;
import uk.org.siri.siri21.RailSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.RoutePointTypeEnumeration;
import uk.org.siri.siri21.SeverityEnumeration;
import uk.org.siri.siri21.SituationSourceTypeEnumeration;
import uk.org.siri.siri21.TramSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.VehicleModesOfTransportEnumeration;
import uk.org.siri.siri21.VehicleStatusEnumeration;
import uk.org.siri.siri21.WaterSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.WorkflowStatusEnumeration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Avro2JaxbEnumConverter extends CommonConverter {

    static AdviceTypeEnumeration convert(AdviceTypeEnum adviceType) {
        if (adviceType == null) {
            return null;
        }
        return AdviceTypeEnumeration.valueOf(adviceType.name());
    }

    static OccupancyEnumeration convert(OccupancyEnum occupancy) {
        if (occupancy == null) {
            return null;
        }
        return OccupancyEnumeration.valueOf(occupancy.name());
    }

    static VehicleStatusEnumeration convert(VehicleStatusEnum vehicleStatus) {
        if (vehicleStatus == null) {
            return null;
        }
        return VehicleStatusEnumeration.valueOf(vehicleStatus.name());
    }

    static List<RoutePointTypeEnumeration> convertStopConditions(List<RoutePointTypeEnum> stopConditions) {
        if (stopConditions == null) {
            return Collections.emptyList();
        }
        List<RoutePointTypeEnumeration> records = new ArrayList<>();
        for (RoutePointTypeEnum stopCondition : stopConditions) {
            records.add(convert(stopCondition));
        }
        return records;
    }

    static RoutePointTypeEnumeration convert(RoutePointTypeEnum stopCondition) {
        if (stopCondition == null) {
            return null;
        }
        return RoutePointTypeEnumeration.valueOf(stopCondition.name());
    }

    static SituationSourceTypeEnumeration convert(SourceTypeEnum sourceType) {
        if (sourceType == null) {
            return null;
        }
        return SituationSourceTypeEnumeration.valueOf(sourceType.name());
    }

    static SeverityEnumeration convert(SeverityEnum severity) {
        if (severity == null) {
            return null;
        }
        return SeverityEnumeration.valueOf(severity.name());
    }


    static WaterSubmodesOfTransportEnumeration convert(WaterSubmodesEnum waterSubmode) {
        if (waterSubmode == WaterSubmodesEnum.UNKNOWN) {
            return WaterSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return WaterSubmodesOfTransportEnumeration.fromValue(waterSubmode.name());
    }

    static TramSubmodesOfTransportEnumeration convert(TramSubmodesEnum tramSubmode) {
        if (tramSubmode == TramSubmodesEnum.UNKNOWN) {
            return TramSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return TramSubmodesOfTransportEnumeration.fromValue(tramSubmode.name());
    }

    static RailSubmodesOfTransportEnumeration convert(RailSubmodesEnum railSubmode) {
        if (railSubmode == RailSubmodesEnum.UNKNOWN) {
            return RailSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return RailSubmodesOfTransportEnumeration.fromValue(railSubmode.name());
    }

    static MetroSubmodesOfTransportEnumeration convert(MetroSubmodesEnum metroSubmode) {
        if (metroSubmode == MetroSubmodesEnum.UNKNOWN) {
            return MetroSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return MetroSubmodesOfTransportEnumeration.valueOf(metroSubmode.name());
    }

    static CoachSubmodesOfTransportEnumeration convert(CoachSubmodesEnum coachSubmode) {
        if (coachSubmode == CoachSubmodesEnum.UNKNOWN) {
            return CoachSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return CoachSubmodesOfTransportEnumeration.valueOf(coachSubmode.name());
    }

    static BusSubmodesOfTransportEnumeration convert(BusSubmodesEnum busSubmode) {
        if (busSubmode == BusSubmodesEnum.UNKNOWN) {
            return BusSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return BusSubmodesOfTransportEnumeration.valueOf(busSubmode.name());
    }

    static AirSubmodesOfTransportEnumeration convert(AirSubmodesEnum airSubmode) {
        if (airSubmode == AirSubmodesEnum.UNKNOWN) {
            return AirSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return AirSubmodesOfTransportEnumeration.valueOf(airSubmode.name());
    }

    static VehicleModesOfTransportEnumeration convert(VehicleModeEnum vehicleMode) {
        if (vehicleMode == null) {
            return null;
        }
        return VehicleModesOfTransportEnumeration.fromValue(vehicleMode.name());
    }

    static WorkflowStatusEnumeration convert(WorkflowStatusEnum progress) {
        if (progress == null) {
            return null;
        }
        return WorkflowStatusEnumeration.valueOf(progress.name());
    }
}
