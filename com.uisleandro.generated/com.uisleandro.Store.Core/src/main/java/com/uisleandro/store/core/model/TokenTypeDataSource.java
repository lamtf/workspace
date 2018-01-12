// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.core.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.core.view.TokenTypeDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class TokenTypeDataSource {

	public static final String AUTHORITY = "com.uisleandro.token_type";
	public static final String SCHEME = "content://";

	public static final String TOKEN_TYPE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String TOKEN_TYPE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String TOKEN_TYPE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String TOKEN_TYPE_ALL = SCHEME + AUTHORITY + "/all";
	public static final String TOKEN_TYPE_SOME = SCHEME + AUTHORITY + "/some";
	public static final String TOKEN_TYPE_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String TOKEN_TYPE_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public TokenTypeDataSource (Context context) {
		this.context = context;
	}

	public List<TokenTypeView> listAll () {
		List<TokenTypeView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(TOKEN_TYPE_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      TokenTypeView that = TokenTypeView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public TokenTypeView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(TOKEN_TYPE_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = TokenTypeView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<TokenTypeView listSome (long page_count, long page_size) {
		List<TokenTypeView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(TOKEN_TYPE_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      TokenTypeView that = TokenTypeView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(TOKEN_TYPE_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (TokenTypeView that) {
		context.getContentResolver().insert(TOKEN_TYPE_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (TokenTypeView that) {
		return context.getContentResolver().update(TOKEN_TYPE_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (TokenTypeView that) {
		return context.getContentResolver().delete(TOKEN_TYPE_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(TOKEN_TYPE_DELETE, null, new String[]{ String.valueOf(id) });
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
