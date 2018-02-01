// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.referral.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.referral.view.ResellerDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class ResellerDataSource {

	public static final String AUTHORITY = "com.uisleandro.reseller";
	public static final String SCHEME = "content://";

	public static final String RESELLER_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String RESELLER_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String RESELLER_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String RESELLER_ALL = SCHEME + AUTHORITY + "/all";
	public static final String RESELLER_SOME = SCHEME + AUTHORITY + "/some";
	public static final String RESELLER_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String RESELLER_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public ResellerDataSource (Context context) {
		this.context = context;
	}

	public List<ResellerView> listAll () {
		List<ResellerView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(RESELLER_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(ResellerView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public ResellerView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(RESELLER_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = ResellerView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<ResellerView> listSome (long page_count, long page_size) {
		List<ResellerView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(RESELLER_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(ResellerView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(RESELLER_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (ResellerView that) {
		context.getContentResolver().insert(RESELLER_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (ResellerView that) {
		return context.getContentResolver().update(RESELLER_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (ResellerView that) {
		return context.getContentResolver().delete(RESELLER_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(RESELLER_DELETE, null, new String[]{ String.valueOf(id) });
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
