// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.client.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.client.view.CountryDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class CountryDataSource {

	public static final String AUTHORITY = "com.uisleandro.country";
	public static final String SCHEME = "content://";

	public static final String COUNTRY_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String COUNTRY_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String COUNTRY_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String COUNTRY_ALL = SCHEME + AUTHORITY + "/all";
	public static final String COUNTRY_SOME = SCHEME + AUTHORITY + "/some";
	public static final String COUNTRY_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String COUNTRY_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public CountryDataSource (Context context) {
		this.context = context;
	}

	public List<CountryView> listAll () {
		List<CountryView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(COUNTRY_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      CountryView that = CountryView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public CountryView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(COUNTRY_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = CountryView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<CountryView listSome (long page_count, long page_size) {
		List<CountryView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(COUNTRY_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      CountryView that = CountryView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(COUNTRY_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (CountryView that) {
		context.getContentResolver().insert(COUNTRY_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (CountryView that) {
		return context.getContentResolver().update(COUNTRY_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (CountryView that) {
		return context.getContentResolver().delete(COUNTRY_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(COUNTRY_DELETE, null, new String[]{ String.valueOf(id) });
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
