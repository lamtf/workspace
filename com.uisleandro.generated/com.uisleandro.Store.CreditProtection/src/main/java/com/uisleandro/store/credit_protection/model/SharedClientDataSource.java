// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.credit_protection.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.credit_protection.view.SharedClientView

public class SharedClientDataSource {

	public static final String AUTHORITY = "com.uisleandro.shared_client";
	public static final String SCHEME = "content://";

	public static final String SHARED_CLIENT_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String SHARED_CLIENT_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String SHARED_CLIENT_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String SHARED_CLIENT_ALL = SCHEME + AUTHORITY + "/all";
	public static final String SHARED_CLIENT_SOME = SCHEME + AUTHORITY + "/some";
	public static final String SHARED_CLIENT_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String SHARED_CLIENT_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public SharedClientDataSource(Context context){
		this.context = context;
	}

	public List<SharedClientView> listAll(){
		List<SharedClientView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SHARED_CLIENT_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      SharedClientView that = SharedClientView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public SharedClientView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SHARED_CLIENT_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = SharedClientView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<SharedClientView listSome(long page_count, long page_size){
		List<SharedClientView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SHARED_CLIENT_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      SharedClientView that = SharedClientView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(SHARED_CLIENT_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(SharedClientView that) {
		context.getContentResolver().insert(SHARED_CLIENT_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(SharedClientView that) {
		return context.getContentResolver().update(SHARED_CLIENT_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(SharedClientView that) {
		return context.getContentResolver().delete(SHARED_CLIENT_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(SHARED_CLIENT_DELETE, null, new String[]{ String.valueOf(id) });
	}

}

