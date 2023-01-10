package org.entur.avro.realtime.siri.converter;

import org.entur.avro.realtime.siri.model.SiriRecord;
import org.entur.siri21.util.SiriXml;
import org.junit.BeforeClass;
import org.junit.Ignore;
import org.junit.Test;
import uk.org.siri.siri21.Siri;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import static org.entur.avro.realtime.siri.converter.Converter.avro2Jaxb;
import static org.entur.avro.realtime.siri.converter.Converter.jaxb2Avro;
import static org.junit.Assert.assertEquals;

public class TestSiriETConverter extends Helper{

    private static String xml;

    @BeforeClass
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
    @Ignore
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
