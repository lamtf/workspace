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
//import com.uisleandro.store.core.model.TokenTypeDbHelper;
import com.uisleandro.store.core.model.DbHelper;
import com.uisleandro.store.core.view.TokenTypeView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class TokenTypeDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.TOKEN_TYPE_ID,
		DbHelper.TOKEN_TYPE_SERVER_ID,
		DbHelper.TOKEN_TYPE_DIRTY,
		DbHelper.TOKEN_TYPE_LAST_UPDATE, 
		DbHelper.TOKEN_TYPE_NAME
	};

	public TokenTypeDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("TokenTypeDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public TokenTypeView cursorToTokenTypeView(Cursor cursor){

	 	TokenTypeView that = new TokenTypeView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setName(cursor.getString(1));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<TokenTypeView> cursorToListOfTokenTypeView(Cursor cursor){

		List<TokenTypeView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			TokenTypeView that = cursorToTokenTypeView(cursor);
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


	public long insert(TokenTypeView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.TOKEN_TYPE_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.TOKEN_TYPE_DIRTY, that.isDirty());
		values.put(DbHelper.TOKEN_TYPE_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.TOKEN_TYPE_NAME, that.getName());
		long last_id = database.insert(DbHelper.TABLE_TOKEN_TYPE, null, values);
		return last_id;
	}

	public int update(TokenTypeView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.TOKEN_TYPE_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.TOKEN_TYPE_DIRTY, that.isDirty());

		values.put(DbHelper.TOKEN_TYPE_LAST_UPDATE, that.getLastUpdate());
		values.put(DbHelper.TOKEN_TYPE_NAME, that.getName());

		int rows_affected = database.update(DbHelper.TABLE_TOKEN_TYPE, values, DbHelper.TOKEN_TYPE_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(TokenTypeView that){
		database.delete(DbHelper.TABLE_TOKEN_TYPE, DbHelper.TOKEN_TYPE_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_TOKEN_TYPE, DbHelper.TOKEN_TYPE_ID + " = " + String.valueOf(id), null);
	}

	public List<TokenTypeView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_TOKEN_TYPE,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfTokenTypeView(cursor);
	}

	public TokenTypeView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_TOKEN_TYPE,
			selectableColumns,
			DbHelper.TOKEN_TYPE_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		TokenTypeView that = cursorToTokenTypeView(cursor);
		cursor.close();
		return that;
	}

	public List<TokenTypeView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"name" +
			" FROM " + DbHelper.TABLE_TOKEN_TYPE;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfTokenTypeView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_TOKEN_TYPE +";";
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

