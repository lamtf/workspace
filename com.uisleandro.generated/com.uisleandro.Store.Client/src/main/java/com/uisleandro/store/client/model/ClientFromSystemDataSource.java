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

	public static final Integer FN_CLIENT_FROM_SYSTEM_INSERT = 998731;
	public static final Integer FN_CLIENT_FROM_SYSTEM_UPDATE = 998732;
	public static final Integer FN_CLIENT_FROM_SYSTEM_DELETE = 998733;
	public static final Integer FN_CLIENT_FROM_SYSTEM_ALL = 998734;
	public static final Integer FN_CLIENT_FROM_SYSTEM_SOME = 998735;
	public static final Integer FN_CLIENT_FROM_SYSTEM_BY_ID = 998736;
	public static final Integer FN_CLIENT_FROM_SYSTEM_LAST_ID = 998737;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	public ClientFromSystemDataSource (Context context) {
		this.context = context;
	}

	public List<ClientFromSystemView> listAll () {
		List<ClientFromSystemView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(ClientFromSystemView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public ClientFromSystemView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = ClientFromSystemView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<ClientFromSystemView> listSome (long page_count, long page_size) {
		List<ClientFromSystemView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(ClientFromSystemView.FromCursor(cursor));
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

	public int insert (ClientFromSystemView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (ClientFromSystemView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (ClientFromSystemView that) {
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
