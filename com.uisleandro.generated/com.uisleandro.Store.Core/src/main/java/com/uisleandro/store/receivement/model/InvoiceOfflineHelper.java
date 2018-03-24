package com.uisleandro.store.receivement.model;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.uisleandro.store.DbHelper;
import com.uisleandro.store.receivement.view.InvoiceDataView;

public class InvoiceOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public InvoiceOfflineHelper (Context context) {
		this.context = context;
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("InvoiceOfflineHelper", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public long insert(InvoiceDataView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.INVOICE_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.INVOICE_DIRTY, that.isDirty());
		values.put(DbHelper.INVOICE_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSystem() > 0){
			values.put(DbHelper.INVOICE_FK_SYSTEM, that.getFkSystem());
		}
		if(that.getFkSale() > 0){
			values.put(DbHelper.INVOICE_FK_SALE, that.getFkSale());
		}
		if(that.getFkClientFromSystem() > 0){
			values.put(DbHelper.INVOICE_FK_CLIENT_FROM_SYSTEM, that.getFkClientFromSystem());
		}
		if(that.getFkInstallmentType() > 0){
			values.put(DbHelper.INVOICE_FK_INSTALLMENT_TYPE, that.getFkInstallmentType());
		}
		if(that.getFkInterestRateType() > 0){
			values.put(DbHelper.INVOICE_FK_INTEREST_RATE_TYPE, that.getFkInterestRateType());
		}
		if(that.getFkBank() > 0){
			values.put(DbHelper.INVOICE_FK_BANK, that.getFkBank());
		}
		long last_id = database.insert(DbHelper.TABLE_INVOICE, null, values);
		return last_id;
	}

	public int update(InvoiceDataView that){
		ContentValues values = new ContentValues();
		if(that.getServerId() > 0){
			values.put(DbHelper.INVOICE_SERVER_ID, that.getServerId());
		}
		values.put(DbHelper.INVOICE_DIRTY, that.isDirty());

		values.put(DbHelper.INVOICE_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSystem() > 0){
			values.put(DbHelper.INVOICE_FK_SYSTEM, that.getFkSystem());
		}
		if(that.getFkSale() > 0){
			values.put(DbHelper.INVOICE_FK_SALE, that.getFkSale());
		}
		if(that.getFkClientFromSystem() > 0){
			values.put(DbHelper.INVOICE_FK_CLIENT_FROM_SYSTEM, that.getFkClientFromSystem());
		}
		if(that.getFkInstallmentType() > 0){
			values.put(DbHelper.INVOICE_FK_INSTALLMENT_TYPE, that.getFkInstallmentType());
		}
		if(that.getFkInterestRateType() > 0){
			values.put(DbHelper.INVOICE_FK_INTEREST_RATE_TYPE, that.getFkInterestRateType());
		}
		if(that.getFkBank() > 0){
			values.put(DbHelper.INVOICE_FK_BANK, that.getFkBank());
		}
		int rows_affected = database.update(DbHelper.TABLE_INVOICE, values, DbHelper.INVOICE_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public List<InvoiceDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t2.server_id as fk_system, " +
		"t3.server_id as fk_sale, " +
		"t4.server_id as fk_client_from_system, " +
		"t5.server_id as fk_installment_type, " +
		"t6.server_id as fk_interest_rate_type, " +
		"t7.server_id as fk_bank" +
		" FROM "+DbHelper.TABLE_INVOICE+" t0" +
		" INNER JOIN "+DbHelper.TABLE_SYSTEM+" t1 ON t0.fk_system = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_SALE+" t2 ON t0.fk_sale = t2.id" +
		" INNER JOIN "+DbHelper.TABLE_CLIENT_FROM_SYSTEM+" t3 ON t0.fk_client_from_system = t3.id" +
		" INNER JOIN "+DbHelper.TABLE_INSTALLMENT_TYPE+" t4 ON t0.fk_installment_type = t4.id" +
		" INNER JOIN "+DbHelper.TABLE_INTEREST_RATE_TYPE+" t5 ON t0.fk_interest_rate_type = t5.id" +
		" INNER JOIN "+DbHelper.TABLE_BANK+" t6 ON t0.fk_bank = t6.id";		query += " WHERE t0.server_id IS NULL";
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Log.wtf("rest-api", query);
		List<InvoiceDataView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(InvoiceDataView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<InvoiceDataView> listForUpdateOnServer(long page_count, long page_size){
		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo
		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update" +
		" FROM "+DbHelper.TABLE_INVOICE+" t0" +
		" INNER JOIN "+DbHelper.TABLE_SYSTEM+" t1 ON t0.fk_system = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_SALE+" t2 ON t0.fk_sale = t2.id" +
		" INNER JOIN "+DbHelper.TABLE_CLIENT_FROM_SYSTEM+" t3 ON t0.fk_client_from_system = t3.id" +
		" INNER JOIN "+DbHelper.TABLE_INSTALLMENT_TYPE+" t4 ON t0.fk_installment_type = t4.id" +
		" INNER JOIN "+DbHelper.TABLE_INTEREST_RATE_TYPE+" t5 ON t0.fk_interest_rate_type = t5.id" +
		" INNER JOIN "+DbHelper.TABLE_BANK+" t6 ON t0.fk_bank = t6.id";		query += " WHERE t0." + DbHelper.INVOICE_DIRTY + " = 1";
		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		//Log.wtf("rest-api", query);
		List<InvoiceDataView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(InvoiceDataView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();
		values.put(DbHelper.INVOICE_SERVER_ID, remote_id);
		values.put(DbHelper.INVOICE_LAST_UPDATE, last_update_time);
		values.put(DbHelper.INVOICE_DIRTY, 0);
		int rows_affected = database.update(
			DbHelper.TABLE_INVOICE,
			values,
			DbHelper.INVOICE_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){
		long result = 0;
		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_INVOICE +";";
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
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_INVOICE+"';";
		Cursor cursor = database.rawQuery(query, null);
		long res = cursor.getLong(0);
		cursor.close();
		return res;
	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_INVOICE + " ) WHERE table_name = '" + DbHelper.TABLE_INVOICE + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);
	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_INVOICE + "';";
		database.rawQuery(query, null);
	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){
		String query = "UPDATE " + DbHelper.TABLE_INVOICE + " SET "+
		"fk_system = ( SELECT id FROM " + DbHelper.TABLE_SYSTEM + " WHERE " + DbHelper.TABLE_SYSTEM + ".server_id = " + DbHelper.TABLE_INVOICE + ".fk_system ), " +
		"fk_sale = ( SELECT id FROM " + DbHelper.TABLE_SALE + " WHERE " + DbHelper.TABLE_SALE + ".server_id = " + DbHelper.TABLE_INVOICE + ".fk_sale ), " +
		"fk_client_from_system = ( SELECT id FROM " + DbHelper.TABLE_CLIENT_FROM_SYSTEM + " WHERE " + DbHelper.TABLE_CLIENT_FROM_SYSTEM + ".server_id = " + DbHelper.TABLE_INVOICE + ".fk_client_from_system ), " +
		"fk_installment_type = ( SELECT id FROM " + DbHelper.TABLE_INSTALLMENT_TYPE + " WHERE " + DbHelper.TABLE_INSTALLMENT_TYPE + ".server_id = " + DbHelper.TABLE_INVOICE + ".fk_installment_type ), " +
		"fk_interest_rate_type = ( SELECT id FROM " + DbHelper.TABLE_INTEREST_RATE_TYPE + " WHERE " + DbHelper.TABLE_INTEREST_RATE_TYPE + ".server_id = " + DbHelper.TABLE_INVOICE + ".fk_interest_rate_type ), " +
		"fk_bank = ( SELECT id FROM " + DbHelper.TABLE_BANK + " WHERE " + DbHelper.TABLE_BANK + ".server_id = " + DbHelper.TABLE_INVOICE + ".fk_bank );";		int result = 0;
		Cursor cursor = database.rawQuery(query, null);
		int res = cursor.getInt(0);
		cursor.close();
		return res;
	}



}
