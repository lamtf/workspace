
package com.uisleandro.store.client.view; 

import org.json.JSONException;
import org.json.JSONObject;

public class ClientFromSystemView{

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private long fk_system;
	private long fk_basic_client;
	private long fk_shared_client;
	private long fk_user;

	public ClientFromSystemView(){
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.fk_system = 0L;
		this.fk_basic_client = 0L;
		this.fk_shared_client = 0L;
		this.fk_user = 0L;

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

	public long getFkSystem(){
		return fk_system;
	}

	public void setFkSystem(long fk_system){
		this.fk_system = fk_system;
	}

	public long getFkBasicClient(){
		return fk_basic_client;
	}

	public void setFkBasicClient(long fk_basic_client){
		this.fk_basic_client = fk_basic_client;
	}

	public long getFkSharedClient(){
		return fk_shared_client;
	}

	public void setFkSharedClient(long fk_shared_client){
		this.fk_shared_client = fk_shared_client;
	}

	public long getFkUser(){
		return fk_user;
	}

	public void setFkUser(long fk_user){
		this.fk_user = fk_user;
	}


	public String toJsonString(){

		String that = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"fk_system\":\"" + this.fk_system+ "\"," + 
			"\"fk_basic_client\":\"" + this.fk_basic_client+ "\"," + 
			"\"fk_shared_client\":\"" + this.fk_shared_client+ "\"," + 
			"\"fk_user\":\"" + this.fk_user+ "\"" + 
		"}";

		return that;

	}

	public String toString(){

		return "ClientFromSystemView";

	}

	public static ClientFromSystemView FromJson(String json){

		if(json != null) {
		try {

		JSONObject obj = new JSONObject(json);
				ClientFromSystemView result = new ClientFromSystemView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				if(obj.has("server_id") && !obj.isNull("fk_system")){
					result.setFkSystem(obj.getLong("fk_system"));
				}
				if(obj.has("server_id") && !obj.isNull("fk_basic_client")){
					result.setFkBasicClient(obj.getLong("fk_basic_client"));
				}
				if(obj.has("server_id") && !obj.isNull("fk_shared_client")){
					result.setFkSharedClient(obj.getLong("fk_shared_client"));
				}
				if(obj.has("server_id") && !obj.isNull("fk_user")){
					result.setFkUser(obj.getLong("fk_user"));
				}

				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


	public static ClientFromSystemView FromJsonObj(JSONObject obj){

		if(null != obj) {
			try {
				ClientFromSystemView result = new ClientFromSystemView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				if(obj.has("fk_system") && !obj.isNull("fk_system")){
					result.setFkSystem(obj.getLong("fk_system"));
				}
				if(obj.has("fk_basic_client") && !obj.isNull("fk_basic_client")){
					result.setFkBasicClient(obj.getLong("fk_basic_client"));
				}
				if(obj.has("fk_shared_client") && !obj.isNull("fk_shared_client")){
					result.setFkSharedClient(obj.getLong("fk_shared_client"));
				}
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


}
