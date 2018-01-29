package com.uisleandro.store.receivement.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class BankDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private String code;
	private String name;
	private String name;
	private String name;
	private String name;
	private String name;
	private String name;
	private String name;
	private String name;
	private String name;
	private String name;

	public BankDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.code = "";
		this.name = "";
		this.name = "";
		this.name = "";
		this.name = "";
		this.name = "";
		this.name = "";
		this.name = "";
		this.name = "";
		this.name = "";
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

	public String getCode () {
		return code;
	}

	public void setCode (String code) {
		this.code = code;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
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
			"\"code\":\"" + this.code+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"name\":\"" + this.name+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.name;

	}

	public static BankView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return BankView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static BankView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				BankView result = new BankView();
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
				result.setCode(obj.getString("code"));
				result.setName(obj.getString("name"));
				result.setName(obj.getString("name"));
				result.setName(obj.getString("name"));
				result.setName(obj.getString("name"));
				result.setName(obj.getString("name"));
				result.setName(obj.getString("name"));
				result.setName(obj.getString("name"));
				result.setName(obj.getString("name"));
				result.setName(obj.getString("name"));
				result.setName(obj.getString("name"));
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static BankView FromCursor (Cursor cursor) {
		if(null != cursor){
			BankView result = new BankView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setCode(cursor.getString(4));
			result.setName(cursor.getString(5));
			result.setName(cursor.getString(6));
			result.setName(cursor.getString(7));
			result.setName(cursor.getString(8));
			result.setName(cursor.getString(9));
			result.setName(cursor.getString(10));
			result.setName(cursor.getString(11));
			result.setName(cursor.getString(12));
			result.setName(cursor.getString(13));
			result.setName(cursor.getString(14));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), code, name, name, name, name, name, name, name, name, name, name};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), code, name, name, name, name, name, name, name, name, name, name};
	}

}
