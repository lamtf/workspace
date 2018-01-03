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
//import com.uisleandro.store.cash.model.CashRegisterDbHelper;
import com.uisleandro.store.cash.model.DbHelper;
import com.uisleandro.store.cash.view.CashRegisterView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class CashRegisterDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.CASH_REGISTER_ID,
		DbHelper.CASH_REGISTER_SERVER_ID,
		DbHelper.CASH_REGISTER_DIRTY,
		DbHelper.CASH_REGISTER_LAST_UPDATE, 
		DbHelper.CASH_REGISTER_FK_USER, 
		DbHelper.CASH_REGISTER_OPENING_VALUE, 
		DbHelper.CASH_REGISTER_RECEIVED_VALUE, 
		DbHelper.CASH_REGISTER_CLOSING_VALUE, 
		DbHelper.CASH_REGISTER_FK_CURRENCY
	};

	public CashRegisterDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("CashRegisterDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public CashRegisterView cursorToCashRegisterView(Cursor cursor){

	 	CashRegisterView that = new CashRegisterView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setFkUser(cursor.getLong(1));
		that.setOpeningValue(cursor.getFloat(2));
		that.setReceivedValue(cursor.getFloat(3));
		that.setClosingValue(cursor.getFloat(4));
		that.setFkCurrency(cursor.getLong(5));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<CashRegisterView> cursorToListOfCashRegisterView(Cursor cursor){

		List<CashRegisterView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			CashRegisterView that = cursorToCashRegisterView(cursor);
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


	public long insert(CashRegisterView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.CASH_REGISTER_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.CASH_REGISTER_DIRTY, that.isDirty());
		values.put(DbHelper.CASH_REGISTER_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkUser() > 0){
			values.put(DbHelper.CASH_REGISTER_FK_USER, that.getFkUser());
		}
		values.put(DbHelper.CASH_REGISTER_OPENING_VALUE, that.getOpeningValue());
		values.put(DbHelper.CASH_REGISTER_RECEIVED_VALUE, that.getReceivedValue());
		values.put(DbHelper.CASH_REGISTER_CLOSING_VALUE, that.getClosingValue());
		if(that.getFkCurrency() > 0){
			values.put(DbHelper.CASH_REGISTER_FK_CURRENCY, that.getFkCurrency());
		}
		long last_id = database.insert(DbHelper.TABLE_CASH_REGISTER, null, values);
		return last_id;
	}

	public int update(CashRegisterView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.CASH_REGISTER_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.CASH_REGISTER_DIRTY, that.isDirty());

		values.put(DbHelper.CASH_REGISTER_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkUser() > 0){
			values.put(DbHelper.CASH_REGISTER_FK_USER, that.getFkUser());
		}
		values.put(DbHelper.CASH_REGISTER_OPENING_VALUE, that.getOpeningValue());
		values.put(DbHelper.CASH_REGISTER_RECEIVED_VALUE, that.getReceivedValue());
		values.put(DbHelper.CASH_REGISTER_CLOSING_VALUE, that.getClosingValue());
		if(that.getFkCurrency() > 0){
			values.put(DbHelper.CASH_REGISTER_FK_CURRENCY, that.getFkCurrency());
		}

		int rows_affected = database.update(DbHelper.TABLE_CASH_REGISTER, values, DbHelper.CASH_REGISTER_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(CashRegisterView that){
		database.delete(DbHelper.TABLE_CASH_REGISTER, DbHelper.CASH_REGISTER_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_CASH_REGISTER, DbHelper.CASH_REGISTER_ID + " = " + String.valueOf(id), null);
	}

	public List<CashRegisterView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_CASH_REGISTER,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfCashRegisterView(cursor);
	}

	public CashRegisterView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_CASH_REGISTER,
			selectableColumns,
			DbHelper.CASH_REGISTER_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		CashRegisterView that = cursorToCashRegisterView(cursor);
		cursor.close();
		return that;
	}

	public List<CashRegisterView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_user, " +
			"opening_value, " +
			"received_value, " +
			"closing_value, " +
			"fk_currency" +
			" FROM " + DbHelper.TABLE_CASH_REGISTER;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfCashRegisterView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_CASH_REGISTER +";";
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

