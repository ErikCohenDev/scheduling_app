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

    public static ZonedDateTime UTCStringToZonedDateTime(String utcDateString) {
        return LocalDateTime.parse(utcDateString, DbFormat).atZone(UTC_ZONE);
    }

    public static String zonedDateTimeToString(ZonedDateTime date) {
        return localFormat.format(date.withZoneSameInstant(LOCAL_ZONE));
    }
}