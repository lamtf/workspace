package com.uisleandro.store.supply.model;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uisleandro.store.supply.view.DistributorContactDataView

public class DistributorContactOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public DistributorContactOfflineHelper (Context context) {
		this.context = context;
	}

	public DistributorContactOfflineHelper (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("DistributorContactOfflineHelper", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public long insert(DistributorContactView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.DISTRIBUTOR_CONTACT_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.DISTRIBUTOR_CONTACT_DIRTY, that.isDirty());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_NAME, that.getName());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_EMAIL1, that.getEmail1());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_EMAIL2, that.getEmail2());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER1, that.getPhoneNumber1());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER2, that.getPhoneNumber2());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER3, that.getPhoneNumber3());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER4, that.getPhoneNumber4());
		if(that.getFkFkBrand() > 0){
			values.put(DbHelper.DISTRIBUTOR_CONTACT_FK_BRAND, that.getFkFkBrand());
		}
		long last_id = database.insert(DbHelper.TABLE_DISTRIBUTOR_CONTACT, null, values);
		return last_id;
	}

	public int update(DistributorContactView that){
		ContentValues values = new ContentValues();
		if(that.getServerId() > 0){
			values.put(DbHelper.DISTRIBUTOR_CONTACT_SERVER_ID, that.getServerId());
		}
		values.put(DbHelper.DISTRIBUTOR_CONTACT_DIRTY, that.isDirty());

		values.put(DbHelper.DISTRIBUTOR_CONTACT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_NAME, that.getName());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_EMAIL1, that.getEmail1());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_EMAIL2, that.getEmail2());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER1, that.getPhoneNumber1());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER2, that.getPhoneNumber2());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER3, that.getPhoneNumber3());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER4, that.getPhoneNumber4());
		if(that.getFkFkBrand() > 0){
			values.put(DbHelper.DISTRIBUTOR_CONTACT_FK_BRAND, that.getFkFkBrand());
		}
		int rows_affected = database.update(DbHelper.TABLE_DISTRIBUTOR_CONTACT, values, DbHelper.DISTRIBUTOR_CONTACT_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public List<DistributorContactDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.name, " +
		"t0.email1, " +
		"t0.email2, " +
		"t0.phone_number1, " +
		"t0.phone_number2, " +
		"t0.phone_number3, " +
		"t0.phone_number4, " +
		"t9.server_id as fk_brand" +
		" FROM "+DbHelper.TABLE_DISTRIBUTOR_CONTACT+" t0" +
		" INNER JOIN "+DbHelper.TABLE_BRAND+" t1 ON t0.fk_brand = t1.id";		query += " WHERE t0.server_id IS NULL";
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Log.wtf("rest-api", query);
		List<DistributorContactView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(DistributorContactView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<DistributorContactDataView> listForUpdateOnServer(long page_count, long page_size){
		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo
		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.name, " +
		"t0.email1, " +
		"t0.email2, " +
		"t0.phone_number1, " +
		"t0.phone_number2, " +
		"t0.phone_number3, " +
		"t0.phone_number4" +
		" FROM "+DbHelper.TABLE_DISTRIBUTOR_CONTACT+" t0" +
		" INNER JOIN "+DbHelper.TABLE_BRAND+" t1 ON t0.fk_brand = t1.id";		query += " WHERE t0." + DbHelper.DISTRIBUTOR_CONTACT_DIRTY + " = 1";
		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		//Log.wtf("rest-api", query);
		List<DistributorContactView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(DistributorContactView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();
		values.put(DbHelper.DISTRIBUTOR_CONTACT_SERVER_ID, remote_id);
		values.put(DbHelper.DISTRIBUTOR_CONTACT_LAST_UPDATE_TIME, last_update_time);
		values.put(DbHelper.DISTRIBUTOR_CONTACT_DIRTY, 0);
		int rows_affected = database.update(
			DbHelper.TABLE_DISTRIBUTOR_CONTACT,
			values,
			DbHelper.DISTRIBUTOR_CONTACT_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){
		long result = 0;
		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_DISTRIBUTOR_CONTACT +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	}

	//the idea is that i don't want to iterate over all the data,
	//bringing all the data to the server is expensive, even if the return value is null sometimes
	//just bring from the server what is newer than my newer data, for updating
	//the implementation is also easier :D
	public long getLastUpdateTime(){
		long result = 0;
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_DISTRIBUTOR_CONTACT+"';";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_DISTRIBUTOR_CONTACT + " ) WHERE table_name = '" + DbHelper.TABLE_DISTRIBUTOR_CONTACT + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);
	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_DISTRIBUTOR_CONTACT + "';";
		database.rawQuery(query, null);
	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){
		String query = "UPDATE " + DbHelper.TABLE_DISTRIBUTOR_CONTACT + " SET "+
		"fk_brand = ( SELECT id FROM " + DbHelper.TABLE_BRAND + " WHERE " + DbHelper.TABLE_BRAND + ".server_id = " + DbHelper.TABLE_DISTRIBUTOR_CONTACT + ".fk_brand );";		int result = 0;
		Cursor cursor = database.rawQuery(query, null);
		return cursorToInteger(cursor);
	}



}
