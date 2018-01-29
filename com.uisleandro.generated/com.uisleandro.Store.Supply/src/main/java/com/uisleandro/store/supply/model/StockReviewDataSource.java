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

	public static final String STOCK_REVIEW_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String STOCK_REVIEW_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String STOCK_REVIEW_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String STOCK_REVIEW_ALL = SCHEME + AUTHORITY + "/all";
	public static final String STOCK_REVIEW_SOME = SCHEME + AUTHORITY + "/some";
	public static final String STOCK_REVIEW_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String STOCK_REVIEW_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public StockReviewDataSource (Context context) {
		this.context = context;
	}

	public List<StockReviewView> listAll () {
		List<StockReviewView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(STOCK_REVIEW_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      StockReviewView that = StockReviewView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public StockReviewView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(STOCK_REVIEW_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = StockReviewView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<StockReviewView listSome (long page_count, long page_size) {
		List<StockReviewView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(STOCK_REVIEW_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      StockReviewView that = StockReviewView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(STOCK_REVIEW_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (StockReviewView that) {
		context.getContentResolver().insert(STOCK_REVIEW_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (StockReviewView that) {
		return context.getContentResolver().update(STOCK_REVIEW_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (StockReviewView that) {
		return context.getContentResolver().delete(STOCK_REVIEW_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(STOCK_REVIEW_DELETE, null, new String[]{ String.valueOf(id) });
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

