//Start of user code reserved-for:android-sqlite-db.imports
package com.uisleandro.store.Core.model;  

import java.util.ArrayList;
import java.util.List;
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

//TODO: I wont return any view, Id rather return the cursor instead 

// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class DbLogProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.db_log";
	public static final String SCHEME = "content://";

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

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.DB_LOG_ID,
		DbHelper.DB_LOG_SERVER_ID,
		DbHelper.DB_LOG_DIRTY,
		DbHelper.DB_LOG_LAST_UPDATE, 
		DbHelper.DB_LOG_ACTION_NAME, 
		DbHelper.DB_LOG_PARAMETER, 
		DbHelper.DB_LOG_FK_USER
	};

	public DbLogDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("DbLogDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public Cursor listAll(){
		Cursor cursor = database.query(DbHelper.TABLE_DB_LOG,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){
		Cursor cursor = database.query(DbHelper.TABLE_DB_LOG,
			selectableColumns,
			DbHelper.DB_LOG_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){
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

	public Cursor getLastId(){
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_DB_LOG +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;		
	}

// begin content-provider-interface

	@Override
	public boolean onCreate() {
		return false;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		long last_id = database.insert(DbHelper.TABLE_DB_LOG, null, values);
		return last_id;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		int rows_affected = database.update(DbHelper.TABLE_DB_LOG, values, DbHelper.DB_LOG_ID + " = " + selectionArgs[0], null);
		return rows_affected;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		int rows_affected = database.delete(DbHelper.TABLE_DB_LOG, DbHelper.DB_LOG_ID + " = " + selectionArgs[0], null);
		return rows_affected;
	}

// end content-provider-interface 

// reserved-for:android-sqlite-db.functions
//End of user code

//Start of user code reserved-for:android-sqlite-sync.functions
//reserved-for:android-sqlite-sync.functions
//End of user code

//Start of user code reserved-for:query3.functions
//reserved-for:query3.functions
//End of user code


//Start of user code reserved-for:android-sqlite-db.begin-default-query
	// TODO: I NEED TO KNOW HOW TO MAKE VARIOUS QUERIES DEPENDING ON THE URI
	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Cursor result = null;
		if (URI_DB_LOG_ALL.equals(uri)) {
			result = listAll();
		} else if(URI_DB_LOG_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		} else if(URI_DB_LOG_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		} else if(URI_DB_LOG_LASTID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:android-sqlite-db.begin-default-query
//End of user code

// Start of user code reserved-for:android-sqlite-sync.default-query

// reserved-for:android-sqlite-sync.default-query
// End of user code

//Start of user code reserved-for:android-sqlite-db.end-default-query
		return result;
	}
}
// reserved-for:android-sqlite-db.end-default-query
//End of user code

