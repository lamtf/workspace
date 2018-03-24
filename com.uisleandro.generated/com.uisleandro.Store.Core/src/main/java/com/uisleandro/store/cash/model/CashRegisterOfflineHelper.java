package com.uisleandro.store.cash.model;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.uisleandro.store.DbHelper;
import com.uisleandro.store.cash.view.CashRegisterDataView;

public class CashRegisterOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public CashRegisterOfflineHelper (Context context) {
		this.context = context;
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("CashRegisterOfflineHelper", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public long insert(CashRegisterDataView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.CASH_REGISTER_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.CASH_REGISTER_DIRTY, that.isDirty());
		values.put(DbHelper.CASH_REGISTER_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkFkUser() > 0){
			values.put(DbHelper.CASH_REGISTER_FK_USER, that.getFkFkUser());
		}
		values.put(DbHelper.CASH_REGISTER_OPENING_VALUE, that.getOpeningValue());
		values.put(DbHelper.CASH_REGISTER_RECEIVED_VALUE, that.getReceivedValue());
		values.put(DbHelper.CASH_REGISTER_CLOSING_VALUE, that.getClosingValue());
		long last_id = database.insert(DbHelper.TABLE_CASH_REGISTER, null, values);
		return last_id;
	}

	public int update(CashRegisterDataView that){
		ContentValues values = new ContentValues();
		if(that.getServerId() > 0){
			values.put(DbHelper.CASH_REGISTER_SERVER_ID, that.getServerId());
		}
		values.put(DbHelper.CASH_REGISTER_DIRTY, that.isDirty());

		values.put(DbHelper.CASH_REGISTER_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkFkUser() > 0){
			values.put(DbHelper.CASH_REGISTER_FK_USER, that.getFkFkUser());
		}
		values.put(DbHelper.CASH_REGISTER_OPENING_VALUE, that.getOpeningValue());
		values.put(DbHelper.CASH_REGISTER_RECEIVED_VALUE, that.getReceivedValue());
		values.put(DbHelper.CASH_REGISTER_CLOSING_VALUE, that.getClosingValue());
		int rows_affected = database.update(DbHelper.TABLE_CASH_REGISTER, values, DbHelper.CASH_REGISTER_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public List<CashRegisterDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t2.server_id as fk_user, " +
		"t0.opening_value, " +
		"t0.received_value, " +
		"t0.closing_value" +
		" FROM "+DbHelper.TABLE_CASH_REGISTER+" t0" +
		" INNER JOIN "+DbHelper.TABLE_USER+" t1 ON t0.fk_user = t1.id";		query += " WHERE t0.server_id IS NULL";
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Log.wtf("rest-api", query);
		List<CashRegisterDataView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(CashRegisterDataView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<CashRegisterDataView> listForUpdateOnServer(long page_count, long page_size){
		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo
		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.opening_value, " +
		"t0.received_value, " +
		"t0.closing_value" +
		" FROM "+DbHelper.TABLE_CASH_REGISTER+" t0" +
		" INNER JOIN "+DbHelper.TABLE_USER+" t1 ON t0.fk_user = t1.id";		query += " WHERE t0." + DbHelper.CASH_REGISTER_DIRTY + " = 1";
		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		//Log.wtf("rest-api", query);
		List<CashRegisterDataView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(CashRegisterDataView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();
		values.put(DbHelper.CASH_REGISTER_SERVER_ID, remote_id);
		values.put(DbHelper.CASH_REGISTER_LAST_UPDATE_TIME, last_update_time);
		values.put(DbHelper.CASH_REGISTER_DIRTY, 0);
		int rows_affected = database.update(
			DbHelper.TABLE_CASH_REGISTER,
			values,
			DbHelper.CASH_REGISTER_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){
		long result = 0;
		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_CASH_REGISTER +";";
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
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_CASH_REGISTER+"';";
		Cursor cursor = database.rawQuery(query, null);
		long res = cursor.getLong(0);
		cursor.close();
		return res;
	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_CASH_REGISTER + " ) WHERE table_name = '" + DbHelper.TABLE_CASH_REGISTER + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);
	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_CASH_REGISTER + "';";
		database.rawQuery(query, null);
	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){
		String query = "UPDATE " + DbHelper.TABLE_CASH_REGISTER + " SET "+
		"fk_user = ( SELECT id FROM " + DbHelper.TABLE_USER + " WHERE " + DbHelper.TABLE_USER + ".server_id = " + DbHelper.TABLE_CASH_REGISTER + ".fk_user );";		int result = 0;
		Cursor cursor = database.rawQuery(query, null);
		int res = cursor.getInt(0);
		cursor.close();
		return res;
	}



}
