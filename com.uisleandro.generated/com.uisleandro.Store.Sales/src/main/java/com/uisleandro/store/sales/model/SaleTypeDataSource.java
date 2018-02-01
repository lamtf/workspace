// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.sales.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.sales.view.SaleTypeDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class SaleTypeDataSource {

	public static final String AUTHORITY = "com.uisleandro.sale_type";
	public static final String SCHEME = "content://";

	public static final String SALE_TYPE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String SALE_TYPE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String SALE_TYPE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String SALE_TYPE_ALL = SCHEME + AUTHORITY + "/all";
	public static final String SALE_TYPE_SOME = SCHEME + AUTHORITY + "/some";
	public static final String SALE_TYPE_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String SALE_TYPE_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public SaleTypeDataSource (Context context) {
		this.context = context;
	}

	public List<SaleTypeView> listAll () {
		List<SaleTypeView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SALE_TYPE_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(SaleTypeView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public SaleTypeView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SALE_TYPE_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = SaleTypeView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<SaleTypeView> listSome (long page_count, long page_size) {
		List<SaleTypeView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SALE_TYPE_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(SaleTypeView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(SALE_TYPE_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (SaleTypeView that) {
		context.getContentResolver().insert(SALE_TYPE_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (SaleTypeView that) {
		return context.getContentResolver().update(SALE_TYPE_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (SaleTypeView that) {
		return context.getContentResolver().delete(SALE_TYPE_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(SALE_TYPE_DELETE, null, new String[]{ String.valueOf(id) });
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
