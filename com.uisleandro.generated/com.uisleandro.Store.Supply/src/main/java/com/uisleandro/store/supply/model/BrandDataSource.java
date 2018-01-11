// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.supply.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.supply.view.BrandView

public class BrandDataSource {

	public static final String AUTHORITY = "com.uisleandro.brand";
	public static final String SCHEME = "content://";

	public static final String BRAND_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String BRAND_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String BRAND_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String BRAND_ALL = SCHEME + AUTHORITY + "/all";
	public static final String BRAND_SOME = SCHEME + AUTHORITY + "/some";
	public static final String BRAND_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String BRAND_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public BrandDataSource(Context context){
		this.context = context;
	}

	public List<BrandView> listAll(){
		List<BrandView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(BRAND_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      BrandView that = BrandView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public BrandView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(BRAND_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = BrandView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<BrandView listSome(long page_count, long page_size){
		List<BrandView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(BRAND_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      BrandView that = BrandView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(BRAND_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(BrandView that) {
		context.getContentResolver().insert(BRAND_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(BrandView that) {
		return context.getContentResolver().update(BRAND_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(BrandView that) {
		return context.getContentResolver().delete(BRAND_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(BRAND_DELETE, null, new String[]{ String.valueOf(id) });
	}

}

