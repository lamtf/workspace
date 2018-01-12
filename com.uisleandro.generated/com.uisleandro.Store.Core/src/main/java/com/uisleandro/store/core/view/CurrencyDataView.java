package com.uisleandro.store.core.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class CurrencyDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private String abbreviature;
	private String description;

	public CurrencyView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.abbreviature = "";
		this.description = "";
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

	public String getAbbreviature () {
		return abbreviature;
	}

	public void setAbbreviature (String abbreviature) {
		this.abbreviature = abbreviature;
	}

	public String getDescription () {
		return description;
	}

	public void setDescription (String description) {
		this.description = description;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"abbreviature\":\"" + this.abbreviature+ "\"," + 
			"\"description\":\"" + this.description+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.abbreviature;

	}

	public static CurrencyView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return CurrencyView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static CurrencyView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				CurrencyView result = new CurrencyView();
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
				result.setAbbreviature(obj.getString("abbreviature"));
				result.setDescription(obj.getString("description"));
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static CurrencyView FromCursor (Cursor cursor) {
		if(null != cursor){
			CurrencyView result = new CurrencyView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setAbbreviature(cursor.getString(4));
			result.setDescription(cursor.getString(5));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), abbreviature, description};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), abbreviature, description};
	}

}
