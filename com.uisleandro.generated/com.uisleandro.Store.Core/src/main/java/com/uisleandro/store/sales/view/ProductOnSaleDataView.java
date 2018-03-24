package com.uisleandro.store.sales.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class ProductOnSaleDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_sale;
	private long fk_product;

	public ProductOnSaleDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_sale = 0L;
		this.fk_product = 0L;
	}

	public long getId () {
		return id;
	}

	public void setId (long id) {
		this.id = id;
	}

	public long getServerId () {
		return server_id;
	}

	public void setServerId (long server_id) {
		this.server_id = server_id;
	}

	public boolean isDirty () {
		return dirty;
	}

	public void setDirty (boolean dirty) {
		this.dirty = dirty;
	}

	public long getLastUpdate () {
		return last_update;
	}

	public void setLastUpdate (long last_update) {
		this.last_update = last_update;
	}

	public long getFkSale () {
		return fk_sale;
	}

	public void setFkSale (long fk_sale) {
		this.fk_sale = fk_sale;
	}

	public long getFkProduct () {
		return fk_product;
	}

	public void setFkProduct (long fk_product) {
		this.fk_product = fk_product;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_sale\":\"" + this.fk_sale+ "\"," + 
			"\"fk_product\":\"" + this.fk_product+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return "ProductOnSaleDataView";

	}

	public static ProductOnSaleDataView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return ProductOnSaleDataView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static ProductOnSaleDataView FromJsonObj(JSONObject obj) {
		if(null != obj) {
			try {
				ProductOnSaleDataView result = new ProductOnSaleDataView();
				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				/* if(obj.has("dirty") && !obj.isNull("dirty")){
					result.setDirty(obj.getInt("dirty") > 0);
				} */
				result.setLastUpdate(obj.getLong("last_update"));
				if(obj.has("fk_sale") && !obj.isNull("fk_sale")){
					result.setFkSale(obj.getLong("fk_sale"));
				}
				if(obj.has("fk_product") && !obj.isNull("fk_product")){
					result.setFkProduct(obj.getLong("fk_product"));
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static ProductOnSaleDataView FromCursor (Cursor cursor) {
		if(null != cursor){
			ProductOnSaleDataView result = new ProductOnSaleDataView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setFkSale(cursor.getLong(4));
			result.setFkProduct(cursor.getLong(5));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), String.valueOf(fk_sale), String.valueOf(fk_product)};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), String.valueOf(fk_sale), String.valueOf(fk_product)};
	}

}
