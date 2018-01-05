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
//import com.uisleandro.store.supply.model.ProductDbHelper;

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
public class ProductDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.PRODUCT_ID,
		DbHelper.PRODUCT_SERVER_ID,
		DbHelper.PRODUCT_DIRTY,
		DbHelper.PRODUCT_LAST_UPDATE, 
		DbHelper.PRODUCT_FK_SYSTEM, 
		DbHelper.PRODUCT_BARCODE, 
		DbHelper.PRODUCT_DESCRIPTION, 
		DbHelper.PRODUCT_AMOUNT, 
		DbHelper.PRODUCT_FK_GENDER, 
		DbHelper.PRODUCT_PURCHASE_PRICE, 
		DbHelper.PRODUCT_SALE_PRICE, 
		DbHelper.PRODUCT_FK_CATEGORY, 
		DbHelper.PRODUCT_SIZE, 
		DbHelper.PRODUCT_FK_UNIT, 
		DbHelper.PRODUCT_FK_CURRENCY, 
		DbHelper.PRODUCT_EXPIRATION_DATE, 
		DbHelper.PRODUCT_FK_BRAND
	};

	public ProductDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("ProductDataSource", "Exception: "+Log.getStackTraceString(e));
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


	public long insert(ProductView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.PRODUCT_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.PRODUCT_DIRTY, that.isDirty());

		values.put(DbHelper.PRODUCT_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSystem() > 0){
			values.put(DbHelper.PRODUCT_FK_SYSTEM, that.getFkSystem());
		}
		values.put(DbHelper.PRODUCT_BARCODE, that.getBarcode());
		values.put(DbHelper.PRODUCT_DESCRIPTION, that.getDescription());
		values.put(DbHelper.PRODUCT_AMOUNT, that.getAmount());
		if(that.getFkGender() > 0){
			values.put(DbHelper.PRODUCT_FK_GENDER, that.getFkGender());
		}
		values.put(DbHelper.PRODUCT_PURCHASE_PRICE, that.getPurchasePrice());
		values.put(DbHelper.PRODUCT_SALE_PRICE, that.getSalePrice());
		if(that.getFkCategory() > 0){
			values.put(DbHelper.PRODUCT_FK_CATEGORY, that.getFkCategory());
		}
		values.put(DbHelper.PRODUCT_SIZE, that.getSize());
		if(that.getFkUnit() > 0){
			values.put(DbHelper.PRODUCT_FK_UNIT, that.getFkUnit());
		}
		if(that.getFkCurrency() > 0){
			values.put(DbHelper.PRODUCT_FK_CURRENCY, that.getFkCurrency());
		}
		values.put(DbHelper.PRODUCT_EXPIRATION_DATE, that.getExpirationDate());
		if(that.getFkBrand() > 0){
			values.put(DbHelper.PRODUCT_FK_BRAND, that.getFkBrand());
		}
		long last_id = database.insert(DbHelper.TABLE_PRODUCT, null, values);
		return last_id;
	}

	public int update(ProductView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.PRODUCT_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.PRODUCT_DIRTY, that.isDirty());

		values.put(DbHelper.PRODUCT_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSystem() > 0){
			values.put(DbHelper.PRODUCT_FK_SYSTEM, that.getFkSystem());
		}
		values.put(DbHelper.PRODUCT_BARCODE, that.getBarcode());
		values.put(DbHelper.PRODUCT_DESCRIPTION, that.getDescription());
		values.put(DbHelper.PRODUCT_AMOUNT, that.getAmount());
		if(that.getFkGender() > 0){
			values.put(DbHelper.PRODUCT_FK_GENDER, that.getFkGender());
		}
		values.put(DbHelper.PRODUCT_PURCHASE_PRICE, that.getPurchasePrice());
		values.put(DbHelper.PRODUCT_SALE_PRICE, that.getSalePrice());
		if(that.getFkCategory() > 0){
			values.put(DbHelper.PRODUCT_FK_CATEGORY, that.getFkCategory());
		}
		values.put(DbHelper.PRODUCT_SIZE, that.getSize());
		if(that.getFkUnit() > 0){
			values.put(DbHelper.PRODUCT_FK_UNIT, that.getFkUnit());
		}
		if(that.getFkCurrency() > 0){
			values.put(DbHelper.PRODUCT_FK_CURRENCY, that.getFkCurrency());
		}
		values.put(DbHelper.PRODUCT_EXPIRATION_DATE, that.getExpirationDate());
		if(that.getFkBrand() > 0){
			values.put(DbHelper.PRODUCT_FK_BRAND, that.getFkBrand());
		}

		int rows_affected = database.update(DbHelper.TABLE_PRODUCT, values, DbHelper.PRODUCT_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public long delete(ProductView that){
		return database.delete(DbHelper.TABLE_PRODUCT, DbHelper.PRODUCT_ID + " = " + String.valueOf(that.getId()), null);
	}

	public long deleteById(long id){
		return database.delete(DbHelper.TABLE_PRODUCT, DbHelper.PRODUCT_ID + " = " + String.valueOf(id), null);
	}

	public Cursor listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_PRODUCT,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_PRODUCT,
			selectableColumns,
			DbHelper.PRODUCT_ID + " = " + id,
			null, null, null, null);

		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_system, " +
			"barcode, " +
			"description, " +
			"amount, " +
			"fk_gender, " +
			"purchase_price, " +
			"sale_price, " +
			"fk_category, " +
			"size, " +
			"fk_unit, " +
			"fk_currency, " +
			"expiration_date, " +
			"fk_brand" +
			" FROM " + DbHelper.TABLE_PRODUCT;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";

		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_PRODUCT +";";
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

