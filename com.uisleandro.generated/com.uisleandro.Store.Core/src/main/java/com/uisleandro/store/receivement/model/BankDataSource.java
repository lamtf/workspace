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
//import com.uisleandro.store.receivement.model.BankDbHelper;
import com.uisleandro.store.receivement.model.DbHelper;
import com.uisleandro.store.receivement.view.BankView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class BankDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.BANK_ID,
		DbHelper.BANK_SERVER_ID,
		DbHelper.BANK_DIRTY,
		DbHelper.BANK_LAST_UPDATE, 
		DbHelper.BANK_CODE, 
		DbHelper.BANK_NAME, 
		DbHelper.BANK_NAME, 
		DbHelper.BANK_NAME, 
		DbHelper.BANK_NAME
	};

	public BankDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("BankDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public BankView cursorToBankView(Cursor cursor){

	 	BankView that = new BankView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setCode(cursor.getString(1));
		that.setName(cursor.getString(2));
		that.setName(cursor.getString(3));
		that.setName(cursor.getString(4));
		that.setName(cursor.getString(5));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<BankView> cursorToListOfBankView(Cursor cursor){

		List<BankView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			BankView that = cursorToBankView(cursor);
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


	public long insert(BankView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.BANK_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.BANK_DIRTY, that.isDirty());
		values.put(DbHelper.BANK_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.BANK_CODE, that.getCode());
		values.put(DbHelper.BANK_NAME, that.getName());
		values.put(DbHelper.BANK_NAME, that.getName());
		values.put(DbHelper.BANK_NAME, that.getName());
		values.put(DbHelper.BANK_NAME, that.getName());
		long last_id = database.insert(DbHelper.TABLE_BANK, null, values);
		return last_id;
	}

	public int update(BankView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.BANK_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.BANK_DIRTY, that.isDirty());

		values.put(DbHelper.BANK_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.BANK_CODE, that.getCode());
		values.put(DbHelper.BANK_NAME, that.getName());
		values.put(DbHelper.BANK_NAME, that.getName());
		values.put(DbHelper.BANK_NAME, that.getName());
		values.put(DbHelper.BANK_NAME, that.getName());

		int rows_affected = database.update(DbHelper.TABLE_BANK, values, DbHelper.BANK_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(BankView that){
		database.delete(DbHelper.TABLE_BANK, DbHelper.BANK_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_BANK, DbHelper.BANK_ID + " = " + String.valueOf(id), null);
	}

	public List<BankView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_BANK,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfBankView(cursor);
	}

	public BankView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_BANK,
			selectableColumns,
			DbHelper.BANK_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		BankView that = cursorToBankView(cursor);
		cursor.close();
		return that;
	}

	public List<BankView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"code, " +
			"name, " +
			"name, " +
			"name, " +
			"name" +
			" FROM " + DbHelper.TABLE_BANK;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfBankView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_BANK +";";
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

