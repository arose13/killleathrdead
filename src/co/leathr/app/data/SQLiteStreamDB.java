package co.leathr.app.data;

import java.util.ArrayList;

import co.leathr.app.data.AppObjects.StreamObject;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class SQLiteStreamDB {
	
	/*Constants*/
	public static final String KEY_ROWID = "_id";
	public static final String KEY_TYPE = "type";
	public static final String KEY_CONTENT = "content";
	public static final String KEY_THUMBNAIL = "thumbnail";
	public static final String KEY_TIME_UNIXTIME = "unixtime";
	public static final String KEY_TIME_DAY = "day";
	public static final String KEY_TIME_MONTH = "month";
	public static final String KEY_TIME_YEAR = "year";
	public static final String KEY_EMOTION = "emotion";
	public static final String KEY_OWNER_ID = "owner_id";
	public static final String KEY_HIDE = "hide";
	
	private static final String DATABASE_NAME = "streamDB";
	private static final String DATABASE_TABLE = "streamTABLE";
	private static final int DATABASE_VERSION = 1;
	
	private static final String AND = " AND ";
		
	//private static final String SQL_DROP_TABLE = "DROP TABLE IF EXISTS " + DATABASE_TABLE;
	private static final String SQL_CREATE_TABLE = "CREATE TABLE " + DATABASE_TABLE + " (" + 
														KEY_ROWID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
														KEY_TYPE + " INTEGER DEFAULT 0, " +
														KEY_CONTENT + AppData.DBConstants.TNN +
														KEY_THUMBNAIL + AppData.DBConstants.TNN +
														KEY_TIME_UNIXTIME + AppData.DBConstants.TNN +
														KEY_TIME_DAY + AppData.DBConstants.TNN +
														KEY_TIME_MONTH + AppData.DBConstants.TNN +
														KEY_TIME_YEAR + AppData.DBConstants.TNN + 
														KEY_EMOTION + " INTEGER DEFAULT 0, " +
														KEY_OWNER_ID + AppData.DBConstants.TNN + 
														KEY_HIDE + " INTEGER DEFAULT 0);";
	
	private static final String[] columns = new String[] {KEY_ROWID, KEY_TYPE, KEY_CONTENT, KEY_THUMBNAIL, KEY_TIME_UNIXTIME, KEY_TIME_DAY, KEY_TIME_MONTH, KEY_TIME_YEAR, KEY_EMOTION, KEY_OWNER_ID, KEY_HIDE};
	
	private DBHelper ourHelper;
	private final Context ourContext;
	private SQLiteDatabase ourDatabase;
	
	/* Required class and methods to run the database
	 * DO NOT modify */
	private static class DBHelper extends SQLiteOpenHelper {

		public DBHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
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
	
	public SQLiteStreamDB(Context context) {
		ourContext = context;
	}
	
	/* Required methods to start and stop the Databases */
	public SQLiteStreamDB open() throws SQLException {
		ourHelper = new DBHelper(ourContext);
		ourDatabase = ourHelper.getWritableDatabase();
		return this;
	}
	
	public void close() {
		ourHelper.close();
	}
	
	//////////////////////////////////////////////////
	/* All Custom Methods for handling the StreamDB */
	
	public void putEntry(int type, String content, String thumnail, String unixtime, String day, String month, String year, int emotion, String ownerID) {
		ContentValues values = new ContentValues();
		values.put(KEY_TYPE, type);
		values.put(KEY_CONTENT, content);
		values.put(KEY_THUMBNAIL, thumnail);
		values.put(KEY_TIME_UNIXTIME, unixtime);
		values.put(KEY_TIME_DAY, day);
		values.put(KEY_TIME_MONTH, month);
		values.put(KEY_TIME_YEAR, year);
		values.put(KEY_TIME_YEAR, year);
		values.put(KEY_EMOTION, emotion);
		values.put(KEY_OWNER_ID, ownerID);
		ourDatabase.insert(DATABASE_TABLE, null, values);
	}
	
	public ArrayList<StreamObject> getEntries(String selectionClause) {
		String orderBy = KEY_TIME_UNIXTIME + " DESC";
		Cursor dbc = ourDatabase.query(DATABASE_TABLE, columns, selectionClause, null, null, null, orderBy);
		
		ArrayList<StreamObject> resultSet = new ArrayList<StreamObject>();
		String currentPicDay = "";
		String currentPicMonth = "";
		String currentPicYear = "";
		int iRowID = dbc.getColumnIndex(KEY_ROWID);
		int iType = dbc.getColumnIndex(KEY_TYPE);
		int iContent = dbc.getColumnIndex(KEY_CONTENT);
		int iThumbnail = dbc.getColumnIndex(KEY_THUMBNAIL);
		int iDay = dbc.getColumnIndex(KEY_TIME_DAY);
		int iMonth = dbc.getColumnIndex(KEY_TIME_MONTH);
		int iYear = dbc.getColumnIndex(KEY_TIME_YEAR);
		int iUnixtime = dbc.getColumnIndex(KEY_TIME_UNIXTIME);
		int iEmotion = dbc.getColumnIndex(KEY_EMOTION);
		
		for (dbc.moveToFirst(); !dbc.isAfterLast(); dbc.moveToNext()) {
			int rowID = 0;
			int type = 0;
			String content = null;
			String thumbnail = null;
			String unixtime = null;
			int emotion = 0;
			ArrayList<String> photoArrayList = null;
			
			rowID = dbc.getInt(iRowID);
			type = dbc.getInt(iType);
			content = dbc.getString(iContent);
			thumbnail = dbc.getString(iThumbnail);
			unixtime = dbc.getString(iUnixtime);
			emotion = dbc.getInt(iEmotion);
			resultSet.add(new StreamObject(rowID, type, content, thumbnail, unixtime, emotion, photoArrayList));
		}
		
		return resultSet;
	}
	
	public String selectBy(String day, String month, String year) {
		String where = KEY_TIME_DAY + "=" + "'" + day + "'" +  AND
								+ KEY_TIME_MONTH + "=" + "'" + month + "'" + AND
								+ KEY_TIME_YEAR + "=" + "'" + year + "'";
		return where;
	}
	
	public boolean checkForDuplicates(String thumbnailsURL) {
		String selection = KEY_THUMBNAIL + "=" + "'" + thumbnailsURL + "'";
		long numberOfMatches = DatabaseUtils.queryNumEntries(ourDatabase, DATABASE_TABLE, selection);
		if (numberOfMatches > 0) {
			/* Yes duplicates exists */
			return true;
		} else {
			/* No duplicates found */
			return false;
		}
	}	
	
}
