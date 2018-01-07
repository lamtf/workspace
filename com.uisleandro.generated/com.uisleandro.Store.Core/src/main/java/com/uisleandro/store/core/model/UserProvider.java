// Start of user code reserved-for:AndroidSqliteDatabase001
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
//import com.uisleandro.store.core.model.UserDbHelper;

import com.uisleandro.store.DbHelper;
// reserved-for:AndroidSqliteDatabase001
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle001
// reserved-for:AndroidSqliteSyncSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001import com.uisleandro.store.core.view.LoginOut;
import com.uisleandro.store.core.view.CanAccessOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase002
public class UserProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.user";
	public static final String SCHEME = "content://";

	public static final String USER_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_USER_INSERT = Uri.parse(USER_INSERT);
	public static final String USER_INSERT_BASE = USER_INSERT + "/";

	public static final String USER_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_USER_UPDATE = Uri.parse(USER_UPDATE);
	public static final String USER_UPDATE_BASE = USER_UPDATE + "/";

	public static final String USER_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_USER_DELETE = Uri.parse(USER_DELETE);
	public static final String USER_DELETE_BASE = USER_DELETE + "/";

	public static final String USER_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_USER_ALL = Uri.parse(USER_ALL);
	public static final String USER_ALL_BASE = USER_ALL + "/";

	public static final String USER_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_USER_SOME = Uri.parse(USER_SOME);
	public static final String USER_SOME_BASE = USER_SOME + "/";

	public static final String USER_BYID = SCHEME + AUTHORITY + "/byid";
	public static final Uri URI_USER_BYID = Uri.parse(USER_BYID);
	public static final String USER_BYID_BASE = USER_BYID + "/";

	public static final String USER_LASTID = SCHEME + AUTHORITY + "/lastid";
	public static final Uri URI_USER_LASTID = Uri.parse(USER_LASTID);
	public static final String USER_LASTID_BASE = USER_LASTID + "/";

// reserved-for:AndroidSqliteDatabase002
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle002
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	public static final String USER_LOGIN = SCHEME + AUTHORITY + "/login";
	public static final Uri URI_USER_LOGIN = Uri.parse(USER_LOGIN);
	public static final String USER_LOGIN_BASE = USER_LOGIN + "/";
	public static final String USER_USER_CAN_ACCESS = SCHEME + AUTHORITY + "/user_can_access";
	public static final Uri URI_USER_USER_CAN_ACCESS = Uri.parse(USER_USER_CAN_ACCESS);
	public static final String USER_USER_CAN_ACCESS_BASE = USER_USER_CAN_ACCESS + "/";
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003
	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
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

	public UserDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("UserDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public Cursor listAll(){
		Cursor cursor = database.query(DbHelper.TABLE_USER,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){
		Cursor cursor = database.query(DbHelper.TABLE_USER,
			selectableColumns,
			DbHelper.USER_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){
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

	public Cursor getLastId(){
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_USER +";";
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
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
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
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
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

// Start of user code reserved-for:AndroidSqliteSyncSingle003
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle006
	/* @SelectOneWhere */
	public Cursor login(String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT user.last_update,user.username,user.password,user.name,user.email,user.last_use_time,user.last_error_time,user.error_count,user.active,role.last_update,role.name,system.last_update,system.name,system.enabled,currency.last_update,currency.abbreviature,currency.description FROM user INNER JOIN system ON user.fk_system = system.id INNER JOIN role ON user.fk_role = role.id INNER JOIN currency ON system.fk_currency = currency.id WHERE user.last_update = ? AND user.fk_system = ? AND user.fk_role = ? AND user.username = ? AND user.password = ? AND user.name = ? AND user.email = ? AND user.last_use_time = ? AND user.last_error_time = ? AND user.error_count = ? AND user.active = ? AND system.last_update = ? AND system.name = ? AND system.enabled = ? AND system.fk_currency = ? AND role.last_update = ? AND role.name = ? AND currency.last_update = ? AND currency.abbreviature = ? AND currency.description = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		return cursor;
	}
	/* @SelectOneWhere */
	public Cursor user_can_access(String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT user.last_update,user.username,user.password,user.name,user.email,user.last_use_time,user.last_error_time,user.error_count,user.active,role.last_update,role.name,system.last_update,system.name,system.enabled,currency.last_update,currency.abbreviature,currency.description FROM user INNER JOIN system ON user.fk_system = system.id INNER JOIN role ON user.fk_role = role.id INNER JOIN currency ON system.fk_currency = currency.id WHERE user.last_update = ? AND user.fk_system = ? AND user.fk_role = ? AND user.username = ? AND user.password = ? AND user.name = ? AND user.email = ? AND user.last_use_time = ? AND user.last_error_time = ? AND user.error_count = ? AND user.active = ? AND system.last_update = ? AND system.name = ? AND system.enabled = ? AND system.fk_currency = ? AND role.last_update = ? AND role.name = ? AND currency.last_update = ? AND currency.abbreviature = ? AND currency.description = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
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
		else if(URI_USER_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if(URI_USER_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if(URI_USER_LASTID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:AndroidSqliteDatabase010
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle004
// reserved-for:AndroidSqliteSyncSingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle007
/* @ExistsWhere||@SelectValueWhere||@SelectOneWhere||@SelectListWhere */
	else if (URI_USER_LOGIN.equals(uri)) {
		result = login(selectionArgs);
	}
	else if (URI_USER_USER_CAN_ACCESS.equals(uri)) {
		result = user_can_access(selectionArgs);
	}
// Start of user code reserved-for:AndroidSqliteQuerySingle007

// Start of user code reserved-for:AndroidSqliteDatabase011
// reserved-for:AndroidSqliteDatabase011
// End of user code
