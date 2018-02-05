// Start of user code reserved-for:AndroidSqliteDatabaseSingle001
package com.uisleandro.store.sales.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.sales.view.SaleDataView
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
import com.uisleandro.store.sales.view.ListProductsOnSalesChartOut;
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class SaleDataSource {

	public static final String AUTHORITY = "com.uisleandro.sale";
	public static final String SCHEME = "content://";

	public static final Integer FN_SALE_INSERT = 998431;
	public static final Integer FN_SALE_UPDATE = 998432;
	public static final Integer FN_SALE_DELETE = 998433;
	public static final Integer FN_SALE_ALL = 998434;
	public static final Integer FN_SALE_SOME = 998435;
	public static final Integer FN_SALE_BY_ID = 998436;
	public static final Integer FN_SALE_LAST_ID = 998437;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
	public static final Integer FN_SALE_REMOVE_ALL_PRODUCTS_FROM_SALES_CHART = 999411;
	public static final Integer FN_SALE_CREATE_SALES_CHART = 999412;
	public static final Integer FN_SALE_LIST_PRODUCTS_ON_SALES_CHART = 999413;
	public static final Integer FN_SALE_ADD_PRODUCT_TO_SALES_CHART = 999414;
	public static final Integer FN_SALE_CANCEL_SALES_CHART = 999415;
	public static final Integer FN_SALE_REMOVE_PRODUCT_FROM_SALES_CHART = 999416;
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	public SaleDataSource (Context context) {
		this.context = context;
	}

	public List<SaleView> listAll () {
		List<SaleView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/all", null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(SaleView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public SaleView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/by_id", null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = SaleView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<SaleView> listSome (long page_count, long page_size) {
		List<SaleView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/some", new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(SaleView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/last_id", null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (SaleView that) {
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/insert", that.toInsertArray());
		return 0;
	}

	public int update (SaleView that) {
		return context.getContentResolver().update(SCHEME + AUTHORITY + "/update", that.toUpdateArray(), that.getId());
	}

	public int delete (SaleView that) {
		return context.getContentResolver().delete(SCHEME + AUTHORITY + "/delete", null, new String[]{ String.valueOf(that.getId()) });
	}

// reserved-for:AndroidSqliteDatabaseSingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @DeleteWhere */
	public int remove_all_products_from_sales_chart(Long fk_sale){
		String selectionArgs = new String[]{ String.valueOf(fk_sale) };
		return context.getContentResolver().delete(SCHEME + AUTHORITY + "/remove_all_products_from_sales_chart", null, selectionArgs);
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @Insert */
	public int create_sales_chart (){
		ContentValues contentValues = new ContentValues(4);
		contentValues.put("last_update",com.uisleandro.util.config.getRightNowString());
		contentValues.put("total_value","0");
		contentValues.put("fk_system",com.uisleandro.util.config.getSystemIdString());
		contentValues.put("fk_user",com.uisleandro.util.config.getUserIdString());
	
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/create_sales_chart", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNCHRONIZED CODE
	}
	
	
	/* @SelectListWhere */
	public List<ListProductsOnSalesChartOut> list_products_on_sales_chart (Long fk_sale, long page_count, long page_size){
		String selectionArgs = new String[]{ String.valueOf(fk_sale) }; 
		Cursor cursor = context.getContentResolver().query(SCHEME + AUTHORITY + "/list_products_on_sales_chart",null, null, selectionArgs, null);
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
	public int add_product_to_sales_chart (Long fk_sale, Long fk_product){
		ContentValues contentValues = new ContentValues(3);
		contentValues.put("last_update",com.uisleandro.util.config.getRightNowString());
		contentValues.put("fk_sale",fk_sale);
		contentValues.put("fk_product",fk_product);
	
		context.getContentResolver().insert(SCHEME + AUTHORITY + "/add_product_to_sales_chart", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNCHRONIZED CODE
	}
	
	
	/* @DeleteWhere */
	public int cancel_sales_chart(Long id){
		String selectionArgs = new String[]{ String.valueOf(id) };
		return context.getContentResolver().delete(SCHEME + AUTHORITY + "/cancel_sales_chart", null, selectionArgs);
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @DeleteWhere */
	public int remove_product_from_sales_chart(Long fk_sale, Long fk_product){
		String selectionArgs = new String[]{ String.valueOf(fk_sale), String.valueOf(fk_product) };
		return context.getContentResolver().delete(SCHEME + AUTHORITY + "/remove_product_from_sales_chart", null, selectionArgs);
	// TODO: PLEASE DONT USE SYNC CODE
	}
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle003
}
// reserved-for:AndroidSqliteDatabaseSingle003
// End of user code
