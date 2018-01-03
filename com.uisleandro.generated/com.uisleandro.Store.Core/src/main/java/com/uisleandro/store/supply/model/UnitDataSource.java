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
//import com.uisleandro.store.supply.model.UnitDbHelper;
import com.uisleandro.store.supply.model.DbHelper;
import com.uisleandro.store.supply.view.UnitView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class UnitDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.UNIT_ID,
		DbHelper.UNIT_SERVER_ID,
		DbHelper.UNIT_DIRTY,
		DbHelper.UNIT_LAST_UPDATE, 
		DbHelper.UNIT_NAME
	};

	public UnitDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("UnitDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public UnitView cursorToUnitView(Cursor cursor){

	 	UnitView that = new UnitView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setName(cursor.getString(1));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<UnitView> cursorToListOfUnitView(Cursor cursor){

		List<UnitView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			UnitView that = cursorToUnitView(cursor);
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


	public long insert(UnitView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.UNIT_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.UNIT_DIRTY, that.isDirty());
		values.put(DbHelper.UNIT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.UNIT_NAME, that.getName());
		long last_id = database.insert(DbHelper.TABLE_UNIT, null, values);
		return last_id;
	}

	public int update(UnitView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.UNIT_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.UNIT_DIRTY, that.isDirty());

		values.put(DbHelper.UNIT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.UNIT_NAME, that.getName());

		int rows_affected = database.update(DbHelper.TABLE_UNIT, values, DbHelper.UNIT_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(UnitView that){
		database.delete(DbHelper.TABLE_UNIT, DbHelper.UNIT_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_UNIT, DbHelper.UNIT_ID + " = " + String.valueOf(id), null);
	}

	public List<UnitView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_UNIT,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfUnitView(cursor);
	}

	public UnitView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_UNIT,
			selectableColumns,
			DbHelper.UNIT_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		UnitView that = cursorToUnitView(cursor);
		cursor.close();
		return that;
	}

	public List<UnitView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"name" +
			" FROM " + DbHelper.TABLE_UNIT;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfUnitView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_UNIT +";";
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

