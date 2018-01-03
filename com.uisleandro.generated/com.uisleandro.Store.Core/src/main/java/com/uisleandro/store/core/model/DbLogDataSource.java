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
//import com.uisleandro.store.core.model.DbLogDbHelper;
import com.uisleandro.store.core.model.DbHelper;
import com.uisleandro.store.core.view.DbLogView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class DbLogDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.DB_LOG_ID,
		DbHelper.DB_LOG_SERVER_ID,
		DbHelper.DB_LOG_DIRTY,
		DbHelper.DB_LOG_LAST_UPDATE, 
		DbHelper.DB_LOG_ACTION_NAME, 
		DbHelper.DB_LOG_PARAMETER, 
		DbHelper.DB_LOG_FK_USER
	};

	public DbLogDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("DbLogDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public DbLogView cursorToDbLogView(Cursor cursor){

	 	DbLogView that = new DbLogView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setActionName(cursor.getString(1));
		that.setParameter(cursor.getString(2));
		that.setFkUser(cursor.getLong(3));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<DbLogView> cursorToListOfDbLogView(Cursor cursor){

		List<DbLogView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			DbLogView that = cursorToDbLogView(cursor);
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


	public long insert(DbLogView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.DB_LOG_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.DB_LOG_DIRTY, that.isDirty());
		values.put(DbHelper.DB_LOG_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.DB_LOG_ACTION_NAME, that.getActionName());
		values.put(DbHelper.DB_LOG_PARAMETER, that.getParameter());
		if(that.getFkUser() > 0){
			values.put(DbHelper.DB_LOG_FK_USER, that.getFkUser());
		}
		long last_id = database.insert(DbHelper.TABLE_DB_LOG, null, values);
		return last_id;
	}

	public int update(DbLogView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.DB_LOG_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.DB_LOG_DIRTY, that.isDirty());

		values.put(DbHelper.DB_LOG_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.DB_LOG_ACTION_NAME, that.getActionName());
		values.put(DbHelper.DB_LOG_PARAMETER, that.getParameter());
		if(that.getFkUser() > 0){
			values.put(DbHelper.DB_LOG_FK_USER, that.getFkUser());
		}

		int rows_affected = database.update(DbHelper.TABLE_DB_LOG, values, DbHelper.DB_LOG_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(DbLogView that){
		database.delete(DbHelper.TABLE_DB_LOG, DbHelper.DB_LOG_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_DB_LOG, DbHelper.DB_LOG_ID + " = " + String.valueOf(id), null);
	}

	public List<DbLogView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_DB_LOG,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfDbLogView(cursor);
	}

	public DbLogView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_DB_LOG,
			selectableColumns,
			DbHelper.DB_LOG_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		DbLogView that = cursorToDbLogView(cursor);
		cursor.close();
		return that;
	}

	public List<DbLogView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"action_name, " +
			"parameter, " +
			"fk_user" +
			" FROM " + DbHelper.TABLE_DB_LOG;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfDbLogView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_DB_LOG +";";
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

