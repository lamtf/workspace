// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.core.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.core.view.DbLogDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class DbLogDataSource {

	public static final String AUTHORITY = "com.uisleandro.db_log";
	public static final String SCHEME = "content://";

	public static final String DB_LOG_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String DB_LOG_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String DB_LOG_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String DB_LOG_ALL = SCHEME + AUTHORITY + "/all";
	public static final String DB_LOG_SOME = SCHEME + AUTHORITY + "/some";
	public static final String DB_LOG_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String DB_LOG_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public DbLogDataSource(Context context){
		this.context = context;
	}

	public List<DbLogView> listAll(){
		List<DbLogView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(DB_LOG_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      DbLogView that = DbLogView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public DbLogView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(DB_LOG_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = DbLogView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<DbLogView listSome(long page_count, long page_size){
		List<DbLogView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(DB_LOG_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      DbLogView that = DbLogView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(DB_LOG_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(DbLogView that) {
		context.getContentResolver().insert(DB_LOG_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(DbLogView that) {
		return context.getContentResolver().update(DB_LOG_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(DbLogView that) {
		return context.getContentResolver().delete(DB_LOG_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(DB_LOG_DELETE, null, new String[]{ String.valueOf(id) });
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

