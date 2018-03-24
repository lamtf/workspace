package com.uisleandro.store.cash.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class CashRegisterDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_user;
	private float opening_value;
	private float received_value;
	private float closing_value;

	public CashRegisterDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_user = 0L;
		this.opening_value = 0F;
		this.received_value = 0F;
		this.closing_value = 0F;
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

	public float getOpeningValue () {
		return opening_value;
	}

	public void setOpeningValue (float opening_value) {
		this.opening_value = opening_value;
	}

	public float getReceivedValue () {
		return received_value;
	}

	public void setReceivedValue (float received_value) {
		this.received_value = received_value;
	}

	public float getClosingValue () {
		return closing_value;
	}

	public void setClosingValue (float closing_value) {
		this.closing_value = closing_value;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_user\":\"" + this.fk_user+ "\"," + 
			"\"opening_value\":\"" + this.opening_value+ "\"," + 
			"\"received_value\":\"" + this.received_value+ "\"," + 
			"\"closing_value\":\"" + this.closing_value+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return "CashRegisterDataView";

	}

	public static CashRegisterDataView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return CashRegisterDataView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static CashRegisterDataView FromJsonObj(JSONObject obj) {
		if(null != obj) {
			try {
				CashRegisterDataView result = new CashRegisterDataView();
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
				result.setOpeningValue(Float.valueOf(obj.getString("opening_value")));
				result.setReceivedValue(Float.valueOf(obj.getString("received_value")));
				result.setClosingValue(Float.valueOf(obj.getString("closing_value")));
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static CashRegisterDataView FromCursor (Cursor cursor) {
		if(null != cursor){
			CashRegisterDataView result = new CashRegisterDataView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setFkUser(cursor.getLong(4));
			result.setOpeningValue(cursor.getFloat(5));
			result.setReceivedValue(cursor.getFloat(6));
			result.setClosingValue(cursor.getFloat(7));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), String.valueOf(fk_user), String.valueOf(opening_value), String.valueOf(received_value), String.valueOf(closing_value)};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), String.valueOf(fk_user), String.valueOf(opening_value), String.valueOf(received_value), String.valueOf(closing_value)};
	}

}
