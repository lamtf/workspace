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
//import com.uisleandro.store.client.model.CountryDbHelper;

import com.uisleandro.store.DbHelper;
// reserved-for:AndroidSqliteDatabase001
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle001
// reserved-for:AndroidSqliteSyncSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase002
public class CountryProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.country";
	public static final String SCHEME = "content://";

	public static final String COUNTRY_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_COUNTRY_INSERT = Uri.parse(COUNTRY_INSERT);
	public static final String COUNTRY_INSERT_BASE = COUNTRY_INSERT + "/";

	public static final String COUNTRY_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_COUNTRY_UPDATE = Uri.parse(COUNTRY_UPDATE);
	public static final String COUNTRY_UPDATE_BASE = COUNTRY_UPDATE + "/";

	public static final String COUNTRY_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_COUNTRY_DELETE = Uri.parse(COUNTRY_DELETE);
	public static final String COUNTRY_DELETE_BASE = COUNTRY_DELETE + "/";

	public static final String COUNTRY_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_COUNTRY_ALL = Uri.parse(COUNTRY_ALL);
	public static final String COUNTRY_ALL_BASE = COUNTRY_ALL + "/";

	public static final String COUNTRY_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_COUNTRY_SOME = Uri.parse(COUNTRY_SOME);
	public static final String COUNTRY_SOME_BASE = COUNTRY_SOME + "/";

	public static final String COUNTRY_BYID = SCHEME + AUTHORITY + "/byid";
	public static final Uri URI_COUNTRY_BYID = Uri.parse(COUNTRY_BYID);
	public static final String COUNTRY_BYID_BASE = COUNTRY_BYID + "/";

	public static final String COUNTRY_LASTID = SCHEME + AUTHORITY + "/lastid";
	public static final Uri URI_COUNTRY_LASTID = Uri.parse(COUNTRY_LASTID);
	public static final String COUNTRY_LASTID_BASE = COUNTRY_LASTID + "/";

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
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.COUNTRY_ID,
		DbHelper.COUNTRY_SERVER_ID,
		DbHelper.COUNTRY_DIRTY,
		DbHelper.COUNTRY_LAST_UPDATE, 
		DbHelper.COUNTRY_NAME
	};

	public CountryDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("CountryDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public Cursor listAll(){
		Cursor cursor = database.query(DbHelper.TABLE_COUNTRY,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){
		Cursor cursor = database.query(DbHelper.TABLE_COUNTRY,
			selectableColumns,
			DbHelper.COUNTRY_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){
		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"name" +
			" FROM " + DbHelper.TABLE_COUNTRY;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public Cursor getLastId(){
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_COUNTRY +";";
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
// reserved-for:AndroidSqliteDatabase003
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase004
	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		Cursor result = null;
		if (URI_COUNTRY_INSERT.equals(uri)) {
			result = database.insert(DbHelper.TABLE_COUNTRY, null, values);
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
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_COUNTRY_UPDATE.equals(uri)) {
			result = database.update(DbHelper.TABLE_COUNTRY, values, DbHelper.COUNTRY_ID + " = " + selectionArgs[0], null);
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
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_COUNTRY_DELETE.equals(uri)) {
			result = database.delete(DbHelper.TABLE_COUNTRY, DbHelper.COUNTRY_ID + " = " + selectionArgs[0], null);
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
		if (URI_COUNTRY_ALL.equals(uri)) {
			result = listAll();
		}
		else if(URI_COUNTRY_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if(URI_COUNTRY_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if(URI_COUNTRY_LASTID.equals(uri)) {
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
		return result;
	}
}
// reserved-for:AndroidSqliteDatabase011
// End of user code