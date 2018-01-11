// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.receivement.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.receivement.view.BoletoSicoobView

public class BoletoSicoobDataSource {

	public static final String AUTHORITY = "com.uisleandro.boleto_sicoob";
	public static final String SCHEME = "content://";

	public static final String BOLETO_SICOOB_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String BOLETO_SICOOB_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String BOLETO_SICOOB_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String BOLETO_SICOOB_ALL = SCHEME + AUTHORITY + "/all";
	public static final String BOLETO_SICOOB_SOME = SCHEME + AUTHORITY + "/some";
	public static final String BOLETO_SICOOB_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String BOLETO_SICOOB_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public BoletoSicoobDataSource(Context context){
		this.context = context;
	}

	public List<BoletoSicoobView> listAll(){
		List<BoletoSicoobView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(BOLETO_SICOOB_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      BoletoSicoobView that = BoletoSicoobView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public BoletoSicoobView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(BOLETO_SICOOB_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = BoletoSicoobView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<BoletoSicoobView listSome(long page_count, long page_size){
		List<BoletoSicoobView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(BOLETO_SICOOB_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      BoletoSicoobView that = BoletoSicoobView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(BOLETO_SICOOB_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(BoletoSicoobView that) {
		context.getContentResolver().insert(BOLETO_SICOOB_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(BoletoSicoobView that) {
		return context.getContentResolver().update(BOLETO_SICOOB_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(BoletoSicoobView that) {
		return context.getContentResolver().delete(BOLETO_SICOOB_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(BOLETO_SICOOB_DELETE, null, new String[]{ String.valueOf(id) });
	}

}

