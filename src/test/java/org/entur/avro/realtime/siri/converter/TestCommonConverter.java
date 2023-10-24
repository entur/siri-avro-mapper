package org.entur.avro.realtime.siri.converter;

import org.apache.avro.util.Utf8;
import org.junit.jupiter.api.Test;
import uk.org.siri.siri21.LineRef;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class TestCommonConverter {
    @Test
    public void testSetValue() {
        String s = "TST:Line:1";
        LineRef lineRef = CommonConverter.setValue(LineRef.class, s);

        assertNotNull(lineRef);
        assertNotNull(lineRef.getValue());
        assertEquals(s, lineRef.getValue());

        Utf8 utf8Str = new Utf8("TST:Line:1");
        LineRef lineRef2 = CommonConverter.setValue(LineRef.class, utf8Str);
        assertNotNull(lineRef2);
        assertNotNull(lineRef2.getValue());
        assertEquals(utf8Str.toString(), lineRef2.getValue());

        StringBuffer bufferStr = new StringBuffer("TST:Line:1");
        LineRef lineRef3 = CommonConverter.setValue(LineRef.class, bufferStr);
        assertNotNull(lineRef3);
        assertNotNull(lineRef3.getValue());
        assertEquals(bufferStr.toString(), lineRef3.getValue());

    }

}
