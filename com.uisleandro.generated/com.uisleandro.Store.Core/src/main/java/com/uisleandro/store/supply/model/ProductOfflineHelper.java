package com.uisleandro.store.supply.model;

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import com.uisleandro.store.supply.view.ProductDataView

public class ProductOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public ProductOfflineHelper (Context context) {
		this.context = context;
	}

	public ProductOfflineHelper (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("ProductDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public List<ProductDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t2.server_id as fk_system, " +
		"t0.barcode, " +
		"t0.description, " +
		"t0.amount, " +
		"t6.server_id as fk_gender, " +
		"t0.purchase_price, " +
		"t0.sale_price, " +
		"t9.server_id as fk_category, " +
		"t0.size, " +
		"t11.server_id as fk_unit, " +
		"t0.expiration_date, " +
		"t13.server_id as fk_brand" +
		" FROM "+DbHelper.TABLE_PRODUCT+" t0" +
		" INNER JOIN "+DbHelper.TABLE_SYSTEM+" t1 ON t0.fk_system = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_GENDER+" t2 ON t0.fk_gender = t2.id" +
		" INNER JOIN "+DbHelper.TABLE_CATEGORY+" t3 ON t0.fk_category = t3.id" +
		" INNER JOIN "+DbHelper.TABLE_UNIT+" t4 ON t0.fk_unit = t4.id" +
		" INNER JOIN "+DbHelper.TABLE_BRAND+" t5 ON t0.fk_brand = t5.id";
		query += " WHERE t0.server_id IS NULL";

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Log.wtf("rest-api", query);

		List<ProductView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);

		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(ProductView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();

		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<ProductDataView> listForUpdateOnServer(long page_count, long page_size){

		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.barcode, " +
		"t0.description, " +
		"t0.amount, " +
		"t0.purchase_price, " +
		"t0.sale_price, " +
		"t0.size, " +
		"t0.expiration_date" +
		" FROM "+DbHelper.TABLE_PRODUCT+" t0" +
		" INNER JOIN "+DbHelper.TABLE_SYSTEM+" t1 ON t0.fk_system = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_GENDER+" t2 ON t0.fk_gender = t2.id" +
		" INNER JOIN "+DbHelper.TABLE_CATEGORY+" t3 ON t0.fk_category = t3.id" +
		" INNER JOIN "+DbHelper.TABLE_UNIT+" t4 ON t0.fk_unit = t4.id" +
		" INNER JOIN "+DbHelper.TABLE_BRAND+" t5 ON t0.fk_brand = t5.id";
		query += " WHERE t0." + DbHelper.PRODUCT_DIRTY + " = 1";

		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		//Log.wtf("rest-api", query);

		List<ProductView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);

		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(ProductView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();

	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();

		values.put(DbHelper.PRODUCT_SERVER_ID, remote_id);
		values.put(DbHelper.PRODUCT_LAST_UPDATE_TIME, last_update_time);
		values.put(DbHelper.PRODUCT_DIRTY, 0);


		int rows_affected = database.update(
			DbHelper.TABLE_PRODUCT,
			values,
			DbHelper.PRODUCT_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){

		long result = 0;


		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_PRODUCT +";";
		Cursor cursor = database.rawQuery(query, null);

		return cursorToLong(cursor);
	}

	//the idea is that i don't want to iterate over all the data,
	//bringing all the data to the server is expensive, even if the return value is null sometimes
	//just bring from the server what is newer than my newer data, for updating
	//the implementation is also easier :D
	public long getLastUpdateTime(){

		long result = 0;
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_PRODUCT+"';";
		Cursor cursor = database.rawQuery(query, null);

		return cursorToLong(cursor);

	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){

		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_PRODUCT + " ) WHERE table_name = '" + DbHelper.TABLE_PRODUCT + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);

	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){

		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_PRODUCT + "';";
		database.rawQuery(query, null);

	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){

		String query = "UPDATE " + DbHelper.TABLE_PRODUCT + " SET "+
		"fk_system = ( SELECT id FROM " + DbHelper.TABLE_SYSTEM + " WHERE " + DbHelper.TABLE_SYSTEM + ".server_id = " + DbHelper.TABLE_PRODUCT + ".fk_system ), " +
		"fk_gender = ( SELECT id FROM " + DbHelper.TABLE_GENDER + " WHERE " + DbHelper.TABLE_GENDER + ".server_id = " + DbHelper.TABLE_PRODUCT + ".fk_gender ), " +
		"fk_category = ( SELECT id FROM " + DbHelper.TABLE_CATEGORY + " WHERE " + DbHelper.TABLE_CATEGORY + ".server_id = " + DbHelper.TABLE_PRODUCT + ".fk_category ), " +
		"fk_unit = ( SELECT id FROM " + DbHelper.TABLE_UNIT + " WHERE " + DbHelper.TABLE_UNIT + ".server_id = " + DbHelper.TABLE_PRODUCT + ".fk_unit ), " +
		"fk_brand = ( SELECT id FROM " + DbHelper.TABLE_BRAND + " WHERE " + DbHelper.TABLE_BRAND + ".server_id = " + DbHelper.TABLE_PRODUCT + ".fk_brand );";

		int result = 0;
		Cursor cursor = database.rawQuery(query, null);

		return cursorToInteger(cursor);
	}






}
