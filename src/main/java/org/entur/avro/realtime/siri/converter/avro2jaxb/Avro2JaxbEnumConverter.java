package org.entur.avro.realtime.siri.converter.avro2jaxb;

import org.entur.avro.realtime.siri.converter.CommonConverter;
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

    private static final CharSequence UNKNOWN = "UNKNOWN";

    static AdviceTypeEnumeration convertAdviceType(CharSequence adviceType) {
        if (adviceType == null) {
            return null;
        }
        return AdviceTypeEnumeration.valueOf(adviceType.toString());
    }

    static OccupancyEnumeration convertOccupancy(CharSequence occupancy) {
        if (occupancy == null) {
            return null;
        }
        return OccupancyEnumeration.valueOf(occupancy.toString());
    }

    static VehicleStatusEnumeration convertVehicleStatus(CharSequence vehicleStatus) {
        if (vehicleStatus == null) {
            return null;
        }
        return VehicleStatusEnumeration.valueOf(vehicleStatus.toString());
    }

    static List<RoutePointTypeEnumeration> convertStopConditions(List<CharSequence> stopConditions) {
        if (stopConditions == null) {
            return Collections.emptyList();
        }
        List<RoutePointTypeEnumeration> records = new ArrayList<>();
        for (CharSequence stopCondition : stopConditions) {
            records.add(convertStopCondition(stopCondition));
        }
        return records;
    }

    static RoutePointTypeEnumeration convertStopCondition(CharSequence stopCondition) {
        if (stopCondition == null) {
            return null;
        }
        return RoutePointTypeEnumeration.valueOf(stopCondition.toString());
    }

    static SituationSourceTypeEnumeration convertSourceType(CharSequence sourceType) {
        if (sourceType == null) {
            return null;
        }
        return SituationSourceTypeEnumeration.valueOf(sourceType.toString());
    }

    static SeverityEnumeration convertSeverity(CharSequence severity) {
        if (severity == null) {
            return null;
        }
        return SeverityEnumeration.valueOf(severity.toString());
    }


    static WaterSubmodesOfTransportEnumeration convertWaterSubmode(CharSequence waterSubmode) {
        if (waterSubmode.equals(UNKNOWN)) {
            return WaterSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return WaterSubmodesOfTransportEnumeration.valueOf(waterSubmode.toString());
    }

    static TramSubmodesOfTransportEnumeration convertTramSubmode(CharSequence tramSubmode) {
        if (tramSubmode.equals(UNKNOWN)) {
            return TramSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return TramSubmodesOfTransportEnumeration.valueOf(tramSubmode.toString());
    }

    static RailSubmodesOfTransportEnumeration convertRailSubmode(CharSequence railSubmode) {
        if (railSubmode.equals(UNKNOWN)) {
            return RailSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return RailSubmodesOfTransportEnumeration.valueOf(railSubmode.toString());
    }

    static MetroSubmodesOfTransportEnumeration convertMetroSubmode(CharSequence metroSubmode) {
        if (metroSubmode.equals(UNKNOWN)) {
            return MetroSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return MetroSubmodesOfTransportEnumeration.valueOf(metroSubmode.toString());
    }

    static CoachSubmodesOfTransportEnumeration convertCoachSubmode(CharSequence coachSubmode) {
        if (coachSubmode.equals(UNKNOWN)) {
            return CoachSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return CoachSubmodesOfTransportEnumeration.valueOf(coachSubmode.toString());
    }

    static BusSubmodesOfTransportEnumeration convertBusSubmode(CharSequence busSubmode) {
        if (busSubmode.equals(UNKNOWN)) {
            return BusSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return BusSubmodesOfTransportEnumeration.valueOf(busSubmode.toString());
    }

    static AirSubmodesOfTransportEnumeration convertAirSubmode(CharSequence airSubmode) {
        if (airSubmode.equals(UNKNOWN)) {
            return AirSubmodesOfTransportEnumeration.UNKNOWN;
        }
        return AirSubmodesOfTransportEnumeration.valueOf(airSubmode.toString());
    }

    static VehicleModesOfTransportEnumeration convertVehicleMode(CharSequence vehicleMode) {
        if (vehicleMode == null) {
            return null;
        }
        return VehicleModesOfTransportEnumeration.valueOf(vehicleMode.toString());
    }

    static WorkflowStatusEnumeration convertProgress(CharSequence progress) {
        if (progress == null) {
            return null;
        }
        return WorkflowStatusEnumeration.valueOf(progress.toString());
    }
}
