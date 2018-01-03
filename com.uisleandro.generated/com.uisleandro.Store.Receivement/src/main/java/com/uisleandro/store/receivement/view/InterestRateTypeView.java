
package com.uisleandro.store.receivement.view; 

import org.json.JSONException;
import org.json.JSONObject;

public class InterestRateTypeView{

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private String name;

	public InterestRateTypeView(){
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.name = "";

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

	public String getName(){
		return name;
	}

	public void setName(String name){
		this.name = name;
	}


	public String toJsonString(){

		String that = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"name\":\"" + this.name+ "\"" + 
		"}";

		return that;

	}

	public String toString(){

		return this.name;

	}

	public static InterestRateTypeView FromJson(String json){

		if(json != null) {
		try {

		JSONObject obj = new JSONObject(json);
				InterestRateTypeView result = new InterestRateTypeView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				result.setName(obj.getString("name"));

				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


	public static InterestRateTypeView FromJsonObj(JSONObject obj){

		if(null != obj) {
			try {
				InterestRateTypeView result = new InterestRateTypeView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				result.setName(obj.getString("name"));

				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


}
