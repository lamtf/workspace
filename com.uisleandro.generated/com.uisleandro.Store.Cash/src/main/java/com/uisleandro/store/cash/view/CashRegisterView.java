
package com.uisleandro.store.cash.view; 

import org.json.JSONException;
import org.json.JSONObject;

public class CashRegisterView{

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_user;
	private float opening_value;
	private float received_value;
	private float closing_value;
	private long fk_currency;

	public CashRegisterView(){
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_user = 0L;
		this.opening_value = 0F;
		this.received_value = 0F;
		this.closing_value = 0F;
		this.fk_currency = 0L;

	}

	public long getId(){
		return id;
	}

	public void setId(long id){
		this.id = id;
	}

	public long getServerId(){
		return server_id;
	}

	public void setServerId(long server_id){
		this.server_id = server_id;
	}

	public boolean isDirty(){
		return dirty;
	}

	public void setDirty(boolean dirty){
		this.dirty = dirty;
	}

	public long getLastUpdate(){
		return last_update;
	}

	public void setLastUpdate(long last_update){
		this.last_update = last_update;
	}

	public long getFkUser(){
		return fk_user;
	}

	public void setFkUser(long fk_user){
		this.fk_user = fk_user;
	}

	public float getOpeningValue(){
		return opening_value;
	}

	public void setOpeningValue(float opening_value){
		this.opening_value = opening_value;
	}

	public float getReceivedValue(){
		return received_value;
	}

	public void setReceivedValue(float received_value){
		this.received_value = received_value;
	}

	public float getClosingValue(){
		return closing_value;
	}

	public void setClosingValue(float closing_value){
		this.closing_value = closing_value;
	}

	public long getFkCurrency(){
		return fk_currency;
	}

	public void setFkCurrency(long fk_currency){
		this.fk_currency = fk_currency;
	}


	public String toJsonString(){

		String that = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_user\":\"" + this.fk_user+ "\"," + 
			"\"opening_value\":\"" + this.opening_value+ "\"," + 
			"\"received_value\":\"" + this.received_value+ "\"," + 
			"\"closing_value\":\"" + this.closing_value+ "\"," + 
			"\"fk_currency\":\"" + this.fk_currency+ "\"" + 
		"}";

		return that;

	}

	public String toString(){

		return "CashRegisterView";

	}

	public static CashRegisterView FromJson(String json){

		if(json != null) {
		try {

		JSONObject obj = new JSONObject(json);
				CashRegisterView result = new CashRegisterView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				if(obj.has("server_id") && !obj.isNull("fk_user")){
					result.setFkUser(obj.getLong("fk_user"));
				}
				result.setOpeningValue(Float.valueOf(obj.getString("opening_value")));
				result.setReceivedValue(Float.valueOf(obj.getString("received_value")));
				result.setClosingValue(Float.valueOf(obj.getString("closing_value")));
				if(obj.has("server_id") && !obj.isNull("fk_currency")){
					result.setFkCurrency(obj.getLong("fk_currency"));
				}

				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


	public static CashRegisterView FromJsonObj(JSONObject obj){

		if(null != obj) {
			try {
				CashRegisterView result = new CashRegisterView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				if(obj.has("fk_user") && !obj.isNull("fk_user")){
					result.setFkUser(obj.getLong("fk_user"));
				}
				result.setOpeningValue(Float.valueOf(obj.getString("opening_value")));
				result.setReceivedValue(Float.valueOf(obj.getString("received_value")));
				result.setClosingValue(Float.valueOf(obj.getString("closing_value")));
				if(obj.has("fk_currency") && !obj.isNull("fk_currency")){
					result.setFkCurrency(obj.getLong("fk_currency"));
				}

				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


}
