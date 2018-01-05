//Start of user code reserved-for:android-sqlite-db.imports
package com.uisleandro.store.Core.model;  

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;
//old
//import com.uisleandro.util.LongDateFormatter;
//import com.uisleandro.store.model.DbHelper;
//import com.uisleandro.store.receivement.model.BoletoSicoobDbHelper;

import com.uisleandro.store.DbHelper;

//TODO: I wont return any view, Id rather return the cursor instead 

// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class BoletoSicoobDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.BOLETO_SICOOB_ID,
		DbHelper.BOLETO_SICOOB_SERVER_ID,
		DbHelper.BOLETO_SICOOB_DIRTY,
		DbHelper.BOLETO_SICOOB_LAST_UPDATE, 
		DbHelper.BOLETO_SICOOB_CPF, 
		DbHelper.BOLETO_SICOOB_NUMERO, 
		DbHelper.BOLETO_SICOOB_DATA, 
		DbHelper.BOLETO_SICOOB_VENCIMENTO, 
		DbHelper.BOLETO_SICOOB_VALOR, 
		DbHelper.BOLETO_SICOOB_NOSSO_NUMERO, 
		DbHelper.BOLETO_SICOOB_QUANTIDADE, 
		DbHelper.BOLETO_SICOOB_PARCELA, 
		DbHelper.BOLETO_SICOOB_FOI_PAGO, 
		DbHelper.BOLETO_SICOOB_DATA_DE_PAGAMENTO, 
		DbHelper.BOLETO_SICOOB_VALOR_RECEBIDO, 
		DbHelper.BOLETO_SICOOB_FK_INVOICE
	};

	public BoletoSicoobDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("BoletoSicoobDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public long cursorToLong(Cursor cursor){
		long result = 0L;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getLong(0);
		}
		cursor.close();
		return result;
	}

	public int cursorToInteger(Cursor cursor){
		int result = 0;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		cursor.close();
		return result;
	}


	public long insert(BoletoSicoobView that){
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

	public int update(BoletoSicoobView that){
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

	public long delete(BoletoSicoobView that){
		return database.delete(DbHelper.TABLE_BOLETO_SICOOB, DbHelper.BOLETO_SICOOB_ID + " = " + String.valueOf(that.getId()), null);
	}

	public long deleteById(long id){
		return database.delete(DbHelper.TABLE_BOLETO_SICOOB, DbHelper.BOLETO_SICOOB_ID + " = " + String.valueOf(id), null);
	}

	public Cursor listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_BOLETO_SICOOB,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_BOLETO_SICOOB,
			selectableColumns,
			DbHelper.BOLETO_SICOOB_ID + " = " + id,
			null, null, null, null);

		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"cpf, " +
			"numero, " +
			"data, " +
			"vencimento, " +
			"valor, " +
			"nosso_numero, " +
			"quantidade, " +
			"parcela, " +
			"foi_pago, " +
			"data_de_pagamento, " +
			"valor_recebido, " +
			"fk_invoice" +
			" FROM " + DbHelper.TABLE_BOLETO_SICOOB;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";

		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_BOLETO_SICOOB +";";
		Cursor cursor = database.rawQuery(query, null);
		
		return cursorToLong(cursor);
	}

// reserved-for:android-sqlite-db.functions
//End of user code

//Start of user code reserved-for:android-sqlite-sync.functions
//reserved-for:android-sqlite-sync.functions
//End of user code

//Start of user code reserved-for:query3.functions
//reserved-for:query3.functions
//End of user code

}

