// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.client.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.client.view.BasicClientDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
import com.uisleandro.store.client.view.FindByCpfOut;
import com.uisleandro.store.client.view.FindByCpfOut;
import com.uisleandro.store.client.view.FindByCpfOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class BasicClientDataSource {

	public static final String AUTHORITY = "com.uisleandro.basic_client";
	public static final String SCHEME = "content://";

	public static final Integer FN_BASIC_CLIENT_INSERT = 998741;
	public static final Integer FN_BASIC_CLIENT_UPDATE = 998742;
	public static final Integer FN_BASIC_CLIENT_DELETE = 998743;
	public static final Integer FN_BASIC_CLIENT_ALL = 998744;
	public static final Integer FN_BASIC_CLIENT_SOME = 998745;
	public static final Integer FN_BASIC_CLIENT_BY_ID = 998746;
	public static final Integer FN_BASIC_CLIENT_LAST_ID = 998747;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
	public static final Integer FN_BASIC_CLIENT_FIND_BY_ID = 999711;
	public static final Integer FN_BASIC_CLIENT_FIND_BY_CPF = 999712;
	public static final Integer FN_BASIC_CLIENT_FIND_BY_NAME = 999713;
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	public BasicClientDataSource (Context context) {
		this.context = context;
	}

	public List<BasicClientView> listAll () {
		List<BasicClientView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(BasicClientView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public BasicClientView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = BasicClientView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<BasicClientView> listSome (long page_count, long page_size) {
		List<BasicClientView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(BasicClientView.FromCursor(cursor));
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

	public int insert (BasicClientView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (BasicClientView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (BasicClientView that) {
		return context.getContentResolver().delete(SCHEME + AUTHORITY + "/delete", null, new String[]{ String.valueOf(that.getId()) });
	}

// reserved-for:AndroidSqliteDatabaseSingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @SelectOneWhere */
	public FindByCpfOut find_by_id(long id){
		String selectionArgs = new String[]{ String.valueOf(id) }; 
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/find_by_id",null, null, selectionArgs, null);
		FindByCpfOut that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = FindByCpfOut.FromCursor(cursor);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @SelectOneWhere */
	public FindByCpfOut find_by_cpf(String cpf){
		String selectionArgs = new String[]{ cpf }; 
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/find_by_cpf",null, null, selectionArgs, null);
		FindByCpfOut that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = FindByCpfOut.FromCursor(cursor);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @SelectOneWhere */
	public FindByCpfOut find_by_name(String name){
		String selectionArgs = new String[]{ name }; 
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/find_by_name",null, null, selectionArgs, null);
		FindByCpfOut that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = FindByCpfOut.FromCursor(cursor);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle003
}
// reserved-for:AndroidSqliteDatabaseSingle003
// End of user code
