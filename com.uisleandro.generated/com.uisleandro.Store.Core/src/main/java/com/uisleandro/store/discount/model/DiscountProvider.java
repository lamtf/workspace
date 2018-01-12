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
//import com.uisleandro.store.discount.model.DiscountDbHelper;

import com.uisleandro.store.DbHelper;
// reserved-for:AndroidSqliteDatabase001
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle001
// reserved-for:AndroidSqliteSyncSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase002
public class DiscountProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.discount";
	public static final String SCHEME = "content://";

	public static final String DISCOUNT_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_DISCOUNT_INSERT = Uri.parse(DISCOUNT_INSERT);
	public static final String DISCOUNT_INSERT_BASE = DISCOUNT_INSERT + "/";

	public static final String DISCOUNT_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_DISCOUNT_UPDATE = Uri.parse(DISCOUNT_UPDATE);
	public static final String DISCOUNT_UPDATE_BASE = DISCOUNT_UPDATE + "/";

	public static final String DISCOUNT_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_DISCOUNT_DELETE = Uri.parse(DISCOUNT_DELETE);
	public static final String DISCOUNT_DELETE_BASE = DISCOUNT_DELETE + "/";

	public static final String DISCOUNT_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_DISCOUNT_ALL = Uri.parse(DISCOUNT_ALL);
	public static final String DISCOUNT_ALL_BASE = DISCOUNT_ALL + "/";

	public static final String DISCOUNT_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_DISCOUNT_SOME = Uri.parse(DISCOUNT_SOME);
	public static final String DISCOUNT_SOME_BASE = DISCOUNT_SOME + "/";

	public static final String DISCOUNT_BYID = SCHEME + AUTHORITY + "/byid";
	public static final Uri URI_DISCOUNT_BYID = Uri.parse(DISCOUNT_BYID);
	public static final String DISCOUNT_BYID_BASE = DISCOUNT_BYID + "/";

	public static final String DISCOUNT_LASTID = SCHEME + AUTHORITY + "/lastid";
	public static final Uri URI_DISCOUNT_LASTID = Uri.parse(DISCOUNT_LASTID);
	public static final String DISCOUNT_LASTID_BASE = DISCOUNT_LASTID + "/";

// reserved-for:AndroidSqliteDatabase002
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle002
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	public static final String DISCOUNT_NEW_EVENTUAL_DISCOUNT = SCHEME + AUTHORITY + "/new_eventual_discount";
	public static final Uri URI_DISCOUNT_NEW_EVENTUAL_DISCOUNT = Uri.parse(DISCOUNT_NEW_EVENTUAL_DISCOUNT);
	public static final String DISCOUNT_NEW_EVENTUAL_DISCOUNT_BASE = DISCOUNT_NEW_EVENTUAL_DISCOUNT + "/";
	public static final String DISCOUNT_NEW_PRODUCT_PROMOTION = SCHEME + AUTHORITY + "/new_product_promotion";
	public static final Uri URI_DISCOUNT_NEW_PRODUCT_PROMOTION = Uri.parse(DISCOUNT_NEW_PRODUCT_PROMOTION);
	public static final String DISCOUNT_NEW_PRODUCT_PROMOTION_BASE = DISCOUNT_NEW_PRODUCT_PROMOTION + "/";
	public static final String DISCOUNT_NEW_CATEGORY_PROMOTION = SCHEME + AUTHORITY + "/new_category_promotion";
	public static final Uri URI_DISCOUNT_NEW_CATEGORY_PROMOTION = Uri.parse(DISCOUNT_NEW_CATEGORY_PROMOTION);
	public static final String DISCOUNT_NEW_CATEGORY_PROMOTION_BASE = DISCOUNT_NEW_CATEGORY_PROMOTION + "/";
	public static final String DISCOUNT_NEW_GENDER_PROMOTION = SCHEME + AUTHORITY + "/new_gender_promotion";
	public static final Uri URI_DISCOUNT_NEW_GENDER_PROMOTION = Uri.parse(DISCOUNT_NEW_GENDER_PROMOTION);
	public static final String DISCOUNT_NEW_GENDER_PROMOTION_BASE = DISCOUNT_NEW_GENDER_PROMOTION + "/";
	public static final String DISCOUNT_NEW_BRAND_BASED_PROMOTION = SCHEME + AUTHORITY + "/new_brand_based_promotion";
	public static final Uri URI_DISCOUNT_NEW_BRAND_BASED_PROMOTION = Uri.parse(DISCOUNT_NEW_BRAND_BASED_PROMOTION);
	public static final String DISCOUNT_NEW_BRAND_BASED_PROMOTION_BASE = DISCOUNT_NEW_BRAND_BASED_PROMOTION + "/";
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003
	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[] { 
		DbHelper.DISCOUNT_ID,
		DbHelper.DISCOUNT_SERVER_ID,
		DbHelper.DISCOUNT_DIRTY,
		DbHelper.DISCOUNT_LAST_UPDATE, 
		DbHelper.DISCOUNT_VALUE, 
		DbHelper.DISCOUNT_PERCENTAGE, 
		DbHelper.DISCOUNT_FK_PRODUCT, 
		DbHelper.DISCOUNT_FK_CATEGORY, 
		DbHelper.DISCOUNT_FK_BRAND, 
		DbHelper.DISCOUNT_FK_CLIENT_FROM_SYSTEM, 
		DbHelper.DISCOUNT_FK_GENDER
	};

	public DiscountDataSource (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("DiscountDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public Cursor listAll () {
		Cursor cursor = database.query(DbHelper.TABLE_DISCOUNT,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById (long id) {
		Cursor cursor = database.query(DbHelper.TABLE_DISCOUNT,
			selectableColumns,
			DbHelper.DISCOUNT_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome (long page_count, long page_size) {
		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"value, " +
			"percentage, " +
			"fk_product, " +
			"fk_category, " +
			"fk_brand, " +
			"fk_client_from_system, " +
			"fk_gender" +
			" FROM " + DbHelper.TABLE_DISCOUNT;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public Cursor getLastId () {
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_DISCOUNT +";";
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
		if (URI_DISCOUNT_INSERT.equals(uri)) {
			result = database.insert(DbHelper.TABLE_DISCOUNT, null, values);
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
		if (URI_DISCOUNT_UPDATE.equals(uri)) {
			result = database.update(DbHelper.TABLE_DISCOUNT, values, DbHelper.DISCOUNT_ID + " = " + selectionArgs[0], null);
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
		if (URI_DISCOUNT_DELETE.equals(uri)) {
			result = database.delete(DbHelper.TABLE_DISCOUNT, DbHelper.DISCOUNT_ID + " = " + selectionArgs[0], null);
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
		if (URI_DISCOUNT_ALL.equals(uri)) {
			result = listAll();
		}
		else if (URI_DISCOUNT_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if (URI_DISCOUNT_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if (URI_DISCOUNT_LASTID.equals(uri)) {
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
