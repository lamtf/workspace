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
import com.uisleandro.store.cash.view.CashRegisterDataView;
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
import com.uisleandro.store.cash.view.CheckHistoryOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class CashRegisterDataSource implements LoaderManager.LoaderCallbacks<Cursor> {

/* Yep this class implements the loaderCallbacks 

context.getloadermanager().init(FN_id, bundle, this)
I need to know about the class Bundle, which seems cary the data

*/

	public static final String AUTHORITY = "com.uisleandro.cash_register";
	public static final String SCHEME = "content://";

	public static final int FN_CASH_REGISTER_INSERT = 998111;
	public static final int FN_CASH_REGISTER_UPDATE = 998112;
	public static final int FN_CASH_REGISTER_DELETE = 998113;
	public static final int FN_CASH_REGISTER_ALL = 998114;
	public static final int FN_CASH_REGISTER_SOME = 998115;
	public static final int FN_CASH_REGISTER_BY_ID = 998116;
	public static final int FN_CASH_REGISTER_LAST_ID = 998117;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
	public static final int FN_CASH_REGISTER_IS_OPEN_TODAY = 999111;
	public static final int FN_CASH_REGISTER_SUM_CASH_LAUNCHES = 999112;
	public static final int FN_CASH_REGISTER_CHECK_HISTORY = 999113;
	public static final int FN_CASH_REGISTER_CLOSE_CASH_REGISTER = 999114;
	public static final int FN_CASH_REGISTER_OPEN_CASH_REGISTER = 999115;
	public static final int FN_CASH_REGISTER_EVENTUAL_CASH_USAGE = 999116;
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	LoaderManager.LoaderCallbacks<Cursor> cursorLoader;
	public CashRegisterDataSource (Context context) {
		this.context = context;
		cursorLoader = (LoaderManager.LoaderCallbacks<Cursor>) context; 
	}

	public List<CashRegisterDataView> listAll () {
		List<CashRegisterDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(CashRegisterDataView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public CashRegisterDataView getById (long id) {
		CashRegisterDataView that = null;
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/by_id"), null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = CashRegisterDataView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<CashRegisterDataView> listSome (long page_count, long page_size) {
		List<CashRegisterDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(CashRegisterDataView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/last_id"), null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0);
		    }
		}
	    return result;	
	}

	public Uri insert (CashRegisterDataView that) {
		Uri result = context.getContentResolver().insert(Uri.parse(SCHEME + AUTHORITY + "/insert"), that.toInsertValues());
		return result;
	}

	public int update (CashRegisterDataView that) {
		return context.getContentResolver().update(Uri.parse(SCHEME + AUTHORITY + "/update"), that.toUpdateValues(), null, new String[]{ String.valueOf(that.getId()) });
	}

	public int delete (CashRegisterDataView that) {
		return context.getContentResolver().delete(Uri.parse(SCHEME + AUTHORITY + "/delete"), null, new String[]{ String.valueOf(that.getId()) });
	}

// reserved-for:AndroidSqliteDatabaseSingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @ExistsWhere */
	public boolean is_open_today () {
		String[] selectionArgs = new String[]{ com.uisleandro.util.config.getTodayString(), com.uisleandro.util.config.getUserIdString(), "0", "0" }; 
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/is_open_today"),null, null, selectionArgs, null);
		boolean that = false;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = (cursor.getInt(0) > 0);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @SelectValueWhere */
	public Float sum_cash_launches (Long fk_cash_register) {
		String[] selectionArgs = new String[]{ String.valueOf(fk_cash_register) }; 
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/sum_cash_launches"),null, null, selectionArgs, null);
		Float that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = cursor.getFloat(0);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @SelectListWhere */
	public List<CheckHistoryOut> check_history (long page_count, long page_size){
		String[] selectionArgs = new String[]{ com.uisleandro.util.config.getTodayString() }; 
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/check_history"),null, null, selectionArgs, null);
		List<CheckHistoryOut> those = null;
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			those.add( CheckHistoryOut.FromCursor(cursor) );
			cursor.moveToNext();
		}
		return those;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @Insert */
	public Uri open_cash_register (Long user){
		ContentValues contentValues = new ContentValues(5);
		contentValues.put("last_update",com.uisleandro.util.config.getRightNowString());
		contentValues.put("user",user);
		contentValues.put("opening_value","0");
		contentValues.put("received_value","0");
		contentValues.put("closing_value","0");
	
		Uri result = context.getContentResolver().insert(Uri.parse(SCHEME + AUTHORITY + "/open_cash_register"), contentValues);
	
	    return result;
	}
	
	
	/* @Insert */
	public Uri eventual_cash_usage (Long fk_cash_register, String justification, Float amount_spent){
		ContentValues contentValues = new ContentValues(4);
		contentValues.put("last_update",com.uisleandro.util.config.getRightNowString());
		contentValues.put("fk_cash_register",fk_cash_register);
		contentValues.put("justification",justification);
		contentValues.put("amount_spent",amount_spent);
	
		Uri result = context.getContentResolver().insert(Uri.parse(SCHEME + AUTHORITY + "/eventual_cash_usage"), contentValues);
	
	    return result;
	}
	
	
// reserved-for:AndroidSqliteQuerySingle002
// End of user code


// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.2
  //TODO: PLEASE REVIEW THIS CODE
  public void please_remake_it(int i, Bundle bundle) {
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
  @Override
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

    if (FN_CASH_REGISTER_INSERT == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/insert"), null, null, null, null);
    }
    else if (FN_CASH_REGISTER_UPDATE == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/update"), null, null, null, null);
    }
    else if (FN_CASH_REGISTER_DELETE == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/delete"), null, null, null, null);
    }
    else if (FN_CASH_REGISTER_ALL == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
    }
    else if (FN_CASH_REGISTER_SOME == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ bundle.getString("page_count"), bundle.getString("page_size") }, null, null, null);
    }
    else if (FN_CASH_REGISTER_BY_ID == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/by_id"), null, null, new String[]{ bundle.getString("id") }, null);
    }
    else if (FN_CASH_REGISTER_LAST_ID == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/last_id"), null, null, null, null);
    }
// reserved-for:AndroidSqliteDatabaseSingle002.4
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.2
	else if (FN_CASH_REGISTER_IS_OPEN_TODAY == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/is_open_today"), new String[]{}, null, null, null);
    }
	else if (FN_CASH_REGISTER_SUM_CASH_LAUNCHES == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/sum_cash_launches"), new String[]{}, null, null, null);
    }
	else if (FN_CASH_REGISTER_CHECK_HISTORY == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/check_history"), new String[]{}, null, null, null);
    }
	else if (FN_CASH_REGISTER_CLOSE_CASH_REGISTER == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/close_cash_register"), new String[]{}, null, null, null);
    }
	else if (FN_CASH_REGISTER_OPEN_CASH_REGISTER == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/open_cash_register"), new String[]{}, null, null, null);
    }
	else if (FN_CASH_REGISTER_EVENTUAL_CASH_USAGE == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/eventual_cash_usage"), new String[]{}, null, null, null);
    }
// reserved-for:AndroidSqliteQuerySingle002.2
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.5
    return null;
  }
// reserved-for:AndroidSqliteDatabaseSingle002.5
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.6
  @Override
  public void onLoaderReset(Loader<Cursor> cursorLoader) {
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
  @Override
  public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
	((LoaderInterface)this.context).onLoadFinished(cursorLoader,cursor);
  }
}
// reserved-for:AndroidSqliteDatabaseSingle002.8
// End of user code

