
package com.uisleandro.store.core.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class DbLogView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private String action_name;
	private String parameter;
	private long fk_user;

	public DbLogView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.action_name = "";
		this.parameter = "";
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

	public String getActionName () {
		return action_name;
	}

	public void setActionName (String action_name) {
		this.action_name = action_name;
	}

	public String getParameter () {
		return parameter;
	}

	public void setParameter (String parameter) {
		this.parameter = parameter;
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
			"\"action_name\":\"" + this.action_name+ "\"," + 
			"\"parameter\":\"" + this.parameter+ "\"," + 
			"\"fk_user\":\"" + this.fk_user+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.action_name;

	}

	public static DbLogView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return DbLogView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static DbLogView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				DbLogView result = new DbLogView();
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
				result.setActionName(obj.getString("action_name"));
				result.setParameter(obj.getString("parameter"));
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

	public static DbLogView FromCursor (Cursor cursor) {
		if(null != cursor){
			DbLogView result = new DbLogView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setActionName(cursor.getString(4));
			result.setParameter(cursor.getString(5));
			result.setFkUser(cursor.getLong(6));
			return result;		
		}
		return null;
	}
}
