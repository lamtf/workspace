// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.sales.model;  

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
//import com.uisleandro.store.sales.model.SaleDbHelper;

import com.uisleandro.store.DbHelper;
// reserved-for:AndroidSqliteDatabase001
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle001
// reserved-for:AndroidSqliteSyncSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001import com.uisleandro.store.sales.view.ListProductsOnSalesChartOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase002
public class SaleProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.sale";
	public static final String SCHEME = "content://";
	private static final UriMatcher MATCHER = new UriMatcher(UriMatcher.NO_MATCH);
	
	public static final int SALE_INSERT_NUMBER = 1;
	public static final String SALE_INSERT_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/insert";
	public static final String SALE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_SALE_INSERT = Uri.parse(SALE_INSERT);
	public static final String SALE_INSERT_BASE = SALE_INSERT + "/";

	public static final int SALE_UPDATE_NUMBER = 2;
	public static final String SALE_UPDATE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/update";
	public static final String SALE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_SALE_UPDATE = Uri.parse(SALE_UPDATE);
	public static final String SALE_UPDATE_BASE = SALE_UPDATE + "/";

	public static final int SALE_DELETE_NUMBER = 3;
	public static final String SALE_DELETE_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/delete";
	public static final String SALE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_SALE_DELETE = Uri.parse(SALE_DELETE);
	public static final String SALE_DELETE_BASE = SALE_DELETE + "/";

	public static final int SALE_ALL_NUMBER = 4;
	public static final String SALE_ALL_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/all";
	public static final String SALE_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_SALE_ALL = Uri.parse(SALE_ALL);
	public static final String SALE_ALL_BASE = SALE_ALL + "/";

	public static final int SALE_SOME_NUMBER = 5;
	public static final String SALE_SOME_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/some";
	public static final String SALE_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_SALE_SOME = Uri.parse(SALE_SOME);
	public static final String SALE_SOME_BASE = SALE_SOME + "/";

	public static final int SALE_BY_ID_NUMBER = 6;
	public static final String SALE_BY_ID_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/by_id";
	public static final String SALE_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final Uri URI_SALE_BY_ID = Uri.parse(SALE_BY_ID);
	public static final String SALE_BY_ID_BASE = SALE_BY_ID + "/";

	public static final int SALE_LAST_ID_NUMBER = 7;
	public static final String SALE_LAST_ID_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/last_id";
	public static final String SALE_LAST_ID = SCHEME + AUTHORITY + "/last_id";
	public static final Uri URI_SALE_LAST_ID = Uri.parse(SALE_LAST_ID);
	public static final String SALE_LAST_ID_BASE = SALE_LAST_ID + "/";


// reserved-for:AndroidSqliteDatabase002
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle002
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	public static final int SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART_NUMBER = 8;
	public static final String SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/remove_all_products_from_sales_chart";
	public static final String SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART = SCHEME + AUTHORITY + "/remove_all_products_from_sales_chart";
	public static final Uri URI_SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART = Uri.parse(SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART);
	public static final String SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART_BASE = SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART + "/";

	public static final int SALE_CREATE_SALES_CHART_NUMBER = 9;
	public static final String SALE_CREATE_SALES_CHART_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/create_sales_chart";
	public static final String SALE_CREATE_SALES_CHART = SCHEME + AUTHORITY + "/create_sales_chart";
	public static final Uri URI_SALE_CREATE_SALES_CHART = Uri.parse(SALE_CREATE_SALES_CHART);
	public static final String SALE_CREATE_SALES_CHART_BASE = SALE_CREATE_SALES_CHART + "/";

	public static final int SALE_LIST_PRODUCTS_ON_SALES_CHART_NUMBER = 10;
	public static final String SALE_LIST_PRODUCTS_ON_SALES_CHART_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/list_products_on_sales_chart";
	public static final String SALE_LIST_PRODUCTS_ON_SALES_CHART = SCHEME + AUTHORITY + "/list_products_on_sales_chart";
	public static final Uri URI_SALE_LIST_PRODUCTS_ON_SALES_CHART = Uri.parse(SALE_LIST_PRODUCTS_ON_SALES_CHART);
	public static final String SALE_LIST_PRODUCTS_ON_SALES_CHART_BASE = SALE_LIST_PRODUCTS_ON_SALES_CHART + "/";

	public static final int SALE_ADD_PRODUCT_TO_SALES_CHART_NUMBER = 11;
	public static final String SALE_ADD_PRODUCT_TO_SALES_CHART_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/add_product_to_sales_chart";
	public static final String SALE_ADD_PRODUCT_TO_SALES_CHART = SCHEME + AUTHORITY + "/add_product_to_sales_chart";
	public static final Uri URI_SALE_ADD_PRODUCT_TO_SALES_CHART = Uri.parse(SALE_ADD_PRODUCT_TO_SALES_CHART);
	public static final String SALE_ADD_PRODUCT_TO_SALES_CHART_BASE = SALE_ADD_PRODUCT_TO_SALES_CHART + "/";

	public static final int SALE_CANCEL_SALES_CHART_NUMBER = 12;
	public static final String SALE_CANCEL_SALES_CHART_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/cancel_sales_chart";
	public static final String SALE_CANCEL_SALES_CHART = SCHEME + AUTHORITY + "/cancel_sales_chart";
	public static final Uri URI_SALE_CANCEL_SALES_CHART = Uri.parse(SALE_CANCEL_SALES_CHART);
	public static final String SALE_CANCEL_SALES_CHART_BASE = SALE_CANCEL_SALES_CHART + "/";

	public static final int SALE_REMOVE_PRODUCT_FROM_SALES_CHART_NUMBER = 13;
	public static final String SALE_REMOVE_PRODUCT_FROM_SALES_CHART_TYPE = ContentResolver.CURSOR_DIR_BASE_TYPE+"/remove_product_from_sales_chart";
	public static final String SALE_REMOVE_PRODUCT_FROM_SALES_CHART = SCHEME + AUTHORITY + "/remove_product_from_sales_chart";
	public static final Uri URI_SALE_REMOVE_PRODUCT_FROM_SALES_CHART = Uri.parse(SALE_REMOVE_PRODUCT_FROM_SALES_CHART);
	public static final String SALE_REMOVE_PRODUCT_FROM_SALES_CHART_BASE = SALE_REMOVE_PRODUCT_FROM_SALES_CHART + "/";

// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003

	static {
		MATCHER.addURI(AUTHORITY,"insert", SALE_INSERT_NUMBER);
		MATCHER.addURI(AUTHORITY,"update", SALE_UPDATE_NUMBER);
		MATCHER.addURI(AUTHORITY,"delete", SALE_DELETE_NUMBER);
		MATCHER.addURI(AUTHORITY,"all", SALE_ALL_NUMBER);
		MATCHER.addURI(AUTHORITY,"some", SALE_SOME_NUMBER);
		MATCHER.addURI(AUTHORITY,"by_id", SALE_BY_ID_NUMBER);
		MATCHER.addURI(AUTHORITY,"last_id", SALE_LAST_ID_NUMBER);
// reserved-for:AndroidSqliteDatabase003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.1
		MATCHER.addURI(AUTHORITY,"remove_all_products_from_sales_chart", SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART_NUMBER);
		MATCHER.addURI(AUTHORITY,"create_sales_chart", SALE_CREATE_SALES_CHART_NUMBER);
		MATCHER.addURI(AUTHORITY,"list_products_on_sales_chart", SALE_LIST_PRODUCTS_ON_SALES_CHART_NUMBER);
		MATCHER.addURI(AUTHORITY,"add_product_to_sales_chart", SALE_ADD_PRODUCT_TO_SALES_CHART_NUMBER);
		MATCHER.addURI(AUTHORITY,"cancel_sales_chart", SALE_CANCEL_SALES_CHART_NUMBER);
		MATCHER.addURI(AUTHORITY,"remove_product_from_sales_chart", SALE_REMOVE_PRODUCT_FROM_SALES_CHART_NUMBER);
// reserved-for:AndroidSqliteQuerySingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003.1
	}

	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[] { 
		DbHelper.SALE_ID,
		DbHelper.SALE_SERVER_ID,
		DbHelper.SALE_DIRTY,
		DbHelper.SALE_LAST_UPDATE, 
		DbHelper.SALE_FK_SALE_TYPE, 
		DbHelper.SALE_FK_SYSTEM, 
		DbHelper.SALE_TOTAL_VALUE, 
		DbHelper.SALE_FK_USER, 
		DbHelper.SALE_FK_CLIENT_FROM_SYSTEM
	};

	public SaleProvider (Context context) {
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("SaleDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open () throws SQLException {
		database = db_helper.getWritableDatabase();
	}

	public void close () {
		db_helper.close();
	}

	public Cursor listAll () {
		Cursor cursor = database.query(DbHelper.TABLE_SALE,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById (long id) {
		Cursor cursor = database.query(DbHelper.TABLE_SALE,
			selectableColumns,
			DbHelper.SALE_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome (long page_count, long page_size) {
		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_sale_type, " +
			"fk_system, " +
			"total_value, " +
			"fk_user, " +
			"fk_client_from_system" +
			" FROM " + DbHelper.TABLE_SALE;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public Cursor getLastId () {
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_SALE +";";
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
			case SALE_INSERT_NUMBER:
				return SALE_INSERT_TYPE;
			case SALE_UPDATE_NUMBER:
				return SALE_UPDATE_TYPE;
			case SALE_DELETE_NUMBER:
				return SALE_DELETE_TYPE;
			case SALE_ALL_NUMBER:
				return SALE_ALL_TYPE;
			case SALE_SOME_NUMBER:
				return SALE_SOME_TYPE;
			case SALE_BY_ID_NUMBER:
				return SALE_BY_ID_TYPE;
			case SALE_LAST_ID_NUMBER:
				return SALE_LAST_ID_TYPE;
// reserved-for:AndroidSqliteDatabase003.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.2
			case SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART_NUMBER:
				return SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART_TYPE;
			case SALE_CREATE_SALES_CHART_NUMBER:
				return SALE_CREATE_SALES_CHART_TYPE;
			case SALE_LIST_PRODUCTS_ON_SALES_CHART_NUMBER:
				return SALE_LIST_PRODUCTS_ON_SALES_CHART_TYPE;
			case SALE_ADD_PRODUCT_TO_SALES_CHART_NUMBER:
				return SALE_ADD_PRODUCT_TO_SALES_CHART_TYPE;
			case SALE_CANCEL_SALES_CHART_NUMBER:
				return SALE_CANCEL_SALES_CHART_TYPE;
			case SALE_REMOVE_PRODUCT_FROM_SALES_CHART_NUMBER:
				return SALE_REMOVE_PRODUCT_FROM_SALES_CHART_TYPE;
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
		if (URI_SALE_INSERT.equals(uri)) {
			result = database.insert(DbHelper.TABLE_SALE, null, values);
		}
// reserved-for:AndroidSqliteDatabase004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle003
/* @Insert */
	else if (URI_SALE_CREATE_SALES_CHART.equals(uri)) {
			result = create_sales_chart(selectionArgs); // << missing arguments
	}
	else if (URI_SALE_ADD_PRODUCT_TO_SALES_CHART.equals(uri)) {
			result = add_product_to_sales_chart(selectionArgs); // << missing arguments
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
	public int update (@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_SALE_UPDATE.equals(uri)) {
			result = database.update(DbHelper.TABLE_SALE, values, DbHelper.SALE_ID + " = " + selectionArgs[0], null);
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
		if (URI_SALE_DELETE.equals(uri)) {
			result = database.delete(DbHelper.TABLE_SALE, DbHelper.SALE_ID + " = " + selectionArgs[0], null);
		}
// reserved-for:AndroidSqliteDatabase008
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle005
/* @DeleteWhere */
	else if (URI_SALE_remove_all_products_from_sales_chart.equals(uri)) {
			result = remove_all_products_from_sales_chart(selectionArgs);
	}
	else if (URI_SALE_cancel_sales_chart.equals(uri)) {
			result = cancel_sales_chart(selectionArgs);
	}
	else if (URI_SALE_remove_product_from_sales_chart.equals(uri)) {
			result = remove_product_from_sales_chart(selectionArgs);
	}
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
	/* @DeleteWhere */
	public int remove_all_products_from_sales_chart (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		int result = 0;
		String query = "DELETE FROM product_on_sale WHERE product_on_sale.fk_sale = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	}
	/* @Insert */
	public int create_sales_chart (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "INSERT INTO sale(last_update,fk_sale_type,total_value,fk_system,fk_user,fk_client_from_system) VALUES (?,NULL,?,?,?,NULL);";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		//TODO: I don't knwo if its returning the last_id, I guess it's not
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	}
	/* @SelectListWhere */
	public Cursor list_products_on_sales_chart (String[] selectionArgs,long page_count, long page_size) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT product_on_sale.last_update,product.barcode,product.description,product.sale_price,product.size,product.expiration_date,brand.fantasy_name,currency.abbreviature,unit.name,category.name,category.fk_category,gender.name FROM product_on_sale INNER JOIN product ON product_on_sale.fk_product = product.id INNER JOIN gender ON product.fk_gender = gender.id INNER JOIN category ON product.fk_category = category.id INNER JOIN unit ON product.fk_unit = unit.id INNER JOIN currency ON product.fk_currency = currency.id INNER JOIN brand ON product.fk_brand = brand.id WHERE product_on_sale.fk_sale = ?;";
		if(page_size > 0){
				query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		Cursor cursor = database.rawQuery(query, selectionArgs);
		return cursor;
	}
	/* @Insert */
	public int add_product_to_sales_chart (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "INSERT INTO product_on_sale(last_update,fk_sale,fk_product) VALUES (?,?,?);";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		//TODO: I don't knwo if its returning the last_id, I guess it's not
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	}
	/* @DeleteWhere */
	public int cancel_sales_chart (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		int result = 0;
		String query = "DELETE FROM sale WHERE sale.id = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	}
	/* @DeleteWhere */
	public int remove_product_from_sales_chart (String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		int result = 0;
		String query = "DELETE FROM product_on_sale WHERE product_on_sale.fk_sale = ?,product_on_sale.fk_product = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
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
		if (URI_SALE_ALL.equals(uri)) {
			result = listAll();
		}
		else if (URI_SALE_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if (URI_SALE_BY_ID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if (URI_SALE_LAST_ID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:AndroidSqliteDatabase010
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle004
// reserved-for:AndroidSqliteSyncSingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle007
/* @ExistsWhere||@SelectValueWhere||@SelectOneWhere||@SelectListWhere */
	else if (URI_SALE_LIST_PRODUCTS_ON_SALES_CHART.equals(uri)) {
		result = list_products_on_sales_chart(selectionArgs);
	}
// Start of user code reserved-for:AndroidSqliteQuerySingle007

// Start of user code reserved-for:AndroidSqliteDatabase011
		return result;
	}
}
// reserved-for:AndroidSqliteDatabase011
// End of user code
