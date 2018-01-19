// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.core.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.core.view.SystemDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class SystemDataSource {

	public static final String AUTHORITY = "com.uisleandro.system";
	public static final String SCHEME = "content://";

	public static final String SYSTEM_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String SYSTEM_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String SYSTEM_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String SYSTEM_ALL = SCHEME + AUTHORITY + "/all";
	public static final String SYSTEM_SOME = SCHEME + AUTHORITY + "/some";
	public static final String SYSTEM_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String SYSTEM_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public SystemDataSource (Context context) {
		this.context = context;
	}

	public List<SystemView> listAll () {
		List<SystemView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SYSTEM_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      SystemView that = SystemView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public SystemView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SYSTEM_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = SystemView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<SystemView listSome (long page_count, long page_size) {
		List<SystemView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SYSTEM_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      SystemView that = SystemView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(SYSTEM_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (SystemView that) {
		context.getContentResolver().insert(SYSTEM_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (SystemView that) {
		return context.getContentResolver().update(SYSTEM_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (SystemView that) {
		return context.getContentResolver().delete(SYSTEM_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(SYSTEM_DELETE, null, new String[]{ String.valueOf(id) });
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
