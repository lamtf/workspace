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

	public static final String SALE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String SALE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String SALE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String SALE_ALL = SCHEME + AUTHORITY + "/all";
	public static final String SALE_SOME = SCHEME + AUTHORITY + "/some";
	public static final String SALE_BY_ID = SCHEME + AUTHORITY + "/by_id";
	public static final String SALE_LAST_ID = SCHEME + AUTHORITY + "/last_id";

	Context context;
	public SaleDataSource (Context context) {
		this.context = context;
	}

	public List<SaleView> listAll () {
		List<SaleView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SALE_ALL, null, null null, null);
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
		Cursor cursor = context.getContentResolver().query(SALE_BY_ID, null, null, new String[]{ String.valueOf(id) }, null);
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
		Cursor cursor = context.getContentResolver().query(SALE_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
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
		Cursor cursor = context.getContentResolver().query(SALE_LAST_ID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert (SaleView that) {
		context.getContentResolver().insert(SALE_INSERT, that.toInsertArray());
		return 0;
	}

	public int update (SaleView that) {
		return context.getContentResolver().update(SALE_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete (SaleView that) {
		return context.getContentResolver().delete(SALE_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById (long id) {
		return context.getContentResolver().delete(SALE_DELETE, null, new String[]{ String.valueOf(id) });
	}
// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
	/* @DeleteWhere */
	public int remove_all_products_from_sales_chart(Long fk_sale){
		String selectionArgs = new String[]{ String.valueOf(fk_sale) };
		return context.getContentResolver().delete("content://com.uisleandro.sale/remove_all_products_from_sales_chart", null, selectionArgs);
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @Insert */
	public int create_sales_chart (){
		ContentValues contentValues = new ContentValues(4);
		contentValues.put("last_update",com.uisleandro.util.config.getRightNowString());
		contentValues.put("total_value","0");
		contentValues.put("fk_system",com.uisleandro.util.config.getSystemIdString());
		contentValues.put("fk_user",com.uisleandro.util.config.getUserIdString());
	
		context.getContentResolver().insert("content://com.uisleandro.sale/create_sales_chart", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNCHRONIZED CODE
	}
	
	
	/* @SelectListWhere */
	public List<ListProductsOnSalesChartOut> list_products_on_sales_chart (Long fk_sale, long page_count, long page_size){
		String selectionArgs = new String[]{ String.valueOf(fk_sale) }; 
		Cursor cursor = context.getContentResolver().query("content://com.uisleandro.sale/list_products_on_sales_chart",null, null, selectionArgs, null);
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
	
		context.getContentResolver().insert("content://com.uisleandro.sale/add_product_to_sales_chart", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNCHRONIZED CODE
	}
	
	
	/* @DeleteWhere */
	public int cancel_sales_chart(Long id){
		String selectionArgs = new String[]{ String.valueOf(id) };
		return context.getContentResolver().delete("content://com.uisleandro.sale/cancel_sales_chart", null, selectionArgs);
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @DeleteWhere */
	public int remove_product_from_sales_chart(Long fk_sale, Long fk_product){
		String selectionArgs = new String[]{ String.valueOf(fk_sale), String.valueOf(fk_product) };
		return context.getContentResolver().delete("content://com.uisleandro.sale/remove_product_from_sales_chart", null, selectionArgs);
	// TODO: PLEASE DONT USE SYNC CODE
	}
// reserved-for:AndroidSqliteQuerySingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle003
}
// reserved-for:AndroidSqliteDatabaseSingle003
// End of user code
