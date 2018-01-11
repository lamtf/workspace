// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.discount.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.discount.view.DiscountView

public class DiscountDataSource {

	public static final String AUTHORITY = "com.uisleandro.discount";
	public static final String SCHEME = "content://";

	public static final String DISCOUNT_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String DISCOUNT_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String DISCOUNT_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String DISCOUNT_ALL = SCHEME + AUTHORITY + "/all";
	public static final String DISCOUNT_SOME = SCHEME + AUTHORITY + "/some";
	public static final String DISCOUNT_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String DISCOUNT_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public DiscountDataSource(Context context){
		this.context = context;
	}

	public List<DiscountView> listAll(){
		List<DiscountView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(DISCOUNT_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      DiscountView that = DiscountView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public DiscountView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(DISCOUNT_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = DiscountView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<DiscountView listSome(long page_count, long page_size){
		List<DiscountView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(DISCOUNT_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      DiscountView that = DiscountView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(DISCOUNT_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(DiscountView that) {
		context.getContentResolver().insert(DISCOUNT_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(DiscountView that) {
		return context.getContentResolver().update(DISCOUNT_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(DiscountView that) {
		return context.getContentResolver().delete(DISCOUNT_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(DISCOUNT_DELETE, null, new String[]{ String.valueOf(id) });
	}

}

