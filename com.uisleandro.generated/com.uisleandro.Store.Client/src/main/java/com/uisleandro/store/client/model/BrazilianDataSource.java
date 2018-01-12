// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.client.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.client.view.BrazilianDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class BrazilianDataSource {

	public static final String AUTHORITY = "com.uisleandro.brazilian";
	public static final String SCHEME = "content://";

	public static final String BRAZILIAN_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String BRAZILIAN_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String BRAZILIAN_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String BRAZILIAN_ALL = SCHEME + AUTHORITY + "/all";
	public static final String BRAZILIAN_SOME = SCHEME + AUTHORITY + "/some";
	public static final String BRAZILIAN_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String BRAZILIAN_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public BrazilianDataSource(Context context){
		this.context = context;
	}

	public List<BrazilianView> listAll(){
		List<BrazilianView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(BRAZILIAN_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      BrazilianView that = BrazilianView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public BrazilianView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(BRAZILIAN_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = BrazilianView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<BrazilianView listSome(long page_count, long page_size){
		List<BrazilianView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(BRAZILIAN_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      BrazilianView that = BrazilianView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(BRAZILIAN_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(BrazilianView that) {
		context.getContentResolver().insert(BRAZILIAN_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(BrazilianView that) {
		return context.getContentResolver().update(BRAZILIAN_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(BrazilianView that) {
		return context.getContentResolver().delete(BRAZILIAN_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(BRAZILIAN_DELETE, null, new String[]{ String.valueOf(id) });
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

