// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.supply.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.supply.view.DistributorContactDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class DistributorContactDataSource {

	public static final String AUTHORITY = "com.uisleandro.distributor_contact";
	public static final String SCHEME = "content://";

	public static final String DISTRIBUTOR_CONTACT_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String DISTRIBUTOR_CONTACT_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String DISTRIBUTOR_CONTACT_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String DISTRIBUTOR_CONTACT_ALL = SCHEME + AUTHORITY + "/all";
	public static final String DISTRIBUTOR_CONTACT_SOME = SCHEME + AUTHORITY + "/some";
	public static final String DISTRIBUTOR_CONTACT_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String DISTRIBUTOR_CONTACT_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public DistributorContactDataSource (Context context) {
		this.context = context;
	}

	public List<DistributorContactView> listAll () {
		List<DistributorContactView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(DISTRIBUTOR_CONTACT_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      DistributorContactView that = DistributorContactView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public DistributorContactView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(DISTRIBUTOR_CONTACT_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = DistributorContactView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<DistributorContactView listSome (long page_count, long page_size) {
		List<DistributorContactView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(DISTRIBUTOR_CONTACT_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      DistributorContactView that = DistributorContactView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(DISTRIBUTOR_CONTACT_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (DistributorContactView that) {
		context.getContentResolver().insert(DISTRIBUTOR_CONTACT_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (DistributorContactView that) {
		return context.getContentResolver().update(DISTRIBUTOR_CONTACT_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (DistributorContactView that) {
		return context.getContentResolver().delete(DISTRIBUTOR_CONTACT_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(DISTRIBUTOR_CONTACT_DELETE, null, new String[]{ String.valueOf(id) });
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
