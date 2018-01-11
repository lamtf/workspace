// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.credit_protection.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.credit_protection.view.IssueView

public class IssueDataSource {

	public static final String AUTHORITY = "com.uisleandro.Issue";
	public static final String SCHEME = "content://";

	public static final String ISSUE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String ISSUE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String ISSUE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String ISSUE_ALL = SCHEME + AUTHORITY + "/all";
	public static final String ISSUE_SOME = SCHEME + AUTHORITY + "/some";
	public static final String ISSUE_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String ISSUE_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public IssueDataSource(Context context){
		this.context = context;
	}

	public List<IssueView> listAll(){
		List<IssueView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(ISSUE_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      IssueView that = IssueView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public IssueView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(ISSUE_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = IssueView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<IssueView listSome(long page_count, long page_size){
		List<IssueView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(ISSUE_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      IssueView that = IssueView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(ISSUE_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(IssueView that) {
		context.getContentResolver().insert(ISSUE_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(IssueView that) {
		return context.getContentResolver().update(ISSUE_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(IssueView that) {
		return context.getContentResolver().delete(ISSUE_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(ISSUE_DELETE, null, new String[]{ String.valueOf(id) });
	}

}

