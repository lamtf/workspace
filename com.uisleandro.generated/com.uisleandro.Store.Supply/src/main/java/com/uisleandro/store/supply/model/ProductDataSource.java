// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.supply.model;  

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;

import java.util.ArrayList;
import java.util.List;

import com.uisleandro.util.LoaderInterface;
import com.uisleandro.store.supply.view.ProductDataView;
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
import com.uisleandro.store.supply.view.MissingProductsRelatoryOut;
import com.uisleandro.store.supply.view.GetByIdOut;
import com.uisleandro.store.supply.view.GetByRepeatedProductCodeOut;
import com.uisleandro.store.supply.view.GetByQrcodeOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class ProductDataSource implements LoaderManager.LoaderCallbacks<Cursor> {

/* Yep this class implements the loaderCallbacks 

context.getloadermanager().init(FN_id, bundle, this)
I need to know about the class Bundle, which seems cary the data

*/

	public static final String AUTHORITY = "com.uisleandro.product";
	public static final String SCHEME = "content://";

	public static final int FN_PRODUCT_INSERT = 998261;
	public static final int FN_PRODUCT_UPDATE = 998262;
	public static final int FN_PRODUCT_DELETE = 998263;
	public static final int FN_PRODUCT_ALL = 998264;
	public static final int FN_PRODUCT_SOME = 998265;
	public static final int FN_PRODUCT_BY_ID = 998266;
	public static final int FN_PRODUCT_LAST_ID = 998267;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
	public static final int FN_PRODUCT_MISSING_PRODUCTS_RELATORY = 999211;
	public static final int FN_PRODUCT_GET_BY_ID = 999212;
	public static final int FN_PRODUCT_GET_BY_REPEATED_PRODUCT_CODE = 999213;
	public static final int FN_PRODUCT_START_PRODUCT_RECOUNTING = 999214;
	public static final int FN_PRODUCT_GET_BY_QRCODE = 999215;
	public static final int FN_PRODUCT_PRODUCT_RECOUNTING = 999216;
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	LoaderManager.LoaderCallbacks<Cursor> cursorLoader;
	public ProductDataSource (Context context) {
		this.context = context;
		cursorLoader = (LoaderManager.LoaderCallbacks<Cursor>) context; 
	}

	public List<ProductDataView> listAll () {
		List<ProductDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(ProductDataView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public ProductDataView getById (long id) {
		ProductDataView that = null;
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/by_id"), null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = ProductDataView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<ProductDataView> listSome (long page_count, long page_size) {
		List<ProductDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(ProductDataView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/last_id"), null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0);
		    }
		}
	    return result;	
	}

	public Uri insert (ProductDataView that) {
		Uri result = context.getContentResolver().insert(Uri.parse(SCHEME + AUTHORITY + "/insert"), that.toInsertValues());
		return result;
	}

	public int update (ProductDataView that) {
		return context.getContentResolver().update(Uri.parse(SCHEME + AUTHORITY + "/update"), that.toUpdateValues(), null, new String[]{ String.valueOf(that.getId()) });
	}

	public int delete (ProductDataView that) {
		return context.getContentResolver().delete(Uri.parse(SCHEME + AUTHORITY + "/delete"), null, new String[]{ String.valueOf(that.getId()) });
	}

// reserved-for:AndroidSqliteDatabaseSingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @SelectListWhere */
	public List<MissingProductsRelatoryOut> missing_products_relatory (long page_count, long page_size){
		String[] selectionArgs = new String[]{ com.uisleandro.util.config.getTodayString() }; 
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/missing_products_relatory"),null, null, selectionArgs, null);
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
		String[] selectionArgs = new String[]{ String.valueOf(id) }; 
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/get_by_id"),null, null, selectionArgs, null);
		GetByIdOut that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = GetByIdOut.FromCursor(cursor);
		}
		return that;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @SelectListWhere */
	public List<GetByRepeatedProductCodeOut> get_by_repeated_product_code (Long fk_systen, String barcode, long page_count, long page_size){
		String[] selectionArgs = new String[]{ String.valueOf(fk_systen), barcode }; 
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/get_by_repeated_product_code"),null, null, selectionArgs, null);
		List<GetByRepeatedProductCodeOut> those = null;
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			those.add( GetByRepeatedProductCodeOut.FromCursor(cursor) );
			cursor.moveToNext();
		}
		return those;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @SelectOneWhere */
	public GetByQrcodeOut get_by_qrcode(String barcode){
		String[] selectionArgs = new String[]{  }; 
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/get_by_qrcode"),null, null, selectionArgs, null);
		GetByQrcodeOut that = null;
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			that = GetByQrcodeOut.FromCursor(cursor);
		}
		return that;
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
	
		Cursor cursor = context.getContentResolver().update(Uri.parse(SCHEME + AUTHORITY + "/product_recounting"), contentValues, null, selectionArgs); 
		cursor.moveToFirst();
		if(!cursor.isAfterLast()){
			result = cursor.getInt(0);
		}
		return result;
	// TODO: PLEASE DON'T USE SYNCHRONIZED CODE
	
	}
// reserved-for:AndroidSqliteQuerySingle002
// End of user code


// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.2
  //TODO: PLEASE REVIEW THIS CODE
  public void please_remake_it(int i, Bundle bundle) {
// reserved-for:AndroidSqliteDatabaseSingle002.2
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.1

// reserved-for:AndroidSqliteQuerySingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.3
  }
// reserved-for:AndroidSqliteDatabaseSingle002.3
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.4
  @Override
  public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {

    if (FN_PRODUCT_INSERT == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/insert"), null, null, null, null);
    }
    else if (FN_PRODUCT_UPDATE == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/update"), null, null, null, null);
    }
    else if (FN_PRODUCT_DELETE == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/delete"), null, null, null, null);
    }
    else if (FN_PRODUCT_ALL == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
    }
    else if (FN_PRODUCT_SOME == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ bundle.getString("page_count"), bundle.getString("page_size") }, null, null, null);
    }
    else if (FN_PRODUCT_BY_ID == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/by_id"), null, null, new String[]{ bundle.getString("id") }, null);
    }
    else if (FN_PRODUCT_LAST_ID == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/last_id"), null, null, null, null);
    }
// reserved-for:AndroidSqliteDatabaseSingle002.4
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.2
	else if (FN_PRODUCT_MISSING_PRODUCTS_RELATORY == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/missing_products_relatory"), new String[]{}, null, null, null);
    }
	else if (FN_PRODUCT_GET_BY_ID == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/get_by_id"), new String[]{}, null, null, null);
    }
	else if (FN_PRODUCT_GET_BY_REPEATED_PRODUCT_CODE == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/get_by_repeated_product_code"), new String[]{}, null, null, null);
    }
	else if (FN_PRODUCT_START_PRODUCT_RECOUNTING == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/start_product_recounting"), new String[]{}, null, null, null);
    }
	else if (FN_PRODUCT_GET_BY_QRCODE == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/get_by_qrcode"), new String[]{}, null, null, null);
    }
	else if (FN_PRODUCT_PRODUCT_RECOUNTING == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/product_recounting"), new String[]{}, null, null, null);
    }
// reserved-for:AndroidSqliteQuerySingle002.2
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.5
    return null;
  }
// reserved-for:AndroidSqliteDatabaseSingle002.5
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.6
  @Override
  public void onLoaderReset(Loader<Cursor> cursorLoader) {
// reserved-for:AndroidSqliteDatabaseSingle002.6
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.3

// reserved-for:AndroidSqliteQuerySingle002.3
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.7
  }
// reserved-for:AndroidSqliteDatabaseSingle002.7
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.8
  @Override
  public void onLoadFinished(Loader<Cursor> cursorLoader, Cursor cursor) {
	((LoaderInterface)this.context).onLoadFinished(cursorLoader,cursor);
  }
}
// reserved-for:AndroidSqliteDatabaseSingle002.8
// End of user code

