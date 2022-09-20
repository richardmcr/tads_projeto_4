package uni9.projetopraticoemsistemas.myhealth.helper;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

public class DateFormatter {

    public static String longDateToStringDateTime(Long date) {
        return Objects.isNull(date) ? null : new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date(date));
    }

    public static String longDateToStringTimeDate(Long date) {
        return Objects.isNull(date) ? null : new SimpleDateFormat(" HH:mm - dd/MM/yyyy", Locale.getDefault()).format(new Date(date));
    }

    public static String longDateToStringDate(Long date) {
        return Objects.isNull(date) ? null : new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(date));
    }

    public static String longDateToStringTime(Long date) {
        return Objects.isNull(date) ? null : new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(date));
    }

    public static Long stringDateTimeTolong(String dateTime) {
        try {
            return Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).parse(dateTime)).getTime();
        } catch (Exception e) {
            return null;
        }
    }

    public static Long stringDateTolong(String date) {
        try {
            return Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)).getTime();
        } catch (Exception e) {
            return null;
        }
    }
}
