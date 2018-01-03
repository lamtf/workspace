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
//import com.uisleandro.store.credit_protection.model.IssueDbHelper;
import com.uisleandro.store.credit_protection.model.DbHelper;
import com.uisleandro.store.credit_protection.view.IssueView;
// reserved-for:android-sqlite-db.imports
//End of user code

//Start of user code reserved-for:android-sqlite-sync.imports
//reserved-for:android-sqlite-sync.imports
//End of user code

//Start of user code reserved-for:query3.imports
// reserved-for:query3.imports
//End of user code

//Start of user code reserved-for:android-sqlite-db.functions
public class IssueDataSource {

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.ISSUE_ID,
		DbHelper.ISSUE_SERVER_ID,
		DbHelper.ISSUE_DIRTY,
		DbHelper.ISSUE_LAST_UPDATE, 
		DbHelper.ISSUE_FK_SHARED_CLIENT, 
		DbHelper.ISSUE_FK_SYSTEM, 
		DbHelper.ISSUE_DESCRIPTION, 
		DbHelper.ISSUE_ACTIVE, 
		DbHelper.ISSUE_ISANSWER, 
		DbHelper.ISSUE_FK_ISSUE
	};

	public IssueDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("IssueDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public IssueView cursorToIssueView(Cursor cursor){

	 	IssueView that = new IssueView();
		that.setId(cursor.getLong(0));
		that.setServerId(cursor.getLong(1));
		that.setDirty(cursor.getInt(2) > 0);
		that.setLastUpdate(cursor.getLong(0));
		that.setFkSharedClient(cursor.getLong(1));
		that.setFkSystem(cursor.getLong(2));
		that.setDescription(cursor.getString(3));
		that.setActive((cursor.getInt(4) > 0));
		that.setIsAnswer((cursor.getInt(5) > 0));
		that.setFkIssue(cursor.getLong(6));
		return that;

	}

	//vai ser o desacoplamento do cursor
	public List<IssueView> cursorToListOfIssueView(Cursor cursor){

		List<IssueView> those = new ArrayList();

		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			IssueView that = cursorToIssueView(cursor);
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


	public long insert(IssueView that){
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

	public int update(IssueView that){
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

	public void delete(IssueView that){
		database.delete(DbHelper.TABLE_ISSUE, DbHelper.ISSUE_ID + " = " + String.valueOf(that.getId()), null);
	}

	public void deleteById(long id){
		database.delete(DbHelper.TABLE_ISSUE, DbHelper.ISSUE_ID + " = " + String.valueOf(id), null);
	}

	public List<IssueView> listAll(){

		Cursor cursor = database.query(DbHelper.TABLE_ISSUE,
			selectableColumns,null,null, null, null, null);

		return cursorToListOfIssueView(cursor);
	}

	public IssueView getById(long id){

		Cursor cursor = database.query(DbHelper.TABLE_ISSUE,
			selectableColumns,
			DbHelper.ISSUE_ID + " = " + id,
			null, null, null, null);

		cursor.moveToFirst();
		IssueView that = cursorToIssueView(cursor);
		cursor.close();
		return that;
	}

	public List<IssueView> listSome(long page_count, long page_size){

		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_shared_client, " +
			"fk_system, " +
			"description, " +
			"active, " +
			"isAnswer, " +
			"fk_issue" +
			" FROM " + DbHelper.TABLE_ISSUE;

		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}

		query += ";";

		Cursor cursor = database.rawQuery(query, null);

		return cursorToListOfIssueView(cursor);
	}

	public long getLastId(){

		long result = 0;

		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_ISSUE +";";
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

