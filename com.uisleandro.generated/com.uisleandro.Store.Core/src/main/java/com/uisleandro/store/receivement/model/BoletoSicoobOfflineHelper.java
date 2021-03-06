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
import com.uisleandro.store.receivement.view.BoletoSicoobDataView;

public class BoletoSicoobOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public BoletoSicoobOfflineHelper (Context context) {
		this.context = context;
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("BoletoSicoobOfflineHelper", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public long insert(BoletoSicoobDataView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.BOLETO_SICOOB_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.BOLETO_SICOOB_DIRTY, that.isDirty());
		values.put(DbHelper.BOLETO_SICOOB_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.BOLETO_SICOOB_CPF, that.getCpf());
		values.put(DbHelper.BOLETO_SICOOB_NUMERO, that.getNumero());
		values.put(DbHelper.BOLETO_SICOOB_DATA, that.getData());
		values.put(DbHelper.BOLETO_SICOOB_VENCIMENTO, that.getVencimento());
		values.put(DbHelper.BOLETO_SICOOB_VALOR, that.getValor());
		values.put(DbHelper.BOLETO_SICOOB_NOSSO_NUMERO, that.getNossoNumero());
		values.put(DbHelper.BOLETO_SICOOB_QUANTIDADE, that.getQuantidade());
		values.put(DbHelper.BOLETO_SICOOB_PARCELA, that.getParcela());
		values.put(DbHelper.BOLETO_SICOOB_FOI_PAGO, that.getFoiPago());
		values.put(DbHelper.BOLETO_SICOOB_DATA_DE_PAGAMENTO, that.getDataDePagamento());
		values.put(DbHelper.BOLETO_SICOOB_VALOR_RECEBIDO, that.getValorRecebido());
		if(that.getFkInvoice() > 0){
			values.put(DbHelper.BOLETO_SICOOB_FK_INVOICE, that.getFkInvoice());
		}
		long last_id = database.insert(DbHelper.TABLE_BOLETO_SICOOB, null, values);
		return last_id;
	}

	public int update(BoletoSicoobDataView that){
		ContentValues values = new ContentValues();
		if(that.getServerId() > 0){
			values.put(DbHelper.BOLETO_SICOOB_SERVER_ID, that.getServerId());
		}
		values.put(DbHelper.BOLETO_SICOOB_DIRTY, that.isDirty());

		values.put(DbHelper.BOLETO_SICOOB_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.BOLETO_SICOOB_CPF, that.getCpf());
		values.put(DbHelper.BOLETO_SICOOB_NUMERO, that.getNumero());
		values.put(DbHelper.BOLETO_SICOOB_DATA, that.getData());
		values.put(DbHelper.BOLETO_SICOOB_VENCIMENTO, that.getVencimento());
		values.put(DbHelper.BOLETO_SICOOB_VALOR, that.getValor());
		values.put(DbHelper.BOLETO_SICOOB_NOSSO_NUMERO, that.getNossoNumero());
		values.put(DbHelper.BOLETO_SICOOB_QUANTIDADE, that.getQuantidade());
		values.put(DbHelper.BOLETO_SICOOB_PARCELA, that.getParcela());
		values.put(DbHelper.BOLETO_SICOOB_FOI_PAGO, that.getFoiPago());
		values.put(DbHelper.BOLETO_SICOOB_DATA_DE_PAGAMENTO, that.getDataDePagamento());
		values.put(DbHelper.BOLETO_SICOOB_VALOR_RECEBIDO, that.getValorRecebido());
		if(that.getFkInvoice() > 0){
			values.put(DbHelper.BOLETO_SICOOB_FK_INVOICE, that.getFkInvoice());
		}
		int rows_affected = database.update(DbHelper.TABLE_BOLETO_SICOOB, values, DbHelper.BOLETO_SICOOB_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public List<BoletoSicoobDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.cpf, " +
		"t0.numero, " +
		"t0.data, " +
		"t0.vencimento, " +
		"t0.valor, " +
		"t0.nosso_numero, " +
		"t0.quantidade, " +
		"t0.parcela, " +
		"t0.foi_pago, " +
		"t0.data_de_pagamento, " +
		"t0.valor_recebido, " +
		"t13.server_id as fk_invoice" +
		" FROM "+DbHelper.TABLE_BOLETO_SICOOB+" t0" +
		" INNER JOIN "+DbHelper.TABLE_INVOICE+" t1 ON t0.fk_invoice = t1.id";		query += " WHERE t0.server_id IS NULL";
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Log.wtf("rest-api", query);
		List<BoletoSicoobDataView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(BoletoSicoobDataView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<BoletoSicoobDataView> listForUpdateOnServer(long page_count, long page_size){
		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo
		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.cpf, " +
		"t0.numero, " +
		"t0.data, " +
		"t0.vencimento, " +
		"t0.valor, " +
		"t0.nosso_numero, " +
		"t0.quantidade, " +
		"t0.parcela, " +
		"t0.foi_pago, " +
		"t0.data_de_pagamento, " +
		"t0.valor_recebido" +
		" FROM "+DbHelper.TABLE_BOLETO_SICOOB+" t0" +
		" INNER JOIN "+DbHelper.TABLE_INVOICE+" t1 ON t0.fk_invoice = t1.id";		query += " WHERE t0." + DbHelper.BOLETO_SICOOB_DIRTY + " = 1";
		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		//Log.wtf("rest-api", query);
		List<BoletoSicoobDataView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(BoletoSicoobDataView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();
		values.put(DbHelper.BOLETO_SICOOB_SERVER_ID, remote_id);
		values.put(DbHelper.BOLETO_SICOOB_LAST_UPDATE, last_update_time);
		values.put(DbHelper.BOLETO_SICOOB_DIRTY, 0);
		int rows_affected = database.update(
			DbHelper.TABLE_BOLETO_SICOOB,
			values,
			DbHelper.BOLETO_SICOOB_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){
		long result = 0;
		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_BOLETO_SICOOB +";";
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
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_BOLETO_SICOOB+"';";
		Cursor cursor = database.rawQuery(query, null);
		long res = cursor.getLong(0);
		cursor.close();
		return res;
	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_BOLETO_SICOOB + " ) WHERE table_name = '" + DbHelper.TABLE_BOLETO_SICOOB + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);
	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_BOLETO_SICOOB + "';";
		database.rawQuery(query, null);
	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){
		String query = "UPDATE " + DbHelper.TABLE_BOLETO_SICOOB + " SET "+
		"fk_invoice = ( SELECT id FROM " + DbHelper.TABLE_INVOICE + " WHERE " + DbHelper.TABLE_INVOICE + ".server_id = " + DbHelper.TABLE_BOLETO_SICOOB + ".fk_invoice );";		int result = 0;
		Cursor cursor = database.rawQuery(query, null);
		int res = cursor.getInt(0);
		cursor.close();
		return res;
	}



}
