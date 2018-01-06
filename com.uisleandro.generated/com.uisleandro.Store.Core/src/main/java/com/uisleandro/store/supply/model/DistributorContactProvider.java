//Start of user code reserved-for:android-sqlite-db.imports
package com.uisleandro.store.Core.model;  

import java.util.ArrayList;
import java.util.List;
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
//import com.uisleandro.store.supply.model.DistributorContactDbHelper;

import com.uisleandro.store.DbHelper;

//TODO: I wont return any view, Id rather return the cursor instead 

// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class DistributorContactProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.distributor_contact";
	public static final String SCHEME = "content://";

	public static final String DISTRIBUTOR_CONTACT_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_DISTRIBUTOR_CONTACT_ALL = Uri.parse(DISTRIBUTOR_CONTACT_ALL);
	public static final String DISTRIBUTOR_CONTACT_ALL_BASE = DISTRIBUTOR_CONTACT_ALL + "/";

	public static final String DISTRIBUTOR_CONTACT_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_DISTRIBUTOR_CONTACT_SOME = Uri.parse(DISTRIBUTOR_CONTACT_SOME);
	public static final String DISTRIBUTOR_CONTACT_SOME_BASE = DISTRIBUTOR_CONTACT_SOME + "/";

	public static final String DISTRIBUTOR_CONTACT_BYID = SCHEME + AUTHORITY + "/byid";
	public static final Uri URI_DISTRIBUTOR_CONTACT_BYID = Uri.parse(DISTRIBUTOR_CONTACT_BYID);
	public static final String DISTRIBUTOR_CONTACT_BYID_BASE = DISTRIBUTOR_CONTACT_BYID + "/";

	public static final String DISTRIBUTOR_CONTACT_LASTID = SCHEME + AUTHORITY + "/lastid";
	public static final Uri URI_DISTRIBUTOR_CONTACT_LASTID = Uri.parse(DISTRIBUTOR_CONTACT_LASTID);
	public static final String DISTRIBUTOR_CONTACT_LASTID_BASE = DISTRIBUTOR_CONTACT_LASTID + "/";

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.DISTRIBUTOR_CONTACT_ID,
		DbHelper.DISTRIBUTOR_CONTACT_SERVER_ID,
		DbHelper.DISTRIBUTOR_CONTACT_DIRTY,
		DbHelper.DISTRIBUTOR_CONTACT_LAST_UPDATE, 
		DbHelper.DISTRIBUTOR_CONTACT_NAME, 
		DbHelper.DISTRIBUTOR_CONTACT_EMAIL1, 
		DbHelper.DISTRIBUTOR_CONTACT_EMAIL2, 
		DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER1, 
		DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER2, 
		DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER3, 
		DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER4, 
		DbHelper.DISTRIBUTOR_CONTACT_FK_BRAND
	};

	public DistributorContactDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("DistributorContactDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public Cursor listAll(){
		Cursor cursor = database.query(DbHelper.TABLE_DISTRIBUTOR_CONTACT,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){
		Cursor cursor = database.query(DbHelper.TABLE_DISTRIBUTOR_CONTACT,
			selectableColumns,
			DbHelper.DISTRIBUTOR_CONTACT_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){
		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"name, " +
			"email1, " +
			"email2, " +
			"phone_number1, " +
			"phone_number2, " +
			"phone_number3, " +
			"phone_number4, " +
			"fk_brand" +
			" FROM " + DbHelper.TABLE_DISTRIBUTOR_CONTACT;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public Cursor getLastId(){
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_DISTRIBUTOR_CONTACT +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;		
	}

// begin content-provider-interface

	@Override
	public boolean onCreate() {
		return false;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		long last_id = database.insert(DbHelper.TABLE_DISTRIBUTOR_CONTACT, null, values);
		return last_id;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		int rows_affected = database.update(DbHelper.TABLE_DISTRIBUTOR_CONTACT, values, DbHelper.DISTRIBUTOR_CONTACT_ID + " = " + selectionArgs[0], null);
		return rows_affected;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		int rows_affected = database.delete(DbHelper.TABLE_DISTRIBUTOR_CONTACT, DbHelper.DISTRIBUTOR_CONTACT_ID + " = " + selectionArgs[0], null);
		return rows_affected;
	}

// end content-provider-interface 

// reserved-for:android-sqlite-db.functions
//End of user code

//Start of user code reserved-for:android-sqlite-sync.functions
//reserved-for:android-sqlite-sync.functions
//End of user code

//Start of user code reserved-for:query3.functions
//reserved-for:query3.functions
//End of user code


//Start of user code reserved-for:android-sqlite-db.begin-default-query
	// TODO: I NEED TO KNOW HOW TO MAKE VARIOUS QUERIES DEPENDING ON THE URI
	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Cursor result = null;
		if (URI_DISTRIBUTOR_CONTACT_ALL.equals(uri)) {
			result = listAll();
		} else if(URI_DISTRIBUTOR_CONTACT_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		} else if(URI_DISTRIBUTOR_CONTACT_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		} else if(URI_DISTRIBUTOR_CONTACT_LASTID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:android-sqlite-db.begin-default-query
//End of user code

// Start of user code reserved-for:android-sqlite-sync.default-query

// reserved-for:android-sqlite-sync.default-query
// End of user code

//Start of user code reserved-for:android-sqlite-db.end-default-query
		return result;
	}
}
// reserved-for:android-sqlite-db.end-default-query
//End of user code

