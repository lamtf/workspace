package com.uisleandro.store.supply.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class DistributorContactDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private String name;
	private String email1;
	private String email2;
	private String phone_number1;
	private String phone_number2;
	private String phone_number3;
	private String phone_number4;
	private long fk_brand;

	public DistributorContactDataView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.name = "";
		this.email1 = "";
		this.email2 = "";
		this.phone_number1 = "";
		this.phone_number2 = "";
		this.phone_number3 = "";
		this.phone_number4 = "";
		this.fk_brand = 0L;
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

	public String getEmail1 () {
		return email1;
	}

	public void setEmail1 (String email1) {
		this.email1 = email1;
	}

	public String getEmail2 () {
		return email2;
	}

	public void setEmail2 (String email2) {
		this.email2 = email2;
	}

	public String getPhoneNumber1 () {
		return phone_number1;
	}

	public void setPhoneNumber1 (String phone_number1) {
		this.phone_number1 = phone_number1;
	}

	public String getPhoneNumber2 () {
		return phone_number2;
	}

	public void setPhoneNumber2 (String phone_number2) {
		this.phone_number2 = phone_number2;
	}

	public String getPhoneNumber3 () {
		return phone_number3;
	}

	public void setPhoneNumber3 (String phone_number3) {
		this.phone_number3 = phone_number3;
	}

	public String getPhoneNumber4 () {
		return phone_number4;
	}

	public void setPhoneNumber4 (String phone_number4) {
		this.phone_number4 = phone_number4;
	}

	public long getFkBrand () {
		return fk_brand;
	}

	public void setFkBrand (long fk_brand) {
		this.fk_brand = fk_brand;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"email1\":\"" + this.email1+ "\"," + 
			"\"email2\":\"" + this.email2+ "\"," + 
			"\"phone_number1\":\"" + this.phone_number1+ "\"," + 
			"\"phone_number2\":\"" + this.phone_number2+ "\"," + 
			"\"phone_number3\":\"" + this.phone_number3+ "\"," + 
			"\"phone_number4\":\"" + this.phone_number4+ "\"," + 
			"\"fk_brand\":\"" + this.fk_brand+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.name;

	}

	public static DistributorContactDataView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return DistributorContactDataView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static DistributorContactDataView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				DistributorContactDataView result = new DistributorContactDataView();
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
				result.setEmail1(obj.getString("email1"));
				result.setEmail2(obj.getString("email2"));
				result.setPhoneNumber1(obj.getString("phone_number1"));
				result.setPhoneNumber2(obj.getString("phone_number2"));
				result.setPhoneNumber3(obj.getString("phone_number3"));
				result.setPhoneNumber4(obj.getString("phone_number4"));
				if(obj.has("fk_brand") && !obj.isNull("fk_brand")){
					result.setFkBrand(obj.getLong("fk_brand"));
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static DistributorContactDataView FromCursor (Cursor cursor) {
		if(null != cursor){
			DistributorContactDataView result = new DistributorContactDataView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setName(cursor.getString(4));
			result.setEmail1(cursor.getString(5));
			result.setEmail2(cursor.getString(6));
			result.setPhoneNumber1(cursor.getString(7));
			result.setPhoneNumber2(cursor.getString(8));
			result.setPhoneNumber3(cursor.getString(9));
			result.setPhoneNumber4(cursor.getString(10));
			result.setFkBrand(cursor.getLong(11));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), name, email1, email2, phone_number1, phone_number2, phone_number3, phone_number4, String.valueOf(brand)};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), name, email1, email2, phone_number1, phone_number2, phone_number3, phone_number4, String.valueOf(brand)};
	}

}
