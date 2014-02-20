package co.leathr.app.data;

import java.util.ArrayList;

public class AppObjects {
	
	/* Objects that appear in the Stream list */
	public static class StreamObject {
		public final int dbRowID;
		public final int type;
		public final String content;
		public final String thumbnail;
		public final String unixtime;
		public final int emotion;
		public final ArrayList<String> photoArrayList;
		
		public StreamObject(int dbRowID, int type, String content, String thumbnail, String unixtime, int emotion, ArrayList<String> photoArrayList) {
			this.dbRowID = dbRowID;
			this.type = type;
			this.content = content;
			this.thumbnail = thumbnail;
			this.unixtime = unixtime;
			this.emotion = emotion;
			this.photoArrayList = photoArrayList;
		}
	}

}
