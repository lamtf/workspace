package com.uisleandro.store.credit_protection.model;

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.util.Log;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import com.uisleandro.store.DbHelper;
import com.uisleandro.store.credit_protection.view.IssueDataView;

public class IssueOfflineHelper {

	private Context context;
	private SQLiteDatabase database;
	private DbHelper db_helper;

	public IssueOfflineHelper (Context context) {
		this.context = context;
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("IssueOfflineHelper", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public long insert(IssueDataView that){
		ContentValues values = new ContentValues();
		//should not set the server id

		if(that.getServerId() > 0){
			values.put(DbHelper.ISSUE_SERVER_ID, that.getServerId());
		}

		values.put(DbHelper.ISSUE_DIRTY, that.isDirty());
		values.put(DbHelper.ISSUE_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSharedClient() > 0){
			values.put(DbHelper.ISSUE_FK_SHARED_CLIENT, that.getFkSharedClient());
		}
		if(that.getFkSystem() > 0){
			values.put(DbHelper.ISSUE_FK_SYSTEM, that.getFkSystem());
		}
		values.put(DbHelper.ISSUE_DESCRIPTION, that.getDescription());
		values.put(DbHelper.ISSUE_ACTIVE, that.getActive());
		values.put(DbHelper.ISSUE_ISANSWER, that.getIsAnswer());
		if(that.getFkIssue() > 0){
			values.put(DbHelper.ISSUE_FK_ISSUE, that.getFkIssue());
		}
		long last_id = database.insert(DbHelper.TABLE_ISSUE, null, values);
		return last_id;
	}

	public int update(IssueDataView that){
		ContentValues values = new ContentValues();
		if(that.getServerId() > 0){
			values.put(DbHelper.ISSUE_SERVER_ID, that.getServerId());
		}
		values.put(DbHelper.ISSUE_DIRTY, that.isDirty());

		values.put(DbHelper.ISSUE_LAST_UPDATE, that.getLastUpdate());
		if(that.getFkSharedClient() > 0){
			values.put(DbHelper.ISSUE_FK_SHARED_CLIENT, that.getFkSharedClient());
		}
		if(that.getFkSystem() > 0){
			values.put(DbHelper.ISSUE_FK_SYSTEM, that.getFkSystem());
		}
		values.put(DbHelper.ISSUE_DESCRIPTION, that.getDescription());
		values.put(DbHelper.ISSUE_ACTIVE, that.getActive());
		values.put(DbHelper.ISSUE_ISANSWER, that.getIsAnswer());
		if(that.getFkIssue() > 0){
			values.put(DbHelper.ISSUE_FK_ISSUE, that.getFkIssue());
		}
		int rows_affected = database.update(DbHelper.TABLE_ISSUE, values, DbHelper.ISSUE_ID + " = " + String.valueOf(that.getId()), null);
		return rows_affected;
	}

	public List<IssueDataView> listForInsertOnServer(long page_count, long page_size){

		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t2.server_id as fk_shared_client, " +
		"t3.server_id as fk_system, " +
		"t0.description, " +
		"t0.active, " +
		"t0.isAnswer, " +
		"t7.server_id as fk_issue" +
		" FROM "+DbHelper.TABLE_ISSUE+" t0" +
		" INNER JOIN "+DbHelper.TABLE_SHARED_CLIENT+" t1 ON t0.fk_shared_client = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_SYSTEM+" t2 ON t0.fk_system = t2.id" +
		" INNER JOIN "+DbHelper.TABLE_ISSUE+" t3 ON t0.fk_issue = t3.id";		query += " WHERE t0.server_id IS NULL";
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Log.wtf("rest-api", query);
		List<IssueDataView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(IssueDataView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
		return those;
	}

	//list for update on server
	//translates the foreign keys
	public List<IssueDataView> listForUpdateOnServer(long page_count, long page_size){
		//Log.wtf("rest-api", "listSomeDirty");
		// Estou com um erro pois nao gero as tabelas que nao fazem parte deste modulo
		String query = "SELECT t0.id, t0.server_id, t0.dirty, "+
		"t0.last_update, " +
		"t0.description, " +
		"t0.active, " +
		"t0.isAnswer" +
		" FROM "+DbHelper.TABLE_ISSUE+" t0" +
		" INNER JOIN "+DbHelper.TABLE_SHARED_CLIENT+" t1 ON t0.fk_shared_client = t1.id" +
		" INNER JOIN "+DbHelper.TABLE_SYSTEM+" t2 ON t0.fk_system = t2.id" +
		" INNER JOIN "+DbHelper.TABLE_ISSUE+" t3 ON t0.fk_issue = t3.id";		query += " WHERE t0." + DbHelper.ISSUE_DIRTY + " = 1";
		if(page_size > 0) {
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		//Log.wtf("rest-api", query);
		List<IssueDataView> those = new ArrayList<>();
		Cursor cursor = database.rawQuery(query, null);
		cursor.moveToFirst();
	    while(!cursor.isAfterLast()){
	      those.add(IssueDataView.FromCursor(cursor));
	      cursor.moveToNext();
	    }
	    cursor.close();
	}

	public int fixAfterServerInsertAndUpdate(long local_id, long remote_id, long last_update_time){
		ContentValues values = new ContentValues();
		values.put(DbHelper.ISSUE_SERVER_ID, remote_id);
		values.put(DbHelper.ISSUE_LAST_UPDATE, last_update_time);
		values.put(DbHelper.ISSUE_DIRTY, 0);
		int rows_affected = database.update(
			DbHelper.TABLE_ISSUE,
			values,
			DbHelper.ISSUE_ID + " = " + String.valueOf(local_id),
		null);
		return rows_affected;
	}

	// given the last id i have on client i can
	// on the client side
	public long getLastServerId(){
		long result = 0;
		String query = "SELECT MAX(server_id) FROM " + DbHelper.TABLE_ISSUE +";";
		Cursor cursor = database.rawQuery(query, null);
		long res = cursor.getLong(0);
		cursor.close();
		return res;
	}

	//the idea is that i don't want to iterate over all the data,
	//bringing all the data to the server is expensive, even if the return value is null sometimes
	//just bring from the server what is newer than my newer data, for updating
	//the implementation is also easier :D
	public long getLastUpdateTime(){
		long result = 0;
		String query = "SELECT last_update_time FROM " + DbHelper.TABLE_UPDATE_HISTORY +" WHERE table_name = '"+DbHelper.TABLE_ISSUE+"';";
		Cursor cursor = database.rawQuery(query, null);
		long res = cursor.getLong(0);
		cursor.close();
		return res;
	} 

	//get the last_update_time, from this table, if if null
	public void before_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = ( SELECT MAX(last_update_time) FROM " +
			DbHelper.TABLE_ISSUE + " ) WHERE table_name = '" + DbHelper.TABLE_ISSUE + "' AND last_update_time IS NULL;";
		database.rawQuery(query, null);
	}

	//set the last_update_time, from this table, to null
	public void after_client_updating(){
		String query = "UPDATE " + DbHelper.TABLE_UPDATE_HISTORY + " SET last_update_time = NULL WHERE table_name = '" + DbHelper.TABLE_ISSUE + "';";
		database.rawQuery(query, null);
	}


	//after i will update then client
	//and after updating the client i need to fix the foreign keys
	public int fixClientForeignKeys(){
		String query = "UPDATE " + DbHelper.TABLE_ISSUE + " SET "+
		"fk_shared_client = ( SELECT id FROM " + DbHelper.TABLE_SHARED_CLIENT + " WHERE " + DbHelper.TABLE_SHARED_CLIENT + ".server_id = " + DbHelper.TABLE_ISSUE + ".fk_shared_client ), " +
		"fk_system = ( SELECT id FROM " + DbHelper.TABLE_SYSTEM + " WHERE " + DbHelper.TABLE_SYSTEM + ".server_id = " + DbHelper.TABLE_ISSUE + ".fk_system ), " +
		"fk_issue = ( SELECT id FROM " + DbHelper.TABLE_ISSUE + " WHERE " + DbHelper.TABLE_ISSUE + ".server_id = " + DbHelper.TABLE_ISSUE + ".fk_issue );";		int result = 0;
		Cursor cursor = database.rawQuery(query, null);
		int res = cursor.getInt(0);
		cursor.close();
		return res;
	}



}
