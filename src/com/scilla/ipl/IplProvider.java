package com.scilla.ipl;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Arrays;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteQueryBuilder;
import android.net.Uri;
import android.provider.BaseColumns;
import android.text.TextUtils;

import com.scilla.util.SLog;

public class IplProvider extends ContentProvider {
	private static final String DATABASE_NAME = "scillaiplDB";
	public static final String PROVIDER_NAME = "com.scilla.ipl.IplProvider";
	private static final int DATABASE_VERSION = 1;
	private static final int TEAMLIST = 1;
	private static final int TEAMLIST1 = 2;
	private static final int PLACE = 3;
	private static final int TIME = 4;
	private static final int DATE = 5;
	private static final int SEARCH_DATE = 6;

	public static final Uri TEAM_URI = Uri.parse("content://" + PROVIDER_NAME
			+ "/team");
	public static final Uri TEAM_URI1 = Uri.parse("content://" + PROVIDER_NAME
			+ "/2team");
	public static final Uri PLACE_URI = Uri.parse("content://" + PROVIDER_NAME
			+ "/place");
	public static final Uri TIME_URI = Uri.parse("content://" + PROVIDER_NAME
			+ "/time");
	public static final Uri DATE_URI = Uri.parse("content://" + PROVIDER_NAME
			+ "/date");
	public static final Uri DATE_URI_SEARCH = Uri.parse("content://"
			+ PROVIDER_NAME + "/sdate");
	private static final UriMatcher MATCHER;
	private SQLiteDatabase db;

	public static final class Columns implements BaseColumns {
		public static final String _ID = "_id";
		public static final String TEAM = "team";
		public static final String TEAM1 = "team1";
		public static final String TEAMS = "teams";
		public static final String STADIUM = "stadium";
		public static final String PLACE = "place";
		public static final String TIME = "time";
		public static final String DATE = "date";
		public static final String VENUE = "venue";
	}

	static {
		MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
		MATCHER.addURI(PROVIDER_NAME, "team/*", TEAMLIST);
		MATCHER.addURI(PROVIDER_NAME, "2team/*/*", TEAMLIST1);
		MATCHER.addURI(PROVIDER_NAME, "place/*", PLACE);
		MATCHER.addURI(PROVIDER_NAME, "time/*", TIME);
		MATCHER.addURI(PROVIDER_NAME, "date", DATE);
		MATCHER.addURI(PROVIDER_NAME, "sdate/*", SEARCH_DATE);

	}

	public int getDbVersion() {
		return (DATABASE_VERSION);
	}

	private class DatabaseHelper extends SQLiteOpenHelper {
		private final Context ctx;

		public DatabaseHelper(Context context) {
			super(context, DATABASE_NAME, null, DATABASE_VERSION);
			ctx = context;
			// checking database and copy Data from asset folder only if it
			// doesn't exists
			if (!isModelDBExists()) {
				try {
					copyDataBase();
				} catch (IOException e) {
					SLog.error(this.getClass(), "Error copying model database",
							e);
					throw new RuntimeException("Error copying model database",
							e);
				}
				SLog.debug(this.getClass(), "Initial model database is created");
			}
		}

		private void copyDataBase() throws IOException {
			InputStream myInput = ctx.getAssets().open(DATABASE_NAME);
			File db = ctx.getDatabasePath(DATABASE_NAME);
			if (!db.exists()) {
				String dbDir = db.getAbsolutePath().substring(0,
						db.getAbsolutePath().lastIndexOf("/"));
				File dbDirectory = new File(dbDir);
				dbDirectory.mkdir();
			}
			OutputStream myOutput = new FileOutputStream(db.getAbsolutePath());

			byte[] buffer = new byte[1024];
			int length;
			while ((length = myInput.read(buffer)) > 0) {
				myOutput.write(buffer, 0, length);
			}

			myOutput.flush();
			myOutput.close();
			myInput.close();
		}

		private boolean isModelDBExists() {

			SQLiteDatabase checkDB = null;
			boolean exist = false;
			try {
				File db = ctx.getDatabasePath(DATABASE_NAME);
				String dbPath = db.getAbsolutePath();
				if (db == null || !db.exists()) {
					exist = false;
				} else {
					checkDB = SQLiteDatabase.openDatabase(dbPath, null,
							SQLiteDatabase.OPEN_READONLY);
				}
			} catch (Exception e) {
				SLog.debug(this.getClass(), "database " + DATABASE_NAME
						+ " does't exist", e);
			}

			if (checkDB != null) {
				exist = true;
				if (checkDB.isOpen())
					checkDB.close();
			}
			return exist;
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
		}
	}

	@Override
	public boolean onCreate() {
		db = (new DatabaseHelper(getContext())).getWritableDatabase();
		return (db == null) ? false : true;
	}

	@Override
	public int delete(Uri arg0, String arg1, String[] arg2) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public String getType(Uri url) {
		if (isCollectionUri(url)) {
			return (getCollectionType());
		}

		return (getSingleType());
	}

	@Override
	public Uri insert(Uri arg0, ContentValues arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Cursor query(Uri url, String[] projection, String selection,
			String[] selectionArgs, String sortOrder) {

		SQLiteQueryBuilder qb = new SQLiteQueryBuilder();
		SLog.debug(this.getClass(), "Query: " + url.getPath());

		qb.setTables(getTableName());
		// Log.i("kumar", "Query: " + MATCHER.match(url));
		switch (MATCHER.match(url)) {

		case TEAMLIST:
			projection = new String[] { Columns._ID, Columns.TEAMS,
					Columns.VENUE, Columns.TIME, Columns.DATE, Columns.TEAM1,
					Columns.TEAM, Columns.STADIUM, Columns.PLACE };
			qb.appendWhere(IplProvider.Columns.TEAM + "=" + " '"
					+ url.getPathSegments().get(1) + "'" + " OR "
					+ IplProvider.Columns.TEAM1 + "=" + " '"
					+ url.getPathSegments().get(1) + "'");

			break;
		case TEAMLIST1:
			projection = new String[] { Columns._ID, Columns.TEAMS,
					Columns.VENUE, Columns.TIME, Columns.DATE, Columns.TEAM1,
					Columns.TEAM, Columns.STADIUM, Columns.PLACE };
			qb.appendWhere(IplProvider.Columns.TEAM + "=" + " '"
					+ url.getPathSegments().get(1) + "'" + " AND "
					+ IplProvider.Columns.TEAM1 + "=" + " '"
					+ url.getPathSegments().get(2) + "'" + " OR "
					+ IplProvider.Columns.TEAM + "=" + " '"
					+ url.getPathSegments().get(2) + "'" + " AND "
					+ IplProvider.Columns.TEAM1 + "=" + " '"
					+ url.getPathSegments().get(1) + "'");
			break;
		case PLACE:
			qb.appendWhere(IplProvider.Columns.PLACE + "=" + " '"
					+ url.getPathSegments().get(1) + "'");
			break;
		case TIME:
			qb.appendWhere(IplProvider.Columns.TIME + "="
					+ url.getPathSegments().get(1));
			break;
		case DATE:
			projection = new String[] { Columns._ID, Columns.DATE };

			selection = "1) GROUP BY (" + Columns.DATE;
			if (TextUtils.isEmpty(selection))
				;
			sortOrder = Columns._ID + " ASC ";
			if (TextUtils.isEmpty(sortOrder))
				;

			break;
		case SEARCH_DATE:
			qb.appendWhere(IplProvider.Columns.DATE + "=" + " '"
					+ url.getPathSegments().get(1) + "'");
			projection = new String[] { Columns._ID, Columns.TEAMS,
					Columns.VENUE, Columns.TIME, Columns.DATE, Columns.TEAM1,
					Columns.TEAM, Columns.STADIUM, Columns.PLACE };
			break;
		default:
			throw new IllegalArgumentException("Unknown URI " + url);

		}

		if (TextUtils.isEmpty(sortOrder)) {
			sortOrder = getDefaultSortOrder();
		}

		Cursor c = qb.query(db, projection, selection, selectionArgs, null,
				null, sortOrder);
		c.setNotificationUri(getContext().getContentResolver(), url);

		SLog.debug(this.getClass(),
				"ModelProvider returns the columns:" + c.getColumnCount()
						+ ", Count:" + c.getCount() + ", Column Names:"
						+ Arrays.toString(c.getColumnNames()));
		return c;
	}

	@Override
	public int update(Uri arg0, ContentValues arg1, String arg2, String[] arg3) {
		// TODO Auto-generated method stub
		return 0;
	}

	private boolean isCollectionUri(Uri url) {
		return (MATCHER.match(url) == TEAMLIST);
	}

	private String getTableName() {
		return ("iplmatch");
	}

	private String getDefaultSortOrder() {
		return (Columns._ID);
	}

	private String getCollectionType() {
		return ("vnd.android.cursor.dir/vnd.scilla");
	}

	private String getSingleType() {
		return ("vnd.android.cursor.item/vnd.scilla");
	}

}