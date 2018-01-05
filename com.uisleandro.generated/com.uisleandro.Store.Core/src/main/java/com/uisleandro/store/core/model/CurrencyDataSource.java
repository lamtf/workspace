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
//import com.uisleandro.store.core.model.CurrencyDbHelper;

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
public class CurrencyDataSource extends ContentProvider {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.CURRENCY_ID,
		DbHelper.CURRENCY_SERVER_ID,
		DbHelper.CURRENCY_DIRTY,
		DbHelper.CURRENCY_LAST_UPDATE, 
		DbHelper.CURRENCY_ABBREVIATURE, 
		DbHelper.CURRENCY_DESCRIPTION
	};

	public CurrencyDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("CurrencyDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public long cursorToLong(Cursor cursor){
		long result = 0L;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getLong(0);
		}
		cursor.close();
		return result;
	}

	public int cursorToInteger(Cursor cursor){
		int result = 0;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		cursor.close();
		return result;
	}


	public long insert(CurrencyView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.CURRENCY_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.CURRENCY_DIRTY, that.isDirty());

		values.put(DbHelper.CURRENCY_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.CURRENCY_ABBREVIATURE, that.getAbbreviature());
		values.put(DbHelper.CURRENCY_DESCRIPTION, that.getDescription());
		long last_id = database.insert(DbHelper.TABLE_CURRENCY, null, values);
		return last_id;
	}

	public int update(CurrencyView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.CURRENCY_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.CURRENCY_DIRTY, that.isDirty());

		values.put(DbHelper.CURRENCY_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.CURRENCY_ABBREVIATURE, that.getAbbreviature());
		values.put(DbHelper.CURRENCY_DESCRIPTION, that.getDescription());

		int rows_affected = database.update(DbHelper.TABLE_CURRENCY, values, DbHelper.CURRENCY_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public long delete(CurrencyView that){
		return database.delete(DbHelper.TABLE_CURRENCY, DbHelper.CURRENCY_ID + " = " + String.valueOf(that.getId()), null);
	}

	public long deleteById(long id){
		return database.delete(DbHelper.TABLE_CURRENCY, DbHelper.CURRENCY_ID + " = " + String.valueOf(id), null);
	}

	public Cursor listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_CURRENCY,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_CURRENCY,
			selectableColumns,
			DbHelper.CURRENCY_ID + " = " + id,
			null, null, null, null);

		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"abbreviature, " +
			"description" +
			" FROM " + DbHelper.TABLE_CURRENCY;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";

		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_CURRENCY +";";
		Cursor cursor = database.rawQuery(query, null);
		
		return cursorToLong(cursor);
	}



//BEGIN THINGS FOR CONTENT PROVIDER

	@Override
	public boolean onCreate() {
		return false;
	}

	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		return null;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}

	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		return null;
	}

	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		return 0;
	}

	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		return 0;
	}


//END THINGS FOR CONTENT PROVIDER


// reserved-for:android-sqlite-db.functions
//End of user code

//Start of user code reserved-for:android-sqlite-sync.functions
//reserved-for:android-sqlite-sync.functions
//End of user code

//Start of user code reserved-for:query3.functions
//reserved-for:query3.functions
//End of user code

}





