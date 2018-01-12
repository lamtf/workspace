package com.uisleandro.store.core.view; 

import org.json.JSONException;
import org.json.JSONObject;
import android.database.Cursor;

public class SystemDataView {

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private String name;
	private boolean enabled;
	private long fk_currency;
	private String fantasy_name;
	private String stores_address;
	private String srores_address_number;
	private String stores_city;
	private String stores_neighborhood;
	private String stores_zip_code;
	private String stores_state;
	private String stores_email;
	private String stores_phonenumber;
	private long fk_refarral;

	public SystemView () {
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.name = "";
		this.enabled = false;
		this.fk_currency = 0L;
		this.fantasy_name = "";
		this.stores_address = "";
		this.srores_address_number = "";
		this.stores_city = "";
		this.stores_neighborhood = "";
		this.stores_zip_code = "";
		this.stores_state = "";
		this.stores_email = "";
		this.stores_phonenumber = "";
		this.fk_refarral = 0L;
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

	public boolean getEnabled () {
		return enabled;
	}

	public void setEnabled (boolean enabled) {
		this.enabled = enabled;
	}

	public long getFkCurrency () {
		return fk_currency;
	}

	public void setFkCurrency (long fk_currency) {
		this.fk_currency = fk_currency;
	}

	public String getFantasyName () {
		return fantasy_name;
	}

	public void setFantasyName (String fantasy_name) {
		this.fantasy_name = fantasy_name;
	}

	public String getStoresAddress () {
		return stores_address;
	}

	public void setStoresAddress (String stores_address) {
		this.stores_address = stores_address;
	}

	public String getSroresAddressNumber () {
		return srores_address_number;
	}

	public void setSroresAddressNumber (String srores_address_number) {
		this.srores_address_number = srores_address_number;
	}

	public String getStoresCity () {
		return stores_city;
	}

	public void setStoresCity (String stores_city) {
		this.stores_city = stores_city;
	}

	public String getStoresNeighborhood () {
		return stores_neighborhood;
	}

	public void setStoresNeighborhood (String stores_neighborhood) {
		this.stores_neighborhood = stores_neighborhood;
	}

	public String getStoresZipCode () {
		return stores_zip_code;
	}

	public void setStoresZipCode (String stores_zip_code) {
		this.stores_zip_code = stores_zip_code;
	}

	public String getStoresState () {
		return stores_state;
	}

	public void setStoresState (String stores_state) {
		this.stores_state = stores_state;
	}

	public String getStoresEmail () {
		return stores_email;
	}

	public void setStoresEmail (String stores_email) {
		this.stores_email = stores_email;
	}

	public String getStoresPhonenumber () {
		return stores_phonenumber;
	}

	public void setStoresPhonenumber (String stores_phonenumber) {
		this.stores_phonenumber = stores_phonenumber;
	}

	public long getFkRefarral () {
		return fk_refarral;
	}

	public void setFkRefarral (long fk_refarral) {
		this.fk_refarral = fk_refarral;
	}

	public String toJsonString () {
		String result = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"name\":\"" + this.name+ "\"," + 
			"\"enabled\":\"" + this.enabled+ "\"," + 
			"\"fk_currency\":\"" + this.fk_currency+ "\"," + 
			"\"fantasy_name\":\"" + this.fantasy_name+ "\"," + 
			"\"stores_address\":\"" + this.stores_address+ "\"," + 
			"\"srores_address_number\":\"" + this.srores_address_number+ "\"," + 
			"\"stores_city\":\"" + this.stores_city+ "\"," + 
			"\"stores_neighborhood\":\"" + this.stores_neighborhood+ "\"," + 
			"\"stores_zip_code\":\"" + this.stores_zip_code+ "\"," + 
			"\"stores_state\":\"" + this.stores_state+ "\"," + 
			"\"stores_email\":\"" + this.stores_email+ "\"," + 
			"\"stores_phonenumber\":\"" + this.stores_phonenumber+ "\"," + 
			"\"fk_refarral\":\"" + this.fk_refarral+ "\"" + 
		"}";
		return result;
	}

	public String toString () {
		return this.name;

	}

	public static SystemView FromJson(String json){
		if(json != null) {
			try {
				JSONObject obj = new JSONObject(json);
				return SystemView.FromJsonObj(obj);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static SystemView FromJsonObj (JSONObject obj) {
		if(null != obj) {
			try {
				SystemView result = new SystemView();
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
				result.setEnabled(obj.getInt("enabled") > 0);
				if(obj.has("fk_currency") && !obj.isNull("fk_currency")){
					result.setFkCurrency(obj.getLong("fk_currency"));
				}
				result.setFantasyName(obj.getString("fantasy_name"));
				result.setStoresAddress(obj.getString("stores_address"));
				result.setSroresAddressNumber(obj.getString("srores_address_number"));
				result.setStoresCity(obj.getString("stores_city"));
				result.setStoresNeighborhood(obj.getString("stores_neighborhood"));
				result.setStoresZipCode(obj.getString("stores_zip_code"));
				result.setStoresState(obj.getString("stores_state"));
				result.setStoresEmail(obj.getString("stores_email"));
				result.setStoresPhonenumber(obj.getString("stores_phonenumber"));
				if(obj.has("fk_refarral") && !obj.isNull("fk_refarral")){
					result.setFkRefarral(obj.getLong("fk_refarral"));
				}
				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	public static SystemView FromCursor (Cursor cursor) {
		if(null != cursor){
			SystemView result = new SystemView();
			result.setId(cursor.getLong(0));
			result.setServerId(cursor.getLong(1));
			result.setDirty(cursor.getInt(2) > 0);
			result.setLastUpdate(cursor.getLong(3));
			result.setName(cursor.getString(4));
			result.setEnabled((cursor.getInt(5) > 0));
			result.setFkCurrency(cursor.getLong(6));
			result.setFantasyName(cursor.getString(7));
			result.setStoresAddress(cursor.getString(8));
			result.setSroresAddressNumber(cursor.getString(9));
			result.setStoresCity(cursor.getString(10));
			result.setStoresNeighborhood(cursor.getString(11));
			result.setStoresZipCode(cursor.getString(12));
			result.setStoresState(cursor.getString(13));
			result.setStoresEmail(cursor.getString(14));
			result.setStoresPhonenumber(cursor.getString(15));
			result.setFkRefarral(cursor.getLong(16));
			return result;		
		}
		return null;
	}

	public String[] toInsertArray () {
		return new String[]{String.valueOf(last_update), name, String.valueOf(enabled), String.valueOf(currency), fantasy_name, stores_address, srores_address_number, stores_city, stores_neighborhood, stores_zip_code, stores_state, stores_email, stores_phonenumber, String.valueOf(refarral)};
	}

	public String[] toUpdateArray () {
		return new String[]{String.valueOf(id), String.valueOf(server_id), String.valueOf(dirty), String.valueOf(last_update), name, String.valueOf(enabled), String.valueOf(currency), fantasy_name, stores_address, srores_address_number, stores_city, stores_neighborhood, stores_zip_code, stores_state, stores_email, stores_phonenumber, String.valueOf(refarral)};
	}

}
