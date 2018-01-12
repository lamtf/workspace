// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.cash.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.cash.view.CashLaunchDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class CashLaunchDataSource {

	public static final String AUTHORITY = "com.uisleandro.cash_launch";
	public static final String SCHEME = "content://";

	public static final String CASH_LAUNCH_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String CASH_LAUNCH_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String CASH_LAUNCH_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String CASH_LAUNCH_ALL = SCHEME + AUTHORITY + "/all";
	public static final String CASH_LAUNCH_SOME = SCHEME + AUTHORITY + "/some";
	public static final String CASH_LAUNCH_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String CASH_LAUNCH_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public CashLaunchDataSource (Context context) {
		this.context = context;
	}

	public List<CashLaunchView> listAll () {
		List<CashLaunchView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(CASH_LAUNCH_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      CashLaunchView that = CashLaunchView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public CashLaunchView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(CASH_LAUNCH_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = CashLaunchView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<CashLaunchView listSome (long page_count, long page_size) {
		List<CashLaunchView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(CASH_LAUNCH_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      CashLaunchView that = CashLaunchView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(CASH_LAUNCH_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (CashLaunchView that) {
		context.getContentResolver().insert(CASH_LAUNCH_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (CashLaunchView that) {
		return context.getContentResolver().update(CASH_LAUNCH_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (CashLaunchView that) {
		return context.getContentResolver().delete(CASH_LAUNCH_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(CASH_LAUNCH_DELETE, null, new String[]{ String.valueOf(id) });
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
