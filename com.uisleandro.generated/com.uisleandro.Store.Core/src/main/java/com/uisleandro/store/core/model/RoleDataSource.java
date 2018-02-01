// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.core.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.core.view.RoleDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class RoleDataSource {

	public static final String AUTHORITY = "com.uisleandro.role";
	public static final String SCHEME = "content://";

	public static final String ROLE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String ROLE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String ROLE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String ROLE_ALL = SCHEME + AUTHORITY + "/all";
	public static final String ROLE_SOME = SCHEME + AUTHORITY + "/some";
	public static final String ROLE_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String ROLE_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public RoleDataSource (Context context) {
		this.context = context;
	}

	public List<RoleView> listAll () {
		List<RoleView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(ROLE_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(RoleView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public RoleView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(ROLE_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = RoleView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<RoleView> listSome (long page_count, long page_size) {
		List<RoleView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(ROLE_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(RoleView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(ROLE_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (RoleView that) {
		context.getContentResolver().insert(ROLE_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (RoleView that) {
		return context.getContentResolver().update(ROLE_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (RoleView that) {
		return context.getContentResolver().delete(ROLE_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(ROLE_DELETE, null, new String[]{ String.valueOf(id) });
	}
// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle003
}
// reserved-for:AndroidSqliteDatabaseSingle003
// End of user code
