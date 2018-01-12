package com.uisleandro.store.core.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class UserDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_system;
	private long fk_role;
	private String username;
	private String password;
	private String name;
	private String email;
	private long last_use_time;
	private long last_error_time;
	private int error_count;
	private boolean active;

	public UserView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_system = 0L;
		this.fk_role = 0L;
		this.username = "";
		this.password = "";
		this.name = "";
		this.email = "";
		this.last_use_time = 0L;
		this.last_error_time = 0L;
		this.error_count = 0;
		this.active = false;
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

	public long getFkRole () {
		return fk_role;
	}

	public void setFkRole (long fk_role) {
		this.fk_role = fk_role;
	}

	public String getUsername () {
		return username;
	}

	public void setUsername (String username) {
		this.username = username;
	}

	public String getPassword () {
		return password;
	}

	public void setPassword (String password) {
		this.password = password;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getEmail () {
		return email;
	}

	public void setEmail (String email) {
		this.email = email;
	}

	public long getLastUseTime () {
		return last_use_time;
	}

	public void setLastUseTime (long last_use_time) {
		this.last_use_time = last_use_time;
	}

	public long getLastErrorTime () {
		return last_error_time;
	}

	public void setLastErrorTime (long last_error_time) {
		this.last_error_time = last_error_time;
	}

	public int getErrorCount () {
		return error_count;
	}

	public void setErrorCount (int error_count) {
		this.error_count = error_count;
	}

	public boolean getActive () {
		return active;
	}

	public void setActive (boolean active) {
		this.active = active;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_system\":\"" + this.fk_system+ "\"," + 
			"\"fk_role\":\"" + this.fk_role+ "\"," + 
			"\"username\":\"" + this.username+ "\"," + 
			"\"password\":\"" + this.password+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"email\":\"" + this.email+ "\"," + 
			"\"last_use_time\":\"" + this.last_use_time+ "\"," + 
			"\"last_error_time\":\"" + this.last_error_time+ "\"," + 
			"\"error_count\":\"" + this.error_count+ "\"," + 
			"\"active\":\"" + this.active+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.username;

	}

	public static UserView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return UserView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static UserView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				UserView result = new UserView();
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
				if(obj.has("fk_role") && !obj.isNull("fk_role")){
					result.setFkRole(obj.getLong("fk_role"));
				}
				result.setUsername(obj.getString("username"));
				result.setPassword(obj.getString("password"));
				result.setName(obj.getString("name"));
				result.setEmail(obj.getString("email"));
				result.setLastUseTime(obj.getLong("last_use_time"));
				result.setLastErrorTime(obj.getLong("last_error_time"));
				result.setErrorCount(obj.getInt("error_count"));
				result.setActive(obj.getInt("active") > 0);
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static UserView FromCursor (Cursor cursor) {
		if(null != cursor){
			UserView result = new UserView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setFkSystem(cursor.getLong(4));
			result.setFkRole(cursor.getLong(5));
			result.setUsername(cursor.getString(6));
			result.setPassword(cursor.getString(7));
			result.setName(cursor.getString(8));
			result.setEmail(cursor.getString(9));
			result.setLastUseTime(cursor.getLong(10));
			result.setLastErrorTime(cursor.getLong(11));
			result.setErrorCount(cursor.getInt(12));
			result.setActive((cursor.getInt(13) > 0));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), String.valueOf(system), String.valueOf(role), username, password, name, email, String.valueOf(last_use_time), String.valueOf(last_error_time), String.valueOf(error_count), String.valueOf(active)};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), String.valueOf(system), String.valueOf(role), username, password, name, email, String.valueOf(last_use_time), String.valueOf(last_error_time), String.valueOf(error_count), String.valueOf(active)};
	}

}
