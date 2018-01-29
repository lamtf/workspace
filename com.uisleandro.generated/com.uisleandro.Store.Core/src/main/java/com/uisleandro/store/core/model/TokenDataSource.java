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

	public static final String TOKEN_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String TOKEN_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String TOKEN_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String TOKEN_ALL = SCHEME + AUTHORITY + "/all";
	public static final String TOKEN_SOME = SCHEME + AUTHORITY + "/some";
	public static final String TOKEN_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String TOKEN_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public TokenDataSource (Context context) {
		this.context = context;
	}

	public List<TokenView> listAll () {
		List<TokenView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(TOKEN_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      TokenView that = TokenView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public TokenView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(TOKEN_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = TokenView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<TokenView listSome (long page_count, long page_size) {
		List<TokenView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(TOKEN_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      TokenView that = TokenView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(TOKEN_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (TokenView that) {
		context.getContentResolver().insert(TOKEN_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (TokenView that) {
		return context.getContentResolver().update(TOKEN_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (TokenView that) {
		return context.getContentResolver().delete(TOKEN_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(TOKEN_DELETE, null, new String[]{ String.valueOf(id) });
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

