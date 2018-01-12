// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.Core.model;  

import java.util.ArrayList;
import java.util.List;
import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
//old
//import com.uisleandro.util.LongDateFormatter;
//import com.uisleandro.store.model.DbHelper;
//import com.uisleandro.store.receivement.model.InvoiceDbHelper;

import com.uisleandro.store.DbHelper;
// reserved-for:AndroidSqliteDatabase001
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle001
// reserved-for:AndroidSqliteSyncSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase002
public class InvoiceProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.invoice";
	public static final String SCHEME = "content://";

	public static final String INVOICE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_INVOICE_INSERT = Uri.parse(INVOICE_INSERT);
	public static final String INVOICE_INSERT_BASE = INVOICE_INSERT + "/";

	public static final String INVOICE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_INVOICE_UPDATE = Uri.parse(INVOICE_UPDATE);
	public static final String INVOICE_UPDATE_BASE = INVOICE_UPDATE + "/";

	public static final String INVOICE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_INVOICE_DELETE = Uri.parse(INVOICE_DELETE);
	public static final String INVOICE_DELETE_BASE = INVOICE_DELETE + "/";

	public static final String INVOICE_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_INVOICE_ALL = Uri.parse(INVOICE_ALL);
	public static final String INVOICE_ALL_BASE = INVOICE_ALL + "/";

	public static final String INVOICE_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_INVOICE_SOME = Uri.parse(INVOICE_SOME);
	public static final String INVOICE_SOME_BASE = INVOICE_SOME + "/";

	public static final String INVOICE_BYID = SCHEME + AUTHORITY + "/byid";
	public static final Uri URI_INVOICE_BYID = Uri.parse(INVOICE_BYID);
	public static final String INVOICE_BYID_BASE = INVOICE_BYID + "/";

	public static final String INVOICE_LASTID = SCHEME + AUTHORITY + "/lastid";
	public static final Uri URI_INVOICE_LASTID = Uri.parse(INVOICE_LASTID);
	public static final String INVOICE_LASTID_BASE = INVOICE_LASTID + "/";

// reserved-for:AndroidSqliteDatabase002
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle002
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	public static final String INVOICE_INSERT_INSTALLMENT_SICOOB = SCHEME + AUTHORITY + "/insert_installment_sicoob";
	public static final Uri URI_INVOICE_INSERT_INSTALLMENT_SICOOB = Uri.parse(INVOICE_INSERT_INSTALLMENT_SICOOB);
	public static final String INVOICE_INSERT_INSTALLMENT_SICOOB_BASE = INVOICE_INSERT_INSTALLMENT_SICOOB + "/";
	public static final String INVOICE_INSERT_INSTALLMENT = SCHEME + AUTHORITY + "/insert_installment";
	public static final Uri URI_INVOICE_INSERT_INSTALLMENT = Uri.parse(INVOICE_INSERT_INSTALLMENT);
	public static final String INVOICE_INSERT_INSTALLMENT_BASE = INVOICE_INSERT_INSTALLMENT + "/";
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003
	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[] { 
		DbHelper.INVOICE_ID,
		DbHelper.INVOICE_SERVER_ID,
		DbHelper.INVOICE_DIRTY,
		DbHelper.INVOICE_LAST_UPDATE, 
		DbHelper.INVOICE_FK_SYSTEM, 
		DbHelper.INVOICE_FK_SALE, 
		DbHelper.INVOICE_FK_CLIENT_FROM_SYSTEM, 
		DbHelper.INVOICE_FK_INSTALLMENT_TYPE, 
		DbHelper.INVOICE_FK_INTEREST_RATE_TYPE, 
		DbHelper.INVOICE_FK_BANK, 
		DbHelper.INVOICE_FK_CURRENCY
	};

	public InvoiceDataSource (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("InvoiceDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public Cursor listAll () {
		Cursor cursor = database.query(DbHelper.TABLE_INVOICE,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById (long id) {
		Cursor cursor = database.query(DbHelper.TABLE_INVOICE,
			selectableColumns,
			DbHelper.INVOICE_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome (long page_count, long page_size) {
		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_system, " +
			"fk_sale, " +
			"fk_client_from_system, " +
			"fk_installment_type, " +
			"fk_interest_rate_type, " +
			"fk_bank, " +
			"fk_currency" +
			" FROM " + DbHelper.TABLE_INVOICE;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public Cursor getLastId () {
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_INVOICE +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;		
	}

// begin content-provider-interface

	@Override
	public boolean onCreate () {
		return false;
	}

	@Nullable
	@Override
	public String getType (@NonNull Uri uri) {
		return null;
	}
// reserved-for:AndroidSqliteDatabase003
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase004
	@Nullable
	@Override
	public Uri insert (@NonNull Uri uri, @Nullable ContentValues values) {
		Cursor result = null;
		if (URI_INVOICE_INSERT.equals(uri)) {
			result = database.insert(DbHelper.TABLE_INVOICE, null, values);
		}
// reserved-for:AndroidSqliteDatabase004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle003
/* @Insert */
	else if (URI_INVOICE_insert_installment_sicoob.equals(uri)) {
			result = insert_installment_sicoob(selectionArgs); // << missing arguments
	}
	else if (URI_INVOICE_insert_installment.equals(uri)) {
			result = insert_installment(selectionArgs); // << missing arguments
	}
// reserved-for:AndroidSqliteQuerySingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase005
		return null;
	}
// reserved-for:AndroidSqliteDatabase005
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase006
	@Override
	public int update (@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_INVOICE_UPDATE.equals(uri)) {
			result = database.update(DbHelper.TABLE_INVOICE, values, DbHelper.INVOICE_ID + " = " + selectionArgs[0], null);
		}
// reserved-for:AndroidSqliteDatabase006
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle004
/* @UpdateWhere */
// reserved-for:AndroidSqliteQuerySingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase007
		return result;
	}
// reserved-for:AndroidSqliteDatabase007
// End of user code


// Start of user code reserved-for:AndroidSqliteDatabase008
	@Override
	public int delete (@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_INVOICE_DELETE.equals(uri)) {
			result = database.delete(DbHelper.TABLE_INVOICE, DbHelper.INVOICE_ID + " = " + selectionArgs[0], null);
		}
// reserved-for:AndroidSqliteDatabase008
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle005
/* @DeleteWhere */
// reserved-for:AndroidSqliteQuerySingle005
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase009
		return result;
	}
// reserved-for:AndroidSqliteDatabase009
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle003
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle006
	/* @Insert */
	public int insert_installment_sicoob (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "INSERT INTO invoice(last_update,system,sale,client_from_system,installment_type,interest_rate_type,bank,currency,last_update,name,enabled,currency,last_update,sale_type,system,total_value,user,client_from_system,currency,last_update,system,basic_client,shared_client,user,last_update,name,last_update,name,last_update,code,name,last_update,abbreviature,description,last_update,name,last_update,system,role,username,password,name,email,last_use_time,last_error_time,error_count,active,last_update,name,birth_date,birth_city,birth_state,mothers_name,fathers_name,profession,zip_code,address,neighborhood,city,state,complement,country,last_update,name,birth_date,birth_city,birth_state,mothers_name,fathers_name,profession,zip_code,address,neighborhood,city,state,complement,country,last_update,name,last_update,name) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		//TODO: I don't knwo if its returning the last_id, I guess it's not
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	}
	/* @Insert */
	public int insert_installment (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "INSERT INTO invoice(last_update,system,sale,client_from_system,installment_type,interest_rate_type,bank,currency,last_update,name,enabled,currency,last_update,sale_type,system,total_value,user,client_from_system,currency,last_update,system,basic_client,shared_client,user,last_update,name,last_update,name,last_update,code,name,last_update,abbreviature,description,last_update,name,last_update,system,role,username,password,name,email,last_use_time,last_error_time,error_count,active,last_update,name,birth_date,birth_city,birth_state,mothers_name,fathers_name,profession,zip_code,address,neighborhood,city,state,complement,country,last_update,name,birth_date,birth_city,birth_state,mothers_name,fathers_name,profession,zip_code,address,neighborhood,city,state,complement,country,last_update,name,last_update,name) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		//TODO: I don't knwo if its returning the last_id, I guess it's not
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	}
// reserved-for:AndroidSqliteQuerySingle006
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase010
	// TODO: I NEED TO KNOW HOW TO MAKE VARIOUS QUERIES DEPENDING ON THE URI
	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Cursor result = null;
		if (URI_INVOICE_ALL.equals(uri)) {
			result = listAll();
		}
		else if (URI_INVOICE_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if (URI_INVOICE_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if (URI_INVOICE_LASTID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:AndroidSqliteDatabase010
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle004
// reserved-for:AndroidSqliteSyncSingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle007
/* @ExistsWhere||@SelectValueWhere||@SelectOneWhere||@SelectListWhere */
// Start of user code reserved-for:AndroidSqliteQuerySingle007

// Start of user code reserved-for:AndroidSqliteDatabase011
// reserved-for:AndroidSqliteDatabase011
// End of user code
