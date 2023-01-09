package org.entur.avro.realtime.siri.converter;

import org.entur.avro.realtime.siri.converter.avro2jaxb.Avro2JaxbConverter;
import org.entur.avro.realtime.siri.converter.jaxb2avro.Jaxb2AvroConverter;
import org.entur.avro.realtime.siri.model.SiriRecord;
import uk.org.siri.siri21.Siri;

public class Converter {

    public static SiriRecord jaxb2Avro(Siri siri) {
        return Jaxb2AvroConverter.convert(siri);
    }
    public static Siri avro2Jaxb(SiriRecord siriRecord) {
        return Avro2JaxbConverter.convert(siriRecord);
    }

}
