package com.uisleandro.store.supply.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class BrandDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private String company_name;
	private String fantasy_name;

	public BrandView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.company_name = "";
		this.fantasy_name = "";
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

	public String getCompanyName () {
		return company_name;
	}

	public void setCompanyName (String company_name) {
		this.company_name = company_name;
	}

	public String getFantasyName () {
		return fantasy_name;
	}

	public void setFantasyName (String fantasy_name) {
		this.fantasy_name = fantasy_name;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"company_name\":\"" + this.company_name+ "\"," + 
			"\"fantasy_name\":\"" + this.fantasy_name+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.company_name;

	}

	public static BrandView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return BrandView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static BrandView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				BrandView result = new BrandView();
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
				result.setCompanyName(obj.getString("company_name"));
				result.setFantasyName(obj.getString("fantasy_name"));
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static BrandView FromCursor (Cursor cursor) {
		if(null != cursor){
			BrandView result = new BrandView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setCompanyName(cursor.getString(4));
			result.setFantasyName(cursor.getString(5));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), company_name, fantasy_name};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), company_name, fantasy_name};
	}

}
