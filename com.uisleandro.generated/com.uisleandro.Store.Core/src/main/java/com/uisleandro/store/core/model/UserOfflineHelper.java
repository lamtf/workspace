package com.uisleandro.store.core.model;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uisleandro.store.core.view.UserDataView

public class UserOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public UserOfflineHelper (Context context) {
		this.context = context;
	}

	public UserOfflineHelper (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("UserOfflineHelper", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public long insert(UserView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.USER_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.USER_DIRTY, that.isDirty());
		values.put(DbHelper.USER_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkFkSystem() > 0){
			values.put(DbHelper.USER_FK_SYSTEM, that.getFkFkSystem());
		}
		if(that.getFkFkRole() > 0){
			values.put(DbHelper.USER_FK_ROLE, that.getFkFkRole());
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
		if(that.getFkFkSystem() > 0){
			values.put(DbHelper.USER_FK_SYSTEM, that.getFkFkSystem());
		}
		if(that.getFkFkRole() > 0){
			values.put(DbHelper.USER_FK_ROLE, that.getFkFkRole());
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

	public List<UserDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t2.server_id as fk_system, " +
		"t3.server_id as fk_role, " +
		"t0.username, " +
		"t0.password, " +
		"t0.name, " +
		"t0.email, " +
		"t0.last_use_time, " +
		"t0.last_error_time, " +
		"t0.error_count, " +
		"t0.active" +
		" FROM "+DbHelper.TABLE_USER+" t0" +
		" INNER JOIN "+DbHelper.TABLE_SYSTEM+" t1 ON t0.fk_system = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_ROLE+" t2 ON t0.fk_role = t2.id";		query += " WHERE t0.server_id IS NULL";
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Log.wtf("rest-api", query);
		List<UserView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(UserView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<UserDataView> listForUpdateOnServer(long page_count, long page_size){
		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo
		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.username, " +
		"t0.password, " +
		"t0.name, " +
		"t0.email, " +
		"t0.last_use_time, " +
		"t0.last_error_time, " +
		"t0.error_count, " +
		"t0.active" +
		" FROM "+DbHelper.TABLE_USER+" t0" +
		" INNER JOIN "+DbHelper.TABLE_SYSTEM+" t1 ON t0.fk_system = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_ROLE+" t2 ON t0.fk_role = t2.id";		query += " WHERE t0." + DbHelper.USER_DIRTY + " = 1";
		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		//Log.wtf("rest-api", query);
		List<UserView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(UserView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();
		values.put(DbHelper.USER_SERVER_ID, remote_id);
		values.put(DbHelper.USER_LAST_UPDATE_TIME, last_update_time);
		values.put(DbHelper.USER_DIRTY, 0);
		int rows_affected = database.update(
			DbHelper.TABLE_USER,
			values,
			DbHelper.USER_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){
		long result = 0;
		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_USER +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	}

	//the idea is that i don't want to iterate over all the data,
	//bringing all the data to the server is expensive, even if the return value is null sometimes
	//just bring from the server what is newer than my newer data, for updating
	//the implementation is also easier :D
	public long getLastUpdateTime(){
		long result = 0;
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_USER+"';";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_USER + " ) WHERE table_name = '" + DbHelper.TABLE_USER + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);
	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_USER + "';";
		database.rawQuery(query, null);
	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){
		String query = "UPDATE " + DbHelper.TABLE_USER + " SET "+
		"fk_system = ( SELECT id FROM " + DbHelper.TABLE_SYSTEM + " WHERE " + DbHelper.TABLE_SYSTEM + ".server_id = " + DbHelper.TABLE_USER + ".fk_system ), " +
		"fk_role = ( SELECT id FROM " + DbHelper.TABLE_ROLE + " WHERE " + DbHelper.TABLE_ROLE + ".server_id = " + DbHelper.TABLE_USER + ".fk_role );";		int result = 0;
		Cursor cursor = database.rawQuery(query, null);
		return cursorToInteger(cursor);
	}



}
