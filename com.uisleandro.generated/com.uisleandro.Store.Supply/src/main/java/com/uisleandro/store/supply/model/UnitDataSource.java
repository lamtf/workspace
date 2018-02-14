// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.supply.model;  

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
import com.uisleandro.store.supply.view.UnitDataView;
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class UnitDataSource implements LoaderManager.LoaderCallbacks<Cursor> {

/* Yep this class implements the loaderCallbacks 

context.getloadermanager().init(FN_id, bundle, this)
I need to know about the class Bundle, which seems cary the data

*/

	public static final String AUTHORITY = "com.uisleandro.unit";
	public static final String SCHEME = "content://";

	public static final int FN_UNIT_INSERT = 998221;
	public static final int FN_UNIT_UPDATE = 998222;
	public static final int FN_UNIT_DELETE = 998223;
	public static final int FN_UNIT_ALL = 998224;
	public static final int FN_UNIT_SOME = 998225;
	public static final int FN_UNIT_BY_ID = 998226;
	public static final int FN_UNIT_LAST_ID = 998227;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	LoaderManager.LoaderCallbacks<Cursor> cursorLoader;
	public UnitDataSource (Context context) {
		this.context = context;
		cursorLoader = (LoaderManager.LoaderCallbacks<Cursor>) context; 
	}

	public List<UnitDataView> listAll () {
		List<UnitDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(UnitDataView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public UnitDataView getById (long id) {
		UnitDataView that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = UnitDataView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<UnitDataView> listSome (long page_count, long page_size) {
		List<UnitDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(UnitDataView.FromCursor(cursor));
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

	public int insert (UnitDataView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (UnitDataView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (UnitDataView that) {
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

    if (FN_UNIT_INSERT == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/insert"), null, null, null, null);
    }
    else if (FN_UNIT_UPDATE == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/update"), null, null, null, null);
    }
    else if (FN_UNIT_DELETE == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/delete"), null, null, null, null);
    }
    else if (FN_UNIT_ALL == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
    }
    else if (FN_UNIT_SOME == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ bundle.getString("page_count"), bundle.getString("page_size") }, null, null, null);
    }
    else if (FN_UNIT_BY_ID == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ bundle.getString("id") }, null);
    }
    else if (FN_UNIT_LAST_ID == i) {
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

