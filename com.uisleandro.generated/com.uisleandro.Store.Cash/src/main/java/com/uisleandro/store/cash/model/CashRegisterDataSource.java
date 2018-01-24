// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.cash.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.cash.view.CashRegisterDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
import com.uisleandro.store.cash.view.CheckHistoryOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class CashRegisterDataSource {

	public static final String AUTHORITY = "com.uisleandro.cash_register";
	public static final String SCHEME = "content://";

	public static final String CASH_REGISTER_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String CASH_REGISTER_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String CASH_REGISTER_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String CASH_REGISTER_ALL = SCHEME + AUTHORITY + "/all";
	public static final String CASH_REGISTER_SOME = SCHEME + AUTHORITY + "/some";
	public static final String CASH_REGISTER_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String CASH_REGISTER_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public CashRegisterDataSource (Context context) {
		this.context = context;
	}

	public List<CashRegisterView> listAll () {
		List<CashRegisterView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(CASH_REGISTER_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      CashRegisterView that = CashRegisterView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public CashRegisterView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(CASH_REGISTER_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = CashRegisterView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<CashRegisterView listSome (long page_count, long page_size) {
		List<CashRegisterView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(CASH_REGISTER_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      CashRegisterView that = CashRegisterView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(CASH_REGISTER_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (CashRegisterView that) {
		context.getContentResolver().insert(CASH_REGISTER_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (CashRegisterView that) {
		return context.getContentResolver().update(CASH_REGISTER_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (CashRegisterView that) {
		return context.getContentResolver().delete(CASH_REGISTER_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(CASH_REGISTER_DELETE, null, new String[]{ String.valueOf(id) });
	}
// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @SelectListWhere */
	public List<CheckHistoryOut> check_history (long page_count, long page_size){
		String selectionArgs = new String[]{ com.uisleandro.util.config.getTodayString() }; 
		Cursor cursor = context.getContentResolver().query("content://com.uisleandro.cash_register/check_history",null, null, selectionArgs, null);
		List<CheckHistoryOut> those = null;
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			those.add( CheckHistoryOut.FromCursor(cursor) );
			cursor.moveToNext();
		}
		return those;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @ExistsWhere */
	public boolean is_open_today () {
		String selectionArgs = new String[]{ com.uisleandro.util.config.getTodayString(), com.uisleandro.util.config.getUserIdString(), "0", "0" }; 
		Cursor cursor = context.getContentResolver().query("content://com.uisleandro.cash_register/is_open_today",null, null, selectionArgs, null);
		boolean that = false;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = (cursor.getInt(0) > 0);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @Insert */
	public int eventual_cash_usage (Long fk_cash_register, String justification, Float amount_spent){
		String[] insertArgs = new String[]{ com.uisleandro.util.config.getRightNowString(), String.valueOf(fk_cash_register), justification, String.valueOf(amount_spent) };
		ContentValues contentValues = null; ~~~~> PLEASE FIX IT <~~~~~
		context.getContentResolver().insert("content://com.uisleandro.cash_register/eventual_cash_usage", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @SelectValueWhere */
	public Float sum_cash_launches (Long fk_cash_register) {
		String selectionArgs = new String[]{ String.valueOf(fk_cash_register) }; 
		Cursor cursor = context.getContentResolver().query("content://com.uisleandro.cash_register/sum_cash_launches",null, null, selectionArgs, null);
		Float that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = cursor.getFloat(0);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @Insert */
	public int open_cash_register (Long user){
		String[] insertArgs = new String[]{ com.uisleandro.util.config.getRightNowString(), String.valueOf(user), "0", "0", "0" };
		ContentValues contentValues = null; ~~~~> PLEASE FIX IT <~~~~~
		context.getContentResolver().insert("content://com.uisleandro.cash_register/open_cash_register", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNC CODE
	}
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle003
}
// reserved-for:AndroidSqliteDatabaseSingle003
// End of user code

