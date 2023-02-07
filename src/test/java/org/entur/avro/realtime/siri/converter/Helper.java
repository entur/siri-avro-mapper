package org.entur.avro.realtime.siri.converter;

import uk.org.siri.siri21.EstimatedTimetableDeliveryStructure;
import uk.org.siri.siri21.EstimatedVersionFrameStructure;
import uk.org.siri.siri21.Siri;
import uk.org.siri.siri21.SituationExchangeDeliveryStructure;
import uk.org.siri.siri21.VehicleMonitoringDeliveryStructure;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.entur.avro.realtime.siri.converter.CommonConverter.isNullOrEmpty;

public class Helper {

    static {
        CommonConverter.forceTimeZone = ZoneId.of("Europe/Oslo");
    }

    public static String init(String path) throws IOException {

        String xml = Helper.readFile(path);

        //Removing indentation and newlines to match unformatted xml
        xml = xml.replace("\n", "");
        while (xml.indexOf("  ") > 0) {
            xml = xml.replace("  ", "");
        }
        return xml;
    }

    static String readFile(String path) throws IOException {

        RandomAccessFile raf = new RandomAccessFile(path, "rw");
        byte[] contents = new byte[(int) raf.length()];
        raf.readFully(contents);
        return new String(contents);
    }
    protected static int printStats(Siri s) {
        int numberOfETMessages = 0;
        int numberOfVMMessages = 0;
        int numberOfSXMessages = 0;
        if (s.getServiceDelivery() != null) {
            if (!isNullOrEmpty(s.getServiceDelivery().getEstimatedTimetableDeliveries())) {
                for (EstimatedTimetableDeliveryStructure delivery : s.getServiceDelivery().getEstimatedTimetableDeliveries()) {
                    for (EstimatedVersionFrameStructure estimatedJourneyVersionFrame : delivery.getEstimatedJourneyVersionFrames()) {
                        numberOfETMessages = estimatedJourneyVersionFrame.getEstimatedVehicleJourneies().size();
                    }
                }
            } else if (!isNullOrEmpty(s.getServiceDelivery().getVehicleMonitoringDeliveries())) {
                for (VehicleMonitoringDeliveryStructure delivery : s.getServiceDelivery().getVehicleMonitoringDeliveries()) {
                    numberOfVMMessages = delivery.getVehicleActivities().size();
                }
            } else if (!isNullOrEmpty(s.getServiceDelivery().getSituationExchangeDeliveries())) {
                for (SituationExchangeDeliveryStructure delivery : s.getServiceDelivery().getSituationExchangeDeliveries()) {
                    numberOfSXMessages = delivery.getSituations().getPtSituationElements().size();
                }
            }
        }
        if (numberOfETMessages > 0) {
            System.out.println("Number of ET-messages: " + numberOfETMessages);
            return numberOfETMessages;
        } else if (numberOfVMMessages > 0) {
            System.out.println("Number of VM-messages: " + numberOfVMMessages);
            return numberOfVMMessages;
        } else if (numberOfSXMessages > 0) {
            System.out.println("Number of SX-messages: " + numberOfSXMessages);
            return numberOfSXMessages;
        }
        return -1;
    }
}
