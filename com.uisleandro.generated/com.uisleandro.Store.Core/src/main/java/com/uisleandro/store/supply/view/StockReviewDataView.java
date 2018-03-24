package com.uisleandro.store.supply.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class StockReviewDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_product;
	private int actual_amount;
	private int sold_items;
	private int previous_amount;
	private int missing_amount;

	public StockReviewDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_product = 0L;
		this.actual_amount = 0;
		this.sold_items = 0;
		this.previous_amount = 0;
		this.missing_amount = 0;
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

	public long getFkProduct () {
		return fk_product;
	}

	public void setFkProduct (long fk_product) {
		this.fk_product = fk_product;
	}

	public int getActualAmount () {
		return actual_amount;
	}

	public void setActualAmount (int actual_amount) {
		this.actual_amount = actual_amount;
	}

	public int getSoldItems () {
		return sold_items;
	}

	public void setSoldItems (int sold_items) {
		this.sold_items = sold_items;
	}

	public int getPreviousAmount () {
		return previous_amount;
	}

	public void setPreviousAmount (int previous_amount) {
		this.previous_amount = previous_amount;
	}

	public int getMissingAmount () {
		return missing_amount;
	}

	public void setMissingAmount (int missing_amount) {
		this.missing_amount = missing_amount;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_product\":\"" + this.fk_product+ "\"," + 
			"\"actual_amount\":\"" + this.actual_amount+ "\"," + 
			"\"sold_items\":\"" + this.sold_items+ "\"," + 
			"\"previous_amount\":\"" + this.previous_amount+ "\"," + 
			"\"missing_amount\":\"" + this.missing_amount+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return "StockReviewDataView";

	}

	public static StockReviewDataView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return StockReviewDataView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static StockReviewDataView FromJsonObj(JSONObject obj) {
		if(null != obj) {
			try {
				StockReviewDataView result = new StockReviewDataView();
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
				if(obj.has("fk_product") && !obj.isNull("fk_product")){
					result.setFkProduct(obj.getLong("fk_product"));
				}
				result.setActualAmount(obj.getInt("actual_amount"));
				result.setSoldItems(obj.getInt("sold_items"));
				result.setPreviousAmount(obj.getInt("previous_amount"));
				result.setMissingAmount(obj.getInt("missing_amount"));
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static StockReviewDataView FromCursor (Cursor cursor) {
		if(null != cursor){
			StockReviewDataView result = new StockReviewDataView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setFkProduct(cursor.getLong(4));
			result.setActualAmount(cursor.getInt(5));
			result.setSoldItems(cursor.getInt(6));
			result.setPreviousAmount(cursor.getInt(7));
			result.setMissingAmount(cursor.getInt(8));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), String.valueOf(fk_product), String.valueOf(actual_amount), String.valueOf(sold_items), String.valueOf(previous_amount), String.valueOf(missing_amount)};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), String.valueOf(fk_product), String.valueOf(actual_amount), String.valueOf(sold_items), String.valueOf(previous_amount), String.valueOf(missing_amount)};
	}

}
