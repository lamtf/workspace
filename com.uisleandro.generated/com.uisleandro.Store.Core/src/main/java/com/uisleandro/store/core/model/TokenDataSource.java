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
//import com.uisleandro.store.core.model.TokenDbHelper;
import com.uisleandro.store.core.model.DbHelper;
import com.uisleandro.store.core.view.TokenView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class TokenDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.TOKEN_ID,
		DbHelper.TOKEN_SERVER_ID,
		DbHelper.TOKEN_DIRTY,
		DbHelper.TOKEN_LAST_UPDATE, 
		DbHelper.TOKEN_FK_USER, 
		DbHelper.TOKEN_FK_SYSTEM, 
		DbHelper.TOKEN_FK_TOKEN_TYPE, 
		DbHelper.TOKEN_GUID, 
		DbHelper.TOKEN_LAST_USE_TIME, 
		DbHelper.TOKEN_EXPIRATION_TIME
	};

	public TokenDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("TokenDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public TokenView cursorToTokenView(Cursor cursor){

	 	TokenView that = new TokenView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setFkUser(cursor.getLong(1));
		that.setFkSystem(cursor.getLong(2));
		that.setFkTokenType(cursor.getLong(3));
		that.setGuid(cursor.getString(4));
		that.setLastUseTime(cursor.getLong(5));
		that.setExpirationTime(cursor.getLong(6));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<TokenView> cursorToListOfTokenView(Cursor cursor){

		List<TokenView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			TokenView that = cursorToTokenView(cursor);
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


	public long insert(TokenView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.TOKEN_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.TOKEN_DIRTY, that.isDirty());
		values.put(DbHelper.TOKEN_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkUser() > 0){
			values.put(DbHelper.TOKEN_FK_USER, that.getFkUser());
		}
		if(that.getFkSystem() > 0){
			values.put(DbHelper.TOKEN_FK_SYSTEM, that.getFkSystem());
		}
		if(that.getFkTokenType() > 0){
			values.put(DbHelper.TOKEN_FK_TOKEN_TYPE, that.getFkTokenType());
		}
		values.put(DbHelper.TOKEN_GUID, that.getGuid());
		values.put(DbHelper.TOKEN_LAST_USE_TIME, that.getLastUseTime());
		values.put(DbHelper.TOKEN_EXPIRATION_TIME, that.getExpirationTime());
		long last_id = database.insert(DbHelper.TABLE_TOKEN, null, values);
		return last_id;
	}

	public int update(TokenView that){
		ContentValues values = new ContentValues();

		if(that.getServerId() > 0){
			values.put(DbHelper.TOKEN_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.TOKEN_DIRTY, that.isDirty());

		values.put(DbHelper.TOKEN_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkUser() > 0){
			values.put(DbHelper.TOKEN_FK_USER, that.getFkUser());
		}
		if(that.getFkSystem() > 0){
			values.put(DbHelper.TOKEN_FK_SYSTEM, that.getFkSystem());
		}
		if(that.getFkTokenType() > 0){
			values.put(DbHelper.TOKEN_FK_TOKEN_TYPE, that.getFkTokenType());
		}
		values.put(DbHelper.TOKEN_GUID, that.getGuid());
		values.put(DbHelper.TOKEN_LAST_USE_TIME, that.getLastUseTime());
		values.put(DbHelper.TOKEN_EXPIRATION_TIME, that.getExpirationTime());

		int rows_affected = database.update(DbHelper.TABLE_TOKEN, values, DbHelper.TOKEN_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public void delete(TokenView that){
		database.delete(DbHelper.TABLE_TOKEN, DbHelper.TOKEN_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_TOKEN, DbHelper.TOKEN_ID + " = " + String.valueOf(id), null);
	}

	public List<TokenView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_TOKEN,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfTokenView(cursor);
	}

	public TokenView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_TOKEN,
			selectableColumns,
			DbHelper.TOKEN_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		TokenView that = cursorToTokenView(cursor);
		cursor.close();
		return that;
	}

	public List<TokenView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_user, " +
			"fk_system, " +
			"fk_token_type, " +
			"guid, " +
			"last_use_time, " +
			"expiration_time" +
			" FROM " + DbHelper.TABLE_TOKEN;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfTokenView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_TOKEN +";";
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

