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

	public static final Integer FN_UNIT_INSERT = 998221;
	public static final Integer FN_UNIT_UPDATE = 998222;
	public static final Integer FN_UNIT_DELETE = 998223;
	public static final Integer FN_UNIT_ALL = 998224;
	public static final Integer FN_UNIT_SOME = 998225;
	public static final Integer FN_UNIT_BY_ID = 998226;
	public static final Integer FN_UNIT_LAST_ID = 998227;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	public UnitDataSource (Context context) {
		this.context = context;
	}

	public List<UnitView> listAll () {
		List<UnitView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
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
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
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
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
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
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/last_id", null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (UnitView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (UnitView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (UnitView that) {
		return context.getContentResolver().delete(SCHEME + AUTHORITY + "/delete", null, new String[]{ String.valueOf(that.getId()) });
	}

// reserved-for:AndroidSqliteDatabaseSingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle003
}
// reserved-for:AndroidSqliteDatabaseSingle003
// End of user code
