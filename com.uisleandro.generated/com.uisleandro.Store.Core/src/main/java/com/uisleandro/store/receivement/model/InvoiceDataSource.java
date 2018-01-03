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
//import com.uisleandro.store.receivement.model.InvoiceDbHelper;
import com.uisleandro.store.receivement.model.DbHelper;
import com.uisleandro.store.receivement.view.InvoiceView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class InvoiceDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.INVOICE_ID,
		DbHelper.INVOICE_SERVER_ID,
		DbHelper.INVOICE_DIRTY,
		DbHelper.INVOICE_LAST_UPDATE, 
		DbHelper.INVOICE_FK_SYSTEM, 
		DbHelper.INVOICE_FK_SALE, 
		DbHelper.INVOICE_FK_CLIENT_FROM_SYSTEM, 
		DbHelper.INVOICE_FK_INSTALLMENT_TYPE, 
		DbHelper.INVOICE_FK_INTEREST_RATE_TYPE, 
		DbHelper.INVOICE_FK_BANK, 
		DbHelper.INVOICE_FK_CURRENCY
	};

	public InvoiceDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("InvoiceDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public InvoiceView cursorToInvoiceView(Cursor cursor){

	 	InvoiceView that = new InvoiceView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setFkSystem(cursor.getLong(1));
		that.setFkSale(cursor.getLong(2));
		that.setFkClientFromSystem(cursor.getLong(3));
		that.setFkInstallmentType(cursor.getLong(4));
		that.setFkInterestRateType(cursor.getLong(5));
		that.setFkBank(cursor.getLong(6));
		that.setFkCurrency(cursor.getLong(7));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<InvoiceView> cursorToListOfInvoiceView(Cursor cursor){

		List<InvoiceView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			InvoiceView that = cursorToInvoiceView(cursor);
			those.add(that);
			cursor.moveToNext();
		}
		cursor.close();
		return those;
	
	}

	//desacoplamento
	public long cursorToLong(Cursor cursor){
		long result = 0L;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getLong(0);
		}

		return result;
	}

	//desacoplamento
	public int cursorToInteger(Cursor cursor){
		int result = 0;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}

		return result;
	}


	public long insert(InvoiceView that){
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
		if(that.getFkCurrency() > 0){
			values.put(DbHelper.INVOICE_FK_CURRENCY, that.getFkCurrency());
		}
		long last_id = database.insert(DbHelper.TABLE_INVOICE, null, values);
		return last_id;
	}

	public int update(InvoiceView that){
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
		if(that.getFkCurrency() > 0){
			values.put(DbHelper.INVOICE_FK_CURRENCY, that.getFkCurrency());
		}

		int rows_affected = database.update(DbHelper.TABLE_INVOICE, values, DbHelper.INVOICE_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(InvoiceView that){
		database.delete(DbHelper.TABLE_INVOICE, DbHelper.INVOICE_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_INVOICE, DbHelper.INVOICE_ID + " = " + String.valueOf(id), null);
	}

	public List<InvoiceView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_INVOICE,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfInvoiceView(cursor);
	}

	public InvoiceView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_INVOICE,
			selectableColumns,
			DbHelper.INVOICE_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		InvoiceView that = cursorToInvoiceView(cursor);
		cursor.close();
		return that;
	}

	public List<InvoiceView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_system, " +
			"fk_sale, " +
			"fk_client_from_system, " +
			"fk_installment_type, " +
			"fk_interest_rate_type, " +
			"fk_bank, " +
			"fk_currency" +
			" FROM " + DbHelper.TABLE_INVOICE;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfInvoiceView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_INVOICE +";";
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

