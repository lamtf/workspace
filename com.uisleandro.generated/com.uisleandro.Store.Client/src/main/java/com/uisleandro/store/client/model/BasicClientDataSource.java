// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.client.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.client.view.BasicClientDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class BasicClientDataSource {

	public static final String AUTHORITY = "com.uisleandro.basic_client";
	public static final String SCHEME = "content://";

	public static final String BASIC_CLIENT_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String BASIC_CLIENT_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String BASIC_CLIENT_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String BASIC_CLIENT_ALL = SCHEME + AUTHORITY + "/all";
	public static final String BASIC_CLIENT_SOME = SCHEME + AUTHORITY + "/some";
	public static final String BASIC_CLIENT_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String BASIC_CLIENT_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public BasicClientDataSource(Context context){
		this.context = context;
	}

	public List<BasicClientView> listAll(){
		List<BasicClientView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(BASIC_CLIENT_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      BasicClientView that = BasicClientView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public BasicClientView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(BASIC_CLIENT_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = BasicClientView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<BasicClientView listSome(long page_count, long page_size){
		List<BasicClientView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(BASIC_CLIENT_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      BasicClientView that = BasicClientView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(BASIC_CLIENT_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(BasicClientView that) {
		context.getContentResolver().insert(BASIC_CLIENT_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(BasicClientView that) {
		return context.getContentResolver().update(BASIC_CLIENT_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(BasicClientView that) {
		return context.getContentResolver().delete(BASIC_CLIENT_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(BASIC_CLIENT_DELETE, null, new String[]{ String.valueOf(id) });
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

