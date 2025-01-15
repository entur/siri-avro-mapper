package org.entur.avro.realtime.siri.converter;

import jakarta.xml.bind.JAXBException;
import org.entur.avro.realtime.siri.model.SiriRecord;
import org.entur.siri21.util.SiriXml;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import uk.org.siri.siri21.Siri;

import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import static org.entur.avro.realtime.siri.converter.Converter.avro2Jaxb;
import static org.entur.avro.realtime.siri.converter.Converter.jaxb2Avro;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSiriETConverter extends Helper {

    private static String xml;

    @BeforeAll
    public static void init() throws IOException {
        xml = init("src/test/resources/et.xml");
    }

    @Test
    public void testConvert() throws XMLStreamException, JAXBException {
        Siri s = SiriXml.parseXml(xml);

        printStats(s);

        SiriRecord siriRecord = jaxb2Avro(s);
        Siri siri = avro2Jaxb(siriRecord);

        assertEquals(SiriXml.toXml(s), SiriXml.toXml(siri));
    }

    @Test
    public void testNativeAvroSerialization() throws XMLStreamException, JAXBException, IOException {
        Siri s = SiriXml.parseXml(xml);
        SiriRecord siriRecord = jaxb2Avro(s);

        byte[] endcodedData = SiriRecord.getEncoder().encode(siriRecord).array();

        SiriRecord decodedObject = SiriRecord.getDecoder().decode(endcodedData);

        assertEquals(siriRecord, decodedObject);
    }

    @Test
    @Disabled
    public void testPerformance() throws XMLStreamException, JAXBException {
        Siri s = SiriXml.parseXml(xml);

        printStats(s);

        int numberOfConversionRounds = 10; // Conversions-time seems to stabilize after 4-5 iterations
        for (int i = 0; i < numberOfConversionRounds; i++) {

            long t1 = System.currentTimeMillis();
            SiriRecord siriRecord = jaxb2Avro(s);
            long t2 = System.currentTimeMillis();
            Siri siri = avro2Jaxb(siriRecord);
            long t3 = System.currentTimeMillis();
            System.out.println( "" + i + ":\t jaxb2avro:" + (t2-t1) + " ms, avro2jaxb: " + (t3-t2));

        }


//        assertEquals(xml, SiriXml.toXml(s));
    }
}
