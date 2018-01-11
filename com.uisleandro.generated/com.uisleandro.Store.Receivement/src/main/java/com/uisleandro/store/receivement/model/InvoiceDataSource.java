// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.receivement.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.receivement.view.InvoiceView

public class InvoiceDataSource {

	public static final String AUTHORITY = "com.uisleandro.invoice";
	public static final String SCHEME = "content://";

	public static final String INVOICE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String INVOICE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String INVOICE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String INVOICE_ALL = SCHEME + AUTHORITY + "/all";
	public static final String INVOICE_SOME = SCHEME + AUTHORITY + "/some";
	public static final String INVOICE_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String INVOICE_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public InvoiceDataSource(Context context){
		this.context = context;
	}

	public List<InvoiceView> listAll(){
		List<InvoiceView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(INVOICE_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      InvoiceView that = InvoiceView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public InvoiceView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(INVOICE_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = InvoiceView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<InvoiceView listSome(long page_count, long page_size){
		List<InvoiceView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(INVOICE_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      InvoiceView that = InvoiceView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(INVOICE_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(InvoiceView that) {
		context.getContentResolver().insert(INVOICE_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(InvoiceView that) {
		return context.getContentResolver().update(INVOICE_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(InvoiceView that) {
		return context.getContentResolver().delete(INVOICE_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(INVOICE_DELETE, null, new String[]{ String.valueOf(id) });
	}

}

