// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.client.model;  

import android.content.ContentProvider;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

//old
//import com.uisleandro.util.LongDateFormatter;
//import com.uisleandro.store.model.DbHelper;
//import com.uisleandro.store.client.model.BasicClientDbHelper;

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
public class BasicClientProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.basic_client";
	public static final String SCHEME = "content://";
	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	
	public static final int BASIC_CLIENT_INSERT_NUMBER = 1;
	public static final String BASIC_CLIENT_INSERT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/insert";
	public static final String BASIC_CLIENT_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_BASIC_CLIENT_INSERT = Uri.parse(BASIC_CLIENT_INSERT);
	public static final String BASIC_CLIENT_INSERT_BASE = BASIC_CLIENT_INSERT + "/";

	public static final int BASIC_CLIENT_UPDATE_NUMBER = 2;
	public static final String BASIC_CLIENT_UPDATE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/update";
	public static final String BASIC_CLIENT_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_BASIC_CLIENT_UPDATE = Uri.parse(BASIC_CLIENT_UPDATE);
	public static final String BASIC_CLIENT_UPDATE_BASE = BASIC_CLIENT_UPDATE + "/";

	public static final int BASIC_CLIENT_DELETE_NUMBER = 3;
	public static final String BASIC_CLIENT_DELETE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/delete";
	public static final String BASIC_CLIENT_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_BASIC_CLIENT_DELETE = Uri.parse(BASIC_CLIENT_DELETE);
	public static final String BASIC_CLIENT_DELETE_BASE = BASIC_CLIENT_DELETE + "/";

	public static final int BASIC_CLIENT_ALL_NUMBER = 4;
	public static final String BASIC_CLIENT_ALL_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/all";
	public static final String BASIC_CLIENT_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_BASIC_CLIENT_ALL = Uri.parse(BASIC_CLIENT_ALL);
	public static final String BASIC_CLIENT_ALL_BASE = BASIC_CLIENT_ALL + "/";

	public static final int BASIC_CLIENT_SOME_NUMBER = 5;
	public static final String BASIC_CLIENT_SOME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/some";
	public static final String BASIC_CLIENT_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_BASIC_CLIENT_SOME = Uri.parse(BASIC_CLIENT_SOME);
	public static final String BASIC_CLIENT_SOME_BASE = BASIC_CLIENT_SOME + "/";

	public static final int BASIC_CLIENT_BY_ID_NUMBER = 6;
	public static final String BASIC_CLIENT_BY_ID_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/by_id";
	public static final String BASIC_CLIENT_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final Uri URI_BASIC_CLIENT_BY_ID = Uri.parse(BASIC_CLIENT_BY_ID);
	public static final String BASIC_CLIENT_BY_ID_BASE = BASIC_CLIENT_BY_ID + "/";

	public static final int BASIC_CLIENT_LAST_ID_NUMBER = 7;
	public static final String BASIC_CLIENT_LAST_ID_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/last_id";
	public static final String BASIC_CLIENT_LAST_ID = SCHEME + AUTHORITY + "/last_id";
	public static final Uri URI_BASIC_CLIENT_LAST_ID = Uri.parse(BASIC_CLIENT_LAST_ID);
	public static final String BASIC_CLIENT_LAST_ID_BASE = BASIC_CLIENT_LAST_ID + "/";


// reserved-for:AndroidSqliteDatabase002
// End of user code


// Start of user code reserved-for:AndroidSqliteSyncSingle002
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	public static final int BASIC_CLIENT_FIND_BY_CPF_NUMBER = 8;
	public static final String BASIC_CLIENT_FIND_BY_CPF_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/find_by_cpf";
	public static final String BASIC_CLIENT_FIND_BY_CPF = SCHEME + AUTHORITY + "/find_by_cpf";
	public static final Uri URI_BASIC_CLIENT_FIND_BY_CPF = Uri.parse(BASIC_CLIENT_FIND_BY_CPF);
	public static final String BASIC_CLIENT_FIND_BY_CPF_BASE = BASIC_CLIENT_FIND_BY_CPF + "/";

	public static final int BASIC_CLIENT_FIND_BY_ID_NUMBER = 9;
	public static final String BASIC_CLIENT_FIND_BY_ID_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/find_by_id";
	public static final String BASIC_CLIENT_FIND_BY_ID = SCHEME + AUTHORITY + "/find_by_id";
	public static final Uri URI_BASIC_CLIENT_FIND_BY_ID = Uri.parse(BASIC_CLIENT_FIND_BY_ID);
	public static final String BASIC_CLIENT_FIND_BY_ID_BASE = BASIC_CLIENT_FIND_BY_ID + "/";

	public static final int BASIC_CLIENT_FIND_BY_NAME_NUMBER = 10;
	public static final String BASIC_CLIENT_FIND_BY_NAME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/find_by_name";
	public static final String BASIC_CLIENT_FIND_BY_NAME = SCHEME + AUTHORITY + "/find_by_name";
	public static final Uri URI_BASIC_CLIENT_FIND_BY_NAME = Uri.parse(BASIC_CLIENT_FIND_BY_NAME);
	public static final String BASIC_CLIENT_FIND_BY_NAME_BASE = BASIC_CLIENT_FIND_BY_NAME + "/";

// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003

	static {
		MATCHER.addURI(AUTHORITY,"insert", BASIC_CLIENT_INSERT_NUMBER);
		MATCHER.addURI(AUTHORITY,"update", BASIC_CLIENT_UPDATE_NUMBER);
		MATCHER.addURI(AUTHORITY,"delete", BASIC_CLIENT_DELETE_NUMBER);
		MATCHER.addURI(AUTHORITY,"all", BASIC_CLIENT_ALL_NUMBER);
		MATCHER.addURI(AUTHORITY,"some", BASIC_CLIENT_SOME_NUMBER);
		MATCHER.addURI(AUTHORITY,"by_id", BASIC_CLIENT_BY_ID_NUMBER);
		MATCHER.addURI(AUTHORITY,"last_id", BASIC_CLIENT_LAST_ID_NUMBER);
// reserved-for:AndroidSqliteDatabase003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.1
		MATCHER.addURI(AUTHORITY,"find_by_cpf", BASIC_CLIENT_FIND_BY_CPF_NUMBER);
		MATCHER.addURI(AUTHORITY,"find_by_id", BASIC_CLIENT_FIND_BY_ID_NUMBER);
		MATCHER.addURI(AUTHORITY,"find_by_name", BASIC_CLIENT_FIND_BY_NAME_NUMBER);
// reserved-for:AndroidSqliteQuerySingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003.1
	}

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[] { 
		DbHelper.BASIC_CLIENT_ID,
		DbHelper.BASIC_CLIENT_SERVER_ID,
		DbHelper.BASIC_CLIENT_DIRTY,
		DbHelper.BASIC_CLIENT_LAST_UPDATE, 
		DbHelper.BASIC_CLIENT_NAME, 
		DbHelper.BASIC_CLIENT_BIRTH_DATE, 
		DbHelper.BASIC_CLIENT_BIRTH_CITY, 
		DbHelper.BASIC_CLIENT_BIRTH_STATE, 
		DbHelper.BASIC_CLIENT_MOTHERS_NAME, 
		DbHelper.BASIC_CLIENT_FATHERS_NAME, 
		DbHelper.BASIC_CLIENT_PROFESSION, 
		DbHelper.BASIC_CLIENT_ZIP_CODE, 
		DbHelper.BASIC_CLIENT_ADDRESS, 
		DbHelper.BASIC_CLIENT_NEIGHBORHOOD, 
		DbHelper.BASIC_CLIENT_CITY, 
		DbHelper.BASIC_CLIENT_STATE, 
		DbHelper.BASIC_CLIENT_COMPLEMENT, 
		DbHelper.BASIC_CLIENT_FK_COUNTRY
	};

	public BasicClientDataSource (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("BasicClientDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public Cursor listAll () {
		Cursor cursor = database.query(DbHelper.TABLE_BASIC_CLIENT,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById (long id) {
		Cursor cursor = database.query(DbHelper.TABLE_BASIC_CLIENT,
			selectableColumns,
			DbHelper.BASIC_CLIENT_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome (long page_count, long page_size) {
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
			" FROM " + DbHelper.TABLE_BASIC_CLIENT;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public Cursor getLastId () {
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_BASIC_CLIENT +";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;		
	}

// begin content-provider-interface

	@Override
	public boolean onCreate () {
		return false;
	}

	@Nullable
	@Override
	public String getType (@NonNull Uri uri) {

		switch (MATCHER.match(uri)){
			case BASIC_CLIENT_INSERT_NUMBER:
				return BASIC_CLIENT_INSERT_TYPE;
			case BASIC_CLIENT_UPDATE_NUMBER:
				return BASIC_CLIENT_UPDATE_TYPE;
			case BASIC_CLIENT_DELETE_NUMBER:
				return BASIC_CLIENT_DELETE_TYPE;
			case BASIC_CLIENT_ALL_NUMBER:
				return BASIC_CLIENT_ALL_TYPE;
			case BASIC_CLIENT_SOME_NUMBER:
				return BASIC_CLIENT_SOME_TYPE;
			case BASIC_CLIENT_BY_ID_NUMBER:
				return BASIC_CLIENT_BY_ID_TYPE;
			case BASIC_CLIENT_LAST_ID_NUMBER:
				return BASIC_CLIENT_LAST_ID_TYPE;
// reserved-for:AndroidSqliteDatabase003.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.2
			case BASIC_CLIENT_FIND_BY_CPF_NUMBER:
				return BASIC_CLIENT_FIND_BY_CPF_TYPE;
			case BASIC_CLIENT_FIND_BY_ID_NUMBER:
				return BASIC_CLIENT_FIND_BY_ID_TYPE;
			case BASIC_CLIENT_FIND_BY_NAME_NUMBER:
				return BASIC_CLIENT_FIND_BY_NAME_TYPE;
// reserved-for:AndroidSqliteQuerySingle002.2
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003.2
		}
		return null;
	}
// reserved-for:AndroidSqliteDatabase003.2
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase004
	@Nullable
	@Override
	public Uri insert (@NonNull Uri uri, @Nullable ContentValues values) {
		Cursor result = null;
		if (URI_BASIC_CLIENT_INSERT.equals(uri)) {
			result = database.insert(DbHelper.TABLE_BASIC_CLIENT, null, values);
		}
// reserved-for:AndroidSqliteDatabase004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle003
/* @Insert */
// reserved-for:AndroidSqliteQuerySingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase005
		return null;
	}
// reserved-for:AndroidSqliteDatabase005
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase006
	@Override
	public int update (@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_BASIC_CLIENT_UPDATE.equals(uri)) {
			result = database.update(DbHelper.TABLE_BASIC_CLIENT, values, DbHelper.BASIC_CLIENT_ID + " = " + selectionArgs[0], null);
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
	public int delete (@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_BASIC_CLIENT_DELETE.equals(uri)) {
			result = database.delete(DbHelper.TABLE_BASIC_CLIENT, DbHelper.BASIC_CLIENT_ID + " = " + selectionArgs[0], null);
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
	/* @SelectOneWhere */
	public Cursor find_by_cpf (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT brazilian.last_update,brazilian.cpf,brazilian.rg,basic_client.last_update,basic_client.name,basic_client.birth_date,basic_client.birth_city,basic_client.birth_state,basic_client.mothers_name,basic_client.fathers_name,basic_client.profession,basic_client.zip_code,basic_client.address,basic_client.neighborhood,basic_client.city,basic_client.state,basic_client.complement,country.last_update,country.name FROM brazilian INNER JOIN basic_client ON brazilian.fk_basic_client = basic_client.id INNER JOIN country ON basic_client.fk_country = country.id WHERE brazilian.cpf = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		return cursor;
	}
	/* @SelectOneWhere */
	public Cursor find_by_id (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT brazilian.last_update,brazilian.cpf,brazilian.rg,basic_client.last_update,basic_client.name,basic_client.birth_date,basic_client.birth_city,basic_client.birth_state,basic_client.mothers_name,basic_client.fathers_name,basic_client.profession,basic_client.zip_code,basic_client.address,basic_client.neighborhood,basic_client.city,basic_client.state,basic_client.complement,country.last_update,country.name FROM brazilian INNER JOIN basic_client ON brazilian.fk_basic_client = basic_client.id INNER JOIN country ON basic_client.fk_country = country.id WHERE id = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		return cursor;
	}
	/* @SelectOneWhere */
	public Cursor find_by_name (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT brazilian.last_update,brazilian.cpf,brazilian.rg,basic_client.last_update,basic_client.name,basic_client.birth_date,basic_client.birth_city,basic_client.birth_state,basic_client.mothers_name,basic_client.fathers_name,basic_client.profession,basic_client.zip_code,basic_client.address,basic_client.neighborhood,basic_client.city,basic_client.state,basic_client.complement,country.last_update,country.name FROM brazilian INNER JOIN basic_client ON brazilian.fk_basic_client = basic_client.id INNER JOIN country ON basic_client.fk_country = country.id WHERE basic_client.name = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		return cursor;
	}
// reserved-for:AndroidSqliteQuerySingle006
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase010
	// TODO: I NEED TO KNOW HOW TO MAKE VARIOUS QUERIES DEPENDING ON THE URI
	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Cursor result = null;
		if (URI_BASIC_CLIENT_ALL.equals(uri)) {
			result = listAll();
		}
		else if (URI_BASIC_CLIENT_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if (URI_BASIC_CLIENT_BY_ID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if (URI_BASIC_CLIENT_LAST_ID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:AndroidSqliteDatabase010
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle004
// reserved-for:AndroidSqliteSyncSingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle007
/* @ExistsWhere||@SelectValueWhere||@SelectOneWhere||@SelectListWhere */
	else if (URI_BASIC_CLIENT_FIND_BY_CPF.equals(uri)) {
		result = find_by_cpf(selectionArgs);
	}
	else if (URI_BASIC_CLIENT_FIND_BY_ID.equals(uri)) {
		result = find_by_id(selectionArgs);
	}
	else if (URI_BASIC_CLIENT_FIND_BY_NAME.equals(uri)) {
		result = find_by_name(selectionArgs);
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

