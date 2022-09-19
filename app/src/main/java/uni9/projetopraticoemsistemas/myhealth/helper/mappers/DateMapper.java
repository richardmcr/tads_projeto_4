package uni9.projetopraticoemsistemas.myhealth.helper.mappers;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

@DateConverter
public class DateMapper {

    @LongToStringDate
    public String longToStringDate(Long date) {
        return new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).format(new Date(date));
    }

    @LongToStringTime
    public String longToStringTime(Long date) {
        return new SimpleDateFormat("HH:mm", Locale.getDefault()).format(new Date(date));
    }

    @LongToStringDateTime
    public String longToStringDateTime(Long date) {
        return new SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(new Date(date));
    }
}
