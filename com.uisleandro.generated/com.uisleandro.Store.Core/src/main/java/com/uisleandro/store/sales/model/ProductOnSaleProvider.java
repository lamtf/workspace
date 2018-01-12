// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.Core.model;  

import java.util.ArrayList;
import java.util.List;
import android.content.ContentProvider;
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
//import com.uisleandro.store.sales.model.ProductOnSaleDbHelper;

import com.uisleandro.store.DbHelper;
// reserved-for:AndroidSqliteDatabase001
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle001
// reserved-for:AndroidSqliteSyncSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase002
public class ProductOnSaleProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.product_on_sale";
	public static final String SCHEME = "content://";

	public static final String PRODUCT_ON_SALE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_PRODUCT_ON_SALE_INSERT = Uri.parse(PRODUCT_ON_SALE_INSERT);
	public static final String PRODUCT_ON_SALE_INSERT_BASE = PRODUCT_ON_SALE_INSERT + "/";

	public static final String PRODUCT_ON_SALE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_PRODUCT_ON_SALE_UPDATE = Uri.parse(PRODUCT_ON_SALE_UPDATE);
	public static final String PRODUCT_ON_SALE_UPDATE_BASE = PRODUCT_ON_SALE_UPDATE + "/";

	public static final String PRODUCT_ON_SALE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_PRODUCT_ON_SALE_DELETE = Uri.parse(PRODUCT_ON_SALE_DELETE);
	public static final String PRODUCT_ON_SALE_DELETE_BASE = PRODUCT_ON_SALE_DELETE + "/";

	public static final String PRODUCT_ON_SALE_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_PRODUCT_ON_SALE_ALL = Uri.parse(PRODUCT_ON_SALE_ALL);
	public static final String PRODUCT_ON_SALE_ALL_BASE = PRODUCT_ON_SALE_ALL + "/";

	public static final String PRODUCT_ON_SALE_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_PRODUCT_ON_SALE_SOME = Uri.parse(PRODUCT_ON_SALE_SOME);
	public static final String PRODUCT_ON_SALE_SOME_BASE = PRODUCT_ON_SALE_SOME + "/";

	public static final String PRODUCT_ON_SALE_BYID = SCHEME + AUTHORITY + "/byid";
	public static final Uri URI_PRODUCT_ON_SALE_BYID = Uri.parse(PRODUCT_ON_SALE_BYID);
	public static final String PRODUCT_ON_SALE_BYID_BASE = PRODUCT_ON_SALE_BYID + "/";

	public static final String PRODUCT_ON_SALE_LASTID = SCHEME + AUTHORITY + "/lastid";
	public static final Uri URI_PRODUCT_ON_SALE_LASTID = Uri.parse(PRODUCT_ON_SALE_LASTID);
	public static final String PRODUCT_ON_SALE_LASTID_BASE = PRODUCT_ON_SALE_LASTID + "/";

// reserved-for:AndroidSqliteDatabase002
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle002
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003
	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[] { 
		DbHelper.PRODUCT_ON_SALE_ID,
		DbHelper.PRODUCT_ON_SALE_SERVER_ID,
		DbHelper.PRODUCT_ON_SALE_DIRTY,
		DbHelper.PRODUCT_ON_SALE_LAST_UPDATE, 
		DbHelper.PRODUCT_ON_SALE_FK_SALE, 
		DbHelper.PRODUCT_ON_SALE_FK_PRODUCT
	};

	public ProductOnSaleDataSource (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("ProductOnSaleDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public Cursor listAll () {
		Cursor cursor = database.query(DbHelper.TABLE_PRODUCT_ON_SALE,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById (long id) {
		Cursor cursor = database.query(DbHelper.TABLE_PRODUCT_ON_SALE,
			selectableColumns,
			DbHelper.PRODUCT_ON_SALE_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome (long page_count, long page_size) {
		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_sale, " +
			"fk_product" +
			" FROM " + DbHelper.TABLE_PRODUCT_ON_SALE;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public Cursor getLastId () {
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_PRODUCT_ON_SALE +";";
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
		return null;
	}
// reserved-for:AndroidSqliteDatabase003
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase004
	@Nullable
	@Override
	public Uri insert (@NonNull Uri uri, @Nullable ContentValues values) {
		Cursor result = null;
		if (URI_PRODUCT_ON_SALE_INSERT.equals(uri)) {
			result = database.insert(DbHelper.TABLE_PRODUCT_ON_SALE, null, values);
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
		if (URI_PRODUCT_ON_SALE_UPDATE.equals(uri)) {
			result = database.update(DbHelper.TABLE_PRODUCT_ON_SALE, values, DbHelper.PRODUCT_ON_SALE_ID + " = " + selectionArgs[0], null);
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
		if (URI_PRODUCT_ON_SALE_DELETE.equals(uri)) {
			result = database.delete(DbHelper.TABLE_PRODUCT_ON_SALE, DbHelper.PRODUCT_ON_SALE_ID + " = " + selectionArgs[0], null);
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

// Start of user code reserved-for:AndroidSqliteSyncSingle003
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle006
// reserved-for:AndroidSqliteQuerySingle006
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase010
	// TODO: I NEED TO KNOW HOW TO MAKE VARIOUS QUERIES DEPENDING ON THE URI
	@Nullable
	@Override
	public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {
		Cursor result = null;
		if (URI_PRODUCT_ON_SALE_ALL.equals(uri)) {
			result = listAll();
		}
		else if (URI_PRODUCT_ON_SALE_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if (URI_PRODUCT_ON_SALE_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if (URI_PRODUCT_ON_SALE_LASTID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:AndroidSqliteDatabase010
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle004
// reserved-for:AndroidSqliteSyncSingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle007
/* @ExistsWhere||@SelectValueWhere||@SelectOneWhere||@SelectListWhere */
// Start of user code reserved-for:AndroidSqliteQuerySingle007

// Start of user code reserved-for:AndroidSqliteDatabase011
// reserved-for:AndroidSqliteDatabase011
// End of user code
