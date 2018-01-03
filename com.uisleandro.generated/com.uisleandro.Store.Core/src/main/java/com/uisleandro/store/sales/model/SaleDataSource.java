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
//import com.uisleandro.store.sales.model.SaleDbHelper;
import com.uisleandro.store.sales.model.DbHelper;
import com.uisleandro.store.sales.view.SaleView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class SaleDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.SALE_ID,
		DbHelper.SALE_SERVER_ID,
		DbHelper.SALE_DIRTY,
		DbHelper.SALE_LAST_UPDATE, 
		DbHelper.SALE_FK_SALE_TYPE, 
		DbHelper.SALE_FK_SYSTEM, 
		DbHelper.SALE_TOTAL_VALUE, 
		DbHelper.SALE_FK_USER, 
		DbHelper.SALE_FK_CLIENT_FROM_SYSTEM, 
		DbHelper.SALE_FK_CURRENCY
	};

	public SaleDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("SaleDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public SaleView cursorToSaleView(Cursor cursor){

	 	SaleView that = new SaleView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setFkSaleType(cursor.getLong(1));
		that.setFkSystem(cursor.getLong(2));
		that.setTotalValue(cursor.getFloat(3));
		that.setFkUser(cursor.getLong(4));
		that.setFkClientFromSystem(cursor.getLong(5));
		that.setFkCurrency(cursor.getLong(6));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<SaleView> cursorToListOfSaleView(Cursor cursor){

		List<SaleView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			SaleView that = cursorToSaleView(cursor);
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


	public long insert(SaleView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.SALE_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.SALE_DIRTY, that.isDirty());
		values.put(DbHelper.SALE_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSaleType() > 0){
			values.put(DbHelper.SALE_FK_SALE_TYPE, that.getFkSaleType());
		}
		if(that.getFkSystem() > 0){
			values.put(DbHelper.SALE_FK_SYSTEM, that.getFkSystem());
		}
		values.put(DbHelper.SALE_TOTAL_VALUE, that.getTotalValue());
		if(that.getFkUser() > 0){
			values.put(DbHelper.SALE_FK_USER, that.getFkUser());
		}
		if(that.getFkClientFromSystem() > 0){
			values.put(DbHelper.SALE_FK_CLIENT_FROM_SYSTEM, that.getFkClientFromSystem());
		}
		if(that.getFkCurrency() > 0){
			values.put(DbHelper.SALE_FK_CURRENCY, that.getFkCurrency());
		}
		long last_id = database.insert(DbHelper.TABLE_SALE, null, values);
		return last_id;
	}

	public int update(SaleView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.SALE_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.SALE_DIRTY, that.isDirty());

		values.put(DbHelper.SALE_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSaleType() > 0){
			values.put(DbHelper.SALE_FK_SALE_TYPE, that.getFkSaleType());
		}
		if(that.getFkSystem() > 0){
			values.put(DbHelper.SALE_FK_SYSTEM, that.getFkSystem());
		}
		values.put(DbHelper.SALE_TOTAL_VALUE, that.getTotalValue());
		if(that.getFkUser() > 0){
			values.put(DbHelper.SALE_FK_USER, that.getFkUser());
		}
		if(that.getFkClientFromSystem() > 0){
			values.put(DbHelper.SALE_FK_CLIENT_FROM_SYSTEM, that.getFkClientFromSystem());
		}
		if(that.getFkCurrency() > 0){
			values.put(DbHelper.SALE_FK_CURRENCY, that.getFkCurrency());
		}

		int rows_affected = database.update(DbHelper.TABLE_SALE, values, DbHelper.SALE_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(SaleView that){
		database.delete(DbHelper.TABLE_SALE, DbHelper.SALE_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_SALE, DbHelper.SALE_ID + " = " + String.valueOf(id), null);
	}

	public List<SaleView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_SALE,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfSaleView(cursor);
	}

	public SaleView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_SALE,
			selectableColumns,
			DbHelper.SALE_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		SaleView that = cursorToSaleView(cursor);
		cursor.close();
		return that;
	}

	public List<SaleView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_sale_type, " +
			"fk_system, " +
			"total_value, " +
			"fk_user, " +
			"fk_client_from_system, " +
			"fk_currency" +
			" FROM " + DbHelper.TABLE_SALE;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfSaleView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_SALE +";";
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

