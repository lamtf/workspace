package com.uisleandro.store.core.model;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uisleandro.store.core.view.DbLogDataView

public class DbLogOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public DbLogOfflineHelper (Context context) {
		this.context = context;
	}

	public DbLogOfflineHelper (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("DbLogOfflineHelper", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public long insert(DbLogView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.DB_LOG_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.DB_LOG_DIRTY, that.isDirty());
		values.put(DbHelper.DB_LOG_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.DB_LOG_ACTION_NAME, that.getActionName());
		values.put(DbHelper.DB_LOG_PARAMETER, that.getParameter());
		if(that.getFkFkUser() > 0){
			values.put(DbHelper.DB_LOG_FK_USER, that.getFkFkUser());
		}
		long last_id = database.insert(DbHelper.TABLE_DB_LOG, null, values);
		return last_id;
	}

	public int update(DbLogView that){
		ContentValues values = new ContentValues();
		if(that.getServerId() > 0){
			values.put(DbHelper.DB_LOG_SERVER_ID, that.getServerId());
		}
		values.put(DbHelper.DB_LOG_DIRTY, that.isDirty());

		values.put(DbHelper.DB_LOG_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.DB_LOG_ACTION_NAME, that.getActionName());
		values.put(DbHelper.DB_LOG_PARAMETER, that.getParameter());
		if(that.getFkFkUser() > 0){
			values.put(DbHelper.DB_LOG_FK_USER, that.getFkFkUser());
		}
		int rows_affected = database.update(DbHelper.TABLE_DB_LOG, values, DbHelper.DB_LOG_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public List<DbLogDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.action_name, " +
		"t0.parameter, " +
		"t4.server_id as fk_user" +
		" FROM "+DbHelper.TABLE_DB_LOG+" t0" +
		" INNER JOIN "+DbHelper.TABLE_USER+" t1 ON t0.fk_user = t1.id";		query += " WHERE t0.server_id IS NULL";
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Log.wtf("rest-api", query);
		List<DbLogView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(DbLogView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<DbLogDataView> listForUpdateOnServer(long page_count, long page_size){
		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo
		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.action_name, " +
		"t0.parameter" +
		" FROM "+DbHelper.TABLE_DB_LOG+" t0" +
		" INNER JOIN "+DbHelper.TABLE_USER+" t1 ON t0.fk_user = t1.id";		query += " WHERE t0." + DbHelper.DB_LOG_DIRTY + " = 1";
		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		//Log.wtf("rest-api", query);
		List<DbLogView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(DbLogView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();
		values.put(DbHelper.DB_LOG_SERVER_ID, remote_id);
		values.put(DbHelper.DB_LOG_LAST_UPDATE_TIME, last_update_time);
		values.put(DbHelper.DB_LOG_DIRTY, 0);
		int rows_affected = database.update(
			DbHelper.TABLE_DB_LOG,
			values,
			DbHelper.DB_LOG_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){
		long result = 0;
		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_DB_LOG +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	}

	//the idea is that i don't want to iterate over all the data,
	//bringing all the data to the server is expensive, even if the return value is null sometimes
	//just bring from the server what is newer than my newer data, for updating
	//the implementation is also easier :D
	public long getLastUpdateTime(){
		long result = 0;
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_DB_LOG+"';";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_DB_LOG + " ) WHERE table_name = '" + DbHelper.TABLE_DB_LOG + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);
	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_DB_LOG + "';";
		database.rawQuery(query, null);
	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){
		String query = "UPDATE " + DbHelper.TABLE_DB_LOG + " SET "+
		"fk_user = ( SELECT id FROM " + DbHelper.TABLE_USER + " WHERE " + DbHelper.TABLE_USER + ".server_id = " + DbHelper.TABLE_DB_LOG + ".fk_user );";		int result = 0;
		Cursor cursor = database.rawQuery(query, null);
		return cursorToInteger(cursor);
	}



}
