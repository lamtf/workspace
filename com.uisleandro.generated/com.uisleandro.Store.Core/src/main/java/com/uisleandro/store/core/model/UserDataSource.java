// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.core.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.core.view.UserDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
import com.uisleandro.store.core.view.LoginOut;
import com.uisleandro.store.core.view.CanAccessOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class UserDataSource {

	public static final String AUTHORITY = "com.uisleandro.user";
	public static final String SCHEME = "content://";

	public static final String USER_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String USER_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String USER_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String USER_ALL = SCHEME + AUTHORITY + "/all";
	public static final String USER_SOME = SCHEME + AUTHORITY + "/some";
	public static final String USER_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String USER_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public UserDataSource (Context context) {
		this.context = context;
	}

	public List<UserView> listAll () {
		List<UserView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(USER_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      UserView that = UserView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public UserView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(USER_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = UserView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<UserView listSome (long page_count, long page_size) {
		List<UserView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(USER_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      UserView that = UserView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(USER_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (UserView that) {
		context.getContentResolver().insert(USER_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (UserView that) {
		return context.getContentResolver().update(USER_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (UserView that) {
		return context.getContentResolver().delete(USER_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(USER_DELETE, null, new String[]{ String.valueOf(id) });
	}
// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @SelectOneWhere */
	public LoginOut login(Long last_update, String username, String password, String name, String email, Long last_use_time, Long last_error_time, Integer error_count, Boolean active, Long last_update, String name, Boolean enabled, Long last_update, String name, Long last_update, String abbreviature, String description){
		String selectionArgs = new String[]{ String.valueOf(last_update), String.valueOf(system), String.valueOf(role), username, password, name, email, String.valueOf(last_use_time), String.valueOf(last_error_time), String.valueOf(error_count), String.valueOf(active), String.valueOf(last_update), name, String.valueOf(enabled), String.valueOf(currency), String.valueOf(last_update), name, String.valueOf(last_update), abbreviature, description }; 
		Cursor cursor = context.getContentResolver().query("content://com.uisleandro.user/login",null, null, selectionArgs, null);
		LoginOut that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = LoginOut.FromCursor(cursor);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @SelectOneWhere */
	public CanAccessOut user_can_access(Long last_update, String username, String password, String name, String email, Long last_use_time, Long last_error_time, Integer error_count, Boolean active, Long last_update, String name, Boolean enabled, Long last_update, String name, Long last_update, String abbreviature, String description){
		String selectionArgs = new String[]{ String.valueOf(last_update), String.valueOf(system), String.valueOf(role), username, password, name, email, String.valueOf(last_use_time), String.valueOf(last_error_time), String.valueOf(error_count), String.valueOf(active), String.valueOf(last_update), name, String.valueOf(enabled), String.valueOf(currency), String.valueOf(last_update), name, String.valueOf(last_update), abbreviature, description }; 
		Cursor cursor = context.getContentResolver().query("content://com.uisleandro.user/user_can_access",null, null, selectionArgs, null);
		CanAccessOut that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = CanAccessOut.FromCursor(cursor);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle003
}
// reserved-for:AndroidSqliteDatabaseSingle003
// End of user code
