package Examples_Collections;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;

public class Date_Test {
    public static void main(String[] args) throws ParseException {
        DateFormat df = new SimpleDateFormat("MMMMM d yyyy", Locale.ENGLISH);
        Date date = new Date();
        Date date2 = new Date();
        date = df.parse("JUNE 1 1980");
        System.out.println(date.toString().substring(4,7));
        long diff = date2.getTime() - date.getTime();
        System.out.println(diff);
        System.out.println(date2.before(date));
        System.out.println(date2.after(date));
        System.out.println(date2.compareTo(date));
        Calendar calendar = new GregorianCalendar(1900,0,31);
        Date date3 = calendar.getTime();
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd G 'at' HH:mm:ss z");
        System.out.println(dateFormat.format(new Date()));
    }
}
