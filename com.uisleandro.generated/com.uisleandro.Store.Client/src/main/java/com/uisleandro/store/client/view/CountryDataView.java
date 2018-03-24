package com.uisleandro.store.client.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.content.ContentValues;
import android.database.Cursor;

public class CountryDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private String name;

	public CountryDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.name = "";
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

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"name\":\"" + this.name+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.name;

	}

	public static CountryDataView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return CountryDataView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static CountryDataView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				CountryDataView result = new CountryDataView();
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
				result.setName(obj.getString("name"));
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static CountryDataView FromCursor (Cursor cursor) {
		if(null != cursor){
			CountryDataView result = new CountryDataView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setName(cursor.getString(4));
			return result;		
		}
		return null;
	}

	public ContentValues toInsertValues () {

		ContentValues contentValues = new ContentValues();
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		return contentValues;

	}

	public ContentValues toUpdateValues () {

		ContentValues contentValues = new ContentValues();
		contentValues.put("last_update",last_update);
		contentValues.put("name",name);
		return contentValues;

	}

}
