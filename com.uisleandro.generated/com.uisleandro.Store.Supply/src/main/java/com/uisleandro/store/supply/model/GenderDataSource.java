// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.supply.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.supply.view.GenderDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class GenderDataSource {

	public static final String AUTHORITY = "com.uisleandro.gender";
	public static final String SCHEME = "content://";

	public static final String GENDER_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String GENDER_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String GENDER_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String GENDER_ALL = SCHEME + AUTHORITY + "/all";
	public static final String GENDER_SOME = SCHEME + AUTHORITY + "/some";
	public static final String GENDER_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String GENDER_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public GenderDataSource (Context context) {
		this.context = context;
	}

	public List<GenderView> listAll () {
		List<GenderView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(GENDER_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(GenderView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public GenderView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(GENDER_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = GenderView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<GenderView> listSome (long page_count, long page_size) {
		List<GenderView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(GENDER_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(GenderView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(GENDER_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (GenderView that) {
		context.getContentResolver().insert(GENDER_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (GenderView that) {
		return context.getContentResolver().update(GENDER_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (GenderView that) {
		return context.getContentResolver().delete(GENDER_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(GENDER_DELETE, null, new String[]{ String.valueOf(id) });
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
