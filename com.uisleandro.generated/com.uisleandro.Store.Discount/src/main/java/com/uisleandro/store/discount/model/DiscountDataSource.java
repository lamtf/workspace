// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.discount.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.discount.view.DiscountDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class DiscountDataSource {

	public static final String AUTHORITY = "com.uisleandro.discount";
	public static final String SCHEME = "content://";

	public static final Integer FN_DISCOUNT_INSERT = 998611;
	public static final Integer FN_DISCOUNT_UPDATE = 998612;
	public static final Integer FN_DISCOUNT_DELETE = 998613;
	public static final Integer FN_DISCOUNT_ALL = 998614;
	public static final Integer FN_DISCOUNT_SOME = 998615;
	public static final Integer FN_DISCOUNT_BY_ID = 998616;
	public static final Integer FN_DISCOUNT_LAST_ID = 998617;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	public DiscountDataSource (Context context) {
		this.context = context;
	}

	public List<DiscountView> listAll () {
		List<DiscountView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(DiscountView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public DiscountView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = DiscountView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<DiscountView> listSome (long page_count, long page_size) {
		List<DiscountView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(DiscountView.FromCursor(cursor));
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

	public int insert (DiscountView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (DiscountView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (DiscountView that) {
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
