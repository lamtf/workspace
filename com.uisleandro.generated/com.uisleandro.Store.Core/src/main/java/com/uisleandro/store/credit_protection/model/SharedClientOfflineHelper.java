package com.uisleandro.store.credit_protection.model;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uisleandro.store.credit_protection.view.SharedClientDataView

public class SharedClientOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public SharedClientOfflineHelper (Context context) {
		this.context = context;
	}

	public SharedClientOfflineHelper (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("SharedClientOfflineHelper", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public long insert(SharedClientView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.SHARED_CLIENT_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.SHARED_CLIENT_DIRTY, that.isDirty());
		values.put(DbHelper.SHARED_CLIENT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.SHARED_CLIENT_NAME, that.getName());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_DATE, that.getBirthDate());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_CITY, that.getBirthCity());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_STATE, that.getBirthState());
		values.put(DbHelper.SHARED_CLIENT_MOTHERS_NAME, that.getMothersName());
		values.put(DbHelper.SHARED_CLIENT_FATHERS_NAME, that.getFathersName());
		values.put(DbHelper.SHARED_CLIENT_PROFESSION, that.getProfession());
		values.put(DbHelper.SHARED_CLIENT_ZIP_CODE, that.getZipCode());
		values.put(DbHelper.SHARED_CLIENT_ADDRESS, that.getAddress());
		values.put(DbHelper.SHARED_CLIENT_NEIGHBORHOOD, that.getNeighborhood());
		values.put(DbHelper.SHARED_CLIENT_CITY, that.getCity());
		values.put(DbHelper.SHARED_CLIENT_STATE, that.getState());
		values.put(DbHelper.SHARED_CLIENT_COMPLEMENT, that.getComplement());
		if(that.getFkFkCountry() > 0){
			values.put(DbHelper.SHARED_CLIENT_FK_COUNTRY, that.getFkFkCountry());
		}
		long last_id = database.insert(DbHelper.TABLE_SHARED_CLIENT, null, values);
		return last_id;
	}

	public int update(SharedClientView that){
		ContentValues values = new ContentValues();
		if(that.getServerId() > 0){
			values.put(DbHelper.SHARED_CLIENT_SERVER_ID, that.getServerId());
		}
		values.put(DbHelper.SHARED_CLIENT_DIRTY, that.isDirty());

		values.put(DbHelper.SHARED_CLIENT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.SHARED_CLIENT_NAME, that.getName());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_DATE, that.getBirthDate());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_CITY, that.getBirthCity());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_STATE, that.getBirthState());
		values.put(DbHelper.SHARED_CLIENT_MOTHERS_NAME, that.getMothersName());
		values.put(DbHelper.SHARED_CLIENT_FATHERS_NAME, that.getFathersName());
		values.put(DbHelper.SHARED_CLIENT_PROFESSION, that.getProfession());
		values.put(DbHelper.SHARED_CLIENT_ZIP_CODE, that.getZipCode());
		values.put(DbHelper.SHARED_CLIENT_ADDRESS, that.getAddress());
		values.put(DbHelper.SHARED_CLIENT_NEIGHBORHOOD, that.getNeighborhood());
		values.put(DbHelper.SHARED_CLIENT_CITY, that.getCity());
		values.put(DbHelper.SHARED_CLIENT_STATE, that.getState());
		values.put(DbHelper.SHARED_CLIENT_COMPLEMENT, that.getComplement());
		if(that.getFkFkCountry() > 0){
			values.put(DbHelper.SHARED_CLIENT_FK_COUNTRY, that.getFkFkCountry());
		}
		int rows_affected = database.update(DbHelper.TABLE_SHARED_CLIENT, values, DbHelper.SHARED_CLIENT_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public List<SharedClientDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.name, " +
		"t0.birth_date, " +
		"t0.birth_city, " +
		"t0.birth_state, " +
		"t0.mothers_name, " +
		"t0.fathers_name, " +
		"t0.profession, " +
		"t0.zip_code, " +
		"t0.address, " +
		"t0.neighborhood, " +
		"t0.city, " +
		"t0.state, " +
		"t0.complement, " +
		"t15.server_id as fk_country" +
		" FROM "+DbHelper.TABLE_SHARED_CLIENT+" t0" +
		" INNER JOIN "+DbHelper.TABLE_COUNTRY+" t1 ON t0.fk_country = t1.id";		query += " WHERE t0.server_id IS NULL";
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Log.wtf("rest-api", query);
		List<SharedClientView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(SharedClientView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<SharedClientDataView> listForUpdateOnServer(long page_count, long page_size){
		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo
		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.name, " +
		"t0.birth_date, " +
		"t0.birth_city, " +
		"t0.birth_state, " +
		"t0.mothers_name, " +
		"t0.fathers_name, " +
		"t0.profession, " +
		"t0.zip_code, " +
		"t0.address, " +
		"t0.neighborhood, " +
		"t0.city, " +
		"t0.state, " +
		"t0.complement" +
		" FROM "+DbHelper.TABLE_SHARED_CLIENT+" t0" +
		" INNER JOIN "+DbHelper.TABLE_COUNTRY+" t1 ON t0.fk_country = t1.id";		query += " WHERE t0." + DbHelper.SHARED_CLIENT_DIRTY + " = 1";
		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		//Log.wtf("rest-api", query);
		List<SharedClientView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(SharedClientView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();
		values.put(DbHelper.SHARED_CLIENT_SERVER_ID, remote_id);
		values.put(DbHelper.SHARED_CLIENT_LAST_UPDATE_TIME, last_update_time);
		values.put(DbHelper.SHARED_CLIENT_DIRTY, 0);
		int rows_affected = database.update(
			DbHelper.TABLE_SHARED_CLIENT,
			values,
			DbHelper.SHARED_CLIENT_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){
		long result = 0;
		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_SHARED_CLIENT +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	}

	//the idea is that i don't want to iterate over all the data,
	//bringing all the data to the server is expensive, even if the return value is null sometimes
	//just bring from the server what is newer than my newer data, for updating
	//the implementation is also easier :D
	public long getLastUpdateTime(){
		long result = 0;
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_SHARED_CLIENT+"';";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_SHARED_CLIENT + " ) WHERE table_name = '" + DbHelper.TABLE_SHARED_CLIENT + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);
	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_SHARED_CLIENT + "';";
		database.rawQuery(query, null);
	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){
		String query = "UPDATE " + DbHelper.TABLE_SHARED_CLIENT + " SET "+
		"fk_country = ( SELECT id FROM " + DbHelper.TABLE_COUNTRY + " WHERE " + DbHelper.TABLE_COUNTRY + ".server_id = " + DbHelper.TABLE_SHARED_CLIENT + ".fk_country );";		int result = 0;
		Cursor cursor = database.rawQuery(query, null);
		return cursorToInteger(cursor);
	}



}
