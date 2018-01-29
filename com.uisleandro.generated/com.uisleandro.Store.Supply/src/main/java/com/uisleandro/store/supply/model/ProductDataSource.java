// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.supply.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.supply.view.ProductDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
import com.uisleandro.store.supply.view.GetByRepeatedProductCodeOut;
import com.uisleandro.store.supply.view.MissingProductsRelatoryOut;
import com.uisleandro.store.supply.view.GetByIdOut;
import com.uisleandro.store.supply.view.GetByQrcodeOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class ProductDataSource {

	public static final String AUTHORITY = "com.uisleandro.product";
	public static final String SCHEME = "content://";

	public static final String PRODUCT_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String PRODUCT_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String PRODUCT_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String PRODUCT_ALL = SCHEME + AUTHORITY + "/all";
	public static final String PRODUCT_SOME = SCHEME + AUTHORITY + "/some";
	public static final String PRODUCT_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String PRODUCT_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public ProductDataSource (Context context) {
		this.context = context;
	}

	public List<ProductView> listAll () {
		List<ProductView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(PRODUCT_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      ProductView that = ProductView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public ProductView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(PRODUCT_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = ProductView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<ProductView listSome (long page_count, long page_size) {
		List<ProductView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(PRODUCT_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      ProductView that = ProductView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(PRODUCT_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (ProductView that) {
		context.getContentResolver().insert(PRODUCT_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (ProductView that) {
		return context.getContentResolver().update(PRODUCT_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (ProductView that) {
		return context.getContentResolver().delete(PRODUCT_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(PRODUCT_DELETE, null, new String[]{ String.valueOf(id) });
	}
// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @SelectListWhere */
	public List<GetByRepeatedProductCodeOut> get_by_repeated_product_code (Long fk_systen, String barcode, long page_count, long page_size){
		String selectionArgs = new String[]{ String.valueOf(fk_systen), barcode }; 
		Cursor cursor = context.getContentResolver().query("content://com.uisleandro.product/get_by_repeated_product_code",null, null, selectionArgs, null);
		List<GetByRepeatedProductCodeOut> those = null;
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			those.add( GetByRepeatedProductCodeOut.FromCursor(cursor) );
			cursor.moveToNext();
		}
		return those;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @UpdateWhere */
	public int product_recounting(Long fk_product){
		int result = 0;
	
		ContentValues contentValues = new ContentValues(5);
		contentValues.put("actual_amount",actual_amount);
		contentValues.put("sold_items",sold_items);
		contentValues.put("previous_amount",previous_amount);
		contentValues.put("missing_amount",missing_amount);
		contentValues.put("barcode",barcode);
	
		String[] selectionArgs = new String[]{ com.uisleandro.util.config.getRightNowString(), String.valueOf(fk_product), "actual_amount + 1" }
	
		Cursor cursor = context.getContentResolver().update("content://com.uisleandro.product/product_recounting", contentValues, null, selectionArgs); 
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	// TODO: PLEASE DON'T USE SYNCHRONIZED CODE
	
	}
	/* @SelectListWhere */
	public List<MissingProductsRelatoryOut> missing_products_relatory (long page_count, long page_size){
		String selectionArgs = new String[]{ com.uisleandro.util.config.getTodayString() }; 
		Cursor cursor = context.getContentResolver().query("content://com.uisleandro.product/missing_products_relatory",null, null, selectionArgs, null);
		List<MissingProductsRelatoryOut> those = null;
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			those.add( MissingProductsRelatoryOut.FromCursor(cursor) );
			cursor.moveToNext();
		}
		return those;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @SelectOneWhere */
	public GetByIdOut get_by_id(Long id){
		String selectionArgs = new String[]{ String.valueOf(id) }; 
		Cursor cursor = context.getContentResolver().query("content://com.uisleandro.product/get_by_id",null, null, selectionArgs, null);
		GetByIdOut that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = GetByIdOut.FromCursor(cursor);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @SelectOneWhere */
	public GetByQrcodeOut get_by_qrcode(String barcode){
		String selectionArgs = new String[]{  }; 
		Cursor cursor = context.getContentResolver().query("content://com.uisleandro.product/get_by_qrcode",null, null, selectionArgs, null);
		GetByQrcodeOut that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = GetByQrcodeOut.FromCursor(cursor);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle003
}
// reserved-for:AndroidSqliteDatabaseSingle003
// End of user code

