
package com.uisleandro.store.discount.view; 

import org.json.JSONException;
import org.json.JSONObject;

public class DiscountView{

	private long id;
	private long server_id;
	private boolean dirty;
	private long last_update;
	private float value;
	private float percentage;
	private long fk_product;
	private long fk_category;
	private long fk_brand;
	private long fk_client_from_system;
	private long fk_gender;

	public DiscountView(){
		this.id = 0L;
		this.server_id = 0L;
		this.dirty = false;
		this.last_update = 0L;
		this.value = 0F;
		this.percentage = 0F;
		this.fk_product = 0L;
		this.fk_category = 0L;
		this.fk_brand = 0L;
		this.fk_client_from_system = 0L;
		this.fk_gender = 0L;

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

	public float getValue(){
		return value;
	}

	public void setValue(float value){
		this.value = value;
	}

	public float getPercentage(){
		return percentage;
	}

	public void setPercentage(float percentage){
		this.percentage = percentage;
	}

	public long getFkProduct(){
		return fk_product;
	}

	public void setFkProduct(long fk_product){
		this.fk_product = fk_product;
	}

	public long getFkCategory(){
		return fk_category;
	}

	public void setFkCategory(long fk_category){
		this.fk_category = fk_category;
	}

	public long getFkBrand(){
		return fk_brand;
	}

	public void setFkBrand(long fk_brand){
		this.fk_brand = fk_brand;
	}

	public long getFkClientFromSystem(){
		return fk_client_from_system;
	}

	public void setFkClientFromSystem(long fk_client_from_system){
		this.fk_client_from_system = fk_client_from_system;
	}

	public long getFkGender(){
		return fk_gender;
	}

	public void setFkGender(long fk_gender){
		this.fk_gender = fk_gender;
	}


	public String toJsonString(){

		String that = "{" +
			"\"client_id\":\"" + this.id + "\"," +
			"\"server_id\":\"" + this.server_id + "\"," +
			"\"last_update\":\"" + this.last_update+ "\"," + 
			"\"value\":\"" + this.value+ "\"," + 
			"\"percentage\":\"" + this.percentage+ "\"," + 
			"\"fk_product\":\"" + this.fk_product+ "\"," + 
			"\"fk_category\":\"" + this.fk_category+ "\"," + 
			"\"fk_brand\":\"" + this.fk_brand+ "\"," + 
			"\"fk_client_from_system\":\"" + this.fk_client_from_system+ "\"," + 
			"\"fk_gender\":\"" + this.fk_gender+ "\"" + 
		"}";

		return that;

	}

	public String toString(){

		return "DiscountView";

	}

	public static DiscountView FromJson(String json){

		if(json != null) {
		try {

		JSONObject obj = new JSONObject(json);
				DiscountView result = new DiscountView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				result.setValue(Float.valueOf(obj.getString("value")));
				result.setPercentage(Float.valueOf(obj.getString("percentage")));
				if(obj.has("server_id") && !obj.isNull("fk_product")){
					result.setFkProduct(obj.getLong("fk_product"));
				}
				if(obj.has("server_id") && !obj.isNull("fk_category")){
					result.setFkCategory(obj.getLong("fk_category"));
				}
				if(obj.has("server_id") && !obj.isNull("fk_brand")){
					result.setFkBrand(obj.getLong("fk_brand"));
				}
				if(obj.has("server_id") && !obj.isNull("fk_client_from_system")){
					result.setFkClientFromSystem(obj.getLong("fk_client_from_system"));
				}
				if(obj.has("server_id") && !obj.isNull("fk_gender")){
					result.setFkGender(obj.getLong("fk_gender"));
				}

				return result;

			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


	public static DiscountView FromJsonObj(JSONObject obj){

		if(null != obj) {
			try {
				DiscountView result = new DiscountView();

				if(obj.has("client_id") && !obj.isNull("client_id")){
					result.setId(obj.getLong("client_id"));
				}
				if(obj.has("server_id") && !obj.isNull("server_id")){
					result.setServerId(obj.getLong("server_id"));
				}
				result.setLastUpdate(obj.getLong("last_update"));
				result.setValue(Float.valueOf(obj.getString("value")));
				result.setPercentage(Float.valueOf(obj.getString("percentage")));
				if(obj.has("fk_product") && !obj.isNull("fk_product")){
					result.setFkProduct(obj.getLong("fk_product"));
				}
				if(obj.has("fk_category") && !obj.isNull("fk_category")){
					result.setFkCategory(obj.getLong("fk_category"));
				}
				if(obj.has("fk_brand") && !obj.isNull("fk_brand")){
					result.setFkBrand(obj.getLong("fk_brand"));
				}
				if(obj.has("fk_client_from_system") && !obj.isNull("fk_client_from_system")){
					result.setFkClientFromSystem(obj.getLong("fk_client_from_system"));
				}
				if(obj.has("fk_gender") && !obj.isNull("fk_gender")){
					result.setFkGender(obj.getLong("fk_gender"));
				}

				return result;
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return null;

	}


}
