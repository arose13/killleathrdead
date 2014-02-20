package co.leathr.app.data;

import java.text.SimpleDateFormat;

public class TimeStampHanlder {
	
	/* Get UnixTime Handlers */
	public long getCurrentUnixTime_returnLong() {
		return(System.currentTimeMillis()/1000L);
	}
	
	public String getCurrentUnixTime_returnString() {
		return String.valueOf((System.currentTimeMillis()/1000L));
	}
	
	public String getDay(long unixtime) {
		long correctedUnixtime = unixtime*1000;
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd");
		return dateFormat.format(correctedUnixtime);
	}
	
	public String getMonth(long unixtime) {
		long correctedUnixtime = unixtime*1000;
		SimpleDateFormat dateFormat = new SimpleDateFormat("MM");
		return dateFormat.format(correctedUnixtime);
	}
	
	public String getYear(long unixtime) {
		long correctedUnixtime = unixtime*1000;
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy");
		return dateFormat.format(correctedUnixtime);
	}
	
}
