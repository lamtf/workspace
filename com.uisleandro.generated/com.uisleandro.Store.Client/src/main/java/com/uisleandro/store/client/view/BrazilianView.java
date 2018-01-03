
package com.uisleandro.store.client.view; 

import org.json.JSONException;
import org.json.JSONObject;

public class BrazilianView{

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private String cpf;
	private String rg;
	private long fk_basic_client;

	public BrazilianView(){
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.cpf = "";
		this.rg = "";
		this.fk_basic_client = 0L;

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

	public String getCpf(){
		return cpf;
	}

	public void setCpf(String cpf){
		this.cpf = cpf;
	}

	public String getRg(){
		return rg;
	}

	public void setRg(String rg){
		this.rg = rg;
	}

	public long getFkBasicClient(){
		return fk_basic_client;
	}

	public void setFkBasicClient(long fk_basic_client){
		this.fk_basic_client = fk_basic_client;
	}


	public String toJsonString(){

		String that = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"cpf\":\"" + this.cpf+ "\"," + 
			"\"rg\":\"" + this.rg+ "\"," + 
			"\"fk_basic_client\":\"" + this.fk_basic_client+ "\"" + 
		"}";

		return that;

	}

	public String toString(){

		return this.cpf;

	}

	public static BrazilianView FromJson(String json){

		if(json != null) {
		try {

		JSONObject obj = new JSONObject(json);
				BrazilianView result = new BrazilianView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				result.setCpf(obj.getString("cpf"));
				result.setRg(obj.getString("rg"));
				if(obj.has("server_id") && !obj.isNull("fk_basic_client")){
					result.setFkBasicClient(obj.getLong("fk_basic_client"));
				}

				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


	public static BrazilianView FromJsonObj(JSONObject obj){

		if(null != obj) {
			try {
				BrazilianView result = new BrazilianView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				result.setCpf(obj.getString("cpf"));
				result.setRg(obj.getString("rg"));
				if(obj.has("fk_basic_client") && !obj.isNull("fk_basic_client")){
					result.setFkBasicClient(obj.getLong("fk_basic_client"));
				}

				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


}
