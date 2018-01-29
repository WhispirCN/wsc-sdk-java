package cc.whispir.wsc.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * Created by garen on 2018/1/29.
 */
public class DateUtils {


    public static final String RFC3339 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static Calendar getUTC(){
        //1、取得本地时间：
        Calendar cal = Calendar.getInstance();
        //2、取得时间偏移量：
        int zoneOffset = cal.get(Calendar.ZONE_OFFSET);
        //3、取得夏令时差：
        int dstOffset = cal.get(Calendar.DST_OFFSET);
        //4、从本地时间里扣除这些差量，即可以取得UTC时间：
        cal.add(Calendar.MILLISECOND, -(zoneOffset + dstOffset));
        return cal;
    }

    public static long getUTCMillis(){
        return getUTC().getTimeInMillis();
    }

    public static String formatUTC(final String pattern){
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(getUTCMillis());
    }



}
