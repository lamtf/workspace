package com.uisleandro.store.sales.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class SaleDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_sale_type;
	private long fk_system;
	private float total_value;
	private long fk_user;
	private long fk_client_from_system;

	public SaleDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_sale_type = 0L;
		this.fk_system = 0L;
		this.total_value = 0F;
		this.fk_user = 0L;
		this.fk_client_from_system = 0L;
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

	public long getFkSaleType () {
		return fk_sale_type;
	}

	public void setFkSaleType (long fk_sale_type) {
		this.fk_sale_type = fk_sale_type;
	}

	public long getFkSystem () {
		return fk_system;
	}

	public void setFkSystem (long fk_system) {
		this.fk_system = fk_system;
	}

	public float getTotalValue () {
		return total_value;
	}

	public void setTotalValue (float total_value) {
		this.total_value = total_value;
	}

	public long getFkUser () {
		return fk_user;
	}

	public void setFkUser (long fk_user) {
		this.fk_user = fk_user;
	}

	public long getFkClientFromSystem () {
		return fk_client_from_system;
	}

	public void setFkClientFromSystem (long fk_client_from_system) {
		this.fk_client_from_system = fk_client_from_system;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_sale_type\":\"" + this.fk_sale_type+ "\"," + 
			"\"fk_system\":\"" + this.fk_system+ "\"," + 
			"\"total_value\":\"" + this.total_value+ "\"," + 
			"\"fk_user\":\"" + this.fk_user+ "\"," + 
			"\"fk_client_from_system\":\"" + this.fk_client_from_system+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return "SaleDataView";

	}

	public static SaleDataView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return SaleDataView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static SaleDataView FromJsonObj(JSONObject obj) {
		if(null != obj) {
			try {
				SaleDataView result = new SaleDataView();
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
				if(obj.has("fk_sale_type") && !obj.isNull("fk_sale_type")){
					result.setFkSaleType(obj.getLong("fk_sale_type"));
				}
				if(obj.has("fk_system") && !obj.isNull("fk_system")){
					result.setFkSystem(obj.getLong("fk_system"));
				}
				result.setTotalValue(Float.valueOf(obj.getString("total_value")));
				if(obj.has("fk_user") && !obj.isNull("fk_user")){
					result.setFkUser(obj.getLong("fk_user"));
				}
				if(obj.has("fk_client_from_system") && !obj.isNull("fk_client_from_system")){
					result.setFkClientFromSystem(obj.getLong("fk_client_from_system"));
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static SaleDataView FromCursor (Cursor cursor) {
		if(null != cursor){
			SaleDataView result = new SaleDataView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setFkSaleType(cursor.getLong(4));
			result.setFkSystem(cursor.getLong(5));
			result.setTotalValue(cursor.getFloat(6));
			result.setFkUser(cursor.getLong(7));
			result.setFkClientFromSystem(cursor.getLong(8));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), String.valueOf(fk_sale_type), String.valueOf(fk_system), String.valueOf(total_value), String.valueOf(fk_user), String.valueOf(fk_client_from_system)};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), String.valueOf(fk_sale_type), String.valueOf(fk_system), String.valueOf(total_value), String.valueOf(fk_user), String.valueOf(fk_client_from_system)};
	}

}
