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
	public static final String SALE_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String SALE_LASTID = SCHEME + AUTHORITY + "/lastid";

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
		      SaleView that = SaleView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public SaleView getById (long id) {
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(SALE_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = SaleView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<SaleView listSome (long page_count, long page_size) {
		List<SaleView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(SALE_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      SaleView that = SaleView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId () {
		long result = 0;
		Cursor cursor = context.getContentResolver().query(SALE_LASTID, null, null, null, null);
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
	/* @Insert */
	public int create_sales_chart (){
		String[] insertArgs = new String[]{ com.uisleandro.util.config.getRightNowString(), "0", com.uisleandro.util.config.getSystemIdString(), com.uisleandro.util.config.getUserIdString() };
		ContentValues contentValues = null; ~~~~> PLEASE FIX IT <~~~~~
		context.getContentResolver().insert("content://com.uisleandro.sale/create_sales_chart", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @DeleteWhere */
	public int cancel_sales_chart(Long id){
		String selectionArgs = new String[]{ String.valueOf(id) };
		return context.getContentResolver().delete("content://com.uisleandro.sale/cancel_sales_chart", null, selectionArgs);
	// TODO: PLEASE DONT USE SYNC CODE
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
		String[] insertArgs = new String[]{ com.uisleandro.util.config.getRightNowString(), String.valueOf(fk_sale), String.valueOf(fk_product) };
		ContentValues contentValues = null; ~~~~> PLEASE FIX IT <~~~~~
		context.getContentResolver().insert("content://com.uisleandro.sale/add_product_to_sales_chart", contentValues);
	// TODO: PLEASE SOLVE THE RETURN OF THE CURRENT FUNCTION
	// TODO: PLEASE DONT USE SYNC CODE
	}
	/* @DeleteWhere */
	public int remove_all_products_from_sales_chart(Long fk_sale){
		String selectionArgs = new String[]{ String.valueOf(fk_sale) };
		return context.getContentResolver().delete("content://com.uisleandro.sale/remove_all_products_from_sales_chart", null, selectionArgs);
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
