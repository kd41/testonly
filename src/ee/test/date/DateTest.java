package ee.test.date;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.apache.commons.lang.time.DateUtils;
import org.apache.commons.lang.time.FastDateFormat;

public class DateTest {
    @SuppressWarnings("deprecation")
    public static void main(String... args) throws Exception {

        String datestr = "1993-07-06 13:40:15.780";
        String timeformat = "yyyy-MM-dd HH:mm:ss.SSS"; 
        SimpleDateFormat timeFormat = new SimpleDateFormat( timeformat );
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Calendar birthDate = Calendar.getInstance();
        birthDate.setTime(timeFormat.parse(datestr));
        System.out.println("birthDate: " + birthDate);
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        int age = currentYear - birthDate.get(Calendar.YEAR);
        if (now.get(Calendar.MONTH) < birthDate.get(Calendar.MONTH) || //
                (now.get(Calendar.MONTH) == birthDate.get(Calendar.MONTH) && now.get(Calendar.DAY_OF_MONTH) < birthDate.get(Calendar.DAY_OF_MONTH))) { // CAS-252623
            age--;
        }

        System.out.println("age: " + age);
    }
    
    private static void test1() throws ParseException {

        String datestr = "1993-07-05 13:40:15.780";
        String timeformat = "yyyy-MM-dd HH:mm:ss.SSS"; 
        SimpleDateFormat timeFormat = new SimpleDateFormat( timeformat );
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        Date birthDate = timeFormat.parse(datestr);
        System.out.println("birthDate: " + birthDate);
        Date now = Calendar.getInstance().getTime();
        int currentYear = now.getYear();
        int age = currentYear - birthDate.getYear();
        if (now.getMonth() < birthDate.getMonth() || //
                (now.getMonth() == birthDate.getMonth() && now.getDay() < birthDate.getDay())) { // CAS-252623
            age--;
        }

        System.out.println("age: " + age);
    }
    
    private static void test2() throws ParseException {
        String datestr = "2013-07-19 13:40:15.780";
        String timeformat = "yyyy-MM-dd HH:mm:ss.SSS";
        SimpleDateFormat timeFormat = new SimpleDateFormat( timeformat );
        timeFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        
        Date date = timeFormat.parse(datestr);
        
        System.out.println("parsed date: " + date);
        
        date = DateUtils.addMinutes(date, 10);
        
        

        System.out.println("add 10 minutes: " + date);
        System.out.println(TimeZone.getDefault().getDisplayName()); 
        System.out.println(timeFormat.getTimeZone().getDisplayName());  
        
        Calendar calendar = Calendar.getInstance(TimeZone.getTimeZone("GMT"));
        
        
        Date now = calendar.getTime();
        
        System.out.println("is before: " + date.before(now));
        
        System.out.println("now: " + now);
        
        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(now);
        cal1.setTimeZone(timeFormat.getTimeZone());
        Calendar cal2 = Calendar.getInstance();
        cal2.setTime(date);
        cal2.setTimeZone(TimeZone.getDefault());
        System.out.println("cal1:" + cal1);
        System.out.println("cal2:" + cal2);
        System.out.println("time1:" + cal1.getTime());
        System.out.println("time2:" + cal2.getTime());
        
        System.out.println("cals compare: " + cal1.compareTo(cal2));
    }
}
