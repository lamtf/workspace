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
//import com.uisleandro.store.credit_protection.model.SharedClientDbHelper;
import com.uisleandro.store.credit_protection.model.DbHelper;
import com.uisleandro.store.credit_protection.view.SharedClientView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class SharedClientDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.SHARED_CLIENT_ID,
		DbHelper.SHARED_CLIENT_SERVER_ID,
		DbHelper.SHARED_CLIENT_DIRTY,
		DbHelper.SHARED_CLIENT_LAST_UPDATE, 
		DbHelper.SHARED_CLIENT_NAME, 
		DbHelper.SHARED_CLIENT_BIRTH_DATE, 
		DbHelper.SHARED_CLIENT_BIRTH_CITY, 
		DbHelper.SHARED_CLIENT_BIRTH_STATE, 
		DbHelper.SHARED_CLIENT_MOTHERS_NAME, 
		DbHelper.SHARED_CLIENT_FATHERS_NAME, 
		DbHelper.SHARED_CLIENT_PROFESSION, 
		DbHelper.SHARED_CLIENT_ZIP_CODE, 
		DbHelper.SHARED_CLIENT_ADDRESS, 
		DbHelper.SHARED_CLIENT_NEIGHBORHOOD, 
		DbHelper.SHARED_CLIENT_CITY, 
		DbHelper.SHARED_CLIENT_STATE, 
		DbHelper.SHARED_CLIENT_COMPLEMENT, 
		DbHelper.SHARED_CLIENT_FK_COUNTRY
	};

	public SharedClientDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("SharedClientDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public SharedClientView cursorToSharedClientView(Cursor cursor){

	 	SharedClientView that = new SharedClientView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setName(cursor.getString(1));
		that.setBirthDate(cursor.getLong(2));
		that.setBirthCity(cursor.getString(3));
		that.setBirthState(cursor.getString(4));
		that.setMothersName(cursor.getString(5));
		that.setFathersName(cursor.getString(6));
		that.setProfession(cursor.getString(7));
		that.setZipCode(cursor.getString(8));
		that.setAddress(cursor.getString(9));
		that.setNeighborhood(cursor.getString(10));
		that.setCity(cursor.getString(11));
		that.setState(cursor.getString(12));
		that.setComplement(cursor.getString(13));
		that.setFkCountry(cursor.getLong(14));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<SharedClientView> cursorToListOfSharedClientView(Cursor cursor){

		List<SharedClientView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			SharedClientView that = cursorToSharedClientView(cursor);
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


	public long insert(SharedClientView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.SHARED_CLIENT_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.SHARED_CLIENT_DIRTY, that.isDirty());
		values.put(DbHelper.SHARED_CLIENT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.SHARED_CLIENT_NAME, that.getName());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_DATE, that.getBirthDate());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_CITY, that.getBirthCity());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_STATE, that.getBirthState());
		values.put(DbHelper.SHARED_CLIENT_MOTHERS_NAME, that.getMothersName());
		values.put(DbHelper.SHARED_CLIENT_FATHERS_NAME, that.getFathersName());
		values.put(DbHelper.SHARED_CLIENT_PROFESSION, that.getProfession());
		values.put(DbHelper.SHARED_CLIENT_ZIP_CODE, that.getZipCode());
		values.put(DbHelper.SHARED_CLIENT_ADDRESS, that.getAddress());
		values.put(DbHelper.SHARED_CLIENT_NEIGHBORHOOD, that.getNeighborhood());
		values.put(DbHelper.SHARED_CLIENT_CITY, that.getCity());
		values.put(DbHelper.SHARED_CLIENT_STATE, that.getState());
		values.put(DbHelper.SHARED_CLIENT_COMPLEMENT, that.getComplement());
		if(that.getFkCountry() > 0){
			values.put(DbHelper.SHARED_CLIENT_FK_COUNTRY, that.getFkCountry());
		}
		long last_id = database.insert(DbHelper.TABLE_SHARED_CLIENT, null, values);
		return last_id;
	}

	public int update(SharedClientView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.SHARED_CLIENT_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.SHARED_CLIENT_DIRTY, that.isDirty());

		values.put(DbHelper.SHARED_CLIENT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.SHARED_CLIENT_NAME, that.getName());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_DATE, that.getBirthDate());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_CITY, that.getBirthCity());
		values.put(DbHelper.SHARED_CLIENT_BIRTH_STATE, that.getBirthState());
		values.put(DbHelper.SHARED_CLIENT_MOTHERS_NAME, that.getMothersName());
		values.put(DbHelper.SHARED_CLIENT_FATHERS_NAME, that.getFathersName());
		values.put(DbHelper.SHARED_CLIENT_PROFESSION, that.getProfession());
		values.put(DbHelper.SHARED_CLIENT_ZIP_CODE, that.getZipCode());
		values.put(DbHelper.SHARED_CLIENT_ADDRESS, that.getAddress());
		values.put(DbHelper.SHARED_CLIENT_NEIGHBORHOOD, that.getNeighborhood());
		values.put(DbHelper.SHARED_CLIENT_CITY, that.getCity());
		values.put(DbHelper.SHARED_CLIENT_STATE, that.getState());
		values.put(DbHelper.SHARED_CLIENT_COMPLEMENT, that.getComplement());
		if(that.getFkCountry() > 0){
			values.put(DbHelper.SHARED_CLIENT_FK_COUNTRY, that.getFkCountry());
		}

		int rows_affected = database.update(DbHelper.TABLE_SHARED_CLIENT, values, DbHelper.SHARED_CLIENT_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(SharedClientView that){
		database.delete(DbHelper.TABLE_SHARED_CLIENT, DbHelper.SHARED_CLIENT_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_SHARED_CLIENT, DbHelper.SHARED_CLIENT_ID + " = " + String.valueOf(id), null);
	}

	public List<SharedClientView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_SHARED_CLIENT,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfSharedClientView(cursor);
	}

	public SharedClientView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_SHARED_CLIENT,
			selectableColumns,
			DbHelper.SHARED_CLIENT_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		SharedClientView that = cursorToSharedClientView(cursor);
		cursor.close();
		return that;
	}

	public List<SharedClientView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"name, " +
			"birth_date, " +
			"birth_city, " +
			"birth_state, " +
			"mothers_name, " +
			"fathers_name, " +
			"profession, " +
			"zip_code, " +
			"address, " +
			"neighborhood, " +
			"city, " +
			"state, " +
			"complement, " +
			"fk_country" +
			" FROM " + DbHelper.TABLE_SHARED_CLIENT;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfSharedClientView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_SHARED_CLIENT +";";
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

