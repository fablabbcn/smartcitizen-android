package cat.lafosca.smartcitizen.commons;

import com.ocpsoft.pretty.time.PrettyTime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Created by ferran on 09/06/15.
 */
public class PrettyTimeHelper {

    PrettyTime pt;
//    SimpleDateFormat sdf;

    private static PrettyTimeHelper ourInstance = new PrettyTimeHelper();

    public static PrettyTimeHelper getInstance() {
        return ourInstance;
    }

    private PrettyTimeHelper() {
        pt = new PrettyTime();
        pt.setLocale(Locale.getDefault());
//        sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");//2015-05-25T07:30:29Z

        /*Calendar cal = Calendar.getInstance();
        TimeZone timeZone1 = cal.getTimeZone();*/

        //sdf.setTimeZone(TimeZone.getTimeZone("GMT"));

//        sdf.setTimeZone(TimeZone.getDefault());
    }

    public String getPrettyTime(Date date) throws ParseException { // date: yyyy-MM-ddTHH:mm:ssZ

        String prettyDate = "";
        prettyDate = pt.format(date);
        if (prettyDate.length() > 1 ) {
            prettyDate = prettyDate.substring(0,1).toUpperCase() + prettyDate.substring(1);
        }
        return prettyDate;
    }


}
