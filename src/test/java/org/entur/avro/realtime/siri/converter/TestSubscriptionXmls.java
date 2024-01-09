package org.entur.avro.realtime.siri.converter;

import org.entur.avro.realtime.siri.model.SiriRecord;
import org.entur.siri21.util.SiriXml;
import org.junit.jupiter.api.Test;
import uk.org.siri.siri21.Siri;

import jakarta.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.File;
import java.io.IOException;

import static org.entur.avro.realtime.siri.converter.Converter.avro2Jaxb;
import static org.entur.avro.realtime.siri.converter.Converter.jaxb2Avro;
import static org.entur.avro.realtime.siri.converter.Helper.readFile;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSubscriptionXmls {

    String rootDir = "src/test/resources/subscription";

    @Test
    public void testSubscriptionConversion() throws IOException, XMLStreamException, JAXBException {
        File dir = new File(rootDir);

        File[] testFiles = dir.listFiles();


        for (File testFile : testFiles) {
            Siri s = SiriXml.parseXml(readFile(testFile.getAbsolutePath()));

            SiriRecord siriRecord = jaxb2Avro(s);
            Siri siri = avro2Jaxb(siriRecord);

            assertEquals(SiriXml.toXml(s), SiriXml.toXml(siri), testFile.getName() + " failed");

            byte[] bytes = SiriRecord.getEncoder().encode(siriRecord).array();

            SiriRecord decoded = SiriRecord.getDecoder().decode(bytes);
            assertEquals(siriRecord, decoded);
        }
    }
}
