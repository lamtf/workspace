// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.client.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.client.view.ClientFromSystemDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class ClientFromSystemDataSource {

	public static final String AUTHORITY = "com.uisleandro.client_from_system";
	public static final String SCHEME = "content://";

	public static final String CLIENT_FROM_SYSTEM_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String CLIENT_FROM_SYSTEM_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String CLIENT_FROM_SYSTEM_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String CLIENT_FROM_SYSTEM_ALL = SCHEME + AUTHORITY + "/all";
	public static final String CLIENT_FROM_SYSTEM_SOME = SCHEME + AUTHORITY + "/some";
	public static final String CLIENT_FROM_SYSTEM_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String CLIENT_FROM_SYSTEM_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public ClientFromSystemDataSource (Context context) {
		this.context = context;
	}

	public List<ClientFromSystemView> listAll () {
		List<ClientFromSystemView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(CLIENT_FROM_SYSTEM_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      ClientFromSystemView that = ClientFromSystemView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public ClientFromSystemView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(CLIENT_FROM_SYSTEM_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = ClientFromSystemView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<ClientFromSystemView listSome (long page_count, long page_size) {
		List<ClientFromSystemView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(CLIENT_FROM_SYSTEM_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      ClientFromSystemView that = ClientFromSystemView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(CLIENT_FROM_SYSTEM_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (ClientFromSystemView that) {
		context.getContentResolver().insert(CLIENT_FROM_SYSTEM_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (ClientFromSystemView that) {
		return context.getContentResolver().update(CLIENT_FROM_SYSTEM_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (ClientFromSystemView that) {
		return context.getContentResolver().delete(CLIENT_FROM_SYSTEM_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(CLIENT_FROM_SYSTEM_DELETE, null, new String[]{ String.valueOf(id) });
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
