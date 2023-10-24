package org.entur.avro.realtime.siri.converter;

import org.entur.avro.realtime.siri.model.FramedVehicleJourneyRefRecord;
import org.entur.avro.realtime.siri.model.TranslatedStringRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uk.org.siri.siri21.DataFrameRefStructure;
import uk.org.siri.siri21.FramedVehicleJourneyRefStructure;
import uk.org.siri.siri21.SituationVersion;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import static java.time.temporal.ChronoField.NANO_OF_SECOND;

public class CommonConverter {


    static final Logger LOG = LoggerFactory.getLogger(CommonConverter.class);

    static final DateTimeFormatter dateTimeFormatter =  new DateTimeFormatterBuilder()
            .appendPattern("yyyy-MM-dd'T'HH:mm:ss")
            .appendFraction(NANO_OF_SECOND, 0, 6, true)
            .appendZoneId()
            .toFormatter();

    private static final DatatypeFactory datatypeFactory;
    protected static ZoneId forceTimeZone;

    static {
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Uses reflection to call "getValue()" to fetch actual values from provided object
     * @param item
     * @return
     */
    protected static CharSequence getValue(Object item) {
        if (item == null) {
            return null;
        }
        try {
            return (CharSequence) item.getClass().getMethod("getValue").invoke(item);
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.warn("Unable to convert - returning null: ", e);
            return null;
        }
    }

    protected static boolean isNullOrEmpty(List items) {
        return items == null || items.isEmpty();
    }

    /**
     * Uses reflection to call "getValue()" to fetch actual values from provided object
     * @param items
     * @return
     */
    protected static List<CharSequence> getValues(List items) {
        if (items == null) {
            return Collections.emptyList();
        }

        try {
            List<CharSequence> values = new ArrayList<>();
            for (Object item : items) {
                values.add((CharSequence) item.getClass().getMethod("getValue").invoke(item));
            }
            return values;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.warn("Unable to convert - returning empty list: ", e);
            return Collections.emptyList();
        }
    }
    /**
     * Uses reflection to call "setValue()" to set actual values from provided object
     * @param clazz
     * @param value
     * @return
     */
    protected static <T> T setValue(Class<T> clazz, CharSequence value) {
        try {

            T instance = clazz.getDeclaredConstructor().newInstance();

            Method method = clazz.getMethod("setValue", String.class);

            method.invoke(instance, value.toString());

            return instance;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            LOG.warn("Unable to convert - returning null: ", e);
            return null;
        }
    }

    /**
     * Uses reflection to call "setValue()" to set actual values in object of provided type
     * @param clazz
     * @param values
     * @return
     */
    protected static <T> List<T> setValues(Class<T> clazz, List values) {
        if (values == null) {
            return Collections.emptyList();
        }
        try {
            List<T> result = new ArrayList<>();
            for (Object value : values) {
                T instance = clazz.getDeclaredConstructor().newInstance();

                Method method = clazz.getMethod("setValue", String.class);

                method.invoke(instance, value.toString());
                result.add(instance);
            }


            return result;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            LOG.warn("Unable to convert - returning empty list: ", e);
            return Collections.emptyList();
        }
    }

    /**
     * Uses reflection to call "getValue()" to fetch actual values from provided object
     * @param items
     * @return
     */
    protected static List<TranslatedStringRecord> getTranslatedValues(List items) {
        if (items == null) {
            return Collections.emptyList();
        }

        try {
            List<TranslatedStringRecord> values = new ArrayList<>();
            for (Object item : items) {
                values.add(
                    TranslatedStringRecord.newBuilder()
                            .setValue((CharSequence) item.getClass().getMethod("getValue").invoke(item))
                            .setLanguage((CharSequence) item.getClass().getMethod("getLang").invoke(item))
                            .build()
                );
            }
            return values;
        } catch (IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
            LOG.warn("Unable to convert - returning empty list: ", e);
            return Collections.emptyList();
        }
    }

    /**
     * Uses reflection to call "getValue()" to fetch actual values from provided object
     *
     * @param clazz
     * @param value
     * @return
     */
    protected static <T> List<T> setTranslatedValues(Class<T> clazz, List<TranslatedStringRecord> value) {
        try {

            List<T> result = new ArrayList<>();

            for (TranslatedStringRecord translatedStringRecord : value) {

                T instance = clazz.getDeclaredConstructor().newInstance();

                Method setValue = clazz.getMethod("setValue", String.class);
                setValue.invoke(instance, ""+translatedStringRecord.getValue());
                if (translatedStringRecord.getLanguage() != null) {
                    Method setLanguage = clazz.getMethod("setLang", String.class);
                    setLanguage.invoke(instance, "" + translatedStringRecord.getLanguage());
                }
                result.add(instance);
            }

            return result;
        } catch (IllegalAccessException | InvocationTargetException | InstantiationException | NoSuchMethodException e) {
            LOG.warn("Unable to convert - returning empty list: ", e);
            return Collections.emptyList();
        }
    }

    protected static CharSequence convert(ZonedDateTime responseTimestamp) {
        if (responseTimestamp == null) {
            return null;
        }
        return dateTimeFormatter.format(responseTimestamp.withZoneSameInstant(ZoneOffset.UTC));
    }


    protected static ZonedDateTime convertDate(CharSequence responseTimestamp) {
        if (responseTimestamp == null) {
            return null;
        }
        if (forceTimeZone != null) {
            return ZonedDateTime.parse(responseTimestamp, dateTimeFormatter).withZoneSameInstant(forceTimeZone);
        }
        return ZonedDateTime.parse(responseTimestamp, dateTimeFormatter);
    }

    protected static List<TranslatedStringRecord> convertNames(List names) {
        if (isNullOrEmpty(names)) {
            return Collections.emptyList();
        }
        return getTranslatedValues(names);
    }

    protected static SituationVersion convertVersion(Integer version) {
        SituationVersion situationVersion = new SituationVersion();
        situationVersion.setValue(BigInteger.valueOf(version));
        return situationVersion;
    }

    protected static Integer convert(SituationVersion version) {
        if (version == null) {
            return null;
        }
        return version.getValue().intValue();
    }

    protected static Collection<String> convertStringList(List<CharSequence> strings) {
        return strings.stream().map(CharSequence::toString).collect(Collectors.toList());
    }

    protected static Integer convert(BigInteger bigInteger) {
        if (bigInteger == null) {
            return null;
        }
        return bigInteger.intValue();
    }


    protected static BigInteger convert(Integer integer) {
        if (integer == null) {
            return null;
        }
        return BigInteger.valueOf(integer);
    }


    protected static Double convert(BigDecimal d) {
        if (d == null) {
            return null;
        }

        return d.doubleValue();
    }

    protected static BigDecimal convert(Double d) {
        if (d == null) {  return null; }
        return BigDecimal.valueOf(d);
    }

    protected static CharSequence convert(Duration duration) {
        if (duration == null) {
            return null;
        }
        return duration.toString();
    }

    protected static CharSequence convert(javax.xml.datatype.Duration duration) {
        if (duration == null) {
            return null;
        }
        return duration.toString();
    }

    protected static FramedVehicleJourneyRefStructure convert(FramedVehicleJourneyRefRecord framedVehicleJourneyRef) {
        FramedVehicleJourneyRefStructure framedVehicleJourneyRefStructure = new FramedVehicleJourneyRefStructure();
        if (framedVehicleJourneyRef.getDataFrameRef() != null) {
            framedVehicleJourneyRefStructure.setDataFrameRef(
                    setValue(DataFrameRefStructure.class, framedVehicleJourneyRef.getDataFrameRef())
            );
        }
        if (framedVehicleJourneyRef.getDatedVehicleJourneyRef() != null) {
            framedVehicleJourneyRefStructure.setDatedVehicleJourneyRef(
                    framedVehicleJourneyRef.getDatedVehicleJourneyRef().toString()
            );
        }
        return framedVehicleJourneyRefStructure;
    }


    protected static FramedVehicleJourneyRefRecord convert(FramedVehicleJourneyRefStructure framedVehicleJourneyRef) {
        if (framedVehicleJourneyRef == null) {
            return null;
        }
        if (framedVehicleJourneyRef.getDataFrameRef() != null &&
                framedVehicleJourneyRef.getDatedVehicleJourneyRef() != null) {
            return FramedVehicleJourneyRefRecord.newBuilder()
                    .setDataFrameRef(getValue(framedVehicleJourneyRef.getDataFrameRef()))
                    .setDatedVehicleJourneyRef(framedVehicleJourneyRef.getDatedVehicleJourneyRef())
                    .build();
        }
        return null;
    }

    protected static java.time.Duration convertDuration(CharSequence duration) {
        return Duration.parse(duration);
    }
}
