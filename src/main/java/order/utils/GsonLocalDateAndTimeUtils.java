package order.utils;

import com.google.gson.*;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Type;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class GsonLocalDateAndTimeUtils {
    private static String PATTERN_DATE = "yyyy-MM-dd";
    private static String PATTERN_TIME = "HH:mm:ss";
    private static String PATTERN_DATETIME = String.format("%s %s", PATTERN_DATE, PATTERN_TIME);

    private static Gson gson =
            new GsonBuilder().disableHtmlEscaping()
                             .setDateFormat(PATTERN_DATETIME)
                             .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter().nullSafe())
                             .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
                             .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter().nullSafe())
                             .create();
//                new GsonBuilder().disableHtmlEscaping()
//                             .setFieldNamingPolicy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
//                             .setDateFormat(PATTERN_DATETIME)
//                             .registerTypeAdapter(LocalDateTime.class, new LocalDateTimeAdapter().nullSafe())
//            .registerTypeAdapter(LocalDate.class, new LocalDateAdapter().nullSafe())
//            .registerTypeAdapter(LocalTime.class, new LocalTimeAdapter().nullSafe())
//            .create();

    public static String toJson(Object o) {
        String result = gson.toJson(o);
        if("null".equals(result))
            return null;
        return result;
    }

    public static <T> T fromJson(String s, Class<T> clazz) {
        try {
            return gson.fromJson(s, clazz);
        } catch(JsonSyntaxException e) {
//            log.error(e.getMessage());
            System.out.println(e.getMessage());
        }
        return null;
    }

    public static <T> T fromJson(Reader json, Type typeOfT){
        return gson.fromJson(json, typeOfT);
    }


    static class LocalDateTimeAdapter extends TypeAdapter<LocalDateTime> {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN_DATETIME);

        @Override
        public void write(JsonWriter out, LocalDateTime value) throws IOException {
            if(value != null)
                out.value(value.format(format));
        }

        @Override
        public LocalDateTime read(JsonReader in) throws IOException {
            return LocalDateTime.parse(in.nextString(), format);
        }
    }

    static class LocalDateAdapter extends TypeAdapter<LocalDate> {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN_DATE);

        @Override
        public void write(JsonWriter out, LocalDate value) throws IOException {
            out.value(value.format(format));
        }

        @Override
        public LocalDate read(JsonReader in) throws IOException {
            return LocalDate.parse(in.nextString(), format);
        }
    }

    static class LocalTimeAdapter extends TypeAdapter<LocalTime> {
        DateTimeFormatter format = DateTimeFormatter.ofPattern(PATTERN_TIME);
        @Override
        public void write(JsonWriter out, LocalTime value) throws IOException {
            out.value(value.format(format));
        }

        @Override
        public LocalTime read(JsonReader in) throws IOException {
            return LocalTime.parse(in.nextString(), format);
        }
    }
}
