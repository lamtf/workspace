// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.sales.model;  

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
import com.uisleandro.store.sales.view.SaleDataView;
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
import com.uisleandro.store.sales.view.ListProductsOnSalesChartOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class SaleDataSource implements LoaderManager.LoaderCallbacks<Cursor> {

/* Yep this class implements the loaderCallbacks 

context.getloadermanager().init(FN_id, bundle, this)
I need to know about the class Bundle, which seems cary the data

*/

	public static final String AUTHORITY = "com.uisleandro.sale";
	public static final String SCHEME = "content://";

	public static final int FN_SALE_INSERT = 998431;
	public static final int FN_SALE_UPDATE = 998432;
	public static final int FN_SALE_DELETE = 998433;
	public static final int FN_SALE_ALL = 998434;
	public static final int FN_SALE_SOME = 998435;
	public static final int FN_SALE_BY_ID = 998436;
	public static final int FN_SALE_LAST_ID = 998437;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
	public static final int FN_SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART = 999411;
	public static final int FN_SALE_CREATE_SALES_CHART = 999412;
	public static final int FN_SALE_LIST_PRODUCTS_ON_SALES_CHART = 999413;
	public static final int FN_SALE_ADD_PRODUCT_TO_SALES_CHART = 999414;
	public static final int FN_SALE_CANCEL_SALES_CHART = 999415;
	public static final int FN_SALE_REMOVE_PRODUCT_FROM_SALES_CHART = 999416;
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	LoaderManager.LoaderCallbacks<Cursor> cursorLoader;
	public SaleDataSource (Context context) {
		this.context = context;
		cursorLoader = (LoaderManager.LoaderCallbacks<Cursor>) context; 
	}

	public List<SaleDataView> listAll () {
		List<SaleDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(SaleDataView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public SaleDataView getById (long id) {
		SaleDataView that = null;
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/by_id"), null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = SaleDataView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<SaleDataView> listSome (long page_count, long page_size) {
		List<SaleDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(SaleDataView.FromCursor(cursor));
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

	public Uri insert (SaleDataView that) {
		Uri result = context.getContentResolver().insert(Uri.parse(SCHEME + AUTHORITY + "/insert"), that.toInsertValues());
		return result;
	}

	public int update (SaleDataView that) {
		return context.getContentResolver().update(Uri.parse(SCHEME + AUTHORITY + "/update"), that.toUpdateValues(), null, new String[]{ String.valueOf(that.getId()) });
	}

	public int delete (SaleDataView that) {
		return context.getContentResolver().delete(Uri.parse(SCHEME + AUTHORITY + "/delete"), null, new String[]{ String.valueOf(that.getId()) });
	}

// reserved-for:AndroidSqliteDatabaseSingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @DeleteWhere */
	public int remove_all_products_from_sales_chart(Long fk_sale){
		String[] selectionArgs = new String[]{ String.valueOf(fk_sale) };
		return context.getContentResolver().delete(Uri.parse(SCHEME + AUTHORITY + "/remove_all_products_from_sales_chart"), null, selectionArgs);
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @Insert */
	public Uri create_sales_chart (){
		ContentValues contentValues = new ContentValues(4);
		contentValues.put("last_update",com.uisleandro.util.config.getRightNowString());
		contentValues.put("total_value","0");
		contentValues.put("fk_system",com.uisleandro.util.config.getSystemIdString());
		contentValues.put("fk_user",com.uisleandro.util.config.getUserIdString());
	
		Uri result = context.getContentResolver().insert(Uri.parse(SCHEME + AUTHORITY + "/create_sales_chart"), contentValues);
	
	    return result;
	}
	
	
	/* @SelectListWhere */
	public List<ListProductsOnSalesChartOut> list_products_on_sales_chart (Long fk_sale, long page_count, long page_size){
		String[] selectionArgs = new String[]{ String.valueOf(fk_sale) }; 
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/list_products_on_sales_chart"),null, null, selectionArgs, null);
		List<ListProductsOnSalesChartOut> those = null;
		cursor.moveToFirst();
		while(!cursor.isAfterLast()){
			those.add( ListProductsOnSalesChartOut.FromCursor(cursor) );
			cursor.moveToNext();
		}
		return those;
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @Insert */
	public Uri add_product_to_sales_chart (Long fk_sale, Long fk_product){
		ContentValues contentValues = new ContentValues(3);
		contentValues.put("last_update",com.uisleandro.util.config.getRightNowString());
		contentValues.put("fk_sale",fk_sale);
		contentValues.put("fk_product",fk_product);
	
		Uri result = context.getContentResolver().insert(Uri.parse(SCHEME + AUTHORITY + "/add_product_to_sales_chart"), contentValues);
	
	    return result;
	}
	
	
	/* @DeleteWhere */
	public int cancel_sales_chart(Long id){
		String[] selectionArgs = new String[]{ String.valueOf(id) };
		return context.getContentResolver().delete(Uri.parse(SCHEME + AUTHORITY + "/cancel_sales_chart"), null, selectionArgs);
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @DeleteWhere */
	public int remove_product_from_sales_chart(Long fk_sale, Long fk_product){
		String[] selectionArgs = new String[]{ String.valueOf(fk_sale), String.valueOf(fk_product) };
		return context.getContentResolver().delete(Uri.parse(SCHEME + AUTHORITY + "/remove_product_from_sales_chart"), null, selectionArgs);
	// TODO: PLEASE DONT USE SYNC CODE
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

    if (FN_SALE_INSERT == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/insert"), null, null, null, null);
    }
    else if (FN_SALE_UPDATE == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/update"), null, null, null, null);
    }
    else if (FN_SALE_DELETE == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/delete"), null, null, null, null);
    }
    else if (FN_SALE_ALL == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
    }
    else if (FN_SALE_SOME == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ bundle.getString("page_count"), bundle.getString("page_size") }, null, null, null);
    }
    else if (FN_SALE_BY_ID == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/by_id"), null, null, new String[]{ bundle.getString("id") }, null);
    }
    else if (FN_SALE_LAST_ID == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/last_id"), null, null, null, null);
    }
// reserved-for:AndroidSqliteDatabaseSingle002.4
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.2
	else if (FN_SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/remove_all_products_from_sales_chart"), new String[]{}, null, null, null);
    }
	else if (FN_SALE_CREATE_SALES_CHART == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/create_sales_chart"), new String[]{}, null, null, null);
    }
	else if (FN_SALE_LIST_PRODUCTS_ON_SALES_CHART == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/list_products_on_sales_chart"), new String[]{}, null, null, null);
    }
	else if (FN_SALE_ADD_PRODUCT_TO_SALES_CHART == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/add_product_to_sales_chart"), new String[]{}, null, null, null);
    }
	else if (FN_SALE_CANCEL_SALES_CHART == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/cancel_sales_chart"), new String[]{}, null, null, null);
    }
	else if (FN_SALE_REMOVE_PRODUCT_FROM_SALES_CHART == i){
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/remove_product_from_sales_chart"), new String[]{}, null, null, null);
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

