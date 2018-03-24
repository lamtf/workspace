package com.uisleandro.store.client.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class ClientFromSystemDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_system;
	private long fk_basic_client;
	private long fk_shared_client;
	private long fk_user;

	public ClientFromSystemDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_system = 0L;
		this.fk_basic_client = 0L;
		this.fk_shared_client = 0L;
		this.fk_user = 0L;
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

	public long getFkSystem () {
		return fk_system;
	}

	public void setFkSystem (long fk_system) {
		this.fk_system = fk_system;
	}

	public long getFkBasicClient () {
		return fk_basic_client;
	}

	public void setFkBasicClient (long fk_basic_client) {
		this.fk_basic_client = fk_basic_client;
	}

	public long getFkSharedClient () {
		return fk_shared_client;
	}

	public void setFkSharedClient (long fk_shared_client) {
		this.fk_shared_client = fk_shared_client;
	}

	public long getFkUser () {
		return fk_user;
	}

	public void setFkUser (long fk_user) {
		this.fk_user = fk_user;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_system\":\"" + this.fk_system+ "\"," + 
			"\"fk_basic_client\":\"" + this.fk_basic_client+ "\"," + 
			"\"fk_shared_client\":\"" + this.fk_shared_client+ "\"," + 
			"\"fk_user\":\"" + this.fk_user+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return "ClientFromSystemDataView";

	}

	public static ClientFromSystemDataView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return ClientFromSystemDataView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static ClientFromSystemDataView FromJsonObj(JSONObject obj) {
		if(null != obj) {
			try {
				ClientFromSystemDataView result = new ClientFromSystemDataView();
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
				if(obj.has("fk_system") && !obj.isNull("fk_system")){
					result.setFkSystem(obj.getLong("fk_system"));
				}
				if(obj.has("fk_basic_client") && !obj.isNull("fk_basic_client")){
					result.setFkBasicClient(obj.getLong("fk_basic_client"));
				}
				if(obj.has("fk_shared_client") && !obj.isNull("fk_shared_client")){
					result.setFkSharedClient(obj.getLong("fk_shared_client"));
				}
				if(obj.has("fk_user") && !obj.isNull("fk_user")){
					result.setFkUser(obj.getLong("fk_user"));
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static ClientFromSystemDataView FromCursor (Cursor cursor) {
		if(null != cursor){
			ClientFromSystemDataView result = new ClientFromSystemDataView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setFkSystem(cursor.getLong(4));
			result.setFkBasicClient(cursor.getLong(5));
			result.setFkSharedClient(cursor.getLong(6));
			result.setFkUser(cursor.getLong(7));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), String.valueOf(fk_system), String.valueOf(fk_basic_client), String.valueOf(fk_shared_client), String.valueOf(fk_user)};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), String.valueOf(fk_system), String.valueOf(fk_basic_client), String.valueOf(fk_shared_client), String.valueOf(fk_user)};
	}

}
