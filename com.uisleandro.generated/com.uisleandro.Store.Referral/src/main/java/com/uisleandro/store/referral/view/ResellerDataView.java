package com.uisleandro.store.referral.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class ResellerDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private int system_amount;
	private String name;
	private String address;
	private String neighborhood;
	private String city;
	private String state;
	private long zip_code;

	public ResellerDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.system_amount = 0;
		this.name = "";
		this.address = "";
		this.neighborhood = "";
		this.city = "";
		this.state = "";
		this.zip_code = 0L;
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

	public int getSystemAmount () {
		return system_amount;
	}

	public void setSystemAmount (int system_amount) {
		this.system_amount = system_amount;
	}

	public String getName () {
		return name;
	}

	public void setName (String name) {
		this.name = name;
	}

	public String getAddress () {
		return address;
	}

	public void setAddress (String address) {
		this.address = address;
	}

	public String getNeighborhood () {
		return neighborhood;
	}

	public void setNeighborhood (String neighborhood) {
		this.neighborhood = neighborhood;
	}

	public String getCity () {
		return city;
	}

	public void setCity (String city) {
		this.city = city;
	}

	public String getState () {
		return state;
	}

	public void setState (String state) {
		this.state = state;
	}

	public long getZipCode () {
		return zip_code;
	}

	public void setZipCode (long zip_code) {
		this.zip_code = zip_code;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"system_amount\":\"" + this.system_amount+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"address\":\"" + this.address+ "\"," + 
			"\"neighborhood\":\"" + this.neighborhood+ "\"," + 
			"\"city\":\"" + this.city+ "\"," + 
			"\"state\":\"" + this.state+ "\"," + 
			"\"zip_code\":\"" + this.zip_code+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.name;

	}

	public static ResellerView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return ResellerView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static ResellerView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				ResellerView result = new ResellerView();
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
				result.setSystemAmount(obj.getInt("system_amount"));
				result.setName(obj.getString("name"));
				result.setAddress(obj.getString("address"));
				result.setNeighborhood(obj.getString("neighborhood"));
				result.setCity(obj.getString("city"));
				result.setState(obj.getString("state"));
				if(obj.has("zip_code") && !obj.isNull("zip_code")){
					result.setZipCode(obj.getLong("zip_code"));
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static ResellerView FromCursor (Cursor cursor) {
		if(null != cursor){
			ResellerView result = new ResellerView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setSystemAmount(cursor.getInt(4));
			result.setName(cursor.getString(5));
			result.setAddress(cursor.getString(6));
			result.setNeighborhood(cursor.getString(7));
			result.setCity(cursor.getString(8));
			result.setState(cursor.getString(9));
			result.setZipCode(cursor.getLong(10));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), String.valueOf(system_amount), name, address, neighborhood, city, state, zip_code};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), String.valueOf(system_amount), name, address, neighborhood, city, state, zip_code};
	}

}
