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
//import com.uisleandro.store.supply.model.DistributorContactDbHelper;
import com.uisleandro.store.supply.model.DbHelper;
import com.uisleandro.store.supply.view.DistributorContactView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class DistributorContactDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.DISTRIBUTOR_CONTACT_ID,
		DbHelper.DISTRIBUTOR_CONTACT_SERVER_ID,
		DbHelper.DISTRIBUTOR_CONTACT_DIRTY,
		DbHelper.DISTRIBUTOR_CONTACT_LAST_UPDATE, 
		DbHelper.DISTRIBUTOR_CONTACT_NAME, 
		DbHelper.DISTRIBUTOR_CONTACT_EMAIL1, 
		DbHelper.DISTRIBUTOR_CONTACT_EMAIL2, 
		DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER1, 
		DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER2, 
		DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER3, 
		DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER4, 
		DbHelper.DISTRIBUTOR_CONTACT_FK_BRAND
	};

	public DistributorContactDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("DistributorContactDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public DistributorContactView cursorToDistributorContactView(Cursor cursor){

	 	DistributorContactView that = new DistributorContactView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setName(cursor.getString(1));
		that.setEmail1(cursor.getString(2));
		that.setEmail2(cursor.getString(3));
		that.setPhoneNumber1(cursor.getString(4));
		that.setPhoneNumber2(cursor.getString(5));
		that.setPhoneNumber3(cursor.getString(6));
		that.setPhoneNumber4(cursor.getString(7));
		that.setFkBrand(cursor.getLong(8));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<DistributorContactView> cursorToListOfDistributorContactView(Cursor cursor){

		List<DistributorContactView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			DistributorContactView that = cursorToDistributorContactView(cursor);
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


	public long insert(DistributorContactView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.DISTRIBUTOR_CONTACT_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.DISTRIBUTOR_CONTACT_DIRTY, that.isDirty());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_NAME, that.getName());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_EMAIL1, that.getEmail1());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_EMAIL2, that.getEmail2());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER1, that.getPhoneNumber1());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER2, that.getPhoneNumber2());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER3, that.getPhoneNumber3());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER4, that.getPhoneNumber4());
		if(that.getFkBrand() > 0){
			values.put(DbHelper.DISTRIBUTOR_CONTACT_FK_BRAND, that.getFkBrand());
		}
		long last_id = database.insert(DbHelper.TABLE_DISTRIBUTOR_CONTACT, null, values);
		return last_id;
	}

	public int update(DistributorContactView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.DISTRIBUTOR_CONTACT_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.DISTRIBUTOR_CONTACT_DIRTY, that.isDirty());

		values.put(DbHelper.DISTRIBUTOR_CONTACT_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_NAME, that.getName());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_EMAIL1, that.getEmail1());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_EMAIL2, that.getEmail2());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER1, that.getPhoneNumber1());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER2, that.getPhoneNumber2());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER3, that.getPhoneNumber3());
		values.put(DbHelper.DISTRIBUTOR_CONTACT_PHONE_NUMBER4, that.getPhoneNumber4());
		if(that.getFkBrand() > 0){
			values.put(DbHelper.DISTRIBUTOR_CONTACT_FK_BRAND, that.getFkBrand());
		}

		int rows_affected = database.update(DbHelper.TABLE_DISTRIBUTOR_CONTACT, values, DbHelper.DISTRIBUTOR_CONTACT_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(DistributorContactView that){
		database.delete(DbHelper.TABLE_DISTRIBUTOR_CONTACT, DbHelper.DISTRIBUTOR_CONTACT_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_DISTRIBUTOR_CONTACT, DbHelper.DISTRIBUTOR_CONTACT_ID + " = " + String.valueOf(id), null);
	}

	public List<DistributorContactView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_DISTRIBUTOR_CONTACT,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfDistributorContactView(cursor);
	}

	public DistributorContactView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_DISTRIBUTOR_CONTACT,
			selectableColumns,
			DbHelper.DISTRIBUTOR_CONTACT_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		DistributorContactView that = cursorToDistributorContactView(cursor);
		cursor.close();
		return that;
	}

	public List<DistributorContactView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"name, " +
			"email1, " +
			"email2, " +
			"phone_number1, " +
			"phone_number2, " +
			"phone_number3, " +
			"phone_number4, " +
			"fk_brand" +
			" FROM " + DbHelper.TABLE_DISTRIBUTOR_CONTACT;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfDistributorContactView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_DISTRIBUTOR_CONTACT +";";
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

