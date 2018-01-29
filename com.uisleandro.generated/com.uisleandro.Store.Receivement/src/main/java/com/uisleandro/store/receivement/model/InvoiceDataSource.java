// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.receivement.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.receivement.view.InvoiceDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class InvoiceDataSource {

	public static final String AUTHORITY = "com.uisleandro.invoice";
	public static final String SCHEME = "content://";

	public static final String INVOICE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String INVOICE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String INVOICE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String INVOICE_ALL = SCHEME + AUTHORITY + "/all";
	public static final String INVOICE_SOME = SCHEME + AUTHORITY + "/some";
	public static final String INVOICE_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String INVOICE_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public InvoiceDataSource (Context context) {
		this.context = context;
	}

	public List<InvoiceView> listAll () {
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

	public InvoiceView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(INVOICE_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = InvoiceView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<InvoiceView listSome (long page_count, long page_size) {
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

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(INVOICE_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (InvoiceView that) {
		context.getContentResolver().insert(INVOICE_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (InvoiceView that) {
		return context.getContentResolver().update(INVOICE_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (InvoiceView that) {
		return context.getContentResolver().delete(INVOICE_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(INVOICE_DELETE, null, new String[]{ String.valueOf(id) });
	}
// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @Insert */
	public int insert_installment (Long last_update, Long last_update, String name, Boolean enabled, Long last_update, Float total_value, Long last_update, Long last_update, String name, Long last_update, String name, Long last_update, String code, String name, Long last_update, String abbreviature, String description, Long last_update, String name, Long last_update, String username, String password, String name, String email, Long last_use_time, Long last_error_time, Integer error_count, Boolean active, Long last_update, String name, Long birth_date, String birth_city, String birth_state, String mothers_name, String fathers_name, String profession, String zip_code, String address, String neighborhood, String city, String state, String complement, Long last_update, String name, Long birth_date, String birth_city, String birth_state, String mothers_name, String fathers_name, String profession, String zip_code, String address, String neighborhood, String city, String state, String complement, Long last_update, String name, Long last_update, String name){
		String[] insertArgs = new String[]{ String.valueOf(last_update), String.valueOf(system), String.valueOf(sale), String.valueOf(client_from_system), String.valueOf(installment_type), String.valueOf(interest_rate_type), String.valueOf(bank), String.valueOf(currency), String.valueOf(last_update), name, String.valueOf(enabled), String.valueOf(currency), String.valueOf(last_update), String.valueOf(sale_type), String.valueOf(system), String.valueOf(total_value), String.valueOf(user), String.valueOf(client_from_system), String.valueOf(currency), String.valueOf(last_update), String.valueOf(system), String.valueOf(basic_client), String.valueOf(shared_client), String.valueOf(user), String.valueOf(last_update), name, String.valueOf(last_update), name, String.valueOf(last_update), code, name, String.valueOf(last_update), abbreviature, description, String.valueOf(last_update), name, String.valueOf(last_update), String.valueOf(system), String.valueOf(role), username, password, name, email, String.valueOf(last_use_time), String.valueOf(last_error_time), String.valueOf(error_count), String.valueOf(active), String.valueOf(last_update), name, String.valueOf(birth_date), birth_city, birth_state, mothers_name, fathers_name, profession, zip_code, address, neighborhood, city, state, complement, String.valueOf(country), String.valueOf(last_update), name, String.valueOf(birth_date), birth_city, birth_state, mothers_name, fathers_name, profession, zip_code, address, neighborhood, city, state, complement, String.valueOf(country), String.valueOf(last_update), name, String.valueOf(last_update), name };
		ContentValues contentValues = null; ~~~~> PLEASE FIX IT <~~~~~
		context.getContentResolver().insert("content://com.uisleandro.invoice/insert_installment", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @Insert */
	public int insert_installment_sicoob (Long last_update, Long last_update, String name, Boolean enabled, Long last_update, Float total_value, Long last_update, Long last_update, String name, Long last_update, String name, Long last_update, String code, String name, Long last_update, String abbreviature, String description, Long last_update, String name, Long last_update, String username, String password, String name, String email, Long last_use_time, Long last_error_time, Integer error_count, Boolean active, Long last_update, String name, Long birth_date, String birth_city, String birth_state, String mothers_name, String fathers_name, String profession, String zip_code, String address, String neighborhood, String city, String state, String complement, Long last_update, String name, Long birth_date, String birth_city, String birth_state, String mothers_name, String fathers_name, String profession, String zip_code, String address, String neighborhood, String city, String state, String complement, Long last_update, String name, Long last_update, String name){
		String[] insertArgs = new String[]{ String.valueOf(last_update), String.valueOf(system), String.valueOf(sale), String.valueOf(client_from_system), String.valueOf(installment_type), String.valueOf(interest_rate_type), String.valueOf(bank), String.valueOf(currency), String.valueOf(last_update), name, String.valueOf(enabled), String.valueOf(currency), String.valueOf(last_update), String.valueOf(sale_type), String.valueOf(system), String.valueOf(total_value), String.valueOf(user), String.valueOf(client_from_system), String.valueOf(currency), String.valueOf(last_update), String.valueOf(system), String.valueOf(basic_client), String.valueOf(shared_client), String.valueOf(user), String.valueOf(last_update), name, String.valueOf(last_update), name, String.valueOf(last_update), code, name, String.valueOf(last_update), abbreviature, description, String.valueOf(last_update), name, String.valueOf(last_update), String.valueOf(system), String.valueOf(role), username, password, name, email, String.valueOf(last_use_time), String.valueOf(last_error_time), String.valueOf(error_count), String.valueOf(active), String.valueOf(last_update), name, String.valueOf(birth_date), birth_city, birth_state, mothers_name, fathers_name, profession, zip_code, address, neighborhood, city, state, complement, String.valueOf(country), String.valueOf(last_update), name, String.valueOf(birth_date), birth_city, birth_state, mothers_name, fathers_name, profession, zip_code, address, neighborhood, city, state, complement, String.valueOf(country), String.valueOf(last_update), name, String.valueOf(last_update), name };
		ContentValues contentValues = null; ~~~~> PLEASE FIX IT <~~~~~
		context.getContentResolver().insert("content://com.uisleandro.invoice/insert_installment_sicoob", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNC CODE
	}
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle003
}
// reserved-for:AndroidSqliteDatabaseSingle003
// End of user code
