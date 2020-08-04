package main.util;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class DateUtils {
    public static final ZoneId LOCAL_ZONE = ZoneId.systemDefault();
    public static final ZoneId UTC_ZONE = ZoneId.of("UTC");
    public static final DateTimeFormatter DbFormat = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
    public static final DateTimeFormatter localFormat = DateTimeFormatter.ofPattern("dd/MM/yy hh:mm a");
    public static final DateTimeFormatter timeformatter = DateTimeFormatter.ofPattern("hha");

    public static ZonedDateTime UTCStringToZonedDateTime(String utcDateString) {
        return LocalDateTime.parse(utcDateString, DbFormat)
                .atZone(UTC_ZONE)
                .withZoneSameInstant(LOCAL_ZONE);
    }

    public static String zonedDateTimeToString(ZonedDateTime date) {
        return localFormat.format(date);
    }

    public static String getUTCStringFromLocalTime(LocalDateTime localDateTime) {
        return DbFormat.format(LocalDateTime.from(localDateTime).atZone(LOCAL_ZONE).withZoneSameInstant(UTC_ZONE));
    }

    public static String getUTCStringNow() {
        return DbFormat.format(ZonedDateTime.now().withZoneSameInstant(UTC_ZONE));
    }

    public static String getLocalStringNow() {
        return localFormat.format(ZonedDateTime.now().withZoneSameInstant(LOCAL_ZONE));
    }

    public static String getTimeStringFromZonedTime(ZonedDateTime dateTime) {
        return timeformatter.format(dateTime.withZoneSameInstant(LOCAL_ZONE));
    }
}
