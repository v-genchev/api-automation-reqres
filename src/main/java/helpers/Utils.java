package helpers;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

public class Utils {

    public static LocalDateTime parseDate(String dateToParse) {
        return parseDate(dateToParse, DateTimeFormatter.ISO_ZONED_DATE_TIME);
    }

    public static LocalDateTime parseDate(String dateToParse, DateTimeFormatter formatter) {
        ZonedDateTime zonedDateTime1 = ZonedDateTime.parse(dateToParse, formatter);
        return zonedDateTime1.toLocalDateTime();
    }
}
