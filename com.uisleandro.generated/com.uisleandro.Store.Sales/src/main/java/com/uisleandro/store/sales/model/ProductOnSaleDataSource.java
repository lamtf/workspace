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
import com.uisleandro.store.sales.view.ProductOnSaleDataView;
// reserved-for:AndroidSqliteDatabaseSingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001
// reserved-for:AndroidSqliteQuerySingle001
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002
public class ProductOnSaleDataSource implements LoaderManager.LoaderCallbacks<Cursor> {

/* Yep this class implements the loaderCallbacks 

context.getloadermanager().init(FN_id, bundle, this)
I need to know about the class Bundle, which seems cary the data

*/

	public static final String AUTHORITY = "com.uisleandro.product_on_sale";
	public static final String SCHEME = "content://";

	public static final int FN_PRODUCT_ON_SALE_INSERT = 998411;
	public static final int FN_PRODUCT_ON_SALE_UPDATE = 998412;
	public static final int FN_PRODUCT_ON_SALE_DELETE = 998413;
	public static final int FN_PRODUCT_ON_SALE_ALL = 998414;
	public static final int FN_PRODUCT_ON_SALE_SOME = 998415;
	public static final int FN_PRODUCT_ON_SALE_BY_ID = 998416;
	public static final int FN_PRODUCT_ON_SALE_LAST_ID = 998417;

// reserved-for:AndroidSqliteDatabaseSingle002
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle001.1
// reserved-for:AndroidSqliteQuerySingle001.1
// End of user code

// Start of user code reserved-for:AndroidSqliteDatabaseSingle002.1

	Context context;
	LoaderManager.LoaderCallbacks<Cursor> cursorLoader;
	public ProductOnSaleDataSource (Context context) {
		this.context = context;
		cursorLoader = (LoaderManager.LoaderCallbacks<Cursor>) context; 
	}

	public List<ProductOnSaleDataView> listAll () {
		List<ProductOnSaleDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(ProductOnSaleDataView.FromCursor(cursor));
		      cursor.moveToNext();
		    }
		    cursor.close();
		}
	    return those;
	}

	public ProductOnSaleDataView getById (long id) {
		ProductOnSaleDataView that = null;
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/by_id"), null, null, new String[]{ String.valueOf(id) }, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    if(!cursor.isAfterLast()){
		      that = ProductOnSaleDataView.FromCursor(cursor);
		    }
			cursor.close();
		}
	    return that;
	}

	public List<ProductOnSaleDataView> listSome (long page_count, long page_size) {
		List<ProductOnSaleDataView> those = new ArrayList<>();
		Cursor cursor = context.getContentResolver().query(Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ String.valueOf(page_count), String.valueOf(page_size) }, null, null, null);
		if (null != cursor) {
			cursor.moveToFirst();
		    while(!cursor.isAfterLast()){
		      those.add(ProductOnSaleDataView.FromCursor(cursor));
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

	public Uri insert (ProductOnSaleDataView that) {
		Uri result = context.getContentResolver().insert(Uri.parse(SCHEME + AUTHORITY + "/insert"), that.toInsertValues());
		return result;
	}

	public int update (ProductOnSaleDataView that) {
		return context.getContentResolver().update(Uri.parse(SCHEME + AUTHORITY + "/update"), that.toUpdateValues(), null, new String[]{ String.valueOf(that.getId()) });
	}

	public int delete (ProductOnSaleDataView that) {
		return context.getContentResolver().delete(Uri.parse(SCHEME + AUTHORITY + "/delete"), null, new String[]{ String.valueOf(that.getId()) });
	}

// reserved-for:AndroidSqliteDatabaseSingle002.1
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002
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

    if (FN_PRODUCT_ON_SALE_INSERT == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/insert"), null, null, null, null);
    }
    else if (FN_PRODUCT_ON_SALE_UPDATE == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/update"), null, null, null, null);
    }
    else if (FN_PRODUCT_ON_SALE_DELETE == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/delete"), null, null, null, null);
    }
    else if (FN_PRODUCT_ON_SALE_ALL == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/all"), null, null, null, null);
    }
    else if (FN_PRODUCT_ON_SALE_SOME == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/some"), new String[]{ bundle.getString("page_count"), bundle.getString("page_size") }, null, null, null);
    }
    else if (FN_PRODUCT_ON_SALE_BY_ID == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/by_id"), null, null, new String[]{ bundle.getString("id") }, null);
    }
    else if (FN_PRODUCT_ON_SALE_LAST_ID == i) {
		return new CursorLoader(this.context, Uri.parse(SCHEME + AUTHORITY + "/last_id"), null, null, null, null);
    }
// reserved-for:AndroidSqliteDatabaseSingle002.4
// End of user code

// Start of user code reserved-for:AndroidSqliteQuerySingle002.2
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

