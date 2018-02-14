// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.core.model;  

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//old
//import com.uisleandro.util.LongDateFormatter;
//import com.uisleandro.store.model.DbHelper;
//import com.uisleandro.store.core.model.UserDbHelper;

import com.uisleandro.store.DbHelper;
// reserved-for:AndroidSqliteDatabase001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001import com.uisleandro.store.core.view.LoginOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase002
public class UserProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.user";
	public static final String SCHEME = "content://";
	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	
	public static final int USER_INSERT_NUMBER = 1;
	public static final String USER_INSERT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/insert";
	public static final String USER_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_USER_INSERT = Uri.parse(USER_INSERT);
	public static final String USER_INSERT_BASE = USER_INSERT + "/";

	public static final int USER_UPDATE_NUMBER = 2;
	public static final String USER_UPDATE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/update";
	public static final String USER_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_USER_UPDATE = Uri.parse(USER_UPDATE);
	public static final String USER_UPDATE_BASE = USER_UPDATE + "/";

	public static final int USER_DELETE_NUMBER = 3;
	public static final String USER_DELETE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/delete";
	public static final String USER_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_USER_DELETE = Uri.parse(USER_DELETE);
	public static final String USER_DELETE_BASE = USER_DELETE + "/";

	public static final int USER_ALL_NUMBER = 4;
	public static final String USER_ALL_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/all";
	public static final String USER_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_USER_ALL = Uri.parse(USER_ALL);
	public static final String USER_ALL_BASE = USER_ALL + "/";

	public static final int USER_SOME_NUMBER = 5;
	public static final String USER_SOME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/some";
	public static final String USER_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_USER_SOME = Uri.parse(USER_SOME);
	public static final String USER_SOME_BASE = USER_SOME + "/";

	public static final int USER_BY_ID_NUMBER = 6;
	public static final String USER_BY_ID_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/by_id";
	public static final String USER_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final Uri URI_USER_BY_ID = Uri.parse(USER_BY_ID);
	public static final String USER_BY_ID_BASE = USER_BY_ID + "/";

	public static final int USER_LAST_ID_NUMBER = 7;
	public static final String USER_LAST_ID_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/last_id";
	public static final String USER_LAST_ID = SCHEME + AUTHORITY + "/last_id";
	public static final Uri URI_USER_LAST_ID = Uri.parse(USER_LAST_ID);
	public static final String USER_LAST_ID_BASE = USER_LAST_ID + "/";


// reserved-for:AndroidSqliteDatabase002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	public static final int USER_USER_CAN_ACCESS_NUMBER = 8;
	public static final String USER_USER_CAN_ACCESS_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/user_can_access";
	public static final String USER_USER_CAN_ACCESS = SCHEME + AUTHORITY + "/user_can_access";
	public static final Uri URI_USER_USER_CAN_ACCESS = Uri.parse(USER_USER_CAN_ACCESS);
	public static final String USER_USER_CAN_ACCESS_BASE = USER_USER_CAN_ACCESS + "/";

	public static final int USER_LOGIN_NUMBER = 9;
	public static final String USER_LOGIN_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/login";
	public static final String USER_LOGIN = SCHEME + AUTHORITY + "/login";
	public static final Uri URI_USER_LOGIN = Uri.parse(USER_LOGIN);
	public static final String USER_LOGIN_BASE = USER_LOGIN + "/";

// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003

	static {
		MATCHER.addURI(AUTHORITY,"insert", USER_INSERT_NUMBER);
		MATCHER.addURI(AUTHORITY,"update", USER_UPDATE_NUMBER);
		MATCHER.addURI(AUTHORITY,"delete", USER_DELETE_NUMBER);
		MATCHER.addURI(AUTHORITY,"all", USER_ALL_NUMBER);
		MATCHER.addURI(AUTHORITY,"some", USER_SOME_NUMBER);
		MATCHER.addURI(AUTHORITY,"by_id", USER_BY_ID_NUMBER);
		MATCHER.addURI(AUTHORITY,"last_id", USER_LAST_ID_NUMBER);
// reserved-for:AndroidSqliteDatabase003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.1
		MATCHER.addURI(AUTHORITY,"user_can_access", USER_USER_CAN_ACCESS_NUMBER);
		MATCHER.addURI(AUTHORITY,"login", USER_LOGIN_NUMBER);
// reserved-for:AndroidSqliteQuerySingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003.1
	}

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[] { 
		DbHelper.USER_ID,
		DbHelper.USER_SERVER_ID,
		DbHelper.USER_DIRTY,
		DbHelper.USER_LAST_UPDATE, 
		DbHelper.USER_FK_SYSTEM, 
		DbHelper.USER_FK_ROLE, 
		DbHelper.USER_USERNAME, 
		DbHelper.USER_PASSWORD, 
		DbHelper.USER_NAME, 
		DbHelper.USER_EMAIL, 
		DbHelper.USER_LAST_USE_TIME, 
		DbHelper.USER_LAST_ERROR_TIME, 
		DbHelper.USER_ERROR_COUNT, 
		DbHelper.USER_ACTIVE
	};

	public UserProvider (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("UserDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public Cursor listAll () {
		Cursor cursor = database.query(DbHelper.TABLE_USER,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById (long id) {
		Cursor cursor = database.query(DbHelper.TABLE_USER,
			selectableColumns,
			DbHelper.USER_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome (long page_count, long page_size) {
		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_system, " +
			"fk_role, " +
			"username, " +
			"password, " +
			"name, " +
			"email, " +
			"last_use_time, " +
			"last_error_time, " +
			"error_count, " +
			"active" +
			" FROM " + DbHelper.TABLE_USER;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public Cursor getLastId () {
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_USER +";";
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

		switch (MATCHER.match(uri)){
			case USER_INSERT_NUMBER:
				return USER_INSERT_TYPE;
			case USER_UPDATE_NUMBER:
				return USER_UPDATE_TYPE;
			case USER_DELETE_NUMBER:
				return USER_DELETE_TYPE;
			case USER_ALL_NUMBER:
				return USER_ALL_TYPE;
			case USER_SOME_NUMBER:
				return USER_SOME_TYPE;
			case USER_BY_ID_NUMBER:
				return USER_BY_ID_TYPE;
			case USER_LAST_ID_NUMBER:
				return USER_LAST_ID_TYPE;
// reserved-for:AndroidSqliteDatabase003.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.2
			case USER_USER_CAN_ACCESS_NUMBER:
				return USER_USER_CAN_ACCESS_TYPE;
			case USER_LOGIN_NUMBER:
				return USER_LOGIN_TYPE;
// reserved-for:AndroidSqliteQuerySingle002.2
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003.2
		}
		return null;
	}
// reserved-for:AndroidSqliteDatabase003.2
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase004
	@Nullable
	@Override
	public Uri insert (@NonNull Uri uri, @Nullable ContentValues values) {
		Cursor result = null;
		if (URI_USER_INSERT.equals(uri)) {
			result = database.insert(DbHelper.TABLE_USER, null, values);
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
		if (URI_USER_UPDATE.equals(uri)) {
			result = database.update(DbHelper.TABLE_USER, values, DbHelper.USER_ID + " = " + selectionArgs[0], null);
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
		if (URI_USER_DELETE.equals(uri)) {
			result = database.delete(DbHelper.TABLE_USER, DbHelper.USER_ID + " = " + selectionArgs[0], null);
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

// Start of user code reserved-for:AndroidSqliteQuerySingle006
	/* @ExistsWhere */
	public Cursor user_can_access (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT exists(*) FROM user INNER JOIN system ON user.fk_system = system.id INNER JOIN role ON user.fk_role = role.id WHERE user.username = ? AND system.enabled = ? AND system.id = ? AND role.name = ?;";
		Cursor cursor = database.rawQuery(query, new String[]{selectionArgs[0], "1", com.uisleandro.util.config.getSystemIdString(), selectionArgs[1]});
		return cursor;
	}
	/* @SelectOneWhere */
	public Cursor login (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT user.last_update,user.username,user.password,user.name,user.email,user.last_use_time,user.last_error_time,user.error_count,user.active,role.last_update,role.name,system.last_update,system.name,system.enabled,currency.last_update,currency.abbreviature,currency.description FROM user INNER JOIN system ON user.fk_system = system.id INNER JOIN role ON user.fk_role = role.id INNER JOIN currency ON system.fk_currency = currency.id WHERE user.fk_system = ? AND user.username = ? AND user.password = ? AND user.error_count < ? AND user.active = ?;";
		Cursor cursor = database.rawQuery(query, new String[]{com.uisleandro.util.config.getSystemIdString(), selectionArgs[0], selectionArgs[1], "3", "1"});
		return cursor;
	}
// reserved-for:AndroidSqliteQuerySingle006
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase010
	// TODO: I NEED TO KNOW HOW TO MAKE VARIOUS QUERIES DEPENDING ON THE URI
	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Cursor result = null;
		if (URI_USER_ALL.equals(uri)) {
			result = listAll();
		}
		else if (URI_USER_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if (URI_USER_BY_ID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if (URI_USER_LAST_ID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:AndroidSqliteDatabase010
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle007
/* @ExistsWhere||@SelectValueWhere||@SelectOneWhere||@SelectListWhere */
	else if (URI_USER_USER_CAN_ACCESS.equals(uri)) {
		result = user_can_access(selectionArgs);
	}
	else if (URI_USER_LOGIN.equals(uri)) {
		result = login(selectionArgs);
	}
// Start of user code reserved-for:AndroidSqliteQuerySingle007

// Start of user code reserved-for:AndroidSqliteDatabase011
		return result;
	}
}
// reserved-for:AndroidSqliteDatabase011
// End of user code
