// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.receivement.model;  

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
import com.uisleandro.store.receivement.view.InvoiceDataView;
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class InvoiceDataSource implements LoaderManager.LoaderCallbacks<Cursor> {

/* Yep this class implements the loaderCallbacks 

context.getloadermanager().init(FN_id, bundle, this)
I need to know about the class Bundle, which seems cary the data

*/

	public static final String AUTHORITY = "com.uisleandro.invoice";
	public static final String SCHEME = "content://";

	public static final int FN_INVOICE_INSERT = 998821;
	public static final int FN_INVOICE_UPDATE = 998822;
	public static final int FN_INVOICE_DELETE = 998823;
	public static final int FN_INVOICE_ALL = 998824;
	public static final int FN_INVOICE_SOME = 998825;
	public static final int FN_INVOICE_BY_ID = 998826;
	public static final int FN_INVOICE_LAST_ID = 998827;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
	public static final int FN_INVOICE_INSERT_INSTALLMENT_SICOOB = 999811;
	public static final int FN_INVOICE_INSERT_INSTALLMENT = 999812;
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	LoaderManager.LoaderCallbacks<Cursor> cursorLoader;
	public InvoiceDataSource (Context context) {
		this.context = context;
		cursorLoader = (LoaderManager.LoaderCallbacks<Cursor>) context; 
	}

	public List<InvoiceDataView> listAll () {
		List<InvoiceDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(InvoiceDataView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public InvoiceDataView getById (long id) {
		InvoiceDataView that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = InvoiceDataView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<InvoiceDataView> listSome (long page_count, long page_size) {
		List<InvoiceDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(InvoiceDataView.FromCursor(cursor));
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

	public int insert (InvoiceDataView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (InvoiceDataView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (InvoiceDataView that) {
		return context.getContentResolver().delete(SCHEME + AUTHORITY + "/delete", null, new String[]{ String.valueOf(that.getId()) });
	}

// reserved-for:AndroidSqliteDatabaseSingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @Insert */
	public int insert_installment_sicoob (Long last_update, Long last_update, String name, Boolean enabled, Long last_update, Float total_value, Long last_update, Long last_update, String name, Long last_update, String name, Long last_update, String code, String name, Long last_update, String abbreviature, String description, Long last_update, String name, Long last_update, String username, String password, String name, String email, Long last_use_time, Long last_error_time, Integer error_count, Boolean active, Long last_update, String name, Long birth_date, String birth_city, String birth_state, String mothers_name, String fathers_name, String profession, String zip_code, String address, String neighborhood, String city, String state, String complement, Long last_update, String name, Long birth_date, String birth_city, String birth_state, String mothers_name, String fathers_name, String profession, String zip_code, String address, String neighborhood, String city, String state, String complement, Long last_update, String name, Long last_update, String name){
		ContentValues contentValues = new ContentValues(81);
		contentValues.put("last_update",last_update);
		contentValues.put("system",system);
		contentValues.put("sale",sale);
		contentValues.put("client_from_system",client_from_system);
		contentValues.put("installment_type",installment_type);
		contentValues.put("interest_rate_type",interest_rate_type);
		contentValues.put("bank",bank);
		contentValues.put("currency",currency);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("enabled",enabled);
		contentValues.put("currency",currency);
		contentValues.put("last_update",last_update);
		contentValues.put("sale_type",sale_type);
		contentValues.put("system",system);
		contentValues.put("total_value",total_value);
		contentValues.put("user",user);
		contentValues.put("client_from_system",client_from_system);
		contentValues.put("currency",currency);
		contentValues.put("last_update",last_update);
		contentValues.put("system",system);
		contentValues.put("basic_client",basic_client);
		contentValues.put("shared_client",shared_client);
		contentValues.put("user",user);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("last_update",last_update);
		contentValues.put("code",code);
		contentValues.put("name",name);
		contentValues.put("last_update",last_update);
		contentValues.put("abbreviature",abbreviature);
		contentValues.put("description",description);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("last_update",last_update);
		contentValues.put("system",system);
		contentValues.put("role",role);
		contentValues.put("username",username);
		contentValues.put("password",password);
		contentValues.put("name",name);
		contentValues.put("email",email);
		contentValues.put("last_use_time",last_use_time);
		contentValues.put("last_error_time",last_error_time);
		contentValues.put("error_count",error_count);
		contentValues.put("active",active);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("birth_date",birth_date);
		contentValues.put("birth_city",birth_city);
		contentValues.put("birth_state",birth_state);
		contentValues.put("mothers_name",mothers_name);
		contentValues.put("fathers_name",fathers_name);
		contentValues.put("profession",profession);
		contentValues.put("zip_code",zip_code);
		contentValues.put("address",address);
		contentValues.put("neighborhood",neighborhood);
		contentValues.put("city",city);
		contentValues.put("state",state);
		contentValues.put("complement",complement);
		contentValues.put("country",country);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("birth_date",birth_date);
		contentValues.put("birth_city",birth_city);
		contentValues.put("birth_state",birth_state);
		contentValues.put("mothers_name",mothers_name);
		contentValues.put("fathers_name",fathers_name);
		contentValues.put("profession",profession);
		contentValues.put("zip_code",zip_code);
		contentValues.put("address",address);
		contentValues.put("neighborhood",neighborhood);
		contentValues.put("city",city);
		contentValues.put("state",state);
		contentValues.put("complement",complement);
		contentValues.put("country",country);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
	
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert_installment_sicoob", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNCHRONIZED CODE
	}
	
	
	/* @Insert */
	public int insert_installment (Long last_update, Long last_update, String name, Boolean enabled, Long last_update, Float total_value, Long last_update, Long last_update, String name, Long last_update, String name, Long last_update, String code, String name, Long last_update, String abbreviature, String description, Long last_update, String name, Long last_update, String username, String password, String name, String email, Long last_use_time, Long last_error_time, Integer error_count, Boolean active, Long last_update, String name, Long birth_date, String birth_city, String birth_state, String mothers_name, String fathers_name, String profession, String zip_code, String address, String neighborhood, String city, String state, String complement, Long last_update, String name, Long birth_date, String birth_city, String birth_state, String mothers_name, String fathers_name, String profession, String zip_code, String address, String neighborhood, String city, String state, String complement, Long last_update, String name, Long last_update, String name){
		ContentValues contentValues = new ContentValues(80);
		contentValues.put("last_update",last_update);
		contentValues.put("system",system);
		contentValues.put("sale",sale);
		contentValues.put("client_from_system",client_from_system);
		contentValues.put("installment_type",installment_type);
		contentValues.put("interest_rate_type",interest_rate_type);
		contentValues.put("bank",bank);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("enabled",enabled);
		contentValues.put("currency",currency);
		contentValues.put("last_update",last_update);
		contentValues.put("sale_type",sale_type);
		contentValues.put("system",system);
		contentValues.put("total_value",total_value);
		contentValues.put("user",user);
		contentValues.put("client_from_system",client_from_system);
		contentValues.put("currency",currency);
		contentValues.put("last_update",last_update);
		contentValues.put("system",system);
		contentValues.put("basic_client",basic_client);
		contentValues.put("shared_client",shared_client);
		contentValues.put("user",user);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("last_update",last_update);
		contentValues.put("code",code);
		contentValues.put("name",name);
		contentValues.put("last_update",last_update);
		contentValues.put("abbreviature",abbreviature);
		contentValues.put("description",description);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("last_update",last_update);
		contentValues.put("system",system);
		contentValues.put("role",role);
		contentValues.put("username",username);
		contentValues.put("password",password);
		contentValues.put("name",name);
		contentValues.put("email",email);
		contentValues.put("last_use_time",last_use_time);
		contentValues.put("last_error_time",last_error_time);
		contentValues.put("error_count",error_count);
		contentValues.put("active",active);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("birth_date",birth_date);
		contentValues.put("birth_city",birth_city);
		contentValues.put("birth_state",birth_state);
		contentValues.put("mothers_name",mothers_name);
		contentValues.put("fathers_name",fathers_name);
		contentValues.put("profession",profession);
		contentValues.put("zip_code",zip_code);
		contentValues.put("address",address);
		contentValues.put("neighborhood",neighborhood);
		contentValues.put("city",city);
		contentValues.put("state",state);
		contentValues.put("complement",complement);
		contentValues.put("country",country);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("birth_date",birth_date);
		contentValues.put("birth_city",birth_city);
		contentValues.put("birth_state",birth_state);
		contentValues.put("mothers_name",mothers_name);
		contentValues.put("fathers_name",fathers_name);
		contentValues.put("profession",profession);
		contentValues.put("zip_code",zip_code);
		contentValues.put("address",address);
		contentValues.put("neighborhood",neighborhood);
		contentValues.put("city",city);
		contentValues.put("state",state);
		contentValues.put("complement",complement);
		contentValues.put("country",country);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
	
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert_installment", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNCHRONIZED CODE
	}
	
	
// reserved-for:AndroidSqliteQuerySingle002
// End of user code


// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.2
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
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
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

    if (FN_INVOICE_INSERT == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/insert"), null, null, null, null);
    }
    else if (FN_INVOICE_UPDATE == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/update"), null, null, null, null);
    }
    else if (FN_INVOICE_DELETE == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/delete"), null, null, null, null);
    }
    else if (FN_INVOICE_ALL == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
    }
    else if (FN_INVOICE_SOME == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ bundle.getString("page_count"), bundle.getString("page_size") }, null, null, null);
    }
    else if (FN_INVOICE_BY_ID == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ bundle.getString("id") }, null);
    }
    else if (FN_INVOICE_LAST_ID == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/last_id"), null, null, null, null);
    }
// reserved-for:AndroidSqliteDatabaseSingle002.4
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.2
	else if (FN_INVOICE_INSERT_INSTALLMENT_SICOOB == i){
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/insert_installment_sicoob"), new String[]{}, null, null, null);
    }
	else if (FN_INVOICE_INSERT_INSTALLMENT == i){
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/insert_installment"), new String[]{}, null, null, null);
    }
// reserved-for:AndroidSqliteQuerySingle002.2
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.5
  }
// reserved-for:AndroidSqliteDatabaseSingle002.5
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.6
  public Loader<Cursor> onLoaderReset(Loader<Cursor> cursorLoader) {
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
  public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
	((LoaderInterface)this.context).onLoadFinished(cursorLoader,cursor);
  }
}
// reserved-for:AndroidSqliteDatabaseSingle002.8
// End of user code

