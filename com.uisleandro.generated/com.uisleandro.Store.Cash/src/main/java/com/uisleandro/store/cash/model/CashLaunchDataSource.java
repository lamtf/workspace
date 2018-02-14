// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.cash.model;  

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

import com.uisleandro.util.LoaderInterface;
import com.uisleandro.store.cash.view.CashLaunchDataView;
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class CashLaunchDataSource implements LoaderManager.LoaderCallbacks<Cursor> {

/* Yep this class implements the loaderCallbacks 

context.getloadermanager().init(FN_id, bundle, this)
I need to know about the class Bundle, which seems cary the data

*/

	public static final String AUTHORITY = "com.uisleandro.cash_launch";
	public static final String SCHEME = "content://";

	public static final int FN_CASH_LAUNCH_INSERT = 998121;
	public static final int FN_CASH_LAUNCH_UPDATE = 998122;
	public static final int FN_CASH_LAUNCH_DELETE = 998123;
	public static final int FN_CASH_LAUNCH_ALL = 998124;
	public static final int FN_CASH_LAUNCH_SOME = 998125;
	public static final int FN_CASH_LAUNCH_BY_ID = 998126;
	public static final int FN_CASH_LAUNCH_LAST_ID = 998127;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	LoaderManager.LoaderCallbacks<Cursor> cursorLoader;
	public CashLaunchDataSource (Context context) {
		this.context = context;
		cursorLoader = (LoaderManager.LoaderCallbacks<Cursor>) context; 
	}

	public List<CashLaunchDataView> listAll () {
		List<CashLaunchDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(CashLaunchDataView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public CashLaunchDataView getById (long id) {
		CashLaunchDataView that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = CashLaunchDataView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<CashLaunchDataView> listSome (long page_count, long page_size) {
		List<CashLaunchDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(CashLaunchDataView.FromCursor(cursor));
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

	public int insert (CashLaunchDataView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (CashLaunchDataView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (CashLaunchDataView that) {
		return context.getContentResolver().delete(SCHEME + AUTHORITY + "/delete", null, new String[]{ String.valueOf(that.getId()) });
	}

// reserved-for:AndroidSqliteDatabaseSingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
// reserved-for:AndroidSqliteQuerySingle002
// End of user code


// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.2
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
// reserved-for:AndroidSqliteDatabaseSingle002.2
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.1

// reserved-for:AndroidSqliteQuerySingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.3
  }
// reserved-for:AndroidSqliteDatabaseSingle002.3
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.4
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

    if (FN_CASH_LAUNCH_INSERT == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/insert"), null, null, null, null);
    }
    else if (FN_CASH_LAUNCH_UPDATE == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/update"), null, null, null, null);
    }
    else if (FN_CASH_LAUNCH_DELETE == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/delete"), null, null, null, null);
    }
    else if (FN_CASH_LAUNCH_ALL == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
    }
    else if (FN_CASH_LAUNCH_SOME == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ bundle.getString("page_count"), bundle.getString("page_size") }, null, null, null);
    }
    else if (FN_CASH_LAUNCH_BY_ID == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ bundle.getString("id") }, null);
    }
    else if (FN_CASH_LAUNCH_LAST_ID == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/last_id"), null, null, null, null);
    }
// reserved-for:AndroidSqliteDatabaseSingle002.4
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.2
// reserved-for:AndroidSqliteQuerySingle002.2
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.5
  }
// reserved-for:AndroidSqliteDatabaseSingle002.5
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.6
  public Loader<Cursor> onLoaderReset(Loader<Cursor> cursorLoader) {
// reserved-for:AndroidSqliteDatabaseSingle002.6
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.3

// reserved-for:AndroidSqliteQuerySingle002.3
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.7
  }
// reserved-for:AndroidSqliteDatabaseSingle002.7
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.8
  public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
	((LoaderInterface)this.context).onLoadFinished(cursorLoader,cursor);
  }
}
// reserved-for:AndroidSqliteDatabaseSingle002.8
// End of user code

