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
//import com.uisleandro.store.client.model.ClientFromSystemDbHelper;
import com.uisleandro.store.client.model.DbHelper;
import com.uisleandro.store.client.view.ClientFromSystemView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class ClientFromSystemDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.CLIENT_FROM_SYSTEM_ID,
		DbHelper.CLIENT_FROM_SYSTEM_SERVER_ID,
		DbHelper.CLIENT_FROM_SYSTEM_DIRTY,
		DbHelper.CLIENT_FROM_SYSTEM_LAST_UPDATE, 
		DbHelper.CLIENT_FROM_SYSTEM_FK_SYSTEM, 
		DbHelper.CLIENT_FROM_SYSTEM_FK_BASIC_CLIENT, 
		DbHelper.CLIENT_FROM_SYSTEM_FK_SHARED_CLIENT, 
		DbHelper.CLIENT_FROM_SYSTEM_FK_USER
	};

	public ClientFromSystemDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("ClientFromSystemDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public ClientFromSystemView cursorToClientFromSystemView(Cursor cursor){

	 	ClientFromSystemView that = new ClientFromSystemView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setFkSystem(cursor.getLong(1));
		that.setFkBasicClient(cursor.getLong(2));
		that.setFkSharedClient(cursor.getLong(3));
		that.setFkUser(cursor.getLong(4));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<ClientFromSystemView> cursorToListOfClientFromSystemView(Cursor cursor){

		List<ClientFromSystemView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			ClientFromSystemView that = cursorToClientFromSystemView(cursor);
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


	public long insert(ClientFromSystemView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.CLIENT_FROM_SYSTEM_DIRTY, that.isDirty());
		values.put(DbHelper.CLIENT_FROM_SYSTEM_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSystem() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_SYSTEM, that.getFkSystem());
		}
		if(that.getFkBasicClient() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_BASIC_CLIENT, that.getFkBasicClient());
		}
		if(that.getFkSharedClient() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_SHARED_CLIENT, that.getFkSharedClient());
		}
		if(that.getFkUser() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_USER, that.getFkUser());
		}
		long last_id = database.insert(DbHelper.TABLE_CLIENT_FROM_SYSTEM, null, values);
		return last_id;
	}

	public int update(ClientFromSystemView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.CLIENT_FROM_SYSTEM_DIRTY, that.isDirty());

		values.put(DbHelper.CLIENT_FROM_SYSTEM_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSystem() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_SYSTEM, that.getFkSystem());
		}
		if(that.getFkBasicClient() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_BASIC_CLIENT, that.getFkBasicClient());
		}
		if(that.getFkSharedClient() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_SHARED_CLIENT, that.getFkSharedClient());
		}
		if(that.getFkUser() > 0){
			values.put(DbHelper.CLIENT_FROM_SYSTEM_FK_USER, that.getFkUser());
		}

		int rows_affected = database.update(DbHelper.TABLE_CLIENT_FROM_SYSTEM, values, DbHelper.CLIENT_FROM_SYSTEM_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(ClientFromSystemView that){
		database.delete(DbHelper.TABLE_CLIENT_FROM_SYSTEM, DbHelper.CLIENT_FROM_SYSTEM_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_CLIENT_FROM_SYSTEM, DbHelper.CLIENT_FROM_SYSTEM_ID + " = " + String.valueOf(id), null);
	}

	public List<ClientFromSystemView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_CLIENT_FROM_SYSTEM,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfClientFromSystemView(cursor);
	}

	public ClientFromSystemView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_CLIENT_FROM_SYSTEM,
			selectableColumns,
			DbHelper.CLIENT_FROM_SYSTEM_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		ClientFromSystemView that = cursorToClientFromSystemView(cursor);
		cursor.close();
		return that;
	}

	public List<ClientFromSystemView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_system, " +
			"fk_basic_client, " +
			"fk_shared_client, " +
			"fk_user" +
			" FROM " + DbHelper.TABLE_CLIENT_FROM_SYSTEM;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfClientFromSystemView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_CLIENT_FROM_SYSTEM +";";
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

