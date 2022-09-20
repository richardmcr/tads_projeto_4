package uni9.projetopraticoemsistemas.myhealth.helper.mappers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Objects;

@DateConverter
public class DateMapper {

    @LongToStringDate
    public String longToStringDate(Long date) {
        return Objects.isNull(date) ? null : new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(date));
    }

    @LongToStringTime
    public String longToStringTime(Long date) {
        return Objects.isNull(date) ? null : new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(date));
    }

    @LongToStringDateTime
    public String longToStringDateTime(Long date) {
        return Objects.isNull(date) ? null : new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date(date));
    }

    @StringToLongDate
    public Long stringToLongDate(String date) {
        try {
            return Objects.requireNonNull(new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(date)).getTime();
        } catch (Exception e) {
            return null;
        }
    }
}
