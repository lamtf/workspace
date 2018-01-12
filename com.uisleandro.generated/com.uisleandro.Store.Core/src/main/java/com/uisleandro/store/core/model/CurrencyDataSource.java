// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.core.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.core.view.CurrencyDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class CurrencyDataSource {

	public static final String AUTHORITY = "com.uisleandro.currency";
	public static final String SCHEME = "content://";

	public static final String CURRENCY_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String CURRENCY_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String CURRENCY_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String CURRENCY_ALL = SCHEME + AUTHORITY + "/all";
	public static final String CURRENCY_SOME = SCHEME + AUTHORITY + "/some";
	public static final String CURRENCY_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String CURRENCY_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public CurrencyDataSource(Context context){
		this.context = context;
	}

	public List<CurrencyView> listAll(){
		List<CurrencyView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(CURRENCY_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      CurrencyView that = CurrencyView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public CurrencyView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(CURRENCY_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = CurrencyView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<CurrencyView listSome(long page_count, long page_size){
		List<CurrencyView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(CURRENCY_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      CurrencyView that = CurrencyView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(CURRENCY_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(CurrencyView that) {
		context.getContentResolver().insert(CURRENCY_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(CurrencyView that) {
		return context.getContentResolver().update(CURRENCY_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(CurrencyView that) {
		return context.getContentResolver().delete(CURRENCY_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(CURRENCY_DELETE, null, new String[]{ String.valueOf(id) });
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

