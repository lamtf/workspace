// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.receivement.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.receivement.view.BankView

public class BankDataSource {

	public static final String AUTHORITY = "com.uisleandro.bank";
	public static final String SCHEME = "content://";

	public static final String BANK_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String BANK_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String BANK_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String BANK_ALL = SCHEME + AUTHORITY + "/all";
	public static final String BANK_SOME = SCHEME + AUTHORITY + "/some";
	public static final String BANK_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String BANK_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public BankDataSource(Context context){
		this.context = context;
	}

	public List<BankView> listAll(){
		List<BankView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(BANK_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      BankView that = BankView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public BankView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(BANK_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = BankView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<BankView listSome(long page_count, long page_size){
		List<BankView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(BANK_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      BankView that = BankView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(BANK_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(BankView that) {
		context.getContentResolver().insert(BANK_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(BankView that) {
		return context.getContentResolver().update(BANK_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(BankView that) {
		return context.getContentResolver().delete(BANK_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(BANK_DELETE, null, new String[]{ String.valueOf(id) });
	}

}

