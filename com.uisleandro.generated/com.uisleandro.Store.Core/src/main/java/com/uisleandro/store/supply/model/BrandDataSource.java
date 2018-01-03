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
//import com.uisleandro.store.supply.model.BrandDbHelper;
import com.uisleandro.store.supply.model.DbHelper;
import com.uisleandro.store.supply.view.BrandView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class BrandDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.BRAND_ID,
		DbHelper.BRAND_SERVER_ID,
		DbHelper.BRAND_DIRTY,
		DbHelper.BRAND_LAST_UPDATE, 
		DbHelper.BRAND_COMPANY_NAME, 
		DbHelper.BRAND_FANTASY_NAME
	};

	public BrandDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("BrandDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public BrandView cursorToBrandView(Cursor cursor){

	 	BrandView that = new BrandView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setCompanyName(cursor.getString(1));
		that.setFantasyName(cursor.getString(2));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<BrandView> cursorToListOfBrandView(Cursor cursor){

		List<BrandView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			BrandView that = cursorToBrandView(cursor);
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


	public long insert(BrandView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.BRAND_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.BRAND_DIRTY, that.isDirty());
		values.put(DbHelper.BRAND_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.BRAND_COMPANY_NAME, that.getCompanyName());
		values.put(DbHelper.BRAND_FANTASY_NAME, that.getFantasyName());
		long last_id = database.insert(DbHelper.TABLE_BRAND, null, values);
		return last_id;
	}

	public int update(BrandView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.BRAND_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.BRAND_DIRTY, that.isDirty());

		values.put(DbHelper.BRAND_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.BRAND_COMPANY_NAME, that.getCompanyName());
		values.put(DbHelper.BRAND_FANTASY_NAME, that.getFantasyName());

		int rows_affected = database.update(DbHelper.TABLE_BRAND, values, DbHelper.BRAND_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(BrandView that){
		database.delete(DbHelper.TABLE_BRAND, DbHelper.BRAND_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_BRAND, DbHelper.BRAND_ID + " = " + String.valueOf(id), null);
	}

	public List<BrandView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_BRAND,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfBrandView(cursor);
	}

	public BrandView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_BRAND,
			selectableColumns,
			DbHelper.BRAND_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		BrandView that = cursorToBrandView(cursor);
		cursor.close();
		return that;
	}

	public List<BrandView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"company_name, " +
			"fantasy_name" +
			" FROM " + DbHelper.TABLE_BRAND;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfBrandView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_BRAND +";";
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

