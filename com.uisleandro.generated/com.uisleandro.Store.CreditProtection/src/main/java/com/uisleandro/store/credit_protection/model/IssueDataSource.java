// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.credit_protection.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.credit_protection.view.IssueDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class IssueDataSource {

	public static final String AUTHORITY = "com.uisleandro.Issue";
	public static final String SCHEME = "content://";

	public static final String ISSUE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String ISSUE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String ISSUE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String ISSUE_ALL = SCHEME + AUTHORITY + "/all";
	public static final String ISSUE_SOME = SCHEME + AUTHORITY + "/some";
	public static final String ISSUE_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String ISSUE_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public IssueDataSource (Context context) {
		this.context = context;
	}

	public List<IssueView> listAll () {
		List<IssueView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(ISSUE_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(IssueView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public IssueView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(ISSUE_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = IssueView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<IssueView> listSome (long page_count, long page_size) {
		List<IssueView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(ISSUE_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(IssueView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(ISSUE_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (IssueView that) {
		context.getContentResolver().insert(ISSUE_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (IssueView that) {
		return context.getContentResolver().update(ISSUE_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (IssueView that) {
		return context.getContentResolver().delete(ISSUE_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(ISSUE_DELETE, null, new String[]{ String.valueOf(id) });
	}
// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @ExistsWhere */
	public boolean check_client (Long fk_shared_client) {
		String selectionArgs = new String[]{ String.valueOf(fk_shared_client), "1" }; 
		Cursor cursor = context.getContentResolver().query("content://com.uisleandro.Issue/check_client",null, null, selectionArgs, null);
		boolean that = false;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = (cursor.getInt(0) > 0);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @Insert */
	public int register_issue (Long fk_shared_client, String description){
		ContentValues contentValues = new ContentValues(5);
		contentValues.put("last_update",com.uisleandro.util.config.getRightNowString());
		contentValues.put("fk_shared_client",fk_shared_client);
		contentValues.put("description",description);
		contentValues.put("active","0");
		contentValues.put("isAnswer","0");
	
		context.getContentResolver().insert("content://com.uisleandro.Issue/register_issue", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNCHRONIZED CODE
	}
	
	
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle003
}
// reserved-for:AndroidSqliteDatabaseSingle003
// End of user code
