package org.entur.avro.realtime.siri.converter;

import org.entur.avro.realtime.siri.model.SiriRecord;
import org.entur.siri21.util.SiriXml;
import org.junit.BeforeClass;
import org.junit.Test;
import uk.org.siri.siri21.Siri;

import javax.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import static org.entur.avro.realtime.siri.converter.Converter.avro2Jaxb;
import static org.entur.avro.realtime.siri.converter.Converter.jaxb2Avro;
import static org.junit.Assert.assertEquals;

public class TestSiriSXConverter extends Helper {

    private static String xml;

    @BeforeClass
    public static void init() throws IOException {
        xml = init("src/test/resources/sx.xml");
    }

    @Test
    public void testConvert() throws XMLStreamException, JAXBException {
        Siri s = SiriXml.parseXml(xml);

        printStats(s);

        SiriRecord siriRecord = jaxb2Avro(s);

        Siri siri = avro2Jaxb(siriRecord);

        assertEquals(SiriXml.toXml(s), SiriXml.toXml(siri));
    }
}
