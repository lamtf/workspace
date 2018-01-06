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
//import com.uisleandro.store.discount.model.DiscountDbHelper;

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
public class DiscountProvider extends ContentProvider {


	public static final String AUTHORITY = "com.spaceforsales.discount";
	public static final String SCHEME = "content://";

	public static final String DISCOUNT_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_DISCOUNT_ALL = Uri.parse(DISCOUNT_ALL);
	public static final String DISCOUNT_ALL_BASE = DISCOUNT_ALL + "/";

	public static final String DISCOUNT_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_DISCOUNT_SOME = Uri.parse(DISCOUNT_SOME);
	public static final String DISCOUNT_SOME_BASE = DISCOUNT_SOME + "/";

	public static final String DISCOUNT_BYID = SCHEME + AUTHORITY + "/byid";
	public static final Uri URI_DISCOUNT_BYID = Uri.parse(DISCOUNT_BYID);
	public static final String DISCOUNT_BYID_BASE = DISCOUNT_BYID + "/";

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.DISCOUNT_ID,
		DbHelper.DISCOUNT_SERVER_ID,
		DbHelper.DISCOUNT_DIRTY,
		DbHelper.DISCOUNT_LAST_UPDATE, 
		DbHelper.DISCOUNT_VALUE, 
		DbHelper.DISCOUNT_PERCENTAGE, 
		DbHelper.DISCOUNT_FK_PRODUCT, 
		DbHelper.DISCOUNT_FK_CATEGORY, 
		DbHelper.DISCOUNT_FK_BRAND, 
		DbHelper.DISCOUNT_FK_CLIENT_FROM_SYSTEM, 
		DbHelper.DISCOUNT_FK_GENDER
	};

	public DiscountDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("DiscountDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public Cursor listAll(){
		Cursor cursor = database.query(DbHelper.TABLE_DISCOUNT,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){
		Cursor cursor = database.query(DbHelper.TABLE_DISCOUNT,
			selectableColumns,
			DbHelper.DISCOUNT_ID + " = " + id,
			null, null, null, null);

		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"value, " +
			"percentage, " +
			"fk_product, " +
			"fk_category, " +
			"fk_brand, " +
			"fk_client_from_system, " +
			"fk_gender" +
			" FROM " + DbHelper.TABLE_DISCOUNT;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";

		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_DISCOUNT +";";
		Cursor cursor = database.rawQuery(query, null);
		
		return cursorToLong(cursor);
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
		long last_id = database.insert(DbHelper.TABLE_DISCOUNT, null, values);
		return last_id;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		int rows_affected = database.update(DbHelper.TABLE_DISCOUNT, values, DbHelper.DISCOUNT_ID + " = " + selectionArgs[0], null);
		return rows_affected;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		int rows_affected = database.delete(DbHelper.TABLE_DISCOUNT, DbHelper.DISCOUNT_ID + " = " + selectionArgs[0], null);
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
		if (URI_DISCOUNT_ALL.equals(uri)) {
			result = listAll();
		} else if(URI_DISCOUNT_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		} else if(URI_DISCOUNT_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
// reserved-for:android-sqlite-db.begin-default-query
//End of user code

// Start of user code reserved-for:android-sqlite-sync.default-query

// reserved-for:android-sqlite-sync.default-query
// End of user code

//Start of user code reserved-for:android-sqlite-db.end-default-query
		return result;
	}
// reserved-for:android-sqlite-db.end-default-query
//End of user code


//Start of user code reserved-for:android-sqlite-db.end-class
}
// reserved-for:android-sqlite-db.end-class
//End of user code


