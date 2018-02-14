// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.client.model;  

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
import com.uisleandro.store.client.view.BasicClientDataView;
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
import com.uisleandro.store.client.view.FindByCpfOut;
import com.uisleandro.store.client.view.FindByCpfOut;
import com.uisleandro.store.client.view.FindByCpfOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class BasicClientDataSource implements LoaderManager.LoaderCallbacks<Cursor> {

/* Yep this class implements the loaderCallbacks 

context.getloadermanager().init(FN_id, bundle, this)
I need to know about the class Bundle, which seems cary the data

*/

	public static final String AUTHORITY = "com.uisleandro.basic_client";
	public static final String SCHEME = "content://";

	public static final int FN_BASIC_CLIENT_INSERT = 998741;
	public static final int FN_BASIC_CLIENT_UPDATE = 998742;
	public static final int FN_BASIC_CLIENT_DELETE = 998743;
	public static final int FN_BASIC_CLIENT_ALL = 998744;
	public static final int FN_BASIC_CLIENT_SOME = 998745;
	public static final int FN_BASIC_CLIENT_BY_ID = 998746;
	public static final int FN_BASIC_CLIENT_LAST_ID = 998747;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
	public static final int FN_BASIC_CLIENT_FIND_BY_ID = 999711;
	public static final int FN_BASIC_CLIENT_FIND_BY_CPF = 999712;
	public static final int FN_BASIC_CLIENT_FIND_BY_NAME = 999713;
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	LoaderManager.LoaderCallbacks<Cursor> cursorLoader;
	public BasicClientDataSource (Context context) {
		this.context = context;
		cursorLoader = (LoaderManager.LoaderCallbacks<Cursor>) context; 
	}

	public List<BasicClientDataView> listAll () {
		List<BasicClientDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(BasicClientDataView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public BasicClientDataView getById (long id) {
		BasicClientDataView that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = BasicClientDataView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<BasicClientDataView> listSome (long page_count, long page_size) {
		List<BasicClientDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(BasicClientDataView.FromCursor(cursor));
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

	public int insert (BasicClientDataView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (BasicClientDataView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (BasicClientDataView that) {
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

    if (FN_BASIC_CLIENT_INSERT == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/insert"), null, null, null, null);
    }
    else if (FN_BASIC_CLIENT_UPDATE == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/update"), null, null, null, null);
    }
    else if (FN_BASIC_CLIENT_DELETE == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/delete"), null, null, null, null);
    }
    else if (FN_BASIC_CLIENT_ALL == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
    }
    else if (FN_BASIC_CLIENT_SOME == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ bundle.getString("page_count"), bundle.getString("page_size") }, null, null, null);
    }
    else if (FN_BASIC_CLIENT_BY_ID == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ bundle.getString("id") }, null);
    }
    else if (FN_BASIC_CLIENT_LAST_ID == i) {
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/last_id"), null, null, null, null);
    }
// reserved-for:AndroidSqliteDatabaseSingle002.4
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.2
	else if (FN_BASIC_CLIENT_FIND_BY_ID == i){
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/find_by_id"), new String[]{}, null, null, null);
    }
	else if (FN_BASIC_CLIENT_FIND_BY_CPF == i){
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/find_by_cpf"), new String[]{}, null, null, null);
    }
	else if (FN_BASIC_CLIENT_FIND_BY_NAME == i){
		return new CursorLoader(this, Uri.parse(SCHEME + AUTHORITY + "/find_by_name"), new String[]{}, null, null, null);
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

