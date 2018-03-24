package com.uisleandro.store.client.model;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.uisleandro.store.DbHelper;
import com.uisleandro.store.client.view.ClientFromSystemDataView;

public class ClientFromSystemOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public ClientFromSystemOfflineHelper (Context context) {
		this.context = context;
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("ClientFromSystemOfflineHelper", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public long insert(ClientFromSystemDataView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.CLIENT_FROM_SYSTEM_DIRTY, that.isDirty());
		values.put(DbHelper.CLIENT_FROM_SYSTEM_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkFkSystem() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_SYSTEM, that.getFkFkSystem());
		}
		if(that.getFkFkBasicClient() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_BASIC_CLIENT, that.getFkFkBasicClient());
		}
		if(that.getFkFkSharedClient() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_SHARED_CLIENT, that.getFkFkSharedClient());
		}
		if(that.getFkFkUser() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_USER, that.getFkFkUser());
		}
		long last_id = database.insert(DbHelper.TABLE_CLIENT_FROM_SYSTEM, null, values);
		return last_id;
	}

	public int update(ClientFromSystemDataView that){
		ContentValues values = new ContentValues();
		if(that.getServerId() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_SERVER_ID, that.getServerId());
		}
		values.put(DbHelper.CLIENT_FROM_SYSTEM_DIRTY, that.isDirty());

		values.put(DbHelper.CLIENT_FROM_SYSTEM_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkFkSystem() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_SYSTEM, that.getFkFkSystem());
		}
		if(that.getFkFkBasicClient() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_BASIC_CLIENT, that.getFkFkBasicClient());
		}
		if(that.getFkFkSharedClient() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_SHARED_CLIENT, that.getFkFkSharedClient());
		}
		if(that.getFkFkUser() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_USER, that.getFkFkUser());
		}
		int rows_affected = database.update(DbHelper.TABLE_CLIENT_FROM_SYSTEM, values, DbHelper.CLIENT_FROM_SYSTEM_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public List<ClientFromSystemDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t2.server_id as fk_system, " +
		"t3.server_id as fk_basic_client, " +
		"t4.server_id as fk_shared_client, " +
		"t5.server_id as fk_user" +
		" FROM "+DbHelper.TABLE_CLIENT_FROM_SYSTEM+" t0" +
		" INNER JOIN "+DbHelper.TABLE_SYSTEM+" t1 ON t0.fk_system = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_BASIC_CLIENT+" t2 ON t0.fk_basic_client = t2.id" +
		" INNER JOIN "+DbHelper.TABLE_SHARED_CLIENT+" t3 ON t0.fk_shared_client = t3.id" +
		" INNER JOIN "+DbHelper.TABLE_USER+" t4 ON t0.fk_user = t4.id";		query += " WHERE t0.server_id IS NULL";
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Log.wtf("rest-api", query);
		List<ClientFromSystemDataView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(ClientFromSystemDataView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<ClientFromSystemDataView> listForUpdateOnServer(long page_count, long page_size){
		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo
		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update" +
		" FROM "+DbHelper.TABLE_CLIENT_FROM_SYSTEM+" t0" +
		" INNER JOIN "+DbHelper.TABLE_SYSTEM+" t1 ON t0.fk_system = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_BASIC_CLIENT+" t2 ON t0.fk_basic_client = t2.id" +
		" INNER JOIN "+DbHelper.TABLE_SHARED_CLIENT+" t3 ON t0.fk_shared_client = t3.id" +
		" INNER JOIN "+DbHelper.TABLE_USER+" t4 ON t0.fk_user = t4.id";		query += " WHERE t0." + DbHelper.CLIENT_FROM_SYSTEM_DIRTY + " = 1";
		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		//Log.wtf("rest-api", query);
		List<ClientFromSystemDataView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(ClientFromSystemDataView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();
		values.put(DbHelper.CLIENT_FROM_SYSTEM_SERVER_ID, remote_id);
		values.put(DbHelper.CLIENT_FROM_SYSTEM_LAST_UPDATE_TIME, last_update_time);
		values.put(DbHelper.CLIENT_FROM_SYSTEM_DIRTY, 0);
		int rows_affected = database.update(
			DbHelper.TABLE_CLIENT_FROM_SYSTEM,
			values,
			DbHelper.CLIENT_FROM_SYSTEM_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){
		long result = 0;
		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_CLIENT_FROM_SYSTEM +";";
		Cursor cursor = database.rawQuery(query, null);
		long res = cursor.getLong(0);
		cursor.close();
		return res;
	}

	//the idea is that i don't want to iterate over all the data,
	//bringing all the data to the server is expensive, even if the return value is null sometimes
	//just bring from the server what is newer than my newer data, for updating
	//the implementation is also easier :D
	public long getLastUpdateTime(){
		long result = 0;
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_CLIENT_FROM_SYSTEM+"';";
		Cursor cursor = database.rawQuery(query, null);
		long res = cursor.getLong(0);
		cursor.close();
		return res;
	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_CLIENT_FROM_SYSTEM + " ) WHERE table_name = '" + DbHelper.TABLE_CLIENT_FROM_SYSTEM + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);
	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_CLIENT_FROM_SYSTEM + "';";
		database.rawQuery(query, null);
	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){
		String query = "UPDATE " + DbHelper.TABLE_CLIENT_FROM_SYSTEM + " SET "+
		"fk_system = ( SELECT id FROM " + DbHelper.TABLE_SYSTEM + " WHERE " + DbHelper.TABLE_SYSTEM + ".server_id = " + DbHelper.TABLE_CLIENT_FROM_SYSTEM + ".fk_system ), " +
		"fk_basic_client = ( SELECT id FROM " + DbHelper.TABLE_BASIC_CLIENT + " WHERE " + DbHelper.TABLE_BASIC_CLIENT + ".server_id = " + DbHelper.TABLE_CLIENT_FROM_SYSTEM + ".fk_basic_client ), " +
		"fk_shared_client = ( SELECT id FROM " + DbHelper.TABLE_SHARED_CLIENT + " WHERE " + DbHelper.TABLE_SHARED_CLIENT + ".server_id = " + DbHelper.TABLE_CLIENT_FROM_SYSTEM + ".fk_shared_client ), " +
		"fk_user = ( SELECT id FROM " + DbHelper.TABLE_USER + " WHERE " + DbHelper.TABLE_USER + ".server_id = " + DbHelper.TABLE_CLIENT_FROM_SYSTEM + ".fk_user );";		int result = 0;
		Cursor cursor = database.rawQuery(query, null);
		int res = cursor.getInt(0);
		cursor.close();
		return res;
	}



}
