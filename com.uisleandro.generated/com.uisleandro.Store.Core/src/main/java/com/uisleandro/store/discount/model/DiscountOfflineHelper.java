package com.uisleandro.store.discount.model;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uisleandro.store.discount.view.DiscountDataView

public class DiscountOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public DiscountOfflineHelper (Context context) {
		this.context = context;
	}

	public DiscountOfflineHelper (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("DiscountOfflineHelper", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public long insert(DiscountView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.DISCOUNT_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.DISCOUNT_DIRTY, that.isDirty());
		values.put(DbHelper.DISCOUNT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.DISCOUNT_VALUE, that.getValue());
		values.put(DbHelper.DISCOUNT_PERCENTAGE, that.getPercentage());
		if(that.getFkFkProduct() > 0){
			values.put(DbHelper.DISCOUNT_FK_PRODUCT, that.getFkFkProduct());
		}
		if(that.getFkFkCategory() > 0){
			values.put(DbHelper.DISCOUNT_FK_CATEGORY, that.getFkFkCategory());
		}
		if(that.getFkFkBrand() > 0){
			values.put(DbHelper.DISCOUNT_FK_BRAND, that.getFkFkBrand());
		}
		if(that.getFkFkClientFromSystem() > 0){
			values.put(DbHelper.DISCOUNT_FK_CLIENT_FROM_SYSTEM, that.getFkFkClientFromSystem());
		}
		if(that.getFkFkGender() > 0){
			values.put(DbHelper.DISCOUNT_FK_GENDER, that.getFkFkGender());
		}
		long last_id = database.insert(DbHelper.TABLE_DISCOUNT, null, values);
		return last_id;
	}

	public int update(DiscountView that){
		ContentValues values = new ContentValues();
		if(that.getServerId() > 0){
			values.put(DbHelper.DISCOUNT_SERVER_ID, that.getServerId());
		}
		values.put(DbHelper.DISCOUNT_DIRTY, that.isDirty());

		values.put(DbHelper.DISCOUNT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.DISCOUNT_VALUE, that.getValue());
		values.put(DbHelper.DISCOUNT_PERCENTAGE, that.getPercentage());
		if(that.getFkFkProduct() > 0){
			values.put(DbHelper.DISCOUNT_FK_PRODUCT, that.getFkFkProduct());
		}
		if(that.getFkFkCategory() > 0){
			values.put(DbHelper.DISCOUNT_FK_CATEGORY, that.getFkFkCategory());
		}
		if(that.getFkFkBrand() > 0){
			values.put(DbHelper.DISCOUNT_FK_BRAND, that.getFkFkBrand());
		}
		if(that.getFkFkClientFromSystem() > 0){
			values.put(DbHelper.DISCOUNT_FK_CLIENT_FROM_SYSTEM, that.getFkFkClientFromSystem());
		}
		if(that.getFkFkGender() > 0){
			values.put(DbHelper.DISCOUNT_FK_GENDER, that.getFkFkGender());
		}
		int rows_affected = database.update(DbHelper.TABLE_DISCOUNT, values, DbHelper.DISCOUNT_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public List<DiscountDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.value, " +
		"t0.percentage, " +
		"t4.server_id as fk_product, " +
		"t5.server_id as fk_category, " +
		"t6.server_id as fk_brand, " +
		"t7.server_id as fk_client_from_system, " +
		"t8.server_id as fk_gender" +
		" FROM "+DbHelper.TABLE_DISCOUNT+" t0" +
		" INNER JOIN "+DbHelper.TABLE_PRODUCT+" t1 ON t0.fk_product = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_CATEGORY+" t2 ON t0.fk_category = t2.id" +
		" INNER JOIN "+DbHelper.TABLE_BRAND+" t3 ON t0.fk_brand = t3.id" +
		" INNER JOIN "+DbHelper.TABLE_CLIENT_FROM_SYSTEM+" t4 ON t0.fk_client_from_system = t4.id" +
		" INNER JOIN "+DbHelper.TABLE_GENDER+" t5 ON t0.fk_gender = t5.id";		query += " WHERE t0.server_id IS NULL";
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Log.wtf("rest-api", query);
		List<DiscountView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(DiscountView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<DiscountDataView> listForUpdateOnServer(long page_count, long page_size){
		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo
		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.value, " +
		"t0.percentage" +
		" FROM "+DbHelper.TABLE_DISCOUNT+" t0" +
		" INNER JOIN "+DbHelper.TABLE_PRODUCT+" t1 ON t0.fk_product = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_CATEGORY+" t2 ON t0.fk_category = t2.id" +
		" INNER JOIN "+DbHelper.TABLE_BRAND+" t3 ON t0.fk_brand = t3.id" +
		" INNER JOIN "+DbHelper.TABLE_CLIENT_FROM_SYSTEM+" t4 ON t0.fk_client_from_system = t4.id" +
		" INNER JOIN "+DbHelper.TABLE_GENDER+" t5 ON t0.fk_gender = t5.id";		query += " WHERE t0." + DbHelper.DISCOUNT_DIRTY + " = 1";
		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		//Log.wtf("rest-api", query);
		List<DiscountView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(DiscountView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();
		values.put(DbHelper.DISCOUNT_SERVER_ID, remote_id);
		values.put(DbHelper.DISCOUNT_LAST_UPDATE_TIME, last_update_time);
		values.put(DbHelper.DISCOUNT_DIRTY, 0);
		int rows_affected = database.update(
			DbHelper.TABLE_DISCOUNT,
			values,
			DbHelper.DISCOUNT_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){
		long result = 0;
		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_DISCOUNT +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	}

	//the idea is that i don't want to iterate over all the data,
	//bringing all the data to the server is expensive, even if the return value is null sometimes
	//just bring from the server what is newer than my newer data, for updating
	//the implementation is also easier :D
	public long getLastUpdateTime(){
		long result = 0;
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_DISCOUNT+"';";
		Cursor cursor = database.rawQuery(query, null);
		return cursorToLong(cursor);
	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_DISCOUNT + " ) WHERE table_name = '" + DbHelper.TABLE_DISCOUNT + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);
	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_DISCOUNT + "';";
		database.rawQuery(query, null);
	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){
		String query = "UPDATE " + DbHelper.TABLE_DISCOUNT + " SET "+
		"fk_product = ( SELECT id FROM " + DbHelper.TABLE_PRODUCT + " WHERE " + DbHelper.TABLE_PRODUCT + ".server_id = " + DbHelper.TABLE_DISCOUNT + ".fk_product ), " +
		"fk_category = ( SELECT id FROM " + DbHelper.TABLE_CATEGORY + " WHERE " + DbHelper.TABLE_CATEGORY + ".server_id = " + DbHelper.TABLE_DISCOUNT + ".fk_category ), " +
		"fk_brand = ( SELECT id FROM " + DbHelper.TABLE_BRAND + " WHERE " + DbHelper.TABLE_BRAND + ".server_id = " + DbHelper.TABLE_DISCOUNT + ".fk_brand ), " +
		"fk_client_from_system = ( SELECT id FROM " + DbHelper.TABLE_CLIENT_FROM_SYSTEM + " WHERE " + DbHelper.TABLE_CLIENT_FROM_SYSTEM + ".server_id = " + DbHelper.TABLE_DISCOUNT + ".fk_client_from_system ), " +
		"fk_gender = ( SELECT id FROM " + DbHelper.TABLE_GENDER + " WHERE " + DbHelper.TABLE_GENDER + ".server_id = " + DbHelper.TABLE_DISCOUNT + ".fk_gender );";		int result = 0;
		Cursor cursor = database.rawQuery(query, null);
		return cursorToInteger(cursor);
	}



}
