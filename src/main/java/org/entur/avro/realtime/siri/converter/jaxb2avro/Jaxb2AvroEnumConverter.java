package org.entur.avro.realtime.siri.converter.jaxb2avro;

import org.entur.avro.realtime.siri.converter.CommonConverter;
import org.entur.avro.realtime.siri.model.AirSubmodesEnum;
import org.entur.avro.realtime.siri.model.BusSubmodesEnum;
import org.entur.avro.realtime.siri.model.CoachSubmodesEnum;
import org.entur.avro.realtime.siri.model.MetroSubmodesEnum;
import org.entur.avro.realtime.siri.model.OccupancyEnum;
import org.entur.avro.realtime.siri.model.RailSubmodesEnum;
import org.entur.avro.realtime.siri.model.ReportTypeEnum;
import org.entur.avro.realtime.siri.model.RoutePointTypeEnum;
import org.entur.avro.realtime.siri.model.SeverityEnum;
import org.entur.avro.realtime.siri.model.TramSubmodesEnum;
import org.entur.avro.realtime.siri.model.VehicleModeEnum;
import org.entur.avro.realtime.siri.model.VehicleStatusEnum;
import org.entur.avro.realtime.siri.model.WaterSubmodesEnum;
import org.entur.avro.realtime.siri.model.WorkflowStatusEnum;
import uk.org.siri.siri21.AirSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.BusSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.CoachSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.MetroSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.OccupancyEnumeration;
import uk.org.siri.siri21.RailSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.ReportTypeEnumeration;
import uk.org.siri.siri21.RoutePointTypeEnumeration;
import uk.org.siri.siri21.SeverityEnumeration;
import uk.org.siri.siri21.TramSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.VehicleModesEnumeration;
import uk.org.siri.siri21.VehicleModesOfTransportEnumeration;
import uk.org.siri.siri21.VehicleStatusEnumeration;
import uk.org.siri.siri21.WaterSubmodesOfTransportEnumeration;
import uk.org.siri.siri21.WorkflowStatusEnumeration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Jaxb2AvroEnumConverter extends CommonConverter {


    static OccupancyEnum convert(OccupancyEnumeration occupancy) {
        if (occupancy == null) {
            return null;
        }
        return OccupancyEnum.valueOf(occupancy.name());
    }
    static List<VehicleModeEnum> convertVehicleModes(List<VehicleModesEnumeration> vehicleModes) {
        if (vehicleModes == null) {
            return Collections.emptyList();
        }
        List<VehicleModeEnum> modes = new ArrayList<>();
        for (VehicleModesEnumeration vehicleMode : vehicleModes) {
            modes.add(convert(vehicleMode));
        }
        return modes;
    }

    static VehicleModeEnum convert(VehicleModesEnumeration vehicleMode) {
        if (vehicleMode == null) {
            return null;
        }
        return VehicleModeEnum.valueOf(vehicleMode.name());
    }
    static VehicleModeEnum convert(VehicleModesOfTransportEnumeration vehicleMode) {
        if (vehicleMode == null) {
            return null;
        }
        return VehicleModeEnum.valueOf(vehicleMode.name());
    }

    static VehicleStatusEnum convert(VehicleStatusEnumeration vehicleStatus) {
        if (vehicleStatus == null) {
            return null;
        }
        return VehicleStatusEnum.valueOf(vehicleStatus.name());
    }

    static ReportTypeEnum convert(ReportTypeEnumeration reportType) {
        if (reportType == null) {
            return null;
        }
        return ReportTypeEnum.valueOf(reportType.name());
    }

    static SeverityEnum convert(SeverityEnumeration severity) {
        if (severity == null) {
            return null;
        }
        return SeverityEnum.valueOf(severity.name());
    }

    static WorkflowStatusEnum convert(WorkflowStatusEnumeration progress) {
        if (progress == null) {
            return null;
        }
        return WorkflowStatusEnum.valueOf(progress.name());
    }

    static WaterSubmodesEnum convert(WaterSubmodesOfTransportEnumeration submode) {
        if (submode == null) {
            return null;
        }
        return WaterSubmodesEnum.valueOf(submode.name());
    }

    static TramSubmodesEnum convert(TramSubmodesOfTransportEnumeration submode) {
        if (submode == null) {
            return null;
        }
        return TramSubmodesEnum.valueOf(submode.name());
    }

    static RailSubmodesEnum convert(RailSubmodesOfTransportEnumeration submode) {
        if (submode == null) {
            return null;
        }
        return RailSubmodesEnum.valueOf(submode.name());
    }

    static MetroSubmodesEnum convert(MetroSubmodesOfTransportEnumeration submode) {
        if (submode == null) {
            return null;
        }
        return MetroSubmodesEnum.valueOf(submode.name());
    }

    static CoachSubmodesEnum convert(CoachSubmodesOfTransportEnumeration submode) {
        if (submode == null) {
            return null;
        }
        return CoachSubmodesEnum.valueOf(submode.name());
    }
    static BusSubmodesEnum convert(BusSubmodesOfTransportEnumeration submode) {
        if (submode == null) {
            return null;
        }
        return BusSubmodesEnum.valueOf(submode.name());
    }

    static AirSubmodesEnum convert(AirSubmodesOfTransportEnumeration submode) {
        if (submode == null) {
            return null;
        }
        return AirSubmodesEnum.valueOf(submode.name());
    }

    static List<RoutePointTypeEnum> convertStopConditions(List<RoutePointTypeEnumeration> stopConditions) {
        if (stopConditions == null) {
            return Collections.emptyList();
        }
        List<RoutePointTypeEnum> records = new ArrayList<>();
        for (RoutePointTypeEnumeration stopCondition : stopConditions) {
            records.add(convert(stopCondition));
        }
        return records;
    }

    static RoutePointTypeEnum convert(RoutePointTypeEnumeration stopCondition) {
        if (stopCondition == null) {
            return null;
        }
        return RoutePointTypeEnum.valueOf(stopCondition.name());
    }


}
