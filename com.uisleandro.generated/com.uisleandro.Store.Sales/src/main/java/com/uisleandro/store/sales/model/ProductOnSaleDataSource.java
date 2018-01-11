// Start of user code reserved-for:AndroidSqliteDatabase001
package com.uisleandro.store.sales.model;  

import java.util.ArrayList;
import java.util.List;
import android.database.Cursor;
import android.net.Uri;
import android.util.Log;
import com.uisleandro.store.sales.view.ProductOnSaleView

public class ProductOnSaleDataSource {

	public static final String AUTHORITY = "com.uisleandro.product_on_sale";
	public static final String SCHEME = "content://";

	public static final String PRODUCT_ON_SALE_INSERT = SCHEME + AUTHORITY + "/insert";
	public static final String PRODUCT_ON_SALE_UPDATE = SCHEME + AUTHORITY + "/update";
	public static final String PRODUCT_ON_SALE_DELETE = SCHEME + AUTHORITY + "/delete";
	public static final String PRODUCT_ON_SALE_ALL = SCHEME + AUTHORITY + "/all";
	public static final String PRODUCT_ON_SALE_SOME = SCHEME + AUTHORITY + "/some";
	public static final String PRODUCT_ON_SALE_BYID = SCHEME + AUTHORITY + "/byid";
	public static final String PRODUCT_ON_SALE_LASTID = SCHEME + AUTHORITY + "/lastid";

	Context context;
	public ProductOnSaleDataSource(Context context){
		this.context = context;
	}

	public List<ProductOnSaleView> listAll(){
		List<ProductOnSaleView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(PRODUCT_ON_SALE_ALL, null, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      ProductOnSaleView that = ProductOnSaleView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public ProductOnSaleView getById(long id){
		CashRegister that = null;
		Cursor cursor = context.getContentResolver().query(PRODUCT_ON_SALE_BYID, null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = ProductOnSaleView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<ProductOnSaleView listSome(long page_count, long page_size){
		List<ProductOnSaleView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(PRODUCT_ON_SALE_SOME, new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      ProductOnSaleView that = ProductOnSaleView.FromCursor(cursor);
		      those.add(that);
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public long getLastId(){
		long result = 0;
		Cursor cursor = context.getContentResolver().query(PRODUCT_ON_SALE_LASTID, null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      result = cursor.getLong(0)
		    }
		}
	    return result;	
	}

	public int insert(ProductOnSaleView that) {
		context.getContentResolver().insert(PRODUCT_ON_SALE_INSERT, that.toInsertArray());
		return 0;
	}

	public int update(ProductOnSaleView that) {
		return context.getContentResolver().update(PRODUCT_ON_SALE_UPDATE, that.toUpdateArray(), that.getId());
	}

	public int delete(ProductOnSaleView that) {
		return context.getContentResolver().delete(PRODUCT_ON_SALE_DELETE, null, new String[]{ String.valueOf(that.getId()) });
	}

	public int deleteById(long id) {
		return context.getContentResolver().delete(PRODUCT_ON_SALE_DELETE, null, new String[]{ String.valueOf(id) });
	}

}

