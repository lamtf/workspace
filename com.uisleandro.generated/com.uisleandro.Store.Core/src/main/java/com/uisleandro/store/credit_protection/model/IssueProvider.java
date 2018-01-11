// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.Core.model;  

import java.util.ArrayList;
import java.util.List;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
//old
//import com.uisleandro.util.LongDateFormatter;
//import com.uisleandro.store.model.DbHelper;
//import com.uisleandro.store.credit_protection.model.IssueDbHelper;

import com.uisleandro.store.DbHelper;
// reserved-for:AndroidSqliteDatabase001
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle001
// reserved-for:AndroidSqliteSyncSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase002
public class IssueProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.Issue";
	public static final String SCHEME = "content://";

	public static final String ISSUE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_ISSUE_INSERT = Uri.parse(ISSUE_INSERT);
	public static final String ISSUE_INSERT_BASE = ISSUE_INSERT + "/";

	public static final String ISSUE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_ISSUE_UPDATE = Uri.parse(ISSUE_UPDATE);
	public static final String ISSUE_UPDATE_BASE = ISSUE_UPDATE + "/";

	public static final String ISSUE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_ISSUE_DELETE = Uri.parse(ISSUE_DELETE);
	public static final String ISSUE_DELETE_BASE = ISSUE_DELETE + "/";

	public static final String ISSUE_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_ISSUE_ALL = Uri.parse(ISSUE_ALL);
	public static final String ISSUE_ALL_BASE = ISSUE_ALL + "/";

	public static final String ISSUE_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_ISSUE_SOME = Uri.parse(ISSUE_SOME);
	public static final String ISSUE_SOME_BASE = ISSUE_SOME + "/";

	public static final String ISSUE_BYID = SCHEME + AUTHORITY + "/byid";
	public static final Uri URI_ISSUE_BYID = Uri.parse(ISSUE_BYID);
	public static final String ISSUE_BYID_BASE = ISSUE_BYID + "/";

	public static final String ISSUE_LASTID = SCHEME + AUTHORITY + "/lastid";
	public static final Uri URI_ISSUE_LASTID = Uri.parse(ISSUE_LASTID);
	public static final String ISSUE_LASTID_BASE = ISSUE_LASTID + "/";

// reserved-for:AndroidSqliteDatabase002
// End of user code


// Start of user code reserved-for:AndroidSqliteSyncSingle002
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	public static final String ISSUE_CHECK_CLIENT = SCHEME + AUTHORITY + "/check_client";
	public static final Uri URI_ISSUE_CHECK_CLIENT = Uri.parse(ISSUE_CHECK_CLIENT);
	public static final String ISSUE_CHECK_CLIENT_BASE = ISSUE_CHECK_CLIENT + "/";
	public static final String ISSUE_REGISTER_ISSUE = SCHEME + AUTHORITY + "/register_issue";
	public static final Uri URI_ISSUE_REGISTER_ISSUE = Uri.parse(ISSUE_REGISTER_ISSUE);
	public static final String ISSUE_REGISTER_ISSUE_BASE = ISSUE_REGISTER_ISSUE + "/";
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003
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

	public Cursor listAll(){
		Cursor cursor = database.query(DbHelper.TABLE_ISSUE,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){
		Cursor cursor = database.query(DbHelper.TABLE_ISSUE,
			selectableColumns,
			DbHelper.ISSUE_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){
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
		return cursor;
	}

	public Cursor getLastId(){
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_ISSUE +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;		
	}

// begin content-provider-interface

	@Override
	public boolean onCreate() {
		return false;
	}

	@Nullable
	@Override
	public String getType(@NonNull Uri uri) {
		return null;
	}
// reserved-for:AndroidSqliteDatabase003
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase004
	@Nullable
	@Override
	public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {
		Cursor result = null;
		if (URI_ISSUE_INSERT.equals(uri)) {
			result = database.insert(DbHelper.TABLE_ISSUE, null, values);
		}
// reserved-for:AndroidSqliteDatabase004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle003
/* @Insert */
	else if (URI_ISSUE_register_issue.equals(uri)) {
			result = register_issue(selectionArgs); // << missing arguments
	}
// reserved-for:AndroidSqliteQuerySingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase005
		return null;
	}
// reserved-for:AndroidSqliteDatabase005
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase006
	@Override
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_ISSUE_UPDATE.equals(uri)) {
			result = database.update(DbHelper.TABLE_ISSUE, values, DbHelper.ISSUE_ID + " = " + selectionArgs[0], null);
		}
// reserved-for:AndroidSqliteDatabase006
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle004
/* @UpdateWhere */
// reserved-for:AndroidSqliteQuerySingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase007
		return result;
	}
// reserved-for:AndroidSqliteDatabase007
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase008
	@Override
	public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_ISSUE_DELETE.equals(uri)) {
			result = database.delete(DbHelper.TABLE_ISSUE, DbHelper.ISSUE_ID + " = " + selectionArgs[0], null);
		}
// reserved-for:AndroidSqliteDatabase008
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle005
/* @DeleteWhere */
// reserved-for:AndroidSqliteQuerySingle005
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase009
		return result;
	}
// reserved-for:AndroidSqliteDatabase009
// End of user code

// end content-provider-interface

// Start of user code reserved-for:AndroidSqliteSyncSingle003
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle006
	/* @ExistsWhere */
	public Cursor check_client(String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT exists(*) FROM Issue WHERE Issue.fk_shared_client = ? AND Issue.active = ? AND (Issue.fk_issue is null or Issue.fk_issue = '');";
		Cursor cursor = database.rawQuery(query, new String[]{ String.valueOf(fk_shared_client), "1" });
		return cursor;
	}
	/* @Insert */
	public int register_issue(String[] selectionArgs){
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "INSERT INTO Issue(last_update,fk_shared_client,description,active,isAnswer) VALUES (?,?,?,?,?);";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		//TODO: I don't knwo if its returning the last_id, I guess it's not
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	}
// reserved-for:AndroidSqliteQuerySingle006
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase010
	// TODO: I NEED TO KNOW HOW TO MAKE VARIOUS QUERIES DEPENDING ON THE URI
	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Cursor result = null;
		if (URI_ISSUE_ALL.equals(uri)) {
			result = listAll();
		}
		else if(URI_ISSUE_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if(URI_ISSUE_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if(URI_ISSUE_LASTID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:AndroidSqliteDatabase010
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle004
// reserved-for:AndroidSqliteSyncSingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle007
/* @ExistsWhere||@SelectValueWhere||@SelectOneWhere||@SelectListWhere */
	else if (URI_ISSUE_CHECK_CLIENT.equals(uri)) {
		result = check_client(selectionArgs);
	}
// Start of user code reserved-for:AndroidSqliteQuerySingle007

// Start of user code reserved-for:AndroidSqliteDatabase011
		return result;
	}
}
// reserved-for:AndroidSqliteDatabase011
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase011
		return result;
	}
}
// reserved-for:AndroidSqliteDatabase011
// End of user code

