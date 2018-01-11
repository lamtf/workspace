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
//import com.uisleandro.store.cash.model.CashRegisterDbHelper;

import com.uisleandro.store.DbHelper;
// reserved-for:AndroidSqliteDatabase001
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle001
// reserved-for:AndroidSqliteSyncSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase002
public class CashRegisterProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.cash_register";
	public static final String SCHEME = "content://";

	public static final String CASH_REGISTER_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_CASH_REGISTER_INSERT = Uri.parse(CASH_REGISTER_INSERT);
	public static final String CASH_REGISTER_INSERT_BASE = CASH_REGISTER_INSERT + "/";

	public static final String CASH_REGISTER_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_CASH_REGISTER_UPDATE = Uri.parse(CASH_REGISTER_UPDATE);
	public static final String CASH_REGISTER_UPDATE_BASE = CASH_REGISTER_UPDATE + "/";

	public static final String CASH_REGISTER_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_CASH_REGISTER_DELETE = Uri.parse(CASH_REGISTER_DELETE);
	public static final String CASH_REGISTER_DELETE_BASE = CASH_REGISTER_DELETE + "/";

	public static final String CASH_REGISTER_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_CASH_REGISTER_ALL = Uri.parse(CASH_REGISTER_ALL);
	public static final String CASH_REGISTER_ALL_BASE = CASH_REGISTER_ALL + "/";

	public static final String CASH_REGISTER_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_CASH_REGISTER_SOME = Uri.parse(CASH_REGISTER_SOME);
	public static final String CASH_REGISTER_SOME_BASE = CASH_REGISTER_SOME + "/";

	public static final String CASH_REGISTER_BYID = SCHEME + AUTHORITY + "/byid";
	public static final Uri URI_CASH_REGISTER_BYID = Uri.parse(CASH_REGISTER_BYID);
	public static final String CASH_REGISTER_BYID_BASE = CASH_REGISTER_BYID + "/";

	public static final String CASH_REGISTER_LASTID = SCHEME + AUTHORITY + "/lastid";
	public static final Uri URI_CASH_REGISTER_LASTID = Uri.parse(CASH_REGISTER_LASTID);
	public static final String CASH_REGISTER_LASTID_BASE = CASH_REGISTER_LASTID + "/";

// reserved-for:AndroidSqliteDatabase002
// End of user code


// Start of user code reserved-for:AndroidSqliteSyncSingle002
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	public static final String CASH_REGISTER_CLOSE_CASH_REGISTER = SCHEME + AUTHORITY + "/close_cash_register";
	public static final Uri URI_CASH_REGISTER_CLOSE_CASH_REGISTER = Uri.parse(CASH_REGISTER_CLOSE_CASH_REGISTER);
	public static final String CASH_REGISTER_CLOSE_CASH_REGISTER_BASE = CASH_REGISTER_CLOSE_CASH_REGISTER + "/";
	public static final String CASH_REGISTER_CHECK_HISTORY = SCHEME + AUTHORITY + "/check_history";
	public static final Uri URI_CASH_REGISTER_CHECK_HISTORY = Uri.parse(CASH_REGISTER_CHECK_HISTORY);
	public static final String CASH_REGISTER_CHECK_HISTORY_BASE = CASH_REGISTER_CHECK_HISTORY + "/";
	public static final String CASH_REGISTER_IS_OPEN_TODAY = SCHEME + AUTHORITY + "/is_open_today";
	public static final Uri URI_CASH_REGISTER_IS_OPEN_TODAY = Uri.parse(CASH_REGISTER_IS_OPEN_TODAY);
	public static final String CASH_REGISTER_IS_OPEN_TODAY_BASE = CASH_REGISTER_IS_OPEN_TODAY + "/";
	public static final String CASH_REGISTER_EVENTUAL_CASH_USAGE = SCHEME + AUTHORITY + "/eventual_cash_usage";
	public static final Uri URI_CASH_REGISTER_EVENTUAL_CASH_USAGE = Uri.parse(CASH_REGISTER_EVENTUAL_CASH_USAGE);
	public static final String CASH_REGISTER_EVENTUAL_CASH_USAGE_BASE = CASH_REGISTER_EVENTUAL_CASH_USAGE + "/";
	public static final String CASH_REGISTER_SUM_CASH_LAUNCHES = SCHEME + AUTHORITY + "/sum_cash_launches";
	public static final Uri URI_CASH_REGISTER_SUM_CASH_LAUNCHES = Uri.parse(CASH_REGISTER_SUM_CASH_LAUNCHES);
	public static final String CASH_REGISTER_SUM_CASH_LAUNCHES_BASE = CASH_REGISTER_SUM_CASH_LAUNCHES + "/";
	public static final String CASH_REGISTER_OPEN_CASH_REGISTER = SCHEME + AUTHORITY + "/open_cash_register";
	public static final Uri URI_CASH_REGISTER_OPEN_CASH_REGISTER = Uri.parse(CASH_REGISTER_OPEN_CASH_REGISTER);
	public static final String CASH_REGISTER_OPEN_CASH_REGISTER_BASE = CASH_REGISTER_OPEN_CASH_REGISTER + "/";
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003
	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.CASH_REGISTER_ID,
		DbHelper.CASH_REGISTER_SERVER_ID,
		DbHelper.CASH_REGISTER_DIRTY,
		DbHelper.CASH_REGISTER_LAST_UPDATE, 
		DbHelper.CASH_REGISTER_FK_USER, 
		DbHelper.CASH_REGISTER_OPENING_VALUE, 
		DbHelper.CASH_REGISTER_RECEIVED_VALUE, 
		DbHelper.CASH_REGISTER_CLOSING_VALUE, 
		DbHelper.CASH_REGISTER_FK_CURRENCY
	};

	public CashRegisterDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("CashRegisterDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public Cursor listAll(){
		Cursor cursor = database.query(DbHelper.TABLE_CASH_REGISTER,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){
		Cursor cursor = database.query(DbHelper.TABLE_CASH_REGISTER,
			selectableColumns,
			DbHelper.CASH_REGISTER_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){
		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_user, " +
			"opening_value, " +
			"received_value, " +
			"closing_value, " +
			"fk_currency" +
			" FROM " + DbHelper.TABLE_CASH_REGISTER;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public Cursor getLastId(){
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_CASH_REGISTER +";";
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
		if (URI_CASH_REGISTER_INSERT.equals(uri)) {
			result = database.insert(DbHelper.TABLE_CASH_REGISTER, null, values);
		}
// reserved-for:AndroidSqliteDatabase004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle003
/* @Insert */
	else if (URI_CASH_REGISTER_eventual_cash_usage.equals(uri)) {
			result = eventual_cash_usage(selectionArgs); // << missing arguments
	}
	else if (URI_CASH_REGISTER_open_cash_register.equals(uri)) {
			result = open_cash_register(selectionArgs); // << missing arguments
	}
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
		if (URI_CASH_REGISTER_UPDATE.equals(uri)) {
			result = database.update(DbHelper.TABLE_CASH_REGISTER, values, DbHelper.CASH_REGISTER_ID + " = " + selectionArgs[0], null);
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
		if (URI_CASH_REGISTER_DELETE.equals(uri)) {
			result = database.delete(DbHelper.TABLE_CASH_REGISTER, DbHelper.CASH_REGISTER_ID + " = " + selectionArgs[0], null);
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

// end content-provider-interface

// Start of user code reserved-for:AndroidSqliteSyncSingle003
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle006
	/* @SelectListWhere */
	public Cursor check_history(String[] selectionArgs,long page_count, long page_size) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT cash_register.last_update,cash_register.opening_value,cash_register.received_value,cash_register.closing_value,user.last_update,user.username,user.password,user.name,user.email,user.last_use_time,user.last_error_time,user.error_count,user.active,role.last_update,role.name,system.last_update,system.name,system.enabled,currency.last_update,currency.abbreviature,currency.description FROM cash_register INNER JOIN user ON cash_register.fk_user = user.id INNER JOIN system ON user.fk_system = system.id INNER JOIN role ON user.fk_role = role.id INNER JOIN currency ON system.fk_currency = currency.id WHERE cash_register.last_update = ?;";
		if(page_size > 0){
				query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		Cursor cursor = database.rawQuery(query, selectionArgs);
		return cursor;
	}
	/* @ExistsWhere */
	public Cursor is_open_today(String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT exists(*) FROM cash_register WHERE cash_register.last_update = ? AND cash_register.user = ? AND cash_register.opening_value > ? AND cash_register.closing_value = ?;";
		Cursor cursor = database.rawQuery(query, new String[]{ com.uisleandro.util.config.getTodayString(), com.uisleandro.util.config.getUserIdString(), "0", "0" });
		return cursor;
	}
	/* @Insert */
	public int eventual_cash_usage(String[] selectionArgs){
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "INSERT INTO cash_launch(last_update,fk_cash_register,justification,amount_spent) VALUES (?,?,?,?);";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		//TODO: I don't knwo if its returning the last_id, I guess it's not
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	}
	/* @SelectValueWhere */
	public Cursor sum_cash_launches(String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT cash_launch.amount_spent FROM cash_launch WHERE cash_launch.fk_cash_register = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		return cursor;
	}
	/* @Insert */
	public int open_cash_register(String[] selectionArgs){
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "INSERT INTO cash_register(last_update,user,opening_value,received_value,closing_value) VALUES (?,?,?,?,?);";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		//TODO: I don't knwo if its returning the last_id, I guess it's not
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	}
// reserved-for:AndroidSqliteQuerySingle006
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase010
	// TODO: I NEED TO KNOW HOW TO MAKE VARIOUS QUERIES DEPENDING ON THE URI
	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Cursor result = null;
		if (URI_CASH_REGISTER_ALL.equals(uri)) {
			result = listAll();
		}
		else if(URI_CASH_REGISTER_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if(URI_CASH_REGISTER_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if(URI_CASH_REGISTER_LASTID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:AndroidSqliteDatabase010
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle004
// reserved-for:AndroidSqliteSyncSingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle007
/* @ExistsWhere||@SelectValueWhere||@SelectOneWhere||@SelectListWhere */
	else if (URI_CASH_REGISTER_CHECK_HISTORY.equals(uri)) {
		result = check_history(selectionArgs);
	}
	else if (URI_CASH_REGISTER_IS_OPEN_TODAY.equals(uri)) {
		result = is_open_today(selectionArgs);
	}
	else if (URI_CASH_REGISTER_SUM_CASH_LAUNCHES.equals(uri)) {
		result = sum_cash_launches(selectionArgs);
	}
// Start of user code reserved-for:AndroidSqliteQuerySingle007

// Start of user code reserved-for:AndroidSqliteDatabase011
		return result;
	}
}
// reserved-for:AndroidSqliteDatabase011
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase011
		return result;
	}
}
// reserved-for:AndroidSqliteDatabase011
// End of user code

