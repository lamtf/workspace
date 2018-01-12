package com.uisleandro.store.core.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class TokenDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_user;
	private long fk_system;
	private long fk_token_type;
	private String guid;
	private long last_use_time;
	private long expiration_time;

	public TokenView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_user = 0L;
		this.fk_system = 0L;
		this.fk_token_type = 0L;
		this.guid = "";
		this.last_use_time = 0L;
		this.expiration_time = 0L;
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

	public long getFkUser () {
		return fk_user;
	}

	public void setFkUser (long fk_user) {
		this.fk_user = fk_user;
	}

	public long getFkSystem () {
		return fk_system;
	}

	public void setFkSystem (long fk_system) {
		this.fk_system = fk_system;
	}

	public long getFkTokenType () {
		return fk_token_type;
	}

	public void setFkTokenType (long fk_token_type) {
		this.fk_token_type = fk_token_type;
	}

	public String getGuid () {
		return guid;
	}

	public void setGuid (String guid) {
		this.guid = guid;
	}

	public long getLastUseTime () {
		return last_use_time;
	}

	public void setLastUseTime (long last_use_time) {
		this.last_use_time = last_use_time;
	}

	public long getExpirationTime () {
		return expiration_time;
	}

	public void setExpirationTime (long expiration_time) {
		this.expiration_time = expiration_time;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_user\":\"" + this.fk_user+ "\"," + 
			"\"fk_system\":\"" + this.fk_system+ "\"," + 
			"\"fk_token_type\":\"" + this.fk_token_type+ "\"," + 
			"\"guid\":\"" + this.guid+ "\"," + 
			"\"last_use_time\":\"" + this.last_use_time+ "\"," + 
			"\"expiration_time\":\"" + this.expiration_time+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.guid;

	}

	public static TokenView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return TokenView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static TokenView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				TokenView result = new TokenView();
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
				if(obj.has("fk_user") && !obj.isNull("fk_user")){
					result.setFkUser(obj.getLong("fk_user"));
				}
				if(obj.has("fk_system") && !obj.isNull("fk_system")){
					result.setFkSystem(obj.getLong("fk_system"));
				}
				if(obj.has("fk_token_type") && !obj.isNull("fk_token_type")){
					result.setFkTokenType(obj.getLong("fk_token_type"));
				}
				result.setGuid(obj.getString("guid"));
				result.setLastUseTime(obj.getLong("last_use_time"));
				result.setExpirationTime(obj.getLong("expiration_time"));
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static TokenView FromCursor (Cursor cursor) {
		if(null != cursor){
			TokenView result = new TokenView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setFkUser(cursor.getLong(4));
			result.setFkSystem(cursor.getLong(5));
			result.setFkTokenType(cursor.getLong(6));
			result.setGuid(cursor.getString(7));
			result.setLastUseTime(cursor.getLong(8));
			result.setExpirationTime(cursor.getLong(9));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), String.valueOf(user), String.valueOf(system), String.valueOf(token_type), guid, String.valueOf(last_use_time), String.valueOf(expiration_time)};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), String.valueOf(user), String.valueOf(system), String.valueOf(token_type), guid, String.valueOf(last_use_time), String.valueOf(expiration_time)};
	}

}
