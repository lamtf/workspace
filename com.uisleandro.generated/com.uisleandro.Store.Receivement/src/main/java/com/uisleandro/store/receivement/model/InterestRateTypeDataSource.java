// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.receivement.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.receivement.view.InterestRateTypeDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class InterestRateTypeDataSource {

	public static final String AUTHORITY = "com.uisleandro.interest_rate_type";
	public static final String SCHEME = "content://";

	public static final String INTEREST_RATE_TYPE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String INTEREST_RATE_TYPE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String INTEREST_RATE_TYPE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String INTEREST_RATE_TYPE_ALL = SCHEME + AUTHORITY + "/all";
	public static final String INTEREST_RATE_TYPE_SOME = SCHEME + AUTHORITY + "/some";
	public static final String INTEREST_RATE_TYPE_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String INTEREST_RATE_TYPE_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public InterestRateTypeDataSource (Context context) {
		this.context = context;
	}

	public List<InterestRateTypeView> listAll () {
		List<InterestRateTypeView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(INTEREST_RATE_TYPE_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      InterestRateTypeView that = InterestRateTypeView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public InterestRateTypeView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(INTEREST_RATE_TYPE_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = InterestRateTypeView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<InterestRateTypeView listSome (long page_count, long page_size) {
		List<InterestRateTypeView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(INTEREST_RATE_TYPE_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      InterestRateTypeView that = InterestRateTypeView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(INTEREST_RATE_TYPE_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (InterestRateTypeView that) {
		context.getContentResolver().insert(INTEREST_RATE_TYPE_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (InterestRateTypeView that) {
		return context.getContentResolver().update(INTEREST_RATE_TYPE_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (InterestRateTypeView that) {
		return context.getContentResolver().delete(INTEREST_RATE_TYPE_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(INTEREST_RATE_TYPE_DELETE, null, new String[]{ String.valueOf(id) });
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
