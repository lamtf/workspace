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
//import com.uisleandro.store.core.model.RoleDbHelper;
import com.uisleandro.store.core.model.DbHelper;
import com.uisleandro.store.core.view.RoleView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class RoleDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.ROLE_ID,
		DbHelper.ROLE_SERVER_ID,
		DbHelper.ROLE_DIRTY,
		DbHelper.ROLE_LAST_UPDATE, 
		DbHelper.ROLE_NAME
	};

	public RoleDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("RoleDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public RoleView cursorToRoleView(Cursor cursor){

	 	RoleView that = new RoleView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setName(cursor.getString(1));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<RoleView> cursorToListOfRoleView(Cursor cursor){

		List<RoleView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			RoleView that = cursorToRoleView(cursor);
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


	public long insert(RoleView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.ROLE_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.ROLE_DIRTY, that.isDirty());
		values.put(DbHelper.ROLE_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.ROLE_NAME, that.getName());
		long last_id = database.insert(DbHelper.TABLE_ROLE, null, values);
		return last_id;
	}

	public int update(RoleView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.ROLE_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.ROLE_DIRTY, that.isDirty());

		values.put(DbHelper.ROLE_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.ROLE_NAME, that.getName());

		int rows_affected = database.update(DbHelper.TABLE_ROLE, values, DbHelper.ROLE_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(RoleView that){
		database.delete(DbHelper.TABLE_ROLE, DbHelper.ROLE_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_ROLE, DbHelper.ROLE_ID + " = " + String.valueOf(id), null);
	}

	public List<RoleView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_ROLE,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfRoleView(cursor);
	}

	public RoleView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_ROLE,
			selectableColumns,
			DbHelper.ROLE_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		RoleView that = cursorToRoleView(cursor);
		cursor.close();
		return that;
	}

	public List<RoleView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"name" +
			" FROM " + DbHelper.TABLE_ROLE;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfRoleView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_ROLE +";";
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

