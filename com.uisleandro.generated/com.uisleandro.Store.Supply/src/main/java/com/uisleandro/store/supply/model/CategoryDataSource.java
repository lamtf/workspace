// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.supply.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.supply.view.CategoryDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class CategoryDataSource {

	public static final String AUTHORITY = "com.uisleandro.category";
	public static final String SCHEME = "content://";

	public static final String CATEGORY_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String CATEGORY_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String CATEGORY_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String CATEGORY_ALL = SCHEME + AUTHORITY + "/all";
	public static final String CATEGORY_SOME = SCHEME + AUTHORITY + "/some";
	public static final String CATEGORY_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String CATEGORY_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public CategoryDataSource (Context context) {
		this.context = context;
	}

	public List<CategoryView> listAll () {
		List<CategoryView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(CATEGORY_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      CategoryView that = CategoryView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public CategoryView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(CATEGORY_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = CategoryView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<CategoryView listSome (long page_count, long page_size) {
		List<CategoryView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(CATEGORY_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      CategoryView that = CategoryView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(CATEGORY_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (CategoryView that) {
		context.getContentResolver().insert(CATEGORY_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (CategoryView that) {
		return context.getContentResolver().update(CATEGORY_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (CategoryView that) {
		return context.getContentResolver().delete(CATEGORY_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(CATEGORY_DELETE, null, new String[]{ String.valueOf(id) });
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
