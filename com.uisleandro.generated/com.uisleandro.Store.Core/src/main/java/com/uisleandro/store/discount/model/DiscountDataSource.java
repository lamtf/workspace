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
//import com.uisleandro.store.discount.model.DiscountDbHelper;
import com.uisleandro.store.discount.model.DbHelper;
import com.uisleandro.store.discount.view.DiscountView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class DiscountDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.DISCOUNT_ID,
		DbHelper.DISCOUNT_SERVER_ID,
		DbHelper.DISCOUNT_DIRTY,
		DbHelper.DISCOUNT_LAST_UPDATE, 
		DbHelper.DISCOUNT_VALUE, 
		DbHelper.DISCOUNT_PERCENTAGE, 
		DbHelper.DISCOUNT_FK_PRODUCT, 
		DbHelper.DISCOUNT_FK_CATEGORY, 
		DbHelper.DISCOUNT_FK_BRAND, 
		DbHelper.DISCOUNT_FK_CLIENT_FROM_SYSTEM, 
		DbHelper.DISCOUNT_FK_GENDER
	};

	public DiscountDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("DiscountDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public DiscountView cursorToDiscountView(Cursor cursor){

	 	DiscountView that = new DiscountView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setValue(cursor.getFloat(1));
		that.setPercentage(cursor.getFloat(2));
		that.setFkProduct(cursor.getLong(3));
		that.setFkCategory(cursor.getLong(4));
		that.setFkBrand(cursor.getLong(5));
		that.setFkClientFromSystem(cursor.getLong(6));
		that.setFkGender(cursor.getLong(7));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<DiscountView> cursorToListOfDiscountView(Cursor cursor){

		List<DiscountView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			DiscountView that = cursorToDiscountView(cursor);
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
		if(that.getFkProduct() > 0){
			values.put(DbHelper.DISCOUNT_FK_PRODUCT, that.getFkProduct());
		}
		if(that.getFkCategory() > 0){
			values.put(DbHelper.DISCOUNT_FK_CATEGORY, that.getFkCategory());
		}
		if(that.getFkBrand() > 0){
			values.put(DbHelper.DISCOUNT_FK_BRAND, that.getFkBrand());
		}
		if(that.getFkClientFromSystem() > 0){
			values.put(DbHelper.DISCOUNT_FK_CLIENT_FROM_SYSTEM, that.getFkClientFromSystem());
		}
		if(that.getFkGender() > 0){
			values.put(DbHelper.DISCOUNT_FK_GENDER, that.getFkGender());
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
		if(that.getFkProduct() > 0){
			values.put(DbHelper.DISCOUNT_FK_PRODUCT, that.getFkProduct());
		}
		if(that.getFkCategory() > 0){
			values.put(DbHelper.DISCOUNT_FK_CATEGORY, that.getFkCategory());
		}
		if(that.getFkBrand() > 0){
			values.put(DbHelper.DISCOUNT_FK_BRAND, that.getFkBrand());
		}
		if(that.getFkClientFromSystem() > 0){
			values.put(DbHelper.DISCOUNT_FK_CLIENT_FROM_SYSTEM, that.getFkClientFromSystem());
		}
		if(that.getFkGender() > 0){
			values.put(DbHelper.DISCOUNT_FK_GENDER, that.getFkGender());
		}

		int rows_affected = database.update(DbHelper.TABLE_DISCOUNT, values, DbHelper.DISCOUNT_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(DiscountView that){
		database.delete(DbHelper.TABLE_DISCOUNT, DbHelper.DISCOUNT_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_DISCOUNT, DbHelper.DISCOUNT_ID + " = " + String.valueOf(id), null);
	}

	public List<DiscountView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_DISCOUNT,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfDiscountView(cursor);
	}

	public DiscountView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_DISCOUNT,
			selectableColumns,
			DbHelper.DISCOUNT_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		DiscountView that = cursorToDiscountView(cursor);
		cursor.close();
		return that;
	}

	public List<DiscountView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"value, " +
			"percentage, " +
			"fk_product, " +
			"fk_category, " +
			"fk_brand, " +
			"fk_client_from_system, " +
			"fk_gender" +
			" FROM " + DbHelper.TABLE_DISCOUNT;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfDiscountView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_DISCOUNT +";";
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

