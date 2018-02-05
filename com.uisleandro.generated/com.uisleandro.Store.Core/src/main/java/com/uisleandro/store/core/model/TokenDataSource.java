// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.core.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.core.view.TokenDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class TokenDataSource {

	public static final String AUTHORITY = "com.uisleandro.token";
	public static final String SCHEME = "content://";

	public static final Integer FN_TOKEN_INSERT = 998551;
	public static final Integer FN_TOKEN_UPDATE = 998552;
	public static final Integer FN_TOKEN_DELETE = 998553;
	public static final Integer FN_TOKEN_ALL = 998554;
	public static final Integer FN_TOKEN_SOME = 998555;
	public static final Integer FN_TOKEN_BY_ID = 998556;
	public static final Integer FN_TOKEN_LAST_ID = 998557;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	public TokenDataSource (Context context) {
		this.context = context;
	}

	public List<TokenView> listAll () {
		List<TokenView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(TokenView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public TokenView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = TokenView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<TokenView> listSome (long page_count, long page_size) {
		List<TokenView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(TokenView.FromCursor(cursor));
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

	public int insert (TokenView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (TokenView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (TokenView that) {
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
