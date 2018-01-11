// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.cash.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.cash.view.CashRegisterView

public class CashRegisterDataSource {

	public static final String AUTHORITY = "com.uisleandro.cash_register";
	public static final String SCHEME = "content://";

	public static final String CASH_REGISTER_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String CASH_REGISTER_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String CASH_REGISTER_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String CASH_REGISTER_ALL = SCHEME + AUTHORITY + "/all";
	public static final String CASH_REGISTER_SOME = SCHEME + AUTHORITY + "/some";
	public static final String CASH_REGISTER_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String CASH_REGISTER_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public CashRegisterDataSource(Context context){
		this.context = context;
	}

	public List<CashRegisterView> listAll(){
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

	public CashRegisterView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(CASH_REGISTER_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = CashRegisterView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<CashRegisterView listSome(long page_count, long page_size){
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

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(CASH_REGISTER_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(CashRegisterView that) {
		context.getContentResolver().insert(CASH_REGISTER_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(CashRegisterView that) {
		return context.getContentResolver().update(CASH_REGISTER_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(CashRegisterView that) {
		return context.getContentResolver().delete(CASH_REGISTER_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(CASH_REGISTER_DELETE, null, new String[]{ String.valueOf(id) });
	}

}

