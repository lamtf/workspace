
package com.uisleandro.store.refarral.view; 

import org.json.JSONException;
import org.json.JSONObject;

public class RefarralView{

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

	public RefarralView(){
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

	public int getSystemAmount(){
		return system_amount;
	}

	public void setSystemAmount(int system_amount){
		this.system_amount = system_amount;
	}

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}

	public String getAddress(){
		return address;
	}

	public void setAddress(String address){
		this.address = address;
	}

	public String getNeighborhood(){
		return neighborhood;
	}

	public void setNeighborhood(String neighborhood){
		this.neighborhood = neighborhood;
	}

	public String getCity(){
		return city;
	}

	public void setCity(String city){
		this.city = city;
	}

	public String getState(){
		return state;
	}

	public void setState(String state){
		this.state = state;
	}

	public long getZipCode(){
		return zip_code;
	}

	public void setZipCode(long zip_code){
		this.zip_code = zip_code;
	}


	public String toJsonString(){

		String that = "{" +
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

		return that;

	}

	public String toString(){

		return this.name;

	}

	public static RefarralView FromJson(String json){

		if(json != null) {
		try {

		JSONObject obj = new JSONObject(json);
				RefarralView result = new RefarralView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				result.setSystemAmount(obj.getInt("system_amount"));
				result.setName(obj.getString("name"));
				result.setAddress(obj.getString("address"));
				result.setNeighborhood(obj.getString("neighborhood"));
				result.setCity(obj.getString("city"));
				result.setState(obj.getString("state"));
				if(obj.has("server_id") && !obj.isNull("zip_code")){
					result.setZipCode(obj.getLong("zip_code"));
				}

				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


	public static RefarralView FromJsonObj(JSONObject obj){

		if(null != obj) {
			try {
				RefarralView result = new RefarralView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
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


}
