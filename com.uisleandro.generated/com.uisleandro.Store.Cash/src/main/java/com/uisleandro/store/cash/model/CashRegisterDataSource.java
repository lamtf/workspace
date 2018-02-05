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

	public static final Integer FN_CASH_REGISTER_INSERT = 998111;
	public static final Integer FN_CASH_REGISTER_UPDATE = 998112;
	public static final Integer FN_CASH_REGISTER_DELETE = 998113;
	public static final Integer FN_CASH_REGISTER_ALL = 998114;
	public static final Integer FN_CASH_REGISTER_SOME = 998115;
	public static final Integer FN_CASH_REGISTER_BY_ID = 998116;
	public static final Integer FN_CASH_REGISTER_LAST_ID = 998117;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
	public static final Integer FN_CASH_REGISTER_IS_OPEN_TODAY = 999111;
	public static final Integer FN_CASH_REGISTER_SUM_CASH_LAUNCHES = 999112;
	public static final Integer FN_CASH_REGISTER_CHECK_HISTORY = 999113;
	public static final Integer FN_CASH_REGISTER_CLOSE_CASH_REGISTER = 999114;
	public static final Integer FN_CASH_REGISTER_OPEN_CASH_REGISTER = 999115;
	public static final Integer FN_CASH_REGISTER_EVENTUAL_CASH_USAGE = 999116;
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	public CashRegisterDataSource (Context context) {
		this.context = context;
	}

	public List<CashRegisterView> listAll () {
		List<CashRegisterView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(CashRegisterView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public CashRegisterView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = CashRegisterView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<CashRegisterView> listSome (long page_count, long page_size) {
		List<CashRegisterView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(CashRegisterView.FromCursor(cursor));
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

	public int insert (CashRegisterView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (CashRegisterView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (CashRegisterView that) {
		return context.getContentResolver().delete(SCHEME + AUTHORITY + "/delete", null, new String[]{ String.valueOf(that.getId()) });
	}

// reserved-for:AndroidSqliteDatabaseSingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @ExistsWhere */
	public boolean is_open_today () {
		String selectionArgs = new String[]{ com.uisleandro.util.config.getTodayString(), com.uisleandro.util.config.getUserIdString(), "0", "0" }; 
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/is_open_today",null, null, selectionArgs, null);
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
		String selectionArgs = new String[]{ String.valueOf(fk_cash_register) }; 
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/sum_cash_launches",null, null, selectionArgs, null);
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
		String selectionArgs = new String[]{ com.uisleandro.util.config.getTodayString() }; 
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/check_history",null, null, selectionArgs, null);
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
	public int open_cash_register (Long user){
		ContentValues contentValues = new ContentValues(5);
		contentValues.put("last_update",com.uisleandro.util.config.getRightNowString());
		contentValues.put("user",user);
		contentValues.put("opening_value","0");
		contentValues.put("received_value","0");
		contentValues.put("closing_value","0");
	
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/open_cash_register", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNCHRONIZED CODE
	}
	
	
	/* @Insert */
	public int eventual_cash_usage (Long fk_cash_register, String justification, Float amount_spent){
		ContentValues contentValues = new ContentValues(4);
		contentValues.put("last_update",com.uisleandro.util.config.getRightNowString());
		contentValues.put("fk_cash_register",fk_cash_register);
		contentValues.put("justification",justification);
		contentValues.put("amount_spent",amount_spent);
	
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/eventual_cash_usage", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNCHRONIZED CODE
	}
	
	
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle003
}
// reserved-for:AndroidSqliteDatabaseSingle003
// End of user code
