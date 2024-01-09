package org.entur.avro.realtime.siri.converter;

import org.entur.avro.realtime.siri.model.SiriRecord;
import org.entur.siri21.util.SiriXml;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import uk.org.siri.siri21.Siri;

import jakarta.xml.bind.JAXBException;
import javax.xml.stream.XMLStreamException;
import java.io.IOException;

import static org.entur.avro.realtime.siri.converter.Converter.avro2Jaxb;
import static org.entur.avro.realtime.siri.converter.Converter.jaxb2Avro;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestSiriVMConverter extends Helper{

    private static String xml;

    @BeforeAll
    public static void init() throws IOException {
        xml = init("src/test/resources/vm.xml");
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
    public void testNativeAvroSerializtion() throws XMLStreamException, JAXBException, IOException {
        Siri s = SiriXml.parseXml(xml);
        SiriRecord siriRecord = jaxb2Avro(s);

        byte[] endcodedData = SiriRecord.getEncoder().encode(siriRecord).array();

        SiriRecord decodedObject = SiriRecord.getDecoder().decode(endcodedData);

        assertEquals(siriRecord, decodedObject);
    }
}
