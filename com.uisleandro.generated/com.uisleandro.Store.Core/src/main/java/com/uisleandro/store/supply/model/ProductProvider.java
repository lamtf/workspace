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
//import com.uisleandro.store.supply.model.ProductDbHelper;

import com.uisleandro.store.DbHelper;
// reserved-for:AndroidSqliteDatabase001
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle001
// reserved-for:AndroidSqliteSyncSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001import com.uisleandro.store.supply.view.view.GetByQrcodeOut;
import com.uisleandro.store.supply.view.view.GetByIdOut;
import com.uisleandro.store.supply.view.view.ProductRecountingOut;
import com.uisleandro.store.supply.view.view.MissingProductsRelatoryOut;
import com.uisleandro.store.supply.view.view.GetByRepeatedProductCodeOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase002
public class ProductProvider extends ContentProvider {


	public static final String AUTHORITY = "com.uisleandro.product";
	public static final String SCHEME = "content://";

	public static final String PRODUCT_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final Uri URI_PRODUCT_INSERT = Uri.parse(PRODUCT_INSERT);
	public static final String PRODUCT_INSERT_BASE = PRODUCT_INSERT + "/";

	public static final String PRODUCT_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final Uri URI_PRODUCT_UPDATE = Uri.parse(PRODUCT_UPDATE);
	public static final String PRODUCT_UPDATE_BASE = PRODUCT_UPDATE + "/";

	public static final String PRODUCT_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final Uri URI_PRODUCT_DELETE = Uri.parse(PRODUCT_DELETE);
	public static final String PRODUCT_DELETE_BASE = PRODUCT_DELETE + "/";

	public static final String PRODUCT_ALL = SCHEME + AUTHORITY + "/all";
	public static final Uri URI_PRODUCT_ALL = Uri.parse(PRODUCT_ALL);
	public static final String PRODUCT_ALL_BASE = PRODUCT_ALL + "/";

	public static final String PRODUCT_SOME = SCHEME + AUTHORITY + "/some";
	public static final Uri URI_PRODUCT_SOME = Uri.parse(PRODUCT_SOME);
	public static final String PRODUCT_SOME_BASE = PRODUCT_SOME + "/";

	public static final String PRODUCT_BYID = SCHEME + AUTHORITY + "/byid";
	public static final Uri URI_PRODUCT_BYID = Uri.parse(PRODUCT_BYID);
	public static final String PRODUCT_BYID_BASE = PRODUCT_BYID + "/";

	public static final String PRODUCT_LASTID = SCHEME + AUTHORITY + "/lastid";
	public static final Uri URI_PRODUCT_LASTID = Uri.parse(PRODUCT_LASTID);
	public static final String PRODUCT_LASTID_BASE = PRODUCT_LASTID + "/";

// reserved-for:AndroidSqliteDatabase002
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle002
// reserved-for:AndroidSqliteSyncSingle003
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	public static final String PRODUCT_GET_BY_QRCODE = SCHEME + AUTHORITY + "/get_by_qrcode";
	public static final Uri URI_PRODUCT_GET_BY_QRCODE = Uri.parse(PRODUCT_GET_BY_QRCODE);
	public static final String PRODUCT_GET_BY_QRCODE_BASE = PRODUCT_GET_BY_QRCODE + "/";
	public static final String PRODUCT_GET_BY_ID = SCHEME + AUTHORITY + "/get_by_id";
	public static final Uri URI_PRODUCT_GET_BY_ID = Uri.parse(PRODUCT_GET_BY_ID);
	public static final String PRODUCT_GET_BY_ID_BASE = PRODUCT_GET_BY_ID + "/";
	public static final String PRODUCT_PRODUCT_RECOUNTING = SCHEME + AUTHORITY + "/product_recounting";
	public static final Uri URI_PRODUCT_PRODUCT_RECOUNTING = Uri.parse(PRODUCT_PRODUCT_RECOUNTING);
	public static final String PRODUCT_PRODUCT_RECOUNTING_BASE = PRODUCT_PRODUCT_RECOUNTING + "/";
	public static final String PRODUCT_MISSING_PRODUCTS_RELATORY = SCHEME + AUTHORITY + "/missing_products_relatory";
	public static final Uri URI_PRODUCT_MISSING_PRODUCTS_RELATORY = Uri.parse(PRODUCT_MISSING_PRODUCTS_RELATORY);
	public static final String PRODUCT_MISSING_PRODUCTS_RELATORY_BASE = PRODUCT_MISSING_PRODUCTS_RELATORY + "/";
	public static final String PRODUCT_GET_BY_REPEATED_PRODUCT_CODE = SCHEME + AUTHORITY + "/get_by_repeated_product_code";
	public static final Uri URI_PRODUCT_GET_BY_REPEATED_PRODUCT_CODE = Uri.parse(PRODUCT_GET_BY_REPEATED_PRODUCT_CODE);
	public static final String PRODUCT_GET_BY_REPEATED_PRODUCT_CODE_BASE = PRODUCT_GET_BY_REPEATED_PRODUCT_CODE + "/";
	public static final String PRODUCT_START_PRODUCT_RECOUNTING = SCHEME + AUTHORITY + "/start_product_recounting";
	public static final Uri URI_PRODUCT_START_PRODUCT_RECOUNTING = Uri.parse(PRODUCT_START_PRODUCT_RECOUNTING);
	public static final String PRODUCT_START_PRODUCT_RECOUNTING_BASE = PRODUCT_START_PRODUCT_RECOUNTING + "/";
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabase003
	private SQLiteDatabase database;
	private DbHelper db_helper;
	private static final String[] selectableColumns = new String[]{ 
		DbHelper.PRODUCT_ID,
		DbHelper.PRODUCT_SERVER_ID,
		DbHelper.PRODUCT_DIRTY,
		DbHelper.PRODUCT_LAST_UPDATE, 
		DbHelper.PRODUCT_FK_SYSTEM, 
		DbHelper.PRODUCT_BARCODE, 
		DbHelper.PRODUCT_DESCRIPTION, 
		DbHelper.PRODUCT_AMOUNT, 
		DbHelper.PRODUCT_FK_GENDER, 
		DbHelper.PRODUCT_PURCHASE_PRICE, 
		DbHelper.PRODUCT_SALE_PRICE, 
		DbHelper.PRODUCT_FK_CATEGORY, 
		DbHelper.PRODUCT_SIZE, 
		DbHelper.PRODUCT_FK_UNIT, 
		DbHelper.PRODUCT_FK_CURRENCY, 
		DbHelper.PRODUCT_EXPIRATION_DATE, 
		DbHelper.PRODUCT_FK_BRAND
	};

	public ProductDataSource(Context context){
		db_helper = DbHelper.getInstance(context);
		try{
			database = db_helper.getWritableDatabase();
		}catch(SQLException e){
			Log.wtf("ProductDataSource", "Exception: "+Log.getStackTraceString(e));
		}
	}

	public void open() throws SQLException{
		database = db_helper.getWritableDatabase();
	}

	public void close(){
		db_helper.close();
	}

	public Cursor listAll(){
		Cursor cursor = database.query(DbHelper.TABLE_PRODUCT,
			selectableColumns,null,null, null, null, null);
		return cursor;
	}

	public Cursor getById(long id){
		Cursor cursor = database.query(DbHelper.TABLE_PRODUCT,
			selectableColumns,
			DbHelper.PRODUCT_ID + " = " + id,
			null, null, null, null);
		return cursor;
	}

	public Cursor listSome(long page_count, long page_size){
		String query = "SELECT id, server_id, dirty, " +
			"last_update, " +
			"fk_system, " +
			"barcode, " +
			"description, " +
			"amount, " +
			"fk_gender, " +
			"purchase_price, " +
			"sale_price, " +
			"fk_category, " +
			"size, " +
			"fk_unit, " +
			"fk_currency, " +
			"expiration_date, " +
			"fk_brand" +
			" FROM " + DbHelper.TABLE_PRODUCT;
		if(page_size > 0){
			query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		query += ";";
		Cursor cursor = database.rawQuery(query, null);
		return cursor;
	}

	public Cursor getLastId(){
		String query = "SELECT MAX(id) FROM " + DbHelper.TABLE_PRODUCT +";";
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
		if (URI_PRODUCT_INSERT.equals(uri)) {
			result = database.insert(DbHelper.TABLE_PRODUCT, null, values);
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
	public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
		int result = 0;
		if (URI_PRODUCT_UPDATE.equals(uri)) {
			result = database.update(DbHelper.TABLE_PRODUCT, values, DbHelper.PRODUCT_ID + " = " + selectionArgs[0], null);
		}
// reserved-for:AndroidSqliteDatabase006
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle004
/* @UpdateWhere */
	else if (URI_PRODUCT_product_recounting.equals(uri)) {
			result = product_recounting(selectionArgs); // << missing arguments
	}
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
		if (URI_PRODUCT_DELETE.equals(uri)) {
			result = database.delete(DbHelper.TABLE_PRODUCT, DbHelper.PRODUCT_ID + " = " + selectionArgs[0], null);
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
	/* @SelectOneWhere */
	public Cursor get_by_qrcode(String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT product.last_update,product.barcode,product.description,product.amount,product.purchase_price,product.sale_price,product.size,product.expiration_date,brand.last_update,brand.company_name,brand.fantasy_name,unit.last_update,unit.name,category.last_update,category.name,gender.last_update,gender.name,system.last_update,system.name,system.enabled,currency.last_update,currency.abbreviature,currency.description FROM product INNER JOIN system ON product.fk_system = system.id INNER JOIN gender ON product.fk_gender = gender.id INNER JOIN category ON product.fk_category = category.id INNER JOIN unit ON product.fk_unit = unit.id INNER JOIN brand ON product.fk_brand = brand.id INNER JOIN currency ON system.fk_currency = currency.id WHERE product.barcode = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		return cursor;
	}
	/* @SelectOneWhere */
	public Cursor get_by_id(String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT product.last_update,product.barcode,product.description,product.amount,product.purchase_price,product.sale_price,product.size,product.expiration_date,brand.last_update,brand.company_name,brand.fantasy_name,unit.last_update,unit.name,category.last_update,category.name,gender.last_update,gender.name,system.last_update,system.name,system.enabled,currency.last_update,currency.abbreviature,currency.description FROM product INNER JOIN system ON product.fk_system = system.id INNER JOIN gender ON product.fk_gender = gender.id INNER JOIN category ON product.fk_category = category.id INNER JOIN unit ON product.fk_unit = unit.id INNER JOIN brand ON product.fk_brand = brand.id INNER JOIN currency ON system.fk_currency = currency.id WHERE product.id = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		return cursor;
	}
	/* @UpdateWhere */
	public int product_recounting(String[] selectionArgs) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "UPDATE stock_review SET (stock_review.actual_amount = ?,stock_review.sold_items = ?,stock_review.previous_amount = ?,stock_review.missing_amount = ?,stock_review.barcode = ?) WHERE stock_review.last_update = ?,stock_review.fk_product = ?,stock_review.actual_amount = ?;";
		Cursor cursor = database.rawQuery(query, selectionArgs);
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	}
	/* @SelectListWhere */
	public Cursor missing_products_relatory(String[] selectionArgs,long page_count, long page_size) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT stock_review.last_update,stock_review.actual_amount,stock_review.sold_items,stock_review.previous_amount,stock_review.missing_amount,product.last_update,product.barcode,product.description,product.amount,product.purchase_price,product.sale_price,product.size,product.expiration_date,brand.last_update,brand.company_name,brand.fantasy_name,unit.last_update,unit.name,category.last_update,category.name,gender.last_update,gender.name,system.last_update,system.name,system.enabled,currency.last_update,currency.abbreviature,currency.description FROM stock_review INNER JOIN product ON stock_review.fk_product = product.id INNER JOIN system ON product.fk_system = system.id INNER JOIN gender ON product.fk_gender = gender.id INNER JOIN category ON product.fk_category = category.id INNER JOIN unit ON product.fk_unit = unit.id INNER JOIN brand ON product.fk_brand = brand.id INNER JOIN currency ON system.fk_currency = currency.id WHERE stock_review.last_update = ?;";
		if(page_size > 0){
				query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
		Cursor cursor = database.rawQuery(query, selectionArgs);
		return cursor;
	}
	/* @SelectListWhere */
	public Cursor get_by_repeated_product_code(String[] selectionArgs,long page_count, long page_size) {
		//TODO: I might have some data from 'selectionArgs' and also some predefined data
		//TODO: the way it is the transformation is wrong
		String query = "SELECT product.last_update,product.barcode,product.description,product.amount,product.purchase_price,product.sale_price,product.size,product.expiration_date,brand.last_update,brand.company_name,brand.fantasy_name,unit.last_update,unit.name,category.last_update,category.name,gender.last_update,gender.name,system.last_update,system.name,system.enabled,currency.last_update,currency.abbreviature,currency.description FROM product INNER JOIN system ON product.fk_system = system.id INNER JOIN gender ON product.fk_gender = gender.id INNER JOIN category ON product.fk_category = category.id INNER JOIN unit ON product.fk_unit = unit.id INNER JOIN brand ON product.fk_brand = brand.id INNER JOIN currency ON system.fk_currency = currency.id WHERE product.fk_systen = ? AND product.barcode = ?;";
		if(page_size > 0){
				query += " LIMIT " + String.valueOf(page_size) + " OFFSET " + String.valueOf(page_size * page_count);
		}
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
		if (URI_PRODUCT_ALL.equals(uri)) {
			result = listAll();
		}
		else if(URI_PRODUCT_SOME.equals(uri)) {
			result = listSome(Long.parseLong(selectionArgs[0]), Long.parseLong(selectionArgs[1]));
		}
		else if(URI_PRODUCT_BYID.equals(uri)) {
			result = getById(Long.parseLong(selectionArgs[0]));
		}
		else if(URI_PRODUCT_LASTID.equals(uri)) {
			result = getLastId();
		}
// reserved-for:AndroidSqliteDatabase010
// End of user code

// Start of user code reserved-for:AndroidSqliteSyncSingle004
// reserved-for:AndroidSqliteSyncSingle004
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle007
/* @ExistsWhere||@SelectValueWhere||@SelectOneWhere||@SelectListWhere */
	else if (URI_PRODUCT_GET_BY_QRCODE.equals(uri)) {
		result = get_by_qrcode(selectionArgs);
	}
	else if (URI_PRODUCT_GET_BY_ID.equals(uri)) {
		result = get_by_id(selectionArgs);
	}
	else if (URI_PRODUCT_MISSING_PRODUCTS_RELATORY.equals(uri)) {
		result = missing_products_relatory(selectionArgs);
	}
	else if (URI_PRODUCT_GET_BY_REPEATED_PRODUCT_CODE.equals(uri)) {
		result = get_by_repeated_product_code(selectionArgs);
	}
// Start of user code reserved-for:AndroidSqliteQuerySingle007

// Start of user code reserved-for:AndroidSqliteDatabase011
		return result;
	}
}
// reserved-for:AndroidSqliteDatabase011
// End of user code