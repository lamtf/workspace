//Start of user code reserved-for:android-sqlite-db.imports
package com.uisleandro.store.Core.model;  

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
//old
//import com.uisleandro.util.LongDateFormatter;
//import com.uisleandro.store.model.DbHelper;
//import com.uisleandro.store.core.model.UserDbHelper;

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
public class UserDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.USER_ID,
		DbHelper.USER_SERVER_ID,
		DbHelper.USER_DIRTY,
		DbHelper.USER_LAST_UPDATE, 
		DbHelper.USER_FK_SYSTEM, 
		DbHelper.USER_FK_ROLE, 
		DbHelper.USER_USERNAME, 
		DbHelper.USER_PASSWORD, 
		DbHelper.USER_NAME, 
		DbHelper.USER_EMAIL, 
		DbHelper.USER_LAST_USE_TIME, 
		DbHelper.USER_LAST_ERROR_TIME, 
		DbHelper.USER_ERROR_COUNT, 
		DbHelper.USER_ACTIVE
	};

	public UserDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("UserDataSource", "Exception: "+Log.getStackTraceString(e));
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


	public long insert(UserView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.USER_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.USER_DIRTY, that.isDirty());

		values.put(DbHelper.USER_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSystem() > 0){
			values.put(DbHelper.USER_FK_SYSTEM, that.getFkSystem());
		}
		if(that.getFkRole() > 0){
			values.put(DbHelper.USER_FK_ROLE, that.getFkRole());
		}
		values.put(DbHelper.USER_USERNAME, that.getUsername());
		values.put(DbHelper.USER_PASSWORD, that.getPassword());
		values.put(DbHelper.USER_NAME, that.getName());
		values.put(DbHelper.USER_EMAIL, that.getEmail());
		values.put(DbHelper.USER_LAST_USE_TIME, that.getLastUseTime());
		values.put(DbHelper.USER_LAST_ERROR_TIME, that.getLastErrorTime());
		values.put(DbHelper.USER_ERROR_COUNT, that.getErrorCount());
		values.put(DbHelper.USER_ACTIVE, that.getActive());
		long last_id = database.insert(DbHelper.TABLE_USER, null, values);
		return last_id;
	}

	public int update(UserView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.USER_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.USER_DIRTY, that.isDirty());

		values.put(DbHelper.USER_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSystem() > 0){
			values.put(DbHelper.USER_FK_SYSTEM, that.getFkSystem());
		}
		if(that.getFkRole() > 0){
			values.put(DbHelper.USER_FK_ROLE, that.getFkRole());
		}
		values.put(DbHelper.USER_USERNAME, that.getUsername());
		values.put(DbHelper.USER_PASSWORD, that.getPassword());
		values.put(DbHelper.USER_NAME, that.getName());
		values.put(DbHelper.USER_EMAIL, that.getEmail());
		values.put(DbHelper.USER_LAST_USE_TIME, that.getLastUseTime());
		values.put(DbHelper.USER_LAST_ERROR_TIME, that.getLastErrorTime());
		values.put(DbHelper.USER_ERROR_COUNT, that.getErrorCount());
		values.put(DbHelper.USER_ACTIVE, that.getActive());

		int rows_affected = database.update(DbHelper.TABLE_USER, values, DbHelper.USER_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public long delete(UserView that){
		return database.delete(DbHelper.TABLE_USER, DbHelper.USER_ID + " = " + String.valueOf(that.getId()), null);
	}

	public long deleteById(long id){
		return database.delete(DbHelper.TABLE_USER, DbHelper.USER_ID + " = " + String.valueOf(id), null);
	}

	public Cursor listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_USER,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_USER,
			selectableColumns,
			DbHelper.USER_ID + " = " + id,
			null, null, null, null);

		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_system, " +
			"fk_role, " +
			"username, " +
			"password, " +
			"name, " +
			"email, " +
			"last_use_time, " +
			"last_error_time, " +
			"error_count, " +
			"active" +
			" FROM " + DbHelper.TABLE_USER;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";

		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_USER +";";
		Cursor cursor = database.rawQuery(query, null);
		
		return cursorToLong(cursor);
	}

// reserved-for:android-sqlite-db.functions
//End of user code

//Start of user code reserved-for:android-sqlite-sync.functions
//reserved-for:android-sqlite-sync.functions
//End of user code

//Start of user code reserved-for:query3.functions
//reserved-for:query3.functions
//End of user code

}

