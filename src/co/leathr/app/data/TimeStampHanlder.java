package co.leathr.app.data;

import android.annotation.SuppressLint;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@SuppressLint("SimpleDateFormat")
public class TimeStampHanlder {
	
	/* Get UnixTime Handlers */
	public static long getCurrentUnixTime_returnLong() {
		return(System.currentTimeMillis()/1000L);
	}
	
	public static String getCurrentUnixTime_returnString() {
		return String.valueOf((System.currentTimeMillis()/1000L));
	}
	
	public static int unixtimeToInt(long unixtime) {
		String unixtimeString  = String.valueOf(unixtime);
		if (unixtimeString.length() == 13) {
			unixtime = unixtime/1000L;
		}
		String unixFinalString = String.valueOf(unixtime);
		int unixtimeInt = Integer.valueOf(unixFinalString);
		return unixtimeInt;
	}
	
	public static long getTimefromTimeStamp(String timestamp) throws ParseException {
		//TimeStamp format is "2013-07-15T19:01:13.000Z"
		String timeStampPreprocessed = timestamp.replace("T", " ").replace("Z", " ");
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S");
		Date date = dateFormat.parse(timeStampPreprocessed);
		return date.getTime();
	}
	
	public static String getDay(long unixtime) {
		long correctedUnixtime = unixtime*1000;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		return dateFormat.format(correctedUnixtime);
	}
	
	public static String getMonth(long unixtime) {
		long correctedUnixtime = unixtime*1000;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		return dateFormat.format(correctedUnixtime);
	}
	
	public static String getYear(long unixtime) {
		long correctedUnixtime = unixtime*1000;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		return dateFormat.format(correctedUnixtime);
	}
	
}
