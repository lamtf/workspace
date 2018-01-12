// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.Core.model;  

import java.util.ArrayList;
import java.util.List;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
//old
//import com.uisleandro.util.LongDateFormatter;
//import com.uisleandro.store.model.DbHelper;
//import com.uisleandro.store.core.model.DbLogDbHelper;

import com.uisleandro.store.DbHelper;
// reserved-for:AndroidSqliteDatabase001
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle001
// reserved-for:AndroidSqliteSyncSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase002
public class DbLogProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.db_log";
	public static final String SCHEME = "content://";

	public static final String DB_LOG_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_DB_LOG_INSERT = Uri.parse(DB_LOG_INSERT);
	public static final String DB_LOG_INSERT_BASE = DB_LOG_INSERT + "/";

	public static final String DB_LOG_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_DB_LOG_UPDATE = Uri.parse(DB_LOG_UPDATE);
	public static final String DB_LOG_UPDATE_BASE = DB_LOG_UPDATE + "/";

	public static final String DB_LOG_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_DB_LOG_DELETE = Uri.parse(DB_LOG_DELETE);
	public static final String DB_LOG_DELETE_BASE = DB_LOG_DELETE + "/";

	public static final String DB_LOG_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_DB_LOG_ALL = Uri.parse(DB_LOG_ALL);
	public static final String DB_LOG_ALL_BASE = DB_LOG_ALL + "/";

	public static final String DB_LOG_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_DB_LOG_SOME = Uri.parse(DB_LOG_SOME);
	public static final String DB_LOG_SOME_BASE = DB_LOG_SOME + "/";

	public static final String DB_LOG_BYID = SCHEME + AUTHORITY + "/byid";
	public static final Uri URI_DB_LOG_BYID = Uri.parse(DB_LOG_BYID);
	public static final String DB_LOG_BYID_BASE = DB_LOG_BYID + "/";

	public static final String DB_LOG_LASTID = SCHEME + AUTHORITY + "/lastid";
	public static final Uri URI_DB_LOG_LASTID = Uri.parse(DB_LOG_LASTID);
	public static final String DB_LOG_LASTID_BASE = DB_LOG_LASTID + "/";

// reserved-for:AndroidSqliteDatabase002
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle002
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003
	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[] { 
		DbHelper.DB_LOG_ID,
		DbHelper.DB_LOG_SERVER_ID,
		DbHelper.DB_LOG_DIRTY,
		DbHelper.DB_LOG_LAST_UPDATE, 
		DbHelper.DB_LOG_ACTION_NAME, 
		DbHelper.DB_LOG_PARAMETER, 
		DbHelper.DB_LOG_FK_USER
	};

	public DbLogDataSource (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("DbLogDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public Cursor listAll () {
		Cursor cursor = database.query(DbHelper.TABLE_DB_LOG,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById (long id) {
		Cursor cursor = database.query(DbHelper.TABLE_DB_LOG,
			selectableColumns,
			DbHelper.DB_LOG_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome (long page_count, long page_size) {
		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"action_name, " +
			"parameter, " +
			"fk_user" +
			" FROM " + DbHelper.TABLE_DB_LOG;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public Cursor getLastId () {
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_DB_LOG +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;		
	}

// begin content-provider-interface

	@Override
	public boolean onCreate () {
		return false;
	}

	@Nullable
	@Override
	public String getType (@NonNull Uri uri) {
		return null;
	}
// reserved-for:AndroidSqliteDatabase003
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase004
	@Nullable
	@Override
	public Uri insert (@NonNull Uri uri, @Nullable ContentValues values) {
		Cursor result = null;
		if (URI_DB_LOG_INSERT.equals(uri)) {
			result = database.insert(DbHelper.TABLE_DB_LOG, null, values);
		}
// reserved-for:AndroidSqliteDatabase004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle003
/* @Insert */
// reserved-for:AndroidSqliteQuerySingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase005
		return null;
	}
// reserved-for:AndroidSqliteDatabase005
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase006
	@Override
	public int update (@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_DB_LOG_UPDATE.equals(uri)) {
			result = database.update(DbHelper.TABLE_DB_LOG, values, DbHelper.DB_LOG_ID + " = " + selectionArgs[0], null);
		}
// reserved-for:AndroidSqliteDatabase006
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle004
/* @UpdateWhere */
// reserved-for:AndroidSqliteQuerySingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase007
		return result;
	}
// reserved-for:AndroidSqliteDatabase007
// End of user code


// Start of user code reserved-for:AndroidSqliteDatabase008
	@Override
	public int delete (@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_DB_LOG_DELETE.equals(uri)) {
			result = database.delete(DbHelper.TABLE_DB_LOG, DbHelper.DB_LOG_ID + " = " + selectionArgs[0], null);
		}
// reserved-for:AndroidSqliteDatabase008
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle005
/* @DeleteWhere */
// reserved-for:AndroidSqliteQuerySingle005
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase009
		return result;
	}
// reserved-for:AndroidSqliteDatabase009
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle003
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle006
// reserved-for:AndroidSqliteQuerySingle006
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase010
	// TODO: I NEED TO KNOW HOW TO MAKE VARIOUS QUERIES DEPENDING ON THE URI
	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Cursor result = null;
		if (URI_DB_LOG_ALL.equals(uri)) {
			result = listAll();
		}
		else if (URI_DB_LOG_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if (URI_DB_LOG_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if (URI_DB_LOG_LASTID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:AndroidSqliteDatabase010
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle004
// reserved-for:AndroidSqliteSyncSingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle007
/* @ExistsWhere||@SelectValueWhere||@SelectOneWhere||@SelectListWhere */
// Start of user code reserved-for:AndroidSqliteQuerySingle007

// Start of user code reserved-for:AndroidSqliteDatabase011
// reserved-for:AndroidSqliteDatabase011
// End of user code
