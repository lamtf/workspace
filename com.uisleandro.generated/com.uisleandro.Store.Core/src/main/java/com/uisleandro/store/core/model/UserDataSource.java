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
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class UserDataSource {

	public static final String AUTHORITY = "com.uisleandro.user";
	public static final String SCHEME = "content://";

	public static final Integer FN_USER_INSERT = 998541;
	public static final Integer FN_USER_UPDATE = 998542;
	public static final Integer FN_USER_DELETE = 998543;
	public static final Integer FN_USER_ALL = 998544;
	public static final Integer FN_USER_SOME = 998545;
	public static final Integer FN_USER_BY_ID = 998546;
	public static final Integer FN_USER_LAST_ID = 998547;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
	public static final Integer FN_USER_USER_CAN_ACCESS = 999511;
	public static final Integer FN_USER_LOGIN = 999512;
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	public UserDataSource (Context context) {
		this.context = context;
	}

	public List<UserView> listAll () {
		List<UserView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(UserView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public UserView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = UserView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<UserView> listSome (long page_count, long page_size) {
		List<UserView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(UserView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/last_id", null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (UserView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (UserView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (UserView that) {
		return context.getContentResolver().delete(SCHEME + AUTHORITY + "/delete", null, new String[]{ String.valueOf(that.getId()) });
	}

// reserved-for:AndroidSqliteDatabaseSingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @ExistsWhere */
	public boolean user_can_access (String username, String name) {
		String selectionArgs = new String[]{ String.valueOf(system), String.valueOf(role), username, "1", com.uisleandro.util.config.getSystemIdString(), name }; 
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/user_can_access",null, null, selectionArgs, null);
		boolean that = false;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = (cursor.getInt(0) > 0);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @SelectOneWhere */
	public LoginOut login(String username, String password){
		String selectionArgs = new String[]{ com.uisleandro.util.config.getSystemIdString(), username, password, "3", "1" }; 
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/login",null, null, selectionArgs, null);
		LoginOut that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = LoginOut.FromCursor(cursor);
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
