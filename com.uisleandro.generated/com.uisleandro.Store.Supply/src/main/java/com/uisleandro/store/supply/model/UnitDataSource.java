// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.supply.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.supply.view.UnitDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class UnitDataSource {

	public static final String AUTHORITY = "com.uisleandro.unit";
	public static final String SCHEME = "content://";

	public static final String UNIT_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String UNIT_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String UNIT_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String UNIT_ALL = SCHEME + AUTHORITY + "/all";
	public static final String UNIT_SOME = SCHEME + AUTHORITY + "/some";
	public static final String UNIT_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String UNIT_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public UnitDataSource (Context context) {
		this.context = context;
	}

	public List<UnitView> listAll () {
		List<UnitView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(UNIT_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(UnitView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public UnitView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(UNIT_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = UnitView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<UnitView> listSome (long page_count, long page_size) {
		List<UnitView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(UNIT_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(UnitView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(UNIT_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (UnitView that) {
		context.getContentResolver().insert(UNIT_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (UnitView that) {
		return context.getContentResolver().update(UNIT_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (UnitView that) {
		return context.getContentResolver().delete(UNIT_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(UNIT_DELETE, null, new String[]{ String.valueOf(id) });
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
