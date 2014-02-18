package co.leathr.app.data;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteEventDB {
	
	/*Constants*/
	public static final String KEY_ROWID = "_id";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_TIME_START = "timestart";
	public static final String KEY_TIME_STOP = "timestop";
	public static final String KEY_LOCATION = "location";
	public static final String KEY_OWNEREMAIL = "owneremail";
	public static final String KEY_HIDE = "hide";
	
	private static final String DATABASE_NAME = "eventDB";
	private static final String DATABASE_TABLE = "eventTABLE";
	private static final int DATABASE_VERSION = 1;
	
	//private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + DATABASE_TABLE;
	private static final String SQL_CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE + " (" +
														KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT,  " +
														KEY_CONTENT + AppData.DBConstants.TNN +
														KEY_TIME_START + AppData.DBConstants.TNN + 
														KEY_TIME_STOP + AppData.DBConstants.TNN +
														KEY_LOCATION + AppData.DBConstants.TNN + 
														KEY_OWNEREMAIL + AppData.DBConstants.TNN +
														KEY_HIDE + " INTEGER DEFAULT 0);";
	private static final String[] columns = new String[] {KEY_ROWID, KEY_CONTENT, KEY_TIME_START, KEY_TIME_STOP, KEY_LOCATION, KEY_OWNEREMAIL, KEY_HIDE};
	
	private DBHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	/* Required class and methods to run the database
	 * DO NOT modify */
	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			/* This creates the database */
			db.execSQL(SQL_CREATE_TABLE);
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		} /* onUpgrade is required but not implemented or defined */
				
	}
	
	public SQLiteEventDB(Context context) {
		ourContext = context;
	}
	public SQLiteEventDB open() throws SQLException {
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		ourHelper.close();
	}
	
	/* All Custom Methods for handling the EventDB */
}
