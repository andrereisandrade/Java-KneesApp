package br.com.kneesapp.web.api.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import org.joda.time.DateTime;
import org.joda.time.Hours;
import org.joda.time.LocalDate;

public final class JDateUtils {

    public static final int SECUND = 1;
    public static final int MINUTE = 2;
    public static final int HOUR = 3;
    public static final int DAY = 4;
    public static final int WEEK = 5;
    public static final int MONTH = 6;
    public static final int YEAR = 7;

    public static final DateFormat DF_DATA_HORA_MINUTO = new SimpleDateFormat("dd/MM/yyyy 'às' HH:mm");

    public static Integer returnUnit(Date data, int unidade) throws IllegalArgumentException {
        Calendar cale = GregorianCalendar.getInstance();
        cale.setTime(data);

        switch (unidade) {
            case JDateUtils.DAY:
                return cale.get(Calendar.DAY_OF_MONTH);
            case JDateUtils.MONTH:
                return cale.get(Calendar.MONTH) + 1;
            case JDateUtils.YEAR:
                return cale.get(Calendar.YEAR);
            case JDateUtils.HOUR:
                return cale.get(Calendar.HOUR_OF_DAY);
            case JDateUtils.MINUTE:
                return cale.get(Calendar.MINUTE);
            case JDateUtils.SECUND:
                return cale.get(Calendar.SECOND);
            case JDateUtils.WEEK:
                return cale.get(Calendar.WEEK_OF_MONTH);
            default:
                throw new IllegalArgumentException("Unidade especificada nao valida. Escolha: DIA, MES ou ANO");
        }

    }

    public static int getTimeDifferent(Date dateInitial, Date dateFinal) {
        DateTime initialPeriod = new DateTime(dateInitial);
        DateTime finalPeriod = new DateTime(dateFinal);

        return Hours.hoursBetween(initialPeriod, finalPeriod).getHours();
    }

    public static Date returnDate(String data, DateFormat df) {
        try {
            return df.parse(data);
        } catch (ParseException ex) {
            return null;
        }
    }

    public static LocalDate[] returnMaxAndMinimalOfMonth(final int mes, int diaInit) {
        LocalDate[] arr = new LocalDate[2];
        int ano = new DateTime().year().get();
        if ((mes >= 1) && (mes <= 12)) {
            LocalDate initialDate = new LocalDate(ano, mes, diaInit);
            LocalDate finalDate = new LocalDate(ano, mes, initialDate.dayOfMonth().getMaximumValue());
            arr[0] = initialDate;
            arr[1] = finalDate;
        }
        return arr;
    }

}
