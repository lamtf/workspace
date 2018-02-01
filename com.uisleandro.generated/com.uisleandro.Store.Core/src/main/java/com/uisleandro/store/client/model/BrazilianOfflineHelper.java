package com.uisleandro.store.client.model;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uisleandro.store.client.view.BrazilianDataView

public class BrazilianOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public BrazilianOfflineHelper (Context context) {
		this.context = context;
	}

	public BrazilianOfflineHelper (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("BrazilianOfflineHelper", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public long insert(BrazilianView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.BRAZILIAN_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.BRAZILIAN_DIRTY, that.isDirty());
		values.put(DbHelper.BRAZILIAN_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.BRAZILIAN_CPF, that.getCpf());
		values.put(DbHelper.BRAZILIAN_RG, that.getRg());
		if(that.getFkFkBasicClient() > 0){
			values.put(DbHelper.BRAZILIAN_FK_BASIC_CLIENT, that.getFkFkBasicClient());
		}
		long last_id = database.insert(DbHelper.TABLE_BRAZILIAN, null, values);
		return last_id;
	}

	public int update(BrazilianView that){
		ContentValues values = new ContentValues();
		if(that.getServerId() > 0){
			values.put(DbHelper.BRAZILIAN_SERVER_ID, that.getServerId());
		}
		values.put(DbHelper.BRAZILIAN_DIRTY, that.isDirty());

		values.put(DbHelper.BRAZILIAN_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.BRAZILIAN_CPF, that.getCpf());
		values.put(DbHelper.BRAZILIAN_RG, that.getRg());
		if(that.getFkFkBasicClient() > 0){
			values.put(DbHelper.BRAZILIAN_FK_BASIC_CLIENT, that.getFkFkBasicClient());
		}
		int rows_affected = database.update(DbHelper.TABLE_BRAZILIAN, values, DbHelper.BRAZILIAN_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public List<BrazilianDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.cpf, " +
		"t0.rg, " +
		"t4.server_id as fk_basic_client" +
		" FROM "+DbHelper.TABLE_BRAZILIAN+" t0" +
		" INNER JOIN "+DbHelper.TABLE_BASIC_CLIENT+" t1 ON t0.fk_basic_client = t1.id";		query += " WHERE t0.server_id IS NULL";
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Log.wtf("rest-api", query);
		List<BrazilianView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(BrazilianView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<BrazilianDataView> listForUpdateOnServer(long page_count, long page_size){
		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo
		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.cpf, " +
		"t0.rg" +
		" FROM "+DbHelper.TABLE_BRAZILIAN+" t0" +
		" INNER JOIN "+DbHelper.TABLE_BASIC_CLIENT+" t1 ON t0.fk_basic_client = t1.id";		query += " WHERE t0." + DbHelper.BRAZILIAN_DIRTY + " = 1";
		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		//Log.wtf("rest-api", query);
		List<BrazilianView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(BrazilianView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();
		values.put(DbHelper.BRAZILIAN_SERVER_ID, remote_id);
		values.put(DbHelper.BRAZILIAN_LAST_UPDATE_TIME, last_update_time);
		values.put(DbHelper.BRAZILIAN_DIRTY, 0);
		int rows_affected = database.update(
			DbHelper.TABLE_BRAZILIAN,
			values,
			DbHelper.BRAZILIAN_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){
		long result = 0;
		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_BRAZILIAN +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	}

	//the idea is that i don't want to iterate over all the data,
	//bringing all the data to the server is expensive, even if the return value is null sometimes
	//just bring from the server what is newer than my newer data, for updating
	//the implementation is also easier :D
	public long getLastUpdateTime(){
		long result = 0;
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_BRAZILIAN+"';";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_BRAZILIAN + " ) WHERE table_name = '" + DbHelper.TABLE_BRAZILIAN + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);
	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_BRAZILIAN + "';";
		database.rawQuery(query, null);
	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){
		String query = "UPDATE " + DbHelper.TABLE_BRAZILIAN + " SET "+
		"fk_basic_client = ( SELECT id FROM " + DbHelper.TABLE_BASIC_CLIENT + " WHERE " + DbHelper.TABLE_BASIC_CLIENT + ".server_id = " + DbHelper.TABLE_BRAZILIAN + ".fk_basic_client );";		int result = 0;
		Cursor cursor = database.rawQuery(query, null);
		return cursorToInteger(cursor);
	}



}
