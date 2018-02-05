// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.supply.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.supply.view.StockReviewDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class StockReviewDataSource {

	public static final String AUTHORITY = "com.uisleandro.stock_review";
	public static final String SCHEME = "content://";

	public static final Integer FN_STOCK_REVIEW_INSERT = 998211;
	public static final Integer FN_STOCK_REVIEW_UPDATE = 998212;
	public static final Integer FN_STOCK_REVIEW_DELETE = 998213;
	public static final Integer FN_STOCK_REVIEW_ALL = 998214;
	public static final Integer FN_STOCK_REVIEW_SOME = 998215;
	public static final Integer FN_STOCK_REVIEW_BY_ID = 998216;
	public static final Integer FN_STOCK_REVIEW_LAST_ID = 998217;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	public StockReviewDataSource (Context context) {
		this.context = context;
	}

	public List<StockReviewView> listAll () {
		List<StockReviewView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(StockReviewView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public StockReviewView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = StockReviewView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<StockReviewView> listSome (long page_count, long page_size) {
		List<StockReviewView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(StockReviewView.FromCursor(cursor));
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

	public int insert (StockReviewView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (StockReviewView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (StockReviewView that) {
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
